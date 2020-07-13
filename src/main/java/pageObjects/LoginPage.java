package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

	public WebDriver driver;
	public WebDriverWait waitLong;

	public By usernameInput = By.id( "username" );
	public By passwordInput = By.xpath( "//input[@name='password']" );
	public By loginButton = By.xpath( "//input[@name='login']" );

	public LoginPage( WebDriver driver ) {
		this.driver = driver;
		this.waitLong = new WebDriverWait( driver, Duration.ofSeconds( 10 ) );
	}

	public void sendUsernameInput( String value ) {
		WebElement username = waitLong.until( ExpectedConditions.presenceOfElementLocated( usernameInput ) );
		username.sendKeys( value );
	}

	public void sendPassword( String value ) {
		WebElement password = waitLong.until( ExpectedConditions.presenceOfElementLocated( passwordInput ) );
		password.sendKeys( value );
	}

	public void submitLogin() {
		WebElement login = waitLong.until( ExpectedConditions.presenceOfElementLocated( loginButton ) );
		login.click();
	}

	public void login( String username, String password ) {
		sendUsernameInput( username );
		sendPassword( password );
		submitLogin();
	}

}
