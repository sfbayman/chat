/**
 *
 */
package com.ua.chat.persistence.impl;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.PersistenceException;

import com.ua.chat.model.Message;
import com.ua.chat.persistence.MessagePersistenceService;
import com.ua.chat.persistence.jpa.TextMessageArchiveRepository;
import com.ua.chat.persistence.jpa.TextMessageRepository;
import com.ua.chat.persistence.model.TextMessageArchiveEntity;
import com.ua.chat.persistence.model.TextMessageEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Praveen Polishetty
 * @date 11/12/2018
 */
@Service
public class MessagePersistenceServiceImpl implements MessagePersistenceService {

  private final TextMessageRepository textMessageRepository;
  private final TextMessageArchiveRepository textMessageArchiveRepository;

  @Autowired
  public MessagePersistenceServiceImpl(TextMessageRepository textMessageRepository,
      TextMessageArchiveRepository textMessageArchiveRepository) {
    this.textMessageRepository = textMessageRepository;
    this.textMessageArchiveRepository = textMessageArchiveRepository;
  }

  @Override
  public TextMessageEntity save(Message message) {
    TextMessageEntity messageEntity = new TextMessageEntity();
    BeanUtils.copyProperties(message, messageEntity);
    try {
      messageEntity = textMessageRepository.saveAndFlush(messageEntity);
    } catch (PersistenceException | DataAccessException ex) {
      //need to create exception un-wrapper and throw custom exception
      throw ex;
    }
    return messageEntity;
  }

  @Override
  public Message get(Long id) {
    Optional<TextMessageEntity> messageEntity = null;
    Optional<TextMessageArchiveEntity> messageArchiveEntity = null;
    try {
      messageEntity = textMessageRepository.findById(id);
      if (messageEntity.isPresent()) {
        return messageEntity.get();
      } else {
        messageArchiveEntity = textMessageArchiveRepository.findById(id);
        if (messageArchiveEntity.isPresent()) {
          return messageArchiveEntity.get();
        } else {
          return null;
        }
      }
    } catch (PersistenceException | DataAccessException ex) {
      //need to create exception un-wrapper and throw custom exception
      return null;
    }

  }

  @Override
  public Collection<Message> list(String userName) {
    List<TextMessageEntity> messages = null;
    List<TextMessageEntity> result = null;
    try {
      synchronized (this) {
        messages = textMessageRepository.findByUserName(userName);
        if (!messages.isEmpty()) {
          result = messages;
          updateExpiryDate(messages);
          archiveMessages(result);
        }
      }
    } catch (PersistenceException | DataAccessException ex) {
      //need to create exception un-wrapper and throw custom exception
      throw ex;
    }
    return Collections.unmodifiableCollection(messages);
  }

  @Async
  void updateExpiryDate(List<TextMessageEntity> messages) {
    for (TextMessageEntity message : messages) {
      message.setExpirationDate(OffsetDateTime.now(ZoneOffset.UTC));
    }
  }

  @Transactional
  @Async
  void archiveMessages(List<TextMessageEntity> messages) {
    textMessageRepository.deleteAll(messages);
    List<TextMessageArchiveEntity> archiveEntityList = new ArrayList<>();
    for (Message message : messages) {
      TextMessageArchiveEntity textMessageArchiveEntity = new TextMessageArchiveEntity();
      BeanUtils.copyProperties(message, textMessageArchiveEntity);
      textMessageArchiveEntity.setExpirationDate(OffsetDateTime.now(ZoneOffset.UTC));
      archiveEntityList.add(textMessageArchiveEntity);
    }
    textMessageArchiveRepository.saveAll(archiveEntityList);
  }
}
