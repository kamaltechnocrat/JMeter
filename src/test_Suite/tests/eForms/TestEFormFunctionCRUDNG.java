/**
 * 
 */
package test_Suite.tests.eForms;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IAlleFormsFunctionsConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormFunctions;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class TestEFormFunctionCRUDNG {

	// *********** Variables Declaration Section ********************
	Class<? extends TestEFormFunctionCRUDNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;
	EFormFunctions eFormFunction;

	ArrayList<EFormFunctions> funcList;

	String preFix = "";
	String postFix = "";

	// ----------- End of Variables Declaration ---------------------

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

			form.setEFormId("Test Create eForm Function");

			form.createEForm();
		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}

	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun=true)
	public void tearDown() {
		
		form = null;
		eFormFunction = null;
		funcList = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "EFormsNG" })
	public void testCreateAllEFormFunctions() throws Exception {

		funcList = new ArrayList<EFormFunctions>();

		eFormFunction = new EFormFunctions(form);

		eFormFunction.setEFormFunctionName(IAlleFormsFunctionsConst.eForm_AutoAssignProjectOrg_FunctionType);

		eFormFunction.setEFormFunctionProperty1("Drop Down Source");

		funcList.add(eFormFunction);

		Assert.assertTrue(eFormFunction.addAnyEFormFunctions(funcList), "FAIL: Error Adding eForm Function in eForm planner!");

	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = { "testCreateAllEFormFunctions" })
	public void testReadeAllEFormFunctions() throws Exception {

		EFormsUtil.expandFormPlannerNode("e.Form Functions",
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());

		Assert.assertTrue(EFormsUtil.openObjectDetail(eFormFunction
				.getEFormFunctionName()),
				"FAIL: unable to open e.Form Functtion");

	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = { "testReadeAllEFormFunctions" })
	public void testModifyAllEFormFunctions() throws Exception {

		Assert.assertTrue(eFormFunction.modifyEFormFunctionsProperties(funcList), "FAIL: error Edit eForm Function in eForm Planner!");

	}

	@Test(groups = { "EFormsNG" }, dependsOnMethods = { "testModifyAllEFormFunctions" })
	public void testDeleteAllEFormFunctions() throws Exception {

		Assert.assertTrue(EFormsUtil.deleteFormPlannerNode(eFormFunction.getEFormFunctionName()), "FAIL: error deleting eForm Function in eForm Planner!");

	}

}
