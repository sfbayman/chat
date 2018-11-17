
/**
 * TODO: Document what TextMessageRepository is.
 *
 * @author Praveen Polishetty 212602153
 * @version 1.0 Nov 15, 2018
 * @since 1.0
 */
package com.ua.chat.persistence.jpa;

import java.util.List;

import com.ua.chat.persistence.model.TextMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TextMessageRepository extends JpaRepository<TextMessageEntity, Long> {

  @Query("SELECT e FROM TextMessageEntity e "
      + "WHERE e.userName = :userName and e.expirationDate = null")
  List<TextMessageEntity> findByUserName(@Param("userName") String userName);
}
