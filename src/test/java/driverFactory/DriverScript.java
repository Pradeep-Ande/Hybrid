package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileutil;


public class DriverScript {
	WebDriver dr;
	String inputpath ="./FileInput/Controller.xlsx";
	String outputpath ="./Fileoutput/HybriedResults.xlsx";
	String TCSheet="MasterTestCases";
	ExtentReports reports;
	ExtentTest logger;
	public void startTest() throws Throwable
	{
		String Module_Status="";
		String Module_New="";
		ExcelFileutil xl=new ExcelFileutil(inputpath);
		for(int i=1;i<=xl.rowcount(TCSheet);i++)
		{
			if(xl.getcelldata(TCSheet, i, 2).equalsIgnoreCase("Y"))
			{
				String TCModule=xl.getcelldata(TCSheet, i, 1);
				reports=new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generaateDate()+".html");
				logger=reports.startTest(TCModule);
				for(int j=1;j<=xl.rowcount(TCModule);j++)
				{
					String Description=xl.getcelldata(TCModule, j, 0);

					String Object_Type
					=xl.getcelldata(TCModule, j, 1);
					String Locator_Type
					=xl.getcelldata(TCModule, j, 2);
					String Locator_Value
					=xl.getcelldata(TCModule, j, 3);
					String Test_Data
					=xl.getcelldata(TCModule, j, 4);
					try {
						if(Object_Type.equalsIgnoreCase("startBrowser"))
						{
							dr=FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openUrl();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("waitforElement"))
						{
							FunctionLibrary.waitforElement(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("captureStock"))
						{
							FunctionLibrary.captureStock(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("stocktable"))
						{
							FunctionLibrary.stockTable();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("captureSupplier"))
						{
							FunctionLibrary.captureSupplier(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("captureCustomer"))
						{
							FunctionLibrary.captureCustomer(Locator_Type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("customerTable"))
						{
							FunctionLibrary.customerTable();
							logger.log(LogStatus.INFO, Description);
						}
						xl.setCelldata(TCModule, j, 5, "Pass", outputpath);
						logger.log(LogStatus.PASS, Description);
						Module_Status="true";
					} 
					catch (Exception e) 
					{
						System.out.println(e.getMessage());
						xl.setCelldata(TCModule, j, 5, "Fail", outputpath);
						logger.log(LogStatus.FAIL, Description);
						Module_New="false";
						File screen=((TakesScreenshot)dr).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(screen, new File("./target/Reports/Screenshot/"+Description+FunctionLibrary.generaateDate()+".png"));
						
					} 
					if(Module_Status.equalsIgnoreCase("true"))
					{
						xl.setCelldata(TCSheet, i, 3, "Pass", outputpath);
					}
					reports.endTest(logger);
					reports.flush();				
				}
				if(Module_New.equalsIgnoreCase("False"))
				{
					xl.setCelldata(TCSheet, i, 3, "Fail", outputpath);
				}
				
				}
			else {
				xl.setCelldata(TCSheet, i, 3, "Blocked", outputpath);
			}
		}
	}

}
