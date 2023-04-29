package ru.tinkoff.edu.java.scrapper.dto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "links")
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "url")
    private String url;
    @Column(name = "last_check_time")
    private OffsetDateTime lastCheckTime;
    @Column(name = "last_update_time")
    private OffsetDateTime lastUpdateTime;
    @ManyToMany
    @JoinTable(
            name = "links_chats",
            joinColumns = @JoinColumn(name = "link_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private Set<Chat> chats = new HashSet<>();
}
