package ru.tinkoff.edu.java.linkparser;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public sealed abstract class AbstractLinkHandler implements LinkHandler permits GitHubLinkHandler, StackOverflowLinkHandler{
    private AbstractLinkHandler nextHandler;
}
