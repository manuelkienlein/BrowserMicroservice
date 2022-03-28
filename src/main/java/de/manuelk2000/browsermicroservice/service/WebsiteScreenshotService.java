package de.manuelk2000.browsermicroservice.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class WebsiteScreenshotService {

    public WebsiteScreenshotService(){
        WebDriverManager.chromedriver().setup();
        //WebDriver driver = new ChromeDriver();
        //driver.get("http://www.bing.com/");
        //File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        // Now you can do whatever you need to do with it, for example copy somewhere
        /*try {
            FileUtils.copyFile(scrFile, new File("screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //driver.close();
    }

    public void screenshot(String url, File file) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://"+url);
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    public void eightComponents() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://wikipedia.org");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement searchBox = driver.findElement(By.id("searchInput"));
        WebElement searchButton = driver.findElement(By.className("pure-button-primary-progress"));

        searchBox.sendKeys("Selenium");
        searchButton.click();

        searchBox = driver.findElement(By.id("searchInput"));

        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        // Now you can do whatever you need to do with it, for example copy somewhere
        try {
            FileUtils.copyFile(scrFile, new File("screenshot_google.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver.quit();
    }

}
