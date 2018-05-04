package test_Suite.tests.r5_2.DataArchive.Archive_Configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author sFatima
 * @author s.grobbelaar
 *
 */

@GUITest
@Test(singleThreaded = true)
public class Reset_ArchiveConfiguration {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	
	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			
			GeneralUtil.navigateToPO();
			
			GeneralUtil.logInSuper();
		
			// -----------------------------------
			
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
	public void reset_ArchiveConfiguration() throws Exception {
		
		Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.archive_Configuration_title));
		
		Assert.assertTrue(ClicksUtil.clickButtons("Reset"));
		
		Assert.assertTrue(GeneralUtil.setTextById(IGeneralConst.archiveConfig_ContentInfo_Id, "Contact Information"));
		
		Assert.assertTrue(GeneralUtil.setTextById(IGeneralConst.archiveConfig_Disclaimer_Id, "Disclaimer"));
		
		
	}
	
	
	
	
}