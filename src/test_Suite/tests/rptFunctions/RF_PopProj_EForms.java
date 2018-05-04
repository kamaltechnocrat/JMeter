/**
 * 
 */
package test_Suite.tests.rptFunctions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.reporting_Functions.IRptFuncConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.reporting_Functions.RptFuncUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class RF_PopProj_EForms {

	/*********** Variables Declaration Section ********************/
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	Project proj;
	
	Program prog;

	String preFix = "-Project-eForm-";
	
	String postFix = "-Reporting-Function";

	boolean itItNewFundOpp = false;
	
	boolean hasProgForm = true;
	
	boolean isItNewOrg = true;
	
	boolean fillProgForm = true;

	String progAdmin[] = { IPreTestConst.adminGroup };
	
	String progOfficers[] = { IPreTestConst.Groups[0][0][0] };

	String orgName = "-Project-eForm-Reporting-Function";
	
	/*********** End ofVariables Declaration Section ********************/

	@BeforeClass(groups = { "SetupReportingFunctionsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			GeneralUtil.setApplicantWorkspace(IRptFuncConst.reportFunc_eFormsIdent[IRptFuncConst.EeFormsIdentifier.profile.ordinal()]);

			prog = new Program(preFix, 'P', hasProgForm, itItNewFundOpp, false);

			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

			prog.setProgPostfix(postFix);

			prog.initProgram();

			prog.setProgramFormName(IRptFuncConst.reportFunc_eFormsIdent[IRptFuncConst.EeFormsIdentifier.admin.ordinal()]);

			if (fillProgForm) {
				fill_ProgramEForm();
			}

			log.info(prog.getProgFullName());

			proj = new Project(prog, true);

			proj.setOrgName(orgName);

			proj.createOrgFullName(isItNewOrg);

			if (isItNewOrg) {
				fill_ApplicantProfile();
			}

			proj.createPOProjectOnly(isItNewOrg);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	@AfterClass(groups = { "SetupReportingFunctionsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		proj = null;
		prog = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	private void fill_ProgramEForm() throws Exception {

		try {

			ClicksUtil.clickLinks(prog.getProgFullIdent());

			prog.openAdminEForm();

			RptFuncUtil.fill_LookupDropdwon_FieldsData();

			ClicksUtil.clickLinks(IClicksConst.backToFundingOppPlannerLnk);
		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	private void fill_ApplicantProfile() throws Exception {

		try {

			Assert.assertTrue(TablesUtil.openInTableByImage(
					ITablesConst.applicantsTableId, proj.getOrgFullName(), 3),
					"Fail: could not Open Applicant Profile");

			RptFuncUtil.fill_eList_FormletData();
			RptFuncUtil.fill_eListDataGrid_FormletData();

			ClicksUtil.clickLinks("Submission Summary");
			ClicksUtil.clickButtons(IClicksConst.completeBtn);

			ClicksUtil.clickLinks(IClicksConst.backToApplicantsListLnk);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	@Test(groups = { "SetupReportingFunctionsNG" })
	public void submitProject() throws Exception {

		try {

			Assert.assertTrue(ProjectUtil.openPOSubmissionForm(proj,
					IRptFuncConst.reportFunc_SubmissionA[0][0],
					"Open Projects", "Latest Version", "All"));

			RptFuncUtil.fill_Dropdown_FieldsData();

			ClicksUtil.clickLinks("Submission Summary");
			ClicksUtil.clickButtons(IClicksConst.submitBtn);
			ClicksUtil.clickLinks(IClicksConst.backToApplicantSubListLnk);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	@Test(groups = { "SetupReportingFunctionsNG" },dependsOnMethods="submitProject")
	public void assignOfficers() throws Exception {

		try {

			proj.assignOfficers(new String[][] { {
					IPreTestConst.Groups[0][0][0],
					IPreTestConst.Groups[0][1][0] } });

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	@Test(groups = { "SetupReportingFunctionsNG" },dependsOnMethods="assignOfficers")
	public void reviewSubmission() throws Exception {

		try {

			// Reviewer 2 & 4 do not submit

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.loginReviewer("2");

			log.debug("Reviewer 2 Logged In");

			Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList(
					proj, IRptFuncConst.reportFunc_ReviewA[0][0],
					"Ready", "Submission Summary", false));

			RptFuncUtil.fill_MinAndMaxProperties_FieldsData();

			ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.loginReviewer("4");

			log.debug("Reviewer 3 Logged In");

			Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList(
					proj, IRptFuncConst.reportFunc_ReviewA[0][0],
					"Ready", "Submission Summary", false));

			RptFuncUtil.fill_MinAndMaxProperties_FieldsData();

			ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);

			// Reviewers 1, 3 & 5 will submit

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.loginReviewer("1");

			log.debug("Reviewer 1 Logged In");

			Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList(
					proj, IRptFuncConst.reportFunc_ReviewA[0][0],
					"Ready", "Submission Summary", false));

			RptFuncUtil.fill_MinAndMaxProperties_FieldsData();

			Assert.assertTrue(ClicksUtil.clickLinks("Submission Summary"), "FAIL: Could not open Submission Summary!");
			GeneralUtil.takeANap(0.5);
			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn), "FAIL: Could not open Submit!");

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.loginReviewer("3");

			log.debug("Reviewer 3 Logged In");

			Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList(
					proj, IRptFuncConst.reportFunc_ReviewA[0][0],
					"Ready", "Submission Summary", false));

			RptFuncUtil.fill_MinAndMaxProperties_FieldsData();

			Assert.assertTrue(ClicksUtil.clickLinks("Submission Summary"), "FAIL: Could not open Submission Summary!");
			GeneralUtil.takeANap(0.5);
			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn), "FAIL: Could not open Submit!");

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.loginReviewer("5");

			log.debug("Reviewer 5 Logged In");

			Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList(
					proj, IRptFuncConst.reportFunc_ReviewA[0][0],
					"Ready", "Submission Summary", false));

			RptFuncUtil.fill_MinAndMaxProperties_FieldsData();

			Assert.assertTrue(ClicksUtil.clickLinks("Submission Summary"), "FAIL: Could not open Submission Summary!");
			GeneralUtil.takeANap(0.5);
			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.submitBtn), "FAIL: Could not open Submit!");

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	@Test(groups = { "SetupReportingFunctionsNG" },dependsOnMethods="reviewSubmission")
	public void submitAward() throws Exception {

		try {

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.logInSuper();

			Assert.assertTrue(ProjectUtil.openPOAwardFormletInList(proj,
					IRptFuncConst.reportFunc_AwardCrit[0][0],
					"Submission Summary"),
					"FAIL: Could not Fill Award Schedule Form");

			RptFuncUtil.fill_SubmissionSchedule(proj, 2,
					IRptFuncConst.reportFunc_initialClaim);

			ClicksUtil.clickLinks("Submission Summary");
			ClicksUtil.clickButtons(IClicksConst.submitBtn);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	@Test(groups = { "SetupReportingFunctionsNG" },dependsOnMethods="submitAward")
	public void submitClaim() throws Exception {

		try {

			proj.setClaimNumber(1);

			Assert.assertTrue(ProjectUtil.openInitialClaimForm(proj,
					IRptFuncConst.reportFunc_initialClaim[0][0],
					"Open Projects", "Latest Version", "Ready"));

			RptFuncUtil.fill_TypesProperties_FieldsData();
			RptFuncUtil.fill_eListDropdown_FormletData();

			ClicksUtil.clickLinks("Submission Summary");
			ClicksUtil.clickButtons(IClicksConst.submitBtn);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	@Test(groups = { "SetupReportingFunctionsNG" },dependsOnMethods="submitClaim")
	public void approveClaim() throws Exception {

		try {

			// Approvers 2 & 4 do not submit
			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.loginApprover("2");
			log.debug("Approver 2 Logged In");

			Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList(
					proj, IRptFuncConst.reportFunc_ApprovA[0][0],
					"Ready", "Submission Summary", false));

			RptFuncUtil.fill_NoProperties_FieldsData();
			RptFuncUtil.fill_ApprovalDropdown_FieldsData();

			ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.loginApprover("4");
			log.debug("Approver 4 Logged In");

			Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList(
					proj, IRptFuncConst.reportFunc_ApprovA[0][0],
					"Ready", "Submission Summary", false));

			RptFuncUtil.fill_NoProperties_FieldsData();
			RptFuncUtil.fill_ApprovalDropdown_FieldsData();

			ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);

			// Approvers 1, 3 & 5 will submit
			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.loginApprover("1");
			log.debug("Approver 1 Logged In");

			Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList(
					proj, IRptFuncConst.reportFunc_ApprovA[0][0],
					"Ready", "Submission Summary", false));

			RptFuncUtil.fill_NoProperties_FieldsData();
			RptFuncUtil.fill_ApprovalDropdown_FieldsData();

			ClicksUtil.clickLinks("Submission Summary");
			ClicksUtil.clickButtons(IClicksConst.submitBtn);

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.loginApprover("3");
			log.debug("Approver 3 Logged In");

			Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList(
					proj, IRptFuncConst.reportFunc_ApprovA[0][0],
					"Ready", "Submission Summary", false));

			RptFuncUtil.fill_NoProperties_FieldsData();
			RptFuncUtil.fill_ApprovalDropdown_FieldsData();

			ClicksUtil.clickLinks("Submission Summary");
			ClicksUtil.clickButtons(IClicksConst.submitBtn);

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.loginApprover("5");
			log.debug("Approver 5 Logged In");

			Assert.assertTrue(ProjectUtil.openEvaluateSubmissionFormletInList(
					proj, IRptFuncConst.reportFunc_ApprovA[0][0],
					"Ready", "Submission Summary", false));

			RptFuncUtil.fill_NoProperties_FieldsData();
			RptFuncUtil.fill_ApprovalDropdown_FieldsData();

			ClicksUtil.clickLinks("Submission Summary");
			ClicksUtil.clickButtons(IClicksConst.submitBtn);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

}
