/**
 * 
 */
package test_Suite.tests.r2_8.lbf_Function.lbfOnAmend;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EeFormsIdentifier;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.LBFunctionUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class LBF_NoSync_Sub {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/	
	Class<?> ownClass = this.getClass();
	
	private Log log = LogFactory.getLog(ownClass);

	LBF lbf;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------

		lbf = new LBF(ILBFunctionConst.ESyncTypes.noSync.ordinal(),
				EProjectType.pre_Award, 2, 0, EeFormsIdentifier.submission);

		lbf.initFopp(ILBFunctionConst.postFixIdentifier[lbf.getSyncType()]);

		ClicksUtil.clickLinks(lbf.fopp.getProgFullIdent());

		lbf.setEFormData(ILBFunctionConst.lbf_SubmissionSource[0][0]);

		lbf.setEFormData(ILBFunctionConst.lbf_StandardAwardSource[0][0]);

		lbf.setEProjType(EProjectType.post_Award);

		lbf.setEFormData(ILBFunctionConst.lbf_IPASSource[0][0]);

		lbf.initializeProjAndCreateOrg(true, true);

	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		lbf = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());

	}

	@Test(groups = { "EFormsNG" })
	public void addEntriesAndSubmitAgreement() throws Exception {

		submitApplicantSubmission();

		lbf.submitProject("Standard Agreement", "Ready");

		lbf.assignOfficer();

		lbf.openAwardForm(ILBFunctionConst.lbf_StandardAwardNoSyncELists[0][0]);

		lbf.setRowsAndIndexes(1, 2);

		lbf.fillAndSubmitStandardAward("");

	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "addEntriesAndSubmitAgreement")
	public void requestAmendmentOnFirstSubmission() throws Exception {

		requestAmendmentOnApplicantSubmission();
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "requestAmendmentOnFirstSubmission")
	public void re_SubmitApplicantionAndOpenAward() throws Exception {

		ProjectUtil.openSubmissionInMyAssignedSubmissionList(lbf.proj,
				ILBFunctionConst.lbf_SubmissionNoSyncELists[0][0],
				"Open Projects");

		lbf.setRowsAndIndexes(1, 3);

		lbf.fillAndSubmitApplicantSubmission();

		openApplicantSubmissionList();

		lbf.submitProject("Standard Agreement", "In Progress");

		lbf.openAwardForm(ILBFunctionConst.lbf_StandardAwardNoSyncELists[0][0]);

		lbf.setRowsAndIndexes(4, 0);
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "re_SubmitApplicantionAndOpenAward", dataProvider = "formletTypes")
	public void testRowsEntriesOnStandardEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}	

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "testRowsEntriesOnStandardEFormNG", alwaysRun = true)
	public void addEntryToIPASS() throws Exception {

		lbf.returnFromAnyList();

		lbf.setRowsAndIndexes(2, 0);

		submitApplicantSubmission();

		lbf.submitProject("Award", "Ready");

		lbf.assignOfficer();

		lbf.proj.submitStandardAgreement(ILBFunctionConst.lbf_IPASNoSyncElists[1][0], 2, true, "");

		lbf.proj.setClaimNumber(1);

		ProjectUtil.openInitialClaimForm(lbf.proj,
				ILBFunctionConst.lbf_IPASSource[0][0], "Open Projects",
				"Latest Version", "Ready");

		lbf.setRowsAndIndexes(1, 2);
		
		LBFunctionUtil.insertDataTo_ELists(lbf);
		
		lbf.returnFromAnyList();

	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "addEntryToIPASS")
	public void requestAmendmentOnSecondSubmission() throws Exception {
		
		lbf.proj.setClaimNumber(0);

		requestAmendmentOnApplicantSubmission();
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "requestAmendmentOnSecondSubmission")
	public void re_SubmitApplicantionAndOpenClaim() throws Exception {

		ProjectUtil.openSubmissionInMyAssignedSubmissionList(lbf.proj,
				ILBFunctionConst.lbf_SubmissionNoSyncELists[0][0],
				"Open Projects");

		lbf.setRowsAndIndexes(1, 3);

		lbf.fillAndSubmitApplicantSubmission();

		openApplicantSubmissionList();

		lbf.submitProject("Award", "In Progress");

		lbf.openAwardForm("Standard Award");

		lbf.submitForm();

		lbf.proj.setClaimNumber(1);

		ProjectUtil.openInitialClaimForm(lbf.proj,
				ILBFunctionConst.lbf_IPASSource[0][0], "Open Projects",
				"Latest Version", "In Progress");

		lbf.setRowsAndIndexes(4, 0);
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "re_SubmitApplicantionAndOpenClaim", dataProvider = "formletTypes")
	public void testRowsEntriesOnIPASEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}	

	@DataProvider(name = "formletTypes")
	public Object[][] generateFormletTypes() {

		Object[][] result = null;

		result = new Object[][] {
				new Object[] { ILBFunctionConst.EFormletTypes.noProp },
				new Object[] { ILBFunctionConst.EFormletTypes.typeProp },
				new Object[] { ILBFunctionConst.EFormletTypes.minMaxProp },
				new Object[] { ILBFunctionConst.EFormletTypes.dGrid }};

		return result;

	}

	private void submitApplicantSubmission() throws Exception {

		lbf.proj.createNewPOProjectOnly(true);

		lbf.submitProject("Submission", "Ready");

		lbf.openSubmissionForm();

		lbf.fillAndSubmitApplicantSubmission();

	}

	private void openApplicantSubmissionList() throws Exception {

		LBFunctionUtil.openApplicantSubmissionList(lbf.proj);
	}

	private void requestAmendmentOnApplicantSubmission() throws Exception {

		String dd = GeneralUtil.setDayofMonth(3);

		Assert.assertTrue(AmendmentsUtil.issueAmendment(new String[] {
				lbf.proj.getProjFullName(),
				ILBFunctionConst.lbf_SubmissionNoSyncELists[0][0],
				IPreTestConst.Groups[0][1][0], dd,
				"Test Re-Execute on Previous Amendment", "This a Comment" }, lbf.proj));

	}


}
