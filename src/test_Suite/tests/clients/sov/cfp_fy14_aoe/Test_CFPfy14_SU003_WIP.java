/**
 * 
 */
package test_Suite.tests.clients.sov.cfp_fy14_aoe;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.clients.ISovConst;
import test_Suite.constants.clients.ISovConst.ESovCfpFy14ApplicantsNum;
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
public class Test_CFPfy14_SU003_WIP {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Sov sov;
	
	String foUser = "GailConley";
	
	String poOfficer1 = "Ed.Haggett";
	
	String poOfficer2 = "Liz.Rand";
	
	String poPAOfficer1 = "John.Leu";
	
	String poPAOfficer2 = "Julie.Robinson";
	
	String poStaff1 = "Deb.Quackenbush";
	
	String poStaff2 = "Bill.Talbott";
	
	String poStaff3 = "Doeadmin2";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "SovRegTest" })
	public void setUp() throws Exception {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.LoginAny(poOfficer1);
			// -----------------------------------
			
			sov = SovUtils.cfpFy14_Initialization(ESovFOPPs.CFPFY14AOE, ESovCfpFy14ApplicantsNum.SU00333, "CFP Application", "Title I - IN_4250");
			
			sov.setFoProjName(sov.getProjectName());
			
			sov.setFoProjAndSubProjName(sov.getFoProjName());
			
			sov.setApplicantNumber("SU003");

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
//			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[1]);
//			
//			Assert.assertTrue(SovUtils.cfpFY14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit Grant Application ESign");
//
//			
//			GeneralUtil.switchToPOOnly();
//			GeneralUtil.LoginAny(poOfficer1);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poPAOfficer1);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poStaff1);
			
			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poStaff2);
			
			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: to Submit Cfo Approval ESign");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[9]);
			
			Assert.assertTrue(SovUtils.cfpFY14_SubmitGrantAwardESign(sov, "Approved"), "FAIL: to Submit Super Grant Agreement ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poStaff2);
			
			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[10]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Approved"), "FAIL: to Submit Cfo Grant Agreement ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poOfficer1);
			
			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[11]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementApproved(sov), "FAIL: to Submit Grant Agreement Approved");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[12]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApprovedAward(sov), "FAIL: to Submit Approved Award");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poPAOfficer1);

			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[13]);
			
			Assert.assertTrue(SovUtils.cfpFY14_SubmitFinanceCreateSubmissionSchedule(sov), "FAIL: to Submit Finance Create Submission Schedule");

			/*
			 * This step Already been Submitted and the Token is on the next step
			 * 
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[14]);
			
			sov.setTmpCurrStepName(sov.getCurrStepName());
			
			sov.setFoProjAndSubProjName(sov.getFoProjName().concat(" - ").concat(sov.getSubProjectName()));
			
			sov.setCurrStepName(sov.getFoProjAndSubProjName());
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poPAOfficer1);
			
			*/
			
			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[15]);
			
			sov.setProjAndSubName(sov.getProjectName().concat(" - ").concat(sov.getSubProjectName()));
			
			Assert.assertTrue(SovUtils.cfpFY14_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_Ready_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy14StepsNames[16]);
			
			sov.setProjAndSubName(sov.getProjectName().concat(" - ").concat(sov.getSubProjectName()));
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_Ready_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
		
	
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
