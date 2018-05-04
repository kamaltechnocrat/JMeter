/**
 * 
 */
package test_Suite.utils.workflow;

import static watij.finders.SymbolFactory.id;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.INotificationsConst;
import test_Suite.constants.workflow.IProgStepsConst;
import test_Suite.constants.workflow.IProgramsConst;
import test_Suite.lib.workflow.ProgramSteps;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 *
 */
@SuppressWarnings("rawtypes")
public class ProgStepUtil {
	
	//Added By Mustafa
	//The UI must be in Step Details Page
	
	static boolean retValue;
	private static Log log = LogFactory.getLog(ProgStepUtil.class);
	
	public static final String staffShowAll = "showallgroups";
	public static final String staffOrg     = "org";
	public static final String staffGroups  = "groups";
	public static final String staffUsers   = "users";
	public static final String staffSearch  = "search";
	
	public static boolean addStep(ProgramSteps progSteps) throws Exception {
		
		retValue = false;
		
		log.info("Adding a Step");
		
		IE ie = IEUtil.getActiveIE();		
		ie.textField(id, IProgStepsConst.step_Id).set(progSteps.getStepId());		
		ie.textField(id, IProgStepsConst.step_Notes).set(progSteps.getStepNotes());		
		ie.selectList(id, IProgStepsConst.step_Type).select(progSteps.getStepType());		
		ie.selectList(id, IProgStepsConst.step_Admin).select(progSteps.getStepProjOfficerGroup());		
		ie.textField(id, IProgStepsConst.step_Start_Date).set(progSteps.getStepStartDate());		
		ie.textField(id, IProgStepsConst.step_End_Date).set(progSteps.getStepEndDate());		
		ie.checkbox(id, IProgStepsConst.step_Is_Critical).set(progSteps.isStepIsCritical());		
		ie.selectList(id, IProgStepsConst.step_Re_Execute).select(progSteps.getStepReExecute());		
		String[] arrStepNames = progSteps.getStepNames();		
		ie.textField(id, IProgStepsConst.step_Title_Start + "0" + IProgStepsConst.step_Title_End).set(progSteps.getBaseLetter() + "-" + arrStepNames[0]);		
		if (ie.textField(id, IProgStepsConst.step_Title_Start + "1" + IProgStepsConst.step_Title_End).exists())
		{
			ie.textField(id, IProgStepsConst.step_Title_Start + "1" + IProgStepsConst.step_Title_End).set(progSteps.getBaseLetter() + "-" + arrStepNames[1]);
		}
		
		if (ie.textField(id, IProgStepsConst.step_Title_Start + "2" + IProgStepsConst.step_Title_End).exists())
		{
			ie.textField(id, IProgStepsConst.step_Title_Start + "2" + IProgStepsConst.step_Title_End).set(progSteps.getBaseLetter() + "-" + arrStepNames[2]);
		}
		
		//Fill out the Properties for now dealing with PAP
		//TODO: Add Award and Post-Award
		
		if((progSteps.getStepType() == IProgStepsConst.stepType_FundOppSubmission) ||
				(progSteps.getStepType() == IProgStepsConst.stepType_ApplicantSubmission) ||
				(progSteps.getStepType() == IProgStepsConst.stepType_Award))
		{
			addSubmissionAndAwardStep_Prop(progSteps);
		}
		else if ((progSteps.getStepType() == IProgStepsConst.stepType_Review) || 
				(progSteps.getStepType() == IProgStepsConst.stepType_Approval) ||
				(progSteps.getStepType() == IProgStepsConst.stepType_FundOppApproval))
		{
			addEvaluationStep_Prop(progSteps);
		}
		else if (progSteps.getStepType()== IProgStepsConst.stepType_Decsision)
		{
			addDecisionStep_Prop(progSteps);
		}
		else if (progSteps.getStepType()== IProgStepsConst.stepType_PostAward)
		{
			addPostAwardStep_Prop(progSteps);
		}
		
		else if (progSteps.getStepType()== IProgStepsConst.stepType_InitialPASubmission)
		{
			//Do nothing for Now!
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		ClicksUtil.clickButtons(IClicksConst.backBtn);
		
		//Assert.assertTrue(addStepStaff(progSteps, new String[] {progSteps.getStepProjOfficerGroup()}), "Fail: Error adding Step Staff");
		Assert.assertTrue(addStepStaff(progSteps), "Fail: Error adding Step Staff");
		
		if (progSteps.getStepDocuments() != null)
		{
			Assert.assertTrue(addStepDocuments(progSteps),"Fail: Error Adding Step Documents");
		}
		
		if (progSteps.getStepFormAccess() != null)
		{
			Assert.assertTrue(addStepFormAccess(progSteps),"Fail: Error Adding Form Access");
		}
		
		if (progSteps.getStepFormData() != null)
		{
			Assert.assertTrue(addStepFormData(progSteps),"Fail: Error Adding Form Data");
		}
			
		return retValue;		
	}
	
	public static boolean addSubmissionAndAwardStep_Prop(ProgramSteps progSteps) throws Exception {
		
		
		IE ie = IEUtil.getActiveIE();
		
		if(!ie.selectList(id, IProgStepsConst.step_Prop_Form).exists())
		return false;
		
		ie.selectList(id, IProgStepsConst.step_Prop_Form).select(progSteps.getStepForm());
		return true;
	}
	
	public static boolean addEvaluationStep_Prop(ProgramSteps progSteps) throws Exception {
		
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();		
		ie.selectList(id, IProgStepsConst.step_Prop_Form).select(progSteps.getStepForm());		
		ie.selectList(id, IProgStepsConst.step_Prop_Eval_Type).select(progSteps.getStepEvaluationType());		
		ie.textField(id, IProgStepsConst.step_Prop_Amount).set(progSteps.getStepQuorumAmount());		
		ie.checkbox(id, IProgStepsConst.step_Prop_Auto_Assign).set(progSteps.isStepAutoAssign());		
		return retValue;		
	}
	
	public static boolean addAwardStep_Prop(ProgramSteps progSteps) throws Exception {
		
		retValue = false;		
	
		return retValue;		
	}
	
	public static boolean addNewNotification(String currentStepName,String noteParams[], boolean conditions[], String everyDays, String repeatNote) throws Exception {
		
		
		IE ie = IEUtil.getActiveIE();	
		
		ie.textField(id, INotificationsConst.ntfName).set(noteParams[1] + " - " + currentStepName);
		
		ie.selectList(id, INotificationsConst.ntfEvent).select(noteParams[0]);
		
		GeneralUtil.takeANap(0.5);
		
		ie.textField(id, "/:0:" + INotificationsConst.ntfSubject).set(currentStepName);
		
		ie.textField(id, "/:0:" + INotificationsConst.ntfBody).set(currentStepName + ": " + noteParams[3]);
		
		if (ie.textField(id,"/:1:" + INotificationsConst.ntfSubject).exists())
		{
			ie.textField(id, "/:1:" + INotificationsConst.ntfSubject).set(currentStepName);
			
			ie.textField(id, "/:1:" + INotificationsConst.ntfBody).set(currentStepName + ": " + noteParams[3]);
		}
		
		if (ie.textField(id, "/:2:" + INotificationsConst.ntfSubject).exists())
		{
			ie.textField(id, "/:2:" + INotificationsConst.ntfSubject).set(currentStepName);
			
			ie.textField(id, "/:2:" + INotificationsConst.ntfBody).set(currentStepName + ": " + noteParams[3]);
		}
		
		if (ie.textField(id, "/:3:" + INotificationsConst.ntfSubject).exists())
		{
			ie.textField(id, "/:3:" + INotificationsConst.ntfSubject).set(currentStepName);
			
			ie.textField(id, "/:3:" + INotificationsConst.ntfBody).set(currentStepName + ": " + noteParams[3]);
		}
		
		ie.checkbox(id, INotificationsConst.ntfIsActive).set(conditions[0]);
		
		
		
		if (ie.checkbox(id, INotificationsConst.ntfRepeatable).exists())
		{
			ie.checkbox(id, INotificationsConst.ntfRepeatable).set(conditions[3]);			
			
		}
		
		ie.checkbox(id, INotificationsConst.ntfNotifyApplicants).set(conditions[1]);

		
		//Set Schedule
		
		ie.textField(id, INotificationsConst.ntfNotifyEvery).set(everyDays);
		
		if (ie.textField(id, INotificationsConst.ntfRepeatEvery).exists())
			ie.textField(id, INotificationsConst.ntfRepeatEvery).set(repeatNote);
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.backBtn);
	
		return true;
	}
	
