import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;
import java.util.Set;
//import java.util.concurrent.TimeUnit;

public class SeleniumFirstTest {

    private static WebDriver driver;
    private static String baseUrl = "http://qualitypointtech.net/timesheetdemo/report.php";
    WebDriverWait waitLong = new WebDriverWait(driver, 10);
    WebDriverWait waitShort = new WebDriverWait(driver, 1);

    @BeforeAll
    public static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "C:/WORK/webdrivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeEach
    public void setUpEach() {
        driver.get(baseUrl);
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void test_login_and_screenshot() throws Exception {
        System.out.println("@Test - test_method_1");
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        System.out.println(driver.getTitle());
        takeSnapShot(driver, "screenshots/beforeLoginScreenShot.png");

        WebElement usernameInput = waitLong.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        //WebElement usernameInput = driver.findElement(By.id("username"));
        usernameInput.sendKeys("admin");

        WebElement passwordInput = waitShort.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='password']")));
        //WebElement passwordInput = driver.findElement(By.xpath("//input[@name='password']"));
        passwordInput.sendKeys("admin");

        WebElement loginButton = waitShort.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='login']")));
        //WebElement loginButton = driver.findElement(By.xpath("//input[@name='login']"));
        loginButton.click();
        takeSnapShot(driver, "screenshots/afterLoginScreenShot.png");

        //Thread.sleep(2000);
    }

    public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(SrcFile, DestFile);
    }

    @Test
    public void test_WindowHandles() throws InterruptedException {

        WebElement newWindowYT = waitShort.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'this Video')]")));

        String mainWindowHandle = driver.getWindowHandle();
        String youtubeWindowHandle = "";
        System.out.println("Main Window after clicking on youtube link: " + mainWindowHandle);

        newWindowYT.click();  //a[contains(@href, 'youtube']

        //System.out.println("Current Window after clicking on youtube link: " + currentWindow);

        Set<String> windowHandlesSet = driver.getWindowHandles();
        for (String handle : windowHandlesSet) {
            if (handle != mainWindowHandle)
                youtubeWindowHandle = handle;
            driver.switchTo().window(handle);
            System.out.println("handles " + handle);
        }

        Thread.sleep(2000);
        WebElement searchBarYoutube = waitLong.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@name='search_query']")));
        searchBarYoutube.click();
        searchBarYoutube.clear();
        searchBarYoutube.sendKeys("lady gaga");

        Thread.sleep(2000);

        driver.close();
        driver.switchTo().window(mainWindowHandle);

        newWindowYT.click();
        driver.close();
    }

    /*
    1.Navigate to http://qualitypointtech.net/timesheetdemo/
    2. Login with admin/admin
    3. In Report view, type ‘test’ in Employee input.
    4. Select ‘test’ option from dropdown
    5. Click ‘View Report’ button
    6. Verify that under column ‘Employee Name’ you have only ‘test’ value

    Hints:
    Step 3 - For dropdown options, use a list of WebElements.
    List<WebElement> elementName = driver.findElements(By.LocatorStrategy("LocatorValue"));
    Step 6 – for column values, you can use the path that you already have it. Create a method that verifies the values and return a Boolean value then use it in test assertion.
    Try to use css instead of xpath.
     */

    @Test
    public void test_playWithElements() throws InterruptedException {

        WebElement usernameInput = waitLong.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        usernameInput.sendKeys("admin");
        WebElement passwordInput = waitShort.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='password']")));
        passwordInput.sendKeys("admin");
        WebElement loginButton = waitShort.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='login']")));
        loginButton.click();

        WebElement arrowButton = waitShort.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='button' and @class='butclas']")));
        arrowButton.click();

        //Thread.sleep(2000);

        List<WebElement> employees = driver.findElements(By.xpath("//select[@id='selEmployeeId']/*"));

//todo
//        Select select = new Select(driver.findElement(By.xpath("//select[@id='selEmployeeId']")));
//        select.selectByVisibleText("test1 (test  )");

        for (WebElement elem : employees) {
            if (elem.getText().equals("test1 (test  )")) {
                elem.click();
                break;
            }
        }

        WebElement viewReportButton = waitShort.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='submit']")));
        viewReportButton.click();

        //Thread.sleep(2000);

        List<WebElement> employeesName = driver.findElements(By.xpath("//tr[@class='evenRow' or @class='oddRow']/td[1]/a"));
        for (WebElement e : employeesName)
            Assertions.assertTrue(verifyEmployeeName(e), "Different expected employee name!");
    }

    public boolean verifyEmployeeName(WebElement e) {
        if (e.getText().equals("test"))
            return true;
        return false;
    }
}
