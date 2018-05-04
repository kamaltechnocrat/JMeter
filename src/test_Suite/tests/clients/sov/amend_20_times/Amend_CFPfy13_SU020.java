/**
 * 
 */
package test_Suite.tests.clients.sov.amend_20_times;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.clients.ISovConst;
import test_Suite.constants.clients.ISovConst.ESovCfpFy13ApplicantsNum;
import test_Suite.constants.clients.ISovConst.ESovFOPPs;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.clients.Sov;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.clients.SovUtils;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */
@GUITest
@Test(singleThreaded = true)
public class Amend_CFPfy13_SU020 {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Sov sov;
	
	String jaynichols = "jaynichols";
	
	String EdHaggett = "Ed.Haggett";
	
	String LizRand = "Liz.Rand";
	
	String JohnLeu = "John.Leu";
	
	String JulieRobinson = "Julie.Robinson";
	
	String DebQuackenbush = "Deb.Quackenbush";
	
	String BillTalbott = "Bill.Talbott";
	
	String Doeadmin2 = "Doeadmin2";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "SovRegTest" })
	public void setUp() throws Exception {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.LoginAny(EdHaggett);
			// -----------------------------------
			
			sov = SovUtils.cfpFy13_Initialization(ESovFOPPs.CFPFY13DOE, ESovCfpFy13ApplicantsNum.SU020, "CFP Application", "Title I - Q4_4250");
			
			sov.setFoProjName(sov.getProjectName());
			
			sov.setFoProjAndSubProjName(sov.getFoProjName());

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "SovRegTest" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		try {
			
			sov = null;
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
			
			log.warn("Ending: " + this.getClass().getSimpleName());
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	private void setFullFOProj() throws Exception {
		
		sov.setTmpCurrStepName(sov.getCurrStepName());
		
		sov.setFoProjAndSubProjName(sov.getFoProjName().concat(" - ").concat(sov.getSubProjectName()));
		
		sov.setCurrStepName(sov.getFoProjAndSubProjName());		
	}
	
	private void setFullPoProject() throws Exception {
		
		sov.setProjAndSubName(sov.getProjectName().concat(" - ").concat(sov.getSubProjectName()));
	}
	
	private void setPoProject() throws Exception {
		
		sov.setProjAndSubName(sov.getProjectName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_01NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 01, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);	
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Not Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			setPoProject();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_02NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 02, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Not Approved"), "FAIL: to Submit Program Officer Approval");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_03NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 03, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JulieRobinson);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Not Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_04NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 04, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			//Testing if it actually skipped
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Not Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			//testing the skip of a step due to decision
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[8]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should not be available");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_05NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 05, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			setPoProject();
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Not Approved"), "FAIL: Cfo Approval ESign should be available");
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_06NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 06, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should be available");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[9]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperGrantAgreementESign(sov), "FAIL: to Submit Super Grant Agreement ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[10]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Not Approved"), "FAIL: to Submit Cfo Grant Agreement ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);
			
			//testing the skip of a step due to decision
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[11]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitGrantAgreementApproved(sov), "FAIL: Grant Agreement Approved should not be available");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_07NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 07, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);				
			
			setPoProject();		
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Not Approved"), "FAIL: Cfo Approval ESign should be available");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_08NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);
			
			log.warn("Starting Amendment Number 08, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			//Testing if it actually skipped
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);					
			
			setPoProject();	
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Not Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			//testing the skip of a step due to decision
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[8]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should not be available");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_09NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 09, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Not Approved"), "FAIL: to Submit initial Review");
			
			//test the skip
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: Program Officer Approval should not be available");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			setPoProject();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_10NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 10, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);				
			
			setPoProject();		
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JulieRobinson);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Not Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_11NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 11, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);					
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);	
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);				
			
			setPoProject();		
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should be available");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[9]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperGrantAgreementESign(sov), "FAIL: to Submit Super Grant Agreement ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[10]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Not Approved"), "FAIL: to Submit Cfo Grant Agreement ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);
			
			//testing the skip of a step due to decision
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[11]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitGrantAgreementApproved(sov), "FAIL: Grant Agreement Approved should not be available");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_12NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 12, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);					
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);	
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			//testing skip
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: PA Quarterly Financial Report Shoul not be available!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);					
			
			setPoProject();	
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should be available");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[9]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperGrantAgreementESign(sov), "FAIL: to Submit Super Grant Agreement ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[10]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Approved"), "FAIL: to Submit Cfo Grant Agreement ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[11]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementApproved(sov), "FAIL: Grant Agreement Approved should be available");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[12]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApprovedAward(sov), "FAIL: to Submit Approved Award");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);

			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[13]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceCreateSubmissionSchedule(sov), "FAIL: to Submit Finance Create Submission Schedule");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_13NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 13, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);					
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);	
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);					
			
			setPoProject();	
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Not Approved"), "FAIL: Cfo Approval ESign should be available");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_14NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 14, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);					
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);	
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);				
			
			setPoProject();		
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Not Approved"), "FAIL: Cfo Approval ESign should be available");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_15NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 15, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);					
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);	
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));
			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);					
			
			setPoProject();	
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Not Approved"), "FAIL: to Submit Program Officer Approval");
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_16NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 16, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			


			
			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);				
			
			setPoProject();		
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JulieRobinson);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Not Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_17NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 17, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));


			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);					
			
			setPoProject();	
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JulieRobinson);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Not Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_18NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 18, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);					
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);	
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));


			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);						
			
			setPoProject();
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JulieRobinson);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Not Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_19NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 19, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);					
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);	
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Not Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			setPoProject();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testAmendment_20NG() throws Exception {
		
		try {
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			log.warn("Starting Amendment Number 20, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

						
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[0]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit CFP Application");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(jaynichols);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			setPoProject();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
