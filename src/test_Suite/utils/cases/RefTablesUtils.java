/**
 * 
 */
package test_Suite.utils.cases;

import static watij.finders.SymbolFactory.*;

import org.apache.commons.logging.*;

import test_Suite.constants.cases.*;
import test_Suite.constants.ui.*;
import test_Suite.lib.cases.RefTables;
import test_Suite.utils.ui.*;
import watij.dialogs.ConfirmDialog;
import watij.elements.*;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 *
 */
public class RefTablesUtils {
	
	private static Log log = LogFactory.getLog(RefTablesUtils.class);
	
	public static boolean deleteRefTable(RefTables refTable) throws Exception {
		
		if(!ClicksUtil.clickLinks(IClicksConst.openRefrenceTablesLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openRefrenceTablesLnk));
			return false;
		}
		
		if(!FiltersUtil.filterListByLabel(IFiltersConst.refTableName, refTable.getRefTableFullIdent(), IFiltersConst.exact))
		{
			log.error("Could not Filter Reference Tables List!");
			
			return false;			
		}
		
		int inxVal = TablesUtil.getRowIndex(ITablesConst.refTablesListTableId, refTable.getRefTableFullIdent());
		
		if(inxVal < 0)
		{
			log.error("Could not find Reference Table in List!");
			return false;
		}
		
		if(!TablesUtil.isImageExistsInTableByAlt(ITablesConst.refTablesListTableId, inxVal, IClicksConst.refTable_DeleteAlt))
		{
			log.error("No Delete Image to click!");
			return false;
		}
		
		TableBody tableBody = TablesUtil.getTableBodyById(ITablesConst.refTablesListTableId);
		
		if (null == tableBody)
		{
			log.error("the Table does not exists!");
			return false;
		}
		


