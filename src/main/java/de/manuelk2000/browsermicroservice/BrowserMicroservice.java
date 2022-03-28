package de.manuelk2000.browsermicroservice;

import de.manuelk2000.browsermicroservice.config.Config;
import de.manuelk2000.browsermicroservice.service.WebsiteScreenshotService;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    public WebsiteScreenshotService wss;

    public void start(String host, int port) {
        logger.info("Starting application on {}:{} ...", host, port);
        app.start(host, port);

        wss = new WebsiteScreenshotService();
        //wss.eightComponents();
    }

    public void stop() {
        logger.info("Stopping application ...");
        app.stop();
    }

    private void configureRoutes(Javalin app) {
        app.get("/", ctx -> ctx.result("BrowserMicroservice"));

        app.get("/screenshot/{url}", ctx -> {
            long start = System.currentTimeMillis();
            String url = ctx.pathParam("url");
            File file = new File("screenshot_"+url+"_"+System.currentTimeMillis()+".png");
            wss.screenshot(url, file);
            ctx.result(new FileInputStream(file));
            long stop = System.currentTimeMillis();
            logger.info("Zeit "+(stop-start)+" ms for "+url);
            //ctx.result("URL: "+url);
        });

        app.get("/extreme", ctx -> {
            String[] urls = {"www.ulinky.de", "www.ulinky.net", "www.google.com", "www.yahoo.com", "www.wikipedia.org"};
            for(String s : urls){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long start = System.currentTimeMillis();
                        String url = s;
                        File file = new File("screenshot_"+url+"_"+System.currentTimeMillis()+".png");
                        wss.screenshot(url, file);
                        try {
                            ctx.result(new FileInputStream(file));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        //ctx.result("URL: "+url);
                        long stop = System.currentTimeMillis();
                        logger.info("Zeit "+(stop-start)+" ms for "+url);
                    }
                }).start();
            }
        });

        app.routes(() -> {
            get("/hello", ctx -> ctx.result("Hello World"));
            path("/api", () -> {
                get("/test", ctx -> ctx.result("Hello API"));
            });
        });
    }
}