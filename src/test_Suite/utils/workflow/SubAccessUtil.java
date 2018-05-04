/**
 * 
 */
package test_Suite.utils.workflow;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.users.Users;
import test_Suite.lib.workflow.FOProject;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;

/**
 * @author mshakshouki
 *
 */
public class SubAccessUtil {

	private static Log log = LogFactory.getLog(SubAccessUtil.class);
	
	public static Users getUser(int userBeat, int index, String userType) throws Exception {
		try {
			
			return new Users(userBeat, IPreTestConst.MultiUsers[index],userType);
		
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		return null;
	}
	
	public static boolean submitAndReturnFromForm() throws Exception {
		
		try {
			Assert.assertTrue(ClicksUtil.submitOrComplete(), "FAIL: Could not submit eForm");
			
			if(!ClicksUtil.returnFromAnyForm())
				return false;
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		return true;		
	}
	
	public static boolean continueWorkflowNG(FOProject proj, boolean includePOSub) throws Exception {
		try{
				if(includePOSub)
			{			
				log.debug("Start PO Submission Non Critical");
				GeneralUtil.switchToPOWithProjOfficer("1");
				
				proj.initializeStep(IGeneralConst.PO_Submission_Non[0][0]);
				
				if(!ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk))
				{
					log.error("could not click on link ".concat(IClicksConst.openMyAssignedSubmissionListLnk));
					return false;
				}
				
				FiltersUtil.filterListByProject(proj);
				
				ProjectUtil.openSubmissionInMyAssignedSubmissionList(proj, IGeneralConst.PO_Submission_Non[0][0], IFiltersConst.openProjView);
				
				submitAndReturnFromForm();
			}
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");
			
			proj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0]);
			
			if(!ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk))
			{
				log.error("could not click on link ".concat(IClicksConst.openAssignedSubmissionListLnk));
				return false;
			}
			
			ProjectUtil.openEvaluateSubmissionFormletInList_New(proj, IGeneralConst.approvQuoCritAuto[0][0], IFiltersConst.evaluateSubmissions_Ready_StatusSubView, "No Propreties Fields LBF", false,"1", IFiltersConst.versionNumber_EqualTo);
			
			GeneralUtil.selectInDropdownList("/0:numericDropdown/", "Approved");
			
			GeneralUtil.takeANap(0.5);
			
			if(!ClicksUtil.clickButtons(IClicksConst.saveBtn))
			{
				log.error("could not click on button ".concat(IClicksConst.saveBtn));
				return false;
			}
			
			if(!ClicksUtil.clickLinks("/Summary/"))
			{
				log.error("could not click on link ".concat("/Summary/"));
				return false;
			}
			
			SubAccessUtil.submitAndReturnFromForm();
			
			GeneralUtil.switchToPOWithProjOfficer("1");
			
			proj.initializeStep(IGeneralConst.pa_AwardCrit[0][0]);
			
			if(!ClicksUtil.clickLinks(IClicksConst.openAwardListLnk))
			{
				log.error("could not click on link ".concat(IClicksConst.openAwardListLnk));
				return false;
			}
			
			FiltersUtil.filterListByProject(proj);
			
			proj.setClaimNumber(1);
			
			proj.changeClaimCriteria(true, false, false, ILBFunctionConst.lbf_IPASSource[1][0], "PA Submission Schedule Fields");
			
			proj.setClaimNumber(2);
			
			proj.changeClaimCriteria(true, false, true, ILBFunctionConst.lbf_IPASSource[1][0], "PA Submission Schedule Fields");
			
			proj.setClaimNumber(0);
			
		}catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		return true;
	}
	
	public static boolean continuePAWorkflow(FOProject foProj, Integer claimNum, boolean includePOSub, boolean continueToParent, String subStatus, String verNum, String verCriteria) throws Exception {
		try {
			
			GeneralUtil.switchToFOOnly();
			GeneralUtil.loginAnyFO("front2");
			
			foProj.setClaimNumber(claimNum);

			Assert.assertTrue(FrontOfficeUtil.openFOSubmissionForm(foProj,
					IGeneralConst.initialClaim[0][0]),
					"Fail: Could not Find Project In FO Submission List!");
			
			submitAndReturnFromForm();
			
			if(includePOSub)
			{			
				log.debug("Start PO Submission Non Critical");
				GeneralUtil.switchToPOWithProjOfficer("1");
				
				if(!ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk))
				{
					log.error("could not click on link ".concat(IClicksConst.openMyAssignedSubmissionListLnk));
					return false;
				}
				
				ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IGeneralConst.PO_Submission_Non[0][0] + "-pa", IFiltersConst.openProjView);
				
				submitAndReturnFromForm();
			}
			
			GeneralUtil.switchToPOOnly();
			GeneralUtil.loginApprover("1");
			
			foProj.initializeStep(IGeneralConst.approvQuoCritAuto[0][0] + "-pa");
			
			if(!ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk))
			{
				log.error("could not click on link ".concat(IClicksConst.openAssignedSubmissionListLnk));
				return false;
			}
			
			ProjectUtil.openEvaluateSubmissionFormletInList_New(foProj, IGeneralConst.approvQuoCritAuto[0][0] + "-pa", subStatus, "No Propreties Fields LBF", false,verNum, verCriteria);
			
			GeneralUtil.selectInDropdownList("/0:numericDropdown/", "Approved");
			
			if(!ClicksUtil.clickButtons(IClicksConst.saveBtn))
			{
				log.error("could not click on button ".concat(IClicksConst.saveBtn));
				return false;
			}
			
			if(!ClicksUtil.clickLinks("/Summary/"))
			{
				log.error("could not click on link ".concat("/Summary/"));
				return false;
			}
			
			SubAccessUtil.submitAndReturnFromForm();
			
			if(continueToParent)
			{				
				log.debug("Start PO Submission Critical");
				GeneralUtil.switchToPOWithProjOfficer("1");
				
				foProj.setClaimNumber(0);
				
				if(!ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk))
				{
					log.error("could not click on link ".concat(IClicksConst.openMyAssignedSubmissionListLnk));
					return false;
				}
				
				ProjectUtil.openSubmissionInMyAssignedSubmissionList(foProj, IGeneralConst.PO_Submission[0][0], IFiltersConst.openProjView);
				
				submitAndReturnFromForm();				
			}
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
		return true;
	}
	
	public static Hashtable<String,Boolean> initializePA_Optional(FOProject foProj,boolean reExecuteClaim, boolean reExecuteApproval) throws Exception {
		
		Hashtable<String,Boolean> claim;
		
		claim = new Hashtable<String,Boolean>();
		
		foProj.initializStep(IGeneralConst.initialClaim[0][0]);
		
		claim.put(foProj.getCurrentStepName(), reExecuteClaim);
		
		foProj.initializStep(IGeneralConst.approvQuoCritAuto[0][0].concat("-pa"));
		
		claim.put(foProj.getCurrentStepName(), reExecuteApproval);		
		
		return claim;		
	}

}
