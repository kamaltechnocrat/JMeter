/**
 * 
 */
package test_Suite.tests.clients.sov.nord_fy14_aoe;

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
public class Test_Nordfy14_SU060 {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Sov sov;
	
	String foUser = "KarenGallese";
	
	String poOfficer1 = "Ed.Haggett";
	
	String poOfficer2 = "Liz.Rand";
	
	String poPAOfficer1 = "John.Leu";
	
	String poPAOfficer2 = "Julie.Robinson";
	
	String poStaff1 = "Deb.Quackenbush";
	
	String poStaff2 = "Bill.Talbott";
	
	String poStaff3 = "Doeadmin2";
	
	String poStaff4 = "David.Baroudi";
	
	String poStaff5 = "Sean.Cousino";

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "SovRegTest" })
	public void setUp() throws Exception {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.LoginAny(poStaff2);
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
//			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[1]);
//			
//			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantApplicationESign(sov, "Approved"), "FAIL: Could not Submit CFP Application");
//
//			
//			GeneralUtil.switchToPOOnly();
//			GeneralUtil.LoginAny(poOfficer1);			
//			
//			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[2]);
//			
//			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
//			
//			
//			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[4]);			
//			
//			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
//			
//			
//			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[5]);
//			
//			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
//			
//			
//			GeneralUtil.switchToPOOnly();
//			GeneralUtil.LoginAny(poPAOfficer1);			
//			
//			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[6]);
//			
//			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
//			
//			
//			GeneralUtil.switchToPOOnly();
//			GeneralUtil.LoginAny(poStaff1);
//			
//			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[7]);
//			
//			Assert.assertTrue(SovUtils.nordFY14_SubmitGrantAwardESign(sov, "Approved"), "FAIL: to Submit Director Grant Review");
//			
//			
//			GeneralUtil.switchToPOOnly();
//			GeneralUtil.LoginAny(poStaff2);
//			
//			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[8]);
//			
//			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: to Submit Cfo Approval ESign");
//			
//			
//			GeneralUtil.switchToFOOnly();
//			GeneralUtil.loginAnyFO(foUser);
//			
//			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[9]);
//			
//			Assert.assertTrue(SovUtils.cfpFY14_SubmitGrantAwardESign(sov, "Approved"), "FAIL: to Submit Super Grant Agreement ESign");
//			
//			
//			GeneralUtil.switchToPOOnly();
//			GeneralUtil.LoginAny(poStaff2);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[10]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Approved"), "FAIL: to Submit Cfo Grant Agreement ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poOfficer1);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[11]);
			
			Assert.assertTrue(SovUtils.nordFy14_SubmitGrantAgreementApproved(sov), "FAIL: to Submit Grant Agreement Approved");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[12]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApprovedAward(sov), "FAIL: to Submit Approved Award");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poPAOfficer1);

			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[13]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceCreateSubmissionSchedule(sov), "FAIL: to Submit Finance Create Submission Schedule");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[14]);
			
			sov.setTmpCurrStepName(sov.getCurrStepName());
			
			sov.setFoProjAndSubProjName(sov.getFoProjName().concat(" - ").concat(sov.getSubProjectName()));
			
			sov.setCurrStepName(sov.getFoProjAndSubProjName());
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poPAOfficer1);
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[15]);
			
			sov.setProjAndSubName(sov.getProjectName().concat(" - ").concat(sov.getSubProjectName()));
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_Ready_StatusSubView, "Approved"), "FAIL: to Submit PA Quarterly Financial Report");
			
			
			sov.setCurrStepName(ISovConst.sovNordFy14StepsNames[16]);
			
			sov.setProjAndSubName(sov.getProjectName().concat(" - ").concat(sov.getSubProjectName()));
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_Ready_StatusSubView), "FAIL: to Submit PA Quarterly Financial Report");
			
		
	
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
