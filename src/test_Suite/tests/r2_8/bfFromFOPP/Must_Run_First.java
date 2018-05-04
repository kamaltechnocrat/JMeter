/**
 * 
 */
package test_Suite.tests.r2_8.bfFromFOPP;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IBf_FoppConst.EPostFix;
import test_Suite.constants.workflow.IBf_FoppConst.EProjNames;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.workflow.BfFoppUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class Must_Run_First {

	/***********************************************************************
	 * To set up the Global Params Name Vars
	 ***********************************************************************/
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FOProject srcProj;
	
	FOProject trgtProj;
	
	FundingOpportunity trgtFopp;
	
	EProjNames epNames = EProjNames.FOPROJ;
	
	Integer orgIndex = 1;

	/*-------------------------------------------------------------------*/

	@BeforeClass(groups = { "EFormsNG" })
	public void setUp() throws Exception {

		log.warn("Starting: " + this.getClass().getSimpleName());

		IEUtil.openNewBrowser();
		GeneralUtil.navigateToPO();
		GeneralUtil.logInSuper();
		// -----------------------------------
		
		GeneralUtil.setApplicantWorkspace("BF-FOPP-Applicant-eForm-Target");
		
		FundingOpportunity fopp = BfFoppUtil.initFopp(EPostFix.SRC);
		
		srcProj = BfFoppUtil.initializeFOSourceProject(fopp,epNames,orgIndex);
		
		trgtFopp = BfFoppUtil.initFopp(EPostFix.TRGT);
	}

	@AfterClass(groups = { "EFormsNG" }, alwaysRun = true)
	public void tearDown() throws Exception {
		
		srcProj = null;
		trgtFopp = null;
		trgtProj = null;

		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups="EFormsNG")
	public void must_Run_First() throws Exception {
		
		trgtFopp = BfFoppUtil.initFopp(EPostFix.TRGT);
		doEveryThing();
		
		trgtFopp = BfFoppUtil.initFopp(EPostFix.AWARDED);
		doEveryThing();
		
		GeneralUtil.switchToFO();
		
		BfFoppUtil.completeFOWorkspace(trgtProj);
	}
	
	private void doEveryThing() throws Exception {
	
		trgtProj = BfFoppUtil.initializeFOTargetProject(trgtFopp,srcProj,true,epNames,orgIndex);
		
		BfFoppUtil.openAndFillAdminEForm(trgtProj.getFopp());
		
		BfFoppUtil.openAndFillPublicationEForm(trgtProj.getFopp());
		
		ClicksUtil.clickButtons(IClicksConst.backToFundingOppListBtn);		
	}

}
