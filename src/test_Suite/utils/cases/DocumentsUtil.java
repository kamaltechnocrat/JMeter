package test_Suite.utils.cases;

import static watij.finders.FinderFactory.attribute;
import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.text;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test_Suite.constants.cases.IDocumentsConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.cases.Documents;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.elements.Div;
import watij.elements.Span;
import watij.elements.Spans;
import watij.runtime.ie.IE;

public class DocumentsUtil {

	private static Log log = LogFactory.getLog(DocumentsUtil.class);

	private static boolean retValue;

	private static int rowIndx;
	private static Spans spans;

	private static ArrayList<String> errorSmalls;
	
	public static boolean saveDownloadDocument(Documents docs) throws Exception {
		
		GeneralUtil.saveDocuments(docs.getDocIdent() + ".doc");
		
		checkForErrorMessages();
		
		if (errorSmalls.isEmpty()) {
			retValue = true;
		}
		
		return retValue;
	}

	public static boolean doesDocumentExists(String docIdent) throws Exception {

		rowIndx = -1;

		ClicksUtil.clickLinks(IClicksConst.openDocumentsListLnk);

		if (docIdent.trim().compareTo("") != 0) {
			FiltersUtil.filterListByLabel(IFiltersConst.gpa_DocName_Lbl,
					docIdent, IFiltersConst.exact);

			rowIndx = TablesUtil.getRowIndex(ITablesConst.documentsTableId,
					docIdent);

			if (rowIndx > -1) {
				return true;
			}

			return false;
		}

		return true;
	}
	
	public static boolean downloadDocument(Documents docs) throws Exception {
		
		
		return false;
	}

	public static void upload_Attachments(String DocType, String DocTitle, String FileName) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		Div tDiv = TablesUtil.tableDiv();
		
		if(tDiv.body(id,ILBFunctionConst.lbf_AttachmentList_Table_Id).exists())
		
		{
			tDiv.body(id, ILBFunctionConst.lbf_AttachmentList_Table_Id).link(text, DocType).click();
		}
		else
		{
			log.error("Could not find the Attachment List!");
			return;
		}			

			ie.textField(id, ILBFunctionConst.lbf_AttachmentDetails_DocDescription_FieldId).set(DocTitle);

