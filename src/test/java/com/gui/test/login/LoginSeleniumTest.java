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
	
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\A00236944\\Desktop\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/GroupProject2016/welcome.html");
		
	}
	
	@Parameters
	public Object[] validLoginDetails(){
		return new Object[]{
			new Object[]{"SystemAdmin1", "password1"},
			new Object[]{"NetworkEng2", "password2"},
			new Object[]{"SupportEng3", "password3"},
			new Object[]{"CustomerServiceRep4", "password4"}
		};
	}
	
	@Parameters
	public Object[] invalidLoginDetails(){
		return new Object[]{
			new Object[]{"SystemAdmin1", "password2"},
			new Object[]{"SystemAdmin1", "password3"},
			new Object[]{"SystemAdmin1", "password4"},
			new Object[]{"NetworkEng2", "password1"},
			new Object[]{"NetworkEng2", "password3"},
			new Object[]{"NetworkEng2", "password4"},
			new Object[]{"SupportEng3", "password1"},
			new Object[]{"SupportEng3", "password2"},
			new Object[]{"SupportEng3", "password4"},
			new Object[]{"CustomerServiceRep4", "password1"},
			new Object[]{"CustomerServiceRep4", "password2"},
			new Object[]{"CustomerServiceRep4", "password3"},
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
		WebElement someElement = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.id("home")));
		assertEquals("http://localhost:8080/GroupProject2016/home.html", driver.getCurrentUrl());
		assertEquals("Home Page", driver.getTitle());
		
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
		WebElement someElement = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.id("home")));
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
		WebElement someElement = (new WebDriverWait(driver, 15)).until(ExpectedConditions.presenceOfElementLocated(By.id("errorMessage")));
		assertTrue(driver.findElement(By.id("errorMessage")).isDisplayed());
	}
	
	@After
	public void tearDown(){
		driver.quit();
	}
	
	
}