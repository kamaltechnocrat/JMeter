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
import test_Suite.utils.eForms.LBFunctionUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class LBF_Filter_ProfileFO {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);	

	LBF lbf;
	
	String orgIndex = "7";

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------

		GeneralUtil.setApplicantWorkspace(ILBFunctionConst.lbf_ProfileEFormSource[2]);

		lbf = new LBF(ILBFunctionConst.ESyncTypes.filtering.ordinal(),
				EProjectType.pre_Award, 4, 0, EeFormsIdentifier.profile);
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		lbf = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = { "EFormsNG" })
	public void prepareDataAndForms() throws Exception {

		lbf.initFopp(ILBFunctionConst.postFixIdentifier[lbf.getSyncType()]);

		ClicksUtil.clickLinks(lbf.fopp.getProgFullIdent());
		
		lbf.setEFormData(ILBFunctionConst.lbf_SubmissionSource[0][0]);
		
		lbf.setEProjType(EProjectType.post_Award);
		
		lbf.setEFormData(ILBFunctionConst.lbf_IPASSource[0][0]);

		lbf.registerAndOpenApplicantProfile(orgIndex);
		
		LBFunctionUtil.isFOApplicantProfileFilled = false;

		lbf.fill_FO_ApplicantProfile();

		lbf.setRowsAndIndexes(2, 0);
		
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods="prepareDataAndForms")
	public void submitAndOpenApplicantSubmission() throws Exception {

		lbf.returnFromAnyList();
		
		lbf.foProj.createFOProject();
		
		lbf.submitFOSubmission("A LBF PA Submission", "Submission");
		
		GeneralUtil.switchToFOOnly();
		
		GeneralUtil.loginAnyFO("front" + orgIndex);
		
		lbf.openFOSubmission(ILBFunctionConst.lbf_SubmissionSource[0][0]);		
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

	@Test(groups = { "EFormsNG" },dependsOnMethods="verifyFieldsAnswersOnSubmissionEFormNG", alwaysRun=true)
	public void submitAndOpenIPASSForm() throws Exception {

		lbf.returnFromAnyList();
		
		lbf.foProj.createFOProject();

		lbf.submitFOSubmission("A LBF PA Submission", "PO Submission");
		
		lbf.submitFOSubmission("A LBF PA Submission B", "Award");
		
		GeneralUtil.switchToPO();

		lbf.assignOfficerFO();

		lbf.foProj.submitStandardAgreement(ILBFunctionConst.lbf_IPASFilteringElists[1][0], 2, true, "");
		
		lbf.foProj.setClaimNumber(1);
		
		GeneralUtil.switchToFOOnly();
		
		GeneralUtil.loginAnyFO("front" + orgIndex);
		
		lbf.openFOSubmission(ILBFunctionConst.lbf_IPASSource[0][0]);
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "submitAndOpenIPASSForm", dataProvider = "formletTypes")
	public void testRowsEntriesOnIPASSEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "testRowsEntriesOnIPASSEFormNG", dataProvider = "formletTypes")
	public void verifyFieldsAnswersOnIPASSEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyFieldsAnswer());
	}

	@DataProvider(name = "formletTypes")
	public Object[][] generateFormletTypes(Method method) {

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
