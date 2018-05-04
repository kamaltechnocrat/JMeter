/**
 * 
 */
package test_Suite.constants.eForms;

/**
 * @author mshakshouki
 *
 */
public interface IeFormFieldsConst {
	
	//Holds eForm Field Details Page HTML Tag Id, eForm Field Type Names, eForm Fields Property
	
	
	//************ eFormField Types	
	
	public static final String eFormFieldType_ApprovalDropdownField_Name = "Approval Dropdown";
	
	public static final String eFormFieldType_ButtonField_Name = "Button";
	
	public static final String eFormFieldType_CheckboxField_Name = "Checkbox";
	
	public static final String eFormFieldType_ChildDropdownField_Name = "Child Dropdown";
	
	public static final String eFormFieldType_COIField_Name = "Conflict of Interest Selector";
	
	public static final String eFormFieldType_DataGridField_Name = "Data-Grid";
	
	public static final String eFormFieldType_DateField_Name = "Date";
	
	public static final String eFormFieldType_DropdownField_Name = "Dropdown";
	
	public static final String eFormFieldType_DropdownFromListField_Name = "Dropdown From List";
	
	public static final String eFormFieldType_DropdownFromList_FromApplicantFormField_Name =  "Dropdown From List (Text Value from Applicant Profile)";
	
	public static final String eFormFieldType_EINNumber_Name = "EIN Number";
	
	public static final String eFormFieldType_EmailAddressField_Name = "Email Address";
	
	public static final String eFormFieldType_InstructionsField_Name = "Instructions";
	
	public static final String eFormFieldType_LableOnly_Name = "Label Only";
	
	public static final String eFormFieldType_LongTextField_Name = "Long Text";
	
	public static final String eFormFieldType_ManyToManyField_Name = "Many-to-Many";
	
	public static final String eFormFieldType_NumericField_Name = "Numeric";
	
	public static final String eFormFieldType_NumericDataGridField_Name = "Numeric Data-Grid";
	
	public static final String eFormFieldType_PasswordField_Name = "Password";
	
	public static final String eFormFieldType_PhoneNumberField_Name = "Phone Number";
	
	public static final String eFormFieldType_PostalCodeField_Name = "Postal Code";	
	
	public static final String eFormFieldType_ReadOnlyField_Name = "Read-Only Text";
	
	public static final String eFormFieldType_ReviewScoreField_Name = "Review Score";
	
	public static final String eFormFieldType_ShortTextField_Name = "Short Text";
	
	public static final String eFormFieldType_TextDataGridField_Name = "Text Data-Grid";
	
	public static final String eFormFieldType_WebAddressField_Name = "Web Address";	
	
	public static final String eFormFieldType_RadioButton_Name = "Radio Button";
	
	public static final String eFormFieldType_GridViewOFListFields_Name = "Grid View of List Fields";	
	
	//******** HTML page fields Tag IDs *********************
	
//	public static final String eFormField_FormletIndentfier_SelectList_Id = "main:EFormFieldDetails:eformfielddefinition:formletIdentifier";
	
	public static final String eFormFieldIdentifier_TextField_Id = "/fieldIdentifier_rw/";
	
	public static final String eFormFieldType_SelectList_Id = "/fieldType_rw/";
	
	public static final String eFormFieldMandatory_Checkbox_Id = "/mandatory/";
	
	public static final String eFormFieldInListColumn_Checkbox_Id = "main:EFormFieldDetails:eformfielddefinition:listColumn";
	
	public static final String eFormFieldIncludeAsELF_Checkbox_Id = "main:EFormFieldDetails:eformfielddefinition:listFilter";
	
	public static final String lineSpacingAboveEformField_SelectList_Id = "/spacingAboveEFormField/";
	
	public static final String eFormFieldMinTextLength_NumericField_Id = "/properties:0:IntProperty/";
	
	public static final String eFormFieldMaxTextLength_NumericField_Id = "/properties:1:IntProperty/";
	
	public static final String eFormFieldQuestionLabel_LongField_Id = "label";
	
	public static final String eFormFieldDescription_LongField_Id = "description";
	
	public static final String eFormFieldPopupHelpText_LongField_Id = "toolTipText";
	
	public static final String eFormFieldColumnTitleText_TextField_Id = "columnTitle";
	
	public static final String eFormFieldEmptyColumnText_TextField_Id = "emptyColumnText";
	
	public static final String eFormFieldProperties_Span_Id = "eformfielddefinition:fieldProperties";
	
	
	//************ Label Only Field Properties *************
	
