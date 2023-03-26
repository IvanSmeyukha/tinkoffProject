package ru.tinkoff.edu.java.linkparser;

public record GitHubLinkResponse(String user, String repository) implements LinkParserResponse {
}
