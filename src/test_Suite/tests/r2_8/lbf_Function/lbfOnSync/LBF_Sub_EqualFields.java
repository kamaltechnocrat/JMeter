/**
 * 
 */
package test_Suite.tests.r2_8.lbf_Function.lbfOnSync;

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
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class LBF_Sub_EqualFields {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	LBF lbf;
	
	String iPASFormIdent = "LBF-Post Award Submission-Equal-eLists";

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------

		lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
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
	
	private void submitApplicantSubmission() throws Exception {
		
		lbf.proj.createNewPOProjectOnly(true);

		lbf.submitProject("Submission", "Ready");

		lbf.openSubmissionForm();
		
		lbf.setRowsCount(2);
		
		lbf.setStartIndex(0);

		lbf.fillAndSubmitApplicantSubmission();		
	}
	
	@Test(groups = {"EFormsNG"})
	public void submitAndOpenAward() throws Exception {
		
		submitApplicantSubmission();
		
		lbf.submitProject("Standard Agreement", "Ready");

		lbf.assignOfficer();

		lbf.openAwardForm(ILBFunctionConst.lbf_StandardAwardEqualELists[0][0]);
	}

	@Test(groups = { "EFormsNG" },dependsOnMethods="submitAndOpenAward", dataProvider = "formletTypes")
	public void testRowsEntriesOnStandardEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}

	@Test(groups = { "EFormsNG" },dependsOnMethods="testRowsEntriesOnStandardEFormNG", dataProvider = "formletTypes")
	public void verifyFieldsAnswersOnStandardAgreementEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyFieldsAnswer());
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods="verifyFieldsAnswersOnStandardAgreementEFormNG")
	public void addEntriesAndSubmitAgreement() throws Exception {
		
		lbf.setRowsCount(1);
		
		lbf.setStartIndex(3);
		
		lbf.fillAndSubmitStandardAward("");
		
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods="addEntriesAndSubmitAgreement")
	public void submitAwardAndOpenClaims() throws Exception {
		
		lbf.returnFromAnyList();
		
		submitApplicantSubmission();
		
		lbf.submitProject("Award", "Ready");

		lbf.assignOfficer();

		lbf.proj.submitStandardAgreement(iPASFormIdent, 2, true, "");

		lbf.proj.setClaimNumber(1);

		ProjectUtil.openInitialClaimForm(lbf.proj,
				ILBFunctionConst.lbf_IPASSource[0][0], "Open Projects",
				"Latest Version", "Ready");
		
		lbf.setRowsCount(2);		
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "submitAwardAndOpenClaims", dataProvider = "formletTypes")
	public void testRowsEntriesOnIPASEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "testRowsEntriesOnIPASEFormNG", dataProvider = "formletTypes")
	public void verifyFieldsAnswersOnIPASEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {
		
		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyFieldsAnswer());		
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods="verifyFieldsAnswersOnIPASEFormNG")
	public void addEntriesAndSubmitIPASS() throws Exception {
		
		lbf.setRowsCount(1);
		
		lbf.setStartIndex(2);
		
		lbf.fillAndSubmitApplicantSubmission();		
	}

	@DataProvider(name = "formletTypes")
	public Object[][] generateFormletTypes() {

		Object[][] result = null;

		result = new Object[][] {
				new Object[] { ILBFunctionConst.EFormletTypes.noProp },
				new Object[] { ILBFunctionConst.EFormletTypes.typeProp },
				new Object[] { ILBFunctionConst.EFormletTypes.minMaxProp },
				new Object[] { ILBFunctionConst.EFormletTypes.dGrid }, };

		return result;
	}
}
