/**
 * 
 */
package test_Suite.tests.applicants;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TransferProjectsNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends TransferProjectsNG> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	
	@BeforeClass(groups = {"ApplicantsNG"})
	public void setUp()
	{
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			//IEUtil.openNewBrowser();
			//GeneralUtil.navigateToPO();
			//GeneralUtil.logInSuper();

			// ---------------------------------
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	@AfterClass(groups = {"ApplicantsNG"}, alwaysRun=true)
	public void tearDown() {
		//GeneralUtil.Logoff();
		//IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"ApplicantsNG"}, enabled=false)
	public void testTransferProject() throws Exception {
		
		//this required 5.8
		//throw new SkipException("Test has not been implemented yet");
		
		Reporter.log("No Implemintation yet for TransferProjectsNG.testTransferProject");

	}

}
