/**
 *     Steps:
 * 1. Open G3 PO
 * 2. Go to Lookups screen
 * 3. Add new Lookup 
 * 4. Make this first new Lookup INACTIVE
 * 5. Create another Lookup with the same name
 * 6. Make sure it is in the list
 * 7. Switch filter to display Inactive lookups
 * 8. Make inactive lookup from step #3 to be ACTIVE
 * 9. Check if two Lookups with the same name are listed as ACTIVE
 */
package test_Suite.tests.fugBugz;

import static watij.finders.SymbolFactory.id;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.*;

//import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import test_Suite.constants.ui.*;
import test_Suite.constants.cases.*;
import test_Suite.utils.cases.*;
import test_Suite.utils.ui.*;
import test_Suite.lib.cases.Lookups;
import watij.elements.Table;
import watij.runtime.ie.IE;

import test_Suite.constants.cases.ILookupsConst;

/**
 * @author apankov
 *
 */
public class FB3595CaseNG implements ILookupsConst{
	
	private static Log log = LogFactory.getLog(FB3595CaseNG.class);
	
	IE ie;
	
	private String [] lookupConstant = {"aaaabc", "aaaabccc"};
	
	private String lookupName = "aaaabc";
	
	Lookups lookup = null;
	
	private int numberOfLookups;
	
	/**
	 * Default constructor method
	 */

	@BeforeClass  
	public void setUp() {    
		
	} 
	
	@Test(groups = { "FBCases" })
    public void fB3595CaseNG() throws Exception {
		
		try{			
			IEUtil.openNewBrowser();
			ie = IEUtil.getActiveIE();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginPO();
			
			//#####***********************************************************************
			//###   To set up the Global Params Name Vars
			//#####***********************************************************************
			ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk); 		
			log.info("Navigate to Lookups screen to create first instance");
			createLookup(lookupConstant[0]);
			
			Table table = ie.table(id, ITablesConst.lookupListTableId);

			if(table != null)
			{
				if(FindNewRecordInTable(lookupName, table, true) == true)
				{
					log.info("New Lookup found. Proceeding with the test");
					
					ie.selectList(id, filterLookupStatus).select(IGeneralConst.statusActive);
					ie.selectList(id, filterLookupOrg).select(IFiltersConst.allLookupStat);
					ClicksUtil.clickButtons(IClicksConst.filterBtn);
					
					//Hashtable FilterValues = new Hashtable();		
					//FilterValues.put(0, IFiltersConst.activeLookupStat);
					//FilterValues.put(2, IFiltersConst.allLookupStat);

					
					//Trying to add a second lookup with the same name
					ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk); 		
					log.info("Navigate to Lookups screen to create second instance");
					createLookup(lookupConstant[1]);
					if(FindNewRecordInTable(lookupName, table, false) == true)
					{
						//Reactivate first Lookup
						log.info("Second instance of the same Lookup has been added");
						ie.selectList(id, filterLookupStatus).select(IGeneralConst.statusInactive);
						ie.selectList(id, filterLookupOrg).select(IFiltersConst.allLookupStat);
						ClicksUtil.clickButtons(IClicksConst.filterBtn);
						FindNewRecordInTable(lookupName, table, true);
						//Check if the list of Active Lookups contais more than one record
						ie.selectList(id, filterLookupStatus).select(IGeneralConst.statusActive);
						ie.selectList(id, filterLookupOrg).select(IFiltersConst.allLookupStat);
						ClicksUtil.clickButtons(IClicksConst.filterBtn);
						if((FindNewRecordInTable(lookupName, table, false) == true) && (numberOfLookups > 1))
						{
							log.info("Number of active Lookups with same name: " + numberOfLookups + " - TEST FAILED " );
						}
						else
							log.info("No more than one active Lookup is available. - TEST PASSED " + numberOfLookups);
					}
					else
						log.info("Second instance of teh same lookup blocked to add. - TEST PASSED");
				}
				else
					log.info("Lookup not found. Unable to proceed with the test scenario");
			}
			else
				log.info("Table is NULL. Failure to find required datble in HTML code");
		} 

				
		catch (Exception e) {	
			
			log.error("Unexpected Exception", e);
			throw new RuntimeException("Unexpected Exception", e);
		} 
		finally {
			//GeneralUtil.Logoff();
			//IEUtil.closeBrowser();
		}
	}
	
	private boolean FindNewRecordInTable(String lookupName, final Table tableLookups, boolean withCheck)
	{
		boolean rIndexFound = false;
		try
		{
			numberOfLookups = 0; //reset value when adding new lookup before the searching loop
			for (int i = 0; i < tableLookups.rowCount(); i++) 
			{
				log.info("Value of: " + tableLookups.row(i).cell(fieldsLookupTable.LookupName.ordinal()).innerText());
				if (tableLookups.row(i).cell(fieldsLookupTable.LookupName.ordinal()).innerText().matches(lookupName)) 
				{
					numberOfLookups ++;
					rIndexFound = true;
					if(withCheck == true)
					{
						final int rowIndex = i;

						if (rowIndex > -1) {
							new Thread(new Runnable() { 
								public void run() { 
									try { 
											ie = IEUtil.getActiveIE();
											tableLookups.row(rowIndex).checkbox(0).click();
			    						} 
									catch (Exception e) { 
									} 
								} 
							}).start();
							GeneralUtil.takeANap(1.0);
							ie.sendKeys("Microsoft Internet Explorer", " ", false); 
							break;
			    		}
					}
				}
			}
		}
		catch(Exception ex)
		{
			log.debug("ERROR: " + ex.getMessage());
		}
		return rIndexFound;
	}

	private void createLookup(String lookupConst)
	{
		try
		{
			lookup = new Lookups();
			lookup.setLookupIdent(lookupConst);
			lookup.setLookupName(lookupName);
			lookup.setLookupPrimeOrg(IGeneralConst.primG3_OrgRoot);
			lookup.setLookupOrgAccess(IGeneralConst.org_Access_Public);
			lookup.createNewLookup();

			ClicksUtil.clickButtons(IClicksConst.backBtn);//navigate back to Lookups screen
			ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);// clear previous filter (if any applied)
		}
		catch(Exception ex)
		{
			log.debug("ERROR ADDING LOOKUP " + ex.getMessage());
		} finally {
			
			lookup = null;
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		}
	
	}
}

