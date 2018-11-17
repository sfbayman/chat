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
public class ActiveTextMessageResource implements Message {

  @JsonProperty(access = Access.READ_ONLY)
  private Long id;
  @JsonProperty("text")
  private String message;
  @JsonIgnore
  private String userName;
  @JsonIgnore
  private Integer timeout;
  @JsonIgnore
  private OffsetDateTime expirationDate;
}
