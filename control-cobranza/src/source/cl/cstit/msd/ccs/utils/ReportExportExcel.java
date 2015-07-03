package cl.cstit.msd.ccs.utils;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cl.cstit.msd.ccs.vo.MonthlyDataVO;
import cl.cstit.msd.ccs.vo.ProductivityVO;
import cl.cstit.msd.ccs.vo.StandardDetailVO;
import cl.cstit.msd.ccs.vo.SummaryVO;
import cl.cstit.msd.ccs.vo.UnitCostProductVO;

public class ReportExportExcel {
	
	XSSFWorkbook workbook = null;
	
	public ReportExportExcel(){
		workbook = new XSSFWorkbook();
	}
	
	public void writeStandardDetail(List<StandardDetailVO> listStandardDetailVO){
        //Blank workbook
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Standard Detail");
        
    	int cont=0;
        Row row = sheet.createRow((short)0);
        
        row.createCell(cont++).setCellValue("Cost Type");
        row.createCell(cont++).setCellValue("Cost Sub Type");
        row.createCell(cont++).setCellValue("Cost Detail");
        row.createCell(cont++).setCellValue("New Stage");
        row.createCell(cont++).setCellValue("Base Stage");
        row.createCell(cont++).setCellValue("Var Nominal");
        row.createCell(cont++).setCellValue("DTE");
        row.createCell(cont++).setCellValue("EX-EXCHANGE");
        row.createCell(cont++).setCellValue("%");
        
        
        for (int i=0; i<listStandardDetailVO.size(); i++) {

        	cont = 0;
            row = sheet.createRow((short)i+1);

            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getTypeCostStandarDetail());
            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getSubTypeCostStandarDetail());
            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getNameCostStandarDetail());
            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getAmountStageBStandarDetail());
            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getAmountStageAStandarDetail());
            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getNominalAmoutStandarDetail());
            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getDteAmoutStandarDetail());
            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getExchangeAmoutStandarDetail());
            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getPercentAmoutStandarDetail());
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
        
	}
	
	public void writeImpactAnalysis(List<StandardDetailVO> listStandardDetailVO){
        //Blank workbook
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Impact Analysis");
        
    	int cont=0;
        Row row = sheet.createRow((short)0);
        
        row.createCell(cont++).setCellValue("Cost Type");
        row.createCell(cont++).setCellValue("Cost Sub Type");
        row.createCell(cont++).setCellValue("Cost Detail");
        row.createCell(cont++).setCellValue("New Stage");
        row.createCell(cont++).setCellValue("Base Stage");
        row.createCell(cont++).setCellValue("Impact");
        
        
        for (int i=0; i<listStandardDetailVO.size(); i++) {

        	cont = 0;
            row = sheet.createRow((short)i+1);

            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getTypeCostStandarDetail());
            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getSubTypeCostStandarDetail());
            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getNameCostStandarDetail());
            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getAmountStageBStandarDetail());
            
            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getAmountStageAStandarDetail());
            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getAmountStageBStandarDetail() - listStandardDetailVO.get(i).getAmountStageAStandarDetail());
