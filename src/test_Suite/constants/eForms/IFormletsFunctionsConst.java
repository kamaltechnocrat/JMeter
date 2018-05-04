/**
 * 
 */
package test_Suite.constants.eForms;

/**
 * @author mshakshouki
 *
 */
public interface IFormletsFunctionsConst {

	
	//************ Formlet Functions **********************
	
//	public static final String formlet_ApplicantProfileCompleted_FunctionType = "Applicant Profile Completed";	
	public static final String formlet_CalculatedFormletReadOnly_FunctionType = "Calculated Formlet Read Only";	
//	public static final String formlet_CalculatedFormletUserAccessGrant_FunctionType = "Calculated Formlet User Access Grant";	
//	public static final String formlet_CalculatedFormletVisibility_FunctionType = "Calculated Formlet Visibility";	
//	public static final String formlet_CalculatedSubmissionCondition_FunctionType = "Calculated Submission Condition";	
//	public static final String formlet_MandatoryFieldValidator_FunctionType = "Mandatory Field Validator";
//	public static final String formlet_RecordPossible_COI_FunctionType = "Record Possible Confilcts of Interest (Formlet)";
//	public static final String formlet_RecordProject_COI_FunctionType = "Record Project Conflict of Interest (Formlet)";
//	public static final String formlet_RecordUser_COI_FunctionType = "Record User Conflict of Interset (Formlet)";	
//	public static final String formlet_SimpleDateComparison_FunctionType = "Simple Date Comparison";	
//	public static final String formlet_SimpleNumberComparison_FunctionType = "Simple Number Comparison";	
//	public static final String formlet_VisibleIfListNotEmpty_FunctionType = "Visible if (other) List not empty";	
	public static final String formlet_ListBringForward_FunctionType = "List Bring Forward";
	
	/*******List Bring Forward************/
	/** HTML Tags ******/
	public static final String formletFunc_ListBF_InvokeWhen_Dropdown_Id = "/properties:0:DropDownProperty_Integer/";
	public static final String formletFunc_ListBF_SourceFormlet_TextField_Id = "/properties:1:StringProperty/";
	public static final String formletFunc_ListBF_Synchronize_Dropdown_Id = "/properties:2:DropDownProperty_Integer/";
	public static final String formletFunc_ListBF_ConditionalExpression_TextField_Id = "/properties:3:ExpressionProperty/";
	public static final String formletFunc_ListBF_SystemVariable_Dropdown_Id = "/properties:3:allSystemVariables/";
	public static final String formletFunc_ListBF_DataObjectMethod_Dropdown_Id = "/properties:3:allGridFromListDataObjectMethods/";
	/**Values **/
	public static final String[] formletFunc_ListBF_InvokeWhen_Values_Array = {"e.Form Opened for the First Time", "e.Form Opened for the First Time or when Dependent Forms Changed"};
	
	
	//********Simple Comparison Values ***************
	public static final String[] formletSimpleComparison_CompOperation_Values_Array = {"EFormField 1 value < EFormField 2 value", "EFormField 1 value <= EFormField 2 value", "EFormField 1 value = EFormField 2 value", "EFormField 1 value >= EFormField 2 value", "EFormField 1 value > EFormField 2 value",};
	
	//********Calculated Formlet User Access Grant Before or After Submission Values
	
	public static final String[] formletCalcUserAccessGrant_BeforeOrAfterSubmission_Values_Array = {"Prior to Submission", "During Amendment", "After Submission", "Always"};
	
	
	//**** Formlets HTML page fields Tags
	
	public static final String formletFunctions_FunctionType_Dropdown_Id = "/functionType_rw/";
	
//	public static final String[] formletMandFieldValid_FuncPropFields_Id = {"/properties:0:booleanProperty/"};
	
//	public static final String formletMandFieldValid_FuncProp_IgnoreHiddenFileds_Checkbox_Id = "/properties:0:booleanProperty/";
	
