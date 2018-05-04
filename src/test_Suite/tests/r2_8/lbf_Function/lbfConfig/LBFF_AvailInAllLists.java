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
import test_Suite.constants.eForms.IFormletsFunctionsConst;
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
public class LBFF_AvailInAllLists {

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

		subForm = new EForm(IEFormsConst.submission_FormTypeName, preFix);

		subForm.setEFormSubType(IEFormsConst.applicantsubmission_FormTypeName);

		subForm.setEFormTitle("Applicant Submission LBF");

		Assert
				.assertTrue(subForm.createEForm(),
						"FAIL: Could not Create eForm");

		formlet = new Formlet(subForm,
				IFormletsConst.formletTypeName_SubmissionSummary,
				IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		Assert.assertTrue(formlet.initFormletWithDetails("Submission Summery",
				IFormletsConst.formletTypeName_SubmissionSummary,
				formleTitlePostFix, formleIdentPostFix),
				"FAIL: Could not add Attachment List Formlet");

	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {

		EFormsUtil.returnToPlanner();

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());

	}

	@Test(groups = { "EFormsNG" })
	public void testAddingLBFFunctionIntoEFormListFormletNG() throws Exception {

		EFormsUtil.returnToPlanner();

		Assert.assertTrue(initFormlet("eForm List Formlet"),
				"FAIL: Could not create List Type e.Form");

		Assert.assertTrue(addLBFFunction(formlet),
				"FAIL: Could not add LBF Function eForm List Formlet");

	}

	@Test(groups = { "EFormsNG" })
	public void testAddingLBFFunctionToAttachmentsListFormletNG()
			throws Exception {

		EFormsUtil.returnToPlanner();

		formlet = new Formlet(subForm,
				IFormletsConst.formletTypeName_AttachmentsList,
				IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		Assert.assertTrue(formlet.initFormletWithDetails("Attachment List",
				IFormletsConst.formletTypeName_AttachmentsList,
				formleTitlePostFix, formleIdentPostFix),
				"FAIL: Could not add Attachment List Formlet");

		Assert.assertTrue(addLBFFunction(formlet),
				"FAIL: Could not add LBF Function to Attachment List Formlet");

	}

	@Test(groups = { "EFormsNG" })
	public void testAddingLBFFunctionToSubmissionScheduleFormletNG()
			throws Exception {

		EFormsUtil.returnToPlanner();

		formlet = new Formlet(subForm,
				IFormletsConst.formletTypeName_SubmissionScheduleList,
				IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		Assert.assertTrue(formlet.initFormletWithDetails("Submission Schedule List",
				IFormletsConst.formletTypeName_SubmissionScheduleList,
				formleTitlePostFix, formleIdentPostFix),
				"FAIL: Could not add Attachment List Formlet");

		Assert
				.assertTrue(addLBFFunction(formlet),
						"FAIL: Could not add LBF Function to Submission Schedule Formlet");

	}

	private boolean initFormlet(String formletTitle) throws Exception {

		formletIdents = new ArrayList<String>();

		formlet = new Formlet(subForm,
				IFormletsConst.formletTypeName_eFormList,
				IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		subFormlet = new Formlet(subForm,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		String formletIdent = formletTitle.replace(" ", "-");

		formlet.setFormletTitleText(formletTitle + formleTitlePostFix);

		formlet.setFormletMenuText(formletTitle + formleTitlePostFix);

		formlet.setFormletId(formletIdent + formleIdentPostFix);

		if (formlet.createFormlet(true, true)) {
			formletIndex += 1;

			formletIdents.add(formletIndex, formlet.getFormletId());

			subFormlet
					.setFormletTitleText(formletTitle + subFormleTitlePostFix);

			subFormlet.setFormletId(formletIdent + subFormleIdentPostFix);

			subFormlet.setParentFormletId(formlet.getFormletId());

			return subFormlet.createSubFormlet();
		}

		return false;

	}

	private boolean addLBFFunction(Formlet funcFormlet) throws Exception {

		// Setup Formlet Function
		formletFunc = new FormletFunctions(subForm, funcFormlet
				.getFormletType(),
				IEFormsConst.applicantWorkspace_FormTypeName, preFix);

		strFormletFuncProp = new String[4];
		strFormletFuncProp[0] = IFormletsFunctionsConst.formletFunc_ListBF_InvokeWhen_Values_Array[1];
		strFormletFuncProp[1] = formlet.getFormletId();

		strFormletFuncProp[2] = "yes";
		strFormletFuncProp[3] = "true";

		return formletFunc.addListBringForwardFunction(strFormletFuncProp,
				funcFormlet);
	}



}
