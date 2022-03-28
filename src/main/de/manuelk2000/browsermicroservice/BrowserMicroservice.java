package de.manuelk2000.browsermicroservice;

import io.javalin.Javalin;

public class BrowserMicroservice {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);
        app.get("/", ctx -> ctx.result("BrowserMicroservice"));
    }
}