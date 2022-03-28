package de.manuelk2000.browsermicroservice;

import io.javalin.Javalin;

public class BrowserMicroservice {

    private final Javalin app;

    public BrowserMicroservice(){
        this.app = Javalin.create();
        start();

        // Configure routes
        configureRoutes(app);

        // Configure events
        app.events(event -> {
            event.serverStopping(() -> { System.out.println("Stopping.."); });
            event.serverStopped(() -> { System.out.println("Stopped!"); });
        });

        // Register shutdown handler
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    public void start(){
        app.start(7000);
    }

    public void stop(){
        app.stop();
    }

    private void configureRoutes(Javalin app) {
        app.get("/", ctx -> ctx.result("BrowserMicroservice"));
    }

    public static void main(String[] args) {
        new BrowserMicroservice();
    }
}