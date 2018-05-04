/**
 * NOTE: current script depends on S2613_01NG which supposed to run first
 * Steps:
 * 1. Create Program Approval Process
 * 2. Add Program Administrators and Program Staff to PAP
 * 3. Add two Steps to it: 'Submit' and 'Approve'
 */
package test_Suite.tests.stories.release_2_0.iter_6;

import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.text;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.workflow.IProgStepsConst;
import test_Suite.lib.workflow.ProgramApprove;
import test_Suite.lib.workflow.ProgramSteps;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.HtmlFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.HtmlFormsUtil.ECreateUpdate;
import watij.runtime.ie.IE;

/**
 * @author apankov
 *
 */

@Test(singleThreaded = true)
public class S2613_02NG {

	private static Log log     = LogFactory.getLog(S2613_02NG.class);
	IE ie;
	private String papId       = "ProgApprove";
	private String [] papNames = {papId, papId, papId};
	private String [] programAdmin = {IPreTestConst.adminGroup, IPreTestConst.AppGroupName};
	ProgramApprove pApprove;
	private String [][] stepNames = {
										{"PAP Submit", "PAP Submit", "PAP Submit"},
										{"PAP Approve", "PAP Approve", "PAP Approve"},
										{"PAP Award", "PAP Award", "PAP Award"}
									};
	@BeforeClass  
	public void setUp() {

	} 

	@Test(groups = { "Iter_26" })
	/**
	 * Parent Method
	 */
	public void s2613_02NG() throws Exception {
		IEUtil.openNewBrowser();
		ie = IEUtil.getActiveIE();
		GeneralUtil.navigateToPO();
		GeneralUtil.loginPO();
		GeneralUtil.setPAPRequired("Yes"); //Program Approval Process required set to "Yes"
		createPAP();
		HtmlFormsUtil.setProgramStatus(IClicksConst.openProgramApprovalProcess, papId, IGeneralConst.statusActive);
		pApprove = null;
		closeBrowser();
	}
	
	private void createPAP()
	{
		try
		{
			ClicksUtil.clickLinkBySpanId( IClicksConst.menuPAP_ApprovalProcess_Span_Id, IClicksConst.openProgramApprovalProcess);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(papId);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			//For this test scenario if the PAP already exists we just update it
			// The same scenario is valid for PAP Steps
			pApprove = new ProgramApprove();
			pApprove.setProgApproveId(papId);
			//pApprove.setProgApproveForm(IUAPConst.programForm[0]);
			pApprove.setProgApproveStartDate(GeneralUtil.getTodayDate());
			pApprove.setProgApproveEndDate(GeneralUtil.getNextYear());
			pApprove.setProgApproveOfficer(IPreTestConst.adminProgPOOfficer);
			pApprove.setProgApproveStatus(IGeneralConst.statusInactive);
			pApprove.setProgApprovePrOrg(IGeneralConst.primG3_OrgRoot);
			pApprove.setProgApproveOrgAccess(IGeneralConst.org_Access_Public);
			pApprove.setProgApproveNames(papNames);
			pApprove.setProgApproveAdmin(programAdmin);
			pApprove.setProgApproveStaff(setPAPStaff());
			if(ie.link(text, papId).exists())
			{   //if PAP with the same name already exists in the list
				pApprove.setCreateOrUpdate(ECreateUpdate.objectUpdate.ordinal());
				log.info("Updating PAP");
			}
			else
			{
				pApprove.setCreateOrUpdate(ECreateUpdate.objectCreate.ordinal());
				log.info("Creating new PAP");
			}
			pApprove.setProgApproveSteps(createPAPSteps());
			HtmlFormsUtil.fillProgApproveDetails(pApprove); 
		}
		catch(Exception ex)
		{
			log.debug("ERROR in createPAP() " + ex.getMessage());
		}
	}
	
	private ArrayList<ProgramSteps> createPAPSteps()
	{
		try
		{
			ArrayList<ProgramSteps> steps = new ArrayList<ProgramSteps>();
			
			//Set PAP Submit step
			ProgramSteps papSubmit = new ProgramSteps();
			papSubmit.setProgId(papId);
			papSubmit.setStepId(IEFormsConst.applicantsubmission_FormTypeName);
			papSubmit.setStepNotes(IEFormsConst.applicantsubmission_FormTypeName);
			papSubmit.setStepType(IProgStepsConst.stepType_FundOppSubmission);
			papSubmit.setStepProjOfficerGroup(IPreTestConst.adminGroup);
			papSubmit.setStepStartDate(pApprove.getProgApproveStartDate());
			papSubmit.setStepEndDate(pApprove.getProgApproveEndDate());
			papSubmit.setStepIsCritical(true);
			papSubmit.setStepForm("Sub");
			papSubmit.setStepNames(stepNames[0]);
			papSubmit.setStepAction(ECreateUpdate.objectCreate.ordinal());
			steps.add(papSubmit);
			
			//Set PAP Approve step
			ProgramSteps papApprove = new ProgramSteps();
			papApprove.setProgId(papId);
			papApprove.setStepId(IEFormsConst.projectApproval_FormTypeName);
			papApprove.setStepNotes(IEFormsConst.projectApproval_FormTypeName);
			papApprove.setStepType(IProgStepsConst.stepType_FundOppApproval);
			papApprove.setStepProjOfficerGroup(IPreTestConst.adminGroup);
			papApprove.setStepStartDate(pApprove.getProgApproveStartDate());
			papApprove.setStepEndDate(pApprove.getProgApproveEndDate());
			papApprove.setStepIsCritical(true);
			papApprove.setStepForm(IEFormsConst.approval_FormTypeName2);
			papApprove.setStepEvaluationType(IEFormsConst.formReviewCrit[1][1]);
			papApprove.setStepQuorumAmount("1");
			papApprove.setStepNames(stepNames[1]);
			papApprove.setStepAction(ECreateUpdate.objectCreate.ordinal());
			steps.add(papApprove);
			
			return steps;
		}
		catch(Exception ex)
		{
			log.debug("ERROR in createPAPSteps() " + ex.getMessage());
			return null;
		}
	}
	
	private Hashtable<String, Object> setPAPStaff()
	{
	     Hashtable<String, Object> staff = new Hashtable<String, Object>();
	     staff.put(HtmlFormsUtil.staffShowAll, false);
	     staff.put(HtmlFormsUtil.staffOrg, IGeneralConst.primG3_OrgRoot);
	     staff.put(HtmlFormsUtil.staffGroups, programAdmin);
	     
	     return staff;
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
