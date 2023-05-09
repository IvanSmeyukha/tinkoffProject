package ru.tinkoff.edu.java.scrapper.dto.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToMany(mappedBy = "chats")
    private Set<Link> links = new HashSet<>();

    public Chat(Long id) {
        this.id = id;
    }

}
