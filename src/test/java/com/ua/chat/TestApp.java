package com.ua.chat;

import com.ua.chat.service.ChatService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

@ComponentScan(basePackages = {
    "com.ua.chat"
})
public class TestApp {

  @Bean
  @Primary
  public ChatService chatService() {
    return Mockito.mock(ChatService.class);
  }

}
