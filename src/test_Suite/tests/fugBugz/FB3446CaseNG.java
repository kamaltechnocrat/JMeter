
/**
 * 
 ** Steps:
 * Open PO 
 * log is as admin
 * navigate to Lookups screen
 * add new lookup to the list
 * validate if the new lookup was actually added
 */
package test_Suite.tests.fugBugz;

import static watij.finders.SymbolFactory.id;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.*;

import test_Suite.constants.ui.*;
import test_Suite.constants.cases.*;
import test_Suite.utils.cases.*;
import test_Suite.utils.ui.*;
import test_Suite.lib.cases.Lookups;
import watij.elements.Table;
import watij.runtime.ie.IE;

/**
 * @author apankov
 *
 */
public class FB3446CaseNG {
private static Log log = LogFactory.getLog(FB3446CaseNG.class);

private String lookupConstant = "aaaaaaaa"; 
private String lookupName = "aaaaaaaa"; //First letter a to prevent entry from not being on the first page
Lookups lookup = null;


	@BeforeClass  
	public void setUp() {    
		
	} 
	
	@Test(groups = { "FBCases" })
    public void fB3446CaseNG() throws Exception {
		
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
			Table table = ie.table(id, ITablesConst.lookupListTableId);
			if(table != null)
			{
				if(LookupUtil.FindNewRecordInTable(lookupName, table) == true)
					log.info("TEST SCENARIO IS INVALID. Create a Lookup with NEW Name please");
				else
				{
					lookup = new Lookups();
					lookup.setLookupIdent(lookupConstant);
					lookup.setLookupName(lookupName);
					lookup.setLookupPrimeOrg(IGeneralConst.primG3_OrgRoot);
					lookup.setLookupOrgAccess(IGeneralConst.org_Access_Public);
					lookup.createNewLookup();
					log.info("New Lookup to be entered");
					ClicksUtil.clickButtons(IClicksConst.backBtn); //go back to Lookups screen
					ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk); //Just in case to clear filters if any


					if(LookupUtil.FindNewRecordInTable(lookupName, table) == true)
						log.info("TEST PASSED");
					else
						log.info("TEST FAILED");
				}
			}
			else
				log.info("Table is NULL");
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

