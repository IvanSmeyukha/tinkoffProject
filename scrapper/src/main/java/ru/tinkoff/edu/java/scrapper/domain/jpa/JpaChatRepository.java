package ru.tinkoff.edu.java.scrapper.domain.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;


public interface JpaChatRepository extends JpaRepository<Chat, Long> {

}
