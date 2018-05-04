/**
 * 
 */
package test_Suite.tests.general;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.*;

import test_Suite.constants.ui.*;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.preTest.*;
import test_Suite.constants.workflow.*;
import test_Suite.constants.cases.*;
import test_Suite.lib.workflow.*;
import test_Suite.utils.cases.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.ui.HtmlFormsUtil.ECreateUpdate;
import test_Suite.utils.workflow.ProgStepUtil;
import test_Suite.utils.workflow.ProgramUtil;

/**
 * @author mshakshouki
 * 
 */
@GUITest
@SuppressWarnings("rawtypes")
@Test(singleThreaded = true)
public class D_GnrlPAPNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	ProgramApprove papProgram;

	ProgramSteps progSteps;

	ArrayList<ProgramSteps> steps;

	ProgramSteps pSubmit;

	ProgramSteps pApprove;

	ProgramSteps pPOSubmit;

	ProgramSteps pClosingPOSS;

	private String[] programStaff = { IPreTestConst.Groups[6][0][0],
			IPreTestConst.AppGroupName };

	boolean repeatNotifyPO[] = INotificationsConst.stepAllCheckBoxValues[2];

	static String[][] stepNames = {
		{ "PAP Submit", "PAP Submit", "PAP Submit" },
		{ "PAP PO Submit", "PAP PO Submit", "PAP PO Submit" },
		{ "PAP Approve", "PAP Approve", "PAP Approve" },
		{ "PAP Closing POSS", "PAP Closing POSS", "PAP Closing POSS" }

	};

	// private String stepStaffSearch = "staff";

	private enum progType {
		typePAP, typeG3
	}

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


		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "PapNG" }, alwaysRun=true)
	public void tearDown() {

		papProgram = null;
		progSteps = null;
		steps = null;
		pSubmit = null;
		pApprove = null;
		pPOSubmit = null;
		pClosingPOSS = null;


		GeneralUtil.Logoff();
		IEUtil.closeBrowser();

		log.warn("Ending: " + this.getClass().getSimpleName());
	}


	@Test(groups = { "PapNG" })
	public void initialize() throws Exception {
		initializePAP();

	}

	@Test(groups = { "PapNG" }, dependsOnMethods = "initialize")
	public void createPAP() throws Exception {

		Assert.assertTrue(ProgramUtil.createNewPAP(papProgram),
				"FAIL: Unable to Create PAP Program!");

		Assert.assertTrue(ProgramUtil.selectingProgPlannerOfficers(papProgram
				.getProgApproveAdmin(), 0), "FAIL: Unable to select Program Approval Administrators!");

		Assert.assertTrue(ProgramUtil.selectingProgPlannerOfficers(programStaff, 1),
				"FAIL: Unable to select Program Staff!"); 
	}

	@Test(groups = { "PapNG" },dependsOnMethods="createPAP")
	public void addingSteps() throws Exception {

		ProgramUtil.openStepOrNotificationDetails(ProgramUtil
				.findObjectInProgPlanner("Steps",
						IProgramsConst.EProjectType.pre_Award.ordinal()));
		Assert.assertTrue(ProgStepUtil.addStep(pSubmit));

		ProgramUtil.openStepOrNotificationDetails(ProgramUtil
				.findObjectInProgPlanner("Steps",
						IProgramsConst.EProjectType.pre_Award.ordinal()));

		Assert.assertTrue(ProgStepUtil.addStep(pPOSubmit));

		ProgramUtil.openNotificationDetails(
				IProgramsConst.EProjectType.pre_Award.ordinal(), pPOSubmit
				.getStepFullNameInPlanner());
	}

	@Test(groups = { "PapNG" },dependsOnMethods="addingSteps")
	public void addNotifToPOSS() throws Exception {		

		Assert.assertTrue(ProgStepUtil.addNewNotification(pPOSubmit.getStepId(),
				INotificationsConst.stepExitValues, repeatNotifyPO,
				programStaff, "0", "1"));

	}

	@Test(groups = { "PapNG" },dependsOnMethods="addNotifToPOSS")
	public void addApprovalAndStepNotif() throws Exception {

		ProgramUtil.openStepOrNotificationDetails(ProgramUtil
				.findObjectInProgPlanner("Steps",
						IProgramsConst.EProjectType.pre_Award.ordinal()));

		Assert.assertTrue(ProgStepUtil.addStep(pApprove));

		ProgramUtil.openNotificationDetails(
				IProgramsConst.EProjectType.pre_Award.ordinal(), pApprove
				.getStepFullNameInPlanner());

		Assert.assertTrue(ProgStepUtil.addNewNotification(pApprove.getStepId(),
				INotificationsConst.stepEvaluatorsNeededValues, repeatNotifyPO,
				new String[] { IPreTestConst.Groups[3][0][0] }, "0", "1"));

	}

	@Test(groups = { "PapNG" },dependsOnMethods="addApprovalAndStepNotif")
	public void addClosingStepAndActivate() throws Exception {

		ProgramUtil.openStepOrNotificationDetails(ProgramUtil
				.findObjectInProgPlanner("Steps",
						IProgramsConst.EProjectType.pre_Award.ordinal()));

		Assert.assertTrue(ProgStepUtil.addStep(pClosingPOSS));

		Assert.assertTrue(ProgramUtil.activatingProgram(papProgram, "Active"));

	}

	private void initializePAP() throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openProgramApprovalProcess);
		papProgram = new ProgramApprove();
		papProgram.setProgApprovePrefix(IProgramsConst.pap_ProgPrefix);
		papProgram.setProgApprovePostfix(IProgramsConst.pap_ProgPostfix);
		papProgram.setProgApproveFullId(IProgramsConst.pap_ProgPrefix
				+ IProgramsConst.pap_ProgIdentifier
				+ IProgramsConst.pap_ProgPostfix);
		papProgram.setProgApproveBaseLetter(ProgramUtil.getNewBaseLetter(papProgram.getProgApproveFullId()));

		papProgram.setProgApproveProjPrefix(IProgramsConst.pap_ProjPrefix);

		papProgram.setProgApproveId(papProgram.getProgApproveBaseLetter()
				+ papProgram.getProgApproveFullId());
		papProgram.setProgApproveStartDate(GeneralUtil.getTodayDate());
		papProgram.setProgApproveEndDate(GeneralUtil.getNextYear());
		papProgram.setProgApproveForm("Basic Program Admin");
		papProgram.setProgApproveOfficer(IPreTestConst.ProgPOfficer);
		papProgram.setProgApproveStaff(setStaff());
		papProgram.setProgApproveStatus(IGeneralConst.statusInactive);
		papProgram.setProgApprovePrOrg(IGeneralConst.primG3_OrgRoot);
		papProgram.setProgApproveOrgAccess(IGeneralConst.org_Access_Public);
		papProgram.setProgApproveName(papProgram.getProgApproveBaseLetter()
				+ IProgramsConst.pap_ProgPrefix
				+ IProgramsConst.pap_Prog_LocalizedName
				+ IProgramsConst.pap_ProgPostfix);
		papProgram.setProgApproveNames(new String[] {
				papProgram.getProgApproveName(),
				papProgram.getProgApproveName(),
				papProgram.getProgApproveName() });

		papProgram.setCreateOrUpdate(0);
		papProgram.setProgApproveAdmin(new String[] { "Super" });
		papProgram.setProgApproveSteps(initializeSteps(progType.typePAP
				.ordinal(), papProgram.getProgApproveId()));
	}


	private Hashtable setStaff() {
		Hashtable<String, Object> staff = new Hashtable<String, Object>();
		staff.put(ProgStepUtil.staffShowAll, false);
		staff.put(ProgStepUtil.staffOrg, IGeneralConst.primG3_OrgRoot);
		staff.put(ProgStepUtil.staffGroups, programStaff);

		return staff;
	}

	private Hashtable setStepStaff(String[] staff) {
		Hashtable<String, Object> stepStaff = new Hashtable<String, Object>();
		stepStaff.put(ProgStepUtil.staffShowAll, false);
		// stepStaff.put(HtmlFormsUtil.staffOrg, IGeneralConst.GRANTIUM_APP);
		stepStaff.put(ProgStepUtil.staffUsers, staff);
		stepStaff.put(ProgStepUtil.staffSearch, staff);

		return stepStaff;
	}

	private ArrayList<ProgramSteps> initializeSteps(int type, String pId) {
		try {
			steps = new ArrayList<ProgramSteps>();

			// Set Submit step
			pSubmit = new ProgramSteps();
			pSubmit.setProgId(pId);
			pSubmit.setBaseLetter(papProgram.getProgApproveBaseLetter());
			pSubmit.setStepId(pSubmit.getBaseLetter() + "-"
					+ "Funding Opp Submission");
			pSubmit.setStepNotes(IEFormsConst.applicantsubmission_FormTypeName);
			if (type == progType.typeG3.ordinal())
				pSubmit
				.setStepType(IProgStepsConst.stepType_ApplicantSubmission);
			else
				pSubmit
				.setStepType(IProgStepsConst.stepType_FundOppSubmission);
			pSubmit.setStepProjOfficerGroup(IPreTestConst.AppGroupName);
			pSubmit.setStepStartDate(GeneralUtil.getTodayDate());
			pSubmit.setStepEndDate(GeneralUtil.getNextYear());
			pSubmit.setStepIsCritical(true);
			pSubmit.setStepReExecute("No");
			pSubmit.setStepForm("Funding Opp Sub");
			pSubmit.setStepNames(stepNames[0]);
			pSubmit
			.setStepStaff(setStepStaff(new String[] { programStaff[1] }));
			pSubmit.setStepFormAccess(new String[] { pSubmit.getStepId() });
			pSubmit.setStepAction(ECreateUpdate.objectCreate.ordinal());
			pSubmit.setStepFullNameInPlanner(pSubmit.getStepId()
					+ IProgStepsConst.stepType_InPlanner_FundOppSubmission);
			steps.add(pSubmit);

			// Set Program Office Submit step
			pPOSubmit = new ProgramSteps();
			pPOSubmit.setProgId(pId);
			pPOSubmit.setBaseLetter(papProgram.getProgApproveBaseLetter());
			pPOSubmit.setStepId(pPOSubmit.getBaseLetter() + "-"
					+ "PAP POSS Submission");
			pPOSubmit
			.setStepNotes(IEFormsConst.progOfficeSubmission_FormTypeName);
			pPOSubmit.setStepType(IProgStepsConst.stepType_POSubmission);
			pPOSubmit.setStepProjOfficerGroup(IPreTestConst.AppGroupName);
			pPOSubmit.setStepStartDate(GeneralUtil.getTodayDate());
			pPOSubmit.setStepEndDate(GeneralUtil.getNextYear());
			pPOSubmit.setStepIsCritical(true);
			pPOSubmit.setStepReExecute("No");
			pPOSubmit.setStepForm("Program Office Sub");
			pPOSubmit.setStepNames(stepNames[1]);
			pPOSubmit
			.setStepStaff(setStepStaff(new String[] { programStaff[1] }));
			pPOSubmit.setStepFormAccess(new String[] { pPOSubmit.getStepId() });
			pPOSubmit.setStepAction(ECreateUpdate.objectCreate.ordinal());
			pPOSubmit.setStepFullNameInPlanner(pPOSubmit.getStepId()
					+ IProgStepsConst.stepType_InPlanner_POSubmission);
			steps.add(pPOSubmit);

			// Set Approve step
			pApprove = new ProgramSteps();
			pApprove.setProgId(pId);
			pApprove.setBaseLetter(papProgram.getProgApproveBaseLetter());
			pApprove.setStepId(pApprove.getBaseLetter() + "-"
					+ "Funding Opp Approval");
			pApprove.setStepNotes(IEFormsConst.projectApproval_FormTypeName);
			if (type == progType.typeG3.ordinal())
				pApprove.setStepType(IProgStepsConst.stepType_Approval);
			else
				pApprove
				.setStepType(IProgStepsConst.stepType_FundOppApproval);
			pApprove.setStepProjOfficerGroup(IPreTestConst.AppGroupName);
			pApprove.setStepStartDate(GeneralUtil.getTodayDate());
			pApprove.setStepEndDate(GeneralUtil.getNextYear());
			pApprove.setStepIsCritical(true);
			pApprove.setStepReExecute("Optional (Yes)");
			pApprove.setStepForm("Funding Opp Approval");
			pApprove.setStepEvaluationType(IEFormsConst.formApprovCrit[1][1]);
			pApprove.setStepQuorumAmount("1");
			pApprove.setStepAutoAssign(false);
			pApprove.setStepNames(stepNames[2]);
			pApprove
			.setStepStaff(setStepStaff(new String[] { IPreTestConst.Groups[3][0][0] }));
			pApprove.setStepFormAccess(new String[] { pApprove.getStepId(),
					pSubmit.getStepId() });
			pApprove.setStepAction(ECreateUpdate.objectCreate.ordinal());
			pApprove.setStepFullNameInPlanner(pApprove.getStepId()
					+ IProgStepsConst.stepType_InPlanner_FundOppApproval);
			steps.add(pApprove);

			// Set Program Office Submit step
			pClosingPOSS = new ProgramSteps();
			pClosingPOSS.setProgId(pId);
			pClosingPOSS.setBaseLetter(papProgram.getProgApproveBaseLetter());
			pClosingPOSS.setStepId(pClosingPOSS.getBaseLetter() + "-"
					+ "pap Closing PO Submission");
			pClosingPOSS
			.setStepNotes(IEFormsConst.progOfficeSubmission_FormTypeName);
			pClosingPOSS.setStepType(IProgStepsConst.stepType_POSubmission);
			pClosingPOSS.setStepProjOfficerGroup(IPreTestConst.AppGroupName);
			pClosingPOSS.setStepStartDate(GeneralUtil.getTodayDate());
			pClosingPOSS.setStepEndDate(GeneralUtil.getNextYear());
			pClosingPOSS.setStepIsCritical(true);
			pClosingPOSS.setStepReExecute("No");
			pClosingPOSS.setStepForm("Program Office Sub");
			pClosingPOSS.setStepNames(stepNames[3]);
			pClosingPOSS
			.setStepStaff(setStepStaff(new String[] { programStaff[1] }));
			pClosingPOSS.setStepFormAccess(new String[] { pClosingPOSS
					.getStepId() });
			pClosingPOSS.setStepAction(ECreateUpdate.objectCreate.ordinal());
			pClosingPOSS.setStepFullNameInPlanner(pClosingPOSS.getStepId()
					+ IProgStepsConst.stepType_InPlanner_POSubmission);
			steps.add(pClosingPOSS);
			return steps;
		} catch (Exception ex) {
			log.debug("ERROR in createSteps() " + ex.getMessage());
			return null;
		}
	}

}
