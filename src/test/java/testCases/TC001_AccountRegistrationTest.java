package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBases.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
    //verify register
    @Test(groups = {"regression","master"})
    public void verify_account_registration() {
        try {
            logger.info("****** Starting Test ******");
            HomePage hp = new HomePage(driver);
            logger.info("Click on MyAccount link...");
            hp.clickMyAccount();
            logger.info("Click on Register link...");
            hp.clickRegister();

            logger.info("Provide customer details...");
            AccountRegistrationPage arp = new AccountRegistrationPage(driver);
            arp.inputFirstName(randomString());
            arp.inputLastName(randomString());
            arp.inputEmail(randomString() + "@gmail.com");
            arp.inputTelephone(randomNumber());

            String pwd = randomAlphaNumberic();
            arp.inputPwd(pwd);
            arp.inputPwdConfirm(pwd);
            arp.clickPolicy();
            arp.clickContinue();

            logger.info("Validate expected message...");
            String actualvdMessage = arp.validationMessage();
            Assert.assertEquals(actualvdMessage, "Your Account Has Been Created!");
        } catch (Exception e) {
            logger.debug("Debug logs...");
            logger.error("Test Case Failed !!!");
            Assert.fail();

        }
    }

}
