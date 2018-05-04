package test_Suite.tests.stories.release_1_5.iter_1_2;



/*
 * Test Case 02 (In Basket List) For Story #1887. Search/Filter: sweep existing lists
 * Depends On Story_1887_01 and the list must have multible pages
 * Steps:
 *  1. Use each Filter Criteria with every Mode for that Filter
 *  	Test The Result after each execution
 *  2. Combination of Search
 *  	Test The Result after each execution
 */

import org.fest.swing.annotation.GUITest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.*;
import test_Suite.constants.ui.*;
import test_Suite.utils.ui.*;
import test_Suite.utils.cases.*;


@GUITest
@Test(singleThreaded = true)
public class S1887_02NG {

	//class fileds that will be used in the Filter Criteria

	private static String projName[] = {"ProjectName"};
	//private static String appName[] = {"aSHAK", "eCHRYS", "kRAJ", "iYOGA"};
	//private static String progName[] = {"QABulkLoad-S.A", "QABulkLoad-S.Rm", "QABulkLoad-S.a"};
	//private static String formName[] = {"Test Case 1 Fields"};

	@BeforeClass  
	public void setUp() {    
		// code that will be invoked when this test is instantiated  
	} 

	@Test(groups = { "Iter_12" })
	public void s1887_2NG() throws Exception {

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.loginPO();

		//#####***********************************************************************
		//###   To set up the local Params Name Vars
		//#####***********************************************************************
		
		int startIndx = 0;
		int endIndx = 3;
		//------------------- End of Local Params -----------------------------------
		
		Hashtable<String, String> fieldsValues = new Hashtable<String, String>();
		Hashtable<String, String> modesValues  = new Hashtable<String, String>();
		
		ClicksUtil.clickLinks(IClicksConst.openInBasketListLnk);
		
		for(int j=0; j<IFiltersConst.filterModes.length; j++) {
			
			for(int i=0; i<projName.length; i++){
				
				fieldsValues.put(IFiltersConst.grantManagement_ProjectName_Lbl, projName[i].substring(startIndx, endIndx));
				
				modesValues.put(IFiltersConst.grantManagement_ProjectName_Lbl, IFiltersConst.filterModes[j]);				
				
				FiltersUtil.filterListByLabel(fieldsValues, modesValues, false);
				
				if(TablesUtil.findInTable(ITablesConst.fundingOpp_IntakeTableId, projName[i]))
					System.out.println("Pass: " + IFiltersConst.filterModes[j] + " : " + projName[i].substring(startIndx, endIndx) + " Found In List");
				else
					System.out.println("Fail: " + IFiltersConst.filterModes[j] + " : " + projName[i].substring(startIndx, endIndx) + " Not Found In List");
			}
			
			startIndx += 1;
			endIndx += 2;
		}	
			
	}
}
