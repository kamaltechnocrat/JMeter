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
import test_Suite.constants.clients.ISovConst.ESovFOPPs;
import test_Suite.constants.clients.ISovConst.ESovNordFy14ApplicantsNum;
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
public class Amend_Nordfy14_SU060 {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Sov sov;
	
	String foUser = "KarenGallese";
	
	String EdHaggett = "Ed.Haggett";
	
	String LizRand = "Liz.Rand";
	
	String JohnLeu = "John.Leu";
	
	String JulieRobinson = "Julie.Robinson";
	
	String DebQuackenbush = "Deb.Quackenbush";
	
	String BillTalbott = "Bill.Talbott";
	
	String poStaff3 = "Doeadmin2";
	
	String DavidBaroudi = "David.Baroudi";
	
	String SeanCousino = "Sean.Cousino";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "SovRegTest" })
	public void setUp() throws Exception {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.LoginAny(EdHaggett);
			// -----------------------------------
			
			sov = SovUtils.nordFy14_Initialization(ESovFOPPs.NORDFY14, ESovNordFy14ApplicantsNum.SU060, "Grant Application", "Title I NorD – Q1_4250");
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);	
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Not Approved"), "FAIL: to Submit initial Review");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Financial Report Review");
			
			
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
			
			log.warn("Starting Amendment Number 02, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Financial Report Review");
			
						
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Tip Gap Payment!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			

			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Financial Report Review");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Tip Gap Payment!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[5]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[6]);
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Financial Report Review");
			
			//Testing if it actually skipped
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Tip Gap Payment!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[5]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Not Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			//testing the skip of a step due to decision
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[8]);
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Financial Report Review");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Tip Gap Payment!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);				
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[5]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(SeanCousino);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[8]);
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Financial Report Review");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Tip Gap Payment!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[5]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should be available");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[9]);
			
			Assert.assertTrue(SovUtils.nordFY14_SubmitGrantAwardESign(sov, "Approved"), "FAIL: to Submit Super Grant Award ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[10]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Not Approved"), "FAIL: to Submit Cfo Grant Agreement ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);
			
			//testing the skip of a step due to decision
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[11]);
			
			Assert.assertFalse(SovUtils.nordFy14_SubmitGrantAgreementApproved(sov), "FAIL: Grant Agreement Approved should not be available");
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Financial Report Review");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Tip Gap Payment!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[5]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(SeanCousino);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[8]);
			
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
			
			log.warn("Starting Amendment Number 08, the Project reached the end of workflow!");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Not Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Financial Report Review");
			
			//Testing if it actually skipped
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Tip Gap Payment!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: Program Officer Approval Should not be available");
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Not Approved"), "FAIL: to Submit POApproval");
			
			//test the skip
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[5]);			
			
			Assert.assertFalse(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: GrantAgreementCreated should not be available");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Financial Report Review");
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Financial Report Review");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Tip Gap Payment!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[5]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(SeanCousino);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[6]);
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);					
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);	
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Financial Report Review");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Tip Gap Payment!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[5]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should be available");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[9]);
			
			Assert.assertTrue(SovUtils.nordFY14_SubmitGrantAwardESign(sov, "Approved"), "FAIL: to Submit Super Grant Award ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[10]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Not Approved"), "FAIL: to Submit Cfo Grant Agreement ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);
			
			//testing the skip of a step due to decision
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[11]);
			
			Assert.assertFalse(SovUtils.nordFy14_SubmitGrantAgreementApproved(sov), "FAIL: Grant Agreement Approved should not be available");
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);					
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);	
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Financial Report Review");
			
			//testing skip
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: PA Tip Gap Payment! Shoul not be available!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[5]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(SeanCousino);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should be available");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[9]);
			
			Assert.assertTrue(SovUtils.nordFY14_SubmitGrantAwardESign(sov, "Approved"), "FAIL: to Submit Super Grant Award ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[10]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Approved"), "FAIL: to Submit Cfo Grant Agreement ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[11]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantAgreementApproved(sov), "FAIL: Grant Agreement Approved should be available");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[12]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApprovedAward(sov), "FAIL: to Submit Approved Award");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);

			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[13]);
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);					
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);	
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));
			
			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Financial Report Review");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Tip Gap Payment!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);	
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[5]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[8]);
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);					
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);	
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Financial Report Review");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Tip Gap Payment!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[5]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(SeanCousino);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(DebQuackenbush);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[8]);
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);					
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);	
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Financial Report Review");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Tip Gap Payment!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(LizRand);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Financial Report Review");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Tip Gap Payment!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[5]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(SeanCousino);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[6]);
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);						
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Financial Report Review");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Tip Gap Payment!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[5]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(SeanCousino);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[6]);
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);					
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);	
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
				
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Financial Report Review");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: to Submit PA Tip Gap Payment!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			setPoProject();
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[5]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(SeanCousino);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[6]);
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);					
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);	
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Not Approved"), "FAIL: to Submit Program Officer Approval");
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Financial Report Review");
			
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
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on Grant Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));

			
			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application eSign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(EdHaggett);			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit PO Approval");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Financial Report Review");
			
			setPoProject();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}


}
