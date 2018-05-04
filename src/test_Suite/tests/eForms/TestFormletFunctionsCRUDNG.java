/**
 * 
 */
package test_Suite.tests.eForms;

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
public class TestFormletFunctionsCRUDNG {

	// *********** Variables Declaration Section ********************
	Class<? extends TestFormletFunctionsCRUDNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;
	Formlet formlet;
	FormletFunctions formletFunc;

	ArrayList<FormletFunctions> formletFuncList;

	String preFix = "";
	String postFix = "";

	private static Hashtable<String, String> textFields;

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() {
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());
			
			log.info("Opening New Window and Login as Admin");

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			form = new EForm(IEFormsConst.submission_FormTypeName, preFix);

			form.setEFormSubType(IEFormsConst.applicantsubmission_FormTypeName);

			form.setEFormTitle("General Submission");

			form.setEFormId("Test CRUD on Formlet Function");

			form.createEForm();

			formlet = new Formlet(form,
					IFormletsConst.formletTypeName_eFormQuestionHolder,
					IEFormsConst.submission_FormTypeName, preFix);

			formlet.setFormletTitleText("General Formlet");

			formlet.setFormletMenuText("Formlet");

			formlet.createFormlet(false, false);
		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}

	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun=true)
	public void tearDown() {
		
		form = null;
		formlet = null;
		formletFunc = null;
		formletFuncList = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "EFormsNG" })
	public void testCreateFormletFunctions() throws Exception {

		formletFuncList = new ArrayList<FormletFunctions>();

		formletFunc = new FormletFunctions(form, formlet.getFormletType(),
				IEFormsConst.submission_FormTypeName, preFix);

		formletFunc
				.setFormletFunctionName(IFormletsFunctionsConst.formlet_CalculatedFormletReadOnly_FunctionType);

		formletFunc.setExpression("true");

		formletFunc.addAnyFormletFunction();
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = { "testCreateFormletFunctions" })
	public void testReadFormletFunctions() throws Exception {

		EFormsUtil.expandFormPlannerNode("Formlet Functions",
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());

		Assert.assertTrue(EFormsUtil.openObjectDetail(formletFunc.getFormletFunctionName()), "FAIL: error Opening Formlet Function details in eForm planner!");
	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = { "testReadFormletFunctions" })
	public void testModifyFormletFunctions() throws Exception {

		textFields = new Hashtable<String, String>();

		textFields.put(
				IFormletsFunctionsConst.formletCalculated_FuncProp_TextArea_Id,
				"true, Modified");

		formletFunc.modifyAnyTextFields(textFields);

		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
		
		Assert.assertEquals(GeneralUtil.isButtonExistsByValue(IClicksConst.previewFormBtn), true);

	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = { "testModifyFormletFunctions" })
	public void testDeleteFormletFunctions() throws Exception {

		Assert.assertTrue(EFormsUtil.deleteFormPlannerNode(formletFunc.getFormletFunctionName()), "FAIL: Error deleting Formlet Function in eForm planner!");

	}

}
