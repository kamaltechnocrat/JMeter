/**
 * 
 */
package test_Suite.constants.workflow;

import test_Suite.constants.preTest.IPreTestConst;

/**
 * @author mshakshouki
 *
 */
public interface IFoppConst {
	
	public static final String[] fundingOpp_Admins = {"Super"};
	
	public static final String[] fundingOpp_Staff = {"Staff", "G06 Project Officers"};
	
	public static final String fundingOpp_Oficer = "Shakshouki, Mustafa (mshakshouki@g3-qa-autobuild.csdc-lan.csdcsystems.com)";
	
	public static final String fundingOpp_Prefix = "-Gnrl-";
	
	public static final String fundingOpp_Name = "FOPP";
	
	public static final String fundingOpp_PA_Prefix = "-Gnrl-PA-";
	
	public static final String fundingOpp_Postfix = "";
	
	public static final String availAdminM2M_Id = "programStaff:staff:availableStaff";
	
	public static final String fundingOpp_AdministrationFormName = "Program Form";
	
	public static final String fundingOpp_publicationFormName = "Publication Form";	

	public static final String fundingOpp_DetailsPage_FieldsStartTag_Id = "editProgram:j_id_1z:";

	public static final String stepDetailsIdent_Id = "editStep:step:tabPane:stepIdentifier";
	
	public static final String fundingOpp_ProjConfigTab_FieldsStartTag_Id = "projectConfiguration:j_id_1z:editProjectConfiguration:projectConfig:"; //"projectConfiguration:_idJsp31:projectConfig:projectConfigTab:";
	
	public static final String fundingOpp_ProjListTab_FieldsStartTag_Id = "projectConfiguration:j_id_1z:editProjectConfiguration:projectListConfig:";
	
	public static final String fundingOpp_DetailsPage_FundingOppIdent_TxtFld_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "txtProgramIdentifier";
	
	public static final String fundingOpp_DetailsPage__TxtFld_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "";
	
	public static final String fundingOpp_DetailsPage_ProjectPrefix_TxtFld_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "projectPrefix";
	
	public static final String fundingOpp_DetailsPage_AdministrationForm_DropDown_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "programFormSelectBox";
	
	public static final String fundingOpp_DetailsPage_PublicationForm_DropDown_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "publicationFormSelectBox";
	
	public static final String fundingOpp_DetailsPage_StartDate_TxtFld_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "programStartDate";
	
	public static final String fundingOpp_DetailsPage_EndDate_TxtFld_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "programEndDate";
	
	public static final String fundingOpp_DetailsPage_Officer_DropDown_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "programManagerMenu";
	
	public static final String fundingOpp_DetailsPage_Status_DropDown_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "programStatusMenu";
	
	public static final String fundingOpp_DetailsPage_PrimaryOrg_DropDown_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "primaryOrganization";
	
	public static final String fundingOpp_DetailsPage_OrgAccess_DropDown_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "organizationAccess";
	
	public static final String fundingOpp_DetailsPage_RegistrationOpen_TxtFld_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "registrationStartDate";
	
	public static final String fundingOpp_DetailsPage_RegistrationOpen_HH_DropDown_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "registrationStartDateHour";
	
	public static final String fundingOpp_DetailsPage_RegistrationOpen_MM_DropDown_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "registrationStartDateMinute";
	
	public static final String fundingOpp_DetailsPage_RegistrationClose_TxtFld_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "registrationEndDate";
	
	public static final String fundingOpp_DetailsPage_RegistrationClose_HH_DropDown_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "registrationEndDateHour";
	
	public static final String fundingOpp_DetailsPage_RegistrationClose_MM_DropDown_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "registrationEndDateMinute";
	
	public static final String fundingOpp_DetailsPage_foDisableProjectCreation_CheckBox_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "foDisable_input";
	
	public static final String fundingOpp_DetailsPage_CompleteApplicantProfile_CheckBox_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "newProjectRequiresCompleteAF_input";
	
	public static final String fundingOpp_DetailsPage_FundingOppName_LocaleStart_TxtFld_Id = fundingOpp_DetailsPage_FieldsStartTag_Id + "programTexts:"; 
	
