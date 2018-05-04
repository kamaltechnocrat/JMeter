/**
 * 
 */
package test_Suite.tests.clients.sov.tech_ed_fy13;

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
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */
@GUITest
@Test(singleThreaded = true)
public class Test_TechEdfy13_U006 {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Sov sov;
	
	String foUser = "RonaldStahley";
	
	String poOfficer1 = "Jane.Murtagh";
	
	String poPAOfficer1 = "Mark.Lang";
	
	String poStaff1 = "Jay.Ramsey";
	
	String poStaff2 = "Bill.Talbott";
	
	String poStaff3 = "Doeadmin2";
	
	String poStaff4 = "Tom.Alderman";
	
	String poStaff5 = "John.Leu";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "SovRegTest" })
	public void setUp() throws Exception {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.LoginAny(poStaff4);
			// -----------------------------------
			
			sov = SovUtils.techEdFy13_Initialization(ESovFOPPs.TechEdFY13, ESovTechEdApplicantsNum.U006, "Grant Application", "TEE Q4_3330");
			
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

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testCaseTemplateNG() throws Exception {
		try {
			
//			GeneralUtil.switchToFOOnly();			
//			GeneralUtil.loginAnyFO(foUser);
//			
//			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
//
//			
//			GeneralUtil.switchToPOOnly();
//			GeneralUtil.LoginAny(poOfficer1);			
//			
//			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[1]);
//			
//			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
//
//			
//			GeneralUtil.switchToPOOnly();
//			GeneralUtil.LoginAny(poStaff1);			
//			
//			
//			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[2]);			
//			
//			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
//
//			
//			GeneralUtil.switchToPOOnly();
//			GeneralUtil.LoginAny(poOfficer1);			
//			
//			
//			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[3]);
//			
//			Assert.assertTrue(SovUtils.techEdFy13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
//			
//			
//			GeneralUtil.switchToPOOnly();
//			GeneralUtil.LoginAny(poPAOfficer1);			
//			
//			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[4]);
//			
//			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance Grant Agreement Review");
//			
//			
//			GeneralUtil.switchToPOOnly();
//			GeneralUtil.LoginAny(poStaff4);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poStaff2);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: to Submit Cfo Approval ESign");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY14_SubmitGrantAwardESign(sov, "Approved"), "FAIL: to Submit Grant Award ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poStaff2);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Approved"), "FAIL: to Submit Cfo Grant Agreement ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poOfficer1);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[9]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantAgreementApproved(sov), "FAIL: to Submit Grant Agreement Approved");
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[10]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApprovedAward(sov), "FAIL: to Submit Approved Award");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poPAOfficer1);

			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[11]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceCreateSubmissionSchedule(sov), "FAIL: to Submit Finance Create Submission Schedule");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[12]);
			
			sov.setTmpCurrStepName(sov.getCurrStepName());
			
			sov.setFoProjAndSubProjName(sov.getFoProjName().concat(" - ").concat(sov.getSubProjectName()));
			
			sov.setCurrStepName(sov.getFoProjAndSubProjName());
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poStaff1);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[13]);
			
			sov.setProjAndSubName(sov.getProjectName().concat(" - ").concat(sov.getSubProjectName()));
			
			Assert.assertTrue(SovUtils.techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(sov, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poPAOfficer1);
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[14]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_Ready_StatusSubView, "Approved"), "FAIL: to Submit PA Finance Report Review");
			
			
			sov.setCurrStepName(ISovConst.sovTechEdStepsNames[15]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_Ready_StatusSubView), "FAIL: to Submit PA Tip Gap Payment");
			
		
	
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
