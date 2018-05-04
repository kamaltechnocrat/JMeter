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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IDocumentsConst;
import test_Suite.lib.cases.Documents;
import test_Suite.utils.cases.DocumentsUtil;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class Documents_TestingNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends Documents_TestingNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	String baseLetter;
	int docIndex;
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

			docIndex = 0;

			log.info("Documents Initialized");

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
	public void testAddDocumentNG() throws Exception {

		docs = new Documents("Z", 0, preFix);

		Assert.assertTrue(DocumentsUtil.editDocument(docs, true,true),
				"FAIL: could not add Document");

		Assert.assertTrue(DocumentsUtil.doesDocumentExists(docs.getDocIdent()),
				"FAIL: could not find Document in List After Creation");
	}

	@Test(groups = { "DocumentsNG" }, dependsOnMethods = "testAddDocumentNG")
	public void testValidationDuplicateDocumentName() throws Exception {

		Assert.assertFalse(DocumentsUtil.editDocument(docs, true,true),
				"FAIL: Should not add Document");
	}

	@Test(groups = { "DocumentsNG" }, dependsOnMethods="testValidationDuplicateDocumentName")
	public void testDeleteDocumentNG() throws Exception {

		Assert.assertTrue(DocumentsUtil.deleteDocument(docs.getDocIdent()),
				"FAIL: could not Delete Document in List After Creation");
	}

	@Test(groups = { "DocumentsNG" }, dataProvider="DocumentParams",dependsOnMethods="testDeleteDocumentNG")
	public void testValidationDocument(String docIdent, int strCount, boolean newDoc, boolean expected) throws Exception {

		docs.setDocIdent(docIdent);
		docs.setDescription(EFormsUtil.createRandomString(strCount));

		Assert.assertFalse(DocumentsUtil.editDocument(docs, newDoc,false),
						"FAIL: Should have a Validation about empty Document Field");
	}
	
	@DataProvider(name = "DocumentParams")
	public Object[][] generateDocumentParams() {
		docs = new Documents("V", 0, preFix);
		return new Object[][] {
				new Object[] {"",50,true,false},
				new Object[] {docs.getDocIdent(),301,false,false}
		};
	}

	@Test(groups = { "DocumentsNG" },dependsOnMethods="testValidationDocument")
	public void testUpdateDocumentNG() throws Exception {

		docs = new Documents("U", 0, preFix);

		Assert.assertTrue(DocumentsUtil.editDocument(docs, true,false),
				"FAIL: could not add Document");

		Assert.assertFalse(GeneralUtil.isButtonExistsByValue("Browse"),
				"FAIL: Browse Button Should not be present!");

		Assert.assertFalse(GeneralUtil.isFileFieldExistsById(IDocumentsConst.document_Doc_File_fileFld),
						"FAIL: File Field Shouldn't be present!");

	}
}
