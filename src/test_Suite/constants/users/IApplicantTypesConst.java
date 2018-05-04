/**
 * 
 */
package test_Suite.constants.users;

/**
 * @author mshakshouki
 *
 */
public interface IApplicantTypesConst {
	
	public static enum EAppCategories {
		regular, parentApplicant, selfRepresented
	}
	
	public static final String applicantCategories[] = {"Regular","Parent Applicant","Self Represented"};
	
	public static final String appTypeIdent_TxtFld = "applicantTypeForm:identifier_text";
	public static final String appCategory_DrpDown = "applicantTypeForm:disable_checkbox_selfRep_input";
	
	public static final String appTypeLocales_DivId = "applicantTypeForm:localizedApplicantTypeValuesTable_content";
	
	public static final String appTypeActive_ChkBox = "applicantTypeForm:disable_checkbox_input";
	
	public static final String appTypesImport_Title = "Import Applicant Types";
	
	public static final String appTypesExport_Title = "Export Applicant Types";
	
	public static final String appTypeAddNew_Title = "Add Applicant Type";
	
	public static final String appTypeFO_DrpDown = "WelcomeWorkspace:form:applicantType";
	
	public static final String appTypePO_DrpDown = "editApplicant:editAppForm:applicantType";
	
	
	
	
}
