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
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.FOPPUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProjActivUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.workflow.SubAccessUtil;
import test_Suite.utils.workflow.AmendmentsUtil.EAmendListIcons;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestNonAssignedAccessWithinProjectsNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	LBF lbf;
	
	FundingOpportunity fopp;
	FOProject foProj;
	Users clerk1;

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
			
			Assert.assertTrue(FOPPUtil.changeExpandedControlLevel(fopp, IFoppConst.EExpCtrlLevels.withinProj), "FAIL: error occur during the process of changing Control level");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		fopp = null;
		foProj = null;
		clerk1 = null;
		lbf= null;
		
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

			foProj = new FOProject(fopp,"" , true, 2, "-NotAssigned-WithinProjs");

			foProj.createFOProjectNewNew();
			
			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj, IGeneralConst.gnrl_SubmissionA[0][0]), "FAIL: Could not Open Submission In FO!");
			
			SubAccessUtil.submitAndReturnFromForm();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="createFOProjectNG")
	public void assignApprovals() throws Exception {		
		try {
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
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
	@Test(groups = { "ProjectsNG" }, dependsOnMethods = "assignApprovals")
	public void rejectApprovalNG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("2");
			
			foProj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0]);
			
			ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);
			
			ProjectUtil.openEvaluateSubmissionFormletInList(foProj, IGeneralConst.approvQuoCritAuto[0][0], IFiltersConst.submissionsStatus_Ready_StatusSubView, "No Propreties Fields LBF", false);
			
			GeneralUtil.selectInDropdownList("/0:numericDropdown/", "Rejected");
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
			ClicksUtil.clickLinks("/Summary/");
			
			SubAccessUtil.submitAndReturnFromForm();
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="rejectApprovalNG")
	public void amendApplicationSubmission() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOWithProgOfficer("1");
			
			foProj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0]);
			
			Assert.assertTrue(AmendmentsUtil.openStepAmendmentRequest(foProj), "FAIL: Could not open Step Amendment Request Page!");
			
			foProj.initializeStep(IGeneralConst.gnrl_SubmissionA[0][0]);
			
			clerk1 = SubAccessUtil.getUser(1, 16, "Program Office Users");
			
			String[] amenders = new String []{"Submission", "PO: ".concat(clerk1.getUserFullId())};

			String dd = GeneralUtil.setDayofMonth(3);
			String reason = "Request Step Amend";
			String comment = "";
			
			Assert.assertTrue(AmendmentsUtil.requestStepAmendment(foProj,amenders,dd,reason,comment,false, ""));
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("3");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="amendApplicationSubmission", dataProvider="subs")
	public void testAccessToMyProjectsLists(String stepName, boolean expected) throws Exception {
		
		try {
			
			Assert.assertEquals(AmendmentsUtil.doesAmendmentRequestIconExists_New(foProj, stepName), expected, "FAIL: error verifying entry");			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="testAccessToMyProjectsLists")
	public void testAccessToProjectsLists() throws Exception {
		
		try {
			
			Assert.assertFalse(ProjActivUtil.openProjectActivity(foProj.getProjFOFullName(), IFiltersConst.openProjView),"FAIL: should not open Project Activity page");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="testAccessToProjectsLists", dataProvider="icons")
	public void testAccessToAmendmentsLists(EAmendListIcons icon, boolean expected) throws Exception {
		
		try {
			
			Assert.assertEquals(AmendmentsUtil.openStepAmendIconFromAmendmentList(foProj, IGeneralConst.gnrl_SubmissionA[0][0],icon, IFiltersConst.amendments_InProgressView), expected);
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}

	@DataProvider(name = "subs")
	public Object[][] generateSteps() {

		Object[][] result = null;
		
		result = new Object[][] {
				new Object[] {IGeneralConst.approvQuoCritAuto[0][0],false},
				new Object[] {IGeneralConst.gnrl_SubmissionA[0][0],false}
		};		
		
		return result;		
	}

	@DataProvider(name = "icons")
	public Object[][] generateIcons() {

		Object[][] result = null;
		
		result = new Object[][] {
				new Object[] {EAmendListIcons.cancel, false},
				new Object[] {EAmendListIcons.details,false},
				new Object[] {EAmendListIcons.submission,false}
		};		
		
		return result;		
	}


}
