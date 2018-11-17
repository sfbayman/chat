package com.ua.chat.persistence;

import java.util.Collection;

import com.ua.chat.model.Message;
import com.ua.chat.persistence.model.TextMessageEntity;

/**
 * TODO: Document what MessagePersistenceService is.
 *
 * @author Praveen Polishetty 212602153
 * @version 1.0 Nov 15, 2018
 * @since 1.0
 */
public interface MessagePersistenceService {

  TextMessageEntity save(Message message);

  Message get(Long id);

  Collection<Message> list(String userName);
}
