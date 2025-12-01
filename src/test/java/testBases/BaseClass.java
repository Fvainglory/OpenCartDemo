package testBases;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {

    public static WebDriver driver;
    public Logger logger;   //org.apache.logging.log4j.Logger;
    public Properties p;

    //setup
    @BeforeClass(groups = {"sanity","regression","datadriven","master"})
    @Parameters({"os", "browser"})
    public void setup(String os, String browser) throws IOException {
        //Loading config.properties file
        FileReader file = new FileReader("./src//test//resources//config.properties");
        p = new Properties();
        p.load(file);

        logger = LogManager.getLogger(this.getClass()); //specify the className, this.getClass will get className

        String hubUrl = " http://localhost:4444/wd/hub";
        if (p.getProperty("execution_env").equalsIgnoreCase("remote")){
            System.out.println("Starting running in remote web driver...");
            DesiredCapabilities cap = new DesiredCapabilities();
            //setPlatform
            if (os.equalsIgnoreCase("Windows")){
                cap.setPlatform(Platform.WIN11);
            }else if (os.equalsIgnoreCase("Mac")){
                cap.setPlatform(Platform.MAC);
            }else if (os.equalsIgnoreCase("Linux")){
                cap.setPlatform(Platform.LINUX);
            }else {
                System.out.println("No matching operation system");
                return ;
            }
            //setBrowser
            switch (browser.toLowerCase()){
                case "edge" : cap.setBrowserName("MicrosoftEdge"); break;
                case "chrome" : cap.setBrowserName("chrome"); break;
                case "firefox" : cap.setBrowserName("firefox"); break;
                default:
                    System.out.println("No matching browser"); return;
            }
            driver = new RemoteWebDriver(new URL(hubUrl),cap);
        }

        if (p.getProperty("execution_env").equalsIgnoreCase("local")){
            System.out.println("Starting running in local web driver...");
            switch (browser.toLowerCase()) {
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "safari":
                    driver = new SafariDriver();
                    break;
                default:
                    System.out.println("Invalid browser name...");
                    logger.error("NoSuchDriverException...");
                    return;
            }
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get(p.getProperty("appURL1"));   //reading url from properties file //localhost url cannot be used for remote webdriver
        driver.manage().window().maximize();
    }

    //teardown
    @AfterClass(groups = {"sanity","regression","datadriven","master"})
    public void teardown() {
        driver.quit();
    }

    public String randomString() {
        String generatedString = RandomStringUtils.randomAlphabetic(6);
        return generatedString;

    }

    public String randomNumber() {
        String generatedNumber = RandomStringUtils.randomNumeric(10);
        return generatedNumber;
    }

    public String randomAlphaNumberic() {
        String generatedString = RandomStringUtils.randomAlphabetic(3);
        String generatedNumber = RandomStringUtils.randomNumeric(3);
        return (generatedString + "@" + generatedNumber);
    }

    public String captureScreenShot(String testName){
        String dataFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());

        TakesScreenshot ts = (TakesScreenshot) driver;
        File screenshotFile = ts.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + testName + "_" + dataFormat + ".png";
        System.out.println(targetFilePath);
        File targetFile= new File(targetFilePath);
        screenshotFile.renameTo(targetFile);

        return targetFilePath;
    }
}
