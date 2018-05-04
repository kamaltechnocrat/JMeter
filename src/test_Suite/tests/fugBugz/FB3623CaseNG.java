/**
 *  Steps
 *  1. Open G3 PO as admin user
 *	2. Add new Lookup or use any existing Lookup from available
 *	3. Click on the icon 'Lookup Values'
 *	4. Click on the icon 'Add Lookup Value'
 *	5. Make sure 'Primary Organization:' and 'Organizational Access:' fields retain values from Lookup screen for chosen Lookup - this is the key
 *	6. Fill the form and click 'Save & Add Another'
 *	NOTE: Bug Fixed
 */
package test_Suite.tests.fugBugz;

import static watij.finders.SymbolFactory.id;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.ui.*;
import test_Suite.constants.cases.*;
import test_Suite.utils.cases.*;
import test_Suite.utils.ui.*;
import test_Suite.lib.cases.Lookups;
import watij.runtime.ie.IE;

/**
 * @author apankov
 * Script per Bug 3623
 */
public class FB3623CaseNG {
	private static Log log = LogFactory.getLog(FB3623CaseNG.class);
	private String lookupConstant = "aaa"; 
	private String lookupName = "aaa"; //First letter a to prevent entry from not being on the first page
	private String lookupValues [][] = 	{{"n-1", "n-1", "n-1", "1"},
										 {"n-2", "n-2", "n-2", "1"}
									   	};
	Lookups lookup = null;
	
	/**
	 * Default constructor
	 */
	@BeforeClass  
	public void setUp() {    
		
	} 
	
	@Test(groups = { "FBCases" })
	public void fB3623CaseNG() {
		try{			
			IEUtil.openNewBrowser();
			IE ie = IEUtil.getActiveIE();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginPO();

			//#####***********************************************************************
			//###   To set up the Global Params Name Vars
			//#####***********************************************************************
			ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk); 		
			log.info("Navigate to Lookups screen");
			lookup = new Lookups();
			lookup.setLookupIdent(lookupConstant);
			lookup.setLookupName(lookupName);
			lookup.setLookupPrimeOrg(IGeneralConst.primG3_OrgRoot);
			lookup.setLookupOrgAccess(IGeneralConst.org_Access_Public);
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

		catch (Exception e) {	
			
			log.error("Unexpected Exception", e);
			throw new RuntimeException("Unexpected Exception", e);
		} 
		finally {
			
			lookup = null;
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		}
	}
}

