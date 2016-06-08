package com.gui.test.login;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class LoginSeleniumTest {

	private WebDriver driver;

	@Before
	public void setUp() {
		driver = new ChromeDriver();
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\A00226084\\Desktop\\chromedriver.exe");
		driver.get("http://localhost:8080/GroupProject2016/welcome.html");
		
	}
	
	@Parameters
	public Object[] validLoginDetails(){
		return new Object[]{
			new Object[]{"Manager2016", "password1"},
			new Object[]{"LineWorker2016", "password2"}
		};
	}
	
	@Parameters
	public Object[] invalidLoginDetails(){
		return new Object[]{
			new Object[]{"Manager2016", "password2"},
			new Object[]{"Manager2017", "password1"},
			new Object[]{"Manager2017", "password2"},
			new Object[]{"LineWorker2016", "password1"},
			new Object[]{"LineWorker2017", "password2"},
			new Object[]{"LineWorker2017", "password1"},
			new Object[]{"", ""}
		};
	}

	@Test
	public void testPageTitle() {
		assertEquals("Login Page", driver.getTitle());
	}
	
	@Test
	@Parameters(method = "validLoginDetails")
	public void testPageNavigationSuccessOnSuccessfulLogin(String usernameToTest, String passwordToTest){
		WebElement username = driver.findElement(By.id("username"));
		username.clear();
		username.sendKeys(usernameToTest);
		WebElement password = driver.findElement(By.id("password"));
		password.clear();
		password.sendKeys(passwordToTest);
		WebElement loginButton = driver.findElement(By.id("button"));
		loginButton.click();
		WebElement someElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("config")));
		assertEquals("https://www.reddit.com/", driver.getCurrentUrl());
		assertEquals("reddit: the front page of the internet", driver.getTitle());
		
	}
	
	@Test
	@Parameters(method = "invalidLoginDetails")
	public void testNoPageNavigationOnFailedLoginWithIncorrectDetails(String usernameToTest, String passwordToTest){
		WebElement username = driver.findElement(By.id("username"));
		username.clear();
		username.sendKeys(usernameToTest);
		WebElement password = driver.findElement(By.id("password"));
		password.clear();
		password.sendKeys(passwordToTest);
		WebElement loginButton = driver.findElement(By.id("button"));
		loginButton.click();
		assertEquals("http://localhost:8080/GroupProject2016/welcome.html", driver.getCurrentUrl());
		assertEquals("Login Page", driver.getTitle());
		
	}
	
	
	@Test
	@Parameters(method = "validLoginDetails")
	public void testErrorMessageNotDisplayedOnSuccessfulLogin(String usernameToTest, String passwordToTest){
		WebElement username = driver.findElement(By.id("username"));
		username.clear();
		username.sendKeys(usernameToTest);
		WebElement password = driver.findElement(By.id("password"));
		password.clear();
		password.sendKeys(passwordToTest);
		WebElement loginButton = driver.findElement(By.id("button"));
		loginButton.click();
		WebElement someElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("config")));
		assertFalse(driver.findElements(By.id("errorMessage")).size() > 0);
	}
	
	@Test
	@Parameters(method = "invalidLoginDetails")
	public void testErrorMessageDisplayedOnFailedLogin(String usernameToTest, String passwordToTest){
		WebElement username = driver.findElement(By.id("username"));
		username.clear();
		username.sendKeys(usernameToTest);
		WebElement password = driver.findElement(By.id("password"));
		password.clear();
		password.sendKeys(passwordToTest);
		WebElement loginButton = driver.findElement(By.id("button"));
		loginButton.click();
		WebElement someElement = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("errorMessage")));
		assertTrue(driver.findElement(By.id("errorMessage")).isDisplayed());
	}
	
	@After
	public void tearDown(){
		driver.quit();
	}
	
	
}