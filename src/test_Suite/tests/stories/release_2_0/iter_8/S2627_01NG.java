/**
 * 
 */
package test_Suite.tests.stories.release_2_0.iter_8;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.lib.workflow.ProjectActivity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.ProjActivUtil;

/**
 * @Story #2627. Send an ad hoc, project-related e-mail on demand
 **/

/**
 * @author mshakshouki
 *
 */
@Test(singleThreaded = true)
public class S2627_01NG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);

	FundingOpportunity fopp;
	Project proj;
	ProjectActivity projActivity;
	
	
	@BeforeClass(groups = { "NotificationsNG" })
	public void setUp() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// ----------------------------------

		fopp = new FundingOpportunity("A", "-Gnrl-", "");

		proj = new Project(fopp, "Add-Hoc-Notif-", true,true,EFormsUtil.createAnyRandomString(5));
	}

	@AfterClass(groups = { "NotificationsNG" }, alwaysRun=true)
	public void tearDown() throws Exception {
		
		fopp = null;
		proj = null;
		projActivity = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"NotificationsNG"})
	public void submitProject() throws Exception {
		try {
			
			proj.newCreateNewOrg();
			proj.newCreateNewPOProject();
			proj.submitProject(true);

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		
	}
	
	@Test(groups = {"NotificationsNG"},dependsOnMethods="submitProject")
	public void assignOfficers() throws Exception {
		proj
		.assignOfficers(new String[][] {
				{ IPreTestConst.Groups[0][0][0],
						IPreTestConst.Groups[0][1][0] } });
		
	}
	
	@Test(groups = {"NotificationsNG"},dependsOnMethods="assignOfficers")
	public void testSendProjectInstantNotificationNG() throws Exception {
		
		initializeProjectActivity();
		
		ProjActivUtil.openInstantNotificationDetails(proj.getProjFullName(), IFiltersConst.openProjView);
		
		ProjActivUtil.AddProjectInstantNotif(projActivity);
		
	}

	private void initializeProjectActivity()throws Exception {
		
		projActivity = new ProjectActivity();
		
		projActivity.setProjectName(proj.getProjFullName());
		projActivity.setApplicantName(proj.getOrgFullName());
		projActivity.setProgramName(fopp.getFoppFullName());
		
		projActivity.setActivityNotifName(proj.getProjFullName());

		projActivity.setActivityNotifMessageLocale("/English/");
		projActivity.setActivityNotifRecipients(new String[] {"/Shakshouki/"});
		projActivity.setActivityNotifMessageSubjet("Testing AddHoc Notif " + proj.getProjFullName());
		projActivity.setActivityNotifMessageBody("This is The Body");
		projActivity.setActivityNotifExternalRecipients("");
		
	}

}
