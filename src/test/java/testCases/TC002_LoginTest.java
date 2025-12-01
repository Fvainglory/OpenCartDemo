package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBases.BaseClass;

public class TC002_LoginTest extends BaseClass {

    @Test(groups = {"sanity","master"})
    public void verify_Login(){
        try {
            logger.info("****** Starting Test Login Case ******");
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickLogin();

            LoginPage lp = new LoginPage(driver);
            lp.inputEmail(p.getProperty("email"));
            lp.inputPwd(p.getProperty("password"));
            lp.clickLogin();

            MyAccountPage map = new MyAccountPage(driver);
            boolean accountPageStatus = map.myAccIsDispalyed();
            //Assert.assertEquals(accountPageStatus,true,"Test failed");
            Assert.assertTrue(accountPageStatus);
        }catch (Exception e){
            Assert.fail();
        }
        logger.info("****** Test Login Case Finished ******");
    }

}
