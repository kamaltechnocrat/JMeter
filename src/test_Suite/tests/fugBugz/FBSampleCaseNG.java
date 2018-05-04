/*
 * Case number and steps goes here
 */
package test_Suite.tests.fugBugz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test_Suite.utils.cases.*;
import test_Suite.utils.ui.*;

public class FBSampleCaseNG {
	private static Log log = LogFactory.getLog(FBSampleCaseNG.class);
	

	@BeforeClass  
	public void setUp() {    
		// code that will be invoked when this test is instantiated  
	} 
	
	@Test(groups = { "FBCases" })
    public void fBSampleCaseNG() throws Exception {
		
		try{			

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		} finally {
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		}
	}
}