	public static final String[] eFormField_WithStyleOnlyPropFields_Id = {"/properties:0:DropDownProperty_Integer/","/properties:1:DropDownProperty_Integer/"};
	
	//-----------------------------------------------
	
	//************ Numeric Field Properties *************
	
	public static final String[] eFormField_NumericPropFields_Id = {"/properties:0:DoubleProperty/", "/properties:1:DoubleProperty/",
																	"/properties:2:IntProperty/", "/properties:3:BooleanProperty/",
																	"/properties:4:DropDownProperty_Integer/","/properties:5:DropDownProperty_Integer/"};
	public static final String [] decoration= {"None","Currency","Percent"};
	
//	public static final String [] eFormField_Numericfield_ID={"/:4:DropDownProperty_Integer/","main:EFormFieldDetails:eformfielddefinition:properties:5:DropDownProperty_Integer" };
	
	
	//*************Numeric Field Constant Values************
	
	
	public static final String eFormField_CurrencySymbol_Value[] = { "$", "USD", "EUR" };
	public static final String eFormField_Value[] = { "$1001.00", "USD1002.00", "EUR1003.00",
		                                               "$1,004.00","$1,005.00","$1,006.00",
		                                               "$1,007.00","$1,008.00","$1,009.00",
		                                               "$1,010.00","$1,011.00","$1,012.00", };
	
