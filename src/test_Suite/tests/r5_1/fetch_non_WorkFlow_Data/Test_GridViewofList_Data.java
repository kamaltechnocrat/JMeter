/**
 * 
 */
package test_Suite.tests.r5_1.fetch_non_WorkFlow_Data;

import static watij.finders.SymbolFactory.id;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import watij.runtime.ie.IE;

/**
 * @author k.sharma
 */
@GUITest
@Test(singleThreaded = true)
public class Test_GridViewofList_Data {
	
	// #####***********************************************************************
		// ### To set up the Global Params Name Vars
		// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
		
		String preFix = "-FetchData-PA-";
		
		String postFix = "-Admin";
		
		String submissionStep= "Submission-A";
		
		String applicantName="Ouia 1";
		
		FundingOpportunity fopp;	
		
		FOProject foProj;
		

		//private static final String newPASuffix = "-pa"; // You may not need this for the current FOPP. check closing step in the post award

		/*------ End of Global Vars --------------*/

		@BeforeClass(groups = { "FetchNonWorkFlowData" })
		public void setUp() throws Exception {
			
			try {
				
				log.warn("Starting: " + this.getClass().getSimpleName());
				
				//this will go to Front Office and lognin as front,
				//The Applicant will be Ouia 1, also front4 can be used as well.

				IEUtil.openNewBrowser();
				GeneralUtil.navigateToPO();
				GeneralUtil.logInSuper();
				
				// -----------------------------------
				
				fopp = new FundingOpportunity("B", preFix,postFix);
				
				foProj = new FOProject(fopp, "", true, 1, "");
							

			} catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			}
		}

		@AfterClass(groups = { "FetchNonWorkFlowData" }, alwaysRun=true)
		public void tearDown() throws Exception {
			
			try {
				
				fopp = null;
				foProj = null;
				
				GeneralUtil.Logoff();
				IEUtil.closeBrowser();
				
				log.warn("Ending: " + this.getClass().getSimpleName());
				
			} catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			}
		}

		/**
		 * 
		 * @throws Exception
		 */
		@Test(groups = { "FetchNonWorkFlowData" }, enabled= true)
		
		public void setAdminEformData() throws Exception {
			try {
					
				 openAndFillAdminForm(fopp);
				 
				 ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
				 ClicksUtil.clickLinks(IClicksConst.backToFundingOppPlannerLnk);
				 ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);
				 
				} catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			}
		}
		
		@Test(groups = { "FetchNonWorkFlowData" },dependsOnMethods="setAdminEformData",enabled=true)
		
		public void registerAndCreateFOProjectAndOpenSubmission() throws Exception {
			try {
				
				//this will register to the FOPP and create a project against it...
				
				// to open the Applicant Submission, please look for methods in FrontOffice and Projects Util under under utils.workflows
				//then create one more method where you test the field and it data
					GeneralUtil.switchToFO();
					
					ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);
					
					Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), foProj.getOrgFullName()), "FAIL: Could not Register to Fopp!");
					
					Assert.assertTrue(foProj.createFOProjectNewNew(), "FAIL: Could not create FO Project");
					
					Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(this.foProj, IGeneralConst.gnrl_SubmissionA[0][0]),"FAIL: Couldn't open Submission Form");
					
			} catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			}
		}
		
		
		
		@Test(groups = { "FetchNonWorkFlowData" },dependsOnMethods="registerAndCreateFOProjectAndOpenSubmission", enabled= false)
		public void validateApplicantSubmissionData() throws Exception {
			try {
				
				Assert.assertTrue(EFormsUtil.validateAppSubmissionFieldValue(), "FAIL: Values are not equal to field values ");
					
					
			} catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			}
		}
		
		
				
		private void openAndFillAdminForm(FundingOpportunity fopp) throws Exception {

			IE ie = IEUtil.getActiveIE();
			
			Assert.assertTrue(fopp.openFundingOppPlanner(),
					"FAIL: Could not open FOPP Planner!");

			Assert.assertTrue(fopp.openAdminEForm(),
					"FAIL: Could not open Admin e.Form from Planner!");
			
			// Add Budget Fiscal Years if not Exists
			
			ClicksUtil.clickLinks("Fiscal Years");
			
			int rowsCount = TablesUtil.howManyEntriesInTable(IProjectsConst.gps_ListTableId);
			
			if(rowsCount <= 0)
			{
				ClicksUtil.clickImage(IClicksConst.newImg);
				
				for(int x = 1; x < 2; x++)
				{
					ie.textField(id, IProjectsConst.gps_Milestone_NameTxtId).set("Claim " + Integer.toString(x));
					
					ie.textField(id, IProjectsConst.gps_Milestone_StartDateId).set(GeneralUtil.getTodayDate());
					
					ie.textField(id, IProjectsConst.gps_Milestone_EndDateId).set(GeneralUtil.getTodayDate());
					
					ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
				}
				
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			}
			

			// Add Budget Rows (Expenses)

			ClicksUtil.clickLinks("Fiscal Expenses");

			ClicksUtil.clickImage(IClicksConst.newImg);

			ie.selectList(id, IProjectsConst.gps_Expense_Category_DropdownId).select("EXPENSE_CAPITAL");
			
			GeneralUtil.takeANap(1.5);

			ie.selectList(id, IProjectsConst.gps_Expense_SubCategory_DropdownId).select("CAPITAL_EQUIPMENT");

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
			
			if(!EFormsUtil.fillBudgetForm("3000", "1000"))
			{
				log.error("Could not complete Budget Formlet!");
				Assert.fail();
			}

           

		}

}
