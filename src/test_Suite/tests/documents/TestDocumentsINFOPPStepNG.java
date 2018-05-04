/**
 * 
 */
package test_Suite.tests.documents;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IDocumentsConst;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IProgramsConst;
import test_Suite.lib.cases.Documents;
import test_Suite.lib.workflow.Program;
import test_Suite.utils.cases.DocumentsUtil;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.ProgramUtil;

/**
 * @author mshakshouki
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class TestDocumentsINFOPPStepNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends TestDocumentsINFOPPStepNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	String baseLetter;
	int docIndex = 0;
	String docName;
	String docIdent;
	String stepFullIdent;

	boolean newProgram = false;
	boolean programForm = false;
	boolean newOrg = false;
	boolean hasPublication = false;
	String progOfficers[] = { IPreTestConst.Groups[0][0][0] };

	String preFix = IDocumentsConst.document_Doc_Init;

	Documents docs;

	Program prog;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "DocumentsNG" })
	public void setup() {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// ----------------------------------

			prog = new Program(preFix, 'P', programForm, newProgram,
					hasPublication);

			prog.setProgOfficers(progOfficers);
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

			prog.initProgram();

			docs = new Documents("A", docIndex, preFix);
			log.info("Setup complete");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "DocumentsNG" }, alwaysRun=true)
	public void tearDown() {
		docs = null;
		prog = null;
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	private void openFOPPPlanner() throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		ClicksUtil.clickLinks(prog.getProgFullIdent());

		prog.setCurrentStepIdent(prog.getProgLetter() + prog.getProgPreFix()
				+ IGeneralConst.gnrl_SubmissionA[0][0]);

		ProgramUtil.openStepManagmentDetails(
				IProgramsConst.EProjectType.pre_Award.ordinal(), prog
						.getCurrentStepIdent(),
				IProgramsConst.EstepManagment.documents.ordinal());
	}

	@Test(groups = { "DocumentsNG" })
	public void testAddDocumentAsStepDoc() throws Exception {

		DocumentsUtil.uploadDocument(docs, false);

		openFOPPPlanner();

		docs.selectDocumentInM2M();
	}

	@Test(groups = { "DocumentsNG" }, dependsOnMethods = { "testAddDocumentAsStepDoc" })
	public void testValidationDeleteDocumentUsedAsStepDoc() throws Exception {

		Assert
				.assertFalse(DocumentsUtil.deleteDocument(docs.getDocIdent()),
						"FAIL: shouldn't be able to Delete Document in List After Asociated to FOPP!");
	}

}
