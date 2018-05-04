/**
 * REPORT EXPIRATION TEST
 * Steps:
 * 1. Open G3 PO as admin user
 * 2. Go to Reports screen
 * 3. Create new Report
 * 4. Set Report Users
 * 5. Launch new Report
 * 6. Take URL parameter from IE Browser
 * 7. Wait for expiration (take from Properties file specified)
 * 8. Open report the second time in new browser window
 * 9. Make sure report is not displayed
 * NOTE: the assumption made that .WAR file has the same expiration property,
 * hence the war .WAR file was built and not altered by the tool like 7-Zip
 */
package test_Suite.tests.stories.release_2_0.iter_3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static watij.finders.SymbolFactory.*;
import watij.runtime.ie.IE;

import test_Suite.constants.ui.*;

import test_Suite.utils.ui.*;
import test_Suite.utils.cases.*;
import test_Suite.lib.cases.Reports;
import test_Suite.constants.cases.IReportsConst;
import test_Suite.constants.cases.IGeneralConst;

/**
 * @author apankov
 * Story S2524 Test Case
 * Expiration test is limited to 60 seconds by test case logic.
 * For bigger intervals Story should be tested manually
 */
@SuppressWarnings("unused")
@Test(singleThreaded = true)
public class S2524_01NG {
	private static Log log = LogFactory.getLog(S2524_01NG.class);
	private static final String expirationPropertyFile = "report_integration.properties";
	private static final String expirationPropertyPath = "C:\\Programs\\Project\\grantium\\src\\"; //Machine specific
	private static final String expirationPropertyName = "report_integration.num_sec_to_expire";
	private static final String SEARCH_TOKEN_START = "window.open(";
	private static final String SEARCH_TOKEN_END   = ")";
	private static final String CHAR_QUOTE   = "'";
	private int expirationPropertyValue = 0;
	private String reportViewerUrl  = null;
	private String reportIdentifier = "Programs_Listing_Report";
	private String reportName       = "Programs_Listing_Report.rpt";
	private String [] reportTitles = {"Report", "Report", "Report"};
	private String [] reportUsers  = {"Super"};
	Reports report;
	IE ie;
	
	@BeforeClass  
	public void setUp() {
		
	} 
	
	@Test(groups = { "Iter_23" })
	/**
	 * Main method to call subroutines sequentially
	 */
	/*public void s2524_01NG() {
		try
		{
			String expirationProperty = getProperty(expirationPropertyPath+expirationPropertyFile, expirationPropertyName);
			expirationPropertyValue = Integer.parseInt(expirationProperty);
			if(expirationPropertyValue == 0 || expirationPropertyValue < 10 || expirationPropertyValue > 60)
				log.info("Expiration property does not exist, or its interval either too big or too small to proceed. " + expirationPropertyValue);
			else
			{
				log.info("Expiration property is: " + expirationPropertyValue);
				navigateToReports();
				ReportsUtil.deleteReport(reportTitles);//This will check if the report under the same name already exists and if it is - then delete it first
				//deleteReport(); 
				createNewReport();
				log.info("Report viewer: " + reportViewerUrl);
				assignReportUsers();
				launchReport();
				openReportInBrowser(); //open report twice checking for expiration
				navigateToReports();
				ReportsUtil.deleteReport(reportTitles); //finally delete report to clean up and reset testing environment
				//deleteReport();
				closeBrowser();
			}
		}
		catch(Exception ex)
		{
			log.error("ERROR in s2524_01NG() " + ex.getMessage());
		}
	}*/

	private void navigateToReports()
	{
		try
		{			
			IEUtil.openNewBrowser();
			ie = IEUtil.getActiveIE();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginPO();
			ClicksUtil.clickLinks(IClicksConst.openReportsList);	
			
		}
		catch(Exception ex)
		{
			log.debug("ERROR in navigateToReports() " + ex.getMessage());
		}
	}
	
	private void createNewReport()
	{
		try
		{	
			report = new Reports();
			report.initReprots();
			reportViewerUrl = report.getReportViewerFullPath();
			report.setReportIdent(reportIdentifier);
			report.setReportOrgName(IGeneralConst.primG3_OrgRoot);
			report.setReportOrgAccess(IGeneralConst.org_Access_Public);
			report.setReportName(reportName);
			report.setReportTitles(reportTitles);
			report.setAccessUser(reportUsers);
			report.addOneNewReport();
			
		}
		catch(Exception ex)
		{
			log.debug("ERROR in createNewReport() " + ex.getMessage());
		}
	}
	
