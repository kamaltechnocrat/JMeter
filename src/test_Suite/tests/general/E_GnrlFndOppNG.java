/**
 * 
 */
package test_Suite.tests.general;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.*;

import test_Suite.constants.ui.*;
import test_Suite.constants.preTest.*;
import test_Suite.constants.cases.*;
import test_Suite.lib.workflow.*;
import test_Suite.utils.cases.*;
import test_Suite.utils.ui.*;

/**
 * @author mshakshouki
 * 
 */

@GUITest
@Test(singleThreaded = true)
public class E_GnrlFndOppNG {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	boolean isNewProgram = true;
	
	boolean hasProgramForm = false;
	
	boolean hasPublicationForm = false;
	
	boolean isNewOrg = true;

	String preFix = IGeneralConst.gnrl_ProgPrefix;
	
	char portaltype = 'P';
	
	Program fndOpp;

	String progAdmin[] = { IPreTestConst.adminGroup };
	String progOfficers[] = { IPreTestConst.Groups[0][0][0],
			IPreTestConst.Groups[6][0][0] };

	// --------------- End of Global Parameters
	// ---------------------------------

	@BeforeClass(groups = { "PapNG" })
	public void setUp() {
		
		try {
			
			log.warn("Starting: " + this.getClass().getSimpleName());

			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
	
	//##Following Line may need to be included?
	//		FoppApprovalUtil.setPAPRequired("No");


		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "PapNG" }, alwaysRun=true)
	public void tearDown() {
		
		fndOpp = null;
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}

	//setters
	private void initializeFundingOpp() throws Exception {

		fndOpp = new Program();

		fndOpp.setProgPreFix(preFix);
		fndOpp.setProgPostfix("");
		fndOpp.setProgPortal(portaltype);
		fndOpp.setProgForm(hasProgramForm);
		fndOpp.setPublicationForm(hasPublicationForm);
		fndOpp.setNewProgram(isNewProgram);
		fndOpp.setProgIdent(fndOpp.getProgPreFix()
				+ IGeneralConst.gnrl_FndOppName);

		ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
		fndOpp.initProgram();

		fndOpp.setProgAdmin(progAdmin);
		fndOpp.setProgOfficers(progOfficers);
		fndOpp.setStepOfficer(IPreTestConst.AppGroupName);
	}
	
	@Test(groups = { "PapNG" })
	private void initialize() throws Exception {
		initializeFundingOpp();
	}

	@Test(groups = { "PapNG" }, dependsOnMethods = "initialize")
	public void createFundingOpp() throws Exception {
		
		try {
			
			fndOpp.createProgram();
	
			fndOpp.addStep(IGeneralConst.gnrl_SubmissionA);
			fndOpp.manageStep(new String[][] { { progOfficers[0] } });
	
			fndOpp.addStep(IGeneralConst.gnrl_Closing_POSS_Step);
			fndOpp.manageStep(new String[][] { { progOfficers[0] } });
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);

		} 
	}
}
