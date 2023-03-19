package ru.tinkoff.edu.java.linkparser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public sealed abstract class LinkHandler permits GitHubLinkHandler, StackOverflowLinkHandler{
    public LinkHandler nextHandler;

    public abstract String parseLink(String link);
}
