/**
 *
 */
package com.ua.chat.persistence;

import java.util.Collection;
import javax.transaction.Transactional;

import com.ua.chat.model.Message;
import com.ua.chat.model.TextMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author Praveen Polishetty
 * @date 11/12/2018
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MessagePersistenceServiceTest {

  @Autowired
  private MessagePersistenceService messagePersistenceService;

  @Transactional
  @Test
  public void post_success() {
    TextMessage message = TextMessage.builder()
        .userName("test_usr")
        .message("this test msg")
        .timeout(30).build();
    Message createdMessage = messagePersistenceService.save(message);
    Assert.assertNotNull(createdMessage.getId());
    Assert.assertEquals(message.getMessage(), createdMessage.getMessage());
  }

  @Transactional
  @Test
  public void get_success() {
    TextMessage message = TextMessage.builder()
        .userName("test_usr")
        .message("this test msg")
        .timeout(30).build();
    Message createdMessage = messagePersistenceService.save(message);
    Message dbCopy = messagePersistenceService.get(createdMessage.getId());
    Assert.assertNotNull(dbCopy.getId());
    Assert.assertEquals(message.getMessage(), dbCopy.getMessage());
  }


  @Test
  public void get_not_found_fail() {
    Message dbCopy = messagePersistenceService.get(Long.valueOf(20));
    Assert.assertNull(dbCopy);
  }

  @Transactional
  @Test
  public void list_success() {
    TextMessage message = TextMessage.builder()
        .userName("test_usr")
        .message("this test msg")
        .timeout(30).build();
    Message createdMessage = messagePersistenceService.save(message);
    Collection<Message> messagesList = messagePersistenceService.list(createdMessage.getUserName());
    Assert.assertNotNull(messagesList);
    Assert.assertEquals(1, messagesList.size());
  }

  @Transactional
  @Test
  public void list_twice_success() {
    TextMessage message = TextMessage.builder()
        .userName("test_usr")
        .message("this test msg")
        .timeout(30).build();
    Message createdMessage = messagePersistenceService.save(message);
    Collection<Message> messagesList = messagePersistenceService.list(createdMessage.getUserName());
    Assert.assertNotNull(messagesList);
    Assert.assertEquals(1, messagesList.size());

    messagesList = messagePersistenceService.list(createdMessage.getUserName());
    Assert.assertNotNull(messagesList);
    Assert.assertEquals(0, messagesList.size());
  }

}
