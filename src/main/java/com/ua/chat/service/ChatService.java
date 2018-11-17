package com.ua.chat.service;

import java.util.Collection;

import com.ua.chat.model.Message;

/**
 * TODO: Document what ChatService is.
 *
 * @author Praveen Polishetty 212602153
 * @version 1.0 Nov 13, 2018
 * @since 1.0
 */
public interface ChatService {

  public Message get(Long id);

  public Collection<Message> list(String userName);

  public Message create(Message message);

}
