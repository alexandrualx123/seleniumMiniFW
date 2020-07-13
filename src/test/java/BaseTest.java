import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import pageObjects.LoginPage;
import pageObjects.ReportPage;

public class BaseTest {

	private static WebDriver driver;
	private static String baseUrl = "http://qualitypointtech.net/timesheetdemo/report.php";
	WebDriverWait waitLong = new WebDriverWait( driver, 10 );
	WebDriverWait waitShort = new WebDriverWait( driver, 1 );

	public LoginPage loginPage = new LoginPage( driver );
	public ReportPage reportPage = new ReportPage( driver );

	@BeforeAll
	public static void setUpAll() {
		System.setProperty( "webdriver.chrome.driver", "src/main/resources/chromedriver.exe" );
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@BeforeEach
	public void setUpEach() {
		driver.get( baseUrl );
	}

	@AfterAll
	public static void tearDown() {
		driver.quit();
	}

	@AfterEach
	public void takeScreenshot() throws Exception {
		takeSnapShot( driver, "screenshots/afterLoginScreenShot.png" );
	}

	public static void takeSnapShot( WebDriver webdriver, String fileWithPath ) throws Exception {
		TakesScreenshot scrShot = ( (TakesScreenshot) webdriver );
		File SrcFile = scrShot.getScreenshotAs( OutputType.FILE );
		File DestFile = new File( fileWithPath );
		FileUtils.copyFile( SrcFile, DestFile );
	}
}
