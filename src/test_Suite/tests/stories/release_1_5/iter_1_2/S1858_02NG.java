/*
 * Test Case _02 for Story 1858 Deactivate user accounts
 * 
 * Steps:
 *  1. Create 2 Evaluators and Associate them to a Group (if they do not exists already)
 *  2. Deactivate Eval_1 from the List
 *  3. Create Program (Submission, Review, Approval)
 *  Test:
 *  	find Eval_1 in the Available List (Step Staff)
 *  
 *  Result Expected:
 *  	Not Available
 *  
 *  4. Configure the Group as Step Staff for Review with Auto Assign
 *  5. Submit a Project.
 *  6. Login as Eval_2 and Approve
 *  7. Change Approval Step to Manual Assignment
 *  8. Submit New Project
 *  9.Assign Officers
 *  Test:
 *   find Eval_1 in the Available List (Assign Evaluator)
 *  a. 
 *  
 */

package test_Suite.tests.stories.release_1_5.iter_1_2;

import test_Suite.constants.users.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.*;

import test_Suite.lib.users.*;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.Project;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.*;
import test_Suite.constants.cases.*;
import test_Suite.constants.workflow.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.users.UsersAndGroupsUtil;
import test_Suite.utils.workflow.ProgStepUtil;
import test_Suite.utils.workflow.ProgramUtil;
import test_Suite.utils.workflow.ProjectUtil;
import test_Suite.utils.cases.*;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class S1858_02NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	private int UserIndex = 16;	
	
	Integer userBeat = 2;
	
	Users user;

	boolean[] uapsRights = IUAPConst.allRightsTrue;
	
	UAPs uaps;
	
	Program prog;	
	
	Project proj;
	
	ArrayList<String[]> uapList;
	
    boolean newProgram = true;
	boolean programForm = false;
	boolean publicationForm = false;	
	boolean newOrg = true;
	
	String preFix = IGeneralConst.gnrl_ProgPrefix;	
	char portaltype = 'P';
	
    String progAdmin[] = {IPreTestConst.adminGroup};
    String progOfficers[] = {IPreTestConst.Groups[0][0][0]};
    String reviewersGrp[] = {IUsersConst.groups[UserIndex][0][0]};
    String approversGrp[] = {IUsersConst.groups[UserIndex][0][0]};
    
    String postFix = "-S1858-02";
    
    //Steps
    String submissionStep[][] = IGeneralConst.gnrl_SubmissionA;
    String reviewStep[][] = IGeneralConst.reviewQuoCritManu;
    String approvStep[][] = IGeneralConst.approvQuoCritAuto;
    
    //Part Object to Find
    String reviewInBrackets = " (Review)";
    
    String tempStepName = "";
    
    int tabIndex;
    
    boolean retValue;
	

	// ------------- End of Global Vars
	// -----------------------------------------

	@BeforeClass(groups = {"UsersNG" })
	public void setUp() {
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			//---------------------------
			
			initializeUserAndUAPs(uapsRights);
			
			initializeProgram();
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = {"UsersNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		user.taggleUserStatusInAccnt(userBeat.toString(), true);
		
		user = null;
		uaps = null;
		uapList = null;
		proj = null;
		prog = null; 
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"UsersNG" })
	public void createUserAndGroup() throws Exception {
		try {
			
			Assert.assertTrue(user.createUser(), "Fail: To Create a User");

			log.debug("Start Creating Group!");
			Assert.assertTrue(user.createGroup(), "Fail: to Create a Group");		

			Assert.assertTrue(user.addUsersToGroup(),"Fail: Adding Users to Group");
			
			UsersAndGroupsUtil.addAccessRightsUAPNew(uapList, uapsRights);
			
			Assert.assertTrue(user.taggleUserStatusInAccnt(userBeat.toString(), false),"FAIL: Unable to De-Activate User");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
				
	}
	
	@Test(groups = {"UsersNG" }, dependsOnMethods="createUserAndGroup")
	public void createProgram() throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		
		prog.setProgPostfix(postFix);		
        
        if(newProgram) {
        	
        	prog.setProgAdmin(progAdmin);
        	
            prog.setProgOfficers(progOfficers);        
            
            ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);            
            
            prog.createProgram();
            
            //Setting the Steps End Date 5 Days from Today
            
            prog.setEndDate(GeneralUtil.setDayofMonth(5));
            
            prog.addStep(submissionStep);            
            prog.manageStep(new String[][] {{IPreTestConst.Groups[0][0][0]},{submissionStep[0][0]}});

    		prog.addStep(reviewStep);
    		prog.manageStep(new String[][] { reviewersGrp,
    				{ reviewStep[0][0] } });
    		
    		tempStepName = prog.getFullStepIdent();

    		prog.addStep(approvStep);
    		prog.manageStep(new String[][] {approversGrp,
    				{ approvStep[0][0] } });
            
            prog.activateProgram("Active");
        }

	}
	
	@Test(groups = {"UsersNG" }, dependsOnMethods="createProgram")
	public void testInStepStaff() throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		
		ClicksUtil.clickLinks(prog.getProgFullIdent());
		
		ClicksUtil.clickLinks(prog.getProgFullIdent());		
		
		log.warn(tempStepName + reviewInBrackets);
		
		ProgramUtil.openStepManagmentDetails(IProgramsConst.EProjectType.pre_Award.ordinal(),
				tempStepName + reviewInBrackets, IProgramsConst.EstepManagment.stepStaff.ordinal());
		
		Assert.assertFalse(ProgStepUtil.isUserInList(user.getArrUser()[0][0] + userBeat.toString()), "Fail: User Should Not be available in Step Staff List");
		
	}
	
	
	@Test(groups = {"UsersNG" }, dependsOnMethods="createProgram")
	public void submitPOProject() throws Exception {
	
		proj = new Project(prog, true);
		
		proj.createPOProject(newOrg);

		proj.submitProject(true);
		
		proj.assignOfficers(new String[][] {
				{ IPreTestConst.Groups[0][0][0],
						IPreTestConst.Groups[0][1][0]}});
		
		proj.setCurrentStepName(tempStepName.replace("-", " "));
		
	}
	
	
	@Test(groups = {"UsersNG" }, dependsOnMethods="submitPOProject")
	public void testInAssignEvaluators() throws Exception {
		
		try {
			
			Assert.assertFalse(ProjectUtil.isEvaluatorsExist(proj, IProgramsConst.EProjectType.pre_Award.ordinal(),user.getArrUser()[0][1] + userBeat.toString(), 0), "FAIL: Inactive Users should not be available");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

		} 
	}
	
	private void initializeUserAndUAPs(boolean[] rights) throws Exception {
		
		user = new Users(userBeat, IUsersConst.users[UserIndex], "Users", "Program Office Users");
		
		uaps = new UAPs();
		
		uapList = new ArrayList<String[]>();
		
		uapList.add(0, IUAPConst.manageProjectEvaluationsUAP_1st);
		uapList.add(1, IUAPConst.manageProjectEvaluationsUAP_Sub1st);
		
		uapsRights = rights;		
	}
	
	private void initializeProgram() throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		
		prog = new Program();
		
		prog.setNewProgram(newProgram);
		
		prog.setProgPreFix(IGeneralConst.gnrl_ProgPrefix);
		
		prog.setProgPortal('P');
		
		prog.setProgIdent(prog.getProgPreFix() + IGeneralConst.gnrl_ProgName);
		
		prog.setProgPostfix( "-S1858-02");
		
		prog.setProgForm(programForm);
		
		prog.setPublicationForm(publicationForm);
		
		prog.initProgram();
		
		prog.setStepOfficer(progOfficers[0]);
		
		prog.setProgAdmin(progAdmin);    	
		
        prog.setProgOfficers(progOfficers);
	}
}

