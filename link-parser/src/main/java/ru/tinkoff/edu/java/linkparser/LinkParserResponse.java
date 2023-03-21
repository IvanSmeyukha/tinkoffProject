package ru.tinkoff.edu.java.linkparser;

public sealed interface LinkParserResponse permits GitHubLinkResponse, StackOverflowLinkResponse{
}
