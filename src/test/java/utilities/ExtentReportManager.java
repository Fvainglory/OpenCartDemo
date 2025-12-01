package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBases.BaseClass;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter esReport;
    public ExtentReports eReport;
    public ExtentTest eTest;

    String repName;
    
    public void onStart(ITestContext context){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = new Date();
        String dateStamp = sdf.format(date);
        repName = "Automation Report - " + dateStamp + ".html";
        esReport = new ExtentSparkReporter(System.getProperty("user.dir")+"\\reports\\"+repName);

        esReport.config().setDocumentTitle("Opencart Automation Report");
        esReport.config().setReportName("Opencart Functional report");
        esReport.config().setTheme(Theme.STANDARD);

        eReport = new ExtentReports();
        eReport.attachReporter(esReport);
        eReport.setSystemInfo("Application","Opencart");
        eReport.setSystemInfo("Module","Admin");
        eReport.setSystemInfo("SubModule","Customers");
        eReport.setSystemInfo("UserName",System.getProperty("user.name"));   //???
        eReport.setSystemInfo("Environment","SIT");

        String os = context.getCurrentXmlTest().getParameter("os");
        eReport.setSystemInfo("Operation System",os);

        String browser = context.getCurrentXmlTest().getParameter("browser");
        eReport.setSystemInfo("Operation System",browser);

        List<String> groupLists = context.getCurrentXmlTest().getIncludedGroups();
        if (!groupLists.isEmpty()){
            eReport.setSystemInfo("Groups",groupLists.toString());
        }

    }

    public void onTestSuccess(ITestResult result){
/*
        System.out.println("===========================");
        System.out.println(result.getClass());  //class org.testng.internal.TestResult
        System.out.println(result.getTestClass());  //[TestClass name=class testCases.TC002_LoginTest]
        System.out.println(result.getTestClass().getRealClass());   //class testCases.TC002_LoginTest
        System.out.println(result.getInstanceName());   //testCases.TC002_LoginTest
        System.out.println(result.getMethod()); //TC002_LoginTest.verify_Login()[pri:0, instance:testCases.TC002_LoginTest@3857f613]
        System.out.println(result.getName());   //verify_Login
        System.out.println("===========================");
*/

        eTest = eReport.createTest(result.getTestClass().getName());
        eTest.assignCategory(result.getMethod().getGroups());
        eTest.log(Status.PASS, result.getName()+" got successfully executed!");
    }

    public void onTestSkipped(ITestResult result){
        eTest = eReport.createTest(result.getTestClass().getName());
        eTest.assignCategory(result.getMethod().getGroups());
        eTest.log(Status.SKIP, result.getName()+" got skipped");
        eTest.log(Status.INFO, result.getThrowable().getMessage());
    }

    public void onTestFailure(ITestResult result){
        eTest = eReport.createTest(result.getTestClass().getName());
        eTest.assignCategory(result.getMethod().getGroups());
        eTest.log(Status.FAIL, result.getName()+" got failed!");
        eTest.log(Status.INFO, result.getThrowable().getMessage());
        try {
            String imgPath = new BaseClass().captureScreenShot(result.getName());
            eTest.addScreenCaptureFromPath(imgPath);
            //在某些框架中可能直接使用Base64
            //String base64Screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            //eTest.addScreenCaptureFromBase64String(base64Screenshot);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onFinish(ITestContext context) {
        eReport.flush();
        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        }catch (IOException e){
            e.printStackTrace();
        }

/*
        try {
            URL url = new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName); //file:/// - 文件协议前缀，表示本地文件
            System.out.println(url);
            ImageHtmlEmail email = new ImageHtmlEmail();    //创建支持HTML内容和内嵌图片的邮件对象
            email.setDataSourceResolver(new DataSourceUrlResolver(url));    //设置邮件中HTML内容引用的资源（如图片）的解析器
            email.setHostName("smtp.qq.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("1459760191@qq.com","tjbqxbnjolinfhhb"));   //提供发件箱的登录凭证，邮箱地址和授权码（不是密码）
            email.setSSLOnConnect(true);    //启SSL加密连接，保证邮件传输安全

            email.setFrom("1459760191@qq.com"); //指定发件人邮箱地址（应与认证邮箱一致）
            email.setSubject("Test Results");   //设置邮件的主题/标题
            email.setMsg("Please find Attached Report..."); //设置邮件的纯文本内容（备用内容，当HTML无法显示时使用）
            email.addTo("fxl0204_001@163.com");
            email.attach(url,"extent report", "please check report");   //将测试报告文件作为附件添加到邮件中
            email.send();

            System.out.println("email send successfully");
        } catch (Exception e) {
            System.out.println("email send failed");
            e.printStackTrace();
        }
*/

    }

}
