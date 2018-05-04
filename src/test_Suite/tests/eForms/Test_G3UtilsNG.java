/**
 * 
 */
package test_Suite.tests.eForms;

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
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.ProjectUtil;


/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class Test_G3UtilsNG {

	// *********** Variables Declaration Section ********************
	Class<? extends Test_G3UtilsNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm progEForm;
	EForm subEForm;

	Project proj;
	Program prog;

	String preFix = "-G3Utils-eForm-";
	String postFix = "-getFieldData";

	boolean itItNewFundOpp = false;
	boolean hasProgForm = true;
	boolean isItNewOrg = true;

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	String orgName = "-G3Utils-eForm-getFieldData";

	String[][] subGrid = {
			{ "Submission-A", "Submission Grid 1.5", "Applicant Submission",
					"true", "No" },
			{ "Submission-Grid 1.5", "Submission Grid 1.5" } };

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());
			
			log.info("Opening New Window and Login as Shak");

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			progEForm = new EForm();

			progEForm
					.setEFormType(IEFormsConst.progAdministration_FormTypeName);

			progEForm
					.setEFormSubType(IEFormsConst.progAdministration_FormTypeName);

			progEForm.setEFormTitle("Program Form Grid 1.5");

			progEForm.setEFormId("Program-Form-Grid 1.5");

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun=true)
	public void tearDown() {
		progEForm = null;
		subEForm = null;
		proj = null;
		prog = null;
		progEForm = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "EFormsNG" })
	public void test_G3UtilsNG() throws Exception {

		try {

			createFundingOpp();

			fill_ProgeForm();

			GeneralUtil.setApplicantWorkspace("Applicant Profile 1.5");

			createProject();

		} catch (Exception e) {

			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	private void createFundingOpp() throws Exception {

		try {

			prog = new Program(preFix, 'P', hasProgForm, itItNewFundOpp, false);

			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

			prog.setProgPostfix(postFix);

			prog.initProgram();

			ClicksUtil.clickLinks(prog.getProgFullIdent());

			prog.openAdminEForm();

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}

	}

	private void fill_ProgeForm() throws Exception {

		try {

			ClicksUtil.clickLinks("Prog Form eList Menu");

			if (TablesUtil.howManyEntriesInTable("/data/") <= 0) {
				ClicksUtil.clickImage(IClicksConst.newImg);

				for (Integer x = 1; x < 4; x++) {
					EFormsUtil.selectFromDropDown(0, "/Mr/");
					EFormsUtil.enterTextToTextField(1, x.toString()
							+ ". Moe Joe - 1");
					EFormsUtil.enterTextToTextField(2, x.toString() + "23456");
					EFormsUtil.enterDateToDateField(3);
					EFormsUtil.taggleCheckbox(0, true);

					ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
				}

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				ClicksUtil.clickLinks("Prog eForm eList 2 Menu");

				ClicksUtil.clickImage(IClicksConst.newImg);

				for (Integer x = 1; x < 4; x++) {
					EFormsUtil.selectFromDropDown(0, "/Rev/");
					EFormsUtil.enterTextToTextField(1, x.toString()
							+ ". Joe Moe - 2");
					EFormsUtil.enterTextToTextField(2, x.toString() + "45632");
					EFormsUtil.enterDateToDateField(3);
					EFormsUtil.taggleCheckbox(0, true);

					ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
				}

				ClicksUtil.clickButtons(IClicksConst.backToListBtn);

				ClicksUtil.clickLinks("Prog eForm QH DG Menu");

				EFormsUtil.selectFromDropDown(0, "2009-10");			
				EFormsUtil.selectFromDropDown(1, "East-L");
				EFormsUtil.enterTextToTextField(2, "123456");
				EFormsUtil.enterTextToTextField(3, "654321");

				ClicksUtil.clickButtons(IClicksConst.saveBtn);
			}

			ClicksUtil.clickLinks(IClicksConst.backToFundingOppPlannerLnk);

			prog.activateProgram("Active");

		} catch (Exception e) {

			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	private void createProject() throws Exception {

		try {

			proj = new Project(prog, true);

			proj.setOrgName(orgName);

			proj.createOrgFullName(isItNewOrg);

			Assert.assertTrue(TablesUtil.openInTableByImage(
					ITablesConst.applicantsTableId, proj.getOrgFullName(), 3),
					"Fail: could not Open Applicant Profile");

			EFormsUtil.selectFromDropDown(0, "2");

			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			ClicksUtil.clickButtons(IClicksConst.completeBtn);
			ClicksUtil.clickLinks(IClicksConst.backToApplicantsListLnk);

			proj.createPOProjectOnly(isItNewOrg);

			Assert.assertTrue(ProjectUtil.openPOSubmissionForm(proj,
					subGrid[0][0], "Open Projects", "Latest Version", "All"));

			ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
			ClicksUtil.clickButtons(IClicksConst.backBtn);

		} catch (Exception e) {

			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

}
