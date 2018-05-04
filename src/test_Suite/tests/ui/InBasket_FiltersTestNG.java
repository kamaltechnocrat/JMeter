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

import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;

@GUITest
@Test(singleThreaded = true)
public class InBasket_FiltersTestNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends InBasket_FiltersTestNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = "UI_ListNG")
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());
		
		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
	}

	@AfterClass(groups = "UI_ListNG")
	public void tearDown() {
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	@Test(groups = "UI_ListNG")
	public void inBasket_FiltersTestNG() throws Exception {

		try {

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}
}
