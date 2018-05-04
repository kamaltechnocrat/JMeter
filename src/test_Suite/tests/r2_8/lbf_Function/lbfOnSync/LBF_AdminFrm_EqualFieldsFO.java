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

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class LBF_AdminFrm_EqualFieldsFO {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<?> ownClass = this
			.getClass();

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
				EProjectType.pre_Award, 2, 0, EeFormsIdentifier.admin);

		lbf.initFopp(ILBFunctionConst.postFixIdentifier[lbf.getSyncType()]);

		ClicksUtil.clickLinks(lbf.fopp.getProgFullIdent());
		
		lbf.setEFormData(ILBFunctionConst.lbf_SubmissionSource[0][0]);
		
		lbf.setEProjType(EProjectType.post_Award);
		
		lbf.setEFormData(ILBFunctionConst.lbf_IPASSource[0][0]);

		lbf.fillAdminForm("");
		
		lbf.initLists();
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
		
		lbf.registerAndCreateFoProjAndOpenSubmission(ILBFunctionConst.lbf_SubmissionSource[0][0]);		
		
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

		lbf.foProj.submitStandardAgreement(iPASFormIdent, 2, true, "");
		
		lbf.foProj.setClaimNumber(1);
		
		GeneralUtil.switchToFO();
		
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

	@Test(groups = { "EFormsNG" }, enabled=false)
	public void testRowsEntriesOnAwardEFormNG() throws Exception {
		
		log.info("Just for Testing");
	}

	@Test(groups = { "EFormsNG" }, enabled=false)
	public void verifyFieldsAnswersOnAwardEFormNG() throws Exception {

		log.info("Just for Testing");
	}

	@Test(groups = { "EFormsNG" }, enabled=false)
	public void testRowsEntriesOnIPASEFormNG() throws Exception {

		log.info("Just for Testing");
	}

	@Test(groups = { "EFormsNG" }, enabled=false)
	public void verifyFieldsAnswersOnIPASEFormNG() throws Exception {

		log.info("Just for Testing");
	}


}
