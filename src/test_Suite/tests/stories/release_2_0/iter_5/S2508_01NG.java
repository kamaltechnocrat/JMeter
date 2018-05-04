/**
 * Steps:
 * 1. Open G3 PO, log as admin user
 * 2. Navigate to Reports screen, create new Report (delete previous report under the same name if exists)
 * 3. Check if Report Planner screen contains Organization node (look for an icon by ALT tag)
 * 4. Check if Report Details screen contains Primary Organization and Organization Access information
 * 5. Delete report created on step #2 to reset testing environment
 */
package test_Suite.tests.stories.release_2_0.iter_5;

import static watij.finders.SymbolFactory.alt;
import static watij.finders.SymbolFactory.id;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IReportsConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.cases.Reports;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.runtime.ie.IE;

/**
 * @author apankov
 * Test script for Story #2508
 */
@Test(singleThreaded = true)
public class S2508_01NG {
	private static Log log = LogFactory.getLog(S2508_01NG.class);
	private String reportIdentifier = "Programs_Listing_Report";
	private String reportName       = "Programs_Listing_Report.rpt";
	private String [] reportTitles = {"Report", "Report", "Report"};
	Reports report;
	IE ie;
	
	@BeforeClass  
	public void setUp() {

	} 
	
	/*@Test(groups = { "Iter_25" })
	*//**
	 * Main method to call subroutines (other methods) sequentially
	 *//*
	public void s2508_01NG() throws Exception{
		GeneralUtil.navigateToPO();
		GeneralUtil.loginPO();
		ClicksUtil.clickLinks(IClicksConst.openReportsList);	
		log.info("Navigate to Reports screen");
		
		ReportsUtil.deleteReport(reportTitles); //delete report if report under the same name already exists
		createNewReport();
		verifyReportDetails();
		ReportsUtil.deleteReport(reportTitles);
		closeBrowser();
	}
*/
	/**
	 * Create one new report
	 */
	@SuppressWarnings("unused")
	private void createNewReport()
	{
		try
		{	
			report = new Reports();
			report.initReprots();
			report.setReportIdent(reportIdentifier);
			report.setReportOrgName(IGeneralConst.primG3_OrgRoot);
			report.setReportOrgAccess(IGeneralConst.org_Access_Public);
			report.setReportName(reportName);
			report.setReportTitles(reportTitles);
			report.addOneNewReport();
			
		}
		catch(Exception ex)
		{
			log.debug("ERROR in createNewReport() " + ex.getMessage());
		}
	}
	/**
	 * Verify if Organization node does not exist in Report Planner
	 * Verify if Primary Organization and Organization Access controls are on Details screen
	 */
	@SuppressWarnings("unused")
	private void verifyReportDetails()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openReportsList);
			for(int i=0; i<reportTitles.length; i++)
			{
				log.info("Title: " + reportTitles[i]);
				int ind = TablesUtil.getRowIndex(ITablesConst.reprotsTableId, reportTitles[i]);
				log.info("Row index " + ind);
				if(ind > -1)
				{
					ie.table(id, ITablesConst.reprotsTableId).row(ind).cell(IReportsConst.eReportsGridFields.reportTitle.ordinal()).link(0).click();
					if(ie.link(alt, "/Organization/").exists())
						log.info("TEST FAILED - Organization node exists in Report Planner");
					else
						log.info("TEST PASSED - Organization node does not exist in Report Planner");
					ClicksUtil.clickImageByAlt("Open " + reportIdentifier);
					if(ie.selectList(id, IEFormsConst.formPrimaryOrg_DropdownField_Id).exists() && 
							ie.selectList(id, IEFormsConst.formOrgAccess_DropdownField_Id).exists())
						log.info("TEST PASSED - Primary Organization and Organization Access controls found");
					else
						log.info("TEST FAILED - Primary Organization and/or Organization Access controls not found");
					ClicksUtil.clickLinks(IClicksConst.openReportsList);
					break;
				}
			}
		}
		catch(Exception ex)
		{
			log.debug("ERROR in verifyReportDetails() " + ex.getMessage());
		}
	}

	/**
	 * Close browser after all done
	 */
	@SuppressWarnings("unused")
	private void closeBrowser()
	{
		try
		{	
			ie = IEUtil.getActiveIE();
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		}
		catch(Exception ex)
		{
			log.debug("ERROR in closeBrowser() " + ex.getMessage());
		}
	}
	
}
