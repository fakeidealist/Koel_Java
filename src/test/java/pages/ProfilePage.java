package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends MainPage {
    public ProfilePage(WebDriver driver){
        super(driver);
    }

    private WebElement getAvatarIcon(){
        By avatarIconBy = By.xpath("//*[@class='avatar']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(avatarIconBy));
        return driver.findElement(avatarIconBy);
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

    public void renameUserName(String newName){
        getAvatarIcon().click();
        getNameField().sendKeys(Keys.CONTROL + "A");
        getNameField().sendKeys(newName);
        getSavebutton().click();
    }

}

//*[contains(text(),'Profile updated.')]