package de.manuelk2000.browsermicroservice;

import de.manuelk2000.browsermicroservice.config.Config;
import de.manuelk2000.browsermicroservice.config.ConfigLoader;
import de.manuelk2000.browsermicroservice.controller.ScreenshotController;
import de.manuelk2000.browsermicroservice.service.screenshot.WebsiteScreenshotService;
import io.javalin.Javalin;
import io.javalin.core.validation.JavalinValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class BrowserMicroservice {

    private static final Logger logger = LoggerFactory.getLogger(BrowserMicroservice.class);
    private final Javalin app;
    private Config config;

    public BrowserMicroservice() {
        this.app = Javalin.create();

        // Load configuration
        try {
            ConfigLoader configLoader = ConfigLoader.getInstance();
            config = configLoader.getConfig();
        } catch (IllegalStateException e) {
            logger.error("Failed to load configuration file!", e);
            e.printStackTrace();
            return;
        }

        // Start webserver
        start(config.getHost(), config.getPort());

        // Configure routes
        configureRoutes(app);

        // Configure validators
        configureValidators();

        // Configure exceptions
        configureExceptions();

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

        // Init services
        WebsiteScreenshotService websiteScreenshotService = new WebsiteScreenshotService();

        // Init controllers
        ScreenshotController screenshotController = new ScreenshotController(websiteScreenshotService, config);

        // Define routes
        app.get("/", ctx -> ctx.result("BrowserMicroservice"));

        app.routes(() -> {
            get("/status", ctx -> {
                Map<String, Object> status = new HashMap<>();
                status.put("timestamp", System.currentTimeMillis());
                status.put("config", config);
                ctx.json(status);
            });
            path("/v{version}", () -> {
                get("/screenshot/{url}", screenshotController::screenshot);
            });
        });

    }

    private void configureValidators() {
        JavalinValidation.register(URL.class, string -> {
            try {
                return new URL(string);
            } catch (MalformedURLException e) {
                return null;
            }
        });
    }

    private void configureExceptions() {

    }
}