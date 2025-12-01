package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountRegistrationPage extends BasePage{
    public AccountRegistrationPage(WebDriver driver){
        super(driver);
    }

    @FindBy(xpath="//input[@id='input-firstname']")
    WebElement textFirstName;
    @FindBy(xpath="//input[@id='input-lastname']")
    WebElement textLastName;
    @FindBy(xpath="//input[@id='input-email']")
    WebElement textEMail;
    @FindBy(xpath="//input[@id='input-telephone']")
    WebElement textTelephone;
    @FindBy(xpath="//input[@id='input-password']")
    WebElement textPassword;
    @FindBy(xpath="//input[@id='input-confirm']")
    WebElement textPwdConfirm;
    @FindBy(xpath="//input[@name='agree']")
    WebElement checkPolicy;




    @FindBy(xpath="//input[@value='Continue']")
    WebElement btnContinue;
    //Your Account Has Been Created!
    @FindBy(xpath="//div[@id='content']//h1")
    WebElement validationMessage;

    public void inputFirstName(String firstName){
        textFirstName.sendKeys(firstName);
    }

    public void inputLastName(String lastName){
        textLastName.sendKeys(lastName);
    }

    public void inputEmail(String email){
        textEMail.sendKeys(email);
    }

    public void inputTelephone(String tele){
        textTelephone.sendKeys(tele);
    }

    public void inputPwd(String pwd){
        textPassword.sendKeys(pwd);
    }

    public void inputPwdConfirm(String pwdConfirm){
        textPwdConfirm.sendKeys(pwdConfirm);
    }

    public void clickPolicy(){
        checkPolicy.click();
    }

    public void clickContinue() throws InterruptedException {
        //sol1
        btnContinue.click();
        Thread.sleep(3000);
        //sol2
        //btnContinue.submit();

        //sol3 --- remember to end with .build().perform()
        //Actions act = new Actions(driver);
        //act.moveToElement(btnContinue).click().build().perform();

        //sol4
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        //js.executeScript("arguments[0].click()",btnContinue);

        //sol5
        //btnContinue.sendKeys(Keys.RETURN);

        //sol6
        //WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(3));
        //mywait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
    }

    public String validationMessage(){
        try{
            String vdMessage = validationMessage.getText();
            return vdMessage;
        }catch (Exception e){
            return (e.getMessage());
        }

    }
}