			ie.fileField(id, ILBFunctionConst.lbf_AttachmentDetails_FileUpload_FieldId).set(
					"\"" + GeneralUtil.getWorkspacePath() + ILBFunctionConst.lbf_DocsFilesPath
							+ FileName + "\"");

			ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);
		}

	public static boolean uploadDocument(Documents docs, boolean deleteFirst)
			throws Exception {

		IE ie = IEUtil.getActiveIE();

		retValue = false;

		if (doesDocumentExists(docs.getDocIdent()) && deleteFirst) {
			deleteDocument(docs.getDocIdent());
		}

		ClicksUtil.clickImage(IClicksConst.newImg);

		ie.textField(id, IDocumentsConst.document_Doc_Identifier_TxtFld).set(
				docs.getDocIdent());
		
		if(!docs.getDocType().equalsIgnoreCase(""))
		{
			ie.selectList(id, IDocumentsConst.document_Doc_Type_DDFld).select(
					docs.getDocType());			
		}

		ie.textField(id, IDocumentsConst.document_Doc_Desc_TxtFld).set(
				docs.getDescription());
		
		if(!docs.getOrgName().equalsIgnoreCase(""))
		{
			ie.selectList(id, IDocumentsConst.document_Doc_PrimOrg_DDFld).select(
					docs.getOrgName());			
		}

		ie.selectList(id, IDocumentsConst.document_Doc_OrgAccess_DDFld).select(
				docs.getOrgAccess());

		ie.textField(id, IDocumentsConst.document_Doc_LocaleName0_TxtFld).set(
				docs.getDocName());

		if (ie.textField(id, IDocumentsConst.document_Doc_LocaleName1_TxtFld)
				.exists()) {
			ie.textField(id, IDocumentsConst.document_Doc_LocaleName1_TxtFld)
					.set(docs.getDocName());
		}

		if (ie.textField(id, IDocumentsConst.document_Doc_LocaleName2_TxtFld)
				.exists()) {
			ie.textField(id, IDocumentsConst.document_Doc_LocaleName2_TxtFld)
					.set(docs.getDocName());
		}

		if (ie.textField(id, IDocumentsConst.document_Doc_LocaleName3_TxtFld)
				.exists()) {
			ie.textField(id, IDocumentsConst.document_Doc_LocaleName3_TxtFld)
					.set(docs.getDocName());
		}
		
		if(!docs.getFileName().equalsIgnoreCase(""))
		{
			ie.fileField(id, IDocumentsConst.document_Doc_File_fileFld).set(
					"\"" + GeneralUtil.getWorkspacePath() + docs.getFilePath()
							+ docs.getFileName() + "\"");
		}	

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		checkForErrorMessages();

		if (errorSmalls.isEmpty()) {
			retValue = true;
		}

		ClicksUtil.clickButtons(IClicksConst.backBtn);

		return retValue;
	}

	public static boolean editDocument(Documents docs, boolean isItNew, boolean returnToList) throws Exception {

		IE ie = IEUtil.getActiveIE();

		retValue = false;	
		

		if(isItNew) {
			ClicksUtil.clickLinks(IClicksConst.openDocumentsListLnk);
			ClicksUtil.clickImage(IClicksConst.newImg);
		}		

		ie.textField(id, IDocumentsConst.document_Doc_Identifier_TxtFld).set(
				docs.getDocIdent());
		
		if(!docs.getDocType().equalsIgnoreCase(""))
		{
			ie.selectList(id, IDocumentsConst.document_Doc_Type_DDFld).select(
					docs.getDocType());			
		}

		ie.textField(id, IDocumentsConst.document_Doc_Desc_TxtFld).set(
				docs.getDescription());
		
		if(!docs.getOrgName().equalsIgnoreCase(""))
		{
			ie.selectList(id, IDocumentsConst.document_Doc_PrimOrg_DDFld).select(
					docs.getOrgName());			
		}

		ie.selectList(id, IDocumentsConst.document_Doc_OrgAccess_DDFld).select(
				docs.getOrgAccess());

		ie.textField(id, IDocumentsConst.document_Doc_LocaleName0_TxtFld).set(
				docs.getDocName());

		if (ie.textField(id, IDocumentsConst.document_Doc_LocaleName1_TxtFld)
				.exists()) {
			ie.textField(id, IDocumentsConst.document_Doc_LocaleName1_TxtFld)
					.set(docs.getDocName());
		}

		if (ie.textField(id, IDocumentsConst.document_Doc_LocaleName2_TxtFld)
				.exists()) {
			ie.textField(id, IDocumentsConst.document_Doc_LocaleName2_TxtFld)
					.set(docs.getDocName());
		}

		if (ie.textField(id, IDocumentsConst.document_Doc_LocaleName3_TxtFld)
				.exists()) {
			ie.textField(id, IDocumentsConst.document_Doc_LocaleName3_TxtFld)
					.set(docs.getDocName());
		}
		
		if(!docs.getFileName().equalsIgnoreCase(""))
		{
			ie.fileField(id, IDocumentsConst.document_Doc_File_fileFld).set(
					"\"" + GeneralUtil.getWorkspacePath() + docs.getFilePath()
							+ docs.getFileName() + "\"");
		}	

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		checkForErrorMessages();

		if (errorSmalls.isEmpty()) {
			retValue = true;
		}
		
		if(returnToList) {

			ClicksUtil.clickButtons(IClicksConst.backBtn);
			
		}

		return retValue;
	}

	public static boolean deleteDocument(String docIdent) throws Exception {

		
		
		rowIndx = -1;

		retValue = false;

		if (doesDocumentExists(docIdent)) {
			
			Div tDiv = TablesUtil.tableDiv();
			
			tDiv.body(id, ITablesConst.documentsTableId).row(rowIndx).image(0).click();
			
			GeneralUtil.takeANap(1.000);
			
			if(!ClicksUtil.clickButtonsById("j_id_1v:j_id_4q"))
			{
				log.error("Could Not find OK Button to confirm delete Document!");
			}
			
			checkForInfoMessages();

			if (errorSmalls.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	public static void checkForErrorMessages() throws Exception {

		IE ie = IEUtil.getActiveIE();

		errorSmalls = new ArrayList<String>();
		
		spans = null;

		if (ie.htmlElement(attribute("class", "ui-messages-error-summary")).exists()) {
			
			spans = ie.htmlElement(attribute("class", "ui-messages-error-summary")).spans();			

		}	


		if (ie.htmlElement(attribute("class", "ui-message-error-detail")).exists()) {
			
			spans = ie.htmlElement(attribute("class", "ui-message-error-detail")).spans();			

		}
		
		
		if(null != spans)
		{			
			for (Span span1 : spans) {

				if (span1.innerText().trim() != "") {
					
					log.error("Validation Error: " + span1.innerText().trim());

					errorSmalls.add(span1.innerText().trim());
				}
			}
			
		}
	}

	public static void checkForInfoMessages() throws Exception {

		IE ie = IEUtil.getActiveIE();

		errorSmalls = new ArrayList<String>();
		
		spans = null;

		if (ie.htmlElement(attribute("class", "ui-messages-info-summary")).exists()) {
			
			spans = ie.htmlElement(attribute("class", "ui-messages-info-summary")).spans();			

		}	


		if (ie.htmlElement(attribute("class", "ui-message-info-detail")).exists()) {
			
			spans = ie.htmlElement(attribute("class", "ui-message-info-detail")).spans();			

		}
		
		
		if(null != spans)
		{			
			for (Span span1 : spans) {

				if (span1.innerText().trim() != "") {
					
					log.error("Validation Info: " + span1.innerText().trim());

					errorSmalls.add(span1.innerText().trim());
				}
			}
			
		}
	}

}
