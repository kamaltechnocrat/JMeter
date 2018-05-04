/**
 * 
 */
package test_Suite.tests.r3_0;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class CfgRunFirst {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	
	FOProject foProj;
	
	Users front;
	
	Users clerk;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "ProjectsNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToFO();
			GeneralUtil.loginAnyFO("front3");
			// -----------------------------------
			
			fopp = new FundingOpportunity("CFG-", "Post-Award-Step-Amendment", "", "");
			
			ClicksUtil.clickLinks(IClicksConst.openRegistrationListLnk);
			
			FrontOfficeUtil.registerApplicantToProgram(fopp.getFoppFullName(), "Ouia 3");

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "ProjectsNG" }, alwaysRun=true)
	public void tearDown() {
		
		clerk = null;
		front = null;
		fopp = null;
		foProj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test(groups = { "ProjectsNG" })
	public void associateRegistrantWithApplicant() throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openFOApplicantsListLnk);

		log.info("opened applicants link");
		
		Assert.assertTrue(FrontOfficeUtil
				.openRegistrantsList(IPreTestConst.FO_OrgShortName
						+ Integer.toString(3)),	"FAIL:Applicant Could not be Found!");

		log.info("clicking new registrant");

		ClicksUtil.clickImageByAlt(IClicksConst.associateRegistrant_ImgAlt);
		
		FrontOfficeUtil.associateRigestrant(IPreTestConst.FrontUsers[1][4]
						+ Integer.toString(5), IPreTestConst.FrontUsers[1][0]
						+ Integer.toString(5)
						+ IPreTestConst.FrontUsers[1][3]);

		ClicksUtil.clickButtons(IClicksConst.addRegistrantBtn);

		log.info("clicked add registrant button");

		ClicksUtil.clickButtons(IClicksConst.backToListBtn);
	}

}
