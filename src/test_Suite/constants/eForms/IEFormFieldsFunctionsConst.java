/**
 * 
 */
package test_Suite.constants.eForms;

/**
 * @author mshakshouki
 *
 */
public interface IEFormFieldsFunctionsConst {
	
	
	//********* eFormField Functions Types *********************************
	
	public static final String eFormField_BringForward_FuncType = "Bring Forward";
	
	public static final String eFormField_NumericSun_FuncType = "Numeric Sum";
	
	public static final String eFormField_SimpleUserAccessGrant_FuncType = "Simple User Access Grant";
	
	public static final String eFormField_CalcMandatory_FuncType = "Calculated Mandatory";
	
	public static final String eFormField_CalcValue_FuncType = "Calculated Value";
	
	public static final String eFormField_CalcVisibility_FuncType = "Calculated Visibility";
	
	public static final String eFormField_CalcReadOnly_FuncType = "Calculated Read Only";
	
	public static final String eFormField_CalcBringForward_FuncType = "Calculated Bring Forward";
	
	public static final String eFormField_CalcUserAccessGrant_FuncType = "Calculated User Access Grant";
	
	public static final String eFormField_GeneratePaymentSchedule_FuncType = "Generate Payment Schedule";
	
	//--------------- End of Functions Type
	
	//*** Numeric Sum This Field Values
	
	public static final String[] eFormFieldNumericSum_ThisFieldValues_Array = {"Repalce", "Include in Sum"};
	
	
//********Calculated eFormField Simple User Access Grant Before or After Submission Values
	
	public static final String[] eFormFieldUserAccessGrant_BeforeOrAfterSubmission_Values_Array = {"Prior to Submission", "During Amendment", "After Submission", "Always"};
	
	//********* Calculated Value's Invoke When Values ***********************
	
	public static final String[] eFormFieldFunction_InvokeWhen_Values_Array = {"e.Form Opened for the First Time", "e.Form Opened for the First Time, or Dependent Fields Changed", "e.Form Opened", "e.Form Opened, or Dependent Fields Changed", "Dependent Fields Changed", "e.Form Opened, for the First Time or when Dependent Forms Changed"};
	
	
	
	public static enum EInvokeWhenValues{
		formOpenedfortheFirstTime,
		formOpenedfortheFirstTimeORdependentFieldsChanged,
		formOpened,
		formOpenedORdependentFieldsChanged,
		dependentFieldsChanged,
		formOpenedfortheFirstTimeORwhenDependentFormsChanged
		
		
	}
	
	//********* eFormField's HTML Tags Id ****************************
	
	public static final String eFormField_FunctionType_DropdownField_Id = "/functionType_rw/";
	
	//----------------------------------------------------------------------------------------
	
	public static final String[] eFormFieldBringForward_FuncPropFields_Id = {"/properties:1:StringProperty/", "/properties:2:StringProperty/", "/properties:3:StringProperty/"};
	
	//public static final String eFormFieldBringForward_FunctionType_DropdownField_Id = "/functionType_rw/";
	
	public static final String eFormFieldBringForward_FuncProp_InvokeWhen_DropDown_Id = "/properties:0:DropDownProperty_Integer/";
	
	public static final String eFormFieldBringForward_FuncProp_SourceField_TextField_Id = "/properties:1:StringProperty/";
	
	public static final String eFormFieldBringForward_FuncProp_SourceGridCell_TextField_Id = "/properties:2:StringProperty/";
	
	public static final String eFormFieldBringForward_FuncProp_TargetGridCell_TextField_Id = "/properties:3:StringProperty/";
	//----------------------------------------------------------------
	
	public static final String[] eFormFieldNumericSum_FuncPropFields_Id = {"/properties:0:StringProperty/", "/properties:1:StringProperty/", "/properties:2:StringProperty/",
																		   "/properties:3:StringProperty/", "/properties:4:DropDownProperty_Integer/"};
	
	public static final String eFormFieldNumericSum_FuncProp_SourceField_1_TextField_Id = "/properties:0:StringProperty/";
	
	public static final String eFormFieldNumericSum_FuncProp_SourceField_2_TextField_Id = "/properties:1:StringProperty/";
	
	public static final String eFormFieldNumericSum_FuncProp_SourceField_3_TextField_Id = "/properties:2:StringProperty/";
	
