/**
 * 
 */
package test_Suite.tests.reports;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class ReportingFieldsConfigNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ReportsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			ClicksUtil.clickLinks(IClicksConst.openReportingFieldsListLnk);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ReportsNG" }, alwaysRun=true)
	public void tearDown() {
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ReportsNG" })
	public void testImportingReportingFields() throws Exception {
		try {
			
			ClicksUtil.clickImage(IClicksConst.imprtImg);
			
			ClicksUtil.clickButtons(IClicksConst.uploadBtn);
			
			ClicksUtil.clickButtons(IClicksConst.backBtn);			
			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ReportsNG" })
	public void testAddingNewReportingFields() throws Exception {
		try {
			
			ClicksUtil.clickImage(IClicksConst.newImg);
			
			ClicksUtil.clickButtons(IClicksConst.report_BackToReportingFieldListBtn);
			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
