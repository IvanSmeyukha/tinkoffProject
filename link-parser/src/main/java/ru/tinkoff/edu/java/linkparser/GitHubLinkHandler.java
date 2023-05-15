package ru.tinkoff.edu.java.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GitHubLinkHandler implements LinkHandler {

    private final LinkHandler nextHandler;

    private final Pattern pattern = Pattern.compile("^https://github\\.com/(?<user>[^/]+)/(?<repo>[^/]+)/?.*$");

    public GitHubLinkHandler(LinkHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public LinkParserResponse parseLink(String link) {
        Matcher matcher = pattern.matcher(link);
        if (matcher.matches()) {
            String user = matcher.group("user");
            String repo = matcher.group("repo");
            return new GitHubLinkResponse(user, repo);
        } else if (nextHandler != null) {
            return nextHandler.parseLink(link);
        }
        return null;
    }
}
