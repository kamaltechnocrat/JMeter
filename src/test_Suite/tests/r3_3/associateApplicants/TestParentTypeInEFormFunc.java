/**
 * 
 */
package test_Suite.tests.r3_3.associateApplicants;

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
import test_Suite.constants.users.IApplicantTypesConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormFunctions;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.ApplicantTypesUtils;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TestParentTypeInEFormFunc {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	
	String foUserBeat = "";
	
	String appType = "";
	
	String typeIdent = "";

	EForm form;
	
	EFormFunctions eFormFunction;

	ArrayList<EFormFunctions> funcList;

	String preFix = "";
	String postFix = "";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			foUserBeat = EFormsUtil.createRandomNumber(3);
			
			appType = "university".concat(foUserBeat);
			
			typeIdent = "G".concat(foUserBeat).concat("_APPLICANT_TYPE_PARENT");

			form = new EForm(IEFormsConst.submission_FormTypeName, preFix);

			form.setEFormSubType(IEFormsConst.applicantsubmission_FormTypeName);

			form.setEFormTitle("Student Submission");

			form.setEFormId("Test_ParentType_Availability");

			funcList = new ArrayList<EFormFunctions>();

			eFormFunction = new EFormFunctions(form);

			eFormFunction.setEFormFunctionName(IAlleFormsFunctionsConst.eForm_CreateParentApplicant_FunctionType);

			eFormFunction.setEFormFunctionProperty1("Applicant Name Field Ident");
			eFormFunction.setEFormFunctionProperty2("Global Id field Ident");
			eFormFunction.seteFormFunctionPropertyDropDown(typeIdent);

			funcList.add(eFormFunction);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		form = null;
		eFormFunction = null;
		funcList = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void addingNewApplicantTypeNG() throws Exception {
		try {
			
			Assert.assertTrue(ApplicantTypesUtils.openApplicantTypesList(), "ERROR: could not open Applicant Types List");
			
			Assert.assertTrue(ApplicantTypesUtils.addNewApplicantType(typeIdent, IApplicantTypesConst.EAppCategories.parentApplicant, appType), "ERROR: Not able to add new Applicant Type!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" }, dependsOnMethods="addingNewApplicantTypeNG")
	public void addingNewEFormParentFunctionNG() throws Exception {
		try {
			
			Assert.assertTrue(form.createEForm(), "FAIL: could not create eForm!");
			
			Assert.assertTrue(eFormFunction.addAnyEFormFunctions(funcList), "FAIL: Error Adding Create Parent Applicant eForm Function in eForm planner!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "ProjectsNG" }, dependsOnMethods = { "addingNewEFormParentFunctionNG" })
	public void testReadAllEFormFunctions() throws Exception {

		EFormsUtil.expandFormPlannerNode("e.Form Functions",
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());

		Assert.assertTrue(EFormsUtil.openObjectDetail(eFormFunction
				.getEFormFunctionName()),
				"FAIL: unable to open e.Form Functtion");

	}

}
