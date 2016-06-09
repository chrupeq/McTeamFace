package com.gui.test.login;



import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;


@RunWith(JUnitParamsRunner.class)
public class AddUserFormSeleniumTest {
	
	private static WebDriver driver;

	@Before
	public void setUp() {
	
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\A00236944\\Desktop\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/GroupProject2016/home.html");
		
	}
	

	@Parameters
	public Object[] validFormDetails(){
		return new Object[]{
			new Object[]{"John", "Hanley", "johnhanley", "123", "123", "CSR"},
			new Object[]{"Mike", "Cannon", "mikecannon", "987", "987", "SE"},
			new Object[]{"Rachel", "Carr", "rachelcarr", "555", "555", "NME"}
			
		};
	}
	
	@Parameters
	public Object[] passwordsDoNotMatch(){
		return new Object[]{
			new Object[]{"John", "Hanley", "johnhanley", "123", "124", "CSR"},
			new Object[]{"Mike", "Cannon", "mikecannon", "987", "943", "SE"},
			new Object[]{"Rachel", "Carr", "rachelcarr", "555", "666", "NME"}
			
		};
	}
	
	
	
	
	@Test
	public void testPageTitle() {
		assertEquals("Home Page", driver.getTitle());
	}
	
	@Test
	@Parameters(method = "passwordsDoNotMatch")
	public void testAddUserFormPasswordsDoNotMatch(String firstNameToTest, String lastNameToTest, String usernameToTest, String passwordToTest1, 
			String passwordToTest2, String jobTitleToTest){
	
		Actions actions = new Actions(driver);
		WebElement menuHoverLink = driver.findElement(By.linkText("Add User Form"));
		actions.moveToElement(menuHoverLink);
		actions.click();
		actions.perform();
			
		WebElement firstName = driver.findElement(By.id("firstName"));
		firstName.sendKeys(firstNameToTest);
		
		WebElement lastName = driver.findElement(By.id("lastName"));
		lastName.sendKeys(lastNameToTest);
		
		WebElement username = driver.findElement(By.id("usernameFormInput"));
		username.clear();
		username.sendKeys(usernameToTest);
		
		
		System.out.println("Getting here");
		WebElement password1 = driver.findElement(By.id("passwordFormInput"));
		password1.clear();
		password1.sendKeys(passwordToTest1);
		
		WebElement password2 = driver.findElement(By.id("reenterPasswordFormInput"));
		password2.clear();
		password2.sendKeys(passwordToTest2);

		Select dropdown = new Select(driver.findElement(By.id("jobTitleSelect")));
		dropdown.selectByValue(jobTitleToTest);
		
		WebElement formButton = driver.findElement(By.id("formButton"));
		formButton.click();
		
		//WebElement someElement = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.id("passwordErrorMessage")));
		assertTrue(driver.findElement(By.id("passwordErrorMessage")).isDisplayed());
		
		
	}
	

	

}
