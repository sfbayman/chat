/**
 *
 */
package com.ua.chat.model;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class TextMessageResponse implements Message {

  public TextMessageResponse(Long id) {
    this.id = id;
  }

  private Long id;
  @JsonIgnore
  private String message;
  @JsonIgnore
  private String userName;
  @JsonIgnore
  private Integer timeout;
  @JsonIgnore
  private OffsetDateTime expirationDate;
}
