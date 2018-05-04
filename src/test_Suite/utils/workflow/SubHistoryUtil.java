/*
 * 
 */

package test_Suite.utils.workflow;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.ui.*;
import test_Suite.constants.workflow.ISubHistoryConst;
import test_Suite.lib.workflow.*;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.*;
import watij.elements.Div;
import watij.elements.Divs;
import watij.runtime.ie.IE;
import static watij.finders.SymbolFactory.*;
import static watij.finders.FinderFactory.*;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author mshakshouki
 * 
 */
public class SubHistoryUtil {

	private static Log log = LogFactory.getLog(SubHistoryUtil.class);

	static boolean retValue;

	static int rowIndex;

	static int rowsCount;

	/**
	 * @param projName
	 * @return
	 * @throws Exception
	 */
	public static boolean openProjectHistory(String projName) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashDropDown = new Hashtable<String, String>();

		log.info("Opening Project History for " + projName);

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, projName);

		if(!ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk))
		{
			log.error("could not click on link ".concat(IClicksConst.openPOProjectListLnk));
			return false;
		}

		FiltersUtil.filterListByLabel(hashTable,hashDropDown,false);

		rowIndex = TablesUtil.findInTable(ITablesConst.projectsTableId,
				new String[] { projName });

		if (rowIndex > -1) {

			Divs divs = ie.divs(attribute("class", IClicksConst.projects_OptionsClick_DivClass));

			for (Div div : divs){
				if (div.id().contains("caseDataTable:"+ rowIndex)){

					if (div.link(title,"View Submission History").exists()){
						div.link(title,"View Submission History").click();

						log.info("Submission History for " + projName + " Opened Successfully");

						return true;
					}
				}
			}
		}

		log.error("Could not open Submission History!");

		return false;

	}

	/**
	 * @param stepName
	 * @return
	 * @throws Exception
	 */
	public static int getStepEntriesCount(String stepName, String stepStatus)
			throws Exception {

		rowsCount = 0;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashDropDown = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl, stepName);

		hashDropDown.put(IFiltersConst.grantManagement_Status_Lbl,
				stepStatus);

		FiltersUtil.filterListByLabel(hashTable, hashDropDown, false);

		rowsCount = TablesUtil.findHowManyInTable(
				ITablesConst.submissionHistoryTableId, stepName);

		return rowsCount;

	}

	/**
	 * @param subHistory
	 * @param stepCriteria
	 * @return
	 * @throws Exception
	 */
	public static int getStepCriteriaEntries(SubmissionHistory subHistory,
			String stepCriteria) throws Exception {

		rowsCount = 0;

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashDropDown = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.grantManagement_SubmissionVersion_Lbl,
				subHistory.getHistorySubVersion());

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl, subHistory
				.getStepName());

		hashTable.put(IFiltersConst.grantManagement_Status_Lbl,
				subHistory.getHistoryStatus());

		FiltersUtil.filterListByLabel(hashTable,hashDropDown,false);

		rowsCount = TablesUtil.findHowManyInTable(
				ITablesConst.submissionHistoryTableId, stepCriteria);

		return rowsCount;

	}

	public static boolean compareAnswers(String stepType) throws Exception {

		IE ie = IEUtil.getActiveIE();

		retValue = false;

		int compValue;

		String str1;

		String str2;

		ie.table(id, ITablesConst.submissionHistoryTableId).body(id,ITablesConst.poTableBodyId).row(0).image(0)
				.click();

		if ("Evaluation" == stepType) {
			str1 = ie.textField(1).innerText();
		} else {
			str1 = ie.textField(0).innerText();
		}

		if(!ClicksUtil.clickLinks(IClicksConst.projList_BackToProjSubHistoryListLnk))
		{
			log.error("could not click on link ".concat(IClicksConst.projList_BackToProjSubHistoryListLnk));
			return false;
		}

		ie.table(id, ITablesConst.submissionHistoryTableId).body(id,ITablesConst.poTableBodyId).row(1).image(0)
				.click();

		if ("Evaluation" == stepType) {
			str2 = ie.textField(1).innerText();
		} else {
			str2 = ie.textField(0).innerText();
		}

		compValue = str1.compareTo(str2);

		if(!ClicksUtil.clickLinks(IClicksConst.projList_BackToProjSubHistoryListLnk))
		{
			log.error("could not click on link ".concat(IClicksConst.projList_BackToProjSubHistoryListLnk));
			return false;
		}

		if (0 != compValue) {
			retValue = true;
		}

		return retValue;
	}
	
	public static int filterSubmissionHistoryList(SubmissionHistory subHistory) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashDropDown = new Hashtable<String, String>();

		hashDropDown.put(IFiltersConst.grantManagement_SubmissionVersion_Lbl,
				subHistory.getHistorySubVersion());
		
		hashDropDown.put(IFiltersConst.grantManagement_Status_Lbl,
				subHistory.getHistoryStatus());

		hashTable.put(IFiltersConst.grantManagement_StepName_Lbl, subHistory
				.getStepName());

		FiltersUtil.filterListByLabel(hashTable, hashDropDown, false);	
		
		return TablesUtil
		.getRowIndex(ITablesConst.submissionHistoryTableId, subHistory
				.getStepName());
	}

	public static boolean openOverrideDetails(SubmissionHistory subHistory)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		if (filterSubmissionHistoryList(subHistory) > -1) {
			ie.body(id,ITablesConst.submissionHistoryTableId).row(rowIndex)
					.link(text, subHistory.getStepName()).click();

			return true;
		}
		return false;
	}	
	
	public static boolean openAndFillOverrideDetails(SubmissionHistory subHistory) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		rowIndex = -1;
		
		retValue = false;

		Assert.assertTrue(openProjectHistory(subHistory.getProjectName()),
				"FAIL: Could not open Submission History!");

		Assert.assertTrue(openOverrideDetails(subHistory),
				"FAIL: Could not open Override Details!");

		ie.textField(id, ISubHistoryConst.overrideDate_TxtFieldId).set(
				subHistory.getOverrideDate());

		ie.textField(id, ISubHistoryConst.overrideComments_TxtFieldId)
				.set(subHistory.getOverrideComments());

		if(!ClicksUtil.clickButtons(IClicksConst.saveBtn))
		{
			log.error("could not click on button ".concat(IClicksConst.saveBtn));
			return false;
		}
		
		ArrayList<String> errorSmalls = GeneralUtil.checkForErrorMessages();
		
		if (errorSmalls.isEmpty()) {				
			
			retValue = true;
		}	

		return retValue;
	}
	
	public static boolean fillOverrideDetails(SubmissionHistory subHistory)
			throws Exception {
		

		IE ie = IEUtil.getActiveIE();

		rowIndex = -1;
		
		retValue = false;

		Assert.assertTrue(openProjectHistory(subHistory.getProjectName()),
				"FAIL: Could not open Submission History!");

		Assert.assertTrue(openOverrideDetails(subHistory),
				"FAIL: Could not open Override Details!");

		ie.textField(id, ISubHistoryConst.overrideDate_TxtFieldId).set(
				subHistory.getOverrideDate());

		ie.textField(id, ISubHistoryConst.overrideComments_TxtFieldId)
				.set(subHistory.getOverrideComments());

		if(!ClicksUtil.clickButtons(IClicksConst.saveBtn))
		{
			log.error("could not click on button ".concat(IClicksConst.saveBtn));
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.submissionHistory_BackToSubHistoryListBtn))
		{
			log.error("could not click on button ".concat(IClicksConst.submissionHistory_BackToSubHistoryListBtn));
			return false;
		}
		return true;
	}
	
	public static boolean returnFromOverrideDetails() throws Exception {
		
		if(!ClicksUtil.clickButtons(IClicksConst.submissionHistory_BackToSubHistoryListBtn))
		{
			log.error("could not click on button ".concat(IClicksConst.submissionHistory_BackToSubHistoryListBtn));
			return false;
		}
		return true;
	}
	
	public static int compareOverrideDates(SubmissionHistory subHistory) throws Exception {
		
		String dateOnList;		
		Date today;
		Date listDate;
		DateFormat df;
		
		df = DateFormat.getDateInstance(DateFormat.DEFAULT,GeneralUtil.currentLocale);		
		
		today = GeneralUtil.sdfnew.parse(subHistory.getOverrideDate());
		
		rowIndex = filterSubmissionHistoryList(subHistory);
		
		if( rowIndex > -1)
		{
			IE ie = IEUtil.getActiveIE();
			
			dateOnList = ie.body(id, ITablesConst.submissionHistoryTableId).row(rowIndex).cell(7).innerText();
			
			listDate = df.parse(dateOnList);
			
			return today.compareTo(listDate);
		}
		
		return -1;
	}

}
