/**
 * 
 */
package test_Suite.tests.rptFunctions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.EFormField;
import test_Suite.lib.eForms.Formlet;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.OrgUtil;
import test_Suite.utils.reporting_Functions.RptFuncUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class RF_PopOrgEForm {

	/*********** Variables Declaration Section ********************/
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;
	
	Formlet formlet;
	
	EFormField eFormField;

	Project proj;
	
	Program prog;

	String preFix = "-Org-eForm-";
	
	String postFix = "-Reporting-Function";

	boolean itItNewFundOpp = true;
	
	boolean hasProgForm = true;
	
	boolean isItNewOrg = false;

	String progAdmin[] = { IPreTestConst.adminGroup };
	
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	String orgName = "G3";
	
	/*********** End ofVariables Declaration Section ********************/

	@BeforeClass(groups = { "SetupReportingFunctionsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			form = new EForm();

			form.setEFormType(IEFormsConst.organization_FormTypeName);

			form.setEFormSubType(IEFormsConst.organization_FormTypeName);

			form.setEFormTitle("Test Organization eForm Reporting Function");

			form.setEFormId("Test-Organization-eForm-Reporting-Function");

			Assert.assertTrue(OrgUtil.selectAndOpenOrgEForm(orgName, form
					.getEFormId()));

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}
	}

	@AfterClass(groups = { "SetupReportingFunctionsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {

		ClicksUtil.clickLinks("Submission Summary");

		ClicksUtil.clickLinks(IClicksConst.orgBackToOrganizationPlanner);

		log.info("Organization Form complete!");
		
		form = null;
		proj = null;
		prog = null;
		formlet = null;
		eFormField = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "SetupReportingFunctionsNG" })
	public void testOrgEFormReportingFunctionNG() throws Exception {

		fill_EForm();

	}

	private void fill_EForm() throws Exception {
		try {

			RptFuncUtil.fill_NoProperties_FieldsData();
			RptFuncUtil.fill_eList_FormletData();
			RptFuncUtil.fill_Dropdown_FieldsData();
			RptFuncUtil.fill_LookupDropdwon_FieldsData();
			RptFuncUtil.fill_TypesProperties_FieldsData();
			RptFuncUtil.fill_MinAndMaxProperties_FieldsData();
			RptFuncUtil.fill_eListDropdown_FormletData();
			RptFuncUtil.fill_eListDataGrid_FormletData();

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}


}
