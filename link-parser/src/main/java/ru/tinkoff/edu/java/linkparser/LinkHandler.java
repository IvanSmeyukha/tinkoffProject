package ru.tinkoff.edu.java.linkparser;

public sealed interface LinkHandler permits GitHubLinkHandler, StackOverflowLinkHandler{
    LinkParserResponse parseLink(String link);
}
