package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends MainPage {
    private ProfilePage(WebDriver driver){
        super(driver);
    }

    private WebElement getNameField(){
        By nameBy = By.xpath("//*[@id='inputProfileName']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameBy));
        return driver.findElement(nameBy);
    }

    private WebElement getEmailField(){
        return driver.findElement(By.xpath("//*[@id='inputProfileEmail']"));
    }

    private WebElement getPasswordField(){
        return driver.findElement(By.xpath("//*[@id='inputProfilePassword']"));
    }

    private WebElement getConfirmPasswordField(){
        return driver.findElement(By.xpath("//*[@id='inputProfileConfirmPassword']"));
    }

    private WebElement getSavebutton(){
        return driver.findElement(By.xpath("//*[@class='btn-submit']"));
    }


}
