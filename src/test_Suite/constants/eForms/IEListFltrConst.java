/**
 * 
 */
package test_Suite.constants.eForms;

/**
 * @author mshakshouki
 *
 */
public interface IEListFltrConst {

	/**** FOPP preFixes *********/

	public static final String lbf_Fopp_name = "FOPP";
	public static final String lbf_FoppPreFix = "-ELF-";
	public static final String lbf_PA_Fopp_PreFix = "-ELF-PA-";
	public static final String lbf_FOPP_PostFix = "eLists";
	public static final String lbf_Proj_Prefix = "elf.";
	
	public static enum ENonELF_FieldsTypes {
		BUTTON, DATAGRID, INSTRUCTION, LABEL,NUMERIC_DG, TEXT_DG
	}
	
	public static String[] non_elfFieldTypes = {
		IeFormFieldsConst.eFormFieldType_ButtonField_Name,
		IeFormFieldsConst.eFormFieldType_DataGridField_Name,
		IeFormFieldsConst.eFormFieldType_InstructionsField_Name,
		IeFormFieldsConst.eFormFieldType_LableOnly_Name,
		IeFormFieldsConst.eFormFieldType_NumericDataGridField_Name,
		IeFormFieldsConst.eFormFieldType_TextDataGridField_Name
	};
	
	public static String[] some_elfFieldTypes = {
		IeFormFieldsConst.eFormFieldType_ApprovalDropdownField_Name,
		
		IeFormFieldsConst.eFormFieldType_CheckboxField_Name,
		IeFormFieldsConst.eFormFieldType_DateField_Name,
		
		IeFormFieldsConst.eFormFieldType_EINNumber_Name,
		IeFormFieldsConst.eFormFieldType_PhoneNumberField_Name,
		IeFormFieldsConst.eFormFieldType_PostalCodeField_Name,
		
		IeFormFieldsConst.eFormFieldType_ReadOnlyField_Name,
		IeFormFieldsConst.eFormFieldType_LongTextField_Name,
		IeFormFieldsConst.eFormFieldType_NumericField_Name,
		IeFormFieldsConst.eFormFieldType_ShortTextField_Name,
		
		IeFormFieldsConst.eFormFieldType_EmailAddressField_Name,
		IeFormFieldsConst.eFormFieldType_WebAddressField_Name,
	};
	
	public static String[] some_elfFieldTypes_Props = {
		IeFormFieldsConst.eFormFieldType_DropdownField_Name,
		IeFormFieldsConst.eFormFieldType_ChildDropdownField_Name,
		
		IeFormFieldsConst.eFormFieldType_DropdownFromList_FromApplicantFormField_Name,
		//IeFormFieldsConst.eFormFieldType_DropdownFromListField_Name,
		
		IeFormFieldsConst.eFormFieldType_ManyToManyField_Name,
		IeFormFieldsConst.eFormFieldType_RadioButton_Name
	};
	
	public static String[][] elfFieldTypes_Props = {
		{"Activity Types", "No"},
		{"Dropdown-ELF-", "No"},
		{"Contact-Info", "No"},
		{"Colors", "Vertical"},
		{"Target Step Names", "No"}
	};

}