	//------------------------------------------
	
	public static final String[] formletSimpleComparision_FuncPropFields_Id = {"/properties:0:StringProperty/", "/properties:1:StringProperty/", "properties:2:DropDownProperty_Integer"};
	
	public static final String formletSimpleComparision_FuncProp_eFormField1_TextField_Id = "/properties:0:StringProperty/";
	
	public static final String formletSimpleComparision_FuncProp_eFormField2_TextField_Id = "/properties:1:StringProperty/";
	
	public static final String formletSimpleComparision_FuncProp_CompareOperation_Dropdown_Id = "properties:2:DropDownProperty_Integer";
	
	//-------------------------------------------
	
	public static final String[] formletVisibleIfListNotEmpty_FuncPropFields_Id = {"/properties:0:StringProperty/"};
	
	public static final String formletVisibleIfListNotEmpty_FuncProp_ListFormletToMonitor_TextField_Id = "/properties:0:StringProperty/";
	
	//---------------------------------------------
	
	public static final String[] formletCalcFormletVisibility_FuncPropField_Id = {"/properties:0:LongStringProperty/"};
	
	public static final String formletCalcFormletVisibility_FuncProp_VisibilityExp_TextField_Id = "/properties:0:LongStringProperty/";
	
	//---------------------------------------------
	
	public static final String[] formletCalcSubCondition_FuncPropFields_Id = {"/properties:0:ExpressionProperty/", "/properties:1:DropDownProperty_Integer/"};
	
	public static final String formletCalcSubCondition_FuncProp_SubConditionExp_TextField_Id = "/properties:0:ExpressionProperty/";
	
	public static final String formletCalcSubCondition_FuncProp_SubPreventedMessage_Dropdwon_Id = "/properties:1:DropDownProperty_Integer/";
	
	//-----------------------------------------------
	
	public static final String[] formletCalcUserAccessGrant_FuncPropFields_Id = {"/properties:0:ExpressionProperty/", "/properties:1:DropDownProperty_Integer/", 
																				 "/properties:3:booleanProperty/", "/properties:5:booleanProperty/", "/properties:7:booleanProperty/",
																				 "/properties:9:booleanProperty/", "/properties:11:booleanProperty/",
																				 "/properties:13:M2M_AvailableItems/","/properties:13:M2M_SelectedItems/"};
	
	public static final String formletCalcUserAccessGrant_FuncProp_Expression_TextField_Id = "/properties:0:ExpressionProperty/";
	
	public static final String formletCalcUserAccessGrant_FuncProp_BeforeOrAfterSub_DropDown_Id = "/properties:1:DropDownProperty_Integer/";
	
	public static final String formletCalcUserAccessGrant_FuncProp_ApplicantRead_Checkbox_Id = "/properties:3:booleanProperty/";
	
	public static final String formletCalcUserAccessGrant_FuncProp_AllStepStaffRead_Checkbox_Id = "/properties:5:booleanProperty/";
	
	public static final String formletCalcUserAccessGrant_FuncProp_AssignProjStepOfficersRead_Checkbox_Id = "/properties:7:booleanProperty/";
	
	public static final String formletCalcUserAccessGrant_FuncProp_AllAssignProjOfficersRead_Checkbox_Id = "/properties:9:booleanProperty/";
	
	public static final String formletCalcUserAccessGrant_FuncProp_AllProjOfficersRead_Checkbox_Id = "/properties:11:booleanProperty/";
	
	public static final String formletCalcUserAccessGrant_FuncProp_M2MAvailableGroups_SelectList_Id = "/properties:13:M2M_AvailableItems/";
	
	public static final String formletCalcUserAccessGrant_FuncProp_M2MSelectedGroups_SelectList_Id = "/properties:13:M2M_SelectedItems/";
	
	//------------------------------------------------
	
