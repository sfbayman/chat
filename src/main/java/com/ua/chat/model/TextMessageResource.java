/**
 *
 */
package com.ua.chat.model;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AllArgsConstructor;
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
public class TextMessageResource implements Message {

  @JsonIgnore
  private Long id;
  @JsonProperty("text")
  private String message;
  @JsonProperty("username")
  private String userName;
  @JsonIgnore
  private Integer timeout;
  @JsonProperty(access = Access.READ_ONLY, value = "expiration_date")
  private OffsetDateTime expirationDate;
}
