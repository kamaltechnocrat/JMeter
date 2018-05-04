/**
 * 
 */
package test_Suite.utils.users;

import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.*;

import java.util.ArrayList;

import org.apache.commons.logging.*;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.*;
import test_Suite.constants.users.IApplicantTypesConst;
import test_Suite.constants.users.IApplicantTypesConst.EAppCategories;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.*;
import watij.elements.*;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 *
 */
public class ApplicantTypesUtils {
	
	private static Log log = LogFactory.getLog(ApplicantsUtil.class);
	
	static ArrayList<String> arrList;
	
	public static boolean openNewApplicantTypesDetails() throws Exception {
		
		if(!GeneralUtil.isLinkExistsByTxt(IClicksConst.openPOApplicantListLnk))
		{
			log.error("Could Not find Link on page!");
			return false;
		}
		
		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);
		
		ClicksUtil.clickImage(IClicksConst.newImg);	
		
		
		return true;
	}
	
	public static boolean openApplicantTypesList() throws Exception {
		
		if(!GeneralUtil.isLinkExistsByTxt(IClicksConst.openApplicantTypesListsLnk))
		{
			log.error("Could Not find Link on page!");
			return false;
		}
		
		ClicksUtil.clickLinks(IClicksConst.openApplicantTypesListsLnk);
		
		
		return true;
	}
	
	public static boolean addNewApplicantType(String typeIdent, EAppCategories category, String locale) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		//Div tDiv = TablesUtil.tableDiv();
		
		if(!GeneralUtil.isImageExistsBySrc(IClicksConst.newImg))
		{
			log.error("Could not find add new Icon on Page!");
			
			return false;
		}
		
		ClicksUtil.clickLinksByTitle(IApplicantTypesConst.appTypeAddNew_Title);		
		
		
		if(!GeneralUtil.setTextById(IApplicantTypesConst.appTypeIdent_TxtFld, typeIdent))
		{
			log.error("Could not enter Applicant Type Identifier!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IApplicantTypesConst.appCategory_DrpDown, IApplicantTypesConst.applicantCategories[category.ordinal()]))
		{
			log.error("Could not select a Category from dropdown!");
			return false;
		}
		
		if(!ie.div(id,IApplicantTypesConst.appTypeLocales_DivId).exists())
		{
			log.error("Could not find Locales Div Table!");
			
			return false;
		}
		
		HtmlElements eles = ie.div(id,IApplicantTypesConst.appTypeLocales_DivId).htmlElements(tag, "dt");
		
//		TableBody tBody = tDiv.body(id, IApplicantTypesConst.appTypeLocales_DivId);
//		
//		tBody.row(0).textField(0).set(locale);
		
		
			
		for (HtmlElement ele : eles) {
			
			ele.textField(0).set(locale);			
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		arrList = GeneralUtil.checkForErrorMessages();
		
		if(arrList != null && !arrList.isEmpty())
		{
			log.error("Validation Errors!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.applicantTypes_BackToAppTypesListBtn);
		
		return true;
	}
	
	public static boolean findApplicantTypeInList(String typeIdent) throws Exception {
		
		if(!TablesUtil.isTableExists(ITablesConst.applicantTypesListTableId))
		{
			log.error("Could not find Applicant Types List!");
			return false;
		}
		
		if(!TablesUtil.findInTable(ITablesConst.applicantTypesListTableId, typeIdent))
		{
			log.error("Could not find entry in list!");
			return false;
		}	
		
		return true;
	}
	
	public static boolean changeApplicantTypeStatus(boolean status, String typeIdent) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		Div tDiv = TablesUtil.tableDiv();
		
		if(!TablesUtil.isTableExists(ITablesConst.applicantTypesListTableId))
		{
			log.error("Could not find Applicant Types List!");
			return false;
		}
		
		int rowIndex = TablesUtil.getRowIndex(ITablesConst.applicantTypesListTableId, typeIdent);
		
		if(rowIndex < 0)
		{
			log.error("Could not find Item In List!");
			return false;
		}
		
		tDiv.body(id,ITablesConst.applicantTypesListTableId).row(rowIndex).link(typeIdent).click();
		
		if(status)
		{
			ie.checkbox(id,IApplicantTypesConst.appTypeActive_ChkBox).set();
		}
		else
		{
			ie.checkbox(id,IApplicantTypesConst.appTypeActive_ChkBox).clear();
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.applicantTypes_BackToAppTypesListBtn);		
		
		return true;
	}
	
	public static boolean exportApplicantType(String xmlFile) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(!openApplicantTypesList())
		{
			log.error("Could not open Applican Types List");
			return false;
		}
		
		if(!ClicksUtil.clickImage(IClicksConst.imprtImg))
		{
			log.error("Could not find the Import Icon");
			return false;
		}
		
		if(!ie.fileField(0).exists())
		{
			log.error("Could not find File Field");
			return false;
		}
		
		ie.fileField(0).set("\"".concat(GeneralUtil.getWorkspacePath()).concat(IGeneralConst.xmlFilesPath).concat(xmlFile).concat("\""));

		ClicksUtil.clickButtons(IClicksConst.uploadBtn);		
		
		return true;
	}

}
