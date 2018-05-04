/**
 * 
 */
package test_Suite.tests.r3_0.submissionSummary;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EeFormsIdentifier;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.eForms.LBFunctionUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.workflow.SubAccessUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class SSAfterAddingRowsToList {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	LBF lbf;
	
	Users user;
	
	Users approver;
	
	FundingOpportunity fopp;
	
	FOProject foProj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.loginAnyFO("front2");
			// -----------------------------------
			
			lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
					EProjectType.pre_Award, 2, 0, EeFormsIdentifier.userProfile);
			
			user = SubAccessUtil.getUser(2,15,"Front Office Users");
			
			fopp = new FundingOpportunity("A","-eLists-PA-","");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun=true)
	public void tearDown() {
		
		lbf  = null;
		user  = null;
		fopp  = null;
		foProj  = null;
		approver  = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EFormsNG" })
	public void createFOProjectNG() throws Exception {
		try {

			foProj = new FOProject(fopp, fopp.getFoppPreFix(), true, 2, EFormsUtil.createRandomString(5));

			foProj.createFOProject();
			
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: Could not Open Submission In FO!");
			
			SubAccessUtil.submitAndReturnFromForm();
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");
			
			approver = SubAccessUtil.getUser(1,0,"Program Office Users");
			
			ProjectUtil.openEvaluateSubmissionFormletInList(foProj, IGeneralConst.approvQuoCritAuto[0][0], IFiltersConst.submissionsStatus_Ready_StatusSubView, "Submission Summary Fields LBF", false);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EFormsNG" },dataProvider="SummaryDisplay", dependsOnMethods="createFOProjectNG")
	public void testSummaryUpdateByOnBF(String formletName, boolean expected) throws Exception {
		try {
			Assert.assertEquals(EFormsUtil.isValueExistsOnSummaryFormlet(formletName,approver.getUserLName() + ", " + approver.getUserFName()), expected);
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EFormsNG" }, dependsOnMethods="testSummaryUpdateByOnBF")
	public void addRows() throws Exception {
		try {
			
			lbf.setDeleteRowIndex(1);

			lbf.deleteRowsFromAnySubmission();
			
			ClicksUtil.clickLinks("/Summary/");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("2");
			
			approver = SubAccessUtil.getUser(2,0,"Program Office Users");
			
			ProjectUtil.openEvaluateSubmissionFormletInList(foProj, IGeneralConst.approvQuoCritAuto[0][0], IFiltersConst.submissionsStatus_InProgress_StatusSubView, "No Propreties Fields LBF", false);
			
			GeneralUtil.selectInDropdownList("/0:numericDropdown/", "Approved");
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			lbf.setRowsAndIndexes(1, 1);
			
			LBFunctionUtil.insertDataTo_ELists(lbf);
			
			ClicksUtil.clickLinks("/Summary/");
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EFormsNG" },dataProvider="SummaryDisplay", dependsOnMethods="addRows")
	public void testSummaryAfterAddingRows(String formletName, boolean expected) throws Exception {
		
		try {
						
			Assert.assertEquals(EFormsUtil.isValueExistsOnSummaryFormlet(formletName,approver.getUserLName() + ", " + approver.getUserFName()), expected);
		
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}

	@DataProvider(name = "SummaryDisplay")
	public Object[][] generateSummaryDisplay() {

		Object[][] result = null;
		
		result = new Object[][] {
				new Object[] {ILBFunctionConst.formletTypes[0],true},
				new Object[] {ILBFunctionConst.formletTypes[1],true},
				new Object[] {ILBFunctionConst.formletTypes[2],true},
				new Object[] {ILBFunctionConst.formletTypes[3],true},
				new Object[] {ILBFunctionConst.formletTypes[4],true},
				new Object[] {ILBFunctionConst.formletTypes[6], false}
		};		
		
		return result;		
	}
	
//	private void setApprover(int userBeat) throws Exception {
//		try {
//			
//			approver = new Users(userBeat, IPreTestConst.MultiUsers[0],"Front Office Users");
//		
//		}catch (Exception e) {
//			Assert.fail("Unexpected Exception: ", e);
//		}
//	}
}
