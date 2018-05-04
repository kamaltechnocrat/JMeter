package test_Suite.tests.r4_1.Audit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;

@GUITest
@Test(singleThreaded = true)
public class auditRunFirst {

	// #####***********************************************************************
		// ### To set up the Global Params Name Vars
		// #####***********************************************************************
		Class<?> ownClass = this.getClass();

		private Log log = LogFactory.getLog(ownClass);

		/*------ End of Global Vars --------------*/

		@BeforeClass(groups = { "AuditRepNG" })
		public void setUp() throws Exception {
			
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

		@AfterClass(groups = { "AuditRepNG" }, alwaysRun=true)
		public void tearDown() throws Exception {
			
			try {
				
				GeneralUtil.Logoff();
				IEUtil.closeBrowser();
				
				log.warn("Ending: " + this.getClass().getSimpleName());
				
			} catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			}
	}

		/**
		 * 
		 * @throws Exception
		 */
		
		
		@Test(groups = { "AuditRepNG" })
		public void testCaseAuditEventsNG() throws Exception {
			try {
				
				Assert.assertTrue(GeneralUtil.runQuartzJob(IClicksConst.quartzAuditReportingJob), "FAIL: Could not run Quartz Job " .concat(IClicksConst.quartzAuditReportingJob));
				
				GeneralUtil.takeANap(2.000);

								
			} catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			}
		}
		
 }