/**
 * 
 */
package test_Suite.utils.ui;

import static watij.finders.SymbolFactory.*;
import static watij.finders.FinderFactory.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.utils.cases.GeneralUtil;
import watij.elements.Div;
import watij.elements.Link;
import watij.elements.Links;
import watij.elements.Span;
import watij.elements.Spans;
import watij.elements.Table;
import watij.elements.TableBody;
import watij.elements.TableCell;
import watij.elements.TableRow;
import watij.elements.TableRows;
import watij.elements.Tables;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 *
 */
public class TablesUtil {
	private static Log log = LogFactory.getLog(TablesUtil.class);
	
	public static Div tableDiv() throws Exception {
		
		IE ie = IEUtil.getActiveIE();

		Div div = ie.div(attribute("class", ITablesConst.tableListDiv_class));
		return div;
	}
	
	public static Div widgetDiv() throws Exception {
		
		IE ie = IEUtil.getActiveIE();

		Div div = ie.div(attribute("class", ITablesConst.widgetDiv_class));
		return div;
	}

	public static boolean isTableExists(String tableId) throws Exception {
	
		if (!tableDiv().exists()){
			
			log.error("Div containing list cannot be found!");
			return false;
		}
		
		return tableDiv().htmlElement(id, tableId).exists();
	}
	
//	public static boolean isTableExists(String tableId) throws Exception {
//
//		IE ie = IEUtil.getActiveIE();

//		return ie.table(id, tableId).exists();
//	}

//	UNUSED: 
//	public static boolean isTableBodyExists(String tableId, String tBodyId) throws Exception
//	{
//		IE ie = IEUtil.getActiveIE();
//
//		if ( !isTableExists(tableId) )
//		{
//			log.error("Could not find table (" + tableId + ")!");
//			return false;
//		}
//
//		if ( ! ie.table(id, tableId).body(id, tBodyId).exists() )
//		{
//			log.error("Could not find table body (" + tBodyId + ")!");
//			return false;
//		}
//
//		return true;
//	}	
	
	public static int getRowIndexInFOSubmissionWithExactMatch(String toFind) throws Exception {

		int rIndex = -1;

		if(!tableDiv().body(id,ITablesConst.fOSubmissionsTableBodyId).exists()){
			
			log.error("Could not find FO Submissions list!");
			return rIndex;
		}

		TableRows tableRows = tableDiv().body(id,ITablesConst.fOSubmissionsTableBodyId).rows();	

		for (TableRow tRow : tableRows) {

			rIndex++;

			if(tRow.cell(1).div(0).span(0).innerText().equalsIgnoreCase(toFind) || tRow.cell(2).div(0).span(1).innerText().equalsIgnoreCase(toFind)){
				
				return rIndex;
			}			
		}
		return -1;
	}
	
//	not used:
//	public static TableRow getRowByLabelWithoutPagination(String tableId, String lbl) throws Exception
//	{
//		IE ie = IEUtil.getActiveIE();
//
//		if ( !isTableExists(tableId) )
//		{
//			log.error("Could not find table (" + tableId + ")!");
//			return null;
//		}
//
//		TableRows rows = ie.table(id, tableId).rows();
//		if ( null == rows )
//		{
//			log.error("Could not access rows for table (" + tableId + ")!");
//			return null;
//		}
//
//		for (TableRow tableRow : rows)
//		{
//			if ( tableRow.innerText().startsWith(lbl) )
//			{
//				return tableRow;
//			}
//		}
//
//		log.error("Could not find row for label (" + lbl + ")!");
//		return null;
//	}

	public static TableRow getRowByLabel(String tBodyId, String lbl) throws Exception
	{
		if ( !isTableExists(tBodyId) ) {
			
			log.error("Could not find table body (" + tBodyId + ")!");
			return null;
		}

		int rowIndex = -1;

		rowIndex = getRowIndexInTableWithPaginator(tBodyId, lbl);

		if ( rowIndex != -1 ) {
			
			return tableDiv().body(id, tBodyId).row(rowIndex);
		}
		log.error("Could not find row for label (" + lbl + ")!");
		return null;
	}
	
	
	public static String getTextFieldIdByLabel_New(String tBodyId, String lbl) throws Exception {

		if ( !isTableExists(tBodyId) ) {
			
			log.error("Could not find table (" + tBodyId + ")!");
			return "";
		}

		int rowIndex = -1;

		rowIndex = getRowIndex(tBodyId,lbl);

		log.debug("Row Index: " + rowIndex);

		if ( rowIndex > -1 ) {
			
			log.debug(tableDiv().body(id,tBodyId).row(rowIndex).textField(0).id());

			return tableDiv().body(id, tBodyId).row(rowIndex).textField(0).id();
		}

		log.error("Could not find text field for label (" + lbl + ")!");
		return "";
	}
	
//  UNUSED:	
//	public static String getTextFieldIdByLabel(String tableId, String lbl) throws Exception
//	{
//		IE ie = IEUtil.getActiveIE();
//
//		if ( !isTableExists(tableId) )
//		{
//			log.error("Could not find table (" + tableId + ")!");
//			return null;
//		}
//
//		int rowIndex = -1;
//
//		rowIndex = getRowIndexInTableWithPaginator(tableId, lbl);
//
//		log.debug("Row Index: " + rowIndex);
//
//		if ( rowIndex > -1 )
//		{
//			log.debug(ie.table(id,tableId).row(rowIndex).textField(0).id());
//
//			return ie.table(id,tableId).row(rowIndex).textField(0).id();
//		}
//
//		log.error("Could not find text field for label (" + lbl + ")!");
//		return "";
//	}

//  UNUSED:	
//	public static String getRowIdByLabel(String tableId, String lbl) throws Exception
//	{
//		IE ie = IEUtil.getActiveIE();
//
//		if ( !isTableExists(tableId) )
//		{
//			log.error("Could not find table (" + tableId + ")!");
//			return null;
//		}
//
//		int rowIndex = -1;
//
//		rowIndex = getRowIndexInTableWithPaginator(tableId,lbl);
//
//		if ( rowIndex != -1 )
//		{
//			log.debug(ie.table(id, tableId).row(rowIndex).id());
//			return ie.table(id, tableId).row(rowIndex).id();
//		}
//
//		log.error("Could not find row for label (" + lbl + ")!");
//		return "";
//	}

