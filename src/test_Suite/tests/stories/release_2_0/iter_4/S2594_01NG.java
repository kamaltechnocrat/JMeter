/**
 * Steps:
 * 
 */
package test_Suite.tests.stories.release_2_0.iter_4;


import static watij.finders.SymbolFactory.id;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.ILookupsConst;
import test_Suite.lib.cases.Lookups;
import test_Suite.tests.stories.release_2_0.iter_3.S2524_01NG;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import watij.runtime.ie.IE;

/**
 * @author apankov
 *
 */
@Test(singleThreaded = true)
public class S2594_01NG {

	private static Log log = LogFactory.getLog(S2524_01NG.class);
	IE ie;
	Lookups lookup = null;
	private String lookupConstant = "aaaa"; 
	private String lookupName = "aaaa"; //First letter a to prevent entry from not being on the first page
	private String lookupValues [][] = 	{{"a-1", "a-1", "a-1", "1"},
										 {"a-2", "a-2", "a-2", "1"},
										 {"a-3", "a-3", "a-3", "0"}
									   	};
	
	@BeforeClass  
	public void setUp() {
		
	} 
	@Test(groups = { "Iter_24" })
	/**
	 * Master script method sequentially calling steps methods
	 */
	public void s2594_01NG() {
		openPO();
		addNewLookup();
		setLookupValues();
		modifyLookup();
		assessLookupValues();
		closeG3();
	}
	/**
	 * Open Browser and open G3 PO
	 * Log in as admin user
	 */
	private void openPO()
	{
		try
		{
			IEUtil.openNewBrowser();
			ie = IEUtil.getActiveIE();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginPO();
		}
		catch (Exception e) {	
			
			log.error("Unexpected Exception in naavigateToLookups() ", e);
			throw new RuntimeException("Unexpected Exception", e);
		} 
	}
	
	private void addNewLookup()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk); 		
			log.info("Navigating to Lookups screen");
			lookup = new Lookups();
			lookup.setLookupIdent(lookupConstant);
			lookup.setLookupName(lookupName);
			lookup.setLookupPrimeOrg(IGeneralConst.primG3_OrgRoot);
			lookup.setLookupOrgAccess(IGeneralConst.org_Access_Public);
		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in naavigateToLookups() " + ex.getMessage());
		}
	}
	
	private void setLookupValues()
	{
		try
		{
			lookup.setLookupEntries(lookupValues);
			lookup.createNewLookup();//create new Lookup with Values
			//Check if values in Select controls are as expected
			if(ie.selectList(id, IEFormsConst.formPrimaryOrg_DropdownField_Id).exists() && 
					GeneralUtil.getSelectedItemValueInDropdwonById(IEFormsConst.formPrimaryOrg_DropdownField_Id).equals(IGeneralConst.primG3_OrgRoot) &&
					ie.selectList(id, IEFormsConst.formOrgAccess_DropdownField_Id).exists() &&
					GeneralUtil.getSelectedItemValueInDropdwonById(IEFormsConst.formOrgAccess_DropdownField_Id).equals(IGeneralConst.org_Access_Public))
				log.info("TEST PASSED  " + GeneralUtil.getSelectedItemValueInDropdwonById(IEFormsConst.formPrimaryOrg_DropdownField_Id) +
						" " + GeneralUtil.getSelectedItemValueInDropdwonById(IEFormsConst.formOrgAccess_DropdownField_Id));
			else
				log.info("TEST FAILED  " + GeneralUtil.getSelectedItemValueInDropdwonById(IEFormsConst.formPrimaryOrg_DropdownField_Id) +
						"  " + GeneralUtil.getSelectedItemValueInDropdwonById(IEFormsConst.formOrgAccess_DropdownField_Id));
		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in setLookupValues() " + ex.getMessage());
		}
	}

	private void modifyLookup()
	{
		try
		{
			//Navigate to Lookups screen again to modify existing lookup
			ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk); 
			if(ie.link(lookupName).exists())//Find link element by portion of ALT TAG
			{
				log.info("Lookup found");
				ClicksUtil.clickLinks(lookupName);
				ie.selectList(id, IEFormsConst.formOrgAccess_DropdownField_Id).select(IGeneralConst.org_Access_Shared);
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				ClicksUtil.clickButtons(IClicksConst.backBtn);
			}
			else
				log.info("TEST FAILED: unable to locate a Lookup - " + lookupName);
			
		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in modifyLookup() " + ex.getMessage());
		}
	}
	
	private void assessLookupValues()
	{
		try
		{
			ClicksUtil.clickImageByAlt(ILookupsConst.openLookupValuesList + IGeneralConst.dASH + lookupName);
			checkLookupValues();
			ie.selectList(id, ILookupsConst.filterLookupStatus).select(IGeneralConst.statusInactive);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			checkLookupValues();
			ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);

		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in assessLookupValues() " + ex.getMessage());
		}
	}
	
	private void closeG3()
	{
		try
		{
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in closeG3() " + ex.getMessage());
		}
	}
	
	private void checkLookupValues()
	{
		try
		{
			for(int i=0; i<lookupValues.length; i++)
			{
				if(ie.link(lookupValues[i][0]).exists())
				{
					ClicksUtil.clickLinks(lookupValues[i][0]);
					//Check if values in Select controls are as expected
					if(ie.selectList(id, IEFormsConst.formPrimaryOrg_DropdownField_Id).exists() && 
						GeneralUtil.getSelectedItemValueInDropdwonById(IEFormsConst.formPrimaryOrg_DropdownField_Id).equals(IGeneralConst.primG3_OrgRoot) &&
						ie.selectList(id, IEFormsConst.formOrgAccess_DropdownField_Id).exists() &&
						GeneralUtil.getSelectedItemValueInDropdwonById(IEFormsConst.formOrgAccess_DropdownField_Id).equals(IGeneralConst.org_Access_Shared))
						
					log.info("TEST PASSED  " + GeneralUtil.getSelectedItemValueInDropdwonById(IEFormsConst.formPrimaryOrg_DropdownField_Id) +
							" " + GeneralUtil.getSelectedItemValueInDropdwonById(IEFormsConst.formOrgAccess_DropdownField_Id));
					else
						log.info("TEST FAILED  " + GeneralUtil.getSelectedItemValueInDropdwonById(IEFormsConst.formPrimaryOrg_DropdownField_Id) +
								" " + GeneralUtil.getSelectedItemValueInDropdwonById(IEFormsConst.formOrgAccess_DropdownField_Id));
					ClicksUtil.clickButtons(IClicksConst.backBtn);
				}
			}
		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in assessLookupValues() " + ex.getMessage());
		}
	}
}
