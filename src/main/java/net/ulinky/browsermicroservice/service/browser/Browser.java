package net.ulinky.browsermicroservice.service.browser;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class Browser {

    private static final Logger logger = LoggerFactory.getLogger(Browser.class);
    private final BrowserDriver browserDriver;
    private boolean open;
    private WebDriver webDriver;
    private Dimension windowSize;

    public Browser(BrowserDriver browserDriver) {
        this.browserDriver = browserDriver;
        this.open = false;
        this.windowSize = new Dimension(1980, 1200);
        browserDriver.setup();
    }

    public void open() {
        if (open) {
            throw new IllegalStateException("Browser is already open!");
        }
        logger.info("Open browser");

        // Configure driver options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu",
            "--window-size=" + windowSize.getWidth() + "," + windowSize.getHeight(),
            "--ignore-certificate-errors", "--hide-scrollbars");
        //options.addArguments("user-agent=YOUR_USER_AGENT");

        // Start driver
        webDriver = new ChromeDriver(options);
        open = true;
    }

    public void close() {
        if (!open) {
            throw new IllegalStateException("Browser is already closed!");
        }
        logger.info("Close browser");

        // Stop driver
        webDriver.quit();
        webDriver = null;
        open = false;
    }

    public boolean isOpen() {
        return isOpen();
    }

    public void url(URL url) {
        logger.info("Browser opens URL: " + url.toString());
        webDriver.get(url.toString());
    }

    public Dimension getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(Dimension windowSize) {
        logger.debug("Change window size to " + windowSize.toString());
        this.windowSize = windowSize;
        if (open) {
            webDriver.manage().window().setSize(windowSize);
        }
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public BrowserDriver getBrowserDriver() {
        return browserDriver;
    }

}
