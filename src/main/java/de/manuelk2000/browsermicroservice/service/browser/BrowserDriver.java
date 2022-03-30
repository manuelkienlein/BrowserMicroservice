package de.manuelk2000.browsermicroservice.service.browser;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum BrowserDriver {

    CHROME, FIREFOX, EDGE;

    private static final Logger logger = LoggerFactory.getLogger(BrowserDriver.class);

    public void setup() {
        logger.info("Setup " + this + " browser driver ...");
        switch (this) {
            case CHROME -> WebDriverManager.chromedriver().setup();
            case FIREFOX -> WebDriverManager.firefoxdriver().setup();
            case EDGE -> WebDriverManager.edgedriver().setup();
        }
    }
}
