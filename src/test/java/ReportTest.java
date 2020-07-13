import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportTest extends BaseTest {

	@BeforeEach
	public void setUpEach() {
		loginPage.login( "admin", "admin" );
	}

	@Test
	public void testFilterEmployeesReport() {
		String employeeName = "test";
		reportPage.sendEmployeeValue( employeeName );
		reportPage.selectEmployeeValueFromDropdown( employeeName );
		reportPage.clickViewReport();

		Assertions.assertTrue( reportPage.isExpectedValueInEmployeeNameColumn( employeeName ), "Expected value for employee name to be: " + employeeName );
	}

}
