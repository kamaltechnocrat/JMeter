/**
 * 
 */
package test_Suite.tests.rptFunctions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.reporting_Functions.IEtlConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.reporting_Functions.EtlUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class Add_RptFieldFromCSV {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "EtlNG" })
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

	@AfterClass(groups = { "EtlNG" }, alwaysRun=true)
	public void tearDown() {
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
//	@Test(groups = { "EtlNG" })
//	public void addSubFields() throws Exception {
//		
//		try {
//			
//			ClicksUtil.clickLinks(IClicksConst.openReportingFiledsSubmissionjLnk);
//			
//			Assert.assertTrue(EtlUtil.addSubFieldsFromCSV(IEtlConst.etlDM_RptFieldsCSVFileSub), "FAIL: Couldn't add report fields!");
//			
//		} catch (Exception e) {
//			Assert.fail("Unexpected Exception: ", e);
//		}
//	}

	/**
	 * 
	 * @throws Exception
	 */
//	@Test(groups = { "EtlNG" })
//	public void addApplicantFields() throws Exception {
//		
//		try {
//			
//			ClicksUtil.clickLinks(IClicksConst.openReportingFiledsApplicantLnk);
//			
//			Assert.assertTrue(EtlUtil.addSubFieldsFromCSV(IEtlConst.etlDM_RptFieldsCSVFileApp), "FAIL: Couldn't add report fields!");
//			
//		} catch (Exception e) {
//			Assert.fail("Unexpected Exception: ", e);
//		}
//	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EtlNG" })
	public void addSubFolders() throws Exception {
		
		try {
			
			ClicksUtil.clickLinks(IClicksConst.openReportingFiledsSubmissionjLnk);
			
			Assert.assertTrue(EtlUtil.addListFoldersFromCSV(IEtlConst.etlDM_RptFieldsCSVFileSubELists), "FAIL: Couldn't add report fields!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
//	@Test(groups = { "EtlNG" })
//	public void addAdminFolders() throws Exception {
//		
//		try {
//			
//			ClicksUtil.clickLinks(IClicksConst.openReportingFiledsFOPPLnk);
//			
//			Assert.assertTrue(EtlUtil.addListFoldersFromCSV(IEtlConst.etlDM_RptFieldsCSVFileFoppELists), "FAIL: Couldn't add report fields!");
//			
//		} catch (Exception e) {
//			Assert.fail("Unexpected Exception: ", e);
//		}
//	}

	/**
	 * 
	 * @throws Exception
	 */
//	@Test(groups = { "EtlNG" })
//	public void addOrgFolders() throws Exception {
//		
//		try {
//			
//			ClicksUtil.clickLinks(IClicksConst.openReportingFiledsOrgLnk);
//			
//			Assert.assertTrue(EtlUtil.addListFoldersFromCSV(IEtlConst.etlDM_RptFieldsCSVFileOrgHighELists), "FAIL: Couldn't add report fields!");
//			
//		} catch (Exception e) {
//			Assert.fail("Unexpected Exception: ", e);
//		}
//	}

	/**                        
	 * 
	 * @throws Exception
	 */
//	@Test(groups = { "EtlNG" })
//	public void addAppFields() throws Exception {
//		
//		try {
//			
//			ClicksUtil.clickLinks(IClicksConst.openReportingFiledsApplicantLnk);
//			
//			ClicksUtil.clickImageByAlt(IClicksConst.dmAddNewRptFld_Alt);
//			
//			Assert.assertTrue(EtlUtil.addSubFieldsFromCSV(IEtlConst.etlDM_RptFieldsCSVFileApp), "FAIL: Couldn't add report fields!");
//			
//		} catch (Exception e) {
//			Assert.fail("Unexpected Exception: ", e);
//		}
//	}

	/**
	 * 
	 * @throws Exception
	 */
//	@Test(groups = { "EtlNG" })
//	public void addAppFolders() throws Exception {
//		
//		try {
//			
//			ClicksUtil.clickLinks(IClicksConst.openReportingFiledsApplicantLnk);
//			
//			Assert.assertTrue(EtlUtil.addListFoldersFromCSV(IEtlConst.etlDM_RptFieldsCSVFileAppELists), "FAIL: Couldn't add report fields!");
//			
//		} catch (Exception e) {
//			Assert.fail("Unexpected Exception: ", e);
//		}
//	}

	/**
	 * 
	 * @throws Exception
	 */
//	@Test(groups = { "EtlNG" })
//	public void addAppFolders() throws Exception {
//		
//		try {
//			
//			ClicksUtil.clickLinks(IClicksConst.openReportingFiledProjectjLnk);
//			
//			Assert.assertTrue(EtlUtil.addListFoldersFromCSV(IEtlConst.etlDM_RptFieldsCSVFileProjELists), "FAIL: Couldn't add report fields!");
//			
//		} catch (Exception e) {
//			Assert.fail("Unexpected Exception: ", e);
//		}
//	}

}
