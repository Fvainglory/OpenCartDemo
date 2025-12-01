package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBases.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

    @Test(dataProvider = "loginData",dataProviderClass = DataProviders.class,groups = {"datadriven","master"})
    public void verify_LoginDDT(String email,String pwd, String res ){
        try {
            logger.info("****** Starting Test Login Case ******");
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickLogin();

            LoginPage lp = new LoginPage(driver);
            lp.inputEmail(email);
            lp.inputPwd(pwd);
            lp.clickLogin();

            MyAccountPage map = new MyAccountPage(driver);
            boolean accountPageStatus = map.myAccIsDispalyed();
            //Assert.assertEquals(accountPageStatus,true,"Test failed");
            if (res.equalsIgnoreCase("Valid")){
                if (accountPageStatus == true){
                    map.clickLogout();
                    Assert.assertTrue(accountPageStatus);
                }else {
                    Assert.assertTrue(false);
                }
            }
            if (res.equalsIgnoreCase("InValid")){
                if (accountPageStatus == true){
                    map.clickLogout();
                    Assert.assertTrue(false);
                }else {
                    Assert.assertTrue(true);
                }
            }
        }catch (Exception e){
            Assert.fail();
        }
        logger.info("****** Test Login Case Finished ******");
    }

}
