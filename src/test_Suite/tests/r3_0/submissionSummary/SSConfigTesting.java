/**
 * 
 */
package test_Suite.tests.r3_0.submissionSummary;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.Formlet;
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
public class SSConfigTesting {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;
	
	Formlet formlet;
	
	EFormField eFormField;
	
	String SubmissionFormId = null;
	
	String tempFormletId = null;

	String preFix = "";
	String postFix = "";

	String tempDefaultFormlet = null;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			createApplicantSubmissionForm();
			
			EFormsUtil.openObjectDetail("Formlet: " + formlet.getFormletId());

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun=true)
	public void tearDown() {
		
		form  = null;
		formlet  = null;
		eFormField  = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EFormsNG" }, dataProvider="SummaryDisplay")
	public void testSSConfigNG(String displayType, String displayBy, boolean expected) throws Exception {
		
		GeneralUtil.selectFullStringInDropdownList(IFormletsConst.formletSummaryLastUpdatedBy_SelectList_Id, displayType);
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
		
		EFormsUtil.openObjectDetail("Formlet: " + formlet.getFormletId());
		
		Assert.assertEquals(GeneralUtil.getSelectedValueInDropDownById(IFormletsConst.formletSummaryLastUpdated_SelectList_Id), "Date and Time");
		
		Assert.assertEquals(GeneralUtil.getSelectedValueInDropDownById(IFormletsConst.formletSummaryLastUpdatedBy_SelectList_Id), displayType);
	}

	@DataProvider(name = "SummaryDisplay")
	public Object[][] generateSummaryDisplay() {

		Object[][] result = null;
		
		result = new Object[][] {
				new Object[] {"User Name", "shak", true},
				new Object[] {"User Name (email address)", "shak (mshakshouki@grant-nds-06.grantium.com)",true},
				new Object[] {"Last Name, First Name", "Shakshouki, Mustafa",true},
				new Object[] {"Last Name, First Name (Email Address)", "Shakshouki, Mustafa (mshakshouki@grant-nds-06.grantium.com)",true},
				new Object[] {"First Name Last Name", "Mustafa Shakshouki",true},
				new Object[] {"First Name Last Name (Email Address)", "Mustafa Shakshouki (mshakshouki@grant-nds-06.grantium.com)",true},
				new Object[] {"Do not display", "None", false}
		};		
		
		return result;
		
	}

	private void createApplicantSubmissionForm() throws Exception {

		form = new EForm(IEFormsConst.submission_FormTypeName, "SS-");

		form.setEFormSubType(IEFormsConst.applicantsubmission_FormTypeName);

		form.setEFormTitle("SS Submission");

		form.createEForm();

		SubmissionFormId = form.getEFormId();

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_eFormQuestionHolder,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.setFormletTitleText("General Formlet");

		formlet.setFormletMenuText("Formlet");

		formlet.createFormlet(true, true);

		tempDefaultFormlet = formlet.getFormletId();

		eFormField = new EFormField(form, formlet,
				IeFormFieldsConst.eFormFieldType_ShortTextField_Name);

		eFormField.setEFormFieldLabel("Enter Any Text:");

		eFormField.setEFormFieldDescription("");

		eFormField.setEFormFieldTooltip("");

		eFormField.addeFormField(true, false, false, false);

		formlet = new Formlet(form,
				IFormletsConst.formletTypeName_SubmissionSummary,
				IEFormsConst.submission_FormTypeName, preFix);

		formlet.createSubmissionSummaryTypeFormlet();
		

		//form.publishForm(tempDefaultFormlet);
	}

}
