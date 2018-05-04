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
public class LBF_Award_EqualFields {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/	
	Class<?> ownClass = this.getClass();
	
	private Log log = LogFactory.getLog(ownClass);
	LBF lbf;
	
	String iPASFormIdent = "LBF-Post Award Submission-Equal-eLists";
	String possFormName = "PO Submission";

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------

		lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
				EProjectType.pre_Award, 2, 0, EeFormsIdentifier.standardAgreement);

		lbf.initFopp(ILBFunctionConst.postFixIdentifier[lbf.getSyncType()]);

		ClicksUtil.clickLinks(lbf.fopp.getProgFullIdent());

		lbf.setEFormData(ILBFunctionConst.lbf_StandardAwardSource[0][0]);

		lbf.setEProjType(EProjectType.post_Award);

		lbf.setEFormData(ILBFunctionConst.lbf_IPASSource[0][0]);
		
		lbf.setEFormData(ILBFunctionConst.lbf_POSubmissionSource[0][0]);

		lbf.initProj(true, true);

		lbf.submitProject("PO Submission", "Ready");

		lbf.submitProject("Standard Agreement", "Ready");

		lbf.assignOfficer();		
		

		lbf.openAwardForm(ILBFunctionConst.lbf_StandardAwardEqualELists[0][0]);

		lbf.fillAndSubmitStandardAward("");
		
		lbf.proj.submitStandardAgreement(iPASFormIdent, 2, true, "");

		lbf.proj.setClaimNumber(2);

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

	@Test(groups = { "EFormsNG" }, dataProvider = "formletTypes")
	public void testRowsEntriesOnIPASEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyRows());
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods="testRowsEntriesOnIPASEFormNG", dataProvider = "formletTypes")
	public void verifyFieldsAnswersOnIPASEFormNG(
			ILBFunctionConst.EFormletTypes eTypes) throws Exception {

		lbf.setEFormletsName(eTypes);

		Assert.assertTrue(lbf.verifyFieldsAnswer());
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods="verifyFieldsAnswersOnIPASEFormNG")
	public void completeIPASSAndSubmit() throws Exception {
		
		lbf.submitForm();
		
		ProjectUtil.openSubmissionInMyAssignedSubmissionList(lbf.proj, possFormName, "Open Projects");
		
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "completeIPASSAndSubmit", dataProvider = "formletTypes")
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
	public Object[][] generateFormletTypes(Method method) {

		Object[][] result = null;
		
		if(method.getName() == "testRowsEntriesOnIPASEFormNG" || method.getName() == "verifyFieldsAnswersOnIPASEFormNG")
		{
			result = new Object[][] {
					new Object[] { ILBFunctionConst.EFormletTypes.noProp },
					new Object[] { ILBFunctionConst.EFormletTypes.typeProp },
					new Object[] { ILBFunctionConst.EFormletTypes.minMaxProp },
					new Object[] { ILBFunctionConst.EFormletTypes.dGrid }, };
			
		} else if(method.getName() == "testRowsEntriesOnPOSSEFormNG" || method.getName() == "verifyFieldsAnswersOnPOSSEFormNG")
		{
			result = new Object[][] {
					new Object[] { ILBFunctionConst.EFormletTypes.noProp },
					new Object[] { ILBFunctionConst.EFormletTypes.typeProp },
					new Object[] { ILBFunctionConst.EFormletTypes.minMaxProp },
					new Object[] { ILBFunctionConst.EFormletTypes.dGrid },
					new Object[] { ILBFunctionConst.EFormletTypes.subSchedule } };
			
		}

		return result;

	}


}
