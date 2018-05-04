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
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.AmendmentsUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class LBF_Sync_SubFO {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/	
	Class<?> ownClass = this.getClass();
	
	private Log log = LogFactory.getLog(ownClass);

	LBF lbf;
	
	String registrant = "/qa_Registrant1/";

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
		
		lbf.registerAndCreateFoProjAndOpenSubmission(ILBFunctionConst.lbf_SubmissionSource[0][0]);
		
		lbf.fillAndSubmitApplicantSubmission();
		
		lbf.submitFOSubmission("A LBF PA Submission B", "Standard Agreement");

	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		lbf = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());

	}
	
	@Test(groups = { "EFormsNG" })
	public void addEntriesAndSubmitAward() throws Exception {

		GeneralUtil.switchToPO();

		lbf.assignOfficerFO();

		lbf.openAwardForm(ILBFunctionConst.lbf_StandardAwardEqualELists[0][0]);

		lbf.setRowsAndIndexes(1, 3);

		lbf.fillAndSubmitStandardAward("");

	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods = "addEntriesAndSubmitAward")
	public void requestAmendmentOnSubmission() throws Exception {

		requestAmendmentOnApplicantSubmission();
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "requestAmendmentOnSubmission")
	public void re_SubmitApplicantionAndOpenAward() throws Exception {
		
		GeneralUtil.switchToFO();
		
		lbf.openFOSubmission(ILBFunctionConst.lbf_SubmissionEqualELists[0][0]);

		lbf.setRowsAndIndexes(1, 2);

		lbf.fillAndSubmitApplicantSubmission();
		
		lbf.submitFOSubmission("A LBF PA Submission B", "Standard Agreement");
		
		GeneralUtil.switchToPO();

		lbf.openAwardForm(ILBFunctionConst.lbf_StandardAwardEqualELists[0][0]);

		lbf.setRowsAndIndexes(3, 0);
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "re_SubmitApplicantionAndOpenAward", dataProvider = "formletTypes")
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

	@DataProvider(name = "formletTypes")
	public Object[][] generateFormletTypes() {

		Object[][] result = null;

		result = new Object[][] {
				new Object[] { ILBFunctionConst.EFormletTypes.noProp },
				new Object[] { ILBFunctionConst.EFormletTypes.typeProp },
				new Object[] { ILBFunctionConst.EFormletTypes.minMaxProp },
				new Object[] { ILBFunctionConst.EFormletTypes.dGrid }, 
				new Object[] { ILBFunctionConst.EFormletTypes.attchments }};

		return result;

	}
	
	private void requestAmendmentOnApplicantSubmission() throws Exception {

		String dd = GeneralUtil.setDayofMonth(3);

		Assert.assertTrue(AmendmentsUtil.issueAmendment(new String[] {
				lbf.foProj.getProjFullName(),
				ILBFunctionConst.lbf_SubmissionEqualELists[0][0],
				registrant, dd,
				"Test Re-Execute on Previous Amendment", "This a Comment" }, lbf.foProj));

	}


}
