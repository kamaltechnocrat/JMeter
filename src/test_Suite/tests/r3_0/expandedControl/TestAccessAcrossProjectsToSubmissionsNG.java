/**
 * 
 */
package test_Suite.tests.r3_0.expandedControl;

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
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FOPPUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.workflow.SubAccessUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestAccessAcrossProjectsToSubmissionsNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	LBF lbf;
	
	FundingOpportunity fopp;
	FOProject foProj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginPO();
			// -----------------------------------
			
			lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
					EProjectType.pre_Award, 2, 0, EeFormsIdentifier.userProfile);
			
			fopp = new FundingOpportunity("A","-ExpandedCtrl-PA-","");
			
			Assert.assertTrue(FOPPUtil.changeExpandedControlLevel(fopp, IFoppConst.EExpCtrlLevels.acrossProj), "FAIL: error occur during the process of changing Control level");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
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
	@Test(groups = { "ProjectsNG" })
	public void createFOProjectNG() throws Exception {
		try {
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO("front2");

			foProj = new FOProject(fopp, "", true, 2, "-AcrossProjetsToSubs");

			foProj.createFOProjectNewNew();
			
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: Could not Open Submission In FO!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="createFOProjectNG", dataProvider="logins")
	public void testAccessToApplicantSubmissions(String loginName, boolean expected) throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(loginName);
			
			Assert.assertEquals(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj, IGeneralConst.gnrl_SubmissionA[0][0], IFiltersConst.submissionsStatus_All_StatusSubView), expected);
			
		
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="createFOProjectNG", dataProvider="logins")
	public void testAccessToMyAssignedSubmissions(String loginName, boolean expected) throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(loginName);
			
			Assert.assertEquals(ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IGeneralConst.PO_Submission_Non[0][0], IFiltersConst.openProjView), expected);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="createFOProjectNG", dataProvider="logins")
	public void testAccessToEvaluateSubmissions(String loginName, boolean expected) throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(loginName);
			
			Assert.assertEquals(ProjectUtil.openEvaluateSubmissionFormletInList(foProj, IGeneralConst.reviewQuoCritManu[0][0],IFiltersConst.evaluateSubmissions_Ready_StatusSubView, "/Summary/", false), expected);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods={"testAccessToMyAssignedSubmissions","testAccessToEvaluateSubmissions"})
	public void assignApprovals() throws Exception {		
		try {
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			Assert.assertEquals(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj, IGeneralConst.gnrl_SubmissionA[0][0], IFiltersConst.submissionsStatus_All_StatusSubView), true);
			
			SubAccessUtil.submitAndReturnFromForm();
			
			foProj.assignEvaluators(new String[] {
					IGeneralConst.approvQuoCritAuto[0][0],
					IPreTestConst.Groups[3][1][0],IPreTestConst.Groups[3][1][1] });
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods={"assignApprovals"})
	public void continueWorkflow() throws Exception {
		try{
			
			SubAccessUtil.continueWorkflowNG(foProj, false);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="continueWorkflow", dataProvider="logins")
	public void testAccessToPostAwardSubmissions(String loginName, boolean expected) throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(loginName);
			
			Assert.assertEquals(ProjectUtil.openClaimInMyAssignedSubmissionList(foProj, IGeneralConst.initialClaim[0][0], IFiltersConst.openProjView), expected);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="continueWorkflow", dataProvider="logins")
	public void testAccessToClaimSubmissionsInApplicantSubmissionList(String loginName, boolean expected) throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(loginName);
			
			Assert.assertEquals(ProjectUtil.openSubmissionInApplicantSubmissionList(foProj, IGeneralConst.initialClaim[0][0], IFiltersConst.submissionsStatus_All_StatusSubView), expected);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	

	@DataProvider(name = "logins")
	public Object[][] generateLogins() {

		Object[][] result = null;
		
		result = new Object[][] {
				new Object[] {"eastuser2", false},
				new Object[] {"FinancialOfficer3",false},
				new Object[] {"northuser1",false}
		};		
		
		return result;		
	}

}
