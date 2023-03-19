package ru.tinkoff.edu.java.linkparser;

public class LinkParser {

    public static String parseLink(String link){
        LinkHandler gitHubLinkHandler = new GitHubLinkHandler(null);
        LinkHandler stackOverflowLinkHandler = new StackOverflowLinkHandler(gitHubLinkHandler);
        return stackOverflowLinkHandler.parseLink(link);
    }
}
