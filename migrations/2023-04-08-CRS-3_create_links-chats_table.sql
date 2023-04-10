--liquibase formatted sql

--changeset i.smeyukha:CRS-3_create_links-chats_table
CREATE TABLE IF NOT EXISTS links_chats
(
    link_id BIGINT,
    chat_id BIGINT,
    PRIMARY KEY (link_id, chat_id),
    FOREIGN KEY (link_id) REFERENCES links(id),
    FOREIGN KEY (chat_id) REFERENCES chats(id)
)