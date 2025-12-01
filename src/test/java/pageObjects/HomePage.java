package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage{
    //Constructor
    public HomePage(WebDriver driver){
        super(driver);
    }

    //Locators
    @FindBy(xpath="//span[normalize-space()='My Account']")
    WebElement linkMyAccount;
    @FindBy(xpath="//a[normalize-space()='Register']")
    WebElement linkRegister;
    //@FindBy(xpath="//a[normalize-space()='Login']")
    @FindBy(linkText = "Login")
    WebElement linkLogin;

    //Action methods
    public void clickMyAccount(){
        linkMyAccount.click();
    }

    public void clickRegister(){
        linkRegister.click();
    }

    public void clickLogin(){
        linkLogin.click();
    }

}
