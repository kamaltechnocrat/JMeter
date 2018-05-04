/**
 * 
 */
package test_Suite.tests.r2_8.lbf_Function.lbfOnSync;

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
public class LBF_AdminFrm_MoreFields {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/	
	Class<?> ownClass = this.getClass();
	
	private Log log = LogFactory.getLog(ownClass);

	LBF lbf;
	
	String iPASFormIdent = ILBFunctionConst.lbf_IPASMoreElists[1][0];
	
	String possFormName = ILBFunctionConst.lbf_POSubmissionMoreAttachments[0][1];
	
	String possStepName = ILBFunctionConst.lbf_POSubmissionMoreAttachments[0][0];
	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------

		lbf = new LBF(ILBFunctionConst.ESyncTypes.more.ordinal(),
				EProjectType.pre_Award, 2, 0, EeFormsIdentifier.admin);

		lbf.initFopp(ILBFunctionConst.postFixIdentifier[lbf.getSyncType()]);

		ClicksUtil.clickLinks(lbf.fopp.getProgFullIdent());
		
		lbf.setEFormData(ILBFunctionConst.lbf_SubmissionMoreElists[0][0]);
		
		lbf.setEFormData(ILBFunctionConst.lbf_StandardAwardMoreSchedules[0][0]);

		lbf.setEProjType(EProjectType.post_Award);

		lbf.setEFormData(ILBFunctionConst.lbf_IPASMoreElists[0][0]);
		
		lbf.setEFormData(ILBFunctionConst.lbf_POSubmissionMoreAttachments[0][0]);

		lbf.fillAdminForm("");

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

		lbf.initProj(true, true);

		lbf.submitProject("Submission", "Ready");

		lbf.openSubmissionForm();

	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "a_submitAndOpenApplicantSubmission", dataProvider = "formletTypes")
	public void b_testRowsEntriesOnSubmissionEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods="b_testRowsEntriesOnSubmissionEFormNG", alwaysRun=true)
	public void d_submitAndOpenAwardForm() throws Exception {

		lbf.returnFromAnyList();
		
		lbf.proj.createNewPOProjectOnly(true);

		lbf.submitProject("PO Submission", "Ready");
		
		lbf.submitProject("Standard Agreement", "Ready");

		lbf.assignOfficer();

		lbf.openAwardForm(ILBFunctionConst.lbf_StandardAwardMoreSchedules[0][0]);
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "d_submitAndOpenAwardForm", dataProvider = "formletTypes")
	public void e_testRowsEntriesOnStandardEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}
	
	@Test(groups = { "EFormsNG" },dependsOnMethods="e_testRowsEntriesOnStandardEFormNG", alwaysRun=true)
	public void f_submitAndOpenIPAS() throws Exception {

		lbf.returnFromAnyList();
		
		lbf.proj.createNewPOProjectOnly(true);

		lbf.submitProject("PO Submission", "Ready");
		
		lbf.submitProject("Award", "Ready");

		lbf.assignOfficer();
		
		lbf.proj.submitStandardAgreement(iPASFormIdent, 2, true, "");

		lbf.proj.setClaimNumber(1);

		ProjectUtil.openInitialClaimForm(lbf.proj,
				ILBFunctionConst.lbf_IPASSource[0][0], "Open Projects",
				"Latest Version", "Ready");		
	}

	@Test(groups = { "EFormsNG" },dependsOnMethods="f_submitAndOpenIPAS", dataProvider = "formletTypes")
	public void g_testRowsEntriesOnIPASEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods="g_testRowsEntriesOnIPASEFormNG", enabled=false)
	public void completeIPASAndSubmit() throws Exception {
		
		lbf.submitForm();
		
		ProjectUtil.openSubmissionInMyAssignedSubmissionList(lbf.proj, possStepName, "Open Projects");
		
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "completeIPASAndSubmit", dataProvider = "formletTypes", enabled=false)
	public void testRowsEntriesOnPOSSEFormNG(ILBFunctionConst.EFormletTypes eTypes) throws Exception {
		
		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}

	@DataProvider(name = "formletTypes")
	public Object[][] generateFormletTypes(Method method) {

		Object[][] result = null;

		if (method.getName() == "e_testRowsEntriesOnStandardEFormNG") {
			result = new Object[][] {
					new Object[] { ILBFunctionConst.EFormletTypes.subSchedule } };

		} else if (method.getName() == "b_testRowsEntriesOnSubmissionEFormNG"
				|| method.getName() == "g_testRowsEntriesOnIPASEFormNG") {
			result = new Object[][] {
					new Object[] { ILBFunctionConst.EFormletTypes.noProp },
					new Object[] { ILBFunctionConst.EFormletTypes.typeProp },
					new Object[] { ILBFunctionConst.EFormletTypes.minMaxProp },
					new Object[] { ILBFunctionConst.EFormletTypes.dGrid } };

		} else if (method.getName() == "testRowsEntriesOnPOSSEFormNG") {
			result = new Object[][] { new Object[] { ILBFunctionConst.EFormletTypes.attchments } };
		}

		return result;

	}


}
