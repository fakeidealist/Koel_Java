package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
//    protected FluentWait<WebDriver> fluentWait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10, 200);
//        fluentWait = new FluentWait<WebDriver>(driver)
//                .pollingEvery(Duration.ofMillis(200))
//                .withTimeout(Duration.ofSeconds(10))
//                .ignoring(NoSuchElementException.class);
    }
}