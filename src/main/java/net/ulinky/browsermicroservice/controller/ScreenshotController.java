package net.ulinky.browsermicroservice.controller;

import io.javalin.http.Context;

import java.net.MalformedURLException;
import java.net.URL;

import static net.ulinky.browsermicroservice.BrowserMicroservice.config;
import static net.ulinky.browsermicroservice.BrowserMicroservice.websiteScreenshotService;

public class ScreenshotController {

    public static void screenshot(Context context) throws MalformedURLException {

        // Screenshot dimension parameters
        int width = context.queryParamAsClass("width", Integer.class)
            .check(w -> w > 0 && w <= 10000, "Width must be between 1 and 10000 pixels!")
            .getOrDefault(config.getScreenshots().getDefaultWidth());
        int height = context.queryParamAsClass("height", Integer.class)
            .check(h -> h > 0 && h <= 10000, "Height must be between 1 and 10000 pixels!")
            .getOrDefault(config.getScreenshots().getDefaultHeight());
        websiteScreenshotService.setScreenSize(width, height);

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
        context.result(websiteScreenshotService.takeScreenshot(url));

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
