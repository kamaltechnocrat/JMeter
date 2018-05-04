/**
 * this Utilities methods are helper to Approve a Funding Opp.
 */
package test_Suite.utils.workflow;

import static watij.finders.SymbolFactory.*;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import watij.dialogs.ConfirmDialog;
import watij.runtime.ie.IE;
import test_Suite.constants.ui.*;
import test_Suite.constants.cases.*;
import test_Suite.constants.workflow.*;
import test_Suite.lib.workflow.ProgramSteps;
import test_Suite.utils.ui.*;
import test_Suite.utils.cases.*;

/**
 * @author mshakshouki
 * 
 */
public class FoppApprovalUtil {

	private static Log log = LogFactory.getLog(FoppApprovalUtil.class);

	static boolean retValue;

	static int rowIndex;

	static String retString;

	public static void setPAPRequired(String isRequired) throws Exception {
		
		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openApplicationSettings);

/**->*/	ie.selectList(id, "/papRequired/").select(isRequired); /**<-*/

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

	}

	public static boolean openFundingOppPlanner(String fndOppFullId,
			String fndOppStatus) throws Exception {
		IE ie = IEUtil.getActiveIE();
		retValue = false;
		rowIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);

		rowIndex = TablesUtil.findInTable(
				ITablesConst.fundingOpp_programsTableId, new String[] {
						fndOppFullId, fndOppStatus });

		if (rowIndex > -1) {
			ie.table(id, ITablesConst.fundingOpp_programsTableId).body(id,"/tbody_element/").row(rowIndex)
					.link(text, fndOppFullId).click();
			retValue = true;
		}
		return retValue;
	}

	public static boolean changeProgramApprovalProcess(String foppFullName,String papFullName)
			throws Exception {

		retValue = false;
		
		ProgramUtil.openProgPlannerObjectDetails(
				IProgramsConst.EProjectType.pre_Award.ordinal(), foppFullName);

		if (GeneralUtil.selectFullStringInDropdownList(
				IProgramsConst.fundingOpp_PROG_PAP_SELECT, papFullName)) {
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			retValue = true;
		}
		ClicksUtil.clickButtons(IClicksConst.backBtn);
		return retValue;
	}

	public static void submitFundingOppForm() throws Exception {

		IE ie = IEUtil.getActiveIE();

		ProgramUtil.openProgPlannerObjectDetails(
				IProgramsConst.EProjectType.pre_Award.ordinal(),
				"Funding Opportunity Submission e.Form");

		ie.textField(0).set("Start FOPP Approval Process");
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		ClicksUtil.clickLinks(IClicksConst.backToFundingOppPlannerLnk);

	}

	public static void startFundingOppApproval(String foppFullName)
			throws Exception {

		ProgramUtil.openProgPlannerObjectDetails(
				IProgramsConst.EProjectType.pre_Award.ordinal(), foppFullName);

		Assert.assertTrue((GeneralUtil
				.isButtonExistsByValue(IClicksConst.startApprovalReviewBtn)),
				"Fail: Start Approval Button is missing");
		
		Thread dialogClickerFOPPApproval = new Thread()
		{
			@Override
			public void run() {
				try
				{
					IE ie = IEUtil.getActiveIE();
					ConfirmDialog dialogFOPPApproval = ie.confirmDialog();
					while (dialogFOPPApproval==null)
					{
						log.debug("can't yet get handle on confirm dialog1");
						GeneralUtil.takeANap(1.0);
					}
					
					dialogFOPPApproval.ok();
					log.debug("got confirm Dialog1 and clicked OK.");					
					
				}
				catch (Exception e)
				{
					throw new RuntimeException("Unexpected exception",e);
				}
			}
		};
		
		dialogClickerFOPPApproval.start();

		ClicksUtil.clickButtons(IClicksConst.startApprovalReviewBtn);
		
		dialogClickerFOPPApproval = null;

	}

	public static void assignProgramApprovalOfficers(String fundOppFullName, String progFullIdent,
			String progFullName, String[][] officers) throws Exception {

		Assert.assertTrue(openFundingOppDetails(fundOppFullName, progFullName, progFullIdent),
				"Fail: Could not Open Funding Opp.!");

		ClicksUtil.clickButtons(IClicksConst.pap_projectOfficerBtn);
		
		ClicksUtil.clickButtons(IClicksConst.assignUserBtn);
		
		GeneralUtil.takeANap(1.0);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		ClicksUtil.clickButtons(IClicksConst.pap_BackToSubmissionList);
	}

	public static void submitPOSSForm(String fundOppFullName, String progFullName)
			throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		Assert.assertTrue(openPOSSFormDetails(fundOppFullName, progFullName),
				"Fail: Could Not open POSS Form");

		ie.textField(0).set("Submit POSS");
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		ClicksUtil.clickButtons(IClicksConst.submitBtn);

		ClicksUtil.clickLinks(IClicksConst.backToMyAssignedSubmissionsListLnk);

	}

	public static boolean openPOSSFormDetails(String fundOppFullName,
			String progFullName) throws Exception {
		IE ie = IEUtil.getActiveIE();

		retValue = false;
		rowIndex = -1;
		
		Hashtable<String, String> hashTableText = new Hashtable<String, String>();

		ClicksUtil.clickLinkBySpanId(IClicksConst.menuPAP_MyInBasket_Span_Id,
				IClicksConst.openMyAssignedSubmissionListLnk);

		hashTableText.put(IFiltersConst.pap_FoppName_Lbl,
				fundOppFullName);
		
		hashTableText.put(IFiltersConst.pap_ProgramName_Lbl,
				progFullName);

		FiltersUtil.filterListByLabel(hashTableText,null,false);

		rowIndex = TablesUtil.findInTable(
				ITablesConst.pap_MyAssignedSubmissionsTableId, new String [] {fundOppFullName});

		if (rowIndex > -1) {
			ie.table(id, ITablesConst.pap_MyAssignedSubmissionsTableId).body(id,"/tbody_element/").row(
					rowIndex).image(0).click();
			retValue = true;
		}
		return retValue;
	}

	public static boolean openFundingOppDetails(String fundOppFullName,
			String progFullName, String progFullId) throws Exception {

		IE ie = IEUtil.getActiveIE();

		retValue = false;
		rowIndex = -1;
		
		Hashtable<String, String> hashTableText = new Hashtable<String, String>();

		ClicksUtil.clickLinkBySpanId(IClicksConst.menuPAP_Intake_Span_Id,
				IClicksConst.openInBasketListLnk);

		hashTableText.put(IFiltersConst.pap_FoppName_Lbl,
				fundOppFullName);
		
		hashTableText.put(IFiltersConst.pap_ProgramName_Lbl, progFullName);

		FiltersUtil.filterListByLabel(hashTableText,null,false);

		rowIndex = TablesUtil.findInTable(ITablesConst.pap_IntakeTableId,new String [] {fundOppFullName});

		if (rowIndex > -1) {
			ie.table(id, ITablesConst.pap_IntakeTableId).body(id,"/tbody_element/").row(rowIndex).link(1).click();
			retValue = true;
		}
		return retValue;
	}

	public static boolean evaluatePAPSubmission(ProgramSteps pEvaluationStep,
			String fndOppFullName, String stepStatus, boolean doSubmit)
			throws Exception {
		IE ie = IEUtil.getActiveIE();

		retValue = false;
		rowIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openPAPAssignedSubmissionListLnk);

		filterEvaluationList(pEvaluationStep, fndOppFullName, stepStatus);

		rowIndex = TablesUtil.findInTable(
				ITablesConst.pap_EvaluateSubmissionsTableId,
				new String[] { pEvaluationStep.getBaseLetter() + " " + pEvaluationStep.getStepNames()[2],
						stepStatus });

		if (rowIndex > -1) {
			
			ie.table(id, ITablesConst.pap_EvaluateSubmissionsTableId).body(id,"/tbody_element/").row(
					rowIndex).image(alt, "Evaluate Submission").click();
			
			ie.selectList(name, "/numericDropdown/").select("Approved");
			
			ie.textField(name, "/datePicker/").set(GeneralUtil.getTodayDate());
			
			ie.textField(name, "/textArea_Below/").set("Comment");
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			if (doSubmit) {
				ClicksUtil.clickButtons(IClicksConst.submitBtn);
			}

			ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);
			retValue = true;
		}

		return retValue;
	}

	public static boolean evaluatePAPReview(ProgramSteps pReview,
			String fndOppFullName, String stepStatus, boolean doSubmit)
			throws Exception {
		IE ie = IEUtil.getActiveIE();

		retValue = false;
		rowIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openPAPAssignedSubmissionListLnk);

		filterEvaluationList(pReview, fndOppFullName, stepStatus);

		rowIndex = TablesUtil.findInTable(
				ITablesConst.pap_EvaluateSubmissionsTableId, new String[] {
						fndOppFullName, pReview.getStepId(), stepStatus });

		if (rowIndex > -1) {
			ie.table(id, ITablesConst.pap_EvaluateSubmissionsTableId).body(id,"/tbody_element/").row(
					rowIndex).image(alt, "Evaluate Submission").click();
			ie.textField(name, "/0:datePicker/")
					.set(GeneralUtil.getTodayDate());
			ie.selectList(name, "/1:numericDropdown/").select("Yes");
			ie.textField(name, "/2:textArea_Below/").set("Comment");
			ie.textField(name, "/3:textBox/").set("5");
			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			if (doSubmit) {
				ClicksUtil.clickButtons(IClicksConst.submitBtn);
			}

			ClicksUtil.clickLinks(IClicksConst.backToAssignedSubListLnk);
			retValue = true;
		}

		return retValue;
	}

	public static boolean assignEvaluator(ProgramSteps pEvaluationStep,
			String fndOppFullName, String [] evalutors) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		retValue = false;
		rowIndex = -1;
		
		Hashtable<String, String> hashTableText = new Hashtable<String, String>();	

		hashTableText.put(IFiltersConst.pap_StepName_Lbl, pEvaluationStep.getBaseLetter() + " " + pEvaluationStep.getStepNames()[0]);
		
		hashTableText.put( IFiltersConst.pap_ProgramName_Lbl, pEvaluationStep.getProgName());
		
		hashTableText.put( IFiltersConst.pap_FoppName_Lbl,	fndOppFullName);
		
		ClicksUtil.clickLinkBySpanId(IClicksConst.menuPAP_Evaluation_Span_Id, IClicksConst.openEvaluatorListLnk);

		FiltersUtil.filterListByLabel(hashTableText,null,false);
		
		rowIndex = TablesUtil.findInTable(ITablesConst.pap_AssignEvaluatorsTableId, new String [] {fndOppFullName, pEvaluationStep.getProgName(),pEvaluationStep.getStepNames()[0]});
		
		if (rowIndex > -1)
		{
			ie.table(id,ITablesConst.pap_AssignEvaluatorsTableId).body(id,"/tbody_element/").row(rowIndex).image(alt, "Assign Reviewers").click();
			for(int i=0; i<evalutors.length; i++)
			{
				ie.selectList(id,IProgramsConst.pap_AssignEvals_AvailableEvals_M2M).select(evalutors[i]);
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);				
			}
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			ClicksUtil.clickButtons(IClicksConst.evaluatorAssignmentListBtn);
			retValue = true;
		}
		return retValue;
	}

	private static void filterEvaluationList(ProgramSteps pEvaluationStep,	String fndOppFullName, String stepStatus) throws Exception {
		
		Hashtable<String, String> hashTableText = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

		hashTableText.put(IFiltersConst.pap_StepName_Lbl,	pEvaluationStep.getBaseLetter() + " " + pEvaluationStep.getStepNames()[2]);
		
		hashTableText.put( IFiltersConst.pap_ProgramName_Lbl, pEvaluationStep.getProgName());
		
		hashTableText.put( IFiltersConst.pap_FoppName_Lbl, 	fndOppFullName);

		hashTableDropdown.put( IFiltersConst.pap_SubmissionStatus_Lbl, stepStatus);

		FiltersUtil.filterListByLabel(hashTableText, hashTableDropdown, false);
	}

	public static String getUsedFndOppBaseLetter(String fndOppFullName)
			throws Exception {
		IE ie = IEUtil.getActiveIE();
		retString = "";
		boolean bolloop = true;
		int AlphaIndex = 0;

		while (bolloop) {
			
			Hashtable<String, String> hashTableText = new Hashtable<String, String>();
			Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();

			hashTableText.put(IFiltersConst.gpa_FundingOppIdent_Lbl,
					IGeneralConst.baseLetters[AlphaIndex] + fndOppFullName);
			
			hashTableDropdown.put(IFiltersConst.gpa_Staus_Lbl,
					"Inactive");

			FiltersUtil.filterListByLabel(hashTableText, hashTableDropdown, false);
			
			ie.waitUntilReady();

			if (GeneralUtil
					.isLinkExistsByTxt(IGeneralConst.baseLetters[AlphaIndex]
							+ fndOppFullName)) {
				bolloop = false;
				retString = IGeneralConst.baseLetters[AlphaIndex];
			}
			AlphaIndex += 1;
		}

		return retString;
	}

}