    public static final String eFormField_Numericfieldlabel_Value []= {"Numeric1","Numeric2","Numeric3"};
	public static final String eFormFiled_Numeric_fieldIds []={"g3-form:eFormFieldList:0:textBox",
		"g3-form:eFormFieldList:1:textBox",
		"g3-form:eFormFieldList:2:textBox",
		"g3-form:eFormFieldList:3:dataGrid:0:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId).concat(":0:gridCell_NumericEntry"),
        "g3-form:eFormFieldList:3:dataGrid:0:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId).concat(":1:gridCell_NumericEntry"),
        "g3-form:eFormFieldList:3:dataGrid:0:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId).concat(":2:gridCell_NumericEntry"),
        "g3-form:eFormFieldList:3:dataGrid:1:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId).concat(":0:gridCell_NumericEntry"),
        "g3-form:eFormFieldList:3:dataGrid:1:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId).concat(":1:gridCell_NumericEntry"),
        "g3-form:eFormFieldList:3:dataGrid:1:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId).concat(":2:gridCell_NumericEntry"),
        "g3-form:eFormFieldList:3:dataGrid:2:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId).concat(":0:gridCell_NumericEntry"),
        "g3-form:eFormFieldList:3:dataGrid:2:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId).concat(":1:gridCell_NumericEntry"),
        "g3-form:eFormFieldList:3:dataGrid:2:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId).concat(":2:gridCell_NumericEntry")};
	
	public static final String [] EformName={"MultipleCurrency","Test-MultiCurrency-NumericFieldValues"};
	public static final String eformType= "Applicant Submission";
	
	//*********** DataGrid Constant Values***********************************
	
	public static final String DataGrid_MiddleOf_TagId="_idJsp278";
	
	public static final String DataGrids_NumericCell_A1_Id="g3-form:eFormFieldList:3:dataGrid:0:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId).concat(":0_0:gridCell_NumericEntry");
	public static final String DataGrids_NumericCell_B1_Id="g3-form:eFormFieldList:3:dataGrid:0:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId).concat(":1_1:gridCell_NumericEntry");
	public static final String DataGrids_NumericCell_C1_Id="g3-form:eFormFieldList:3:dataGrid:0:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId).concat(":2_2:gridCell_NumericEntry");
	
	public static final String DataGrids_NumericCell_A2_Id="g3-form:eFormFieldList:3:dataGrid:1:".concat("ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId").concat(":0_0:gridCell_NumericEntry");
	public static final String DataGrids_NumericCell_B2_Id="g3-form:eFormFieldList:3:dataGrid:1:".concat("ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId").concat(":1_1:gridCell_NumericEntry");
	public static final String DataGrids_NumericCell_C2_Id="g3-form:eFormFieldList:3:dataGrid:1:".concat("ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId").concat(":2_2:gridCell_NumericEntry");
	
	public static final String DataGrids_NumericCell_A3_Id="g3-form:eFormFieldList:3:dataGrid:2:".concat("ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId").concat(":0_0:gridCell_NumericEntry");
	public static final String DataGrids_NumericCell_B3_Id="g3-form:eFormFieldList:3:dataGrid:2:".concat("ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId").concat(":1_1:gridCell_NumericEntry");
	public static final String DataGrids_NumericCell_C3_Id="g3-form:eFormFieldList:3:dataGrid:2:".concat("ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId").concat(":2_2:gridCell_NumericEntry");
	//---------------------------------------
	
	//************ Short Text Field Properties *************
	
	public static final String[] eFormField_ShortTextPropFields_Id = {"/properties:0:IntProperty/", "/properties:1:IntProperty/"};
	//--------------------------------------------------------
	
	//************ Long Text Field Properties *************

	public static final String[] eFormField_LongTextPropFields_Id = {"/properties:0:IntProperty/", "/properties:1:IntProperty/",
																	 "/properties:2:IntProperty/", "/properties:3:DropDownProperty_Integer/"};
	//--------------------------------------------------
	
	//************ Read-Only Field Properties *************
	
	public static final String[] eFormField_ReadOnlyPropFields_Id = {"/properties:0:DropDownProperty_Integer/"};
	//--------------------------------------------------
	
	//************ Dropdown Field Properties *************
	
	public static final String[] eFormField_DropdownPropFields_Id = {"/properties:0:DropDownProperty_Integer/", "/properties:1:DropDownProperty_Integer/", "/properties:2:DropDownProperty_Integer/","/properties:0:StringProperty/"};
	//--------------------------------------------------
	
	//************ Dropdown From List Field Properties *************
	
	public static final String[] eFormField_DropdownFromListPropFields_Id = {"/properties:0:StringProperty/", "/properties:1:DropDownProperty_Integer/","/properties:2:DropDownProperty_Integer/"};
	//--------------------------------------------------
	
	//************ Dropdown From List (Text Value fron Applicant Form) Field Properties *************
	public static final String[] eFormField_DropdownFromListFromApplicantFormPropFields_Id = {"/properties:0:StringProperty/", "/properties:0:booleanProperty/"};
	//--------------------------------------------------
	
	//************ Many-to-Many Field Properties *************
	
	public static final String[] eFormField_M2MPropFields_Id = {"/properties:0:DropDownProperty_Integer/", "/properties:1:DropDownProperty_Integer/","/properties:2:DropDownProperty_Integer/"};
	//--------------------------------------------------
	
	//************ Checkbox Field Properties *************
	
	public static final String eFormField_CheckboxPropField_Id = "/properties:0:DropDownProperty_Integer/";
	
	//--------------------------------------------------
	
	//************ Radio Button Field Properties *************
	
	public static final String[] eFormField_RadioButtonPropField_Id = {"/properties:0:DropDownProperty_Integer/","/properties:1:DropDownProperty_Integer/"};
	
	//***********Review Score Field Properties****************
	
	public static final String[] eFormField_ReviewScorePropFields_Id = {"/properties:0:DoubleProperty/", "/properties:1:DoubleProperty/",
																		"/properties:2:IntProperty/", "/properties:3:BooleanProperty/",
																		"/properties:4:DoubleProperty/"};
	
	public static final String reviewScoreProperty_MinValue_DoubleField_Id = "/properties:0:DoubleProperty/";
	
	public static final String reviewScoreProperty_MaxValue_DoubleField_Id = "/properties:1:DoubleProperty/";
	
	public static final String reviewScoreProperty_NumOfDecimal_IntegerField_Id = "/properties:2:IntProperty/";
	
	public static final String reviewScoreProperty_DigitGrouping_CheckBox_Id = "/properties:3:BooleanProperty/";
	
	public static final String reviewScoreProperty_Multiplier_DoubleField_Id = "/properties:4:DoubleProperty/";	
	//--------------------------------------------------
	
	//***********Phone Number Field Properties****************

	public static final String[] eFormField_PhoneNumberPropFields_Id = {"/properties:0:DropDownProperty_Integer/", "/properties:1:DropDownProperty_Integer/"};
	
	public static final String[] eFormField_PhoneNumber_TypesValues = {"North American only","UK only","International only","Any"};
	
	public static final String[] eFormField_PhoneNumber_NorthAmericanDisplayStyles = {"(xxx) xxx-xxxx","xxx-xxx-xxxx","xxx.xxx.xxxx"};
	//--------------------------------------------------
	
	//***********Postal Code Field Properties****************
	public static final String[] eFormField_PostalCodePropFields_Id = {"/properties:0:DropDownProperty_Integer/", "/properties:1:DropDownProperty_Integer/",
																		"/properties:2:DropDownProperty_Integer/"};
	
	public static final String[] eFormField_PostalCode_TypesValues = {"Canadians Postal Codes","U.S. ZIP Codes","Both"};
	
	public static final String[] eFormField_PostalCode_CanadiansDisplayCodes = {"X0X 0X0","X0X-0X0"};
	
	public static final String[] eFormField_PostalCode_USDisplayCodes = {"12345-1234","12345"};
	//--------------------------------------------------
	
	//***********Password Field Properties****************

	public static final String[] eFormField_PasswordPropFields_Id = {"/properties:0:IntProperty/", "/properties:1:IntProperty/"};
	//--------------------------------------------------
	
	//************ Instructions Field Properties *************
	
	public static final String[] eFormField_InstructionsPropFields_Id = {"/properties:0:DropDownProperty_Integer/"};
	//--------------------------------------------------
	
	//************ Data-Grid Field Properties *************
	public static final String[] eFormField_DataGridPropFields_Id = {"/properties:0:booleanProperty/", "/properties:1:booleanProperty/"};

	//--------------------------------------------------
	
	//***********Button Field Properties****************

	public static final String[] eFormField_ButtonPropFields_Id = {"/properties:0:DropDownProperty_Integer/", "/properties:1:DropDownProperty_Integer/"};
	//------------------------------------------------
	
	//************ Grid view of List Field Properties *********************
	
	public static final String[] eFormField_GridViewOfListPropFields_Id = {"/properties:0:IntProperty/", "/properties:1:StringProperty/",
																			"/properties:4:DropDownProperty_Integer/", "/properties:5:StringProperty/",
																			"/properties:6:StringProperty/", "/properties:7:StringProperty/",
																			"/properties:10:DropDownProperty_Integer/", "/properties:11:StringProperty/",
																			"/properties:12:StringProperty/", "/properties:13:StringProperty/",
																			"/properties:15:DropDownProperty_Integer/", "/properties:16:DropDownProperty_Integer/",
																			"/properties:18:booleanProperty/", "/properties:19:DropDownProperty_Integer/",
																			"/properties:21:booleanProperty/", "/properties:22:DropDownProperty_Integer/",
																			"/properties:24:booleanProperty/", "/properties:25:DropDownProperty_Integer/",
																			"/properties:27:booleanProperty/", "/properties:28:DropDownProperty_Integer/"};
	
	public static final String[] eFormField_EFormListColumnsPropFields_Id = {"/listColumn/", ":columnTitle/", ":emptyColumnText/"};
	//-------------------------------------------------------------------------------------------------------------------------------------------------------
	
//************ Data-Grid Filed Properties and Configure *************************
	
//	public static final String eFormField_DataGrid_ConfigureGrid_Table_Id = "main:EFormFieldDetails_ConfigureGrid:eformfielddetails_configureGrid:gridEditor";
	
	public static final String eFormField_DataGrid_ConfigureGrid_TableBody_Id = "main:EFormFieldDetails_ConfigureGrid:eformfielddetails_configureGrid:gridEditor:tbody_element";
	
	public static final String eFormField_DataGrid_treatFirstRowForPDFExportField_Id = "/properties:0:booleanProperty/";
	
	public static final String eFormField_DataGrid_treatFirstColumnForPDFExportField_Id = "/properties:1:booleanProperty/";
	
	public static final String eFormField_DataGrid_Configure_CellType_DrpDownField_Id = "/configureGrid:cellType/";
	
	public static final String eFormField_DataGrid_Configure_ClearSelection_CheckBoxField_Id = "/autoClearCellSelection/";
	
	public static final String eFormField_DataGrid_Configure_NumberOfColumnsField_Id = "/numColumnsToInsert/";
	
	public static final String eFormField_DataGrid_Configure_NumberOfRowsField_Id= "/numRowsToInsert/";
	
	public static final String eFormField_DataGrid_Configure_InsertColumns_PlusAlt = "Insert columns at far right";
	
	public static final String eFormField_DataGrid_Configure_InsertRows_PlusAlt = "Insert rows at bottom";
	
	public static final String eFormField_DataGrid_Configure_decoration="/numeric_decoration/";
	
	public static final String eFormField_DataGrid_Configure_Currency_Symbol_Id= "/currency_symbol/";
	public static enum EeFormField_DataGrid_Configure_SelectionButtonsValue {
		selectNone,
		selectAll,
		selectRange
	}
	
	public static final String m2mAvailableField_ID= "g3-form:eFormFieldList:6:M2M_AvailableItems_h";
	
	public static final String [] eFormField_IDs ={"g3-form:eFormFieldList:0:numericDropdown_input",
		"g3-form:eFormFieldList:1:booleanProperty_input",
		"/:0:gridCell_TextEntry/",
        "/:1:gridCell_TextEntry/",
        "g3-form:eFormFieldList:3:datePicker_input",
        "g3-form:eFormFieldList:4:numericDropdown_input",
        "g3-form:eFormFieldList:5:textArea_Below",
        "g3-form:eFormFieldList:6:M2M_SelectedItems_h",
        "g3-form:eFormFieldList:7:textBox",
        "g3-form:eFormFieldList:8:radioButtonSet:1",
        "g3-form:eFormFieldList:9:textBox",
        "g3-form:eFormFieldList:6:textArea_Below"};
	
	public static final String[] appProfileField_IDs={"g3-form:eFormFieldList:0:textBox",
		"g3-form:eFormFieldList:1:textBox",
		"g3-form:eFormFieldList:2:dataGrid:0:j_id_ap:0:gridCell_TextEntry",
		"g3-form:eFormFieldList:3:numericDropdown_input",
		"g3-form:eFormFieldList:4:textBox",
		"g3-form:eFormFieldList:5:numericDropdown_input",
		"g3-form:eFormFieldList:6:booleanProperty_input",
		"g3-form:eFormFieldList:7:textBox",
		"g3-form:eFormFieldList:8:booleanProperty_input",
		"g3-form:eFormFieldList:9:textBox",
		"g3-form:eFormFieldList:10:booleanProperty_input",
		"g3-form:eFormFieldList:11:textBox"};
	
	public static final String [] submissionFormFields_ID={"g3-form:eFormFieldList:0:textBox","g3-form:eFormFieldList:1:textBox","g3-form:eFormFieldList:2:textBox",
		                                                   "g3-form:eFormFieldList:3:textBox","g3-form:eFormFieldList:4:textBox","g3-form:eFormFieldList:5:textBox",
		                                                   "g3-form:eFormFieldList:6:textBox","g3-form:eFormFieldList:7:textBox","g3-form:eFormFieldList:8:textBox",
		                                                   "g3-form:eFormFieldList:9:textBox","g3-form:eFormFieldList:10:textBox","g3-form:eFormFieldList:11:textBox"};
	public static final String [] appSubmissionField_values={"1234","abcd","pqrs","Mail","abcdef","Site Visit","true","efgh","true","4567","true","wxyz"};
	
	public static final String [] appProfileFieldDescription_IDs={"g3-form:eFormFieldList:4:fieldDescriptionBelow","g3-form:eFormFieldList:5:fieldDescriptionBelow"};
	public static final String [] appProfileMandatoryField_IDs={"g3-form:eFormFieldList:6:labelFieldBeside","g3-form:eFormFieldList:7:labelFieldBeside"};
	public static final String [] appSubmissionValidateField_values={"1,234.00","","pqrs","Mail","abcdef","Site Visit"};
	
	public static final String adminFormfield_ID="g3-form:eFormFieldList:0:textBox";
	
	public static final String [] budgetFormField_IDs= {"g3-form:eFormFieldList:0:textBox","g3-form:eFormFieldList:1:textBox"};
	
	public static final String [] projectFormField_IDs= {"g3-form:eFormFieldList:0:textBox","g3-form:eFormFieldList:1:textBox"};
	
	public static final String []budgetFormfield_Values={"111-111-1111","a@b.com"};
	
	public static final String []projectFormfield_Values={"a1a1a1","www.aaa.com"};
	
	public static final String []projectFormValidatefield_Values={"A1A 1A1","http://www.aaa.com"};
	
	public static final String []adminFormfield_Values={"5","5.00"};
	
	public static final String [] organizationFormField_IDs= {"g3-form:eFormFieldList:0:numericDropdown_input","g3-form:eFormFieldList:1:numericChildDropdown_input","g3-form:eFormFieldList:2:textBox"};
	
	public  static final String slected_Items="g3-form:eFormFieldList:6:M2M_SelectedItems_h";
	
	public static final String available_Items="g3-form:eFormFieldList:6:M2M_AvailableItems_h";

   public static final String [] eFormField_Values={"true","abcd","pqrs","Alaska","abcdefghijklmnopqrstuvwxyz","1234","Yes","abcd"};

   public static final String [] approvalDropDown_Values={"--select--","Approved","Pending Approval","Rejected"};

   public static final String [] dropdown_Values={"E-mail","Mail","Site Visit"};
   
   public static final String [] childDropdown_Values={"Yellow","Light Grey","Very Light Grey"};
   
   public static final String [] organizationFormField_Value={"Mail","Light Grey","11-1111111"};

   public static final String [] AppeFormField_Values={"Rejected","true","abcd","pqrs","","Mail","abcdefghijklmnopqrstuvwxyz","Alaska","1,234.00","Yes","","Alaska"};//
}