	public static final String eFormFieldNumericSum_FuncProp_SourceField_4_TextField_Id = "/properties:3:StringProperty/";
	
	public static final String eFormFieldNumericSum_FuncProp_ThisFieldValue_Dropdown_Id = "/properties:4:DropDownProperty_Integer/";
	//-----------------------------------------------------
	
	public static final String[] eFormFieldSimpleAccessGrant_FuncPropFileds_Id = {"/properties:0:DropDownProperty_Integer/", "/properties:1:StringProperty/",
																				  "/properties:3:booleanProperty/", "/properties:4:booleanProperty/",
																				  "/properties:6:booleanProperty/", "/properties:7:booleanProperty/",
																				  "/properties:9:booleanProperty/", "/properties:10:booleanProperty/",
																				  "/properties:12:booleanProperty/", "/properties:13:booleanProperty/",
																				  "/properties:15:booleanProperty/", "/properties:16:booleanProperty/",
																				  "/properties:18:M2M_AvailableItems/", "/properties:18:M2M_SelectedItems/",
																				  "/properties:19:M2M_AvailableItems/", "/properties:19:M2M_SelectedItems/"};
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_BeforeOrAfterSub_Dropdown_Id = "/properties:0:DropDownProperty_Integer/";
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_TargetGridCell_TextField_Id = "/properties:1:StringProperty/";
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_ApplicantRead_Checkbox_Id = "/properties:3:booleanProperty/";
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_ApplicantWrite_Checkbox_Id = "/properties:4:booleanProperty/";
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_AllStepStaffRead_Checkbox_Id = "/properties:6:booleanProperty/";
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_AllStepStaffWrite_Checkbox_Id = "/properties:7:booleanProperty/";
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_AssignProjStepOfficersRead_Checkbox_Id = "/properties:9:booleanProperty/";
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_AssignProjStepOfficersWrite_Checkbox_Id = "/properties:10:booleanProperty/";
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_AllAssignedProjOfficersRead_Checkbox_Id = "/properties:12:booleanProperty/";
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_AllAssignedProjOfficersWrite_Checkbox_Id = "/properties:13:booleanProperty/";
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_AllProjOfficersRead_Checkbox_Id = "/properties:15:booleanProperty/";
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_AllProjOfficersWrite_Checkbox_Id = "/properties:16:booleanProperty/";
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_M2MReadAvailableGroups_SelectList_Id = "/properties:18:M2M_AvailableItems/";
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_M2MReadSelectedGroups_SelectList_Id = "/properties:18:M2M_SelectedItems/";
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_M2MWriteAvailableGroups_SelectList_Id = "/properties:19:M2M_AvailableItems/";
	
	public static final String eFormFieldSimpleAccessGrant_FuncProp_M2MWriteSelectedGroups_SelectList_Id = "/properties:19:M2M_SelectedItems/";
	//--------------------------------------------------------
	
	public static final String[] eFormFieldCalcValue_FuncPropFields_Id = {"/properties:0:StringProperty/","/properties:1:LongStringProperty/", "/properties:2:DropDownProperty_Integer/"};
	
	public static final String eFormFieldCalcValue_FuncProp_TargetGridCell_TextField_Id = "/properties:1:StringProperty/";
	
	public static final String eFormFieldCalcValue_FuncProp_ValueExp_TextField_Id = "/properties:2:ExpressionProperty/";
	
	public static final String eFormFieldCalcValue_FuncProp_InvokeWhen_Dropdown_Id = "/properties:0:DropDownProperty_Integer/";
	//----------------------------------------------------------
	
	public static final String[] eFormFieldCalcVisibility_FuncPropFields_Id = {"/properties:0:StringProperty/", "/properties:1:LongStringProperty/"};
	
	public static final String eFormFieldCalcVisibility_FuncProp_TargetGridCell_TextField_Id = "/properties:0:StringProperty/";
	
	public static final String eFormFieldCalcVisibility_FuncProp_VisibilityExp_TextField_Id = "/ExpressionProperty/";
	//----------------------------------------------------------
	
	public static final String[] eFormFieldCalcReadOnly_FuncPropFields_Id = {"/properties:0:StringProperty/", "/properties:1:LongStringProperty/"};
	
	public static final String eFormFieldCalcReadOnly_FuncProp_TargetGridCell_TextField_Id = "/properties:0:StringProperty/";
	
