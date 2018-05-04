/**
 * 
 */
package test_Suite.tests.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class ProjectStatusFilterInAppSub {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	String preFix = IFoppConst.fundingOpp_PA_Prefix;
	String postFix = "";

	FundingOpportunity fopp;
	Project proj;

	// -------------- End of Global parameters ----------------------

	@BeforeClass(groups="WorkflowNG")
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		//------------------------------

	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {

		proj = null;
		fopp = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}



	@Test(groups = { "WorkflowNG" })
	public void initialize() throws Exception {

		initializeFundingOpp();
		newPOProject();
		assignOfficers();
		evaluateApplication();
		submitAward();
		loginProjOfficer("1");
		submitInitialClaim(1);

		proj.closeOrOpenProject("Close Project");
	}

	@Test(groups = { "WorkflowNG" }, dependsOnMethods = "initialize")
	public void testClosedProjectStatusFilter() throws Exception {

		proj.openApplicantSubList();

		FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl, proj.getProjFullName(),IFiltersConst.exact);

		Assert.assertFalse(TablesUtil.findInTable(ITablesConst.applicantSubmissionTableId, proj.getProjFullName()),"FAIL: this Project is Closed!");		

	}

	private void initializeFundingOpp() throws Exception {

		fopp = new FundingOpportunity("A", preFix,postFix);
	}

	private void newPOProject() throws Exception {

		proj = new Project(fopp,"ProjStatus-",true,true,EFormsUtil.createAnyRandomString(5));

		proj.newCreateNewOrg();
		proj.newCreateNewPOProject();	

		proj.submitProject(true);
	}

	private void assignOfficers() throws Exception {
		proj
		.assignOfficers(new String[][] {
				{ IPreTestConst.Groups[0][0][0],
					IPreTestConst.Groups[0][1][0] },
					{ IPreTestConst.Groups[6][0][0],
						IPreTestConst.Groups[6][1][0] } });
	}

	private void evaluateApplication() throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginApprover("1");

		log.debug("Approver Logged In");

		Assert.assertTrue(proj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0], true,
				"Ready", false, false));
	}

	private void loginProjOfficer(String officerIndex) throws Exception {

		GeneralUtil.switchToPOOnly();
		GeneralUtil.loginProjOfficer(officerIndex);
	}

	private void submitAward() throws Exception {

		GeneralUtil.switchToPO();

		Assert.assertTrue(proj.submitAward("Standard", 2, true),
				"FAIL: Could not Submit Award Form");
	}

	private void submitInitialClaim(int claimNumber) throws Exception {

		proj.setClaimNumber(claimNumber);

		proj.submitFromApplicantSubList(IGeneralConst.initialClaim[0][0], true);
	}
}
