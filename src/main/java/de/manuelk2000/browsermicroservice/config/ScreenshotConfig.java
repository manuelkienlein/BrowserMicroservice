package de.manuelk2000.browsermicroservice.config;

import de.manuelk2000.browsermicroservice.service.screenshot.BrowserDrivers;

public class ScreenshotConfig {

    private int defaultWidth = 1920;
    private int defaultHeight = 1200;
    private BrowserDrivers defaultBrowserEngine = BrowserDrivers.CHROME;

    public int getDefaultWidth() {
        return defaultWidth;
    }

    public void setDefaultWidth(int defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    public int getDefaultHeight() {
        return defaultHeight;
    }

    public void setDefaultHeight(int defaultHeight) {
        this.defaultHeight = defaultHeight;
    }

    public BrowserDrivers getDefaultBrowserEngine() {
        return defaultBrowserEngine;
    }

    public void setDefaultBrowserEngine(BrowserDrivers defaultBrowserEngine) {
        this.defaultBrowserEngine = defaultBrowserEngine;
    }
}
