package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ReportPage {

	public WebDriver driver;
	public WebDriverWait waitLong;

	public By employeeInput = By.id( "emplid" );
	public By employeeDropdown = By.cssSelector( "div.ac_results > ul > li" );
	public By viewReportButton = By.xpath( "//input[@type='submit']" );
	public By employeeNameColumn = By.xpath( "//tr[@class='evenRow' or @class='oddRow']/td[1]/a" );

	public ReportPage( WebDriver driver ) {
		this.driver = driver;
		this.waitLong = new WebDriverWait( driver, Duration.ofSeconds( 10 ) );
	}

	public void sendEmployeeValue( String employeeValue ) {
		WebElement employee = driver.findElement( employeeInput );
		employee.clear();
		employee.sendKeys( employeeValue );
	}

	public void selectEmployeeValueFromDropdown( String employeeValue ) {
		waitLong.until( ExpectedConditions.presenceOfElementLocated( employeeDropdown ) );
		List<WebElement> employeeValues = driver.findElements( employeeDropdown );
		for ( WebElement value : employeeValues ) {
			if ( value.getText().equals( employeeValue ) ) {
				value.click();
			}
		}
	}

	public void clickViewReport() {
		WebElement viewReport = waitLong.until( ExpectedConditions.presenceOfElementLocated( viewReportButton ) );
		viewReport.click();
	}

	public boolean isExpectedValueInEmployeeNameColumn( String expectedEmployeeValue ) {
		List<WebElement> employeesNames = driver.findElements( employeeNameColumn );
		boolean isSame = true;
		for ( WebElement employeeName : employeesNames ) {
			if ( !employeeName.getText().equalsIgnoreCase( expectedEmployeeValue ) ) {
				isSame = false;
				break;
			}
		}
		return isSame;
	}

}
