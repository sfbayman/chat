/**
 *
 */
package com.ua.chat.service.impl;

import java.util.Collection;

import com.ua.chat.model.Message;
import com.ua.chat.persistence.MessagePersistenceService;
import com.ua.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Praveen Polishetty
 * @date 11/12/2018
 */
@Service
public class ChatServiceImpl implements ChatService {

  private final MessagePersistenceService messagePersistenceService;

  @Autowired
  public ChatServiceImpl(MessagePersistenceService messagePersistenceService) {
    this.messagePersistenceService = messagePersistenceService;
  }

  public Message get(Long id) {
    return messagePersistenceService.get(id);
  }

  public Collection<Message> list(String userName) {
    return messagePersistenceService.list(userName);
  }

  public Message create(Message message) {
    return messagePersistenceService.save(message);
  }
}
