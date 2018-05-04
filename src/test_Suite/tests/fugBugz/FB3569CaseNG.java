/*
 * Case 3569 -- PO, Openning Submission List after award amendment causes 
 * this is in both 2.0 and in 1.5 including the up coming release 1.5.2
 *Steps:
 *Depends on PreTest02NG
 *submit award with 2 items in submission schedule list (PAS1, PAS2)
 *open PAS2, do not submit
 *amend award, delete PAS2
 *submit award
 *go to Applicants --> click on view Submission List Icon
 *result: exception thrown.
 */

package test_Suite.tests.fugBugz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.*;

import test_Suite.constants.ui.*;
import test_Suite.constants.preTest.*;
import test_Suite.constants.cases.*;
import test_Suite.lib.workflow.*;
import test_Suite.utils.cases.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.workflow.AmendmentsUtil;

public class FB3569CaseNG {
private static Log log = LogFactory.getLog(FB3569CaseNG.class);
Project proj;
Program prog;
	

	@BeforeClass  
	public void setUp() {    
		// code that will be invoked when this test is instantiated  
	} 
	
	@Test(groups = { "FBCases" })
    public void fB3569CaseNG() throws Exception {
		
		try{
			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();

			//#####***********************************************************************
			//###   To set up the Global Params Name Vars
			//#####***********************************************************************
			
			 String paAwardStep[][] = IGeneralConst.pa_AwardCrit;
			 
			boolean newProgram = false;
			boolean programForm = false;

			boolean newOrg = true;

			String preFix = IGeneralConst.pa_ProgPrefix;

			String postFix = "";

			char portaltype = 'P';
			prog = new Program(preFix, portaltype, programForm,
					newProgram, false);

			prog.setProgPostfix(postFix);

			//#####*******************************************
			//###       Initialize a Program
			//#####*******************************************

			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

			prog.initProgram();

			/*******************************************
			 ###       Create a Project
			 *******************************************/
			
			proj = new Project(prog, true);

			proj.createPOProject(newOrg);

			proj.submitProject(true);

			proj.assignOfficers(new String[][] {
					{ IPreTestConst.Groups[0][0][0],
							IPreTestConst.Groups[0][1][0] },
					{ IPreTestConst.Groups[6][0][0],
							IPreTestConst.Groups[6][1][0] } });

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.loginApprover("1");

			proj.approveSubmission(IGeneralConst.approvQuoCritAuto[0][0], true,
					"Ready", false,false);

			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.logInSuper();

			proj.submitAward("Standard", 2, true);
			
			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.loginProjOfficer("1");

			proj.setClaimNumber(1);

			proj.submitFromApplicantSubList(IGeneralConst.initialClaim[0][0],
					false);
			
			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.logInSuper();
			
			String dd = GeneralUtil.setDayofMonth(3);
			
			if (AmendmentsUtil.issueAmendment(new String[] {proj.getProjFullName(), paAwardStep[0][0], IPreTestConst.Groups[0][1][0],dd , "Test Re-Execute on Previous Amendment", "This a Comment"}, proj))
			{
				proj.removeClaimEntry(paAwardStep[0][0], true);
				
				GeneralUtil.Logoff();
				GeneralUtil.logBack();
				GeneralUtil.loginProjOfficer("1");
				
				proj.setClaimNumber(2);
				
				proj.submitFromApplicantSubList(IGeneralConst.initialClaim[0][0],false);
			}
			
			

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		} finally {
			proj = null;
			prog = null;
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		}
	}

}
