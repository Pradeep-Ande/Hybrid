package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileutil {
	XSSFWorkbook wb;
	//constucter for reading excel path
	public ExcelFileutil(String Excelpath)throws Throwable
	{
		FileInputStream fi=new FileInputStream(Excelpath);
		wb=new XSSFWorkbook(fi);
	}
	public int rowcount(String sheetname)
	{
		return wb.getSheet(sheetname).getLastRowNum();
	}
	public String getcelldata(String sheetname,int row,int column)
	{
		String data="";
		if (wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC) 
		{
			int celldata=(int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();	
			data=String.valueOf(celldata);
		} else {
			data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();

		}
		return data;
	}
	//Method for writing status into Another Workbook
	public void setCelldata(String sheetname,int row,int column,String status,String WriteExcel)throws Throwable
	{
		XSSFSheet ws=wb.getSheet(sheetname);
		XSSFRow rowNum=ws.getRow(row);
		XSSFCell Cell=ws.getRow(row).createCell(column);
		Cell.setCellValue(status);
		if (status.equalsIgnoreCase("Pass")) {
			XSSFCellStyle style=wb.createCellStyle();
			XSSFFont font=wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
			
		}
		else if (status.equalsIgnoreCase("Fail"))
		{
			XSSFCellStyle style=wb.createCellStyle();
			XSSFFont font=wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("Blocked"))
		{
			XSSFCellStyle style=wb.createCellStyle();
			XSSFFont font=wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		FileOutputStream fo=new FileOutputStream(WriteExcel);
		wb.write(fo);
	}
	

}
