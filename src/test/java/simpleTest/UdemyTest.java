package simpleTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UdemyTest {
    @Test
    public void searchInUdemy() throws InterruptedException {
        WebDriverManager.chromedriver().setup(); // this is a static method
//        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.udemy.com");

        WebElement searchField = driver.findElement(By.xpath("//*[@name='q']"));
        Thread.sleep(1000);
        searchField.sendKeys("Java");
        searchField.sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        WebElement result = driver.findElement(By.tagName("h1"));
        System.out.println(result.getText());
        Assert.assertTrue(result.getText().contains("10,000 results"));

        Thread.sleep(4000);
        driver.quit();
    }
}