	public static final String formlet_FuncProp_SourceField_1_TextField_Id = "j_id_1v:properties:0:StringProperty";
	public static final String formlet_FuncProp_SourceField_2_TextField_Id = "j_id_1v:properties:1:StringProperty";
	public static final String formlet_FuncProp_SourceField_3_TextField_Id = "j_id_1v:properties:2:StringProperty";
	public static final String formlet_FuncProp_SourceField_4_TextField_Id = "j_id_1v:properties:3:StringProperty";
	public static final String formlet_FuncProp_SourceField_5_TextField_Id = "j_id_1v:properties:4:StringProperty";
	public static final String formlet_FuncProp_SourceField_6_TextField_Id = "j_id_1v:properties:5:StringProperty";
	public static final String formlet_FuncProp_SourceField_7_TextField_Id = "j_id_1v:properties:6:StringProperty";
	public static final String formlet_FuncProp_SourceField_8_TextField_Id = "j_id_1v:properties:7:StringProperty";
	
	//------------------- For All Formlet Calculated Function ----------
	// the HTML tags for text Area and Dropdowns
	
	public static final String formletCalculated_FuncProp_TextArea_Id = "functionDetailsForm:properties:0:ExpressionProperty";
	
	public static final String formletCalculated_FuncProp_EFormFieldRef_Dropdown_Id = "functionDetailsForm:properties:0:allFieldIdentifiers_input";
	
	public static final String formletCalculated_FuncProp_SystemVariablesRef_Dropdown_Id = "functionDetailsForm:properties:0:allSystemVariables_input";
	
	public static final String formletCalculated_FuncProp_DataObjectMethod_Dropdown_Id = "functionDetailsForm:properties:0:allGridFromListDataObjectMethods_input";
	
	//The dropdown Values
	public static final String formletCalculated_FuncProp_SystemVariablesRef_AgreementNumber = "AgreementNumber : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_AmendmentRequestAmender = "AmendmentRequestAmender : F,C,X=String, N=AccessID";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_AmendmentRequestComments = "AmendmentRequestComments : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_AmendmentRequestDate = "AmendmentRequestDate : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_AmendmentRequestReason = "AmendmentRequestReason : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_AmendmentRequestRequiredBy = "AmendmentRequestRequiredBy : F,C,X=String, N=Date";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_AmendmentRequestStep = "AmendmentRequestStep : F,C,X=String, N=StepID";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_AssociatedAwardSubmissionScheduleRow = "AssociatedAwardSubmissionScheduleRow : F=String; C,X=Number; N=RowID";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentDate = "CurrentDate : F,C,X=String; N=Date";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentDateEOD = "CurrentDateEOD : F,C,X=String; N=Date";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentDateTime = "CurrentDateTime : F,C,X=String; N=Date";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentStepName = "CurrentStepName : F,C,X=String; N=StepID";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserAddress1 = "CurrentUserAddress1 : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserAddress2 = "CurrentUserAddress2 : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserApprovalLimit = "CurrentUserApprovalLimit : F,C,X=String; N=Number";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserCity = "CurrentUserCity : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserContactNodes = "CurrentUserContactNodes : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserCountry = "CurrentUserCountry : F=String; C=Code; X=Constant; N=LookupValueID";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserDepartment = "CurrentUserDepartment : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserEmailAddress = "CurrentUserEmailAddress : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserFaxNumber = "CurrentUserFaxNumber : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserFirstName = "CurrentUserFirstName : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserLanguage = "CurrentUserLanguage : F=String; C=Code; X=Constant; N=LookupValueID";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserLastName = "CurrentUserLastName : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserMiddleName = "CurrentUserMiddleName : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserMobileNumber = "CurrentUserMobileNumber : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserOrganization = "CurrentUserOrganization : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserPagerNumber = "CurrentUserPagerNumber : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserPhoneExtension = "CurrentUserPhoneExtension : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserPhoneNumber = "CurrentUserPhoneNumber : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserPosition = "CurrentUserPosition : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserPostalCode = "CurrentUserPostalCode : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserProvinceState = "CurrentUserProvinceState : F=String; C=Code; X=Constant; N=LookupValueID";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserProvinceStateOther = "CurrentUserProvinceStateOther : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserSalutation = "CurrentUserSalutation : F=String; C=Code; X=Constant; N=LookupValueID";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_CurrentUserUserName = "CurrentUserUserName : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_FormUnderAmendment = "FormUnderAmendment : Boolean";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_FrontOfficeProjectName = "FrontOfficeProjectName : F,C,X=String; N=ProjectID";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_addDaysToDate = "G3Utils.addDaysToDate(date, numberOfDays)";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_dateAsLocalizedString = "G3Utils.dateAsLocalizedString(date)";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_getLookupValueFromConstant = "G3Utils.getLookupValueFromConstant(lookupConstant)";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_getNumberOfDaysBetweenDates = "G3Utils.getNumberOfDaysBetweenDates(date, date)";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_removeTimeComponentsFromDate = "G3Utils.removeTimeComponentsFromDate(date)";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_roundDecimals = "G3Utils.roundDecimals(value, numberOfDecimalDigits)";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_ProgramOfficeProjectName = "ProgramOfficeProjectName : F,C,X=String; N=ProjectID";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_ProjectNumber = "ProjectNumber : String";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_ProjectOrganizationName = "ProjectOrganizationName : F,C,X=String; N=OrganizationID";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_ProjectOrganizationShortName = "ProjectOrganizationShortName : F,C,X=String; N=OrganizationID";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_ReviewScore = "ReviewScore : Array of Numbers [%, Score, OutOf]";
	public static final String formletCalculated_FuncProp_SystemVariablesRef_UserLoggedIn = "UserLoggedIn : Boolean";
	
