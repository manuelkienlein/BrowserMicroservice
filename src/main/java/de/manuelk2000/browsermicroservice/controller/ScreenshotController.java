package de.manuelk2000.browsermicroservice.controller;

import de.manuelk2000.browsermicroservice.service.WebsiteScreenshotService;
import io.javalin.http.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ScreenshotController {

    private final WebsiteScreenshotService websiteScreenshotService;

    public ScreenshotController(WebsiteScreenshotService websiteScreenshotService) {
        this.websiteScreenshotService = websiteScreenshotService;
    }

    public void screenshot(Context context) {
        long start = System.currentTimeMillis();
        String url = context.pathParam("url");
        File file = new File("screenshot_" + System.currentTimeMillis() + ".png");
        try {
            this.websiteScreenshotService.takeScreenshot(url, file);
            context.result(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        long stop = System.currentTimeMillis();
        System.out.println("Zeit " + (stop - start) + " ms for " + url);
    }
}
