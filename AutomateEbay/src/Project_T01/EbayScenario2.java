package Project_T01;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EbayScenario2 {
	
	  // Set the system property to use the Chrome driver
	static {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
	}
	@Test
	public void accessProductViaSearch() throws IOException  {
		
		// Create an instance of the Chrome driver
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.ebay.com/");
		
		//fetching the data from property file to reuse it further
		FileInputStream fis =new FileInputStream("./data/commandata.property");
		Properties p= new Properties();
		p.load(fis);
		String search=p.getProperty("key");
		
		WebElement searchBox = driver.findElement(By.xpath("//input[@class='gh-tb ui-autocomplete-input']"));
		searchBox.sendKeys(search);
		
		WebElement listBox = driver.findElement(By.xpath("//select[@aria-label='Select a category for search']"));

		//Creating object of Select class will allow u to access options of list box
		Select s= new Select(listBox);
		s.selectByValue("58058");
		driver.findElement(By.xpath("//input[@class='btn btn-prim gh-spr']")).click();
		
		// page title will print when page loads sucessfully
		
		String tittle = driver.getTitle();
		System.out.println(tittle);
		
		//name of first searched result will store in firstResult to do verification 
		String firstResult = driver.findElement(By.xpath("(//span[contains(text(),'Apple MacBook')])[1]")).getText();
	
		if (firstResult.contains(search))
		{
			Assert.assertTrue(true);
		}
		else Assert.fail();
		
		driver.close();
		}
	}