	//--Data Object Methods
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getCell_getValueAsNumber = "GridFromList.getCell(row, column).getValueAsNumber : Double";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getCell_getValueAsString = "GridFromList.getCell(row, column).getValueAsString : String";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getCell_hasValue = "GridFromList.getCell(row, column).hasValue : Boolean";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getCell_isBlankCell = "GridFromList.getCell(row, column).isBlankCell : Boolean";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getCell_isNumeric = "GridFromList.getCell(row, column).isNumeric : Boolean";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getCell_isReadOnly = "GridFromList.getCell(row, column).isReadOnly : Boolean";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getCell_isVisible = "GridFromList.getCell(row, column).isVisible : Boolean";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getDataColumn_dataColumnNumber = "GridFromList.getDataColumn(dataColumnNumber) : Integer";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getNumColumns = "GridFromList.getNumColumns : Integer";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getNumDataColumns = "GridFromList.getNumDataColumns : Integer";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getNumRows = "GridFromList.getNumRows : Integer";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getProgramFunding = "GridFromList.getProgramFunding : Double";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getTotal_Group1 = "GridFromList.getTotal_Group1 : Double";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getTotal_Group1_dataColumnNumber = "GridFromList.getTotal_Group1(dataColumnNumber) : Integer";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getTotal_Group2 = "GridFromList.getTotal_Group2 : Double";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getTotal_Group2_dataColumnNumber = "GridFromList.getTotal_Group2(dataColumnNumber) : Integer";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getTotal_Variance = "GridFromList.getTotal_Variance : Double";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getTotalColumn = "GridFromList.getTotalColumn : Integer";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getTotalRow_Group1 = "GridFromList.getTotalRow_Group1 : Integer";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getTotalRow_Group2 = "GridFromList.getTotalRow_Group2 : Integer";
	public static final String formletCalculated_FuncProp_DataObjectMethod_GridFromList_getVarianceRow = "GridFromList.getVarianceRow : Integer";
	
	//----------------End of Formlet Functions ----------------------------
	

}
