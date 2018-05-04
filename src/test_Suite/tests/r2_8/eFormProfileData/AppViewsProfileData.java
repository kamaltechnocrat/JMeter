/**
 * 
 */
package test_Suite.tests.r2_8.eFormProfileData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.eForms.IEFormsConst.EDisplayProjInfo;
import test_Suite.lib.eForms.EForm;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.IEUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class AppViewsProfileData {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<? extends AppViewsProfileData> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	EForm form;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() {
		try {

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() {
		
		form = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"EFormsNG"})
	public void initiatingWorkflow() throws Exception {
		
		log.warn("Starting: " + this.getClass().getSimpleName());
		
		form = EFormsUtil.initializeBasicEForm(IEFormsConst.approval_FormTypeName,IEFormsConst.projectApproval_FormTypeName,"ProfileData-");
		
		form.setDisplayProjInfo(IEFormsConst.displayProjInfoValues[EDisplayProjInfo.ANYFORMLET.ordinal()]);
		
		EFormsUtil.createBasicEForm(form);
		
		form = EFormsUtil.initializeBasicEForm(IEFormsConst.submission_FormTypeName,IEFormsConst.postAwardSubmission_FormTypeName,"ProfileData-");
		
		form.setDisplayProjInfo(IEFormsConst.displayProjInfoValues[EDisplayProjInfo.DEFFORMLET.ordinal()]);
		
		EFormsUtil.createBasicEForm(form);
		
		form = EFormsUtil.initializeBasicEForm(IEFormsConst.submission_FormTypeName,IEFormsConst.progOfficeSubmission_FormTypeName,"ProfileData-");
		
		form.setDisplayProjInfo(IEFormsConst.displayProjInfoValues[EDisplayProjInfo.DEFFORMLET.ordinal()]);
		
		EFormsUtil.createBasicEForm(form);
		
		form = EFormsUtil.initializeBasicEForm(IEFormsConst.review_FormTypeName,IEFormsConst.review_FormTypeName,"ProfileData-");
		
		form.setDisplayProjInfo(IEFormsConst.displayProjInfoValues[EDisplayProjInfo.ANYFORMLET.ordinal()]);
		
		EFormsUtil.createBasicEForm(form);
	}
	
	@Test(groups = { "EFormsNG" }, dependsOnMethods = "initiatingWorkflow")
	public void startingProject() throws Exception {
		
		Reporter.log(this.getClass().getSimpleName() + " has not been implemented yet");
		
	}

}
