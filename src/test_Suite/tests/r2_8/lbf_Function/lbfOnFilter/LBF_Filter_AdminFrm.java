                                                                                                                                                                                       /**
 * 
 */
package test_Suite.tests.r2_8.lbf_Function.lbfOnFilter;

import java.lang.reflect.Method;

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
public class LBF_Filter_AdminFrm {

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

		lbf = new LBF(ILBFunctionConst.ESyncTypes.filtering.ordinal(),
				EProjectType.pre_Award, 4, 0, EeFormsIdentifier.admin);

		lbf.initFopp(ILBFunctionConst.postFixIdentifier[lbf.getSyncType()]);

		ClicksUtil.clickLinks(lbf.fopp.getProgFullIdent());

		lbf.setEFormData(ILBFunctionConst.lbf_SubmissionSource[0][0]);

		lbf.setEFormData(ILBFunctionConst.lbf_StandardAwardSource[0][0]);

		lbf.setEProjType(EProjectType.post_Award);

		lbf.setEFormData(ILBFunctionConst.lbf_IPASSource[0][0]);

		lbf.fillAdminForm("");

		lbf.setRowsAndIndexes(2, 0);
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		lbf = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());

	}

	@Test(groups = { "EFormsNG" })
	public void submitAndOpenApplicantSubmission() throws Exception {

		lbf.returnFromAnyList();

		lbf.initProj(true, true);

		lbf.submitProject("Submission", "Ready");

		lbf.openSubmissionForm();

	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "submitAndOpenApplicantSubmission", dataProvider = "formletTypes")
	public void testRowsEntriesOnSubmissionEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "testRowsEntriesOnSubmissionEFormNG", dataProvider = "formletTypes")
	public void verifyFieldsAnswersOnSubmissionEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyFieldsAnswer());
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "verifyFieldsAnswersOnSubmissionEFormNG", alwaysRun = true)
	public void submitAndOpenAwardForm() throws Exception {

		lbf.returnFromAnyList();

		lbf.proj.createNewPOProjectOnly(true);

		lbf.submitProject("PO Submission", "Ready");

		lbf.submitProject("Standard Agreement", "Ready");

		lbf.assignOfficer();

		lbf.openAwardForm(ILBFunctionConst.lbf_StandardAwardFilteringELists[0][0]);
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "submitAndOpenAwardForm", dataProvider = "formletTypes")
	public void testRowsEntriesOnAwardEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "testRowsEntriesOnAwardEFormNG", dataProvider = "formletTypes")
	public void verifyFieldsAnswersOnAwardEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyFieldsAnswer());
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "verifyFieldsAnswersOnAwardEFormNG", alwaysRun = true)
	public void submitAwardAndOpenClaims() throws Exception {

		lbf.returnFromAnyList();

		lbf.proj.createNewPOProjectOnly(true);

		lbf.submitProject("PO Submission", "Ready");

		lbf.submitProject("Award", "Ready");

		lbf.assignOfficer();

		lbf.proj.submitStandardAgreement(
				ILBFunctionConst.lbf_IPASFilteringElists[1][0], 2, true, "");

		lbf.proj.setClaimNumber(1);

		ProjectUtil.openInitialClaimForm(lbf.proj,
				ILBFunctionConst.lbf_IPASSource[0][0], "Open Projects",
				"Latest Version", "Ready");
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

	@DataProvider(name = "formletTypes")
	public Object[][] generateFormletTypes(Method method) {

		Object[][] result = null;

		if (method.getName() == "testRowsEntriesOnAwardEFormNG"
				|| method.getName() == "verifyFieldsAnswersOnAwardEFormNG") {
			result = new Object[][] {
					new Object[] { ILBFunctionConst.EFormletTypes.noProp },
					new Object[] { ILBFunctionConst.EFormletTypes.typeProp },
					new Object[] { ILBFunctionConst.EFormletTypes.minMaxProp },
					new Object[] { ILBFunctionConst.EFormletTypes.dGrid },
					new Object[] { ILBFunctionConst.EFormletTypes.subSchedule },
					new Object[] { ILBFunctionConst.EFormletTypes.attchments } };

		} else if (method.getName() == "testRowsEntriesOnSubmissionEFormNG"
				|| method.getName() == "verifyFieldsAnswersOnSubmissionEFormNG"
				|| method.getName() == "testRowsEntriesOnIPASEFormNG"
				|| method.getName() == "verifyFieldsAnswersOnIPASEFormNG") {
			result = new Object[][] {
					new Object[] { ILBFunctionConst.EFormletTypes.noProp },
					new Object[] { ILBFunctionConst.EFormletTypes.typeProp },
					new Object[] { ILBFunctionConst.EFormletTypes.minMaxProp },
					new Object[] { ILBFunctionConst.EFormletTypes.dGrid },
					new Object[] { ILBFunctionConst.EFormletTypes.attchments } };
		}

		return result;

	}


}
