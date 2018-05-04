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
public class LBF_NoSync_IPASS {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/	
	Class<?> ownClass = this.getClass();
	
	private Log log = LogFactory.getLog(ownClass);

	LBF lbf;
	
	String iPASFormIdent = "LBF-Post Award Submission-NoSync-eLists";
	
	String iPASFormName = "Initial-Claim";
	
	String possFormName = "PO Submission";

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------

		lbf = new LBF(ILBFunctionConst.ESyncTypes.noSync.ordinal(),
				EProjectType.pre_Award, 2, 0, EeFormsIdentifier.claim);

		lbf.initFopp(ILBFunctionConst.postFixIdentifier[lbf.getSyncType()]);

		ClicksUtil.clickLinks(lbf.fopp.getProgFullIdent());

		lbf.setEProjType(EProjectType.post_Award);

		lbf.setEFormData(ILBFunctionConst.lbf_IPASSource[0][0]);
		
		lbf.setEFormData(ILBFunctionConst.lbf_POSubmissionSource[0][0]);

		lbf.initProj(true, true);

		lbf.submitProject("PO Submission", "Ready");
		
		lbf.submitProject("Award", "Ready");

		lbf.assignOfficer();
		
		lbf.proj.submitStandardAgreement(iPASFormIdent, 2, true, "");

		lbf.proj.setClaimNumber(1);

		ProjectUtil.openInitialClaimForm(lbf.proj,
				ILBFunctionConst.lbf_IPASSource[0][0], "Open Projects",
				"Latest Version", "Ready");
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		lbf = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());

	}
	
	@Test(groups = { "EFormsNG" })
	public void submitClaimAddEntriesToPOSSAndSubmit() throws Exception {

		lbf.fillAndSubmitApplicantSubmission();
		
		ProjectUtil.openSubmissionInMyAssignedSubmissionList(lbf.proj, possFormName, "Open Projects");
		
		lbf.setRowsAndIndexes(1,2);
		
		lbf.fillPOSSAndSubmit("");
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods = "submitClaimAddEntriesToPOSSAndSubmit")
	public void requestAmendmentAndRe_SubmitClaim() throws Exception {

		String dd = GeneralUtil.setDayofMonth(3);

		Assert.assertTrue(AmendmentsUtil.issueAmendment(new String[] {
				lbf.proj.getProjFullName(),
				iPASFormName,
				IPreTestConst.Groups[0][1][0], dd,
				"Test Re-Execute on Previous Amendment", "This a Comment" }, lbf.proj));
		
		ProjectUtil.openSubmissionInMyAssignedSubmissionList(lbf.proj, iPASFormName, "Open Projects");
		
		lbf.setRowsAndIndexes(1,3);
		
		lbf.fillAndSubmitApplicantSubmission();
		
		ProjectUtil.openSubmissionInMyAssignedSubmissionList(lbf.proj, possFormName, "Open Projects");
		
		lbf.setRowsAndIndexes(4,0);
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "requestAmendmentAndRe_SubmitClaim", dataProvider = "formletTypes")
	public void testRowsEntriesOnPOSSEFormNG(ILBFunctionConst.EFormletTypes eTypes) throws Exception {
		
		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}

	//TODO: make new utils for unordered list
	
	/*@Test(groups = { "EFormsNG" }, dependsOnMethods = "testRowsEntriesOnPOSSEFormNG", dataProvider = "formletTypes")
	public void verifyFieldsAnswersOnPOSSEFormNG(ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyFieldsAnswer());
	}*/

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


}
