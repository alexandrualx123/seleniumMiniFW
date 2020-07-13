import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTest {

	@Test
	public void testLoginPage() {
		System.out.println( "Login test" );

		loginPage.sendUsernameInput( "admin" );
		loginPage.sendPassword( "admin" );
		loginPage.submitLogin();
	}

	@Test
	public void testLoginWithInvalidPassword() {
		loginPage.login( "admin", "wrong" );
		// TODO Add specific assertion to check validation message
	}

}