	public static final String fundingOpp_DetailsPage_FundingOppName_LocaleEnd_TxtFld_Id = ":programText";
	
	public static final String fundingOpp_DetailsPage_FoppStatus_DropDown_Id =fundingOpp_DetailsPage_FieldsStartTag_Id.concat("programStatusMenu_input");
	
	public static final String fundingOpp_DetailsPage_HistoricalProjs_Checkbox_Id = fundingOpp_ProjConfigTab_FieldsStartTag_Id.concat("hideProjects_input");
	
	public static final String fundingOpp_DetailsPage_ControlLevel_DropDown_Id = fundingOpp_ProjConfigTab_FieldsStartTag_Id.concat("accrossProjectAccess_input");
	
	public static final String fundingOpp_DetailsPage_DuplicateProjects_DropDown_Id = fundingOpp_ProjConfigTab_FieldsStartTag_Id.concat("duplicateProjectsMenu_input");
	
	public static final String fundingOpp_ProjConfig_ProjectEForm_DropDown_Id = fundingOpp_ProjConfigTab_FieldsStartTag_Id.concat("projectEFormsSelectBox_input");
	
	public static final String fundingOpp_ProjListConfig_EvalProjLst_Checkbox_Id = fundingOpp_ProjListTab_FieldsStartTag_Id.concat("evaluateProjectsList_input");
	
	public static final String fundingOpp_ProjListConfig_EvalProjLst_M2MAvailable_Id = fundingOpp_ProjListTab_FieldsStartTag_Id.concat("M2M_AvailableFields");
	
	public static final String fundingOpp_ProjListConfig_EvalProjLst_M2MSelected_Id = fundingOpp_ProjListTab_FieldsStartTag_Id.concat("M2M_SelectedFields");
	
	//--- Start of Funding Opp Planner-------
	
	public static final String fundingOpp_PlannerPage_Form_Id = "j_id_1v";
		
    public static final String fundingOpp_PlannerPage_Span_Id = "programPlanner:j_id_1w:planner"; //"main:_idJsp31:planner"; //5.2
	
	public static final String fundingOpp_PlannerPage_Div_Id = "j_id_28:programPlannerTree";
	
	public static final String fundingOpp_PlannerPage_FoppDetails_LinkId = "programPlanner:j_id_1v:planner:0:editProgramLink";
	
	public static enum EStepsType {
		APP_SUBMISSION, REVIEW, APPROVAL, PO_SUBMISSION, AWARD, DDECISION,WSS, POST_AWARD, CLAIM, PA_PO_SUBMISSION, PA_REVIEW, PA_APPROVAL,PA_DECISION,PA_WSS
	}
	
	public static final String[] expandedControlLevels = {"Project Officer Access Only","Access Within Projects","Access Across Projects"};
	
	public static enum EExpCtrlLevels {
		
		poOnly,withinProj,acrossProj
	}
	
	public static enum EDuplicateProjs {
		Allow, Warn_Against, Disallow
	}
	
	public static enum E3StatesFopp {
		
		active,internal,inactive
	}
	
	public static final String[] threeStates = {"Active","Active (Internal)","Inactive"};
	
	public static final String[] duplicateProjs = {"Allow","Warn Against","Disallow"};
	
	
	
	
	/******** Steps Used *******************/
	
	public static final String[] stepOfficerGrp = {"Staff"};
	public static final String stepProjOfficer = IPreTestConst.ProgPOfficer;
	
	public static final String[] stepStaffGrp = {"Staff"};
	public static final String[] stepStaffUsrs = {IPreTestConst.ProgPOfficer};

	public static final String m2mAvailStaff_listId = "/availableStaff/";
	public static final String m2mSelectStaff_listId = "/selectedStaff/";

