package ru.tinkoff.edu.java.scrapper.dto.entity;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

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
