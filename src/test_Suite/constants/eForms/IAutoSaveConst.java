/**
 * 
 */
package test_Suite.constants.eForms;

/**
 * @author mshakshouki
 *
 */
public interface IAutoSaveConst {

	/**** FOPP preFixes *********/

	public static final String autoSave_Fopp_name = "FOPP";
	public static final String autoSave_FoppPreFix = "-AutoSave-";
	public static final String autoSave_PA_Fopp_PreFix = "-AutoSave-PA-";
	public static final String autoSave_FOPP_PostFix = "-Validation";
	public static final String autoSave_Proj_Prefix = "autoSave.";
	
	public static enum EFieldType {child,dropdown,dropdownFromList,dropdownFromProfile,radio,checkbox};
	
	public static final String[] fieldsType = {
		IeFormFieldsConst.eFormFieldType_ChildDropdownField_Name,
		IeFormFieldsConst.eFormFieldType_DropdownField_Name,
		IeFormFieldsConst.eFormFieldType_DropdownFromListField_Name,
		IeFormFieldsConst.eFormFieldType_DropdownFromList_FromApplicantFormField_Name,
		IeFormFieldsConst.eFormFieldType_RadioButton_Name,
		IeFormFieldsConst.eFormFieldType_CheckboxField_Name};
	
	public static enum EFormletType {child,dropdown,dropdownFromList,dropdownFromProfile,radio,checkboxVisible,checkboxRead};
	
	public static final String[] formletName = {
		"Child Dropdown ",
		"Dropdown ",
		"Dropdown From List ",
		"Dropdown From Applicant Profile ",
		"Radio Button ",
		"Checkbox Visibility ",
		"Checkbox Readability "
	};

	public String[] formleTitlePostFix = {" Dropdown", " Checkbox", " Radio Button"};
	public String[] formleIdentPostFix = {"-Dropdown", "-Checkbox", "-Radio-Button"};

	public String[] subFormleTitlePostFix = {" Sub eList Dropdown", " Sub eList Checkbox", " Sub eList Radio Button"};
	public String[] subFormleIdentPostFix = {"-Sub-eList-Dropdown", "-Sub-eList-Checkbox","-Sub-eList-Radio-Button"};
	
	public enum EFormletsPostfix { dropdown, checkbox, Radio};
	
	public static final String parentLookup_Name = "Auto Save Parent";
	public static final String childLookup_Name = "Auto Save Child";
	
	public static final String projPostsFix[] = {
		"-Changing-Field-Status",
		"-Changing-eLists-Fields-Status",
		"-Changing-eLists-Status",
		"-Changing-Formlet-Status",
		"-Field",
		"-eLists-Field"};
	
	public enum EProjPostfix { field, eListField, eList,formlet};
	
	public static final String[] selectableValues = {
		"Invisible",
		"Writable",
		"ReadOnly",
		"Visible",
		"true",
		"false"};
	
	public static enum eSelectable { inivisible,writable,readOnly,visible,True,False};

	/********* Auto-Save Steps Arrays *****************/

	public static final String[] autoSave_ProfileEFormSource = {
			"Applicant Profile", "AutoSave Applicant Profile",
			"AutoSave-Applicant-Profile" };

	public static final String[][] autoSave_SubmissionField = {
			{ "Submission", "LBF Applicant Submission Source eLists",
					"Applicant Submission", "true", "No" },
			{ "LBF-Applicant Submission-Source-eLists",
					"LBF Applicant Submission Source eLists" } };
	
	

}
