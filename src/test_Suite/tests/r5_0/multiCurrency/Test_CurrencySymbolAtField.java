/**
 * 
 */
package test_Suite.tests.r5_0.multiCurrency;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.eForms.IeFormFieldsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.Formlet;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author k.sharma
 *
 */
@GUITest
@Test(singleThreaded = true)
public class Test_CurrencySymbolAtField {
	
	// *********** Variables Declaration Section ********************
		Class<? extends Test_CurrencySymbolAtField> ownClass = this.getClass();

		private Log log = LogFactory.getLog(ownClass);

		EForm form;
		
		Formlet formlet;

		EFormField eFormField;

		String SubmissionFormId = null;

		String tempDefaultFormlet = null;

		String preFix = "MC";
		String postFix = "Test";
		boolean checkstatus;

		// ----------- End of Variables Declaration ---------------------

		@BeforeClass(groups = { "MultiCurrencyNG" })
		public void setUp() throws Exception {

			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			
			form = new EForm(IEFormsConst.submission_FormTypeName, preFix);

			form.setEFormSubType(IEFormsConst.applicantsubmission_FormTypeName);

			form.setEFormTitle("General Submission");

			form.setEFormId("Sub");

			formlet = new Formlet(form, IFormletsConst.formletTypeName_eFormQuestionHolder,
					IEFormsConst.submission_FormTypeName, preFix);
			
		}

		@AfterClass(groups = { "MultiCurrencyNG" }, alwaysRun = true)
		public void tearDown() throws Exception {
			
			form = null;
			formlet = null;
			eFormField = null;
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();

			log.warn("Ending: " + this.getClass().getSimpleName());
		}

		@Test(groups = { "MultiCurrencyNG" })
		public void createEForm() throws Exception {
			
			Assert.assertTrue(form.createEForm(), "FAIL: Could not create e.Form!");

			SubmissionFormId = form.getEFormId();

			formlet.setFormletTitleText("MultiCurrency Test Formlet");

			formlet.setFormletMenuText(" MutliCurrency Formlet");

			Assert.assertTrue(formlet.createFormlet(true, true), "FAIL: could not create Formlet!");

			tempDefaultFormlet = formlet.getFormletId();			
		}

		@Test(groups = { "MultiCurrencyNG" }, dependsOnMethods = "createEForm")
		public void testNumericFieldCurrencySymbol() throws Exception {
			try {

				eFormField = new EFormField(form, formlet, IeFormFieldsConst.eFormFieldType_NumericField_Name);

				eFormField.setEFormFieldLabel("Numeric1");

				eFormField.setEFormFieldDescription("");

				eFormField.setEFormFieldTooltip("");

				Assert.assertTrue(eFormField.addeFormField(true, true, false, false), "FAIL: Could not add eFormfiled!");			
				
				Assert.assertTrue(eFormField.searchDefaultSymbol(),	"FAIL: Default Dollar symbol is not available");				

			} catch (Exception e) {

				Assert.fail("Unexpected Exception: ", e);

			}
		}

		@Test(groups = { "MultiCurrencyNG" }, dependsOnMethods = "createEForm")
		public void testDataGridFieldCurrencySymbol() throws Exception {
			try {
				
				eFormField = new EFormField(form, formlet, IeFormFieldsConst.eFormFieldType_DataGridField_Name);

				eFormField.initListDetailsField(IeFormFieldsConst.eFormFieldType_DataGridField_Name, "-Numeric");

				Assert.assertTrue(eFormField.addeFormField(true, true, false, false), "FAIL: Could not add eFormfiled!");

				Assert.assertTrue(eFormField.configureDataGridEFormField("2", "2"), "FAIL: Could not Configure Data Grid field");

				Assert.assertTrue(eFormField.selectDataGridCells(new int[] { IeFormFieldsConst.EeFormField_DataGrid_Configure_SelectionButtonsValue.selectAll.ordinal() }), "FAIL: Could not select Grid Cells");

				Assert.assertTrue(eFormField.testDataGridCellType_NumericEntryCell(), "FAIL: Could Not Test Numeric Cell!");
				
				Assert.assertTrue(eFormField.searchDataGridDefaultSymbol(), "FAIL: Default Dollar symbol is not available");				

				ClicksUtil.clickButtons(IClicksConst.backToEFormFieldDetails);

				ClicksUtil.clickButtons(IClicksConst.backBtn);

			} catch (Exception e) {

				Assert.fail("Unexpected Exception: ", e);

			}
		}
}
