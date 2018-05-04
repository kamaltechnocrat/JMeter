/**
 * 
 */
package test_Suite.tests.documents;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IDocumentsConst;
import test_Suite.constants.cases.ILookupsConst;
import test_Suite.constants.cases.ILookupsConst.ELookupsTypes;
import test_Suite.lib.cases.Documents;
import test_Suite.lib.cases.Lookups;
import test_Suite.utils.cases.DocumentsUtil;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class TestDocumentTypeAddedAndUsedNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends TestDocumentTypeAddedAndUsedNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	String baseLetter = "O";
	int docIndex = 2;
	String docName;
	String docIdent;

	String preFix = IDocumentsConst.document_Doc_Init;

	Lookups lookup;

	Lookups lookupValA;
	Lookups lookupValB;

	ArrayList<String> lookupParams;

	ILookupsConst.ELookupsTypes eLookupTypes;

	Documents docs;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "DocumentsNG" })
	public void setup() throws Exception {

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// ----------------------------------

			lookupParams = new ArrayList<String>();

			lookupParams.add(0, "Document Types");
			lookupParams.add(1, "Document Types");
			lookupParams.add(2, "G3");
			lookupParams.add(3, "Public");

			lookup = new Lookups(lookupParams, false, ELookupsTypes.generic);

			lookupParams = new ArrayList<String>();

			lookupParams.add(0, "Steps");
			lookupParams.add(1, "Document Types");
			lookupParams.add(2, "G3");
			lookupParams.add(3, "Public");

			lookupValA = new Lookups(lookupParams, lookup, false,
					ELookupsTypes.generic);

			lookup.addNewLookupAndValues();

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}

	}

	@AfterClass(groups = { "DocumentsNG" }, alwaysRun=true)
	public void tearDown() {
		
		docs = null;
		lookup = null;
		lookupValA = null;
		lookupValB = null;
		lookupParams = null;
		eLookupTypes = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = { "DocumentsNG" })
	public void testUploadDocumentWithNewTypeAddedNG() throws Exception {

		docs = new Documents(baseLetter, docIndex, preFix);

		docs.setDocType(lookupValA.getLookupFullName());

		Assert.assertTrue(DocumentsUtil.uploadDocument(docs, true),
				"FAIL: could not add Document");

	}

}
