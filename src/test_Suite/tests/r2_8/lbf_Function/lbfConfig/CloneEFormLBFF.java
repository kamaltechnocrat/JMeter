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
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.EFormFieldFunctions;
import test_Suite.lib.eForms.Formlet;
import test_Suite.lib.eForms.FormletFunctions;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class CloneEFormLBFF {

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
	int eFormCount = -1;
	
	ArrayList<String> errorSmall;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------
		
		subForm = new EForm(IEFormsConst.submission_FormTypeName, preFix);

		subForm.setEFormSubType(IEFormsConst.postAwardSubmission_FormTypeName);

		subForm.setEFormTitle("Post Award Submission LBF");

		formletIdents = new ArrayList<String>();

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
	public void startAddingeForm() throws Exception {

		Assert.assertTrue(subForm.createEForm(),
						"FAIL: Could not Create eForm");

		formlet = new Formlet(subForm,
				IFormletsConst.formletTypeName_SubmissionSummary,
				IEFormsConst.postAwardSubmission_FormTypeName, preFix);

		Assert.assertTrue(formlet.initFormletWithDetails("Submission Summery",
				IFormletsConst.formletTypeName_SubmissionSummary,
				formleTitlePostFix, formleIdentPostFix),
				"FAIL: Could not add Attachment List Formlet");
		
		log.debug("the FormletCounter is: " + subForm.getFormletCounter());

		formletIdents.add(subForm.getFormletCounter() - 1, formlet.getFormletId());

		formletFunc = new FormletFunctions(subForm, formlet.getFormletType(),
				IEFormsConst.postAwardSubmission_FormTypeName, preFix);
		
		/******** Adding Formlet ****************/
		
		ClicksUtil.clickButtons("Expand All");
		addingLBFFunctionIntoEFormListFormletNG();
		addingLBFFunctionToAttachmentsListFormletNG();
		addingLBFFunctionToSubmissionScheduleFormletNG();
		
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = "startAddingeForm")
	public void testCloningUnpublishedEFormNG() throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);
		
		eFormCount = EFormsUtil.getEFormCounts(subForm.getEFormFullId(),IEFormsConst.postAwardSubmission_FormTypeName);

		Assert.assertTrue(EFormsUtil.cloneEForm(subForm.getEFormFullId(),
				IEFormsConst.postAwardSubmission_FormTypeName), "FAIL: error occured cloning eForms");
		

	}
	
	
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods = "testCloningUnpublishedEFormNG")
	public void testClonedEFormExistsNG() throws Exception {

		
		if((EFormsUtil.getEFormCounts(subForm.getEFormFullId(),IEFormsConst.postAwardSubmission_FormTypeName) - eFormCount) < 1)
		{
			Assert.fail("Error Cloning the eForm with no Validation");
		}
		
		if((EFormsUtil.getEFormCounts(subForm.getEFormFullId(),IEFormsConst.postAwardSubmission_FormTypeName) - eFormCount) > 1)
		{
			Assert.fail("Error Cloning, the result is more than one cloned eForm created");
		}			
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods = "testClonedEFormExistsNG")
	public void testOpeningClonedEFormNG() throws Exception {
		
		Assert.assertTrue(EFormsUtil.openClonedEForm(subForm.getEFormFullId(),IEFormsConst.postAwardSubmission_FormTypeName, 1), "FAIL: Could not open the cloned eForm");
			
		
		
	}
	
	private void addingLBFFunctionIntoEFormListFormletNG() throws Exception {

		EFormsUtil.returnToPlanner();

		Assert.assertTrue(initFormlet("eForm List Formlet"),
				"FAIL: Could not create List Type e.Form");

		formletIdents.add(subForm.getFormletCounter() - 2, formlet.getFormletId());

		Assert.assertTrue(formletFunc.addLBFFunction(formlet),
				"FAIL: Could not add LBF Function eForm List Formlet");

	}
	
	private void addingLBFFunctionToAttachmentsListFormletNG() throws Exception {

		EFormsUtil.returnToPlanner();

		formlet = new Formlet(subForm,
				IFormletsConst.formletTypeName_AttachmentsList,
				IEFormsConst.postAwardSubmission_FormTypeName, preFix);

		Assert.assertTrue(formlet.initFormletWithDetails("Attachment List",
				IFormletsConst.formletTypeName_AttachmentsList,
				formleTitlePostFix, formleIdentPostFix),
				"FAIL: Could not add Attachment List Formlet");

		formletIdents.add(subForm.getFormletCounter() - 2, formlet.getFormletId());

		Assert.assertTrue(formletFunc.addLBFFunction(formlet),
				"FAIL: Could not add LBF Function to Attachment List Formlet");

	}
	
	private void addingLBFFunctionToSubmissionScheduleFormletNG()
			throws Exception {

		EFormsUtil.returnToPlanner();

		formlet = new Formlet(subForm,
				IFormletsConst.formletTypeName_SubmissionScheduleList,
				IEFormsConst.postAwardSubmission_FormTypeName, preFix);

		Assert.assertTrue(formlet.initFormletWithDetails(
				"Submission Schedule List",
				IFormletsConst.formletTypeName_SubmissionScheduleList,
				formleTitlePostFix, formleIdentPostFix),
				"FAIL: Could not add Attachment List Formlet");

		formletIdents.add(subForm.getFormletCounter() - 2, formlet.getFormletId());

		Assert
				.assertTrue(formletFunc.addLBFFunction(formlet),
						"FAIL: Could not add LBF Function to Submission Schedule Formlet");

	}

	private boolean initFormlet(String formletTitle) throws Exception {

		formlet = new Formlet(subForm,
				IFormletsConst.formletTypeName_eFormList,
				IEFormsConst.postAwardSubmission_FormTypeName, preFix);

		subFormlet = new Formlet(subForm,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.postAwardSubmission_FormTypeName, preFix);

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


}
