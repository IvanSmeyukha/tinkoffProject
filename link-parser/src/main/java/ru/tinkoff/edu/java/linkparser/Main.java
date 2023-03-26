package ru.tinkoff.edu.java.linkparser;

public class Main {
    public static void main(String[] args) {
        System.out.println(LinkParser.parseLink("https://github.com/sanyarnd/tinkoff-java-course-2022/"));
        System.out.println(LinkParser.parseLink("https://github.com/sanyarnd/tinkoff-java-course-2022/"));
        System.out.println(LinkParser.parseLink("https://github.com/sanyarnd/tinkoff-java-course-2022/"));
        System.out.println(LinkParser.parseLink("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"));
        System.out.println(LinkParser.parseLink("https://stackoverflow.com/search?q=unsupported%20link"));
        System.out.println(LinkParser.parseLink("https://stackoverflow.com/questions/16420289999/what-is-the-operator-in-c"));
    }
}
