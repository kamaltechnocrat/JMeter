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
public class auditEventsNG {

	// #####***********************************************************************
		// ### To set up the Global Params Name Vars
		// #####***********************************************************************
		Class<?> ownClass = this.getClass();

		private Log log = LogFactory.getLog(ownClass);
		
		int numberOfEntries;

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
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
			
			log.warn("Ending: " + this.getClass().getSimpleName());
		}

		/**
		 * 
		 * @throws Exception
		 */
		@Test(groups = { "AuditRepNG" })
		public void testCaseAuditEventsNG() throws Exception {
			try {

				Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openAuditReports), "FAIL: Could not open Audit Reporting");
				
				Assert.assertTrue(ClicksUtil.clickLinks(IAuditConst.auditEventsLnk), "FAIL: Could not open Events");
				
				ClicksUtil.clickLinks(IClicksConst.clearSearchLnk);
				
				//search on today's events
				Assert.assertTrue(FiltersUtil.filterAuditList(IFiltersConst.audit_Date_Lbl, GeneralUtil.getTodayDate(), IFiltersConst.onBeforeLastSub));
								
				// search on user login successfully
				Assert.assertTrue(FiltersUtil.filterAuditList(IFiltersConst.audit_Event_Lbl, "", IFiltersConst.audit_Event_Login));
				
				// search on user Xxx login 
				Assert.assertTrue(FiltersUtil.filterAuditList(IFiltersConst.audit_ByUser_Lbl, IAuditConst.newUserLogin, ""));
				
				//repeat the date entry to overcome a known issue with the dropdown that 
				//reset/reload the page, therefore losing the current date
				Assert.assertTrue(FiltersUtil.filterAuditList(IFiltersConst.audit_Date_Lbl, GeneralUtil.getTodayDate(), ""));
				
				Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.searchBtn));
				
				//numberOfEntries = TablesUtil.findHowManyInTable(ITablesConst.auditEventsTableId, IAuditConst.newUserLogin);
				numberOfEntries = TablesUtil.howManyEntriesInTable(ITablesConst.auditEventsTableId);
							
			} catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			}
		}


		@Test(groups = { "AuditRepNG" }, dependsOnMethods={"testCaseAuditEventsNG"})
		public void testLoginReportOfficerNG() throws Exception {
			try {
				
				GeneralUtil.switchToPOOnly();
				
				GeneralUtil.LoginAny(IAuditConst.newUserLogin);
				
			} catch (Exception e) {
				Assert.fail("Unexpected Exception: ", e);
			}
		}
		
		@Test(groups = { "AuditRepNG" }, dependsOnMethods={"testLoginReportOfficerNG"})
		public void testAuditorCheckNG() throws Exception {
				try {
					
					GeneralUtil.switchToPO();
					
					Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openAuditReports), "FAIL: Could not open Audit Reporting");
					
					Assert.assertTrue(ClicksUtil.clickLinks(IAuditConst.auditEventsLnk), "FAIL: Could not open Events");
					
					ClicksUtil.clickLinks(IClicksConst.clearSearchLnk);
					
					//search on today's events
					Assert.assertTrue(FiltersUtil.filterAuditList(IFiltersConst.audit_Date_Lbl, GeneralUtil.getTodayDate(), IFiltersConst.onBeforeLastSub));
									
					// search on user login successfully
					Assert.assertTrue(FiltersUtil.filterAuditList(IFiltersConst.audit_Event_Lbl, "", IFiltersConst.audit_Event_Login));
					
					// search on user Xxx login 
					Assert.assertTrue(FiltersUtil.filterAuditList(IFiltersConst.audit_ByUser_Lbl, IAuditConst.newUserLogin, ""));
					
					//repeat the date entry to overcome a known issue with the dropdown that 
					//reset/reload the page, therefore losing the current date
					Assert.assertTrue(FiltersUtil.filterAuditList(IFiltersConst.audit_Date_Lbl, GeneralUtil.getTodayDate(), ""));
					
					Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.searchBtn));
					
						
					Assert.assertTrue(TablesUtil.findInTable(ITablesConst.auditEventsTableId, IAuditConst.newUserLogin), "FAIL: Could not find logged user ".concat(IAuditConst.newUserLogin));
					 
					Assert.assertTrue(TablesUtil.isEntryAddedInTable(ITablesConst.auditEventsTableId, numberOfEntries), "FAIL: Thre is no new login for user ".concat(IAuditConst.newUserLogin));
											
				} catch (Exception e) {
					Assert.fail("Unexpected Exception: ", e);
				}
		}

}
