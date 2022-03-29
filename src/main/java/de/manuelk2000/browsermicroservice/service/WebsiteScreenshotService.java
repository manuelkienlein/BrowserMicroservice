package de.manuelk2000.browsermicroservice.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;

public class WebsiteScreenshotService {

    public WebsiteScreenshotService() {
        WebDriverManager.chromedriver().setup();
    }

    public void takeScreenshot(String url, File file) throws IOException {
        // Prepare browser
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors");
        WebDriver driver = new ChromeDriver(options);

        // Fetch website and store screenshot to file
        driver.get(url);
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, file);

        // Close browser
        driver.quit();
    }

}