	private void assignReportUsers()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openReportsList);	
			log.info("Navigate to Reports screen to add Users Access");
			report.allowReportAccess();
		}
		catch(Exception ex)
		{
			log.debug("ERROR in assignReportUsers() " + ex.getMessage());
		}
	}
	
	private void launchReport()
	{
		try
		{
			int rowIndx = -1;
			ClicksUtil.clickLinks(IClicksConst.openMyReportsListLnk);
			log.info("Navigate to My Reports screen to launch report");
			for(int i=0; i<reportTitles.length; i++)
			{
				rowIndx = TablesUtil.getRowIndex(ITablesConst.myReprotsTableId, reportTitles[i]);		
				if (rowIndx > -1)
				{
					ie.table(id, ITablesConst.myReprotsTableId).row(rowIndx).cell(IReportsConst.eMyReportsGridFields.reportTitle.ordinal()).link(0).click();
					ClicksUtil.clickButtons(IClicksConst.launchReport);
					reportViewerUrl = getChildURL(ie.html()); //WARNING: with PDF viewer here watij crashes !!!
					break;
				}
			}
			IE ie2 = new IE();
			//WARNING: following two line of code work only with HTML viewer but may not with PDF !
			ie2 = ie.childBrowser();
			ie2.close();
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();

		}
		catch(Exception ex)
		{
			log.debug("ERROR in launchReport() " + ex.getMessage());
		}	
	}
	
	private void openReportInBrowser() throws Exception
	{
		try
		{
			String strHTMLResponse = null;
			int timeToCheck = 0;
			while(timeToCheck <= expirationPropertyValue)
			{
				if(timeToCheck == expirationPropertyValue/2)//half time to expire
				{
					strHTMLResponse = getHtmlResponse(reportViewerUrl); //report should be viewed
					if(strHTMLResponse.contains(IReportsConst.report_CrystalReportViewer))
						log.info("TEST PASSED");
					else
						log.info("TEST FAILED - Unexpected output");
				}
				GeneralUtil.takeANap(1.0);
				timeToCheck ++;
			}
			strHTMLResponse = getHtmlResponse(reportViewerUrl);//invoke report after expiration
			if(strHTMLResponse.contains(IReportsConst.report_ReportExpirationError))
				log.info("TEST PASSED");
			else
				log.info("TEST FAILED. Report must expire by now");
		}
		catch(Exception ex)
		{
			log.debug("ERROR in openReportInBrowser() " + ex.getMessage());
		}

	}
	
	private String getHtmlResponse(String reportUrl) throws Exception
	{
		try
		{
			String strHTML = null;
			IEUtil.openNewBrowser();
			ie = IEUtil.getActiveIE();
			ie.navigate(reportUrl);
			strHTML = ie.html();
			return strHTML;
		}
		catch(Exception ex)
		{
			return "HTML _ OK";	
		}
		finally
		{
			GeneralUtil.takeANap(1.0);
			IEUtil.closeBrowser();
		}
	}
	
	private void closeBrowser() throws Exception
	{
		ie = IEUtil.getActiveIE();
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
	}
	/**
	 * Method to extract Property from Property file
	 * @param propFilePath
	 * @param propName
	 * @return Property as String
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	private String getProperty(String propFilePath, String propName) throws FileNotFoundException, Exception
	{		
		String propertyValue = null;
		File file = new File(propFilePath);
		FileInputStream fis = null;
		Properties p = new Properties();
		try
		{             
			fis = new FileInputStream(file);
			p.load(fis);
			propertyValue = p.getProperty(propName);
			return propertyValue;
		}
		catch(FileNotFoundException fnfe)
		{
			log.error("throwing FileNotFoundException");
			throw new FileNotFoundException(fnfe.getMessage());
		}
		catch(IOException ioe)
		{
			log.error("throwing IOException");
			throw new Exception(ioe.getMessage());
		}
		catch(Exception ex)
		{
			log.error("throwing Exception");
			throw new Exception(ex.getMessage());
		}
		finally
		{
			try
			{
				fis.close();
				log.info("property input closed");
			}
			catch(Exception ec)
			{
				log.error("Error closing file stream: " + ec.getMessage());
				ec.printStackTrace();
			}
			fis  = null;
			file = null;
		}

	}
	/**
	 * Method returns report url from parent's page HTML content
	 * @param content
	 * @return String 
	 * @throws Exception
	 */
	private String getChildURL(String content) throws Exception
	{
		String result = null;
		int index     = 0;
		String tmp    = null;
		
		try
		{
			index = content.indexOf(SEARCH_TOKEN_START);
			//log.info("index: " + index);
			
			tmp = content.substring((index + SEARCH_TOKEN_START.length()+1), content.indexOf(SEARCH_TOKEN_END));
			//log.info("tmp: " + tmp);
			
			result = tmp.substring(0,tmp.indexOf(CHAR_QUOTE));
			log.info("result: " + result);
		}
		catch(Exception e)
		{
			log.error("Error finding token: " + e.getMessage());
		}
		
		return result;
	}
	
}
