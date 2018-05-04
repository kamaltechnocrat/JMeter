package test_Suite.tests.r5_2.DataArchive.Verify_Archive_Log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test_Suite.lib.cases.Documents;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author sFatima
 *
 */

@GUITest
@Test(singleThreaded = true)
public class Verify_ArchiveLog {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	Documents docs;
	
	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			
			GeneralUtil.navigateToPO();
			
			GeneralUtil.LoginAny("shak");
			// -----------------------------------
			
			fopp = new FundingOpportunity("A", "-Gnrl-PA-","");

			foProj = new FOProject(fopp, "", true, 1, EFormsUtil.createAnyRandomString(5));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" }, alwaysRun=true)
	public void tearDown() {
		
		foProj = null;
		fopp = null;
		
     	GeneralUtil.Logoff();
    	IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	
	@Test(groups = { "WorkflowNG" })
	public void download_archiveLog() throws Exception {
	
       foProj.filterProject_ArchiveLog("A-Gnrl-PA-FOPP", docs);
        
     
	}
	
	
	
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
}