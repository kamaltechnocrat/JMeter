package test_Suite.tests.r4_1.Audit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.reporting_Functions.IAuditConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;

@GUITest
@Test(singleThreaded = true)
public class auditHistoryNG {

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
				
				Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openAuditReports), "FAIL: Could not open Audit Reporting.");
				
				Assert.assertTrue(ClicksUtil.clickLinks(IAuditConst.auditHistoryLnk), "FAIL: Could not open Audit History View.");
				
				ClicksUtil.clickLinks(IClicksConst.clearSearchLnk);
				
				//search on today's events
				FiltersUtil.searchListByLabel(IFiltersConst.audit_Date_Lbl, GeneralUtil.getTodayDate(), IFiltersConst.onBeforeLastSub);
								
				// search on entity - Applicant
				FiltersUtil.searchListByLabel(IFiltersConst.audit_Entity_Lbl, "", IFiltersConst.audit_Entity_Applicant);
				
				// search on user Xxx 
				FiltersUtil.searchListByLabel(IFiltersConst.audit_ByUser_Lbl, IAuditConst.userFront, "");
				
				//repeat the date entry to overcome a known issue with the dropdown that 
				//reset/reload the page, therefore losing the current date
				FiltersUtil.searchListByLabel(IFiltersConst.audit_Date_Lbl, GeneralUtil.getTodayDate(), "");
				
				Assert.assertTrue(TablesUtil.findInTable(ITablesConst.auditHistoryTableId, IAuditConst.applicantFO), "FAIL: Could not find History Entry:  ".concat(IAuditConst.applicantFO));

						
			} catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			}
		}
		
 }