/**
 * 
 */
package test_Suite.utils.workflow;

/**
 * @author mshakshouki
 * 
 */

import static watij.finders.SymbolFactory.alt;
import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.text;
import static watij.finders.SymbolFactory.title;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IAmendmentsConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.elements.TableBody;
import watij.elements.TableRow;
import watij.elements.TableRows;
import watij.runtime.ie.IE;

public class GpsUtil {

	private static Log log = LogFactory.getLog(GpsUtil.class);

	//static boolean retValue;

	static int intValue;


	/**
	 * @param proj
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean openAwardAgreementSchedule(Project proj)
			throws Exception {

		IE ie = IEUtil.getActiveIE();		

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		if(!ClicksUtil.clickLinks(IClicksConst.openAwardListLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openAwardListLnk));
			return false;	
		}

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, proj.getProjFullName());
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl, proj.getProgFullName());

		FiltersUtil.filterListByLabel(hashTable,null,false);

		if(!ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.sortBySubmissionDateLnk));
			return false;	
		}

		if(!ClicksUtil.clickLinks(IClicksConst.sortBySubmissionDateLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.sortBySubmissionDateLnk));
			return false;	
		}

		ie.body(id, ITablesConst.awardsTableId).row(0).link(title, "View Award").click();

		return true;
	}

	/**
	 * 
	 * @param proj
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean openAndFillGPSAwardForm(Project proj) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		if(!ClicksUtil.clickLinks(IClicksConst.openAwardListLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openAwardListLnk));
			return false;	
		}

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, proj.getProjFullName());
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl, proj.getProgFullName());

		FiltersUtil.filterListByLabel(hashTable,null,false);

		Assert.assertTrue(TablesUtil.findInTable(ITablesConst.awardsTableId, proj.getProgFullName()), "FAIL: Could not find Award eForm!");

		ie.body(id, ITablesConst.awardsTableId).row(0).link(title, "View Award").click();

		// Add Claims if do not listed

		Integer claimsCount = howManyEntriesInMilestones();

		if (claimsCount < 2) {

			ClicksUtil.clickImage(IClicksConst.newImg);

			for (int x = 1; x <= claimsCount; x++) 
			{
				ie.textField(id, IProjectsConst.gps_Schedule_NameTxtId).set("Claim" + " " + Integer.toString(x));

				ie.textField(id, IProjectsConst.gps_Schedule_StartDateId).set(proj.getStartDate());

				ie.textField(id, IProjectsConst.gps_Schedule_EndDateId).set(proj.getEndDate());

				ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
			}

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
		}

		// Add Budget Rows (Expenses)

		if(!ClicksUtil.clickLinks(IClicksConst.openBudgetRowsExpensesLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openBudgetRowsExpensesLnk));
			return false;	
		}

		ClicksUtil.clickImage(IClicksConst.newImg);

		ie.selectList(id, IProjectsConst.gps_Expense_Category_DropdownId).select("EXPENSE_CAPITAL");

		ie.selectList(id, IProjectsConst.gps_Expense_SubCategory_DropdownId).select("CAPITAL_EQUIPMENT");

		ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

		// Add Budget Rows (Funding)

		if(!ClicksUtil.clickLinks(IClicksConst.openBudgetRowsFundingLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openBudgetRowsFundingLnk));
			return false;	
		}

		ClicksUtil.clickImage(IClicksConst.newImg);

		ie.selectList(id, IProjectsConst.gps_Contribute_Type_DropdownId).select("Program Funding");

		ie.textField(id, IProjectsConst.gps_Contribute_NameTxtId).set("Grantium Inc.");

		ie.selectList(id, IProjectsConst.gps_Contribution_Type_DropdownId).select("Cash");

		ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

		// Add Budget Formlet

		if(!ClicksUtil.clickLinks(IClicksConst.openBudgetFormletLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openBudgetFormletLnk));
			return false;	
		}

		if(!fillGPSBudgetForm("3000", "1000"))
		{
			log.error("Could not complete Budget Formlet!");

			Assert.fail();
		}

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		// Add(Generate) Payment Schedule

		if(!ClicksUtil.clickLinks(IClicksConst.openMilestoneLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openMilestoneLnk));
			return false;	
		}

		ClicksUtil.clickButtons(IClicksConst.generatePaymentScheduleBtn);

		ClicksUtil.clickLinks(IClicksConst.openClaimSubmissionScheduleLnk);

		setGPSRequiredAndPOOnlyCheckBox(0, true, false);
		setGPSRequiredAndPOOnlyCheckBox(1, false, false);
		return true;
	}

	/**
	 * 
	 * @param rowIndex
	 * @param required
	 * @param poOnly
	 * @return
	 * @throws Exception
	 */
	public static boolean setGPSRequiredAndPOOnlyCheckBox(Integer rowIndex,
			boolean required, boolean poOnly) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ie.body(id, IProjectsConst.gps_ListTableId).row(rowIndex).link(title, "View this list item").exists())
			return false;
		else
			ie.body(id, IProjectsConst.gps_ListTableId).row(rowIndex).link(title, "View this list item").click();

		EFormsUtil.taggleCheckbox(0, required);
		EFormsUtil.taggleCheckbox(1, poOnly);

		ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);
		return true;
	}

	/**
	 * 
	 * @param proj
	 * @param isItSubmittable
	 * @return
	 * @throws Exception
	 */
	public static boolean submitPaymentSchedule(Project proj,
			boolean isItSubmittable) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		if(!ClicksUtil.clickLinks(IClicksConst.openAwardListLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openAwardListLnk));
			return false;	
		}

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, proj
				.getProjFullName());
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl, proj
				.getProgFullName());

		FiltersUtil.filterListByLabel(hashTable,null,false);

		ie.table(id, ITablesConst.awardsTableId).body(id,ITablesConst.poTableBodyId).row(0).link(title, "View Award").click();

		// Add Budget Rows (Expenses)

		ClicksUtil.clickLinks(IClicksConst.openBudgetRowsExpensesLnk);

		ClicksUtil.clickImage(IClicksConst.newImg);

		ie.selectList(id, IProjectsConst.gps_Expense_Category_DropdownId).select("EXPENSE_CAPITAL");

		ie.selectList(id, IProjectsConst.gps_Expense_SubCategory_DropdownId).select("CAPITAL_EQUIPMENT");

		ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

		// Add Budget Rows (Funding)

		ClicksUtil.clickLinks(IClicksConst.openBudgetRowsFundingLnk);

		ClicksUtil.clickImage(IClicksConst.newImg);

		ie.selectList(id, IProjectsConst.gps_Contribute_Type_DropdownId).select("Program Funding");

		ie.textField(id, IProjectsConst.gps_Contribute_NameTxtId).set("Grantium Inc.");

		ie.selectList(id, IProjectsConst.gps_Contribution_Type_DropdownId).select("Cash");

		ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

		// Add Budget Formlet

		ClicksUtil.clickLinks(IClicksConst.openBudgetFormletLnk);

		if(!fillGPSBudgetForm("3000", "1000"))
		{
			log.error("Could not complete Budget Formlet!");

			return false;
		}

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		// Add(Generate) Payment Schedule

		ClicksUtil.clickLinks(IClicksConst.openMilestoneLnk);

		ClicksUtil.clickButtons(IClicksConst.generatePaymentScheduleBtn);

		// Open PA Submission Schedule

		ClicksUtil.clickLinks(IClicksConst.openClaimSubmissionScheduleLnk);

		if (isItSubmittable) {
			ClicksUtil.clickLinks("Summary");

			ClicksUtil.clickButtons(IClicksConst.submitBtn);

		}

		ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

		return true;

	}

	/**
	 * 
	 * @param proj
	 * @param claimsNum
	 * @param isItSubmittable
	 * @return
	 * @throws Exception
	 */
	public static boolean completeGPSPaymentScheduleAndSubmit(Project proj, int claimsNum,
			boolean isItSubmittable) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		if(!ClicksUtil.clickLinks(IClicksConst.openAwardListLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openAwardListLnk));
			return false;	
		}

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, proj.getProjFullName());
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl, proj.getProgFullName());

		FiltersUtil.filterListByLabel(hashTable,null,false);

		ie.table(id, ITablesConst.awardsTableId).body(id,ITablesConst.poTableBodyId).row(0).link(title, "View Award").click();

		// Add Milestones
		ClicksUtil.clickLinks(IClicksConst.openMilestoneLnk);

		ClicksUtil.clickImage(IClicksConst.newImg);

		for(int x = 1; x <= claimsNum; x++)
		{
			ie.textField(id, IProjectsConst.gps_Milestone_NameTxtId).set("Claim " + Integer.toString(x));
			ie.textField(id, IProjectsConst.gps_Milestone_StartDateId).set(GeneralUtil.getTodayDate());
			ie.textField(id, IProjectsConst.gps_Milestone_EndDateId).set(GeneralUtil.getNextYear());

			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
		}

		ClicksUtil.clickButtons(IClicksConst.backToListBtn);

		// Add Budget Rows (Expenses)

		ClicksUtil.clickLinks(IClicksConst.openBudgetRowsExpensesLnk);

		ClicksUtil.clickImage(IClicksConst.newImg);

		ie.selectList(id, IProjectsConst.gps_Expense_Category_DropdownId).select("EXPENSE_CAPITAL");

		ie.selectList(id, IProjectsConst.gps_Expense_SubCategory_DropdownId).select("CAPITAL_EQUIPMENT");

		ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

		// Add Budget Rows (Funding)

		ClicksUtil.clickLinks(IClicksConst.openBudgetRowsFundingLnk);

		ClicksUtil.clickImage(IClicksConst.newImg);

		ie.selectList(id, IProjectsConst.gps_Contribute_Type_DropdownId).select("Program Funding");

		ie.textField(id, IProjectsConst.gps_Contribute_NameTxtId).set("Grantium Inc.");

		ie.selectList(id, IProjectsConst.gps_Contribution_Type_DropdownId).select("Cash");

		ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

		// Add Budget Formlet

		ClicksUtil.clickLinks(IClicksConst.openBudgetFormletLnk);

		TableBody tBody = ie.table(id, IProjectsConst.gps_Budget_DataGrid_TableId).body(0);

		int TextfieldsCount = tBody.row(3).textFields().length();

		for (int x = 0; x <= TextfieldsCount - 2; x++) {
			tBody.row(3).textField(x).set("3000");
			tBody.row(11).textField(x).set("1000");
		}

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		// Add(Generate) Payment Schedule

		ClicksUtil.clickLinks(IClicksConst.openMilestoneLnk);

		ClicksUtil.clickButtons(IClicksConst.generatePaymentScheduleBtn);

		// Open PA Submission Schedule

		ClicksUtil.clickLinks(IClicksConst.openClaimSubmissionScheduleLnk);

		if (isItSubmittable) {
			ClicksUtil.clickLinks("/Summary/");

			ClicksUtil.clickButtons(IClicksConst.submitBtn);

		}

		ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

		return true;

	}

	/**
	 * 
	 * @param proj
	 * @param isItSubmittable
	 * @return
	 * @throws Exception
	 */
	public static boolean submitPaymentScheduleBFSubmissionFunding(
			FOProject proj, boolean isItSubmittable) throws Exception {

		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();

		if(!ClicksUtil.clickLinks(IClicksConst.openAwardListLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openAwardListLnk));
			return false;	
		}

		hashTable.put(IFiltersConst.grantManagement_ProjectName_Lbl, proj
				.getProjFullName());
		hashTable.put(IFiltersConst.grantManagement_FundingOppName_Lbl, proj
				.getProgFullName());

		FiltersUtil.filterListByLabel(hashTable,null,false);

		ie.table(id, ITablesConst.awardsTableId).body(id,ITablesConst.poTableBodyId).row(0).link(title, "View Award").click();

		// Add(Generate) Payment Schedule

		ClicksUtil.clickLinks(IClicksConst.openMilestoneLnk);

		ClicksUtil.clickButtons(IClicksConst.generatePaymentScheduleBtn);

		// Open PA Submission Schedule

		ClicksUtil.clickLinks(IClicksConst.openClaimSubmissionScheduleLnk);

		if (ie.link(text, "Result Formlet").exists()) {
			ClicksUtil.clickLinks("Result Formlet");

			Assert.assertEquals(ie.textField(id, IProjectsConst.gps_Result_ProgTotal_TxtId).getContents(), "3,000.00");

			Assert.assertEquals(ie.textField(id, IProjectsConst.gps_Result_Grp1_TxtId).getContents(), "9,000.00");

			Assert.assertEquals(ie.textField(id, IProjectsConst.gps_Result_Grp2_TxtId).getContents(), "$3,000.00");

			Assert.assertEquals(ie.textField(id, IProjectsConst.gps_Result_VarianceTotal_TxtId).getContents(), "$6,000.00");

		}

		if (isItSubmittable) {
			ClicksUtil.clickLinks("Summary");

			ClicksUtil.clickButtons(IClicksConst.submitBtn);

		}

		ClicksUtil.clickLinks(IClicksConst.backToAwardListLnk);

		return true;
	}

	/**
	 * 
	 * @param foProj
	 * @param stepName
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean openAndFillFOApplicationFunding(FOProject foProj, String stepName) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openSubmissionListLnk));
			return false;	
		}


		foProj.initializStep(stepName);

		foProj.filterSubmissionList(foProj.getCurrentStepName(), "");

		int rowIndx = TablesUtil.getRowIndex(ITablesConst.fOSubmissionsTableId,
				foProj.getCurrentStepName());

		if (rowIndx > -1) {

			ie.body(id, ITablesConst.fOSubmissionsTableId).row(rowIndx).image(alt,"Open e.Form").click();

			// Add Budget Rows (Expenses)

			ClicksUtil.clickLinks("Fiscal Expenses");

			ClicksUtil.clickImage(IClicksConst.newImg);

			ie.selectList(id, IProjectsConst.gps_Expense_Category_DropdownId).select("EXPENSE_CAPITAL");
			
			GeneralUtil.takeANap(1.0);

			ie.selectList(id, IProjectsConst.gps_Expense_SubCategory_DropdownId).select("CAPITAL_EQUIPMENT");
			
			GeneralUtil.takeANap(1.0);

			ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

			// Add Budget Rows (Funding)

			ClicksUtil.clickLinks("Fiscal Funding");

			ClicksUtil.clickImage(IClicksConst.newImg);

			ie.selectList(id, IProjectsConst.gps_Contribute_Type_DropdownId).select("Program Funding");
			
			GeneralUtil.takeANap(1.0);

			ie.textField(id, IProjectsConst.gps_Contribute_NameTxtId).set("Grantium Inc.");

			ie.selectList(id, IProjectsConst.gps_Contribution_Type_DropdownId).select("Cash");

			ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

			// Add Budget Formlet

			ClicksUtil.clickLinks("Fiscal Budget");

			if(!fillGPSBudgetForm("3000", "1000"))
			{
				log.error("Could not complete Budget Formlet!");
				return false;
			}

			ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param expensesAmount
	 * @param cashAmount
	 * @return
	 * @throws Exception
	 */
	public static boolean fillGPSBudgetForm(String expensesAmount, String cashAmount) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ie.body(id,IProjectsConst.gps_Budget_DataGrid_TableId).exists())
		{
			log.error("Could not find the Table for text fields!");
			return false;
		}

		//Div div = ie.div(attribute("class","g3-scrollingGrid"));

		TableRows rows = ie.body(id,IProjectsConst.gps_Budget_DataGrid_TableId).rows();

		TableRow row = null;

		for (TableRow tableRow : rows) {

			if(tableRow.innerText().trim().contains("CAPITAL_EQUIPMENT"))
			{
				row = tableRow;

				for(int x=1; x <=2;x++)
				{
					row.cell(x).textField(0).set(expensesAmount);
				}
			}
			else if(tableRow.innerText().trim().contains("Cash"))
			{
				row = tableRow;

				for(int x=1; x <=2;x++)
				{
					row.cell(x).textField(0).set(cashAmount);
				}
				break;				
			}			
		}		

		return true;
	}

	/**
	 * @param foProj
	 * @param isItSubmittable
	 * @param stepName
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean submitApplicationFunding(FOProject foProj,
			boolean isItSubmittable, String stepName) throws Exception {

		IE ie = IEUtil.getActiveIE();

		if(!ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openSubmissionListLnk));
			return false;	
		}

		foProj.initializStep(stepName);

		foProj.filterSubmissionList(foProj.getCurrentStepName(), "");

		int rowIndx = TablesUtil.getRowIndex(ITablesConst.fOSubmissionsTableId,
				foProj.getCurrentStepName());

		ie.body(id, ITablesConst.fOSubmissionsTableId).row(rowIndx).image(alt, "Open e.Form").click();

		// Add Budget Fiscal Years if not Exists

		ClicksUtil.clickLinks("Fiscal Years");

		int rowsCount = TablesUtil.howManyEntriesInTable(IProjectsConst.gps_ListTableId);

		if(rowsCount <= 0)
		{
			ClicksUtil.clickImage(IClicksConst.newImg);

			for(int x = 1; x <= 2; x++)
			{
				ie.textField(id, IProjectsConst.gps_Milestone_NameTxtId).set("Claim " + Integer.toString(x));

				ie.textField(id, IProjectsConst.gps_Milestone_StartDateId).set(foProj.getStartDate());

				ie.textField(id, IProjectsConst.gps_Milestone_EndDateId).set(foProj.getEndDate());

				ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
			}

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
		}
		else
		{
			Assert.assertTrue(TablesUtil.openInTableByImage(IProjectsConst.gps_ListTableId, "Claim 1", 1), "FAIL: Could not open Claim Details!");

			foProj.setStartDate(ie.textField(id,IProjectsConst.gps_Milestone_StartDateId).getContents());

			foProj.setEndDate(ie.textField(id,IProjectsConst.gps_Milestone_EndDateId).getContents());

			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
		}


		// Add Budget Rows (Expenses)

		ClicksUtil.clickLinks("Fiscal Expenses");

		ClicksUtil.clickImage(IClicksConst.newImg);

		ie.selectList(id, IProjectsConst.gps_Expense_Category_DropdownId).select("EXPENSE_CAPITAL");
		
		GeneralUtil.takeANap(1.0);

		ie.selectList(id, IProjectsConst.gps_Expense_SubCategory_DropdownId).select("CAPITAL_EQUIPMENT");
		
		GeneralUtil.takeANap(1.0);

		ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

		// Add Budget Rows (Funding)

		ClicksUtil.clickLinks("Fiscal Funding");

		ClicksUtil.clickImage(IClicksConst.newImg);

		ie.selectList(id, IProjectsConst.gps_Contribute_Type_DropdownId).select("Program Funding");
		
		GeneralUtil.takeANap(1.0);

		ie.textField(id, IProjectsConst.gps_Contribute_NameTxtId).set("Grantium Inc.");

		ie.selectList(id, IProjectsConst.gps_Contribution_Type_DropdownId).select("Cash");

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		log.debug(GeneralUtil.getSelectedItemValueInDropdwonById(IProjectsConst.gps_Contribute_Type_DropdownId));

		ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

		// Add Budget Formlet

		ClicksUtil.clickLinks("Fiscal Budget");

		if(!fillGPSBudgetForm("3000", "1000"))
		{
			log.error("Could not complete Budget Formlet!");
			Assert.fail();
		}

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

		if (isItSubmittable) {
			ClicksUtil.clickLinks("/Summary/");

			if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
			{
				log.error("Could Not Submit the form!");
				return false;
			}
		}return true;
	}

	public static int howManyEntriesInPaymentSchedule() throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openClaimSubmissionScheduleLnk);

		return TablesUtil.findHowManyInTable("/:data/", "Claim");
	}

	public static int howManyEntriesInMilestones() throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openMilestoneLnk);

		return TablesUtil.findHowManyInTable(IProjectsConst.gps_ListTableId, "Claim");
	}

}
