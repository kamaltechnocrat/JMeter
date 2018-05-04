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

import test_Suite.constants.cases.IDocumentsConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IFormletsConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.cases.Documents;
import test_Suite.lib.eForms.EForm;
import test_Suite.lib.eForms.Formlet;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import test_Suite.utils.workflow.ProgramUtil;
import test_Suite.utils.workflow.ProjectUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class Test_DocumentsAttachmentListNG {

	// *********** Variables Declaration Section ********************
	Class<? extends Test_DocumentsAttachmentListNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	EForm form;
	Formlet formlet;

	ArrayList<String> attachmentParams;

	FOProject foProj;
	Program prog;

	String preFix = "-Attachment-Doc-";
	String postFix = "";

	boolean itItNewFundOpp = false;
	boolean hasProgForm = false;
	boolean isItNewOrg = true;

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0] };

	String orgName = "-EForm-Attachment-Org";

	String[][] submissionStep = {
			{ "Submission-A", "A Attachment Doc Submission A",
					"Applicant Submission", "true", "No" },
			{ "Attachment List Sub", "General Attachment List Submission" } };

	Documents docs;
	int docIndex;

	@BeforeClass(groups = { "EFormsNG", "DocumentsNG" })
	public void setup() throws Exception {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			form = new EForm(IEFormsConst.submission_FormTypeName, "");

			form.setEFormType(IEFormsConst.applicantsubmission_FormTypeName);

			form.setEFormSubType(IEFormsConst.applicantsubmission_FormTypeName);

			form.setEFormTitle("General Attachment List Submission");

			form.setEFormId("Attachment List Sub");

			formlet = new Formlet(form,
					IFormletsConst.formletTypeName_AttachmentsList,
					IEFormsConst.submission_FormTypeName, "");

			formlet.setFormletId("Attachment List Formlet");

			formlet.setFormletTitleText("Formlet");

			formlet.setFormletMenuText("Formlet");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@AfterClass(groups = { "EFormsNG", "DocumentsNG" }, alwaysRun=true)
	public void tearDown() {
		
		form = null;
		formlet = null;
		foProj = null;
		attachmentParams = null;
		foProj  = null;
		prog  = null;
		docs  = null;
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "EFormsNG", "DocumentsNG" })
	public void testAddingAttachmentDocumentNG() throws Exception {

		try {

			initializeAttachment();

			Assert.assertTrue(EFormsUtil.openEFormPlanner(form), "FAIL: Could not open eForm Planner!");

			Assert.assertTrue(formlet.addAttachments(attachmentParams, true), "FAIL: could not add attachment!");

			Assert.assertTrue(form.publishForm(formlet.getFormletId()), "FAIL: could not publish eForm!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "EFormsNG", "DocumentsNG" }, dependsOnMethods = { "testAddingAttachmentDocumentNG" })
	public void testUploadingDocumentsToEFormNG() throws Exception {

		try {

			initializeFOPP();

			openFOSubmission();
			docIndex = 0;
			docs = new Documents("A", docIndex,	IDocumentsConst.document_Doc_Init);

			Assert.assertTrue(FrontOfficeUtil.uploadAttachmentDoc(foProj,
					submissionStep[0][0], attachmentParams.get(2),
					"Description", formlet.getFormletMenuText(), docs),
					"FAIL: Could not upload Attachment in FO!");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@Test(groups = { "EFormsNG", "DocumentsNG" }, dependsOnMethods = { "testUploadingDocumentsToEFormNG" })
	public void testVerifyDeleteIconExistsNG() throws Exception {
		try {

			Assert.assertTrue(GeneralUtil.isImageExistsInTableByAlt("/data/",
					0, "Remove this Attachment"),
					"FAIL: Could not find Delete Icon");

			Assert.assertTrue(GeneralUtil.isImageExistsInTableByAlt("/data/",
					0, "Download"), "FAIL: Could not find Download Icon");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	//FIXME: download issue

	@Test(groups = { "EFormsNG", "DocumentsNG" }, dependsOnMethods = { "testVerifyDeleteIconExistsNG" })
	public void testDownloadAttachmentAfterFormSubmitedNG() throws Exception {

		try {

			ClicksUtil.clickLinks(IClicksConst.submissionSummaryLnk);
			ClicksUtil.clickButtons(IClicksConst.submitBtn);
			ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);

			GeneralUtil.switchToPO();

			Assert.assertTrue(ProjectUtil.openSubmissionInBasket(foProj, submissionStep[0][0]),
					"FAIL: Could not Open Submission in In Basket!");
			
			//Commented Out due to Watij flaw

//			Assert.assertTrue(ProjectUtil.downloadAttachment("/data/", formlet
//					.getFormletMenuText(), attachmentParams.get(2), foProj
//					.getProjFOFullName()
//					+ ".doc"), "FAIL: Could not complete Attachment Download");
			
			ClicksUtil.clickLinks(IClicksConst.backToSubListLnk);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	private void initializeAttachment() throws Exception {
		attachmentParams = new ArrayList<String>();

		attachmentParams.add(0, "Doc1");
		attachmentParams.add(1, "1 MB");
		attachmentParams.add(2, "Steps");
		attachmentParams.add(3, "Instruction");
		attachmentParams.add(4, ".*");

	}

	private void initializeFOPP() throws Exception {
		try {

			prog = new Program(preFix, 'F', hasProgForm, itItNewFundOpp, false);

			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

			prog.setProgPostfix(postFix);

			prog.initProgram();

			prog.setProgAdmin(progAdmin);

			prog.setProgOfficers(progOfficers);

			ClicksUtil.clickLinks(prog.getProgFullIdent());

			ProgramUtil.changeStepEForm(preFix + submissionStep[0][0],
					EProjectType.pre_Award.ordinal(), form.getEFormId());

			prog.activateProgram("Active");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	private void openFOSubmission() throws Exception {

		GeneralUtil.switchToFO();

		foProj = new FOProject(prog);

		ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(prog
				.getProgFullName(), foProj.getFoOrgName()),
				"FAIL: Could not register to Funding Opp.!");

		foProj.createFOProject();
	}
}
