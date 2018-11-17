/**
 *
 */
package com.ua.chat.model;

/**
 * @author Praveen Polishetty
 * @date 11/12/2018
 */
public interface Message {

  Long getId();

  String getMessage();

  String getUserName();

  Integer getTimeout();
}
