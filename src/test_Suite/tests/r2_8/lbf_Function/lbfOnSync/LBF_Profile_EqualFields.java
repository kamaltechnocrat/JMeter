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

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class LBF_Profile_EqualFields {

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

		GeneralUtil.setApplicantWorkspace(ILBFunctionConst.lbf_ProfileEFormSource[2]);

		lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
				EProjectType.pre_Award, 2, 0, EeFormsIdentifier.profile);

		lbf.initFopp(ILBFunctionConst.postFixIdentifier[lbf.getSyncType()]);

		ClicksUtil.clickLinks(lbf.fopp.getProgFullIdent());
		
		lbf.setEFormData(ILBFunctionConst.lbf_SubmissionSource[0][0]);
		
		lbf.setEFormData(ILBFunctionConst.lbf_StandardAwardSource[0][0]);

		lbf.initializeProjAndCreateOrg(true, true);

		lbf.fillApplicantProfile();
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		lbf = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());

	}

	@Test(groups = { "EFormsNG" })
	public void a_submitAndOpenApplicantSubmission() throws Exception {

		lbf.returnFromAnyList();
		
		lbf.proj.createNewPOProjectOnly(true);

		lbf.submitProject("Submission", "Ready");

		lbf.openSubmissionForm();
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "a_submitAndOpenApplicantSubmission", dataProvider = "formletTypes")
	public void b_testRowsEntriesOnSubmissionEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "a_submitAndOpenApplicantSubmission", dataProvider = "formletTypes")
	public void c_verifyFieldsAnswersOnSubmissionEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyFieldsAnswer());
	}

	@Test(groups = { "EFormsNG" },dependsOnMethods="c_verifyFieldsAnswersOnSubmissionEFormNG", alwaysRun=true)
	public void d_submitAndOpenStandardAgreementForm() throws Exception {

		lbf.returnFromAnyList();
		
		lbf.proj.createNewPOProjectOnly(true);

		lbf.submitProject("PO Submission", "Ready");
		
		lbf.submitProject("Standard Agreement", "Ready");

		lbf.assignOfficer();

		lbf.openAwardForm(ILBFunctionConst.lbf_StandardAwardEqualELists[0][0]);
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "d_submitAndOpenStandardAgreementForm", dataProvider = "formletTypes")
	public void e_testRowsEntriesOnStandardEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "d_submitAndOpenStandardAgreementForm", dataProvider = "formletTypes")
	public void f_verifyFieldsAnswersOnStandardAgreementEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyFieldsAnswer());
	}

	@DataProvider(name = "formletTypes")
	public Object[][] generateFormletTypes() {

		Object[][] result = null;
		
		result = new Object[][] {
				new Object[] { ILBFunctionConst.EFormletTypes.noProp },
				new Object[] { ILBFunctionConst.EFormletTypes.typeProp },
				new Object[] { ILBFunctionConst.EFormletTypes.minMaxProp },
				new Object[] { ILBFunctionConst.EFormletTypes.dGrid },
				new Object[] { ILBFunctionConst.EFormletTypes.attchments } };

		return result;

	}


}
