package ru.tinkoff.edu.java.bot.enums;

public enum CommandInfo {
    START("/start", "зарегистрировать пользователя"),
    HELP("/help", "вывести окно с командами"),
    TRACK("/track", "начать отслеживание ссылки"),
    UNTRACK("/untrack", "прекратить отслеживание ссылки"),
    LIST("/list", "показать список отслеживаемых ссылок");
    private final String command;
    private final String description;

    CommandInfo(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }
}
