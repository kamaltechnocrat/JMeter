package test_Suite.tests.stories.release_2_0.iter_6;
/**
 * Steps:
 * 1. Open PO, log on as admin user
 * 2. Navigate to 'Application Settings', switch 'Program Approval Required' to "No", save changes
 * 3. Go to 'Programs' select Grantium Program from Pre-test set of Programs,
 * 	switch Program Oficer to Admin default user
 * 4. Navigate to 'Application Settings', switch 'Program Approval Required' to "Yes", save changes
 * 5. Navigate to Program again, select the same program as on step #3, check if PAP fields exist
 */

import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.text;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IProgramsConst;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IErrorConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import watij.runtime.ie.IE;

/**
 * @author apankov
 * Script per Story # S2613 including fields existence verification 
 *  plus basic steps to complete the story validation
 */
@Test(singleThreaded = true)
public class S2613_01NG {
	private static Log log = LogFactory.getLog(S2613_01NG.class);
	IE ie;
	
	private String programName = IGeneralConst.baseLetters[0]+IGeneralConst.gnrl_ProgPrefix+IGeneralConst.gnrl_ProgName;
	
	@BeforeClass  
	public void setUp() {

	} 

	@Test(groups = { "Iter_26" })
	/**
	 * Parent Method
	 */
	public void s2613_01NG() throws Exception 
	{
		IEUtil.openNewBrowser();
		ie = IEUtil.getActiveIE();
		GeneralUtil.navigateToPO();
		GeneralUtil.loginPO();
		GeneralUtil.setPAPRequired("No"); //Program Approval Process required set to "No"
		findProgram();
		updateProgramOfficer();
		GeneralUtil.setPAPRequired("Yes"); //Program Approval Process required set to "Yes"
		findProgram();
		checkPAPFields();
		closeBrowser();
	}
	
	private void findProgram()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(programName);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			if(ie.link(text, programName).exists())
			{				
				ClicksUtil.clickLinks(programName);
				ClicksUtil.clickImageByAlt(IClicksConst.openBtn + " " + programName);				
			}
			else
				log.warn("WARNING: Program required for test case does not exist " + programName  
						+ " make sure that preTest runs before the test case");
		}
		catch(Exception ex)
		{
			log.debug("ERROR in resetDefaults() " + ex.getMessage());
		}
	}
	
	private void updateProgramOfficer() throws Exception
	{
		// Update Program Officer field
		ie.selectList(id, IProgramsConst.fundingOpp_PROG_MANAGER_MENU).select(IPreTestConst.adminProgPOOfficer);
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
	}
	
	private void checkPAPFields()
	{
		try
		{	
			//Test for new fields on Program Details screen
			if(ie.selectList(id, IProgramsConst.fundingOpp_PROG_PAP_SELECT).exists())
				log.info(IErrorConst.tstTestPassed + " PAP Select Exists");
			else
				log.warn(IErrorConst.tstTestFailed + " PAP Select does not Exist");
			if(ie.label(id, IProgramsConst.fundingOpp_PROG_PAP_STATUS_LBL).exists() && 
					ie.label(id, IProgramsConst.fundingOpp_PROG_PAP_STATUS_LBL).text().equals(IClicksConst.newBtn))
				log.info(IErrorConst.tstTestPassed + " PAP Status Label exists and displays correct Status");
			else
				log.warn(IErrorConst.tstTestFailed + " PAP Status Label either doe snot exists or displays incorrect PAP status");
			// Check if Status Select is disabled
			if(ie.selectList(id, IProgramsConst.fundingOpp_PROG_STATUS_MENU).disabled())
				log.info(IErrorConst.tstTestPassed + " Program Status menu is disabled");
			else
				log.warn(IErrorConst.tstTestFailed + " Program Status menu is enabled while supposed to be disbled until PAP is Approved");

		}
		catch(Exception ex)
		{
			log.debug("ERROR in updateG3Program() " + ex.getMessage());
		}
	}
	
	/**
	 * Close PO application and close web browser
	 *
	 */
	private void closeBrowser()
	{
		try
		{
			ie = IEUtil.getActiveIE();
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		}
		catch(Exception ex)
		{
			log.debug("ERROR in closeBrowser() " + ex.getMessage());
		}
	}

}



