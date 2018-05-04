/**
 * 
 */
package test_Suite.tests.general;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.*;

import test_Suite.constants.ui.*;
import test_Suite.constants.workflow.*;
import test_Suite.constants.preTest.*;
import test_Suite.constants.cases.*;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.ProgramApprove;
import test_Suite.lib.workflow.ProgramSteps;
import test_Suite.utils.ui.*;
import test_Suite.utils.cases.*;
import test_Suite.utils.workflow.*;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class F_GnrlFndOppApprovalNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************

	private static Log log = LogFactory.getLog(F_GnrlFndOppApprovalNG.class);

	ProgramApprove papProgram;
	ProgramSteps progSteps;
	ArrayList<ProgramSteps> steps;
	ProgramSteps pSubmit;
	ProgramSteps pApprove;
	ProgramSteps pPOSubmit;

	Program fndOpp;

	String preFix = IGeneralConst.gnrl_ProgPrefix;

	String[] evals = { IPreTestConst.Groups[3][1][0] };
	String[][] stepNames = { { "PAP Submit", "PAP Submit", "PAP Submit" },
			{ "PAP PO Submit", "PAP PO Submit", "PAP PO Submit" },
			{ "PAP Approve", "PAP Approve", "PAP Approve" } };

	// --------------- End of Global Parameters
	// ---------------------------------

	@BeforeClass(groups = { "PapNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------

			FoppApprovalUtil.setPAPRequired("Yes");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "PapNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		try {	

			GeneralUtil.switchToPO();
			
			FoppApprovalUtil.setPAPRequired("No");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		} finally {	
			
			papProgram = null;
			progSteps = null;
			steps = null;
			pSubmit = null;
			pApprove = null;
			pPOSubmit = null;
			fndOpp = null;
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
			
			log.warn("Ending: " + this.getClass().getSimpleName());			
		}
	}


	@Test(groups = { "PapNG" })
	public void initialize() throws Exception {
		initializePAP();
		initializeFndOpp();
	}
	
	@Test(groups = { "PapNG" }, dependsOnMethods = "initialize")
	public void startfndOppApprovalProcess() throws Exception {

		Assert.assertTrue(FoppApprovalUtil.openFundingOppPlanner(fndOpp
				.getProgFullIdent(), "Inactive"));

		Assert.assertTrue(FoppApprovalUtil.changeProgramApprovalProcess(
				fndOpp.getProgFullIdent(), papProgram.getProgApproveId()));

		FoppApprovalUtil.submitFundingOppForm();

		FoppApprovalUtil.startFundingOppApproval(fndOpp.getProgFullIdent());
	}
	
	@Test(groups = { "PapNG" },dependsOnMethods="startfndOppApprovalProcess")
	public void assignOfficers() throws Exception {

		FoppApprovalUtil.assignProgramApprovalOfficers(fndOpp
				.getProgFullName(), papProgram.getProgApproveId(), papProgram
				.getProgApproveName(),
				new String[][] { { IPreTestConst.Groups[0][0][0],
						IPreTestConst.Groups[0][1][0] } });
		
	}
	
	@Test(groups = { "PapNG" },dependsOnMethods="assignOfficers")
	public void submitPOSS() throws Exception {

		FoppApprovalUtil.submitPOSSForm(fndOpp.getProgFullName(),
				papProgram.getProgApproveName());
		
	}
	
	@Test(groups = { "PapNG"},dependsOnMethods="submitPOSS")
	public void assignApproversAndApprove() throws Exception {

		Assert.assertTrue(FoppApprovalUtil.assignEvaluator(pApprove,
				fndOpp.getProgFullName(), evals));

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.loginApprover("1");

		Assert.assertTrue(FoppApprovalUtil.evaluatePAPSubmission(
				pApprove, fndOpp.getProgFullName(), "Ready", true));

		GeneralUtil.Logoff();
		GeneralUtil.logBack();
		GeneralUtil.logInSuper();
		
		ClicksUtil.clickLinkBySpanId(IClicksConst.menuPAP_MyInBasket_Span_Id, IClicksConst.openMyProjectSubmissionsLnk);
		
		ClicksUtil.clickButtons(IClicksConst.filterBtn);	
	
	}
	
	private void initializePAP() throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openProgramApprovalProcess);
		
		papProgram = new ProgramApprove();
		papProgram.setProgApprovePrefix(IProgramsConst.pap_ProgPrefix);
		papProgram.setProgApprovePostfix(IProgramsConst.pap_ProgPostfix);
		papProgram.setProgApproveFullId(IProgramsConst.pap_ProgPrefix
				+ IProgramsConst.pap_ProgIdentifier
				+ IProgramsConst.pap_ProgPostfix);
		papProgram.setProgApproveBaseLetter(ProgramUtil.getUsedBaseLetter(papProgram.getProgApproveFullId()));

		papProgram.setProgApproveProjPrefix(IProgramsConst.pap_ProjPrefix);

		papProgram.setProgApproveId(papProgram.getProgApproveBaseLetter()
				+ papProgram.getProgApproveFullId());

		papProgram.setProgApproveName(papProgram.getProgApproveBaseLetter()
				+ IProgramsConst.pap_ProgPrefix
				+ IProgramsConst.pap_Prog_LocalizedName
				+ IProgramsConst.pap_ProgPostfix);
		
		papProgram.setProgApproveName(papProgram.getProgApproveName().replace('-', ' '));

		papProgram.setProgApproveSteps(initializeSteps(papProgram
				.getProgApproveId(), papProgram.getProgApproveName()));

	}

	private void initializeFndOpp() throws Exception {
		fndOpp = new Program();

		fndOpp.setProgPreFix(preFix);
		fndOpp.setProgPostfix("");
		fndOpp.setProgPortal('P');
		fndOpp.setProgForm(false);
		fndOpp.setPublicationForm(false);
		fndOpp.setNewProgram(false);
		fndOpp.setProgName(fndOpp.getProgPreFix()
				+ IGeneralConst.gnrl_FndOppName);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		
		fndOpp.setProgLetter(FoppApprovalUtil
				.getUsedFndOppBaseLetter(fndOpp.getProgName()));
		
		fndOpp.setProgFullIdent(fndOpp.getProgLetter() + fndOpp.getProgName());
		
		fndOpp.setProgFullName(fndOpp.getProgFullIdent().replace('-', ' '));
	}

	private ArrayList<ProgramSteps> initializeSteps(String pId, String progName) {

		steps = new ArrayList<ProgramSteps>();

		// Set Submit step
		pSubmit = new ProgramSteps();
		pSubmit.setProgId(pId);
		pSubmit.setProgName(progName);
		pSubmit.setBaseLetter(papProgram.getProgApproveBaseLetter());
		pSubmit.setStepId(pSubmit.getBaseLetter() + "-"
				+ "Funding Opp Submission");
		pSubmit.setStepProjOfficerGroup(IPreTestConst.AppGroupName);
		pSubmit.setStepIsCritical(true);
		pSubmit.setStepReExecute("No");
		pSubmit.setStepForm("Funding Opp Sub");
		pSubmit.setStepNames(stepNames[0]);
		steps.add(pSubmit);

		// Set Program Office Submit step
		pPOSubmit = new ProgramSteps();
		pPOSubmit.setProgId(pId);
		pPOSubmit.setProgName(progName);
		pPOSubmit.setBaseLetter(papProgram.getProgApproveBaseLetter());
		pPOSubmit.setStepId(pPOSubmit.getBaseLetter() + "-"
				+ "Funding Opp PO Submission");
		pPOSubmit.setStepProjOfficerGroup(IPreTestConst.AppGroupName);
		pPOSubmit.setStepIsCritical(true);
		pPOSubmit.setStepReExecute("No");
		pPOSubmit.setStepForm("Program Office Sub");
		pPOSubmit.setStepNames(stepNames[1]);
		steps.add(pPOSubmit);

		// Set Approve step
		pApprove = new ProgramSteps();
		pApprove.setProgId(pId);
		pApprove.setProgName(progName);
		pApprove.setBaseLetter(papProgram.getProgApproveBaseLetter());
		pApprove.setStepId(pApprove.getBaseLetter() + "-"
				+ "Funding Opp Approval");
		pApprove.setStepProjOfficerGroup(IPreTestConst.AppGroupName);
		pApprove.setStepForm("Funding Opp Approval");
		pApprove.setStepAutoAssign(false);
		pApprove.setStepNames(stepNames[2]);
		steps.add(pApprove);
		return steps;
	}

}
