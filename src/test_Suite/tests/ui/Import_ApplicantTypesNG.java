/**
 * 
 */
package test_Suite.tests.ui;

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
public class Import_ApplicantTypesNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "UI_AdminNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "UI_AdminNG" }, alwaysRun=true)
	public void tearDown() {
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "UI_AdminNG" })
	public void exportApplicantTypes() throws Exception {
		
		try {
			
			ClicksUtil.clickLinks(IClicksConst.openApplicantTypesListsLnk);
			
			ClicksUtil.clickImageByAlt(IClicksConst.importApplicantTypes);
			
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
	@Test(groups = { "UI_AdminNG" })
	public void editApplicantTypes() throws Exception {
		try {
			
			ClicksUtil.clickLinks(IClicksConst.openApplicantTypesListsLnk);
			
			ClicksUtil.clickLinks(IClicksConst.applicantTypeOrg);
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
			ClicksUtil.clickButtons(IClicksConst.applicantTypes_BackToAppTypesListBtn);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
