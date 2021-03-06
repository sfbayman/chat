/**
 *
 */
package com.ua.chat.persistence.model;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ua.chat.model.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Praveen Polishetty
 * @date 11/12/2018
 */
@Entity
@Table(name = "text_message_archive")
@Getter
@Setter
@NoArgsConstructor
public class TextMessageArchiveEntity implements Message {

  @Id
  private Long id;
  @Column(nullable = false)
  private String message;
  @Column(nullable = false)
  private String userName;
  @Column
  private Integer timeout;
  @Column
  private OffsetDateTime expirationDate;
}
