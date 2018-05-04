/**
 * 
 */
package test_Suite.tests.r2_8.lbf_Function.lbfOnFilter;

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
public class LBF_Filter_IPASS {

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
				EProjectType.pre_Award, 4, 0, EeFormsIdentifier.claim);

		lbf.initFopp(ILBFunctionConst.postFixIdentifier[lbf.getSyncType()]);

		ClicksUtil.clickLinks(lbf.fopp.getProgFullIdent());

		lbf.setEProjType(EProjectType.post_Award);

		lbf.setEFormData(ILBFunctionConst.lbf_IPASSource[0][0]);
		
		lbf.setEFormData(ILBFunctionConst.lbf_POSubmissionSource[0][0]);

		lbf.initProj(true, true);

		lbf.submitProject("PO Submission", "Ready");
		
		lbf.submitProject("Award", "Ready");

		lbf.assignOfficer();
		
		lbf.proj.submitStandardAgreement(ILBFunctionConst.lbf_IPASFilteringElists[1][0], 2, true, "");

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
	public void submitClaimAndOpenPOSS() throws Exception {

		lbf.fillAndSubmitApplicantSubmission();
		
		ProjectUtil.openSubmissionInMyAssignedSubmissionList(lbf.proj, ILBFunctionConst.lbf_POSubmissionFilteringElists[0][0], "Open Projects");
		
		lbf.setRowsAndIndexes(2, 0);
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "submitClaimAndOpenPOSS", dataProvider = "formletTypes")
	public void testRowsEntriesOnPOSSEFormNG(ILBFunctionConst.EFormletTypes eTypes) throws Exception {
		
		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "testRowsEntriesOnPOSSEFormNG", dataProvider = "formletTypes")
	public void verifyFieldsAnswersOnPOSSEFormNG(ILBFunctionConst.EFormletTypes eTypes) throws Exception {

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


}
