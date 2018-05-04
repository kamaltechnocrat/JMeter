/**
 * 
 */
package test_Suite.tests.r2_9.feat_3.lists_Enhance;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fest.swing.annotation.GUITest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.lib.workflow.FundingOpportunity;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.ProjActivUtil;

/**
 * @author mshakshouki
 *
 */

@GUITest
@Test(singleThreaded = true)
public class TrnsfrListFiltering {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************
	Class<?> ownClass = this.getClass();

	private Log log = LogFactory.getLog(ownClass);
	
	FundingOpportunity fopp;
	Project proj;

	/*------ End of Global Vars --------------*/

	@BeforeClass(groups = { "WorkflowNG" })
	public void setUp() {
		
		try {

			log.warn("Starting: " + this.getClass().getSimpleName());
			
			IEUtil.openNewBrowser();
			GeneralUtil.navigateToPO();
			GeneralUtil.logInSuper();
			// -----------------------------------
			
			fopp = new FundingOpportunity("A",IFoppConst.fundingOpp_Prefix,"");
			
			proj = new Project(fopp,"", true,true,EFormsUtil.createAnyRandomString(5));

		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}

	@AfterClass(groups = { "WorkflowNG" })
	public void tearDown() {
		
		fopp = null;
		proj = null;
		
		GeneralUtil.Logoff();
		IEUtil.closeBrowser();
		
		log.warn("Ending: " + this.getClass().getSimpleName());
	}
	
	@Test(groups = {"WorkflowNG"})
	public void createOrgAndProject() throws Exception {
		
		proj.newCreateNewOrg();
		proj.newCreateNewPOProject();
		proj.submitProject(true);
		
	}
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods="createOrgAndProject")
	public void testTransferProjectWithFilters() throws Exception {
		
		proj.assignOfficers(new String[][] {{ IPreTestConst.Groups[0][0][0],IPreTestConst.Groups[0][1][0] }});
		
		proj.setTrnsfrToOrgFullName("Ouia 3");
		
		Assert.assertTrue(ProjActivUtil.trnasferProjectToApplicant(proj, "Open Projects"), "FAIL: could not transfer Project: " + proj.getProjFullName() + " to " + proj.getTrnsfrToOrgFullName() );
		
		FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ApplicantName_Lbl, proj.getTrnsfrToOrgFullName(), IFiltersConst.exact);
		
		Assert.assertFalse(TablesUtil.findInTable(ITablesConst.transferProjectTableId, proj.getTrnsfrToOrgFullName()), "FAIL: the Target Applicant should not be in List");
		
	}
	
	@Test(groups = {"WorkflowNG"}, dependsOnMethods="testTransferProjectWithFilters")
	public void testContinueTransferedProject() throws Exception {
		
		proj.setOrgFullName("Ouia 3");
		
		Assert.assertTrue(proj.reviewSubmission(IGeneralConst.reviewQuoCritAuto[0][0], true, "Yes", false, IFiltersConst.evaluateSubmissions_Ready_StatusSubView), "FAIL: Could Not Submit Review Step");
		
	}

}
