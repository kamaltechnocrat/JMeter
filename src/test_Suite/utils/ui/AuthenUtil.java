 package test_Suite.utils.ui;

import static watij.finders.SymbolFactory.id;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import watij.elements.Table;
import watij.elements.TableRow;
import watij.runtime.ie.IE;
import test_Suite.constants.ui.*;


public class AuthenUtil {
	
	private static Log log = LogFactory.getLog(AuthenUtil.class);
	
	public static boolean setChangePwdOnLoginFO(boolean allow) throws Exception {
		IE ie = IEUtil.getActiveIE();
		
		if(!ClicksUtil.clickLinks(IClicksConst.openSystemSettingListLnk))
		{
			log.error("could not click on link " .concat(IClicksConst.openSystemSettingListLnk));
			return false;
		}
		
		int rowIndex = -1;
		rowIndex = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.sys_Settings_TableId, IConfigConst.changePwdOnLoginFO_Lbl);
		
		if(rowIndex == -1) 
			return false;
		else
		{
			if (allow)
			{
				ie.table(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set("true");
			}
			else
			{
				ie.table(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set("false");
			}
		}
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		return true;
	}
	
	
	
	public static boolean setChangePwdOnLoginPO(boolean allow) throws Exception {
		IE ie = IEUtil.getActiveIE();
		
		if(!ClicksUtil.clickLinks(IClicksConst.openSystemSettingListLnk))
		{
			log.error("could not click on link " .concat(IClicksConst.openSystemSettingListLnk));
			return false;
		}
		
		int rowIndex = -1;
		rowIndex = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.sys_Settings_TableId, IConfigConst.changePwdOnLoginPO_Lbl);
		
		if(rowIndex == -1)
			return false;
		else
		{
			if (allow)
			{
				ie.table(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set("true");
			}
			else
			{
				ie.table(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set("false");
			}
		}		
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		return true;
	}
	
	
	
	public static boolean changeLicenseKey(String strKey) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
//		ClicksUtil.clickLinks(IClicksConst.openSystemSettingListLnk);
//		
//		int rowIndex = -1;
//		rowIndex = TablesUtil.getRowIndexInTableWithPaginator(ITablesConst.sys_Settings_TableId, IConfigConst.licenseKey_Lbl);
//		
//		if(rowIndex != -1)
//		{
//			ie.table(id,ITablesConst.sys_Settings_TableId).row(rowIndex).textField(0).set(strKey);
//		}
//		
//		ClicksUtil.clickButtons(IClicksConst.saveBtn);	

		if(!ClicksUtil.clickLinks(IClicksConst.openLicenseManagementLnk))
		{
			log.error("could not click on link " .concat(IClicksConst.openLicenseManagementLnk));
			return false;
		}
		
		Table table = ie.table(id,ITablesConst.license_Management_TableId);
		
		for (TableRow row : table.rows()) {
			
			if(row.innerText().startsWith(IConfigConst.licenseKey_Names[0]))
			{
				row.textField(0).set(strKey);
				break;
			}			
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		return true;
	}
}