	public static boolean addNewNotification(String currentStepName,String noteParams[], boolean conditions[], String groupsToSelect[], String everyDays, String repeatNote) throws Exception {
		
		
		IE ie = IEUtil.getActiveIE();	
		
		ie.textField(id, INotificationsConst.ntfName).set(noteParams[1] + " - " + currentStepName);
		
		ie.selectList(id, INotificationsConst.ntfEvent).select(noteParams[0]);
		
		ie.textField(id, INotificationsConst.ntfSubject + "[0]").set(currentStepName);
		
		ie.textField(id, INotificationsConst.ntfBody + "[0]").set(currentStepName + ": " + noteParams[3]);
		
		if (ie.textField(id,INotificationsConst.ntfSubject + "[1]").exists())
		{
			ie.textField(id, INotificationsConst.ntfSubject + "[1]").set("[fr_CA] " + currentStepName);
			
			ie.textField(id, INotificationsConst.ntfBody + "[1]").set("[fr_CA] " + currentStepName + ": " + noteParams[3]);
		}
		
		if (ie.textField(id,INotificationsConst.ntfSubject + "[2]").exists())
		{
			ie.textField(id, INotificationsConst.ntfSubject + "[2]").set(currentStepName);
			
			ie.textField(id, INotificationsConst.ntfBody + "[2]").set(currentStepName + ": " + noteParams[3]);
		}
		
		ie.checkbox(id, INotificationsConst.ntfIsActive).set(conditions[0]);
		
		
		
		if (ie.checkbox(id, INotificationsConst.ntfRepeatable).exists())
		{
			ie.checkbox(id, INotificationsConst.ntfRepeatable).set(conditions[3]);			
			
		}
		
		/*************************************
		 *  Since the introduction of Group Assignment
		 *************************************/
			
		//ie.checkbox(id, INotificationsConst.ntfNotifyApplicants).set(conditions[1]);
		
		//ie.checkbox(id, INotificationsConst.ntfNotifyAllProjectOfficers).set(conditions[2]);
		
		//FIXME: for now it's hardcoded
		
		ie.selectList(id,INotificationsConst.ntfNotifyProjOfficerForThisStep).select("All Project Officer Group Members");
		
		ie.selectList(id,INotificationsConst.ntfNotifyAllProjOffForOtherSteps).select("Assigned Project Officers or Groups");
		//Setting Groups
		
		if (groupsToSelect.length > 0) {
			
			for(int i=0; i<groupsToSelect.length; i++) {
				
				ie.selectList(id, INotificationsConst.ntfAvailableRecipients).select(groupsToSelect[i]);
				
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			}
		}
		
		//Set Schedule
		
		ie.textField(id, INotificationsConst.ntfNotifyEvery).set(everyDays);
		
		if (ie.textField(id, INotificationsConst.ntfRepeatEvery).exists())
			ie.textField(id, INotificationsConst.ntfRepeatEvery).set(repeatNote);
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.backBtn);
	
