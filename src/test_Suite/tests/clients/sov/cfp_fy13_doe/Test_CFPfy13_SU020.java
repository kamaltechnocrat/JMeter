/**
 * 
 */
package test_Suite.tests.clients.sov.cfp_fy13_doe;

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
public class Test_CFPfy13_SU020 {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	Sov sov;
	
	String foUser = "jaynichols";
	
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
			GeneralUtil.navigateToFO();
			GeneralUtil.loginAnyFO(foUser);
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

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "SovRegTest" })
	public void testCaseTemplateNG() throws Exception {
		try {
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApplication(sov), "FAIL: Could not Submit CFP Application");
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[1]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperApplicationESign(sov), "FAIL: Could not Submit Super Application ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poOfficer1);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[2]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitInitialReview(sov, "Approved"), "FAIL: to Submit initial Review");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[4]);			
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPOApproval(sov, "Approved"), "FAIL: to Submit Program Officer Approval");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[5]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementCreated(sov), "FAIL: to Submit Grant Agreement Created");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poPAOfficer1);			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[6]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceGrantAgreementReview(sov, "Approved"), "FAIL: to Submit Finance GrantAgreement Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poStaff1);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[7]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitDirectorGrantReview(sov, "Approved"), "FAIL: to Submit Director Grant Review");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poStaff2);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[8]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoApprovalESign(sov, "Approved"), "FAIL: to Submit Cfo Approval ESign");
			
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[9]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitSuperGrantAgreementESign(sov), "FAIL: to Submit Super Grant Agreement ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poStaff2);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[10]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitCfoGrantAgreementESign(sov, "Approved"), "FAIL: to Submit Cfo Grant Agreement ESign");
			
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poOfficer1);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[11]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitGrantAgreementApproved(sov), "FAIL: to Submit Grant Agreement Approved");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[12]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitApprovedAward(sov), "FAIL: to Submit Approved Award");
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poPAOfficer1);

			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[13]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitFinanceCreateSubmissionSchedule(sov), "FAIL: to Submit Finance Create Submission Schedule");

			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO(foUser);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[14]);
			
			sov.setTmpCurrStepName(sov.getCurrStepName());
			
			sov.setFoProjAndSubProjName(sov.getFoProjName().concat(" - ").concat(sov.getSubProjectName()));
			
			sov.setCurrStepName(sov.getFoProjAndSubProjName());
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAQuarterlyFinancialReport(sov), "FAIL: to Submit PA Quarterly Financial Report");
			

			GeneralUtil.switchToPOOnly();
			GeneralUtil.LoginAny(poPAOfficer1);
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[15]);
			
			sov.setProjAndSubName(sov.getProjectName().concat(" - ").concat(sov.getSubProjectName()));
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPAFinanceReportReview(sov,IFiltersConst.submissionsStatus_Ready_StatusSubView, "Approved"), "FAIL: to Submit PA Finance Report Review");
			
			
			sov.setCurrStepName(ISovConst.sovCfpFy13StepsNames[16]);
			
			Assert.assertTrue(SovUtils.cfpFY13_SubmitPATipGapPayment(sov,IFiltersConst.submissionsStatus_Ready_StatusSubView), "FAIL: to Submit PA Tip Gap Payment");
		
	
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

}
