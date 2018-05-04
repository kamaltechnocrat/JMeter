/**
 * 
 */
package test_Suite.tests.r2_8.lbf_Function.lbfConfig;

import java.util.ArrayList;
import java.util.Hashtable;

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
public class LBFF_CRUD {

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

	private static Hashtable<String, String> textFields;

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

		EFormsUtil.returnToPlanner();

		Assert.assertTrue(initFormlet("eForm List Formlet"),
				"FAIL: Could not create List Type e.Form");

		formletFunc = new FormletFunctions(subForm, formlet.getFormletType(),
				IEFormsConst.applicantWorkspace_FormTypeName, preFix);

	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		EFormsUtil.returnToPlanner();
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());

	}

	@Test(groups = { "EFormsNG" })
	public void testCreateLBFFunctionNG() throws Exception {

		Assert.assertTrue(formletFunc.addLBFFunction(formlet),
				"FAIL: Could not add LBF Function eForm List Formlet");
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = { "testCreateLBFFunctionNG" })
	public void testReadFormletFunctionsNG() throws Exception {
		EFormsUtil.returnToPlanner();

		EFormsUtil.expandFormPlannerNode("Formlet Functions",
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());

		Assert.assertTrue(EFormsUtil.openObjectDetail(formletFunc
				.getFormletFunctionName()),
				"FAIL: Could not Open LBF Function for Reading");
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = { "testReadFormletFunctionsNG" })
	public void testUpdateFormletFunctionsNG() throws Exception {

		textFields = new Hashtable<String, String>();

		textFields
				.put(
						IFormletsFunctionsConst.formletFunc_ListBF_ConditionalExpression_TextField_Id,
						"true, Modified");

		formletFunc.modifyAnyTextFields(textFields);

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);

	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = { "testUpdateFormletFunctionsNG" })
	public void testDeleteFormletFunctions() throws Exception {

		EFormsUtil.returnToPlanner();

		Assert.assertTrue(EFormsUtil.deleteFormPlannerNode(formletFunc
				.getFormletFunctionName()),
				"FAIL: Could not Delete LBF Function");

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


}