		return true;
	}
	
	public static void addPostAwardStep_Prop(ProgramSteps progSteps) throws Exception {		
		
		IE ie = IEUtil.getActiveIE();		
		ie.selectList(id, IProgStepsConst.step_Prop_Form).select(progSteps.getStepAwardName());		
		String[] arrPASubFormNames = progSteps.getStepPostAwardForms();	
		
		for(int i=0; i<arrPASubFormNames.length; i++)
		{
			ie.selectList(id, IProgStepsConst.step_Prop_M2M_Available).select(arrPASubFormNames[i]);
			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
		}	
			
	}
	
	public static boolean addDecisionStep_Prop(ProgramSteps progSteps) throws Exception {
		IE ie = IEUtil.getActiveIE();
		
		if(!ie.selectList(id, IProgStepsConst.step_Decision_Prop_DataFrom).exists()) return false;
		
		ie.selectList(id, IProgStepsConst.step_Decision_Prop_DataFrom).select(progSteps.getStepDecisionDataFromStep());
		
		if(!ie.selectList(id, IProgStepsConst.step_Decision_Prop_SkipToStep).exists()) return false;
		
		ie.selectList(id, IProgStepsConst.step_Decision_Prop_SkipToStep).select(progSteps.getStepDecisionSkipToStep());
		
		if(!ie.selectList(id, IProgStepsConst.step_Decision_Prop_Experssion).exists()) return false;
		
		ie.textField(id, IProgStepsConst.step_Decision_Prop_Experssion).set(progSteps.getStepDecisionExpression());
		
		return true;
	}
	

	public static boolean isUserInList(String userName) throws Exception {
		
		retValue = false;		
		IE ie = IEUtil.getActiveIE();
		
		ie.textField(id, IProgStepsConst.stepStaff_AccessSearch_Id).set(userName);
		
		ClicksUtil.clickButtons(IClicksConst.searchBtn);
		
		GeneralUtil.takeANap(1.0);
		if (GeneralUtil.isObjectExistsInList(IProgStepsConst.stepStaff_AvailableStaff_Id, userName))
		{
			ie.selectList(id,IProgStepsConst.stepStaff_AvailableStaff_Id).select("/" + userName + "/");
			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			GeneralUtil.takeANap(0.5);				
			
			retValue = true;
		}
		ClicksUtil.clickButtons(IClicksConst.backBtn);
		
		return retValue;
		
	}
	public static boolean addStepStaff(ProgramSteps progStep) throws Exception {
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		
		Hashtable hashStaff = progStep.getStepStaff();
		
		String [] stepStaff = (String []) hashStaff.get(staffUsers);
		
		ProgramUtil.openStepManagmentDetails(IProgramsConst.EProjectType.pre_Award.ordinal(),progStep.getStepFullNameInPlanner(),IProgramsConst.EstepManagment.stepStaff.ordinal());

		for(int i=0; i<stepStaff.length; i++)
		{
			ie.textField(id, IProgStepsConst.stepStaff_AccessSearch_Id).set(stepStaff[i]);
			
			ClicksUtil.clickButtons(IClicksConst.searchBtn);
			
			GeneralUtil.takeANap(1.0);
			
			if (GeneralUtil.isObjectExistsInList(IProgStepsConst.stepStaff_AvailableStaff_Id, stepStaff[i]))
			{
				ie.selectList(id,IProgStepsConst.stepStaff_AvailableStaff_Id).select("/" + stepStaff[i] + "/");
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
				GeneralUtil.takeANap(0.5);				
				
				retValue = true;
			}
			else if(GeneralUtil.isObjectExistsInList(IProgStepsConst.stepStaff_SelectedStaff_Id, stepStaff[i]))
			{
				retValue = true;
			}
		}
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		ClicksUtil.clickButtons(IClicksConst.backBtn);
		return retValue;
	}
	
	public static boolean addStepStaff(ProgramSteps progStep, String[] stepStaff) throws Exception {
		
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		ProgramUtil.openStepManagmentDetails(IProgramsConst.EProjectType.pre_Award.ordinal(),progStep.getStepFullNameInPlanner(),IProgramsConst.EstepManagment.stepStaff.ordinal());
		
		for(int i=0; i<stepStaff.length; i++)
		{
			ie.textField(id, IProgStepsConst.stepStaff_AccessSearch_Id).set(stepStaff[i]);
			
			ClicksUtil.clickButtons(IClicksConst.searchBtn);
			
			GeneralUtil.takeANap(1.0);
			
			if (GeneralUtil.isObjectExistsInList(IProgStepsConst.stepStaff_AvailableStaff_Id, stepStaff[i]))
			{
				ie.selectList(id,IProgStepsConst.stepStaff_AvailableStaff_Id).select("/" + stepStaff[i] + "/");
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
				GeneralUtil.takeANap(0.5);				
				
				retValue = true;
			}
			else if(GeneralUtil.isObjectExistsInList(IProgStepsConst.stepStaff_SelectedStaff_Id, stepStaff[i]))
			{
				retValue = true;
			}
			
		}
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		ClicksUtil.clickButtons(IClicksConst.backBtn);
	
		return retValue;		
	}
	
	public static boolean addStepDocuments(ProgramSteps progStep) throws Exception {
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		ProgramUtil.openStepManagmentDetails(IProgramsConst.EProjectType.pre_Award.ordinal(),progStep.getStepFullNameInPlanner(),IProgramsConst.EstepManagment.documents.ordinal());
		for(int i=0; i<progStep.getStepDocuments().length; i++)
		{			
			
			if (GeneralUtil.isObjectExistsInList(IProgStepsConst.stepDocuments_AvailableDocument_Id, progStep.getStepDocuments()[i]))
			{
				ie.selectList(id,IProgStepsConst.stepDocuments_AvailableDocument_Id).select(progStep.getStepDocuments()[i]);
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);			
				retValue = true;
			}
			else if (GeneralUtil.isObjectExistsInList(IProgStepsConst.stepDocuments_SelectedDocument_Id, progStep.getStepDocuments()[i]))
			{
				retValue = true;
			}
			
		}
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		ClicksUtil.clickButtons(IClicksConst.backBtn);
		return retValue;
	}
	
	public static boolean addStepFormAccess(ProgramSteps progStep) throws Exception {
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		ProgramUtil.openStepManagmentDetails(IProgramsConst.EProjectType.pre_Award.ordinal(),progStep.getStepFullNameInPlanner(),IProgramsConst.EstepManagment.formAccess.ordinal());
		for(int i=0; i<progStep.getStepFormAccess().length; i++)
		{			
			
			if (GeneralUtil.isObjectExistsInList(IProgStepsConst.stepFormAccess_AvailableForms_Id, progStep.getStepFormAccess()[i]))
			{
				ie.selectList(id,IProgStepsConst.stepFormAccess_AvailableForms_Id).select(progStep.getStepFormAccess()[i]);
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
				retValue = true;
			}
			else if(GeneralUtil.isObjectExistsInList(IProgStepsConst.stepFormAccess_SelectedForms_Id, progStep.getStepFormAccess()[i]))
			{
				retValue = true;
			}
			
		}
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		ClicksUtil.clickButtons(IClicksConst.backBtn);
		return retValue;
	}
	
	
	public static boolean addStepFormData(ProgramSteps progStep) throws Exception {
		retValue = false;
		
		IE ie = IEUtil.getActiveIE();
		
		if(progStep.getStepFormData().length == 4)
		{
			ProgramUtil.openStepManagmentDetails(IProgramsConst.EProjectType.pre_Award.ordinal(),progStep.getStepFullNameInPlanner(),IProgramsConst.EstepManagment.formData.ordinal());
			
			ie.selectList(id,IProgStepsConst.stepFormData_ApplicantProfile_Id).select(progStep.getStepFormData()[0]);
			ie.selectList(id,IProgStepsConst.stepFormData_ProgramForm_Id).select(progStep.getStepFormData()[1]);
			for(int i=0; i<progStep.getStepFormData().length; i++)
			{			
				
				if (GeneralUtil.isObjectExistsInList(IProgStepsConst.stepFormData_AvailableForms_Id, progStep.getStepFormData()[i]))
				{
					ie.selectList(id,IProgStepsConst.stepFormData_AvailableForms_Id).select(progStep.getStepFormData()[i]);
					ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
					retValue = true;
				}
				else if(GeneralUtil.isObjectExistsInList(IProgStepsConst.stepFormData_SelectedForms_Id, progStep.getStepFormData()[i]))
				{
					retValue = true;
				}
				
			}
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			ClicksUtil.clickButtons(IClicksConst.backBtn);
		}

		return retValue;
	}
	

