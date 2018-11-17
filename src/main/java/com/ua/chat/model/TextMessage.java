/**
 *
 */
package com.ua.chat.model;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Praveen Polishetty
 * @date 11/12/2018
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TextMessage implements Message {

  @JsonIgnore
  private Long id;
  @JsonProperty("text")
  private String message;
  @JsonProperty("username")
  private String userName;
  private Integer timeout;
  @JsonIgnore
  private OffsetDateTime expirationDate;
}
