package de.manuelk2000.browsermicroservice;

import de.manuelk2000.browsermicroservice.config.Config;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BrowserMicroservice {

    private final Javalin app;
    private static final Logger logger = LoggerFactory.getLogger(BrowserMicroservice.class);

    public BrowserMicroservice(){
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
            event.serverStopping(() -> { System.out.println("Stopping.."); });
            event.serverStopped(() -> { System.out.println("Stopped!"); });
        });

        // Register shutdown handler
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    public void start(String host, int port){
        logger.info("Starting application on {}:{} ...", host, port);
        app.start(host, port);
    }

    public void stop(){
        logger.info("Stopping application ...");
        app.stop();
    }

    private void configureRoutes(Javalin app) {
        app.get("/", ctx -> ctx.result("BrowserMicroservice"));
    }

    public static void main(String[] args) {
        new BrowserMicroservice();
    }
}