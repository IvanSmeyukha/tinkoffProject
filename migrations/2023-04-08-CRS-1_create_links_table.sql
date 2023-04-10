--liquibase formatted sql

--changeset i.smeyukha:CRS-1_create_links_table
CREATE TABLE IF NOT EXISTS links
(
    id BIGINT PRIMARY KEY,
    url TEXT NOT NULL
)