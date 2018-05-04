/**
 * 
 */
package test_Suite.tests.ui;

/**
 * @author mshakshouki
 * 
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IDocumentsConst;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.lib.cases.Documents;
import test_Suite.utils.cases.DocumentsUtil;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;

@GUITest
@Test(singleThreaded = true)
public class Documents_FiltersTestNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends Documents_FiltersTestNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	String baseLetter;
	
	String docName;
	
	String preFix = IDocumentsConst.document_Doc_Init;
	
	Documents document;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = "UI_ListNG")
	public void setUp() {
		// code that will be invoked when this test is instantiated

		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			for (int x = 5; x < 10; x++) {
				baseLetter = IGeneralConst.baseLetters[x];

				for (int y = 0; y < IDocumentsConst.document_DocFiles.length; y++) {

					document = new Documents(baseLetter, y, preFix);

					DocumentsUtil.uploadDocument(document, true);
				}
			}

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	@AfterClass(groups = "UI_ListNG")
	public void tearDown() {
		
		document = null;
		baseLetter = null;
		docName = null;
		document = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = "UI_ListNG")
	public void documents_FiltersTestNG() throws Exception {

	}

}
