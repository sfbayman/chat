/**
 *
 */
package com.ua.chat.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.ua.chat.model.ActiveTextMessageResource;
import com.ua.chat.model.Message;
import com.ua.chat.model.TextMessage;
import com.ua.chat.model.TextMessageResource;
import com.ua.chat.model.TextMessageResponse;
import com.ua.chat.service.ChatService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Praveen Polishetty
 * @date 11/12/2018
 */
@RestController
public class ChatController {

  private final ChatService chatService;

  @Autowired
  public ChatController(ChatService chatService) {
    this.chatService = chatService;
  }

  @ApiOperation(value = "Get chat")
  @ApiResponses({@ApiResponse(code = 200, message = "OK", response = TextMessageResource.class)})
  @GetMapping(path = "/chat/{id}")
  @ResponseBody
  public ResponseEntity<TextMessageResource> get(@PathVariable Long id) {
    Message message = chatService.get(id);
    if (message == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.status(HttpStatus.OK).body(toResource(message));
  }

  @ApiOperation(value = "create message")
  @ApiResponses({@ApiResponse(code = 201, message = "Created", response = TextMessageResource.class)})
  @PostMapping(path = "/chat")
  @ResponseBody
  public ResponseEntity<TextMessageResponse> create(@RequestBody TextMessage inputMessage) {
    TextMessageResource resource = new TextMessageResource();
    BeanUtils.copyProperties(inputMessage, resource);
    Message message = chatService.create(resource);
    if (message == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(
        (new TextMessageResponse(message.getId())));
  }

  @ApiOperation(value = "List messages")
  @ApiResponses({@ApiResponse(code = 200, message = "OK", response = Collection.class)})
  @GetMapping(path = "/chats/{userName}")
  @ResponseBody
  public ResponseEntity<Collection<ActiveTextMessageResource>> list(@PathVariable String userName) {
    return ResponseEntity.status(HttpStatus.OK).body(toResources(chatService.list(userName)));
  }

  private TextMessageResource toResource(Message message) {
    TextMessageResource textMessageResource = new TextMessageResource();
    BeanUtils.copyProperties(message, textMessageResource);
    return textMessageResource;
  }

  private ActiveTextMessageResource toActiveMessageResource(Message message) {
    ActiveTextMessageResource textMessageResource = new ActiveTextMessageResource();
    BeanUtils.copyProperties(message, textMessageResource);
    return textMessageResource;
  }

  private List<ActiveTextMessageResource> toResources(Collection<Message> messages) {
    return messages.stream()
        .map(message -> toActiveMessageResource(message)).collect(Collectors.toList());
  }
}
