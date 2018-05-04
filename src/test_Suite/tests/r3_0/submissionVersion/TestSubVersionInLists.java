/**
 * 
 */
package test_Suite.tests.r3_0.submissionVersion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EeFormsIdentifier;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.workflow.SubAccessUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestSubVersionInLists {

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
		
		lbf = null;
		user = null;
		approver = null;
		fopp = null;
		foProj = null;
		
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
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EFormsNG" },dependsOnMethods="createFOProjectNG")
	public void testSVInFoSubmissionListNG() throws Exception {
		
		Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: Could not Open Submission In FO!");
		
		SubAccessUtil.submitAndReturnFromForm();
		
		Assert.assertEquals(TablesUtil.getCellContent(ITablesConst.fOSubmissionsTableId, 0, 6, 0), "1", "FAIL: the version should be 1");
	
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EFormsNG" },dependsOnMethods="testSVInFoSubmissionListNG")
	public void testSVInMyAssignedSubmissionsNG() throws Exception {
		
		GeneralUtil.switchToPOWithProjOfficer("1");
		
		foProj.initializeStep(IGeneralConst.PO_Submission_Non[0][0]);
		
		ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);
		
		FiltersUtil.filterListByProject(foProj);
		
		Assert.assertEquals(TablesUtil.getCellContent(ITablesConst.fundingOpp_myAssignedSubmissionsTableId, 0, 5, 0), "1", "FAIL: the version should be 1");
		
		ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IGeneralConst.PO_Submission_Non[0][0], IFiltersConst.openProjView);
		
		SubAccessUtil.submitAndReturnFromForm();
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EFormsNG" },dependsOnMethods="testSVInMyAssignedSubmissionsNG")
	public void testSVInEvaluateSubmissionNG() throws Exception {
		
		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");
		
		approver = SubAccessUtil.getUser(1, 0, "Program Office Users");
		
		foProj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0]);
		
		ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);
		
		FiltersUtil.filterListByProject(foProj);
		
		Assert.assertEquals(TablesUtil.getCellContent(ITablesConst.fundingOpp_EvaluateSubmissionsTableId, 0, 6, 0), "1", "FAIL: the version should be 1");
		
		ProjectUtil.openEvaluateSubmissionFormletInList(foProj, IGeneralConst.approvQuoCritAuto[0][0], IFiltersConst.submissionsStatus_Ready_StatusSubView, "No Propreties Fields LBF", false);
		
		GeneralUtil.selectInDropdownList("/0:numericDropdown/", "Approved");
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickLinks("/Summary/");
		
		SubAccessUtil.submitAndReturnFromForm();
		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EFormsNG" },dependsOnMethods="testSVInEvaluateSubmissionNG")
	public void testSVInAwardListNG() throws Exception {
		
		GeneralUtil.switchToPOWithProjOfficer("1");
		
		foProj.initializeStep(IGeneralConst.pa_AwardCrit[0][0]);
		
		ClicksUtil.clickLinks(IClicksConst.openAwardListLnk);
		
		FiltersUtil.filterListByProject(foProj);
		
		Assert.assertEquals(TablesUtil.getCellContent(ITablesConst.awardsTableId, 0, 5, 0), "1", "FAIL: the version should be 1");
		
		foProj.setClaimNumber(1);
		
		foProj.changeClaimCriteria(true, false, false, ILBFunctionConst.lbf_IPASSource[1][0], "PA Submission Schedule Fields");
		
		foProj.setClaimNumber(2);
		
		foProj.changeClaimCriteria(true, false, true, ILBFunctionConst.lbf_IPASSource[1][0], "PA Submission Schedule Fields");
	}

}
