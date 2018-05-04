package test_Suite.utils.cases;

import static watij.finders.SymbolFactory.*;

import java.util.Hashtable;

import watij.dialogs.ConfirmDialog;
import watij.elements.Div;
import watij.elements.Table;
import watij.runtime.ie.IE;
import test_Suite.constants.ui.*;
import test_Suite.constants.cases.*;
import test_Suite.utils.ui.*;
import test_Suite.constants.cases.ILookupsConst;
import test_Suite.constants.cases.ILookupsConst.CurrencySymbol;
import test_Suite.constants.cases.ILookupsConst.fieldsLookupTable;
import test_Suite.lib.cases.Lookups;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

public class LookupUtil {

	private static Log log = LogFactory.getLog(LookupUtil.class);

	public static boolean taggleLookupActiveness(String lookupName, String tableId) throws Exception {

		if (!FiltersUtil.filterListByLabel(IFiltersConst.administration_LookupName_Lbl, lookupName, IFiltersConst.exact))
		{
			log.error("Could not do filtering on ".concat(IFiltersConst.administration_LookupName_Lbl));
			return false;
		}

		if (!FiltersUtil.filterPOListByLabel(IFiltersConst.administration_LookupStatus_Lbl, "", IGeneralConst.statusAll))
		{
			log.error("Could not do filtering on ".concat(IFiltersConst.administration_LookupStatus_Lbl));
			return false;
		}

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn))
		{
			log.error("Could not click button ".concat(IClicksConst.filterBtn));
			return false;
		}

		Div tDiv = TablesUtil.tableDiv();

		if (tDiv.body(id, tableId).rowCount() <= 0)
		{
			log.error("No Items Found in List!");
			return false;
		}

		if(tDiv.body(id,tableId).row(0).button(id,ILookupsConst.lookupList_disableChbx).exists())
		{
			deactivateFilteredLookup();
		}
		else
		{
			activateFilteredLookup();
		}

		return true;
	}

	public static boolean taggleLookupValuesActiveness(String lookupValueName, String tableId) throws Exception {

		Div tDiv = TablesUtil.tableDiv();

		if (!FiltersUtil.filterListByLabel(IFiltersConst.administration_LookupValueName_Lbl, lookupValueName, IFiltersConst.exact))
		{
			log.error("Could not do filtering on ".concat(IFiltersConst.administration_LookupValueName_Lbl));
			return false;
		}

		if (!FiltersUtil.filterPOListByLabel(IFiltersConst.administration_LookupValueStatus_Lbl, "", IGeneralConst.statusAll))
		{
			log.error("Could not do filtering on ".concat(IFiltersConst.administration_LookupValueStatus_Lbl));
			return false;
		}

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn))
		{
			log.error("Could not click button ".concat(IClicksConst.filterBtn));
			return false;
		}

		if (tDiv.body(id, tableId).rowCount() <= 0)
		{
			log.error("No Items Found in List!");
			return false;
		}

		if(tDiv.body(id,tableId).row(0).checkbox(0).checked())
		{
			deactivateFilteredLookup();
		}
		else
		{
			activateFilteredLookup();
		}

		return true;
	}

	public static boolean searchAllMultiCurrencyLookupValues(String tableId)
			throws Exception {

		Div tDiv = TablesUtil.tableDiv();
		boolean retValue = true;
		CurrencySymbol[] lookupValueName = CurrencySymbol.values();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropDown = new Hashtable<String, String>();

		hashTableDropDown.put(
				IFiltersConst.administration_LookupValueStatus_Lbl,
				IGeneralConst.statusActive);
		for (int i = 0; i < lookupValueName.length; i++) {

			hashTable.put(IFiltersConst.administration_LookupValueName_Lbl,
					lookupValueName[i].getValue());
			FiltersUtil.filterListByLabel(hashTable, hashTableDropDown, false);

			if (tDiv.body(id, tableId).rowCount() <= 0) {

				log.error("No Items Found in List!");

				retValue = false;

			}

		}

		return retValue;

	}

	public static void activateFilteredLookup() throws Exception {

		IE ie = IEUtil.getActiveIE();

		ie.button(id,ILookupsConst.lookupList_enableChbx).click(); //checkbox(0).set();	

		GeneralUtil.takeANap(2.0);
		
		Assert.assertTrue(ClicksUtil.clickButtons("Ok"));

		GeneralUtil.takeANap(1.0);		
		

		//dialogClicker = null;    	
	}

	public static void deactivateFilteredLookup() throws Exception {

		IE ie = IEUtil.getActiveIE();
		ie.button(id,ILookupsConst.lookupList_disableChbx).click(); //checkbox(0).set();	

		GeneralUtil.takeANap(2.0);
		
		Assert.assertTrue(ClicksUtil.clickButtons("Ok"));
		
		GeneralUtil.takeANap(1.0);		
	}

	/**
	 * Find if the Lookup Name exists in the HTML table
	 * @param lookupName
	 * @param tableLookups
	 * @return
	 */
	public static boolean FindNewRecordInTable(String lookupName, Table tableLookups)
	{
		boolean retValue = false;
		try
		{
			for (int i = 0; i < tableLookups.rowCount(); i++) 
			{
				if (tableLookups.row(i).cell(fieldsLookupTable.LookupName.ordinal()).innerText().matches(lookupName)) 
				{
					retValue = true;
					break;
				}
			}
			return retValue;
		}
		catch(Exception ex)
		{
			log.debug("ERROR: " + ex.getMessage());
			return retValue;
		}
	}

	public static boolean activateLookup(String value1) throws Exception {
		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> drpDwnHash = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.administration_LookupName_Lbl, value1);
		drpDwnHash.put(IFiltersConst.administration_LookupStatus_Lbl, IGeneralConst.statusInactive);

		if(!FiltersUtil.filterListByLabel(hashTable,drpDwnHash, false))
		{
			log.error("Could not filter Lookup List!");
			return false;
		}

		if(!TablesUtil.findInTable(ITablesConst.lookupListTableId, value1))
		{
			log.error("Could not find lookup in List!");
			return false;
		}

		Thread dialogClicker = new Thread()
		{
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try
				{
					IE ie = IEUtil.getActiveIE();
					ConfirmDialog dialog1 = ie.confirmDialog();
					while (dialog1==null)
					{
						log.debug("can't yet get handle on confirm dialog1");
						this.sleep(1000);
					}

					dialog1.ok();
					log.debug("got confirm Dialog1 and clicked OK.");

				}
				catch (Exception e)
				{
					throw new RuntimeException("Unexpected exception",e);
				}
			}
		};

		dialogClicker.start();

		log.debug("started dialog clicker thread");

		ie.checkbox(0).set();	

		GeneralUtil.takeANap(1.000);

		dialogClicker = null;

		return true;
	}


	public static String findlookupInList(String value1, String tableId) throws Exception {  

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> drpDwnHash = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.administration_LookupName_Lbl, value1);
		drpDwnHash.put(IFiltersConst.administration_LookupStatus_Lbl, IGeneralConst.statusActive);

		FiltersUtil.filterListByLabel(hashTable,drpDwnHash, false);

		if (TablesUtil.findInTable(tableId, new String[] {value1}) >= 0)
			return IGeneralConst.statusActive;

		if  (!FiltersUtil.filterPOListByLabel(IFiltersConst.administration_LookupStatus_Lbl, "", IGeneralConst.statusInactive))
		{
			log.error("Failed to filter on ".concat(IFiltersConst.administration_LookupStatus_Lbl));
		}	

		if (!ClicksUtil.clickButtons(IClicksConst.filterBtn))
		{
			log.error("Failed to click button ".concat(IClicksConst.filterBtn));
		}

		if (TablesUtil.findInTable(tableId, new String[] {value1}) >= 0)    		
			return IGeneralConst.statusInactive;

		return IGeneralConst.statusAll;
	}


	public static boolean deactivateLookup(String value1) throws Exception {
		IE ie = IEUtil.getActiveIE();

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> drpDwnHash = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.administration_LookupName_Lbl, value1);
		drpDwnHash.put(IFiltersConst.administration_LookupStatus_Lbl, IGeneralConst.statusActive);

		FiltersUtil.filterListByLabel(hashTable,drpDwnHash, false);

		Thread dialogClicker = new Thread()
		{
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try
				{
					IE ie = IEUtil.getActiveIE();
					ConfirmDialog dialog1 = ie.confirmDialog();

					while (dialog1==null)
					{
						log.debug("can't yet get handle on confirm dialog1");
						this.sleep(250);
					}

					dialog1.ok();
					log.debug("got confirm Dialog1 and clicked OK.");					

				}
				catch (Exception e)
				{
					throw new RuntimeException("Unexpected exception", e);
				}
			}
		};

		dialogClicker.start();

		log.debug("started dialog clicker thread");

		ie.checkbox(0).clear();

		GeneralUtil.takeANap(1.000);

		dialogClicker = null;

		return true;
	}


	public static boolean createNewLookupAndValues(Lookups lookup) throws Exception {

		if(!ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk))
		{
			log.error("Could Not click on link " .concat(IClicksConst.openLookupsListsLnk));
			return false;
		}
		return true;
	}


	public static boolean createNewLookupAndValues(String lookupParams[]) throws Exception {
		IE ie = IEUtil.getActiveIE();
		
    	Div tDiv = TablesUtil.tableDiv();

		//retValue = false;

		if(!ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk))
		{
			log.error("Could Not click on link ".concat(IClicksConst.openLookupsListsLnk));
			return false;
		}

		if (findlookupInList(lookupParams[0], ITablesConst.lookupListTableId) == "All") {

			if (!ClicksUtil.clickImage(IClicksConst.newImg))
			{
				log.error("Failed to click on Image");
				return false;
			}

			ie.textField (id, ILookupsConst.fieldLookupName).set(lookupParams[0]);
			ie.selectList(id, ILookupsConst.primOrg_Drpdwn_Id).select(IGeneralConst.primG3_OrgRoot);
			ie.selectList(id, ILookupsConst.orgAccess_Drpdwn_Id).select(IGeneralConst.org_Access_Public);

			if(!ClicksUtil.clickButtons(IClicksConst.saveBtn)) 
			{
				log.error("Could Not click on button " .concat(IClicksConst.saveBtn));
				return false;
			}
			if (!ClicksUtil.clickButtons(IClicksConst.lookup_BackToLookupsListBtn))
			{
				log.error("Could Not click on button " .concat(IClicksConst.lookup_BackToLookupsListBtn));
				return false;
			}
		}
		else
		{
			if (findlookupInList(lookupParams[0], ITablesConst.lookupListTableId) == IGeneralConst.statusInactive) {

				activateLookup(lookupParams[0]);

				if(!FiltersUtil.filterPOListByLabel(IFiltersConst.administration_LookupStatus_Lbl, "", IGeneralConst.statusActive))
				{
					log.error("Could Not filter on " .concat(IFiltersConst.administration_LookupStatus_Lbl));
					return false;
				}


				if(!ClicksUtil.clickButtons(IClicksConst.filterBtn))
				{
					log.error("Could Not click on button " .concat(IClicksConst.filterBtn));
					return false;
				}
			}
		}

		if (!FiltersUtil.filterListByLabel(IFiltersConst.administration_LookupStatus_Lbl,"", lookupParams[0]))
		{
			log.error("Could Not filter on " .concat(IFiltersConst.administration_LookupStatus_Lbl));
			return false;
		}

		tDiv.body(id,ITablesConst.lookupListTableId).row(0).image(0).click();

		for(int i=1; i<lookupParams.length; i++) {

			if (findlookupInList(lookupParams[i], ITablesConst.lookupValueTableId) == "All") {

				ClicksUtil.clickImage(IClicksConst.newImg);

				ie.textField(0).set(lookupParams[i]);

				ie.textField(1).set(lookupParams[i]);   

				ie.textField(2).set(lookupParams[i]);

				if (ie.textField(3).exists())
				{
					ie.textField(3).set(lookupParams[i]);
				}

				if (ie.textField(4).exists())
				{
					ie.textField(4).set(lookupParams[i]);
				}

				if (!ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn))
				{
					log.error("Could Not click on button " .concat(IClicksConst.saveAndBackBtn));
					return false;
				}
			}
			else
				if (findlookupInList(lookupParams[i], ITablesConst.lookupValueTableId) == "Inactive") {

					activateLookup(lookupParams[i]);
				}    			
		}   			
		return true;  
	}

	public static boolean addLookupValues(String[] Vals, String lookupName) throws Exception {

		if(!openLookupValueList(lookupName))
		{
			log.error("Could not Open Lookup Values or Filter Lookup List!");
			return false;
		}

		for (String string : Vals) {

			if(!filterLookupValueList(string))
			{
				String ident = ("qa_".concat(lookupName.replace(" ", "_")).concat("_").concat(string.replace(" ", "_"))).toUpperCase();

				if(!ClicksUtil.clickImageByAlt(ILookupsConst.addLookupValueIconAlt))
				{
					log.error("Could Not click on image " .concat(ILookupsConst.addLookupValueIconAlt));
					return false;
				}

				if(!GeneralUtil.setTextById(ILookupsConst.valueConstantText, ident))
				{
					log.error("Could Not set text " .concat(ILookupsConst.valueConstantText));
					return false;
				}

				if(!GeneralUtil.setTextById(ILookupsConst.valueCodeText, ident))
				{
					log.error("Could Not set text " .concat(ILookupsConst.valueCodeText));
					return false;
				}	

				if(!GeneralUtil.setTextById(ILookupsConst.valueLocale1Text, string))
				{
					log.error("Could Not set text " .concat(ILookupsConst.valueLocale1Text));
					return false;
				}

				if(!GeneralUtil.setTextById(ILookupsConst.valueLocale2Text, string))
				{
					log.error("Could Not set text " .concat(ILookupsConst.valueLocale2Text));
				}

				if(!GeneralUtil.setTextById(ILookupsConst.valueLocale3Text, string))
				{
					log.error("Could Not set text " .concat(ILookupsConst.valueLocale3Text));
				}

				if(!GeneralUtil.setTextById(ILookupsConst.valueLocale4Text, string))
				{
					log.error("Could Not set text " .concat(ILookupsConst.valueLocale4Text));
				}

				if (!ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn))
				{
					log.error("Could Not click on button " .concat(IClicksConst.saveAndBackBtn));
					return false;
				}
			}

		}

		return true;
	}

	public static boolean openLookupValueList(String lookupName) throws Exception {

		if (!filterLookupList(lookupName))
		{
			log.error("Could not Filter the Loookup List");
			return false;
		}

		if(!TablesUtil.openInTableByImageAlt(ITablesConst.lookupListTableId, lookupName, ILookupsConst.lookupValueIconAlt.concat(lookupName)))
		{
			log.error("Could Not open list ".concat(ITablesConst.lookupListTableId));
			return false;
		}

		return true;
	}

	public static boolean filterLookupList(String lookupName) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> drpDwnHash = new Hashtable<String, String>();

		if (!ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk))
		{
			log.error("Could Not click link ".concat(IClicksConst.openLookupsListsLnk));
			return false;
		}

		hashTable.put(IFiltersConst.administration_LookupName_Lbl, lookupName);
		drpDwnHash.put(IFiltersConst.administration_LookupStatus_Lbl, IGeneralConst.statusAll);    

		if (!FiltersUtil.filterListByLabel(hashTable,drpDwnHash, false))
		{
			log.error("Could Not Filter the Lookup List");
			return false;
		}

		if(!TablesUtil.findInTable(ITablesConst.lookupListTableId, lookupName)) 
		{    		
			log.error("After Filtering the list the lookup Value does not exists!");
			return false;
		}    	

		return true;
	}

	public static boolean filterLookupValueList(String lookupValueName) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> drpDwnHash = new Hashtable<String, String>();

		hashTable.put(IFiltersConst.administration_LookupValueName_Lbl, lookupValueName);
		drpDwnHash.put(IFiltersConst.administration_LookupValueStatus_Lbl, IGeneralConst.statusAll);    

		if(!FiltersUtil.filterListByLabel(hashTable,drpDwnHash, false))
		{
			log.error("Could Not Filter the Lookup Values List!");
			return false;
		}

		if(!TablesUtil.findInTable(ITablesConst.lookupValueTableId, lookupValueName)) 
		{    		
			log.error("After Filtering the list the lookup Value does not exists!");
			return false;
		}    	

		return true;
	}

	public static boolean sortLookupValues(String lookupName) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> drpDwnHash = new Hashtable<String, String>();

		if(!ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openLookupsListsLnk));
			return false;
		}

		hashTable.put(IFiltersConst.administration_LookupName_Lbl, lookupName);
		drpDwnHash.put(IFiltersConst.administration_LookupStatus_Lbl, IGeneralConst.statusAll);    

		if (!FiltersUtil.filterListByLabel(hashTable,drpDwnHash, false))
		{
			log.error("Could Not Filter the Lookup Values List!");
			return false;
		}

		if(!ClicksUtil.clickImageByAlt("/" + ILookupsConst.openLookupValuesList + "/"))
		{
			log.error("Could not click on ".concat(ILookupsConst.openLookupValuesList));
			return false;
		}
		if(!ClicksUtil.clickButtons(IClicksConst.lookup_ReorderValuesBtn)) 
		{
			log.error("Could not click on button ".concat(IClicksConst.lookup_ReorderValuesBtn));
			return false;
		}
		if(!ClicksUtil.clickButtons(IClicksConst.lookup_SortAlphaBtn))
		{
			log.error("Could not click on button ".concat(IClicksConst.lookup_SortAlphaBtn));
			return false;
		}

		if(!ClicksUtil.clickButtons(IClicksConst.saveBtn))
		{
			log.error("Could not click on button ".concat(IClicksConst.saveBtn) );
			return false;
		}
		if(!ClicksUtil.clickButtons(IClicksConst.backBtn))
		{
			log.error("Could not click on button ".concat(IClicksConst.backBtn) );
			return false;
		}
		return true;
	} 

	public static boolean changeChildLookup(String lookupName, String childLookup) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> drpDwnHash = new Hashtable<String, String>();

		if(!ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk))
		{
			log.error("Could not click on link ".concat(IClicksConst.openLookupsListsLnk) );
			return false;
		}

		hashTable.put(IFiltersConst.administration_LookupName_Lbl, lookupName);
		drpDwnHash.put(IFiltersConst.administration_LookupStatus_Lbl, IGeneralConst.statusAll);  

		if(!FiltersUtil.filterListByLabel(hashTable,drpDwnHash, false))
		{
			log.error("Could not click on Filter");
			return false;
		}

		if(!ClicksUtil.clickLinks(lookupName))
		{
			log.error("Could not click on link");
			return false;
		}

		Assert.assertTrue(GeneralUtil.selectInDropdownList(ILookupsConst.childLookup_Drpdwn_Id, childLookup), "Fail: Could not find Child Lookup in Dropdown");    	

		if(!ClicksUtil.clickButtons(IClicksConst.saveBtn))
		{
			log.error("Could not click button ".concat(IClicksConst.saveBtn) );
			return false;
		} 
		if(!ClicksUtil.clickButtons(IClicksConst.saveBtn))
		{
			log.error("Could not click button ".concat(IClicksConst.backBtn) );
			return false;
		}  

		return true;
	}

	public static boolean changeOrgInLookup(String lookupName,String primOrg, String orgAccess) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> drpDwnHash = new Hashtable<String, String>();

		if(!ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk))
		{
			log.error("Could Not click on link ".concat(IClicksConst.openLookupsListsLnk));
			return false;
		}

		hashTable.put(IFiltersConst.administration_LookupName_Lbl, lookupName);
		drpDwnHash.put(IFiltersConst.administration_LookupStatus_Lbl, IGeneralConst.statusAll);  

		if(!FiltersUtil.filterListByLabel(hashTable,drpDwnHash, false))
		{
			log.error("Could not click on Filter");
			return false;
		}

		if(!ClicksUtil.clickLinks(lookupName))
		{
			log.error("Could not click on link");
			return false;
		}

		Assert.assertTrue(GeneralUtil.selectInDropdownList(ILookupsConst.primOrg_Drpdwn_Id, primOrg), "Fail: Could not find Primary Org in Dropdown");    	
		Assert.assertTrue(GeneralUtil.selectInDropdownList(ILookupsConst.orgAccess_Drpdwn_Id, orgAccess), "Fail: Could not find Org Access Type in Dropdown");    	

		if(!ClicksUtil.clickButtons(IClicksConst.saveBtn))
		{
			log.error("Could not click button ".concat(IClicksConst.saveBtn) );
			return false;
		} 
		if(!ClicksUtil.clickButtons(IClicksConst.saveBtn))
		{
			log.error("Could not click button ".concat(IClicksConst.backBtn) );
			return false;
		}  

		return true;
	}

	public static String getNewBaseLetter(String baseObject) throws Exception {

		return FiltersUtil.getNewBaseLetter(ITablesConst.lookupListTableId, IFiltersConst.administration_LookupName_Lbl, baseObject, IFiltersConst.exact);
	}

}
