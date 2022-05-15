package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    private String subject;
    private String body;

    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {
    }

    public void emailTo(User user) {
        subject = String.format("Notification %s to email %s",
                user.getUsername(), user.getEmail());
        body = String.format("Add a new event to %s", user.getUsername());
        System.out.println(subject + "  " + body);
        pool.submit(() ->
                send(subject, body, user.getEmail()));
    }

    public static void main(String[] args) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.emailTo(new User("Dima", "krutaxe@mail.ru"));
    }
}