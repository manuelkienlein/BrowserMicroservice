package de.manuelk2000.browsermicroservice.service.screenshot;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class WebsiteScreenshotService {

    private static final Logger logger = LoggerFactory.getLogger(WebsiteScreenshotService.class);
    private WebDriver webDriver;

    public WebsiteScreenshotService() {
        setupDriver();
    }

    private void setupDriver() {
        // Install browser driver
        logger.info("Setup webdriver.");
        WebDriverManager.chromedriver().setup();
    }

    private void startDriver() {
        // Configure driver options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors", "--hide-scrollbars");

        // Start driver
        webDriver = new ChromeDriver(options);
    }

    private void stopDriver() {
        // Stop driver
        webDriver.close();
    }

    public void setScreenSize(int width, int height) {
        webDriver.manage().window().setSize(new Dimension(width, height));
    }

    public void takeScreenshot(String url, File file) throws IOException {
        // Start driver
        startDriver();

        // Open website url
        webDriver.get(url);

        // Take screenshot of website
        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

        // Store screenshot to file
        FileUtils.copyFile(scrFile, file);

        // Stop driver
        stopDriver();
    }

    public byte[] takeScreenshot(String url) {
        // Start driver
        startDriver();

        // Open website url
        webDriver.get(url);

        // Take screenshot of website
        byte[] imageBytes = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);

        // Stop driver
        stopDriver();

        // Return screenshot image bytes
        return imageBytes;
    }

}
