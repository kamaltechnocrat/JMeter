/**
 * Note: this test is not a GUI test but rather is a direct database access test
 * Steps:
 * 1. Open g3 GUI, log in as admin user, navigate to Lookups screen
 * 2. Check if all records in dataset have Primary Org = 'G3' and Organization Access = 'Pablic'
 * 3. Open g3 GUI, log in as admin user
 * 4. Make sure Lookups datagrid (table) does not contain any of records from dataset
 * 	  returned from the query for Non-editable Lookups and contains all Editable lookups
 * 5. Close GUI
 */
package test_Suite.tests.stories.release_2_0.iter_4;
import static watij.finders.SymbolFactory.id;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Properties;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.constants.db.IDbConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.lib.db.DBSqlBase;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.cases.LookupUtil;
import watij.elements.Table;
import watij.runtime.ie.IE;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author apankov
 * Checking 
 */
@Test(singleThreaded = true)
public class S2595_01NG {
	private static Log log = LogFactory.getLog(S2595_01NG.class);
	private static final int ORGANIZATION_ID_G3 = 1;
	private static final int ACCESS_LEVEL_ID_PUBLIC = 8;
	private static final int LOOKUP_NON_EDITABLE =0;
	private static final int LOOKUP_EDITABLE =1;
	private final static String sqlQueryBase = "select LOOKUP_NAME, ORGANIZATION_ID, ACCESS_LEVEL_ID from LOOKUPS";
	private final static String sqlQueryFilterNonEditable = " where (SYSTEM_FLAG = 1 and EDIT_FLAG = 0)";
	private final static String sqlQueryFilterEditable = " where (SYSTEM_FLAG = 1 and EDIT_FLAG = 1)";
	DBSqlBase dbHandler;
	IE ie;
	ResultSet rs;
	Properties connProperties;
	private enum eDbQueryFields
	{
		LOOKUP_ID,		//  = 0 (default)
		LOOKUP_NAME,     // = 1
		ORGANIZATION_ID, // = 2
		ACCESS_LEVEL_ID  // = 3
	}
	
	@BeforeClass  
	public void setUp() {
		
	} 
	
	@Test(groups = { "Iter_24" })
	/**
	 * Parent method containing calls to child methods
	 */
	public void s2595_01NG() {
		openPO();
		queryDatabase();
		dbHandler = null;
		rs = null;
		connProperties = null;
		closeG3();
	}
	/**
	 * Method to get System and Non-editable Lookups as dataset
	 *
	 */
	private void queryDatabase()
	{
		try
		{
			connProperties = GeneralUtil.initProperties(IDbConst.DB_CONN_INIT_PROP);
			dbHandler = new DBSqlBase();
			dbHandler.setDbMode(IDbConst.dbConnMode.DB_MODE_DIRECT.ordinal());
			dbHandler.setUser(connProperties.getProperty(IDbConst.DB_USER_KEY));
			dbHandler.setPassword(connProperties.getProperty(IDbConst.DB_PASSWORD_KEY));
			dbHandler.setUrl(connProperties.getProperty(IDbConst.DB_URL_KEY));
			log.info("*********TESTING NON_EDITABLE LOOKUPS************");
			rs = dbHandler.getResultSet(sqlQueryBase + sqlQueryFilterNonEditable); //non-editable
			validateRecords(LOOKUP_NON_EDITABLE);
			log.info("*********TESTING EDITABLE LOOKUPS************");
			rs = dbHandler.getResultSet(sqlQueryBase + sqlQueryFilterEditable); //editable
			validateRecords(LOOKUP_EDITABLE);

		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in queryDatabase() " + ex.getMessage());
		}
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
			ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk); //One click moved to this method in purpose
		}
		catch (Exception e) {	
			
			log.error("Unexpected Exception in naavigateToLookups() ", e);
			throw new RuntimeException("Unexpected Exception", e);
		} 
	}
	/**
	 * Varify if Lookup records have proper vlues in ORGANIZATION_ID and ACCESS_LEVEL_ID fields
	 * @param lookupType
	 */
	private void validateRecords(int lookupType)
	{
		try
		{
		
			log.info("Navigating to Lookups screen");
	    	//ResultSetMetaData rsmd = rs.getMetaData();
	    	while(this.rs.next())
	    	{
	            String lookupName = this.rs.getString(eDbQueryFields.LOOKUP_NAME.ordinal());
	            int lookupOrg = this.rs.getInt(eDbQueryFields.ORGANIZATION_ID.ordinal());
	            int lookupAccess = this.rs.getInt(eDbQueryFields.ACCESS_LEVEL_ID.ordinal());
	            //Test for ORG Name and ORG Access
	            if(lookupOrg == ORGANIZATION_ID_G3 && lookupAccess == ACCESS_LEVEL_ID_PUBLIC)
	            	log.info("TEST PASSED " + lookupName + " " + lookupOrg + " " + lookupAccess);
	            else
	            	log.info("TEST FAILED " + lookupName + " " + lookupOrg + " " + lookupAccess);
	            findRecordsInTable(lookupType, lookupName);
	    	}
		}
		catch (SQLException sqle)
		{
			log.error("SQL EXCEPTION: " + sqle.getMessage());
		}
		catch (Exception ex)
		{
			log.error("EXCEPTION:" + ex.getMessage());
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
			dbHandler.cleanUp();
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in closeG3() " + ex.getMessage());
		}
	}
	/**
	 * Verifying Lookups records from db upon exitence in Lookups data grid
	 * @param loolupIsEditeble
	 */
	private void findRecordsInTable(int loolupIsEditeble, String lookup)
	{
		try
		{
			Table table = ie.table(id, ITablesConst.lookupListTableId);
			switch(loolupIsEditeble)
			{
				case LOOKUP_NON_EDITABLE:
		            //check if Lookup does not show up in a Lookups grid
		            if(LookupUtil.FindNewRecordInTable(lookup, table) == true)
		            	log.info("TEST FAILED " + lookup + " can be viewed through GUI");
		            else
		            	log.info("TEST PASSED " + lookup + " cannot be viewed through GUI");
					break;
				case LOOKUP_EDITABLE:
		            if(LookupUtil.FindNewRecordInTable(lookup, table) == true)
		            	log.info("TEST PASSED " + lookup + " can be viewed through GUI");
		            else
		            	log.info("TEST FAILED " + lookup + " cannot be viewed through GUI");	
					break;
				default:
					break;
			}
		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in findRecordsInTable() " + ex.getMessage());
		}
	}
}
