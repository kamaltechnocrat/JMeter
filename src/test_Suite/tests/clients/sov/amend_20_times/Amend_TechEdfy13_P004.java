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
import test_Suite.constants.clients.ISovConst.ESovTechEdApplicantsNum;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.clients.Sov;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.clients.SovUtils;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */
@GUITest
@Test(singleThreaded = true)
public class Amend_TechEdfy13_P004 {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Sov sov;
	
	String foUser = "KathrynDaley";
	
	String JaneMurtagh = "Jane.Murtagh";
	
	String MarkLang = "Mark.Lang";
	
	String JayRamsey = "Jay.Ramsey";
	
	String BillTalbott = "Bill.Talbott";
	
	String Doeadmin2 = "Doeadmin2";
	
	String TomAlderman = "Tom.Alderman";
	
	String JohnLeu = "John.Leu";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "SovRegTest" })
	public void setUp() throws Exception {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.LoginAny(JaneMurtagh);
			// -----------------------------------
			
			sov = SovUtils.techEdFy13_Initialization(ESovFOPPs.TechEdFY13, ESovTechEdApplicantsNum.P004, "Grant Application", "TEE Final FN_3330");
			
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
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Not Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPOApproval(sov, "Not Approved"), "FAIL: Program Officer Approval should not be Submittable!");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Not Approved"), "FAIL: to Submit PA Program TeamReview Payment Request");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: PA Financial Report Review should not be Submittable");

			
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
			
			log.warn("Starting Amendment Number 02, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Not Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertFalse(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should not be Submittable");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Approved"), "FAIL: to Submit PA Program TeamReview Payment Request");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[15]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: PA Tip Gap Payment Should not be submittable");
			
			
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
	public void testAmendment_03NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 03, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should be Submittable");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Not Approved"), "FAIL: to Submit Finance Grant Agreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(TomAlderman);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[5]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: Director Grant Review should Not be submittable!");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[15]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: PA Tip Gap Payment Should be submittable");
			
			
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
	public void testAmendment_04NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 04, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should be Submittable");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance Grant Agreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(TomAlderman);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Not Approved"), "FAIL: Director Grant Review should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[6]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should Not be submittable!");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: PA Financial Report Review should not be Submittable");

			
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
	public void testAmendment_05NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 05, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should be Submittable");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance Grant Agreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(TomAlderman);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: Director Grant Review should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Not Approved"), "FAIL: Cfo Approval ESign should be submittable!");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[7]);
			
			Assert.assertFalse(SovUtils.techEdFy13_SubmitGrantAwardESign(sov, "Not Approved"), "FAIL: Super Grant Agreement ESign should Not be submittable!");
			
			ClicksUtil.returnFromAnyForm();
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[15]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: PA Tip Gap Payment Should not be submittable");
			
			
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
	public void testAmendment_06NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 06, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should be Submittable");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance Grant Agreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(TomAlderman);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: Director Grant Review should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should be submittable!");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[7]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAwardESign(sov, "Not Approved"), "FAIL: Super Grant Agreement ESign should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[8]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Not Approved"), "FAIL: Cfo Grant Agreement ESign should Not be submittable!");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[15]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: PA Tip Gap Payment Should Not be submittable");
			
			
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
	public void testAmendment_07NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 07, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should be Submittable");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance Grant Agreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(TomAlderman);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: Director Grant Review should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should be submittable!");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[7]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAwardESign(sov, "Approved"), "FAIL: Super Grant Agreement ESign should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Not Approved"), "FAIL: Cfo Grant Agreement ESign should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[9]);
			
			Assert.assertFalse(SovUtils.nordFy14_SubmitGrantAgreementApproved(sov), "FAIL: Grant Agreement Approved Should Not be Submittable!");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: PA Financial Report Review should not be Submittable");

			
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
	public void testAmendment_08NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 08, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should be Submittable");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance Grant Agreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(TomAlderman);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: Director Grant Review should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should be submittable!");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[7]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAwardESign(sov, "Approved"), "FAIL: Super Grant Agreement ESign should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Approved"), "FAIL: Cfo Grant Agreement ESign should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[9]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantAgreementApproved(sov), "FAIL: Grant Agreement Approved Should be Submittable!");
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[10]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApprovedAward(sov), "FAIL: to Submit Approved Award");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[11]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceCreateSubmissionSchedule(sov), "FAIL: to Submit Finance Create Submission Schedule");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Approved"), "FAIL: to Submit PA Program TeamReview Payment Request");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[15]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: PA Tip Gap Payment Should not be submittable");
			
			
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
	public void testAmendment_09NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 09, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Not Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPOApproval(sov, "Not Approved"), "FAIL: Program Officer Approval should not be Submittable!");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[15]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: PA Tip Gap Payment Should be submittable");
			
			
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
			
			log.warn("Starting Amendment Number 10, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Not Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertFalse(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should not be Submittable");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Not Approved"), "FAIL: to Submit PA Program TeamReview Payment Request");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: PA Financial Report Review should not be Submittable");

			
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
	public void testAmendment_11NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 11, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should be Submittable");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Not Approved"), "FAIL: to Submit Finance Grant Agreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(TomAlderman);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[5]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: Director Grant Review should Not be submittable!");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Approved"), "FAIL: to Submit PA Program TeamReview Payment Request");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[15]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: PA Tip Gap Payment Should not be submittable");
			
			
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
	public void testAmendment_12NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 12, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should be Submittable");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance Grant Agreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(TomAlderman);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Not Approved"), "FAIL: Director Grant Review should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[6]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should Not be submittable!");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[15]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: PA Tip Gap Payment Should be submittable");
			
			
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
	public void testAmendment_13NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 13, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should be Submittable");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance Grant Agreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(TomAlderman);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: Director Grant Review should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Not Approved"), "FAIL: Cfo Approval ESign should be submittable!");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[7]);
			
			Assert.assertFalse(SovUtils.techEdFy13_SubmitGrantAwardESign(sov, "Not Approved"), "FAIL: Super Grant Agreement ESign should Not be submittable!");
			
			ClicksUtil.returnFromAnyForm();
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Not Approved"), "FAIL: to Submit PA Program TeamReview Payment Request");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: PA Financial Report Review should not be Submittable");

			
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
	public void testAmendment_14NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 14, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should be Submittable");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance Grant Agreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(TomAlderman);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: Director Grant Review should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should be submittable!");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[7]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAwardESign(sov, "Not Approved"), "FAIL: Super Grant Agreement ESign should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[8]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Not Approved"), "FAIL: Cfo Grant Agreement ESign should Not be submittable!");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Approved"), "FAIL: to Submit PA Program TeamReview Payment Request");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[15]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: PA Tip Gap Payment Should not be submittable");
			
			
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
	public void testAmendment_15NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 15, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should be Submittable");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance Grant Agreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(TomAlderman);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: Director Grant Review should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should be submittable!");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[7]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAwardESign(sov, "Approved"), "FAIL: Super Grant Agreement ESign should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Not Approved"), "FAIL: Cfo Grant Agreement ESign should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[9]);
			
			Assert.assertFalse(SovUtils.nordFy14_SubmitGrantAgreementApproved(sov), "FAIL: Grant Agreement Approved Should Not be Submittable!");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[15]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: PA Tip Gap Payment Should be submittable");
			
			
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
	public void testAmendment_16NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 16, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should be Submittable");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance Grant Agreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(TomAlderman);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: Director Grant Review should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should be submittable!");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[7]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAwardESign(sov, "Approved"), "FAIL: Super Grant Agreement ESign should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Approved"), "FAIL: Cfo Grant Agreement ESign should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[9]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantAgreementApproved(sov), "FAIL: Grant Agreement Approved Should be Submittable!");
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[10]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApprovedAward(sov), "FAIL: to Submit Approved Award");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[11]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceCreateSubmissionSchedule(sov), "FAIL: to Submit Finance Create Submission Schedule");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Not Approved"), "FAIL: to Submit PA Program TeamReview Payment Request");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: PA Financial Report Review should not be Submittable");

			
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
	public void testAmendment_17NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 17, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Not Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPOApproval(sov, "Not Approved"), "FAIL: Program Officer Approval should not be Submittable!");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Approved"), "FAIL: to Submit PA Program TeamReview Payment Request");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[15]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: PA Tip Gap Payment Should not be submittable");
			
			
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
	public void testAmendment_18NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 18, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Not Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertFalse(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should not be Submittable");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[15]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: PA Tip Gap Payment Should be submittable");
			
			
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
	public void testAmendment_19NG() throws Exception {
		
		try {
			
			log.warn("Starting Amendment Number 19, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should be Submittable");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Not Approved"), "FAIL: to Submit Finance Grant Agreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(TomAlderman);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[5]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: Director Grant Review should Not be submittable!");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Not Approved"), "FAIL: to Submit PA Program TeamReview Payment Request");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: PA Financial Report Review should not be Submittable");

			
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
			
			log.warn("Starting Amendment Number 20, the Project reached the end of workflow!");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.amendApplicationSubmission(sov),"FAIL: Unable to request amendment on CFP Application of Project: ".concat(sov.getProjectName()));	

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);	
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);		
			
			Assert.assertTrue(SovUtils.amendInitialPostAwardSubmission(sov), "FAIL: Unable to request amendment on Initial Post award of: ".concat(sov.getSubProjectName()));	

			
			//Start re-execution
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[0]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplication(sov), "FAIL: Could not Submit Grant Application");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);					
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");

			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JaneMurtagh);			
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: Grant Agreement Created Should be Submittable");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JohnLeu);			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[4]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance Grant Agreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(TomAlderman);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Not Approved"), "FAIL: Director Grant Review should be submittable!");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(BillTalbott);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[6]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: Cfo Approval ESign should Not be submittable!");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			setFullFOProj();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(JayRamsey);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			setFullPoProject();
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Approved"), "FAIL: to Submit PA Program TeamReview Payment Request");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(MarkLang);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView, "Not Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[15]);
			
			Assert.assertFalse(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_InProgress_StatusSubView), "FAIL: PA Tip Gap Payment Should not be submittable");
			
			
			setPoProject();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