	public static final String eFormFieldCalcReadOnly_FuncProp_ReadOnlyExp_TextField_Id = "/properties:1:ExpressionProperty/";
	//--------------------------------------------------------
	
	public static final String[] eFormFieldCalcBringForward_FuncPropFields_Id = {"/properties:0:StringProperty/", "/properties:1:LongStringProperty/"};
	
	public static final String eFormFieldCalcBringForward_FuncProp_TargetGridCell_TextField_Id = "/properties:0:StringProperty/";
	
	public static final String eFormFieldCalcBringForward_FuncProp_BringForwardExp_TextField_Id = "/properties:1:LongStringProperty/";
	//--------------------------------------------------------
	
	public static final String[] eFormFieldCalcAccessGrant_FuncPropFields_Id = {"/properties:0:LongStringProperty/", "/properties:1:DropDownProperty_Integer/", "/properties:2:StringProperty/",
																				"/properties:4:booleanProperty/", "/properties:5:booleanProperty/",
																				"/properties:7:booleanProperty/", "/properties:8:booleanProperty/",
																				"/properties:10:booleanProperty/", "/properties:11:booleanProperty/",
																				"/properties:13:booleanProperty/", "/properties:14:booleanProperty/",
																				"/properties:16:booleanProperty/", "/properties:17:booleanProperty/",
																				"/properties:19:M2M_AvailableItems/", "/properties:19:M2M_SelectedItems/",
																				"/properties:20:M2M_AvailableItems/", "/properties:20:M2M_SelectedItems/"};
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_Expression_TextField_Id = "/properties:0:ExpressionProperty/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_BeforeOrAfterSub_Dropdown_Id = "/DropDownProperty_Integer/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_TargetGridCell_TextField_Id = "/StringProperty/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_Applicant_Checkbox_Id = ":booleanProperty/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_ApplicantWrite_Checkbox_Id = ":booleanProperty/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_AllStepStaff_Checkbox_Id = ":booleanProperty/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_AllStepStaffWrite_Checkbox_Id = ":booleanProperty/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_AssignProjStepOfficers_Checkbox_Id = ":booleanProperty/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_AssignProjStepOfficersWrite_Checkbox_Id = ":booleanProperty/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_AllAssignedProjOfficers_Checkbox_Id = ":booleanProperty/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_AllAssignedProjOfficersWrite_Checkbox_Id = ":booleanProperty/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_AllProjOfficers_Checkbox_Id = ":booleanProperty/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_AllProjOfficersWrite_Checkbox_Id = ":booleanProperty/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_M2MReadAvailableGroups_SelectList_Id = ":M2M_AvailableItems/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_M2MReadSelectedGroups_SelectList_Id = ":M2M_SelectedItems/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_M2MWriteAvailableGroups_SelectList_Id = ":M2M_AvailableItems/";
	
	public static final String eFormFieldCalcAccessGrant_FuncProp_M2MWriteSelectedGroups_SelectList_Id = ":M2M_SelectedItems/";
	//--------------------------------------------------------------
	
	public static final String[] eFormFieldGeneratePaymentSchedule_FuncPropFields_Id = {"/properties:0:StringProperty/","/properties:1:StringProperty/",
																					    "/properties:2:StringProperty/","/properties:3:StringProperty/"};
	
	public static final String eFormFieldGeneratePaymentSchedule_FuncProp_ButtonId_TextField_Id = "/properties:0:StringProperty/";
	
	public static final String eFormFieldGeneratePaymentSchedule_FuncProp_MilestoneName_TextField_Id = "/properties:1:StringProperty/";
	
	public static final String eFormFieldGeneratePaymentSchedule_FuncProp_MilestoneStartDate_TextField_Id = "/properties:2:StringProperty/";
	
	public static final String eFormFieldGeneratePaymentSchedule_FuncProp_MilestoneEndDate_TextField_Id = "/properties:3:StringProperty/";
	//----------------------------------------------------	
	
	public static final String eFormFieldCalculatedFunctions_EFormFieldReference_DropDown_Id = "/allFieldIdentifiers/";
	
	public static final String eFormFieldCalculatedFunctions_SystemVariableReference_DropDown_Id = "/allSystemVariables/";
	
	public static final String eFormFieldCalculatedFunctions_DataObjectMethod_DropDown_Id = "/allGridFromListDataObjectMethods/";


}
