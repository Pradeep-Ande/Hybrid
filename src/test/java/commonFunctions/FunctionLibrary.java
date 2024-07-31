package commonFunctions;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;


public class FunctionLibrary {
	public static WebDriver dr;
	public static Properties conpro;
	public static WebDriver startBrowser() throws Throwable
	{
		conpro=new Properties();
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			dr=new ChromeDriver();
			dr.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			dr=new FirefoxDriver();
		}
		return dr;
	}
	public static void openUrl()
	{
		dr.get(conpro.getProperty("Url"));
	}
	public static void waitforElement(String LocatorType,String LocatorValue,String TestData)
	
	{
		WebDriverWait mywait=new WebDriverWait(dr, Duration.ofSeconds(Integer.parseInt(TestData)));
		if(LocatorType.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
			
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
			
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
			
		}
	}
	public static void typeAction(String LocatorType,String LocatorValue,String TestData)
	{
		if(LocatorType.equalsIgnoreCase("name"))
		{
			dr.findElement(By.name(LocatorValue)).clear();
			dr.findElement(By.name(LocatorValue)).sendKeys(TestData);
			
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			dr.findElement(By.xpath(LocatorValue)).clear();
			dr.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
			
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			dr.findElement(By.id(LocatorValue)).clear();
			dr.findElement(By.id(LocatorValue)).sendKeys(TestData);
			
		}
	}
	public static void clickAction(String LocatorType,String LocatorValue)
	{
		if(LocatorType.equalsIgnoreCase("name"))
		{
			dr.findElement(By.name(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			dr.findElement(By.xpath(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			dr.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
	}
	public static void validateTitle(String Exp_Title)
	{
		String Act_Title=dr.getTitle();
		try {
			Assert.assertEquals(Act_Title, Exp_Title,"Title is Not Matching");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void closeBrowser()
	{
		dr.quit();
	}
	public static String generaateDate()
	{
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("YYYY_MM_dd hh_mm");
		return df.format(date);
	}
	public static void dropDownAction(String LocatorType,String LocatorValue,String TestData)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			int value=Integer.parseInt(TestData);
			Select element=new Select(dr.findElement(By.xpath(LocatorValue)));
			element.selectByIndex(value);
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			int value=Integer.parseInt(TestData);
			Select element=new Select(dr.findElement(By.id(LocatorValue)));
			element.selectByIndex(value);
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			int value=Integer.parseInt(TestData);
			Select element=new Select(dr.findElement(By.name(LocatorValue)));
			element.selectByIndex(value);
		}
	}
	public static void captureStock(String LocatorType,String LocatorValue) throws Throwable
	{
		String stockNum="";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			stockNum=dr.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			stockNum=dr.findElement(By.name(LocatorValue)).getAttribute("value");	
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			stockNum=dr.findElement(By.id(LocatorValue)).getAttribute("value");
			
		}
		FileWriter fw=new FileWriter("./CaptureData/StockNumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(stockNum);
		bw.flush();
		bw.close();
	}
	public static void stockTable() throws Throwable
	{
		FileReader fr=new FileReader("./CaptureData/StockNumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_Data=br.readLine();
		if(!dr.findElement(By.xpath(conpro.getProperty("searchTextbox"))).isDisplayed())
		{
			dr.findElement(By.xpath(conpro.getProperty("searchPanel"))).click();
		}
		dr.findElement(By.xpath(conpro.getProperty("searchTextbox"))).clear();
		dr.findElement(By.xpath(conpro.getProperty("searchTextbox"))).sendKeys(Exp_Data);
		dr.findElement(By.xpath(conpro.getProperty("searchButton"))).click();
		Thread.sleep(3000);
		String Act_Data=dr.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Exp_Data+"     "+Act_Data,true);
		try {
			Assert.assertEquals(Act_Data, Exp_Data,"Stock Number Is Not Matching");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
		
	}
	public static void captureSupplier(String LocatorType,String Locatorvalue) throws Throwable
	{
		String supplierNum="";
		if(LocatorType.equalsIgnoreCase("name"))
		{
			supplierNum=dr.findElement(By.name(Locatorvalue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			supplierNum=dr.findElement(By.xpath(Locatorvalue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			supplierNum=dr.findElement(By.id(Locatorvalue)).getAttribute("value");
		}
		FileWriter fw=new FileWriter("./CaptureData/supplierNumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(supplierNum);
		bw.flush();
		bw.close();
	}
	public static void supplierTable() throws Throwable
	{
		Thread.sleep(5000);
		FileReader fr=new FileReader("./CaptureData/supplierNumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_data=br.readLine();
		if(!dr.findElement(By.xpath(conpro.getProperty("searchTextbox"))).isDisplayed())
			dr.findElement(By.xpath(conpro.getProperty("searchPanel"))).click();
		dr.findElement(By.xpath(conpro.getProperty("searchTextbox"))).clear();
		dr.findElement(By.xpath(conpro.getProperty("searchTextbox"))).sendKeys(Exp_data);
		Thread.sleep(2000);
		dr.findElement(By.xpath(conpro.getProperty("searchButton"))).click();
		Thread.sleep(3000);
		String Act_data=dr.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr/td[6]/div/span/span")).getText();
		Reporter.log(Exp_data+"    "+Act_data,true);

		try {
			Assert.assertEquals(Act_data, Exp_data,"Supplier is Not Matching");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public static void captureCustomer(String LocatorType,String Locatorvalue) throws Throwable
	{
		String CustomerNum="";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			CustomerNum=dr.findElement(By.xpath(Locatorvalue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			CustomerNum=dr.findElement(By.name(Locatorvalue)).getAttribute("value");
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			CustomerNum=dr.findElement(By.id(Locatorvalue)).getAttribute("value");
		}
		FileWriter fw=new FileWriter("./CaptureData/customerNumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(CustomerNum);
		bw.flush();
		bw.close();
	}
	public static void customerTable() throws Throwable
	{
		Thread.sleep(5000);
		FileReader fr=new FileReader("./CaptureData/customerNumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_Data=br.readLine();
		if(!dr.findElement(By.xpath(conpro.getProperty("searchTextbox"))).isDisplayed())
			dr.findElement(By.xpath(conpro.getProperty("searchPanel"))).click();
		dr.findElement(By.xpath(conpro.getProperty("searchTextbox"))).clear();
		dr.findElement(By.xpath(conpro.getProperty("searchTextbox"))).sendKeys(Exp_Data);
		Thread.sleep(2000);
		dr.findElement(By.xpath(conpro.getProperty("searchButton"))).click();
		Thread.sleep(3000);
		String Act_data=dr.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr/td[5]/div/span/span")).getText();
		Reporter.log(Exp_Data+"    "+Act_data,true);

		try {
			Assert.assertEquals(Act_data, Exp_Data,"Customer Number is Not Matching");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}

















