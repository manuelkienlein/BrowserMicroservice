package de.manuelk2000.browsermicroservice.config;

import de.manuelk2000.browsermicroservice.service.browser.BrowserDriver;

public class ScreenshotConfig {

    private int defaultWidth = 1920;
    private int defaultHeight = 1200;
    private BrowserDriver defaultBrowserEngine = BrowserDriver.CHROME;

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

    public BrowserDriver getDefaultBrowserEngine() {
        return defaultBrowserEngine;
    }

    public void setDefaultBrowserEngine(BrowserDriver defaultBrowserEngine) {
        this.defaultBrowserEngine = defaultBrowserEngine;
    }
}
