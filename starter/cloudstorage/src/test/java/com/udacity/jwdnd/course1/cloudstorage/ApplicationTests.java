package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

	private final String firstName = "David";
	private final String lastName = "Mills";
	private final String password = "seven";
	private final String username = "dMills";

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(1)
	public void signUpRequired() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
    public void signUpLoginRedirectToHomePageAndLogout(){
		// SignUp
		driver.get("http://localhost:" + this.port + "/signup");
		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.signUp(firstName, lastName, username, password);
		WebElement successMessage = driver.findElement(By.id("success-msg"));
		Assertions.assertEquals("You successfully signed up!", successMessage.getText());

		// Login and redirection to home page
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		// Logout
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(3)
	public void createEditAndDeleteNote(){
		driver.get("http://localhost:" + this.port + "/signup");

		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.signUp(firstName, lastName, username, password);

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);

		NotePage notePage = new NotePage(driver);
		notePage.clickNoteTab();
		webDriverWait.until(ExpectedConditions.visibilityOf(notePage.getAddNewNoteButton()));

		notePage.clickAddNewNoteButton();
		webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOf(notePage.getNoteSubmitButtonModal()));

		notePage.createNote("Don Quijote", "Don Quijote de la Mancha es una novela moderna y la primera novela polifónica escrita por el español Miguel de Cervantes Saavedra");

		webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("logout-button"))));
		notePage.clickNoteTab();
		webDriverWait.until(ExpectedConditions.visibilityOf(notePage.getAddNewNoteButton()));

		Assertions.assertEquals("Don Quijote", notePage.getNoteTitle());

		notePage.clickEditNoteButton();
		webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOf(notePage.getNoteSubmitButtonModal()));
		notePage.editNote("Sancho Panza", "Don Quijote de la Mancha es una novela moderna y la primera novela polifónica escrita por el español Miguel de Cervantes");

		webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("logout-button"))));
		notePage.clickNoteTab();
		webDriverWait.until(ExpectedConditions.visibilityOf(notePage.getAddNewNoteButton()));

		Assertions.assertEquals("Sancho Panza", notePage.getNoteTitle());

		notePage.clickDeleteNoteButton();

		Assertions.assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.id("note-title-view"));
		});
	}

	@Test
	@Order(4)
	public void CRUDCredentials(){
		driver.get("http://localhost:" + this.port + "/signup");

		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.signUp(firstName, lastName, username, password);

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("logout-button"))));

		CredentialPage credentialPage = new CredentialPage(driver);

		credentialPage.clickCredentialTab();

		webDriverWait.until(ExpectedConditions.visibilityOf(credentialPage.getAddNewCredentialButton()));

		credentialPage.clickAddNewCredentialButton();
		webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOf(credentialPage.getSubmitButtonModal()));

		credentialPage.createCredential("https://www.udacity.com/","admin","admin");

		webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("logout-button"))));
		credentialPage.clickCredentialTab();
		webDriverWait.until(ExpectedConditions.visibilityOf(credentialPage.getAddNewCredentialButton()));

		Assertions.assertEquals("https://www.udacity.com/", credentialPage.getCredentialUrlView());

		credentialPage.clickEditCredentialButton();
		webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOf(credentialPage.getSubmitButtonModal()));
		credentialPage.editCredential("google.com", "flash", "temp");

		webDriverWait = new WebDriverWait(driver, 5);
		webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("logout-button"))));
		credentialPage.clickCredentialTab();

		webDriverWait.until(ExpectedConditions.visibilityOf(credentialPage.getAddNewCredentialButton()));

		Assertions.assertEquals("google.com", credentialPage.getCredentialUrlView());

		credentialPage.clickDeleteCredentialButton();

		Assertions.assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.id("credential-url-view"));
		});





	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}



}
