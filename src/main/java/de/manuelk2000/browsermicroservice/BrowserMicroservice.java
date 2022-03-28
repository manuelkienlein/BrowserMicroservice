package de.manuelk2000.browsermicroservice;

import de.manuelk2000.browsermicroservice.config.Config;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.get;

public class BrowserMicroservice {

    private static final Logger logger = LoggerFactory.getLogger(BrowserMicroservice.class);
    private final Javalin app;

    public BrowserMicroservice() {
        this.app = Javalin.create();

        // Load configuration
        Config config;
        try {
            config = new Config();
        } catch (IOException e) {
            logger.error("Failed to load configuration file!", e);
            e.printStackTrace();
            return;
        }

        // Start webserver
        start(config.getStringProperty("host"), config.getIntProperty("port"));

        // Configure routes
        configureRoutes(app);

        // Configure events
        app.events(event -> {
            event.serverStopping(() -> {
                System.out.println("Stopping..");
            });
            event.serverStopped(() -> {
                System.out.println("Stopped!");
            });
        });

        // Register shutdown handler
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    public static void main(String[] args) {
        new BrowserMicroservice();
    }

    public void start(String host, int port) {
        logger.info("Starting application on {}:{} ...", host, port);
        app.start(host, port);
    }

    public void stop() {
        logger.info("Stopping application ...");
        app.stop();
    }

    private void configureRoutes(Javalin app) {
        app.get("/", ctx -> ctx.result("BrowserMicroservice"));
        app.routes(() -> {
            get("/hello", ctx -> ctx.result("Hello World"));
            path("/api", () -> {
                get("/test", ctx -> ctx.result("Hello API"));
            });
        });
    }
}