		Thread dialogClicker = new Thread() {
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try {
					IE ie = IEUtil.getActiveIE();
					ConfirmDialog dialog1 = ie.confirmDialog();
					while (dialog1 == null) {
						log.debug("can't yet get handle on confirm dialog1");
						this.sleep(250);
					}

					dialog1.ok();
					log.debug("got confirm Dialog1 and clicked OK.");
				} catch (Exception e) {
					throw new RuntimeException("Unexpected exception", e);
				}
			}
		};

		dialogClicker.start();
		log.debug("started dialog clicker thread");
		
		tableBody.row(inxVal).image(alt,IClicksConst.refTable_DeleteAlt).click();
		
		dialogClicker = null;		
		
		return true;
	}
	
	public static boolean addNewRefTable(RefTables refTable) throws Exception {
		
		if(!ClicksUtil.clickLinks(IClicksConst.openRefrenceTablesLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openRefrenceTablesLnk));
			return false;
		}
		
		if(!ClicksUtil.clickImage(IClicksConst.newImg))
		{
			log.error("Could not open new Refernce Tables details");
			return false;
		}
		
		if(!GeneralUtil.setTextById(IRefTablesConst.refTableIdent_TxtFieldId, refTable.getRefTableFullIdent()))
		{
			log.error("Could not enter Ref Table Identifier to Text Field");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IRefTablesConst.refTableEForm_DropdownId, refTable.getRefTableEFormFullIdent()))
		{
			log.error("Could not select the Ref Table e.Form");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IRefTablesConst.refTableOrg_DropdownId, refTable.getRefTableOrgAccess()))
		{
			log.error("Could not find Org Hierarchy: ".concat(refTable.getRefTableOrgAccess()));
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		ClicksUtil.clickButtons(IClicksConst.backBtn);
		
		return true;
	}
	
	
	public static boolean importDataToRefTable(RefTables refTable, boolean deleteAllContent) throws Exception {

		IE ie = IEUtil.getActiveIE();
		
		if(!ClicksUtil.clickLinks(IClicksConst.openRefrenceTablesLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openRefrenceTablesLnk));
			return false;
		}
		
		if(!TablesUtil.openInTableByImageAlt(ITablesConst.refTablesListTableId, refTable.getRefTableFullIdent(), IClicksConst.refTable_ImportDataAlt))
		{
			log.error("Could not open Import form!");
			return false;
		}
		
		if(!ie.fileField(0).exists())
		{
			log.error("could not find File Field on page!");
			return false;
		}
		
		ie.fileField(0).set("\"".concat(GeneralUtil.getWorkspacePath()).concat(IGeneralConst.xmlFilesPath).concat(refTable.getRefTableDataFilePath()).concat("\""));

		if(deleteAllContent)
		{
			if(!GeneralUtil.toggleCheckBoxById(IRefTablesConst.refTableDeleteAllContent_ChkboxId, deleteAllContent))	
			{
				log.error("Could not Toggle the Checkbox!");
				return false;
			}			
		}		
		
		if(!ClicksUtil.clickButtons(IClicksConst.uploadBtn))
		{
			log.error("Could not click on button " .concat(IClicksConst.uploadBtn));
			return false;
		}
		return true;
	}
	
	
	public static boolean openRefTableDataList(RefTables refTable) throws Exception {	
		
		if(!ClicksUtil.clickLinks(IClicksConst.openRefrenceTablesLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openRefrenceTablesLnk));
			return false;
		}
		
		if(!FiltersUtil.filterListByLabel(IFiltersConst.refTableName, refTable.getRefTableFullIdent(), IFiltersConst.exact))
		{
			log.error("Could not Filter Reference Tables List!");
			
			return false;			
		}
		
		int inxVal = TablesUtil.getRowIndex(ITablesConst.refTablesListTableId, refTable.getRefTableFullIdent());
		
		if(inxVal < 0)
		{
			log.error("Could not find Reference Table in List!");
			return false;
		}
		
		if(!TablesUtil.openInTableByImageAlt(ITablesConst.refTablesListTableId, refTable.getRefTableFullIdent(), IClicksConst.refTable_OpenDataListAlt))
		{
			log.error("Could not open Ref Table Data List!");
			return false;
		}		
		
		return true;		
	}
	
	public static boolean deleteRefTableDataListItem(RefTables refTable, String itemToDelete, String itemFilterLabel) throws Exception {
		
		if(!openRefTableDataList(refTable))
		{
			log.error("Could not open Data List!");
			return false;
		}
		
		if(!FiltersUtil.filterListByLabel(itemFilterLabel, itemToDelete, IFiltersConst.exact))
		{
			log.error("Could not Filter Reference Tables List!");
			
			return false;			
		}
		
		int inxVal = TablesUtil.getRowIndex(ITablesConst.refTableDataListTableId, refTable.getRefTableFullIdent());
		
		if(inxVal < 0)
		{
			log.error("Could not find Reference Table in List!");
			return false;
		}
		
		if(!TablesUtil.isImageExistsInTableByAlt(ITablesConst.refTableDataListTableId, inxVal, IClicksConst.refTable_DeleteItemAlt))
		{
			log.error("No Delete Image to click!");
			return false;
		}
		
		TableBody tableBody = TablesUtil.getTableBodyById(ITablesConst.refTableDataListTableId);
		
		if (null == tableBody)
		{
			log.error("the Table does not exists!");
			return false;
		}

		Thread dialogClicker = new Thread() {
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try {
					IE ie = IEUtil.getActiveIE();
					ConfirmDialog dialog1 = ie.confirmDialog();
					while (dialog1 == null) {
						log.debug("can't yet get handle on confirm dialog1");
						this.sleep(250);
					}

					dialog1.ok();
					log.debug("got confirm Dialog1 and clicked OK.");
				} catch (Exception e) {
					throw new RuntimeException("Unexpected exception", e);
				}
			}
		};

		dialogClicker.start();
		log.debug("started dialog clicker thread");
		
		tableBody.row(inxVal).image(alt,IClicksConst.refTable_DeleteItemAlt).click();
		
		dialogClicker = null;	
		
		if(!ClicksUtil.returnFromAnyForm())
		{
			log.error("Could not return back to Reference Table List!");
			
			return false;
		}
		
		return true;
	}

}
