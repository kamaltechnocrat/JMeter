/**
 * 
 */
package test_Suite.tests.r4_0.projDataStorage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class CreateOrgAndSubmitProjNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	Project proj;

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A", IFoppConst.projData_PA_Prefix, IFoppConst.projData_pushBack_Postfix);
			
			proj = new Project(fopp, "", true);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		try {
			
			proj = null;
			fopp = null;
			
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
			
			log.warn("Ending: " + this.getClass().getSimpleName());
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void createNewProjectAndSubmit() throws Exception {
		try {
			
			proj.setOrgName(IFoppConst.projData_PA_Prefix.concat("Org").concat(IFoppConst.projData_pushBack_Postfix));
			
			for(int x=0; x <= 50; x++ )
			{				
				proj.createOrgFullName(true);
				
				proj.createPOProjectOnly(true);
				
				proj.submitProject(true);
			}
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}		
	}

}