//            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getNominalAmoutStandarDetail());
//            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getDteAmoutStandarDetail());
//            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getExchangeAmoutStandarDetail());
//            row.createCell(cont++).setCellValue(listStandardDetailVO.get(i).getPercentAmoutStandarDetail());
        }
        
        sheet.autoSizeColumn((short)0); 
        sheet.autoSizeColumn((short)1); 
        sheet.autoSizeColumn((short)2);
        sheet.autoSizeColumn((short)3); 
        sheet.autoSizeColumn((short)4);
        sheet.autoSizeColumn((short)5); 
        
	}
	
	
	public void writeProductivity(List<ProductivityVO> listProductivityVO){
        //Blank workbook
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Productivity");
        
    	int cont=0;
        Row row = sheet.createRow((short)0);
        
        row.createCell(cont++).setCellValue("Local Code");
        row.createCell(cont++).setCellValue("Description");
        row.createCell(cont++).setCellValue("Units New Stage");
        row.createCell(cont++).setCellValue("Units Base Stage");
        row.createCell(cont++).setCellValue("New Stage (LC) Duties");
        row.createCell(cont++).setCellValue("New Stage (LC) Freight & Others");
        row.createCell(cont++).setCellValue("Base Stage (LC) Duties");
        row.createCell(cont++).setCellValue("Base Stage (LC) Freight & Others");
        row.createCell(cont++).setCellValue("Inflation");
        row.createCell(cont++).setCellValue("Exchange");
        row.createCell(cont++).setCellValue("Productivity in (US) Total Duties");
        row.createCell(cont++).setCellValue("Productivity in (US) Total Freight & Others");
        row.createCell(cont++).setCellValue("Productivity in (US) Total Local Add");
        
        
        for (int i=0; i<listProductivityVO.size(); i++) {

        	cont = 0;
            row = sheet.createRow((short)i+1);

            row.createCell(cont++).setCellValue(listProductivityVO.get(i).getIdProductProductivity());
            row.createCell(cont++).setCellValue(listProductivityVO.get(i).getDescProductProductivity());
            row.createCell(cont++).setCellValue(listProductivityVO.get(i).getUnitStageBProductivity());
            row.createCell(cont++).setCellValue(listProductivityVO.get(i).getUnitStageAProductivity());            
            row.createCell(cont++).setCellValue(listProductivityVO.get(i).getDutiesStageBProductivity());
            row.createCell(cont++).setCellValue(listProductivityVO.get(i).getFreightStageBProductivity()+listProductivityVO.get(i).getOtherStageBProductivity());
            row.createCell(cont++).setCellValue(listProductivityVO.get(i).getDutiesStageAProductivity());
            row.createCell(cont++).setCellValue(listProductivityVO.get(i).getFreightStageAProductivity()+listProductivityVO.get(i).getOtherStageAProductivity());
            row.createCell(cont++).setCellValue(listProductivityVO.get(i).getInflationProductivity());
            row.createCell(cont++).setCellValue(listProductivityVO.get(i).getExchangeProductivity());
            row.createCell(cont++).setCellValue(listProductivityVO.get(i).getTotalDutiesProductivity());
            row.createCell(cont++).setCellValue(listProductivityVO.get(i).getTotalFreightProductivity());
            row.createCell(cont++).setCellValue(listProductivityVO.get(i).getTotalLocalAddProductivity());
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
	}
	
	public void writeSummaryUnits(List<SummaryVO> listSummaryVO){
        //Blank workbook
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Summary - Units");
        
    	int cont=0;
        Row row = sheet.createRow((short)0);
        
        row.createCell(cont++).setCellValue("Month");
        row.createCell(cont++).setCellValue("New Stage");
        row.createCell(cont++).setCellValue("Base Stage");
        row.createCell(cont++).setCellValue("Variance");
        row.createCell(cont++).setCellValue("Year to Date");
        row.createCell(cont++).setCellValue("%");
        float yearToDateUnitSummary = 0;
		float percentUnitSummary    = 0;
        
        
        for (int i=0; i<listSummaryVO.size(); i++) {

        	cont = 0;
        	yearToDateUnitSummary=yearToDateUnitSummary+listSummaryVO.get(i).getByMonthUnitSummary();
        	percentUnitSummary=(listSummaryVO.get(i).getAmountUnitStageBSummary()-listSummaryVO.get(i).getAmountUnitStageASummary())*100/listSummaryVO.get(i).getAmountUnitStageASummary();
            row = sheet.createRow((short)i+1);
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getMonthSummary());
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getAmountUnitStageBSummary());
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getAmountUnitStageASummary());
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getByMonthUnitSummary());
            row.createCell(cont++).setCellValue(yearToDateUnitSummary);            
            row.createCell(cont++).setCellValue(percentUnitSummary);
           
        }
        
        sheet.autoSizeColumn((short)0); 
        sheet.autoSizeColumn((short)1); 
        sheet.autoSizeColumn((short)2);
        sheet.autoSizeColumn((short)3); 
        sheet.autoSizeColumn((short)4);
        sheet.autoSizeColumn((short)5);
      
	}
	
	public void writeSummaryTrnPrice(List<SummaryVO> listSummaryVO){
        //Blank workbook
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Summary - Trn. Price");
        
    	int cont=0;
        Row row = sheet.createRow((short)0);
        
        row.createCell(cont++).setCellValue("Month");
        row.createCell(cont++).setCellValue("New Stage");
        row.createCell(cont++).setCellValue("Base Stage");
        row.createCell(cont++).setCellValue("Variance");
        row.createCell(cont++).setCellValue("Year to Date");
        row.createCell(cont++).setCellValue("%");
        float yearToDateUnitSummary = 0;
		float percentUnitSummary    = 0;
        
        
        for (int i=0; i<listSummaryVO.size(); i++) {

        	cont = 0;
        	yearToDateUnitSummary=yearToDateUnitSummary+listSummaryVO.get(i).getByMonthFobsSummary();
        	percentUnitSummary=(listSummaryVO.get(i).getAmountFobsStageBSummary()-listSummaryVO.get(i).getAmountFobsStageASummary())*100/listSummaryVO.get(i).getAmountFobsStageASummary();
            row = sheet.createRow((short)i+1);
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getMonthSummary());
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getAmountFobsStageBSummary());
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getAmountFobsStageASummary());
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getByMonthFobsSummary());
            row.createCell(cont++).setCellValue(yearToDateUnitSummary);
            row.createCell(cont++).setCellValue(percentUnitSummary);
           
        }
        
        sheet.autoSizeColumn((short)0); 
        sheet.autoSizeColumn((short)1); 
        sheet.autoSizeColumn((short)2);
        sheet.autoSizeColumn((short)3); 
        sheet.autoSizeColumn((short)4);
        sheet.autoSizeColumn((short)5);
      
	}
	
	public void writeSummaryDuties(List<SummaryVO> listSummaryVO){
        //Blank workbook
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Summary - Duties");
        
    	int cont=0;
        Row row = sheet.createRow((short)0);
        
        row.createCell(cont++).setCellValue("Month");
        row.createCell(cont++).setCellValue("New Stage");
        row.createCell(cont++).setCellValue("Base Stage");
        row.createCell(cont++).setCellValue("Variance");
        row.createCell(cont++).setCellValue("Year to Date");
        row.createCell(cont++).setCellValue("DTE");
        row.createCell(cont++).setCellValue("EX Exchange");
        row.createCell(cont++).setCellValue("%");
        float yearToDateDutiesSummary = 0;
		float percentUnitSummary    = 0;
        
        
        for (int i=0; i<listSummaryVO.size(); i++) {

        	cont = 0;
        	yearToDateDutiesSummary=yearToDateDutiesSummary+listSummaryVO.get(i).getByMonthDutiesSummary();
        	percentUnitSummary=(listSummaryVO.get(i).getAmountDutiesStageBSummary()-listSummaryVO.get(i).getAmountDutiesStageASummary())*100/listSummaryVO.get(i).getAmountDutiesStageASummary();        	
            row = sheet.createRow((short)i+1);
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getMonthSummary());
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getAmountDutiesStageBSummary());
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getAmountDutiesStageASummary());
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getByMonthDutiesSummary());
            row.createCell(cont++).setCellValue(yearToDateDutiesSummary);
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getDteDutiesSummary());
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getByMonthDutiesSummary()-listSummaryVO.get(i).getDteDutiesSummary());
            row.createCell(cont++).setCellValue(percentUnitSummary);
            
           
        }
        
        sheet.autoSizeColumn((short)0); 
        sheet.autoSizeColumn((short)1); 
        sheet.autoSizeColumn((short)2);
        sheet.autoSizeColumn((short)3); 
        sheet.autoSizeColumn((short)4);
        sheet.autoSizeColumn((short)5);
        sheet.autoSizeColumn((short)6);
        sheet.autoSizeColumn((short)7);
        
      
	}
	
	public void writeSummaryFreight(List<SummaryVO> listSummaryVO){
        //Blank workbook
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Summary - Freight & Others");
        
    	int cont=0;
        Row row = sheet.createRow((short)0);
        
        row.createCell(cont++).setCellValue("Month");
        row.createCell(cont++).setCellValue("New Stage");
        row.createCell(cont++).setCellValue("Base Stage");
        row.createCell(cont++).setCellValue("Variance");
        row.createCell(cont++).setCellValue("Year to Date");
        row.createCell(cont++).setCellValue("DTE");
        row.createCell(cont++).setCellValue("EX Exchange");
        row.createCell(cont++).setCellValue("%");
        float yearToDateFreightsSummary = 0;
		float percentFreightsSummary    = 0;
		float freightOthersStageA = 0;
		float freightOthersStageB = 0;		
		float freightOthersByMonth = 0;
        
        for (int i=0; i<listSummaryVO.size(); i++) {

        	cont = 0;
        	freightOthersByMonth=listSummaryVO.get(i).getByMonthFreightsSummary()+listSummaryVO.get(i).getByMonthOthersSummary();
        	freightOthersStageA=listSummaryVO.get(i).getAmountFreightsStageASummary()+listSummaryVO.get(i).getAmountOthersStageASummary();
        	freightOthersStageB=listSummaryVO.get(i).getAmountFreightsStageBSummary()+listSummaryVO.get(i).getAmountOthersStageBSummary();
        	yearToDateFreightsSummary=yearToDateFreightsSummary+freightOthersByMonth;
        	percentFreightsSummary = listSummaryVO.get(i).getAmountFreightsStageASummary() / (freightOthersByMonth - listSummaryVO.get(i).getDteFreightsSummary());     	
            row = sheet.createRow((short)i+1);
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getMonthSummary());
            row.createCell(cont++).setCellValue(freightOthersStageB);
            row.createCell(cont++).setCellValue(freightOthersStageA);
            row.createCell(cont++).setCellValue(freightOthersByMonth);
            row.createCell(cont++).setCellValue(yearToDateFreightsSummary);
            row.createCell(cont++).setCellValue(listSummaryVO.get(i).getDteFreightsSummary());
            row.createCell(cont++).setCellValue(freightOthersByMonth-listSummaryVO.get(i).getDteFreightsSummary());
    		row.createCell(cont++).setCellValue(percentFreightsSummary);
           
        }
        
        sheet.autoSizeColumn((short)0); 
        sheet.autoSizeColumn((short)1); 
        sheet.autoSizeColumn((short)2);
        sheet.autoSizeColumn((short)3); 
        sheet.autoSizeColumn((short)4);
        sheet.autoSizeColumn((short)5);
        sheet.autoSizeColumn((short)6);
        sheet.autoSizeColumn((short)7);
        
      
	}
	
	public void writeMonthlyData(List<MonthlyDataVO> listMonthlyDataVO){
        //Blank workbook
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Monthly Data");
        
    	int cont=0;
        Row row = sheet.createRow((short)0);
        
        row.createCell(cont++).setCellValue("Local Code");
        row.createCell(cont++).setCellValue("Description");
        row.createCell(cont++).setCellValue("Units New Stage");
        row.createCell(cont++).setCellValue("Units Base Stage");
        row.createCell(cont++).setCellValue("Units Variance");
        row.createCell(cont++).setCellValue("Trn New Stage");
        row.createCell(cont++).setCellValue("Trn Base Stage");
        row.createCell(cont++).setCellValue("Trn Variance");
        row.createCell(cont++).setCellValue("Duties New Stage");
        row.createCell(cont++).setCellValue("Duties Base Stage");
        row.createCell(cont++).setCellValue("Duties Variance");
        row.createCell(cont++).setCellValue("Duties DTE");
        row.createCell(cont++).setCellValue("Duties EX Exchange");
        row.createCell(cont++).setCellValue("Duties %");
        row.createCell(cont++).setCellValue("Freight New Stage");
        row.createCell(cont++).setCellValue("Freight Base Stage");
        row.createCell(cont++).setCellValue("Freight Variance");
        row.createCell(cont++).setCellValue("Freight DTE");
        row.createCell(cont++).setCellValue("Freight EX Exchange");
        row.createCell(cont++).setCellValue("Freight %");
        
        
        for (int i=0; i<listMonthlyDataVO.size(); i++) {

        	cont = 0;
            row = sheet.createRow((short)i+1);

            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getIdProductMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getDescProductMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getStageBUnitMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getStageAUnitMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getUnitVarianceMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getStageBFOBsMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getStageAFOBsMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getfOBsVarianceMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getStageBDutiesMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getStageADutiesMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getDutiesVarianceMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getDteDutiesMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getExchangeDutiesMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getExchangeDutiesMonthly()/listMonthlyDataVO.get(i).getStageADutiesMonthly()*100);
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getStageBFreightMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getStageAFreightMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getFreightVarianceMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getDteFreightMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getExchangeFreightMonthly());
            row.createCell(cont++).setCellValue(listMonthlyDataVO.get(i).getExchangeFreightMonthly()/listMonthlyDataVO.get(i).getStageAFreightMonthly()*100);
            
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
        sheet.autoSizeColumn((short)14);
        sheet.autoSizeColumn((short)15);
        sheet.autoSizeColumn((short)16);
        sheet.autoSizeColumn((short)17);
        sheet.autoSizeColumn((short)18);
        sheet.autoSizeColumn((short)19);
        
	}
	
	public void writeUnitCost(List<UnitCostProductVO> listUnitCostProductVO){
        //Blank workbook
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Unit Cost");
        
    	int cont=0;
        Row row = sheet.createRow((short)0);
        
        
        row.createCell(cont++).setCellValue("Local code");//mover al 1
        row.createCell(cont++).setCellValue("Hub Code");//mover al 2
        row.createCell(cont++).setCellValue("Product Description");
        row.createCell(cont++).setCellValue("Local Transport");//mover a 3
        row.createCell(cont++).setCellValue("Temporal Warehousing");//mover a 4
        row.createCell(cont++).setCellValue("IVA Import 1=yes");//mover a 5
        row.createCell(cont++).setCellValue("Presentation");//mover a 6
        row.createCell(cont++).setCellValue("Busines");//mover a 7
        row.createCell(cont++).setCellValue("Shipment Type");//mover a 8
        
        row.createCell(cont++).setCellValue("Transp Mode");//mover 9
        row.createCell(cont++).setCellValue("UNITS COST 2015 USD Fob");//mover 10
        row.createCell(cont++).setCellValue("UNITS COST 2015 USD Duties");// 11
        row.createCell(cont++).setCellValue("UNITS COST 2015 USD Freight");// 12
        row.createCell(cont++).setCellValue("UNITS COST 2015 USD Other");// 13
        row.createCell(cont++).setCellValue("UNITS COST 2015 LC Fob");//-----------------------------------ingresar
        row.createCell(cont++).setCellValue("UNITS COST 2015 LC Duties");// 14
        row.createCell(cont++).setCellValue("UNITS COST 2015 LC Freight");// 15
        row.createCell(cont++).setCellValue("UNITS COST 2015 LC Other");// 16     
     
        
        for (int i=0; i<listUnitCostProductVO.size(); i++) {

        	cont = 0;
            row = sheet.createRow((short)i+1);
            
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getLocalCodeUnitCostProduct());//mover a 1
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getHubCodeUnitCostProduct());//mover a 2
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getProductDescriptionUnitCostProduct());
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getLocalTransportUnitCostProduct());//mover a 3
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getTemporalWarehousingUnitCostProduct());//mover a 4
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getIvaImportsUnitCostProduct());//mover a 5
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getPresentationUnitCostProduct());//mover a 6
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getBusinesUnitCostProduct());//mover a 7
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getShipmentTypeUnitCostProduct());//mover a 8
            
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getTranspModeUnitCostProduct());//mover 9
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getFobUnitCostProduct());//mover a 10
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getDutiesUnitCostProduct());// 11
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getFreightUnitCostProduct());// 12
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getOtherUnitCostProduct());// 13
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getFobUnitCostLCProduct());//------------------------ ingresar
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getDutiesUnitCostLCProduct());// 14
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getFreightUnitCostLCProduct());// 15
            row.createCell(cont++).setCellValue(listUnitCostProductVO.get(i).getOtherUnitCostLCProduct());// 16
           
            
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
        sheet.autoSizeColumn((short)14);
        sheet.autoSizeColumn((short)15);
        sheet.autoSizeColumn((short)16);
        sheet.autoSizeColumn((short)17);
        
        
	}
	
	public XSSFWorkbook getXSSFWorkbook(){
		return workbook;
	}
	
	
	
}
