package ru.tinkoff.edu.java.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GitHubLinkHandler extends AbstractLinkHandler {

    private final Pattern pattern;
    {
        pattern = Pattern.compile("^https://github\\.com/(?<user>[^/]+)/(?<repo>[^/]+)/?.*$");
    }
    public GitHubLinkHandler(AbstractLinkHandler linkHandler) {
        super(linkHandler);
    }

    @Override
    public LinkParserResponse parseLink(String link){
        Matcher matcher = pattern.matcher(link);
        if (matcher.matches()) {
            String user = matcher.group("user");
            String repo = matcher.group("repo");
            return new GitHubLinkResponse(user, repo);
        } else if (super.getNextHandler() != null){
            return super.getNextHandler().parseLink(link);
        }
        return null;
    }
}