	public static final String bf_FOPP_Submission[][] = {
		{"Submission", "Applicant Submission", "true", "No",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"Sub"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String fopp_SubmissionB[][] = {
		{"Submission-B", "Applicant Submission", "true", "Optional (Yes)",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"Sub"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String fopp_Review[][] = {
		{"Review", "Review", "true", "Optional (Yes)",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"Project Review Form", "Quorum", "1", "true","false"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String fopp_Approval[][] = {
		{"Approval", "Approval", "true", "Optional (Yes)",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"Project Approval Form", "Quorum", "1", "false","false"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String fopp_PO_Submission[][] = {
		{"PO-Submission", "Program Office Submission", "true", "No",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"Program Office Sub"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String fopp_PO_SubmissionNon[][] = {
		{"PO-Submission-Non", "Program Office Submission", "true", "No",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"Program Office Sub Non"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String fopp_AwardCrit[][] = {
		{"Award", "Award", "true", "Optional (Yes)",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"Basic Award"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String fopp_PA_AwardCrit[][] = {
		{"Award", "Award", "true", "Optional (Yes)",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"Standard Award"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String fopp_initialClaim[][] = {
		{"Post-Award-Submission", "Initial Post Award Submission", "true", "Optional (Yes)",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"Post Award Submission"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String fopp_postAwardCrit[][] = {
		{"Post-Award", "Post-Award", "true", "Optional (Yes)",stepOfficerGrp[0],stepStaffGrp[0]}
		, {fopp_AwardCrit[0][0], fopp_initialClaim[1][0]}
		, {stepProjOfficer}
		, stepStaffUsrs
		};	
	
	
	/***************** Commitment and Balance Budgeting ****************************/
	
	public static final String commBal_Prefix = "-CommBal-";
	
	public static final String commBal_PA_Prefix = "-CommBal-PA-";
	
	public static final String commBal_Budget_InitialBal_Ttl = "Initial Balance";
	public static final String commBal_Budget_SumOfAwarded_Ttl = "Sum of Awarded";
	public static final String commBal_Budget_Rescinded_Ttl = "Sum of Rescinded";
	public static final String commBal_Budget_Refunded_Ttl = "Sum of Refunded";
	public static final String commBal_Budget_Payment_Ttl = " Sum of Payment";
	public static final String commBal_Budget_Cancel_Ttl = "Sum of Cancel";
	public static final String commBal_Budget_Refund_Ttl = "Sum of Refund";
	public static final String commBal_Budget_CurrentBal_Ttl = "Current Balance";	
	
	
	/***************** Project Data Storage and Scoring ****************************/
	
	public static final String projData_Prefix = "-PEF-";
	
	public static final String projData_PA_Prefix = "-PEF-PA-";
	
	public static final String projData_pushBack_Postfix = "-PushBack-QH";
	
	public static final String projData_LBF_Postfix = "-LBF-eList";
	
	public static final String projData_eList_Postfix = "-BF-eLists";
	
	public static final String projData_BF_Postfix = "-BF-QH";
	
	public static final String refData_Prefix = "-Reference-PA-";
	
	public static final String refData_Postfix = "";
	
	public static final String decisionFOPP_Prefix = "-Decision-Notif-";
	
	public static final String decisionFOPP_Postfix = "";
	
	public static final String decision_Letter = "A";
	
	public static final String fopp_Letter = "A";
	
	public static final String bfFOPP_prefix = "-BF-Amendment-";
	
	public static final String  fopp_postFix = "";
	

	
	
	
	
	/***************** Project e.Form Config and Scoring ****************************/
	
	
	/***************** FOPP e.Form Data Export And Cloning ****************************/
	
	public static final String FundingOpp_AdminFormCheckBoxId = "exporteForm:_idJsp31:checkbox_Admin";
	
	public static final String FundingOpp_PublicFormCheckBoxId = "exporteForm:_idJsp31:checkbox_Pub";
	
	public static final String FundingOpp_BudgetFormCheckBoxId = "exporteForm:_idJsp31:checkbox_Budget";
	
	
    public static final String contactName = "Contact Name";
	
	public static final String equipment = "Equipment";
	
	public static final String  operational = "Operational";
	
	public static final String textToEnter = "CSDC";
	
	public static final String equipmentExpense = "1000.00";
	
	public static final String operationalExpense = "2000.00";

	
	
	



	

}
