--liquibase formatted sql

--changeset i.smeyukha:CRS-2_create_chats_table
CREATE TABLE IF NOT EXISTS chats
(
    id BIGINT PRIMARY KEY
)