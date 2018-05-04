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
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class TestDownloadDocumentsNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends TestDownloadDocumentsNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	String baseLetter = "S";
	int docIndex = 3;
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

			DocumentsUtil.uploadDocument(docs, true);
			
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

	@Test(groups = { "DocumentsNG" })
	public void testDownloadDocuments() throws Exception {

		Assert.assertTrue(DocumentsUtil.doesDocumentExists(docs.getDocIdent()),
				"FAIL: Documents does not exists to complete the Download");
		
		//Commented out due to flaw in Watij API

//		Assert.assertTrue(DocumentsUtil.saveDownloadDocument(docs),
//				"FAIL: Could not Download Documents");
	}

}
