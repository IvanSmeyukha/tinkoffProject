package ru.tinkoff.edu.java.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GitHubLinkHandler extends LinkHandler{

    GitHubLinkHandler(LinkHandler linkHandler) {
        super(linkHandler);
    }

    @Override
    public String parseLink(String link){
        Pattern pattern = Pattern.compile("^https://github\\.com/(?<user>[^/]+)/(?<repo>[^/]+)/?.*$");
        Matcher matcher = pattern.matcher(link);
        if (matcher.matches()) {
            String user = matcher.group("user");
            String repo = matcher.group("repo");
            return String.format("%s/%s", user, repo);
        } else if (nextHandler != null){
            return nextHandler.parseLink(link);
        }
        return null;
    }
}
