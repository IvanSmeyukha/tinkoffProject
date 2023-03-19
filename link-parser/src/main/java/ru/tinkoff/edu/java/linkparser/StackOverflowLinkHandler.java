package ru.tinkoff.edu.java.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StackOverflowLinkHandler extends LinkHandler{
    StackOverflowLinkHandler(LinkHandler linkHandler) {
        super(linkHandler);
    }

    @Override
    public String parseLink(String link) {
        Pattern pattern = Pattern.compile("^https://stackoverflow\\.com/questions/(?<id>\\d+)/?.*$");
        Matcher matcher = pattern.matcher(link);
        if (matcher.matches()) {
            return matcher.group("id");
        } else if (nextHandler != null){
            return nextHandler.parseLink(link);
        }
        return null;
    }
}
