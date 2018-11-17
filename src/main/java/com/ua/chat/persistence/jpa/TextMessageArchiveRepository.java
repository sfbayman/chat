
/**
 * TODO: Document what TextMessageRepository is.
 *
 * @author Praveen Polishetty 212602153
 * @version 1.0 Nov 15, 2018
 * @since 1.0
 */
package com.ua.chat.persistence.jpa;

import com.ua.chat.persistence.model.TextMessageArchiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextMessageArchiveRepository extends JpaRepository<TextMessageArchiveEntity, Long> {

}
