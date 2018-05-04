/**
 * 
 */
package test_Suite.tests.r3_0;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.eForms.ILBFunctionConst.EeFormsIdentifier;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IProgramsConst.EProjectType;
import test_Suite.lib.eForms.LBF;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.LBFunctionUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.users.ApplicantsUtil;
import test_Suite.utils.users.UsersAndGroupsUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class SSmustRunFirst {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	LBF  lbf;
	
	FundingOpportunity fop;
	
	Users user;
	
	Integer usrBeat = 2;
	
	String applicant = "Ouia ".concat(usrBeat.toString());
	
	String regist = "front".concat(usrBeat.toString());
	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			user = new Users(usrBeat, IPreTestConst.MultiUsers[15],"Front Office Users");
			
			user.setUsrName(regist);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun=true)
	public void tearDown() {
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}	

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EFormsNG" })
	public void configureUserProfileNG() throws Exception {
		try {			

			
			lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
					EProjectType.pre_Award, 2, 0, EeFormsIdentifier.userProfile);
			
			GeneralUtil.setApplicantWorkspace("LBF-Applicant Profile-Source-eLists");
			
			GeneralUtil.setUserProfile("User-Profile-Source-eLists", true);
			
			Assert.assertTrue(UsersAndGroupsUtil.openUserProfile(user), "FAIL: Could not open User Profile!");			
			
			LBFunctionUtil.insertDataTo_ELists(lbf);
			
			ClicksUtil.clickLinks(IClicksConst.backToUsersListLnk);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EFormsNG" }, dependsOnMethods="configureUserProfileNG")
	public void configureAdminFormNG() throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		
		lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
				EProjectType.pre_Award, 1, 0, EeFormsIdentifier.userProfile);
		
		fop = new FundingOpportunity("A","-ReExecution-PA-","");
		
		fillAdminForm(fop,lbf);
		
		//-----------------------------------------------
		
		lbf = new LBF(ILBFunctionConst.ESyncTypes.equal.ordinal(),
				EProjectType.pre_Award, 2, 0, EeFormsIdentifier.userProfile);
		
		//---------------------------------------------		
		fop = new FundingOpportunity("A","-3-State-PA-","");
		
		fillAdminForm(fop,lbf);
		
		//--------------------------------------------------		
		fop = new FundingOpportunity("A","-eLists-PA-","");
		
		fillAdminForm(fop,lbf);
		
		//--------------------------------------------------		
		fop = new FundingOpportunity("A","-ExpandedCtrl-PA-","");
		
		fillAdminForm(fop,lbf);
		
		//--------------------------------------------------		
		fop = new FundingOpportunity("A","-StepAmendment-PA-","");
		
		fillAdminForm(fop,lbf);	
		
		//--------------------------------------------------		
		fop = new FundingOpportunity("A","-Allow-MultiProj-PA-","");
		
		fillAdminForm(fop,lbf);	
		
		//--------------------------------------------------		
		fop = new FundingOpportunity("A","-Disallow-MultiProj-PA-","");
		
		fillAdminForm(fop,lbf);	
		
		//--------------------------------------------------		
		fop = new FundingOpportunity("A","-Warn-MultiProj-PA-","");
		
		fillAdminForm(fop,lbf);	
		
		GeneralUtil.switchToFOOnly();
		GeneralUtil.loginAnyFO(regist);	
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EFormsNG" }, dependsOnMethods={"configureAdminFormNG"})	
	public void configureApplicantProfile() throws Exception {
		
		try {
			
			Assert.assertTrue(ApplicantsUtil.openFOApplicantProfile("Ouia 2"), "FAIL: could not open Applicant profile!");
			
			
			//Use this only if User Profile is not used any more!
			
			//LBFunctionUtil.insertDataTo_ELists(lbf);
			
			ClicksUtil.clickLinks("/Summary/");
			
			if(ClicksUtil.clickEditAndReturn())
			{				
				Assert.assertTrue(ApplicantsUtil.openFOApplicantProfile("Ouia 2"), "FAIL: could not open Applicant profile!");
				
				ClicksUtil.clickLinks("/Summary/");
			}
			
			Assert.assertTrue(ClicksUtil.submitOrComplete(), "FAIL: Could not complete Profile");
			
			ClicksUtil.returnFromAnyForm();
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "EFormsNG" }, dependsOnMethods={"configureAdminFormNG"})
	public void registerToFOPP() throws Exception {
		try {

			ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);
			
			//---------------------------------------------			
			fop = new FundingOpportunity("A","-3-State-PA-","");
			
			registerToFundingOpps(fop);
			
			//--------------------------------------------------			
			fop = new FundingOpportunity("A","-eLists-PA-","");
			
			registerToFundingOpps(fop);
			
			//--------------------------------------------------			
			fop = new FundingOpportunity("A","-ExpandedCtrl-PA-","");
			
			registerToFundingOpps(fop);
			
			//--------------------------------------------------
			fop = new FundingOpportunity("A","-ReExecution-PA-","");
			
			registerToFundingOpps(fop);
			
			//--------------------------------------------------			
			fop = new FundingOpportunity("A","-StepAmendment-PA-","");
			
			registerToFundingOpps(fop);
			
			//--------------------------------------------------		
			fop = new FundingOpportunity("A","-Allow-MultiProj-PA-","");
			
			registerToFundingOpps(fop);	
			
			//--------------------------------------------------		
			fop = new FundingOpportunity("A","-Disallow-MultiProj-PA-","");
			
			registerToFundingOpps(fop);	
			
			//--------------------------------------------------		
			fop = new FundingOpportunity("A","-Warn-MultiProj-PA-","");
			
			registerToFundingOpps(fop);	
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}	
	
	
	
	private void fillAdminForm(FundingOpportunity fopp, LBF lbf) throws Exception {

		Assert.assertTrue(fopp.openFundingOppPlanner(), "FAIL: Could not open FOPP Planner!");
		
		Assert.assertTrue(fopp.openAdminEForm(), "FAIL: Could not open Admin e.Form from Planner!");
		
		LBFunctionUtil.insertDataTo_ELists(lbf);
		
		ClicksUtil.clickLinks(IClicksConst.backToFundingOppPlannerLnk);
		
		ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);
		
	}
	
	private void registerToFundingOpps(FundingOpportunity fopp) throws Exception {

		Assert.assertTrue(FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), applicant),
				"FAIL: Could not Register to Funding Opp.!");
		
	}
}
