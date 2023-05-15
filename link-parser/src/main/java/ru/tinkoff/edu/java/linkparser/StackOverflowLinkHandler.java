package ru.tinkoff.edu.java.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StackOverflowLinkHandler implements LinkHandler {

    private final LinkHandler nextHandler;
    private final Pattern pattern = Pattern.compile("^https://stackoverflow\\.com/questions/(?<id>\\d+)/?.*$");

    public StackOverflowLinkHandler(LinkHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public LinkParserResponse parseLink(String link) {
        Matcher matcher = pattern.matcher(link);
        if (matcher.matches()) {
            return new StackOverflowLinkResponse(Long.parseLong(matcher.group("id")));
        } else if (nextHandler != null) {
            return nextHandler.parseLink(link);
        }
        return null;
    }
}
