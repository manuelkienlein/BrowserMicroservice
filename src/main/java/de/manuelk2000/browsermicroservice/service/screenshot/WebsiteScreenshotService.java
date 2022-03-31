package de.manuelk2000.browsermicroservice.service.screenshot;

import de.manuelk2000.browsermicroservice.service.browser.Browser;
import de.manuelk2000.browsermicroservice.service.browser.BrowserDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class WebsiteScreenshotService {

    private static final Logger logger = LoggerFactory.getLogger(WebsiteScreenshotService.class);
    private final Browser browser;

    public WebsiteScreenshotService() {
        browser = new Browser(BrowserDriver.CHROME);
    }

    public void setScreenSize(int width, int height) {
        browser.setWindowSize(new Dimension(width, height));
    }

    public Dimension getScreenSize() {
        return browser.getWindowSize();
    }

    public void takeScreenshot(URL url, File file) throws IOException {
        // Open browser
        browser.open();

        // Open website url
        browser.url(url);

        // Take screenshot of website
        logger.debug("Take screenshot of URL: " + url);
        File scrFile = ((TakesScreenshot) browser.getWebDriver()).getScreenshotAs(OutputType.FILE);

        // Store screenshot to file
        FileUtils.copyFile(scrFile, file);

        // Close browser
        browser.close();
    }

    public byte[] takeScreenshot(URL url) {
        // Open browser
        browser.open();

        // Open website url
        browser.url(url);

        // Take screenshot of website
        logger.debug("Take screenshot of URL: " + url);
        byte[] imageBytes = ((TakesScreenshot) browser.getWebDriver()).getScreenshotAs(OutputType.BYTES);

        // Close browser
        browser.close();

        // Return screenshot image bytes
        return imageBytes;
    }

}
