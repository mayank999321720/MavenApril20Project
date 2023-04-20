package Project_T01;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EbayScenario1 {
	
	  // Set the system property to use the Chrome driver
	static {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
	}
	@Test
	public void applyFiters() throws IOException {
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// Launch the Online Store Website
		driver.get("https://www.ebay.com/");
		driver.findElement(By.id("gh-shop-a")).click();
		driver.findElement(By.linkText("Cell phones & accessories")).click();
		driver.findElement(By.linkText("Cell Phones & Smartphones")).click();
		driver.findElement(By.xpath("(//button[@class='btn btn--small'])[1]")).click();
		driver.findElement(By.xpath("//div[@role='tab' and @id='c3-mainPanel-Screen%20Size']")).click();

		driver.findElement(By.id("c3-subPanel-Screen%20Size_5.5%20-%205.9%20in_cbx")).click();

		driver.findElement(By.xpath("//div[@id='c3-mainPanel-price']")).click();
		
		//fetching the data from property file to reuse it further
		
		FileInputStream fis =new FileInputStream("./data/commandata.property");
		Properties p= new Properties();
		p.load(fis);
		String min=p.getProperty("min");
		String max=p.getProperty("max");
		
		WebElement minPrice = driver.findElement(By.xpath("//input[@aria-label='Minimum Value, US Dollar']"));
		minPrice.sendKeys(min);
		WebElement maxPrice = driver.findElement(By.xpath("//input[@aria-label='Maximum Value, US Dollar']"));
		maxPrice.sendKeys(max);
		
		driver.findElement(By.xpath("//div[@data-aspecttitle='location']")).click();
		driver.findElement(By.xpath("//input[@value='Worldwide']")).click();
		driver.findElement(By.xpath("//button[@aria-label='Apply']")).click();
		driver.findElement(By.xpath("//span[contains(text(),'filters applied')]")).click();

		//storing of filter name so that we can verify further steps 
		String filter1 = driver.findElement(By.xpath("//span[contains(text(),'Screen Size:')]")).getText();
		String filter2 = driver.findElement(By.xpath("//span[contains(text(),'Price:')]")).getText();
		String filter3 = driver.findElement(By.xpath("//span[contains(text(),'Item Location: Worldwide')]")).getText();
		
		String ScreenSize =p.getProperty("keyA");
		String Price =p.getProperty("keyB");
		String ItemLocation=p.getProperty("keyC");
		
		if (filter1.contains(ScreenSize)) {
			Assert.assertTrue(true);
		}
		else Assert.fail();

		if (filter2.contains(Price)) {
			Assert.assertTrue(true);
		}
		else Assert.fail();

		if (filter3.contains(ItemLocation)) {
			Assert.assertTrue(true);
		}
		else Assert.fail();


		driver.close();


	}

}
