/**
 *
 */
package com.ua.chat.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import javax.persistence.PersistenceException;

import com.ua.chat.TestApp;
import com.ua.chat.model.TextMessage;
import com.ua.chat.service.ChatService;
import org.hamcrest.Matchers;
import org.hibernate.ObjectNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


/**
 * @author Praveen Polishetty
 * @date 11/12/2018
 */
@SpringBootTest(classes = TestApp.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ChatControllerTest {

  @Autowired
  private ChatService chatService;
  @Autowired
  private MockMvc mockMvc;

  @After
  public void cleanMocks() {
    Mockito.reset(chatService);
  }

  @Test
  public void get_success() throws Exception {
    TextMessage message = TextMessage.builder()
        .id(1L).userName("test_usr")
        .message("this test msg")
        .expirationDate(OffsetDateTime.now(ZoneOffset.UTC))
        .timeout(30).build();
    Mockito.when(chatService.get(any()))
        .thenReturn(message);
    mockMvc.perform(get("/chat/1").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", Matchers.is(message.getUserName())))
        .andExpect(jsonPath("$.text", Matchers.is(message.getMessage()))).andReturn();
  }

  @Test
  public void get_extra_field_fail() throws Exception {
    TextMessage message = TextMessage.builder()
        .id(1L).userName("test_usr")
        .message("this test msg")
        .expirationDate(OffsetDateTime.now(ZoneOffset.UTC))
        .timeout(30).build();
    Mockito.when(chatService.get(any()))
        .thenReturn(message);
    MvcResult result = mockMvc.perform(get("/chat/1").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk()).andReturn();
    Assert.assertFalse(result.getResponse().getContentAsString().contains("\"id\""));
    Assert.assertFalse(result.getResponse().getContentAsString().contains("\"timeout\""));
  }


  @Test
  public void post_success() throws Exception {
    TextMessage message = TextMessage.builder()
        .id(1L).userName("test_usr")
        .message("this test msg")
        .timeout(30).build();
    Mockito.when(chatService.create(any()))
        .thenReturn(message);
    String requestBody = "{ \"username\": \"paulrad\", \"text\": \"This is a messagwe2ww3\", \"timeout\": 30 }";
    MvcResult result = mockMvc.perform(
        post("/chat").content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isCreated()).andReturn();
    Assert.assertTrue(result.getResponse().getContentAsString().contains("\"id\""));
    Assert.assertFalse(result.getResponse().getContentAsString().contains("\"text\""));
  }

  @Test
  public void post_fails() throws Exception {
    TextMessage message = TextMessage.builder()
        .id(1L).userName("test_usr")
        .message("this test msg")
        .timeout(30).build();
    Mockito.when(chatService.create(any()))
        .thenThrow(PersistenceException.class);
    String requestBody = "{ \"username\": \"paulrad\", \"text\": \"This is a messagwe2ww3\", \"timeout\": 30 }";
    mockMvc.perform(
        post("/chat").content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void post_setting_id_fail() throws Exception {
    String requestBody = "{ \"id\": 1L,\"username\": \"paulrad\", \"text\": \"This is a messagwe2ww3\", \"timeout\": 30 }";
    mockMvc.perform(
        post("/chat").content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void post_setting_expiry_date_fail() throws Exception {
    String requestBody = "{ \"expiry_date\": " + OffsetDateTime.now(ZoneOffset.UTC)
        + ",\"username\": \"paulrad\", \"text\": \"This is a messagwe2ww3\", \"timeout\": 30 }";
    mockMvc.perform(
        post("/chat").content(requestBody)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void list_success() throws Exception {
    TextMessage message = TextMessage.builder()
        .id(Long.valueOf(1)).userName("test_usr")
        .message("this test msg")
        .timeout(30).build();
    Mockito.when(chatService.list(any()))
        .thenReturn(Collections.singletonList(message));
    mockMvc.perform(get("/chats/test_usr").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", Matchers.notNullValue()))
        .andExpect(jsonPath("$[0].id", Matchers.is(message.getId().intValue())))
        .andExpect(jsonPath("$[0].text", Matchers.is(message.getMessage())));
  }

  @Test
  public void list_extra_field_fail() throws Exception {
    TextMessage message = TextMessage.builder()
        .id(Long.valueOf(1)).userName("test_usr")
        .message("this test msg")
        .timeout(30).build();
    Mockito.when(chatService.list(any()))
        .thenReturn(Collections.singletonList(message));
    MvcResult result = mockMvc.perform(get("/chats/test_usr").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk()).andReturn();
    Assert.assertFalse(result.getResponse().getContentAsString().contains("\"expiration_date\""));
    Assert.assertFalse(result.getResponse().getContentAsString().contains("\"timeout\""));
    Assert.assertFalse(result.getResponse().getContentAsString().contains("\"username\""));
  }

  @Test
  public void get_not_found_fail() throws Exception {
    Mockito.when(chatService.get(any()))
        .thenThrow(ObjectNotFoundException.class);
    mockMvc.perform(get("/chat/1").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }
}
