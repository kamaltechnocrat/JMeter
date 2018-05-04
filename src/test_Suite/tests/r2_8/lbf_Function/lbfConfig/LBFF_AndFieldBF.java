/**
 * 
 */
package test_Suite.tests.r2_8.lbf_Function.lbfConfig;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.EFormFieldFunctions;
import test_Suite.lib.eForms.Formlet;
import test_Suite.lib.eForms.FormletFunctions;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class LBFF_AndFieldBF {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;
	EForm subForm;
	EForm adminForm;
	Formlet formlet;
	Formlet subFormlet;

	Formlet summaryFormlet;
	EFormField eFormField;
	EFormFieldFunctions eFormFieldFunc;
	FormletFunctions formletFunc;
	String strFormletFuncProp[];

	String tempEFormFieldId = null;
	String tempFormletId = null;
	String tempEFormId = null;

	static String tempDefaultFormlet;

	String formleTitlePostFix = " eList BF";
	String formleIdentPostFix = "-eList-BF";

	String subFormleTitlePostFix = " Sub eList BF";
	String subFormleIdentPostFix = "-Sub-eList-BF";

	ArrayList<String> formletIdents;

	String preFix = "LBF-";
	String postFix = "";

	int formletIndex = -1;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());
		
		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------
		
		createEForm();

	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	private void createEForm() throws Exception {

		subForm = new EForm(IEFormsConst.submission_FormTypeName,
				IEFormsConst.applicantsubmission_FormTypeName, preFix);

		subForm.setEFormTitle("Applicant Submission LBF");

		Assert
				.assertTrue(subForm.createEForm(),
						"FAIL: Could not Create eForm");

		summaryFormlet = new Formlet(subForm,
				IFormletsConst.formletTypeName_SubmissionSummary);

		Assert.assertTrue(summaryFormlet.initFormletWithDetails(
				"Submission Summery",
				IFormletsConst.formletTypeName_SubmissionSummary,
				formleTitlePostFix, formleIdentPostFix),
				"FAIL: Could not add Submisison Summary Formlet");
		
	}
	
	@Test(groups = { "EFormsNG" }, dataProvider="Formlet-Names")
	public void testPublishingEFormWith_LBF_And_BF_eFormFieldNG(String formId, String formletId, boolean expected) throws Exception {		
		
		tempDefaultFormlet = "Formlet: " + formletId;
		Assert.assertTrue(EFormsUtil.cloneFormlet(formId, formletId), "FAIL: could not Clone Formlet");
		
		EFormsUtil.returnToPlanner();
		
		Assert.assertEquals(subForm.publishEFormAndCheckErrors(formletId), expected, "FAIL: Unexpected result!");
	}
	
	@AfterMethod(groups = { "EFormsNG" }, alwaysRun=true)
	public void cleanupPlanner() throws Exception {
		
		EFormsUtil.returnToPlanner();
		
		EFormsUtil.deleteFormPlannerNode(tempDefaultFormlet);
	}
	
	@DataProvider(name="Formlet-Names")
	public Object[][] generateFormletNames(){
		
		return new Object[][] {
				new Object[] {"LBF-Applicant Profile-BF-eFormFields","No-Propreties-eFormFields-eList-BF",false},
				new Object[] {"LBF-Applicant Profile-BF-eFormFields","Data-Grid-eFormFields-eList-BF",false},
				new Object[] {"LBF-Applicant Profile-BF-eFormFields","Attachment-List-eList-BF",false},
				new Object[] {"LBF-Applicant Profile-BF-eFormFields","PA-Submission-Schedule-eList-BF",false},
				new Object[] {"LBF-Applicant Profile-BF-eFormFields","Min-And-Max-Propreties-eFormFields-eList-BF",true}};
		
	}



}
