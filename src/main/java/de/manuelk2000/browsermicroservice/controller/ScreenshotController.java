package de.manuelk2000.browsermicroservice.controller;

import de.manuelk2000.browsermicroservice.service.screenshot.WebsiteScreenshotService;
import io.javalin.http.Context;

public class ScreenshotController {

    private final WebsiteScreenshotService websiteScreenshotService;

    public ScreenshotController(WebsiteScreenshotService websiteScreenshotService) {
        this.websiteScreenshotService = websiteScreenshotService;
    }

    public void screenshot(Context context) {
        String url = context.pathParam("url");

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
