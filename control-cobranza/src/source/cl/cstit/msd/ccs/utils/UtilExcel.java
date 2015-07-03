package cl.cstit.msd.ccs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cl.cstit.msd.ccs.vo.FieldExcel;

public class UtilExcel {

	
	public static List<FieldExcel> readExcel(File file){
    	
		List<FieldExcel> listExcel = new ArrayList<FieldExcel>();

    	try
        {
            FileInputStream fileInputStream = new FileInputStream(file);
 
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
 
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            rowIterator.next();
            while (rowIterator.hasNext()) 
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                
                FieldExcel fieldExcel = new FieldExcel();
                
                //Guardamos en el objeto
                fieldExcel.setLinea(row.getRowNum() + 1);
                
                
                fieldExcel.setHubCode(valueField(row.getCell(0)));
                fieldExcel.setDescriptionProduct(valueField(row.getCell(2)));

                fieldExcel.setJan(valueField(row.getCell(3)));
                fieldExcel.setFeb(valueField(row.getCell(4)));
                fieldExcel.setMar(valueField(row.getCell(5)));
                fieldExcel.setApr(valueField(row.getCell(6)));
                fieldExcel.setMay(valueField(row.getCell(7)));
                fieldExcel.setJun(valueField(row.getCell(8)));
                fieldExcel.setJul(valueField(row.getCell(9)));
                fieldExcel.setAug(valueField(row.getCell(10)));
                fieldExcel.setSep(valueField(row.getCell(11)));
                fieldExcel.setOct(valueField(row.getCell(12)));
                fieldExcel.setNov(valueField(row.getCell(13)));
                fieldExcel.setDec(valueField(row.getCell(14)));
                
                
                
                listExcel.add(fieldExcel);
            }
            
            fileInputStream.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return listExcel;
        }
        
        return listExcel;
	}
	
	public static XSSFWorkbook writtenExcel(List<FieldExcel> listExcel){
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Import");
        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[] {"HUB Code","Locale Code", "Description Product", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        int cont = 1;
        for (FieldExcel fieldExcel : listExcel) {
        	cont++;
        	data.put(""+cont, new Object[] {fieldExcel.getHubCode(),fieldExcel.getLocalCode(), fieldExcel.getDescriptionProduct(), 
        									Integer.parseInt(fieldExcel.getJan()), Integer.parseInt(fieldExcel.getFeb()), Integer.parseInt(fieldExcel.getMar()), 
						        			Integer.parseInt(fieldExcel.getApr()), Integer.parseInt(fieldExcel.getMay()), Integer.parseInt(fieldExcel.getJun()), 
						        			Integer.parseInt(fieldExcel.getJul()), Integer.parseInt(fieldExcel.getAug()), Integer.parseInt(fieldExcel.getSep()), 
						        			Integer.parseInt(fieldExcel.getOct()), Integer.parseInt(fieldExcel.getNov()), Integer.parseInt(fieldExcel.getDec())});
		}
        
        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        
        sheet.autoSizeColumn((short)0); 
        sheet.autoSizeColumn((short)1); 
        sheet.autoSizeColumn((short)2);
        sheet.autoSizeColumn((short)3); 
        sheet.autoSizeColumn((short)4);
        sheet.autoSizeColumn((short)5); 
        sheet.autoSizeColumn((short)6);
        sheet.autoSizeColumn((short)7); 
        sheet.autoSizeColumn((short)8);
        sheet.autoSizeColumn((short)9); 
        sheet.autoSizeColumn((short)10);
        sheet.autoSizeColumn((short)11); 
        sheet.autoSizeColumn((short)12);
        sheet.autoSizeColumn((short)13); 
        return workbook;
	}
	
	
	public static XSSFWorkbook writtenExcelResultCost(){
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook(); 
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Import");
        //This data needs to be written (Object[])
//        Map<String, Object[]> data = new TreeMap<String, Object[]>();
//        data.put("1", new Object[] {"HUB Code","Locale Code", "Description Product", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
//        int cont = 1;
//        for (FieldExcel fieldExcel : listExcel) {
//        	cont++;
//        	data.put(""+cont, new Object[] {fieldExcel.getHubCode(),fieldExcel.getLocalCode(), fieldExcel.getDescriptionProduct(), 
//        									Integer.parseInt(fieldExcel.getJan()), Integer.parseInt(fieldExcel.getFeb()), Integer.parseInt(fieldExcel.getMar()), 
//						        			Integer.parseInt(fieldExcel.getApr()), Integer.parseInt(fieldExcel.getMay()), Integer.parseInt(fieldExcel.getJun()), 
//						        			Integer.parseInt(fieldExcel.getJul()), Integer.parseInt(fieldExcel.getAug()), Integer.parseInt(fieldExcel.getSep()), 
//						        			Integer.parseInt(fieldExcel.getOct()), Integer.parseInt(fieldExcel.getNov()), Integer.parseInt(fieldExcel.getDec())});
//		}
        
        //Iterate over data and write to sheet
//        Set<String> keyset = data.keySet();
//        int rownum = 0;
//        for (String key : keyset)
//        {
//            Row row = sheet.createRow(rownum++);
//            Object [] objArr = data.get(key);
//            int cellnum = 0;
//            for (Object obj : objArr)
//            {
//               Cell cell = row.createCell(cellnum++);
//               if(obj instanceof String)
//                    cell.setCellValue((String)obj);
//                else if(obj instanceof Integer)
//                    cell.setCellValue((Integer)obj);
//            }
//        }
        
//        sheet.autoSizeColumn((short)0); 
//        sheet.autoSizeColumn((short)1); 
//        sheet.autoSizeColumn((short)2);
//        sheet.autoSizeColumn((short)3); 
//        sheet.autoSizeColumn((short)4);
//        sheet.autoSizeColumn((short)5); 
//        sheet.autoSizeColumn((short)6);
//        sheet.autoSizeColumn((short)7); 
//        sheet.autoSizeColumn((short)8);
//        sheet.autoSizeColumn((short)9); 
//        sheet.autoSizeColumn((short)10);
//        sheet.autoSizeColumn((short)11); 
//        sheet.autoSizeColumn((short)12);
//        sheet.autoSizeColumn((short)13); 
        return workbook;
        
	}
	
	
	
	private static String valueField(Cell cell){
		String value = "";
		
		if (cell == null)
			value="0";
		else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
        	value = String.valueOf((int)cell.getNumericCellValue());
        else
        	value = cell.getStringCellValue().trim().equals("")?"0":cell.getStringCellValue();
		
        return value;
	}
	
}
