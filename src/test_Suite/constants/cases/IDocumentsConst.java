/**
 * 
 */
package test_Suite.constants.cases;

/**
 * @author mshakshouki
 *
 */

public interface IDocumentsConst {

	
	public static final String document_Doc_Identifier_TxtFld = "editDocForm:documentIdentifier";
	
	public static final String document_Doc_Type_DDFld = "editDocForm:documentType_input";
	
	public static final String document_Doc_Desc_TxtFld = "editDocForm:documentDescription";
	
	public static final String document_Doc_File_fileFld = "editDocForm:documentFile";
	
	public static final String document_Doc_PrimOrg_DDFld = "editDocForm:primaryOrganization_input";
	
	public static final String document_Doc_OrgAccess_DDFld = "editDocForm:organizationAccess_input";
	
	public static final String document_Doc_LocaleName0_TxtFld = "editDocForm:locales:0:documentTitleText";
	
	public static final String document_Doc_LocaleName1_TxtFld = "editDocForm:locales:1:documentTitleText";
	
	public static final String document_Doc_LocaleName2_TxtFld = "editDocForm:locales:2:documentTitleText";
	
	public static final String document_Doc_LocaleName3_TxtFld = "editDocForm:locales:3:documentTitleText";
	
	public static final String[] document_DocTypes = {"FAQ", "GuideLines", "Costing Memorandums", "Instructions"};
	
	public static final String document_DocFiles[] = {"FAQ.doc", "Guidelines.doc", "CostingMemorandums.doc", "Instructions.doc"};
	
	public static final String document_DocFilesName[] = {"FAQ_Name", "Guidelines_Name", "CostingMemorandums_Name", "Instructions_Name"};
	
	public static final String document_DocFilesIdent[] = {"FAQ_Ident", "Guidelines_Ident", "CostingMemorandums_Ident", "Instructions_Ident"};
	
	public static final String document_Doc_Init = "-Gnrl-Doc-";
	
	public static final String document_Doc_FilesPath = "src\\test_Suite\\xml_Files\\Documents\\";
}
