package test_Suite.tests.stories.release_1_5.iter_1_2;

/*
 * Test Case 08 (Users List) For Story #1887. Search/Filter: sweep existing lists
 * Depends On Story_1887_01
 */

import org.fest.swing.annotation.GUITest;
import org.testng.annotations.*;

import java.util.*;

import test_Suite.constants.ui.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.cases.*;


@GUITest
@Test(singleThreaded = true)
public class S1887_08NG {

	//class fileds that will be used in the Filter Criteria

	private static String lastName[] = {"ProjectName"};
	//private static String firstName[] = {"aSHAK", "eCHRYS", "kRAJ", "iYOGA"};
	//private static String userName[] = {"QABulkLoad-S.A", "QABulkLoad-S.Rm", "QABulkLoad-S.a"};
	//private static String orgName[] = {"Test Case 1 Fields", "Test Case 1 Fields"};

	@BeforeClass  
	public void setUp() {    
		// code that will be invoked when this test is instantiated  
	} 

	@Test(groups = { "Iter_12" })
	public void s1887_8NG() throws Exception {

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();

		//#####***********************************************************************
		//###   To set up the local Params Name Vars
		//#####***********************************************************************
		
		int startIndx = 0;
		int endIndx = 3;
		//------------------- End of Local Params -----------------------------------
		
		Hashtable<String, String> fieldsValues = new Hashtable<String, String>();
		Hashtable<String, String> modesValues  = new Hashtable<String, String>();
		
		ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);
		
		for(int j=0; j<IFiltersConst.filterModes.length; j++) {
			
			for(int i=0; i<lastName.length; i++){
				
				fieldsValues.put(IFiltersConst.administration_LastName_Lbl, lastName[i].substring(startIndx, endIndx));
				
				modesValues.put(IFiltersConst.administration_LastName_Lbl, IFiltersConst.filterModes[j]);				
				
				FiltersUtil.filterListByLabel(fieldsValues, modesValues, false);
				
				if(TablesUtil.findInTable(ITablesConst.usersTableId, lastName[i]))
					System.out.println("Pass: " + IFiltersConst.filterModes[j] + " : " + lastName[i] + " Found In List");
				else
					System.out.println("Fail: " + IFiltersConst.filterModes[j] + " : " + lastName[i] + " Not Found In List");
			}
			
			startIndx += 1;
			endIndx += 2;
		}	
			
	}
	
}
