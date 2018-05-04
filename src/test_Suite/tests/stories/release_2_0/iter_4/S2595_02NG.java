/**
 * Steps:
 * 1. Open G3 PO as admin user, navigate to Lookups screen
 * 2. Ensure that Check boxes beside System records are checked and disabled
 * 3. Check if all mocked up Lookups have properly assigned G3 and Public
 * 4. Log off and close browser
 * Note: this mock-up sulution can be replaced by database solution with taking parameters from
 * SQL queries rather than hard-coded Lookups
 */
package test_Suite.tests.stories.release_2_0.iter_4;

import static watij.finders.SymbolFactory.id;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.cases.ILookupsConst;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.runtime.ie.IE;
import watij.elements.*;

/**
 * @author apankov
 *
 */
@Test(singleThreaded = true)
public class S2595_02NG {
	private static Log log = LogFactory.getLog(S2595_02NG.class);
	IE ie;
	private String [] systemEditableLookups = {"Activity Types", "Budget Strings", "Canadian Provinces"};
	@BeforeClass  
	public void setUp() {
		
	} 
	@Test(groups = { "Iter_24" })
	/**
	 * Master Method to call otehr methods sequentially
	 * Commenting/Uncommenting methods inside master method allows to simplify debugging
	 */
	public void s2595_02NG() {
		openPO();
		verifyLookupProperties();
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
	/**
	 * Navigate to Lookups screen
	 * Start verifications
	 */
	private void verifyLookupProperties()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk); 		
			log.info("Navigating to Lookups screen");
			for(int i=0; i<systemEditableLookups.length; i++)
			{
				//First check if Checkbox is set and disabled
				int rowIndx = -1;
				rowIndx = TablesUtil.getRowIndex(ITablesConst.lookupListTableId, systemEditableLookups[i]);
				if(rowIndx > -1)
				{
					//log.info("Lookup " + systemEditableLookups[i] + " is positioned in row " + rowIndx);
					HtmlElement ch = ie.table(id, ITablesConst.lookupListTableId).row(rowIndx).cell(ILookupsConst.fieldsLookupTable.Active.ordinal()).checkbox(id, ILookupsConst.valueDisableCh2);
					if(ch.checkbox(0).disabled() && ch.checkbox(0).checked())
						log.info("TEST PASSED: Checkbox is checked and disabled " + ch.id());
					else
						log.info("TEST FAILED: Checkbox does not exist or either is not checked or enabled ");
					//Check if all controls are disabled and G3 and Public selected	
					ClicksUtil.clickLinks(systemEditableLookups[i]);
					checkLookupDetails(systemEditableLookups[i]);
				}
			}
		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in verifyLookupProperties() " + ex.getMessage());
		}
	}
	/**
	 * Close G3 method
	 *
	 */
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
	/**
	 * Check states and values of controls on Lookup details screen
	 * @param lookupName
	 */
	private void checkLookupDetails(String lookupName)
	{
		try
		{
			//Verify fields properties
			if(ie.textField(id, ILookupsConst.fieldLookupIdent).disabled() &&
					ie.textField(id, ILookupsConst.fieldLookupName).disabled() &&
					ie.textField(id, ILookupsConst.fieldLookupName).value().equals(lookupName) &&
					ie.selectList(id, IEFormsConst.formPrimaryOrg_DropdownField_Id).disabled() &&
					GeneralUtil.getSelectedItemValueInDropdwonById(IEFormsConst.formPrimaryOrg_DropdownField_Id).equals(IGeneralConst.primG3_OrgRoot) &&
					ie.selectList(id, IEFormsConst.formOrgAccess_DropdownField_Id).disabled() &&
					GeneralUtil.getSelectedItemValueInDropdwonById(IEFormsConst.formOrgAccess_DropdownField_Id).equals(IGeneralConst.org_Access_Public)
					)
				log.info("TEST PASSED for properties of Lookup: " + lookupName);
			else
				log.info("TEST FAILED for properties of Lookup: " + lookupName);
			ClicksUtil.clickButtons(IClicksConst.backBtn);
		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in checkLookupDetails() " + ex.getMessage());
		}
	}
}
