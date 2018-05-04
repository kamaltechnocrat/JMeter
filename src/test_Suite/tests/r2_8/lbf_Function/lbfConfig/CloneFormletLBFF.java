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
import org.testng.annotations.BeforeClass;
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
public class CloneFormletLBFF {

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
	EFormField eFormField;
	EFormFieldFunctions eFormFieldFunc;
	FormletFunctions formletFunc;
	String strFormletFuncProp[];

	String tempEFormFieldId = null;
	String tempFormletId = null;
	String tempEFormId = null;

	String tempDefaultFormlet = null;

	String formleTitlePostFix = " eList BF";
	String formleIdentPostFix = "-eList-BF";

	String subFormleTitlePostFix = " Sub eList BF";
	String subFormleIdentPostFix = "-Sub-eList-BF";
	
	ArrayList<String> formletIdents;
	
	String randString;

	String preFix = "LBF-";
	String postFix = "";
	
	int formletIndex = - 1;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------

		subForm = new EForm(IEFormsConst.submission_FormTypeName, preFix);

		subForm.setEFormSubType(IEFormsConst.applicantsubmission_FormTypeName);

		subForm.setEFormTitle("Applicant Submission LBF");

		Assert
				.assertTrue(subForm.createEForm(),
						"FAIL: Could not Create eForm");
		
		randString = subForm.getScrumble();

		formlet = new Formlet(subForm,
				IFormletsConst.formletTypeName_SubmissionScheduleList,
				IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		Assert.assertTrue(formlet.initFormletWithDetails(
				"Submission Schedule List",
				IFormletsConst.formletTypeName_SubmissionScheduleList,
				formleTitlePostFix, formleIdentPostFix),
				"FAIL: Could not add Attachment List Formlet");

		formletFunc = new FormletFunctions(subForm, formlet.getFormletType(),
				IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		Assert.assertTrue(formletFunc.addLBFFunction(formlet),
				"FAIL: Could not add LBF Function eForm List Formlet");

	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		formletFunc = null;
		formlet = null;
		
		formletIdents = null;
		
		subForm = null;
		
		EFormsUtil.returnToPlanner();
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = { "EFormsNG" })
	public void testCloningFormletNG() throws Exception {

		form = new EForm(IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		form.setEFormSubType(IEFormsConst.applicantWorkspace_FormTypeName);

		form.setEFormTitle("Applicant Profile LBF");

		Assert.assertTrue(form.createEForm());
		
		Assert.assertTrue(EFormsUtil.cloneFormlet(subForm.getEFormFullId(), formlet.getFormletId()));
		
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods = "testCloningFormletNG")
	public void testCloningExistingFormletNG() throws Exception {
		
		EFormsUtil.returnToPlanner();
		
		Assert.assertFalse(EFormsUtil.cloneFormlet(subForm.getEFormFullId(), formlet.getFormletId()));

	}



}