	public static int findCellIndex(String tBodyId, String ColName) throws Exception {

		if ( !isTableExists(tBodyId) ) {
			
			log.error("Could not find table (" + tBodyId + ")!");
			return -1;
		}

		log.info("Finding CellIndex");	

		for ( int i = 0; i < tableDiv().body(id, tBodyId).columnCount(); i++ ) {
			
			if ( tableDiv().htmlElement(tag, "thead").cell(i).innerText().matches(ColName) ) {
				
				return i;
			}
		}
		log.error("Could not find cell index for column (" + ColName + ")!");
		return -1;
	}
	
	public static int findColumnIndex(String tHeaderId, String ColHeader) throws Exception {
	
		if ( !isTableExists(tHeaderId) ) {
			
			log.error("Could not find table (" + tHeaderId + ")!");
			return -1;
		}

		TableRow tRow = (TableRow) tableDiv().htmlElement(id, tHeaderId).row(0);
				
		log.info("Finding CellIndex");	

		for ( int i = 0; i < (tRow.columnCount()); i++ ) {
			
			if ( tRow.cell(i).innerText().trim().startsWith(ColHeader) ) {
				
				return i;
			}
		}
		log.error("Could not find cell index for column (" + ColHeader + ")!");
		return -1;
	}


	public static String getCellContent(String tBodyId, int rowIndex, int cellIndex, int cellDivIndex) throws Exception {

		if ( !isTableExists(tBodyId) ) {
			log.error("Could not find table (" + tBodyId + ")!");
			return null;
		}

		TableCell cell = tableDiv().body(id, tBodyId).row(rowIndex).cell(cellIndex);
		if ( null == cell ) {
			log.error("Could not access cell from table (" + tBodyId + ")!");
			return null;
		}

		if ( cell.spans().span(cellDivIndex).exists() ) {
			return cell.spans().span(cellDivIndex).innerText().trim();
		}

		return cell.innerText().trim();
	}

	public static TableBody getTableBodyById(String tBodyId) throws Exception 
	{
		if ( !isTableExists(tBodyId) ) {
			log.error("Could not find table (" + tBodyId + ")!");
			return null;
		}

		return  tableDiv().body(id, tBodyId);
	}
	
//  UNUSED:
//	public static Table getTableLetters() throws Exception
//	{
//		IE ie = IEUtil.getActiveIE();
//
//		if ( !ie.table(attribute("class", ITablesConst.tableListLetters_Class)).exists() )
//		{
//			Assert.fail("Could not find table (" + ITablesConst.tableListLetters_Class + ")!");
//		}
//
//		return ie.table(attribute("class", ITablesConst.tableListLetters_Class)); 
//	}
	
//  UNUSED:
//	public static Table getTablePaginationFooter() throws Exception
//	{
//		IE ie = IEUtil.getActiveIE();
//
//		if ( !ie.table(attribute("class", ITablesConst.tableListFooter_Class)).exists() )
//		{
//			Assert.fail("Could not find table (" + ITablesConst.tableListFooter_Class + ")!");
//		}
//
//		return ie.table(attribute("class", ITablesConst.tableListFooter_Class));
//	}

