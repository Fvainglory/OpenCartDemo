package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

    public LoginPage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath="//input[@id='input-email']")
    WebElement txtEmail;
    @FindBy(xpath="//input[@id='input-password']")
    WebElement txtPwd;
    @FindBy(xpath="//button[normalize-space()='Login']")
    WebElement btnLogin;

    public void inputEmail(String email){
        txtEmail.sendKeys(email);
    }

    public void inputPwd(String pwd){
        txtPwd.sendKeys(pwd);
    }

    public void clickLogin(){
        btnLogin.click();
    }
}
