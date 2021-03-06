/**
 * 
 */
package test_Suite.tests.eForms;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.EFormFieldFunctions;
import test_Suite.lib.eForms.Formlet;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class TestAllFormletsPDFNG {

	// *********** Variables Declaration Section ********************
	Class<? extends TestAllFormletsPDFNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;
	Formlet formlet;
	EFormField eFormField;
	EFormFieldFunctions eFormFieldFunc;

	ArrayList<EFormFieldFunctions> eFormFieldFuncList;

	String preFix = "";
	String postFix = "";

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

			form.setEFormId("Test Create eFormField Function");

			form.createEForm();

			formlet = new Formlet(form,
					IFormletsConst.formletTypeName_eFormQuestionHolder,
					IEFormsConst.submission_FormTypeName, preFix);

			formlet.setFormletTitleText("General Formlet");

			formlet.setFormletMenuText("Formlet");

			formlet.createFormlet(false, false);

			eFormField = new EFormField(form, formlet,
					IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

			eFormField.setEFormFieldLabel("Enter Any Text:");

			eFormField.setEFormFieldDescription("");

			eFormField.setEFormFieldTooltip("");

			eFormField.addeFormField(true, false, false, false);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}

	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun=true)
	public void tearDown() {
		
		form = null;
		formlet = null;
		eFormField = null;
		eFormFieldFunc = null;
		eFormFieldFuncList = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "EFormsNG" })
	public void testMenuItemOnly() throws Exception {

	}

	@Test(groups = { "EFormsNG" })
	public void testAttachmentList() throws Exception {

	}

	@Test(groups = { "EFormsNG" })
	public void testEFormList() throws Exception {

	}

	@Test(groups = { "EFormsNG" })
	public void testQuestionHolder() throws Exception {

	}

	@Test(groups = { "EFormsNG" })
	public void testQuestionHodlerWithSubmissionList() throws Exception {

	}

	@Test(groups = { "EFormsNG" })
	public void testSubmissionScheduleList() throws Exception {

	}

	@Test(groups = { "EFormsNG" })
	public void testSubmissionSummary() throws Exception {

	}

}