	public static Table getTableByInnerTextInSpan(String spanId, String innerTxt) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ie.span(id, spanId).exists())
		{
			log.error("Can't find the Span ".concat(spanId));

			return null;
		}

		Span span = ie.span(id, spanId);

		Tables tabls = span.tables();

		for (Table table : tabls) {

			if(table.innerText().startsWith(innerTxt))
			{
				return table;
			}			
		}

		log.error("Couldn't find table with inner text: ".concat(innerTxt));

		return null;
	}

	public static Table getTableList() throws Exception
	{
		IE ie = IEUtil.getActiveIE();

		if ( !ie.table(attribute("class", ITablesConst.tableList_Class)).exists() )
		{
			Assert.fail("Could not find table (" + ITablesConst.tableList_Class + ")!");
		}

		return ie.table(attribute("class", ITablesConst.tableList_Class));
	}

	public static TableRows getTableRows() throws Exception
	{
		Div tDiv = tableDiv();

		if ( !tDiv.exists() )
		{
			Assert.fail("Could not find tableDiv (" + tDiv.id() + ")!");
		}

		return tDiv.body(0).rows();
	}

	public static int getRowIndex(String cellEntry) throws Exception
	{
		TableRows tableRows = null;
		int rIndex = -1;
		tableRows = getTableRows();

		for ( TableRow tRow : tableRows )
		{
			rIndex++;

			for ( TableCell tCell : tRow.cells() )
			{
				if ( tCell.innerText().contains(cellEntry) )
				{
					return rIndex;
				}
			}
		}

		return -1;
	}

	public static int getRowIndexInFOSubmissionWithDiv(String projName, String stepName) throws Exception
	{

		Div tDiv = tableDiv();
		
		int rIndex = -1;

		if ( !tDiv.body(id,ITablesConst.fOSubmissionsTableId).exists() )
		{
			log.error("Could not find FO Submissions list!");
			return rIndex;
		}

		TableRows tableRows = tDiv.body(id,ITablesConst.fOSubmissionsTableId).rows();
		if ( null == tableRows )
		{
			log.error("Could not access rows for the FO Submissions list (" + projName + ")!");
			return -1;
		}

		for (TableRow tRow : tableRows)
		{
			rIndex++;

			if ( tRow.cell(1).div(0).span(0).innerText().contains(projName) && tRow.cell(2).div(0).span(1).innerText().contains(stepName) )
			{
				return rIndex;
			}
		}

		log.error("Could not find index for FO Submissions project (" + projName + ")!");
		return -1;
	}

	public static int getRowIndexInFOSubmission(String projName, String stepName) throws Exception
	{
		Div tDiv = tableDiv();

		int rIndex = -1;

		if ( !tDiv.body(id,ITablesConst.fOSubmissionsTableId).exists() )
		{
			log.error("Could not find FO Submissions list!");
			return rIndex;
		}

		TableRows tableRows = tDiv.body(id,ITablesConst.fOSubmissionsTableId).rows();
		if ( null == tableRows )
		{
			log.error("Could not access rows for the FO Submissions list (" + projName + ")!");
			return -1;
		}

		for (TableRow tRow : tableRows)
		{
			rIndex++;

			if ( tRow.cell(1).div(0).span(0).innerText().contains(projName) && tRow.cell(2).div(0).span(1).innerText().contains(stepName) )
			{
				return rIndex;
			}
		}

		log.error("Could not find row index for FO Submissions project (" + projName + ")!");
		return -1;
	}

	public static int getRowIndexWithDiv(String tBodyId, String string)
			throws Exception {

		Div tDiv = tableDiv();

		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find table (" + tBodyId + ")!");
			return -1;
		}

		int rIndex = -1;
		TableRows tableRows = null;

		tableRows = tDiv.body(id, tBodyId).rows();
		if ( null == tableRows )
		{
			log.error("Could not access rows for table (" + tBodyId + ")!");
			return -1;
		}

		for ( TableRow tRow : tableRows )
		{
			rIndex++;

			for ( TableCell tCell : tRow.cells() )
			{
				for ( Span span : tCell.spans() )
				{
					if ( span.innerText().equalsIgnoreCase(string) )
					{
						log.warn(span.innerText());
						return rIndex;
					}
				}
			}
		}

		log.error("Could not find row index for table (" + tBodyId + ")!");
		return -1;
	}

	public static int getRowIndex(String tBodyId, String string) throws Exception
	{	
		Div tDiv = tableDiv();

		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find table (" + tBodyId + ")!");
			return -1;
		}

		int rIndex = -1;
		TableRows tableRows = null;

		tableRows = tDiv.body(id, tBodyId).rows();
		if ( null == tableRows )
		{
			log.error("Could not access rows for tablebody (" + tBodyId + ")!");
			return -1;
		}

		for (TableRow tRow : tableRows)
		{
			rIndex++;

			for (TableCell tCell : tRow.cells())
			{
				if (tCell.innerText().toLowerCase().contains(string.toLowerCase()))
				{
					return rIndex;
				}
			}
		}

		log.error("Could not find row index for table (" + tBodyId + ")!");
		return -1;
	}

	public static int getRowIndexWithDivAndExactString(String tBodyId, String string) throws Exception
	{

		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find table (" + tBodyId + ")!");
			return -1;
		}

		int rIndex = -1;
		TableRows tableRows = null;
		
		Div tDiv = tableDiv();

		tableRows = tDiv.body(id, tBodyId).rows();
		if ( null == tableRows )
		{
			log.error("Could not access rows for table (" + tBodyId + ")!");
			return -1;
		}

		for ( TableRow tRow : tableRows )
		{
			rIndex++;

			for ( TableCell tCell : tRow.cells() )
			{
				{
					if ( tCell.innerText().toLowerCase().equalsIgnoreCase(string.toLowerCase()) )
					{
						return rIndex;
					}
				}
			}
		}

		log.error("Could not find row index for table (" + tBodyId + ")!");
		return -1;
	}



	public static int getRowIndexInTableWithPaginator(String tBodyId, String nameToFind) throws Exception
	{
		IE ie = IEUtil.getActiveIE();

		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find tablebody (" + tBodyId + ")!");
			return -1;
		}

		Integer pageNumber = 1;
		int rIndex = -1;

		Div divFooter = ie.div(id, ITablesConst.paginationDivId);
		
		if ( null == divFooter ) {
			log.error("Could not access footer for div!");
			return -1;
		}
		
		if ((divFooter).span(text, pageNumber.toString()).exists())	{
			(divFooter).span(text, pageNumber.toString()).click();

			GeneralUtil.takeANap(1.0);

			divFooter = ie.div(id, ITablesConst.paginationDivId);
			if ( null == divFooter )
			{
				log.error("Could not access footer for div!");
				return -1;
			}
		}

		TableRows tableRows = tableDiv().body(id, tBodyId).rows();
		do{
			if ( pageNumber > 1 ) {
				(divFooter).span(text, pageNumber.toString()).click();
				
				GeneralUtil.takeANap(1.0);
			}


			for (TableRow tRow : tableRows)	{
				rIndex++;

				for (TableCell tCell : tRow.cells()) {
					//		log.warn(tCell.innerText());

					if ( tCell.innerText().startsWith(nameToFind) ) {
						//comment out when JSF2 fixed pagination issue:
						if (ie.span(text, IClicksConst.saveBtn).exists())
							ClicksUtil.clickButtons(IClicksConst.saveBtn); 

						return rIndex;
					}
				}
			}

			pageNumber += 1;

			rIndex = -1;

		} while ((divFooter).span(text, pageNumber.toString()).exists());
		

		if ( -1 == rIndex )  log.error("Could not access tableBody (" + tBodyId + ")!");
		return rIndex;
	}

	/**
	 * @deprecated Use {@link #getRowIndex(String,String)} instead
	 */
	public static int getRowIndexFO(String tableId, String string)
			throws Exception {
		return getRowIndex(tableId, string);
	}

	public static int getRowIndexByTBody(String tBodyId, String string)	throws Exception
	{
		int rIndex = -1;
		TableRows tableRows = null;

		tableRows= getTableRows();
		if ( null == tableRows )
		{
			log.error("Could not access rows for table body (" + tBodyId + ")!");
			return -1;
		}

		for ( TableRow tRow : tableRows )
		{
			rIndex++;

			for ( TableCell tCell : tRow.cells() )
			{
				if ( tCell.innerText().contains(string) )
				{
					return rIndex;
				}
			}
		}

		log.error("Could not find row index for table body (" + tBodyId + ")!");
		return -1;
	}

	public static int findHowManyInTable(String tBodyId, String stringToFind)
			throws Exception {

		Div tDiv = tableDiv();
		
		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find table (" + tBodyId + ")!");
			return -1;
		}

		int retInt = 0;
		TableRows tableRows = null;

		tableRows = tDiv.body(id, tBodyId).rows();
		if ( null == tableRows )
		{
			log.error("Could not access rows for table (" + tBodyId + ")!");
			return -1;
		}

		for ( TableRow tRow : tableRows )
		{
			for ( TableCell tCell : tRow.cells() )
			{
				if ( tCell.innerText().contains(stringToFind) )
				{
					retInt++;
				}
			}
		}

		return retInt;
	}

	public static int testHowManyEntriesInAttachmentList(String tableId) throws Exception
	{
		IE ie = IEUtil.getActiveIE();

		if ( !isTableExists(tableId) )
		{
			log.error("Could not find table (" + tableId + ")!");
			return -1;
		}

		TableBody body = ie.body(id, tableId);
		
		if ( null == body )
		{
			log.error("Could not access table (" + tableId + ")!");
			return -1;
		}
		
		body = ie.body(id, ILBFunctionConst.lbf_AttachmentList_Table_Id);

//		if ( ie.body(id, ILBFunctionConst.lbf_AttachmentList_Table_Id).exists() )
//		{
//			body = ie.body(id, ILBFunctionConst.lbf_AttachmentList_Table_Id);
//			if ( null == body )
//			{
//				log.error("Could not access table (" + ILBFunctionConst.lbf_AttachmentList_Table_Id + ")!");
//				return -1;
//			}
//		}
//		else if ( ie.body(id, ILBFunctionConst.lbf_FormletList_Table_Id).exists() )
//		{
//			body = ie.body(id, ILBFunctionConst.lbf_FormletList_Table_Id);
//			if ( null == body )
//			{
//				log.error("Could not access table (" + ILBFunctionConst.lbf_FormletList_Table_Id + ")!");
//				return -1;
//			}
//		}
//		else
//		{
//			log.error("Could not find the Attachment List!");
//
//			return 0;
//		}

		TableRows tableRows = body.rows();
		
		if ( null == tableRows )
		{
			log.error("Could not access rows for table (" + tableId + ")!");
			return -1;
		}

		int attCount = 0;

		for (TableRow tableRow : tableRows)
		{
			if ( tableRow.cell(3).image(0).exists() )
			{
				attCount++;
			}
		}

		return attCount;
	}

	public static int howManyEntriesInTable(String tBodyId) throws Exception
	{
		Div tDiv = tableDiv();

		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find table (" + tBodyId + ")!");
			return -1;
		}

		//in grantium 5.1 JSF 1.1.14 creates one row with one empty cell for empty table
		//the logic has been changed to deal with that!

		int retInt = tDiv.body(id,tBodyId).rowCount();

		if(retInt > 1)
		{
			return retInt;

		}

		if (retInt == 0)
		{
			return retInt;
		}
		int celSize = tDiv.body(id,tBodyId).row(0).cells().length();

		if(celSize > 1)
		{
			return retInt;
		}

		return 0;
	}

	public static int howManyEntriesInTableWithPagenator(String tBodyId) throws Exception
	{
		IE ie = IEUtil.getActiveIE();

		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find table (" + tBodyId + ")!");
			return -1;
		}

		Div tableFooter = null;
		
		Spans spans = null;
		
		Span span = null;
		
		int retInt = 0;	

		boolean footerExists = ie.div(id, ITablesConst.paginationDivId).exists();

		if(footerExists)
		{
			tableFooter = ie.div(id, ITablesConst.paginationDivId);
			
			span = tableFooter.spans().span(attribute("class","ui-paginator-pages"));
			
			spans = span.spans();
			
			log.warn(spans.length());
			
			for (Span span2 : spans) {
				
				log.warn(span2.innerText());
				
				log.warn(span2.title());
				if(span2.title().startsWith("View Page"))
				{					
					span2.click();
					
					GeneralUtil.takeANap(0.5);
					
					retInt += howManyEntriesInTable(tBodyId);					
				}								
			}	

		} else
		{
			retInt = howManyEntriesInTable(tBodyId);
		}

		return retInt;
	}

	public static boolean findInTable(String tBodyId, String string) throws Exception
	{
		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find table (" + tBodyId + ")!");
			return false;
		}

		TableRows tableRows = null;
		
		Div tDiv = tableDiv();

		tableRows = tDiv.body(id, tBodyId).rows();
		if ( null == tableRows )
		{
			log.error("Could not access rows for table (" + tBodyId + ")!");
			return false;
		}

		for ( TableRow tRow : tableRows )
		{
			for ( TableCell tCell : tRow.cells() )
			{

				if ((tCell.innerText().toUpperCase()).contains(string.toUpperCase()) )

					return true;
			}
		}

		return false;
	}

	public static String[] getDataFromTable(String tBodyId) throws Exception
	{
		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find table (" + tBodyId + ")!");
			return null;
		}

		TableRows tableRows = null;

		tableRows = tableDiv().body(id, tBodyId).rows();

		if ( null == tableRows )
		{
			log.error("Could not access rows for table (" + tBodyId + ")!");
			return null;
		}

		String str1[]= new String[tableRows.length()];

		int index=0;

		for ( TableRow tRow : tableRows )
		{

			str1[index++] = tRow.innerText();

		}

		return str1 ;
	}

	public static boolean verifyDataFromTable(String tBodyId, String str[])
			throws Exception {
		IE ie = IEUtil.getActiveIE();

		boolean retValue;

		retValue = false;

		if (!isTableExists(tBodyId)) {
			log.error("Could not find table (" + tBodyId + ")!");
			return false;
		}

		TableRows tableRows = null;

		tableRows = ie.table(id, tBodyId).body(id, ITablesConst.poTableBodyId)
				.rows();

		if (null == tableRows) {
			log.error("Could not access rows for table (" + tBodyId + ")!");
			return false;
		}

		int index = 0;

		for (TableRow tRow : tableRows) {

			if (str[index++].equalsIgnoreCase(tRow.innerText().toString())) {
				retValue = true;
			}

			if (!retValue) {
				break;
			}

		}

		return retValue;
	}

	public static boolean findInTableForEqualId(String tBodyId, String string)
			throws Exception {
		int amendmentId, v_amendmentId;
		boolean retValue = false;
		Integer pageNumber = 1;
		IE ie = IEUtil.getActiveIE();

		if (!isTableExists(tBodyId)) {
			log.error("Could not find table (" + tBodyId + ")!");
			return false;
		}

		v_amendmentId = Integer.parseInt(string);

		TableRows tableRows = null;

		tableRows = tableDiv().body(id, tBodyId).rows();
		if (null == tableRows) {
			log.error("Could not access rows for table (" + tBodyId + ")!");
			return false;
		}

		Div tableFooter = ie.div(id, ITablesConst.paginationDivId);
		if (null == tableFooter) {
			log.error("Could not access footer for table (" + tBodyId + ")!");
			return false;
		}

		if ((tableFooter).link(text, pageNumber.toString()).exists()) {
			(tableFooter).link(text, pageNumber.toString()).click();

			GeneralUtil.takeANap(1.0);

			tableFooter = ie.div(id, ITablesConst.paginationDivId);
			if (null == tableFooter) {
				log.error("Could not access footer for table (" + tBodyId
						+ ")!");
				return false;
			}
		}

		do {

			if (pageNumber > 1) {
				(tableFooter).link(text, pageNumber.toString()).click();

				GeneralUtil.takeANap(1.0);
			}

			for (TableRow tRow : tableRows) {

				if (tRow.cells().length() == 9) {
					amendmentId = Integer.parseInt(tRow.cell(3).innerText());

					if (amendmentId == v_amendmentId) {
						retValue = true;
					} else
						retValue = false;
				} 

				if (!retValue) {
					break;
				}
			}

		} while ((tableFooter).link(text, pageNumber.toString()).exists());

		return retValue;
	}

	public static boolean findInTableForLessThanId(String tBodyId, String string)
			throws Exception {
		int amendmentId, v_amendmentId;
		boolean retValue = false;
		Integer pageNumber = 1;
		IE ie = IEUtil.getActiveIE();

		if (!isTableExists(tBodyId)) {
			log.error("Could not find table (" + tBodyId + ")!");
			return false;
		}

		v_amendmentId = Integer.parseInt(string);

		TableRows tableRows = null;

		tableRows = tableDiv().body(id, tBodyId).rows();
		if (null == tableRows) {
			log.error("Could not access rows for table (" + tBodyId + ")!");
			return false;
		}

		Div tableFooter = ie.div(id, ITablesConst.paginationDivId);
		if (null == tableFooter) {
			log.error("Could not access footer for table (" + tBodyId + ")!");
			return false;
		}

		if ((tableFooter).link(text, pageNumber.toString()).exists()) {
			(tableFooter).link(text, pageNumber.toString()).click();

			GeneralUtil.takeANap(1.0);

			tableFooter = ie.div(id, ITablesConst.paginationDivId);
			if (null == tableFooter) {
				log.error("Could not access footer for table (" + tBodyId
						+ ")!");
				return false;
			}
		}

		do {
			if (pageNumber > 1) {
				(tableFooter).link(text, pageNumber.toString()).click();

				GeneralUtil.takeANap(1.0);
			}
			for (TableRow tRow : tableRows) {

				if (tRow.cells().length() == 9) {
					amendmentId = Integer.parseInt(tRow.cell(3).innerText());

					if (amendmentId < v_amendmentId) {
						retValue = true;
					} else
						retValue = false;
				} 

				if (!retValue) {
					break;
				}
			}
		} while ((tableFooter).link(text, pageNumber.toString()).exists());

		return retValue;
	}

	public static boolean findInTableForLessThanEqualToId(String tBodyId, String string)
			throws Exception {
		int amendmentId, v_amendmentId;
		boolean retValue = false;
		Integer pageNumber = 1;
		IE ie = IEUtil.getActiveIE();

		if (!isTableExists(tBodyId)) {
			log.error("Could not find table (" + tBodyId + ")!");
			return false;
		}

		v_amendmentId = Integer.parseInt(string);

		TableRows tableRows = null;

		tableRows = tableDiv().body(id, tBodyId).rows();
		if (null == tableRows) {
			log.error("Could not access rows for table (" + tBodyId + ")!");
			return false;
		}

		Div tableFooter = ie.div(id, ITablesConst.paginationDivId);
		if (null == tableFooter) {
			log.error("Could not access footer for table (" + tBodyId + ")!");
			return false;
		}

		if ((tableFooter).link(text, pageNumber.toString()).exists()) {
			(tableFooter).link(text, pageNumber.toString()).click();

			GeneralUtil.takeANap(1.0);

			tableFooter = ie.div(id, ITablesConst.paginationDivId);
			if (null == tableFooter) {
				log.error("Could not access footer for table (" + tBodyId
						+ ")!");
				return false;
			}
		}

		do {
			if (pageNumber > 1) {
				(tableFooter).link(text, pageNumber.toString()).click();

				GeneralUtil.takeANap(1.0);
			}
			for (TableRow tRow : tableRows) {

				if (tRow.cells().length() == 9) {
					amendmentId = Integer.parseInt(tRow.cell(3).innerText());

					if (amendmentId <= v_amendmentId) {
						retValue = true;
					} else
						retValue = false;
				} 

				if (!retValue) {
					break;
				}
			}
		} while ((tableFooter).link(text, pageNumber.toString()).exists());

		return retValue;
	}
	public static boolean findInTableForGreaterThanId(String tBodyId, String string)
			throws Exception {
		int amendmentId, v_amendmentId;
		boolean retValue = false;
		Integer pageNumber = 1;
		IE ie = IEUtil.getActiveIE();

		if (!isTableExists(tBodyId)) {
			log.error("Could not find table (" + tBodyId + ")!");
			return false;
		}

		v_amendmentId = Integer.parseInt(string);

		TableRows tableRows = null;

		tableRows = tableDiv().body(id, tBodyId).rows();
		if (null == tableRows) {
			log.error("Could not access rows for table (" + tBodyId + ")!");
			return false;
		}

		Div tableFooter = ie.div(id, ITablesConst.paginationDivId);

		if (null == tableFooter) {
			log.error("Could not access footer for table (" + tBodyId + ")!");
			return false;
		}

		if ((tableFooter).link(text, pageNumber.toString()).exists()) {

			(tableFooter).link(text, pageNumber.toString()).click();

			GeneralUtil.takeANap(1.0);

			tableFooter = ie.div(id, ITablesConst.paginationDivId);
			if (null == tableFooter) {
				log.error("Could not access footer for table (" + tBodyId
						+ ")!");
				return false;
			}
		}

		do {
			if (pageNumber > 1) {
				(tableFooter).link(text, pageNumber.toString()).click();

				GeneralUtil.takeANap(1.0);
			}
			for (TableRow tRow : tableRows) {

				if (tRow.cells().length() == 9) {

					amendmentId = Integer.parseInt(tRow.cell(3).innerText());

					if (amendmentId > v_amendmentId) {
						retValue = true;
					} else
						retValue = false;
				} 

				if (!retValue) {
					break;
				}
			}
		} while ((tableFooter).link(text, pageNumber.toString()).exists());

		return retValue;
	}

	public static boolean findInTableForGreaterThanEqualToId(String tBodyId, String string)
			throws Exception {
		int amendmentId, v_amendmentId;
		boolean retValue = false;
		Integer pageNumber = 1;
		IE ie = IEUtil.getActiveIE();

		if (!isTableExists(tBodyId)) {
			log.error("Could not find table (" + tBodyId + ")!");
			return false;
		}

		v_amendmentId = Integer.parseInt(string);

		TableRows tableRows = null;

		tableRows = tableDiv().body(id, tBodyId).rows();
		if (null == tableRows) {
			log.error("Could not access rows for table (" + tBodyId + ")!");
			return false;
		}

		Div tableFooter = ie.div(id, ITablesConst.paginationDivId);
		if (null == tableFooter) {
			log.error("Could not access footer for table (" + tBodyId + ")!");
			return false;
		}

		if ((tableFooter).link(text, pageNumber.toString()).exists()) {
			(tableFooter).link(text, pageNumber.toString()).click();

			GeneralUtil.takeANap(1.0);

			tableFooter = ie.div(id, ITablesConst.paginationDivId);
			if (null == tableFooter) {
				log.error("Could not access footer for table (" + tBodyId
						+ ")!");
				return false;
			}
		}

		do {
			if (pageNumber > 1) {
				(tableFooter).link(text, pageNumber.toString()).click();

				GeneralUtil.takeANap(1.0);
			}
			for (TableRow tRow : tableRows) {

				if (tRow.cells().length() == 9) {
					amendmentId = Integer.parseInt(tRow.cell(3).innerText());

					if (amendmentId >= v_amendmentId) {
						retValue = true;
					} else
						retValue = false;
				} 

				if (!retValue) {
					break;
				}
			}
		} while ((tableFooter).link(text, pageNumber.toString()).exists());

		return retValue;
	}

	public static boolean findInTableForBetweenId(String tBodyId, String string)
			throws Exception {
		int amendmentId, v_amendmentId;
		boolean retValue = false;
		Integer pageNumber = 1;
		IE ie = IEUtil.getActiveIE();

		if (!isTableExists(tBodyId)) {
			log.error("Could not find table body (" + tBodyId + ")!");
			return false;
		}

		v_amendmentId = Integer.parseInt(string);

		TableRows tableRows = null;

		tableRows = tableDiv().body(id, tBodyId).rows();
		if (null == tableRows) {
			log.error("Could not access rows for table (" + tBodyId + ")!");
			return false;
		}

		Div tableFooter = ie.div(id, ITablesConst.paginationDivId);
		if (null == tableFooter) {
			log.error("Could not access footer for table (" + tBodyId + ")!");
			return false;
		}

		if ((tableFooter).link(text, pageNumber.toString()).exists()) {
			(tableFooter).link(text, pageNumber.toString()).click();

			GeneralUtil.takeANap(1.0);

			tableFooter = ie.div(id, ITablesConst.paginationDivId);
			if (null == tableFooter) {
				log.error("Could not access footer for table (" + tBodyId
						+ ")!");
				return false;
			}
		}

		do {
			if (pageNumber > 1) {
				(tableFooter).link(text, pageNumber.toString()).click();

				GeneralUtil.takeANap(1.0);
			}
			for (TableRow tRow : tableRows) {

				if (tRow.cells().length() == 9) {
					amendmentId = Integer.parseInt(tRow.cell(3).innerText());

					if (amendmentId <= v_amendmentId && amendmentId>=1) {
						retValue = true;
					} else
						retValue = false;
				} 

				if (!retValue) {
					break;
				}
			}
		} while ((tableFooter).link(text, pageNumber.toString()).exists());

		return retValue;
	}

	public static int findInTable(String tBodyId, String[] ListTable) throws Exception
	{		
		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find table body (" + tBodyId + ")!");
			return -1;
		}

		int HashElements = ListTable.length;

		int RowIndex = -1;	

		TableRows tableRows = null;

		Div tDiv = tableDiv();

		tableRows = tDiv.body(id,tBodyId).rows();
		if ( null == tableRows )
		{
			log.error("Could not access rows for table body (" + tBodyId + ")!");
			return -1;
		}

		if ( tableRows.length() > 0 )
		{
			switch (HashElements) {
			case 1:

				for (TableRow tRow : tableRows)
				{
					RowIndex++;

					if ( tRow.innerText().contains((ListTable[0])) )
					{
						return RowIndex;
					}
				}
				break;

			case 2:

				for (TableRow tRow : tableRows)
				{
					RowIndex++;
					if ( (tRow.innerText().contains(ListTable[0]))
							&& (tRow.innerText().contains(ListTable[1])) )
					{
						return RowIndex;
					}
				}
				break;

			case 3:

				for (TableRow tRow : tableRows)
				{
					RowIndex++;

					if ( (tRow.innerText().contains(String.valueOf(ListTable[0])))
							&& (tRow.innerText().contains(String.valueOf(ListTable[1])))
							&& (tRow.innerText().contains(String.valueOf(ListTable[2]))) )
					{
						return RowIndex;
					}
				}
				break;

			case 4:

				for (TableRow tRow : tableRows)
				{
					RowIndex++;

					if ( (tRow.innerText().contains(String.valueOf(ListTable[0])))
							&& (tRow.innerText().contains(String.valueOf(ListTable[1])))
							&& (tRow.innerText().contains(String.valueOf(ListTable[2])))
							&& (tRow.innerText().contains(String.valueOf(ListTable[3]))) )
					{
						return RowIndex;
					}
				}
				break;
			} // end of switch
		}

		return -1;
	}

	public static boolean openInTableByImage(String tBodyId, String objToFind, int imageIndex) throws Exception
	{
		Div tDiv = tableDiv();
		
		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find table body (" + tBodyId + ")!");
			return false;
		}

		int rowIndex = -1;

		rowIndex = getRowIndex(tBodyId, objToFind);

		TableBody tBody = tDiv.body(id, tBodyId);
		if ( null == tBody )
		{
			log.error("Could not access table (" + tBodyId + ")!");
			return false;
		}

		if ( rowIndex <= -1 )
		{
			log.error("Could not find Item in List");
			return false;
		}

		if ( !tBody.row(rowIndex).image(imageIndex).exists() )
		{
			log.error("Could not find image by Index!");
			return false;
		}
		log.warn("Looking for image: " + tBody.row(rowIndex).link(imageIndex).image(0).alt());
		tBody.row(rowIndex).link(imageIndex).image(0).click();

		return true;
	}

	public static boolean openInTableByImageAndIndex(String tBodyId, int objToFind,	int imageIndex) throws Exception
	{
	
		Div tDiv = tableDiv();

		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find table (" + tBodyId + ")!");
			return false;
		}

		TableBody tBody = tDiv.body(id,tBodyId);
		if ( null == tBody )
		{
			log.error("Could not access table (" + tBodyId + ")!");
			return false;
		}

		if ( objToFind <= -1 )
		{
			log.error("Could not find Item in List");
			return false;
		}

		if ( !tBody.row(objToFind).cell(imageIndex).image(1).exists() )
		{
			log.error("Could not find image by Index!");
			return false;
		}

		tBody.row(objToFind).cell(imageIndex).link(1).click();

		return true;
	}

	public static boolean openInTableByImageLinkAndIndex(String tBodyId, int objToFind,	int cellIndex, int linkIndex) throws Exception
	{
	
		Div tDiv = tableDiv();

		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find table (" + tBodyId + ")!");
			return false;
		}

		TableBody tBody = tDiv.body(id,tBodyId);
		if ( null == tBody )
		{
			log.error("Could not access table (" + tBodyId + ")!");
			return false;
		}

		if ( objToFind <= -1 )
		{
			log.error("Could not find Item in List");
			return false;
		}

		if ( !tBody.row(objToFind).cell(cellIndex).link(linkIndex).exists() )
		{
			log.error("Could not find image by Index!");
			return false;
		}

		tBody.row(objToFind).cell(cellIndex).link(linkIndex).click();

		return true;
	}

	public static boolean openInTableByImageAlt(String tBodyId, String objToFind, String imageAlt) throws Exception
	{
		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find table (" + tBodyId + ")!");
			return false;
		}

		
		
		int rowIndex = -1;

		rowIndex = getRowIndex(tBodyId, objToFind);
		
		Div tDiv = tableDiv();

		TableBody tBody = tDiv.body(id ,tBodyId);
		if ( null == tBody )
		{
			log.error("Could not access table (" + tBodyId + ")!");
			return false;
		}

		if ( rowIndex <= -1 )
		{
			log.error("Could not find Item in List");
			return false;
		}

		if ( !tBody.row(rowIndex).image(alt,imageAlt).exists() )
		{
			log.error("Could not find image by Alt!");
			return false;
		}

		tBody.row(rowIndex).image(alt,imageAlt).click();

		return true;
	}

	public static boolean openInTableByImageAltAndIndex(String tBodyId, int objToFind, String imageAlt) throws Exception
	{
		Div tDiv = tableDiv();

		if(!isImageExistsInTableByAlt(tBodyId,objToFind,imageAlt))
		{
			log.error("Could not find image by Alt!");
			return false;
		}

		tDiv.body(0).row(objToFind).image(alt,imageAlt).click();

		return true;
	}

	public static boolean isImageExistsInTableByAlt(String tBodyId, int rowIndx, String imgAlt) throws Exception
	{
		Div tDiv = tableDiv();

		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find tableBody (" + tBodyId + ")!");
			return false;
		}

		TableBody tBody = tDiv.body(id,tBodyId);
		if ( null == tBody )
		{
			log.error("Could not access tableBody (" + tBodyId + ")!");
			return false;
		}

		if (rowIndx <= -1)
		{
			log.error("Could not find Item in List");
			return false;
		}

		return tBody.row(rowIndx).image(alt,imgAlt).exists();
	}


	public static boolean isEntryAddedInTable(String tableId, int beforeEntries) throws Exception
	{

		int nowEntries = howManyEntriesInTable(tableId);

		if (beforeEntries >= nowEntries)
		{
			log.error("The new table entry was not created !");
			return false;
		}
		return true;
	}

	/**
	 * Returns int found in the first row of a specified table column
	 * @param tBodyId
	 * @param column
	 * @param str
	 * @return int from table
	 * @throws Exception
	 */
	public static int manageArchive_VerifyNumberinTable(String tBodyId, int column) throws Exception {		
		
		Div tDiv = TablesUtil.tableDiv();

		if ( !isTableExists(tBodyId) ) {
			log.error("Could not find table body (" + tBodyId + ")!");
			return -1;
		}	

		else {
			TableBody tBody= tDiv.body(id,tBodyId);

			if(tBody.rows().length() > 0) {

				String string = tBody.row(0).cell(column).text().trim(); 
				int numOpen = Integer.parseInt(string);	

				log.warn("Found number " + numOpen + " in table");

				return numOpen;
			}
		}
		return -1;
	}


	public static int getTotalRows(String tBodyId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Integer pageNumber = 1;

		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find table body (" + tBodyId + ")!");
			return -1;
		}

		int rIndex = 0;
		TableRows tableRows = null;
		
		Div tDiv = tableDiv();

		do {

			if (pageNumber > 1)
			{
				ie.link(text, pageNumber.toString()).click();
			}
			else if (pageNumber == 1 && ie.link(text,pageNumber.toString()).exists())
			{
				ie.link(text, pageNumber.toString()).click();
			}
			tableRows = tDiv.body(id, tBodyId).rows();

			if ( null == tableRows )
			{
				log.error("Could not access rows for table body (" + tBodyId + ")!");
				return -1;
			}

			for (TableRow tRow : tableRows)
			{
				if (tRow.columnCount()>1)
					rIndex++;

				if (rIndex==20)
					pageNumber += 1;
			} 	

		} while(ie.link(text, pageNumber.toString()).exists());

		log.warn("Found " + rIndex + " rows in table " + tBodyId);
		return rIndex;
	}
	


	public static int getTotalColumns(String tBodyId) throws Exception {
		
		Div tDiv = tableDiv();

		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find tableBody (" + tBodyId + ")!");
			return -1;
		}

		TableRows tableRows = tDiv.body(id, tBodyId).rows();

		int tCol = 0;

		for (TableRow tRow : tableRows){

			tCol = tRow.columnCount();
		}

		log.warn("There are "+tCol+ " total columns in each row from table "+ tBodyId);
		return tCol; 	
	}


	/**
	 * Returns string found in the first row of a specified table column
	 * @param tBodyId
	 * @param column
	 * @return string from table
	 * @throws Exception
	 */
	public static String verifyStringinTable(String tBodyId, int row, int column) throws Exception {		
		
		Div tDiv = tableDiv();
		
		TableBody tBody = tDiv.body(id,tBodyId);
		
		if ( !isTableExists(tBodyId) )
		{
			log.error("Could not find table body (" + tBodyId + ")!");
			return null;
		}	

		else
		{
			String string = tBody.row(row).cell(column).text(); 

			log.warn("String found: " + string);

			return string;
		}
	}


}
