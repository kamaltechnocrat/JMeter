/**
 * 
 */
package test_Suite.tests.rptFunctions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.reporting_Functions.RptFuncUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class RF_PopAdminEForm {

	/*********** Variables Declaration Section ********************/
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;

	Project proj;
	
	Program prog;

	String preFix = "-Admin-eForm-";
	
	String postFix = "-Reporting-Function";

	boolean itItNewFundOpp = false;
	
	boolean hasProgForm = true;
	
	boolean isItNewOrg = false;

	String progAdmin[] = { IPreTestConst.adminGroup };
	
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	String orgName = "-Admin-eForm-Reporting-Function";
	
	/*********** End ofVariables Declaration Section ********************/

	@BeforeClass(groups = { "SetupReportingFunctionsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			form = new EForm();

			form.setEFormType(IEFormsConst.progAdministration_FormTypeName);

			form.setEFormSubType(IEFormsConst.progAdministration_FormTypeName);

			form.setEFormTitle("Test Admin eForm Reporting Function");

			form.setEFormId("Test-Admin-eForm-Reporting-Function");

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}
	}

	@AfterClass(groups = { "SetupReportingFunctionsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {

		ClicksUtil.clickLinks("Submission Summary");
		ClicksUtil.clickLinks(IClicksConst.backToFundingOppPlannerLnk);

		prog.activateProgram("Active");
		log.info("General_Program_Calls complete!");
		
		form = null;
		proj = null;
		prog = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	private void createFundingOpp() throws Exception {

		try {

			prog = new Program(preFix, 'P', hasProgForm, itItNewFundOpp, false);

			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

			prog.setProgPostfix(postFix);

			if (itItNewFundOpp) {
				prog.initProgram();

				prog.setProgramFormName(form.getEFormId());

				prog.setProgAdmin(progAdmin);

				prog.setProgOfficers(progOfficers);

				prog.createProgram();

				prog.addStep(IGeneralConst.gnrl_SubmissionA);

				prog.manageStep(new String[][] { { progOfficers[0] },
						{ IGeneralConst.gnrl_SubmissionA[0][0] } });

				prog.openAdminEForm();
			} else {
				prog.initProgram();

				ClicksUtil.clickLinks(prog.getProgFullIdent());

				prog.openAdminEForm();
			}

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}

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

	@Test(groups = { "SetupReportingFunctionsNG" })
	public void testAdminEFormReportingFunctionNG() throws Exception {

		createFundingOpp();

		ClicksUtil.clickLinks("eList Data Grid");

		if (TablesUtil.howManyEntriesInTable("/data/") <= 0) {
			fill_EForm();
		}

	}


}
