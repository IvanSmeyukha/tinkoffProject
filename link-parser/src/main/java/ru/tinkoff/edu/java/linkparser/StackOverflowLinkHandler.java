package ru.tinkoff.edu.java.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StackOverflowLinkHandler extends AbstractLinkHandler {

    private final Pattern pattern;
    {
        pattern = Pattern.compile("^https://stackoverflow\\.com/questions/(?<id>\\d+)/?.*$");
    }
    public StackOverflowLinkHandler(AbstractLinkHandler linkHandler) {
        super(linkHandler);
    }

    @Override
    public LinkParserResponse parseLink(String link) {
        Matcher matcher = pattern.matcher(link);
        if (matcher.matches()) {
            return new StackOverflowLinkResponse(matcher.group("id"));
        } else if (super.getNextHandler() != null){
            return super.getNextHandler().parseLink(link);
        }
        return null;
    }
}
