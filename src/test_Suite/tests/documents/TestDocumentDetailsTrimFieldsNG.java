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
import test_Suite.lib.cases.Documents;
import test_Suite.utils.cases.DocumentsUtil;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class TestDocumentDetailsTrimFieldsNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends TestDocumentDetailsTrimFieldsNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	String baseLetter = "T";
	int docIndex = 1;
	String docName;
	String docIdent;

	String preFix = IDocumentsConst.document_Doc_Init;

	Documents docs;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "DocumentsNG" })
	public void setUp() {
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// ----------------------------------

			docs = new Documents(baseLetter, docIndex, preFix);

			DocumentsUtil.deleteDocument(docs.getDocIdent());

			docs.addTrailingAndLeadingSpaces();

			DocumentsUtil.uploadDocument(docs, true);

			DocumentsUtil.doesDocumentExists(docs.getDocIdent().trim());

			ClicksUtil.clickLinks(docs.getDocIdent().trim());

			log.info("Setup complete");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "DocumentsNG" }, alwaysRun=true)
	public void tearDown() {
		docs = null;
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "CasesNG", "DocumentsNG" })
	public void testDocumentDetailsTrimLeadingSpaceIdentifierFieldsNG()
			throws Exception {

		try {

			Assert
					.assertTrue(docs.doesHaveLeadingSpaces(GeneralUtil
							.getTextInTextFieldByIndex(1)),
							"FAIL: G3 did not Trim Leading Spaces of Documents Identifier");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "DocumentsNG" })
	public void testDocumentDetailsTrimTrailingSpaceIdentifierFieldsNG()
			throws Exception {

		try {

			Assert
					.assertTrue(docs.doesHaveTrailingSpaces(GeneralUtil
							.getTextInTextFieldByIndex(1)),
							"FAIL: G3 did not Trim Trailing Spaces of Documents Identifier");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "DocumentsNG" })
	public void testDocumentDetailsTrimLeadingSpaceNameFieldsNG()
			throws Exception {

		try {

			Assert
					.assertTrue(docs.doesHaveLeadingSpaces(GeneralUtil
							.getTextInTextFieldByIndex(3)),
							"FAIL: G3 did not Trim Leading Spaces of Documents Localized Name");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@Test(groups = { "DocumentsNG" })
	public void testDocumentDetailsTrimTrailingSpaceNameFieldsNG()
			throws Exception {

		try {

			Assert
					.assertTrue(docs.doesHaveTrailingSpaces(GeneralUtil
							.getTextInTextFieldByIndex(3)),
							"FAIL: G3 did not Trim Trailing Spaces of Documents Localized Name");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

}
