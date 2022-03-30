package de.manuelk2000.browsermicroservice.controller;

import de.manuelk2000.browsermicroservice.config.Config;
import de.manuelk2000.browsermicroservice.service.screenshot.WebsiteScreenshotService;
import io.javalin.http.Context;

import java.net.MalformedURLException;
import java.net.URL;

public class ScreenshotController {

    private final WebsiteScreenshotService websiteScreenshotService;
    private final Config config;

    public ScreenshotController(WebsiteScreenshotService websiteScreenshotService, Config config) {
        this.websiteScreenshotService = websiteScreenshotService;
        this.config = config;
    }

    public void screenshot(Context context) throws MalformedURLException {

        // Screenshot dimension parameters
        int width = context.queryParamAsClass("width", Integer.class)
                .check(w -> w > 0 && w <= 10000, "Width must be between 1 and 10000 pixels!")
                .getOrDefault(config.getScreenshots().getDefaultWidth());
        int height = context.queryParamAsClass("height", Integer.class)
                .check(h -> h > 0 && h <= 10000, "Height must be between 1 and 10000 pixels!")
                .getOrDefault(config.getScreenshots().getDefaultHeight());
        this.websiteScreenshotService.setScreenSize(width, height);

        // Validate url parameter
        context.pathParamAsClass("url", String.class)
                .check(urlParam -> {
                    try {
                        new URL(urlParam);
                        return true;
                    } catch (MalformedURLException e) {
                        return false;
                    }
                }, "URL is not valid").get();

        // Get url from parameter
        URL url = new URL(context.pathParam("url"));

        // Return response
        context.result(this.websiteScreenshotService.takeScreenshot(url));

        // Store screenshot as file

//        File file = new File("screenshot_" + System.currentTimeMillis() + ".png");
//        try {
//            this.websiteScreenshotService.takeScreenshot(url, file);
//            context.result(new FileInputStream(file));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