public static boolean addConditionalNotification(String currentStepName,String noteParams[], boolean conditions[], String everyDays, String repeatNote, String datafromStep,String expression) throws Exception {
		
		
		IE ie = IEUtil.getActiveIE();	
		
		ie.textField(id, INotificationsConst.ntfName).set(noteParams[1] + " - " + currentStepName);
		
		ie.selectList(id, INotificationsConst.ntfEvent).select(noteParams[0]);
		
		ie.textField(id, INotificationsConst.ntfSubject + "[0]").set(currentStepName);
		
		ie.textField(id, INotificationsConst.ntfBody + "[0]").set(currentStepName + ": " + noteParams[3]);
		
		if (ie.textField(id,INotificationsConst.ntfSubject + "[1]").exists())
		{
			ie.textField(id, INotificationsConst.ntfSubject + "[1]").set(currentStepName);
			
			ie.textField(id, INotificationsConst.ntfBody + "[1]").set(currentStepName + ": " + noteParams[3]);
		}
		
		if (ie.textField(id,INotificationsConst.ntfSubject + "[2]").exists())
		{
			ie.textField(id, INotificationsConst.ntfSubject + "[2]").set(currentStepName);
			
			ie.textField(id, INotificationsConst.ntfBody + "[2]").set(currentStepName + ": " + noteParams[3]);
		}
		
		if (ie.textField(id,INotificationsConst.ntfSubject + "[3]").exists())
		{
			ie.textField(id, INotificationsConst.ntfSubject + "[3]").set(currentStepName);
			
			ie.textField(id, INotificationsConst.ntfBody + "[3]").set(currentStepName + ": " + noteParams[3]);
		}
		
		ie.checkbox(id, INotificationsConst.ntfIsActive).set(conditions[0]);
		
		
		
		if (ie.checkbox(id, INotificationsConst.ntfRepeatable).exists())
		{
			ie.checkbox(id, INotificationsConst.ntfRepeatable).set(conditions[3]);			
			
		}
		
		ie.checkbox(id, INotificationsConst.ntfNotifyApplicants).set(conditions[1]);

		
		//Set Schedule
		
		ie.textField(id, INotificationsConst.ntfNotifyEvery).set(everyDays);
		
		if (ie.textField(id, INotificationsConst.ntfRepeatEvery).exists())
			ie.textField(id, INotificationsConst.ntfRepeatEvery).set(repeatNote);
		
		//Set Condition
		if (ie.checkbox(id, INotificationsConst.ntfDefineSendCondition).exists())
			
		{
			ie.checkbox(id, INotificationsConst.ntfDefineSendCondition).click();
			
			GeneralUtil.takeANap(3.0);
			
			IE ie1 = IEUtil.getActiveIE();
				
			if(ie1.selectList(id, INotificationsConst.ntfDatafromStep_Dropdwn_Id).exists())	
			{
				Assert.assertTrue(GeneralUtil.selectFullStringInDropdownList(
						INotificationsConst.ntfDatafromStep_Dropdwn_Id,
						datafromStep), "FAIL: No Data from Step is available in select list: "
						.concat(datafromStep));
			}
			
			else
			{
				log.error("Could Not Find  Data from step Dropdown list" );
				return false;
			}
			 if(ie1.textField(id, INotificationsConst.ntfExpressionId).exists())
			 {
				 ie1.textField(id, INotificationsConst.ntfExpressionId).set(expression); 
			 }
			 
			 else
				{
					log.error("Could Not Find Expression field" );
					return false;
				}
		}
		
		else
		{
			log.error("Define Send Condition checkbox is doesn't existed" );
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.backBtn);
	
		return true;
	}

}
