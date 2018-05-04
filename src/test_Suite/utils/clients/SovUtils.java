/**
 * 
 */
package test_Suite.utils.clients;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test_Suite.constants.clients.ISovConst;
import test_Suite.constants.clients.ISovConst.ESovCfpFy13ApplicantsNum;
import test_Suite.constants.clients.ISovConst.ESovCfpFy14ApplicantsNum;
import test_Suite.constants.clients.ISovConst.ESovFOPPs;
import test_Suite.constants.clients.ISovConst.ESovNordFy14ApplicantsNum;
import test_Suite.constants.clients.ISovConst.ESovTechEdApplicantsNum;
import test_Suite.constants.eForms.ILBFunctionConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IConfigConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.workflow.IAmendmentsConst;
import test_Suite.constants.workflow.IFoppConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.constants.workflow.IStepsConst;
import test_Suite.lib.clients.Sov;
import test_Suite.lib.workflow.FOProject;
//import test_Suite.tests.clients.sov.tech_ed_fy13.SOV_Notification_Amendments.Agreement;
//import test_Suite.tests.clients.sov.tech_ed_fy13.SOV_Notification_Amendments.Finance;
import test_Suite.utils.cases.DocumentsUtil;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import test_Suite.utils.workflow.FrontOfficeUtil;
import watij.dialogs.AlertDialog;
import watij.runtime.ie.IE;

/**
 * @author Mustafa Shakshouki
 *
 */
public class SovUtils {

	private static Log log = LogFactory.getLog(SovUtils.class);
	
	
	public static Sov cfpFy13_Initialization(ESovFOPPs foppType, ESovCfpFy13ApplicantsNum appNum, String currStepName, String subProj) throws Exception {
		
		log.info("Initializing CFP_FY13_DOE_FUNDING_OPP!");
		
		Sov sov = new Sov(
				ISovConst.sovCfpFy13FoUsersNames[appNum.ordinal()], 
				appNum.name(), 
				ISovConst.sovCfpFy13ProjectsNames[appNum.ordinal()], 
				subProj, 
				ISovConst.sovFoppsNames[foppType.ordinal()], 
				ISovConst.sovFoppsIdents[foppType.ordinal()], 
				currStepName, 
				ISovConst.sovCfpFy13InProcessNames[appNum.ordinal()]);
		
		
		return sov;
	}
	
	
	public static Sov cfpFy14_Initialization(ESovFOPPs foppType, ESovCfpFy14ApplicantsNum appNum, String currStepName, String subProj) throws Exception {
		
		log.info("Initializing CFP_FY14_AOE_FUNDING_OPP!");
		
		Sov sov = new Sov(
				ISovConst.sovCfpFy14FoUsersNames[appNum.ordinal()], 
				appNum.name(), 
				ISovConst.sovCfpFy14ProjectsNames[appNum.ordinal()], 
				subProj, 
				ISovConst.sovFoppsNames[foppType.ordinal()], 
				ISovConst.sovFoppsIdents[foppType.ordinal()], 
				currStepName, 
				ISovConst.sovCfpFy14InProcessStepsNames[appNum.ordinal()]);
		
		
		return sov;
	}
	
	
	public static Sov nordFy14_Initialization(ESovFOPPs foppType, ESovNordFy14ApplicantsNum appNum, String currStepName, String subProj) throws Exception {
		
		log.info("Initializing Nord_FY14_AOE_FUNDING_OPP!");
		
		Sov sov = new Sov(
				ISovConst.sovNordFy14FoUsersNames[appNum.ordinal()], 
				appNum.name(), 
				ISovConst.sovNordFy14ProjectsNames[appNum.ordinal()], 
				subProj, 
				ISovConst.sovFoppsNames[foppType.ordinal()], 
				ISovConst.sovFoppsIdents[foppType.ordinal()], 
				currStepName, 
				ISovConst.sovNordFy14InProcessStepsNames[appNum.ordinal()]);
		
		
		return sov;
	}
	
	
	public static Sov techEdFy13_Initialization(ESovFOPPs foppType, ESovTechEdApplicantsNum appNum, String currStepName, String subProj) throws Exception {
		
		log.info("Initializing Nord_FY14_AOE_FUNDING_OPP!");
		
		Sov sov = new Sov(
				ISovConst.sovTechEdFoUsersNames[appNum.ordinal()], 
				appNum.name(), 
				ISovConst.sovTechEdProjectsNames[appNum.ordinal()], 
				subProj, 
				ISovConst.sovFoppsNames[foppType.ordinal()], 
				ISovConst.sovFoppsIdents[foppType.ordinal()], 
				currStepName, 
				ISovConst.sovTechEdInProcessStepsNames[appNum.ordinal()]);
		
		
		return sov;
	}
	
	
	public static boolean amendApplicationSubmission(Sov sov) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);		
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
		
		FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl, sov.getProjectName(), IFiltersConst.exact);
		
		
		if(!TablesUtil.openInTableByImageAlt(ITablesConst.projectsTableId, sov.getProjectName(), IAmendmentsConst.stepAmend_RequestAmendment_ImageAlt.concat(sov.getProjectName())))
		{			
			log.error("Could not open Request Amendment in Project List!");
			
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, sov.getCurrStepName()))
		{
			log.error("Could not find the Step to Amend: ".concat(sov.getCurrStepName()));
			return false;		
		}
		
		GeneralUtil.takeANap(2.0);
		
		String dd = GeneralUtil.setDayofMonth(3);
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID, dd))
		{
			log.error("Could not find Date Field!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Reason_TxtFld_ID, "Request Amendment on Applicant Submission"))
		{
			log.error("Could not find reason Field!");
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Next Button is disabled");
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.issueAmendBtn))
		{
			log.error("Could not click on Issue Amendment Button, it's possible disabled!");
			return false;
		}
		
		return true;
	}
	
public static boolean amendApplicationSubmission_SOV(Sov sov, int dateAmended) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);		
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
		
		FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl, sov.getProjectName(), IFiltersConst.exact);
		
		
		if(!TablesUtil.openInTableByImageAlt(ITablesConst.projectsTableId, sov.getProjectName(), IAmendmentsConst.stepAmend_RequestAmendment_ImageAlt.concat(sov.getProjectName())))
		{			
			log.error("Could not open Request Amendment in Project List!");
			
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, sov.getCurrStepName()))
		{
			log.error("Could not find the Step to Amend: ".concat(sov.getCurrStepName()));
			return false;		
		}
		
		GeneralUtil.takeANap(2.0);
		
		String dd = GeneralUtil.setDayofMonth(dateAmended);
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID, dd))
		{
			log.error("Could not find Date Field!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Reason_TxtFld_ID, "Request Amendment on Applicant Submission"))
		{
			log.error("Could not find reason Field!");
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Next Button is disabled");
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.issueAmendBtn))
		{
			log.error("Could not click on Issue Amendment Button, it's possible disabled!");
			return false;
		}
		
		return true;
	}


public static boolean amendInitialReview_SOV(Sov sov, int dateAmended) throws Exception {
	
	ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

	ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);		
	ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	
	FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl, sov.getProjectName(), IFiltersConst.exact);
	
	
	if(!TablesUtil.openInTableByImageAlt(ITablesConst.projectsTableId, sov.getProjectName(), IAmendmentsConst.stepAmend_RequestAmendment_ImageAlt.concat(sov.getProjectName())))
	{			
		log.error("Could not open Request Amendment in Project List!");
		
		return false;
	}
	
	if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, "Initial Review"))
	{
		log.error("Could not find the Step to Amend: ".concat(sov.getCurrStepName()));
		return false;		
	}
	
	GeneralUtil.takeANap(2.0);
	
	String dd = GeneralUtil.setDayofMonth(dateAmended);
	
	if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID, dd))
	{
		log.error("Could not find Date Field!");
		return false;
	}
	
	if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Reason_TxtFld_ID, "Request Amendment on Applicant Submission"))
	{
		log.error("Could not find reason Field!");
		return false;
	}
	
	if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
	{
		log.error("Next Button is disabled");
		return false;
	}
	
	if(!ClicksUtil.clickButtons(IClicksConst.issueAmendBtn))
	{
		log.error("Could not click on Issue Amendment Button, it's possible disabled!");
		return false;
	}
	
	return true;
}


public static boolean amendProgramTeamReview_SOV(Sov sov, int dateAmended) throws Exception {
	
	ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

	ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);		
	ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	
	FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl, sov.getProjectName(), IFiltersConst.exact);
	
	
	if(!TablesUtil.openInTableByImageAlt(ITablesConst.projectsTableId, sov.getProjectName(), IAmendmentsConst.stepAmend_RequestAmendment_ImageAlt.concat(sov.getProjectName())))
	{			
		log.error("Could not open Request Amendment in Project List!");
		
		return false;
	}
	
	if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, "Program Team Review"))
	{
		log.error("Could not find the Step to Amend: ".concat(sov.getCurrStepName()));
		return false;		
	}
	
	GeneralUtil.takeANap(2.0);
	
	String dd = GeneralUtil.setDayofMonth(dateAmended);
	
	if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID, dd))
	{
		log.error("Could not find Date Field!");
		return false;
	}
	
	if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Reason_TxtFld_ID, "Request Amendment on Applicant Submission"))
	{
		log.error("Could not find reason Field!");
		return false;
	}
	
	if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
	{
		log.error("Next Button is disabled");
		return false;
	}
	
	if(!ClicksUtil.clickButtons(IClicksConst.issueAmendBtn))
	{
		log.error("Could not click on Issue Amendment Button, it's possible disabled!");
		return false;
	}
	
	return true;
}

public static boolean amendGrantAgreementCreated_SOV(Sov sov, int dateAmended) throws Exception {
	
	ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

	ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);		
	ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	
	FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl, sov.getProjectName(), IFiltersConst.exact);
	
	
	if(!TablesUtil.openInTableByImageAlt(ITablesConst.projectsTableId, sov.getProjectName(), IAmendmentsConst.stepAmend_RequestAmendment_ImageAlt.concat(sov.getProjectName())))
	{			
		log.error("Could not open Request Amendment in Project List!");
		
		return false;
	}
	
	if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, "Grant Agreement Created"))
	{
		log.error("Could not find the Step to Amend: ".concat(sov.getCurrStepName()));
		return false;		
	}
	
	GeneralUtil.takeANap(2.0);
	
	String dd = GeneralUtil.setDayofMonth(dateAmended);
	
	if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID, dd))
	{
		log.error("Could not find Date Field!");
		return false;
	}
	
	if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Reason_TxtFld_ID, "Request Amendment on Applicant Submission"))
	{
		log.error("Could not find reason Field!");
		return false;
	}
	
	if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
	{
		log.error("Next Button is disabled");
		return false;
	}
	
	if(!ClicksUtil.clickButtons(IClicksConst.issueAmendBtn))
	{
		log.error("Could not click on Issue Amendment Button, it's possible disabled!");
		return false;
	}
	
	return true;
}



public static boolean amendFinanceReview_SOV(Sov sov, int dateAmended) throws Exception {
	
	ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

	ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);		
	ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	
	FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl, sov.getProjectName(), IFiltersConst.exact);
	
	
	if(!TablesUtil.openInTableByImageAlt(ITablesConst.projectsTableId, sov.getProjectName(), IAmendmentsConst.stepAmend_RequestAmendment_ImageAlt.concat(sov.getProjectName())))
	{			
		log.error("Could not open Request Amendment in Project List!");
		
		return false;
	}
	
	if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, "Finance Grant Agreement Review"))
	{
		log.error("Could not find the Step to Amend: ".concat(sov.getCurrStepName()));
		return false;		
	}
	
	GeneralUtil.takeANap(2.0);
	
	String dd = GeneralUtil.setDayofMonth(dateAmended);
	
	if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID, dd))
	{
		log.error("Could not find Date Field!");
		return false;
	}
	
	if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Reason_TxtFld_ID, "Request Amendment on Applicant Submission"))
	{
		log.error("Could not find reason Field!");
		return false;
	}
	
	if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
	{
		log.error("Next Button is disabled");
		return false;
	}
	
	if(!ClicksUtil.clickButtons(IClicksConst.issueAmendBtn))
	{
		log.error("Could not click on Issue Amendment Button, it's possible disabled!");
		return false;
	}
	
	return true;
}



   public static boolean amendDirectorGrantAgreement_SOV(Sov sov, int dateAmended) throws Exception {
	
	ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

	ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);		
	ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
	
	FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl, sov.getProjectName(), IFiltersConst.exact);
	
	
	if(!TablesUtil.openInTableByImageAlt(ITablesConst.projectsTableId, sov.getProjectName(), IAmendmentsConst.stepAmend_RequestAmendment_ImageAlt.concat(sov.getProjectName())))
	{			
		log.error("Could not open Request Amendment in Project List!");
		
		return false;
	}
	
	if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, "Director Grant Agreement Review"))
	{
		log.error("Could not find the Step to Amend: ".concat(sov.getCurrStepName()));
		return false;		
	}
	
	GeneralUtil.takeANap(2.0);
	
	String dd = GeneralUtil.setDayofMonth(dateAmended);
	
	if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID, dd))
	{
		log.error("Could not find Date Field!");
		return false;
	}
	
	if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Reason_TxtFld_ID, "Request Amendment on Applicant Submission"))
	{
		log.error("Could not find reason Field!");
		return false;
	}
	
	if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
	{
		log.error("Next Button is disabled");
		return false;
	}
	
	if(!ClicksUtil.clickButtons(IClicksConst.issueAmendBtn))
	{
		log.error("Could not click on Issue Amendment Button, it's possible disabled!");
		return false;
	}
	
	return true;
}


   
   public static boolean amendment_SOV(Sov sov, int dateAmended, String stepName) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);		
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
		
		FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl, sov.getProjectName(), IFiltersConst.exact);
		
		
		if(!TablesUtil.openInTableByImageAlt(ITablesConst.projectsTableId, sov.getProjectName(), IAmendmentsConst.stepAmend_RequestAmendment_ImageAlt.concat(sov.getProjectName())))
		{			
			log.error("Could not open Request Amendment in Project List!");
			
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, stepName))
		{
			log.error("Could not find the Step to Amend: ".concat(sov.getCurrStepName()));
			return false;		
		}
		
		GeneralUtil.takeANap(2.0);
		
		String dd = GeneralUtil.setDayofMonth(dateAmended);
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID, dd))
		{
			log.error("Could not find Date Field!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Reason_TxtFld_ID, "Request Amendment on Applicant Submission"))
		{
			log.error("Could not find reason Field!");
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Next Button is disabled");
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.issueAmendBtn))
		{
			log.error("Could not click on Issue Amendment Button, it's possible disabled!");
			return false;
		}
		
		return true;
	}


   public static boolean amendment_SOV_PA(Sov sov, int dateAmended,String subStepName, String stepName) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);		
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
		
		FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl, sov.getProjectName(), IFiltersConst.exact);
		
		
		if(!TablesUtil.openInTableByImageAlt(ITablesConst.projectsTableId, sov.getProjectName(), IAmendmentsConst.stepAmend_RequestAmendment_ImageAlt.concat(sov.getProjectName())))
		{			
			log.error("Could not open Request Amendment in Project List!");
			
			return false;
		}
		
		
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.subStepAmend_Dropdown_Id, subStepName))
		{
			log.error("Could not find the subStep to Amend: ".concat(sov.getCurrStepName()));
			return false;		
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, stepName))
		{
			log.error("Could not find the Step to Amend: ".concat(sov.getCurrStepName()));
			return false;		
		}
		
		GeneralUtil.takeANap(2.0);
		
		String dd = GeneralUtil.setDayofMonth(dateAmended);
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID, dd))
		{
			log.error("Could not find Date Field!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Reason_TxtFld_ID, "Request Amendment on Applicant Submission"))
		{
			log.error("Could not find reason Field!");
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Next Button is disabled");
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.issueAmendBtn))
		{
			log.error("Could not click on Issue Amendment Button, it's possible disabled!");
			return false;
		}
		
		return true;
	}

	
	public static boolean amendInitialPostAwardSubmission(Sov sov) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openPOProjectListLnk);

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);		
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
		
		FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_ProjectName_Lbl, sov.getProjectName(), IFiltersConst.exact);
		
		
		if(!TablesUtil.openInTableByImageAlt(ITablesConst.projectsTableId, sov.getProjectName(), IAmendmentsConst.stepAmend_RequestAmendment_ImageAlt.concat(sov.getProjectName())))
		{			
			log.error("Could not open Request Amendment in Project List!");
			
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_RequestStepAmendmentAt_DropdownId, sov.getSubProjectName()))
		{
			log.error("Could not find the Sub-Project to Amend: ".concat(sov.getSubProjectName()));
			return false;		
		}
		
		GeneralUtil.takeANap(2.0);
		
		if(!GeneralUtil.selectFullStringInDropdownList(IAmendmentsConst.stepAmend_StepToAmend_DropDownId, sov.getCurrStepName()))
		{
			log.error("Could not find the Step to Amend: ".concat(sov.getCurrStepName()));
			return false;		
		}
		
		GeneralUtil.takeANap(2.0);
		
		String dd = GeneralUtil.setDayofMonth(3);
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_DateRequiredBy_DateFld_ID, dd))
		{
			log.error("Could not find Date Field!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(IAmendmentsConst.amendRequest_Reason_TxtFld_ID, "Request Amendment on Applicant Submission"))
		{
			log.error("Could not find reason Field!");
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Next Button is disabled");
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.issueAmendBtn))
		{
			log.error("Could not click on Issue Amendment Button, it's possible disabled!");
			return false;
		}
		
		
		
		return true;
	}
	
	public static boolean sovChangeApplicationsSetting() throws Exception {
		

		if(!ClicksUtil.clickLinks(IClicksConst.openApplicationSettings))
		{
			log.error("Could Not Open Application Setting!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IConfigConst.appSettings_GrantStepStaffAccesstoAmendingSubmissions, "Yes"))
		{
			
			log.error("Could not set Grant Step Staff Access in Application Settings!");
			
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IConfigConst.appSettings_UpdateEFormsWhenOpened, "Yes"))
		{
			
			log.error("Could not set Update e.Form when Opened in Application Settings!");
			
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.saveBtn))
		{
			log.error("Could not click Save in Applicantion Settings!");
			
			return false;
		}
		
		
		return true;
	}
	
	public static boolean sovChangePrimaryOrg(Sov sov, String primOrg) throws Exception {
		
		sov.setFoppFullIdent(sov.getFundingOppIdent());
		
		sov.setFoppFullName(sov.getFundingOppName());
		
		if(!sov.openFundingOppDetails())
		{
			log.error("Could not open FOPP Details!: ".concat(sov.getFoppFullIdent()));
		}
		
		Thread dialogClickerG3 = new Thread() {
			@Override
			public void run() {
				try {
					IE ie = IEUtil.getActiveIE();
					AlertDialog dialogG3 = ie.alertDialog();
					while (dialogG3 == null) {
						log.debug("can't yet get handle on confirm dialog1");
						GeneralUtil.takeANap(1.000);
					}

					dialogG3.ok();
					log.debug("got confirm Dialog1 and clicked OK.");

				} catch (Exception e) {
					throw new RuntimeException("Unexpected exception", e);
				}
			}
		};

		dialogClickerG3.start();
		log.debug("started dialog clicker thread");
		
		if(!GeneralUtil.selectInDropdownList(IFoppConst.fundingOpp_DetailsPage_PrimaryOrg_DropDown_Id, primOrg))
		{
			log.error("Could not select Primary Org: ".concat(primOrg));
			
			return false;
		}
		
		dialogClickerG3 = null;
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
		
		
		return true;
	}
	
	public static boolean sovUncheckPAReExecute(Sov sov) throws Exception {
		
		sov.setFoppFullIdent(sov.getFundingOppIdent());
		
		sov.setFoppFullName(sov.getFundingOppName());
		
		if(!sov.openFundingOppPlanner())
		{
			log.error("Could Not open FOpp Planner!");
			return false;
		}
		
		if(!sov.openStepDetails(false, "Quarterly Financial Reports Loop (Post-Award)"))
		{
			log.error("Could Not open QFR Loop!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IStepsConst.step_ReExecute_Id, "No"))
		{
			log.error("Could not uncheck Re-Execute checkbox!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
		
		
		return true;
	}
	
	public static boolean sovChangeAwardsEForm(Sov sov, String newFormIdent) throws Exception {
		
		sov.setFoppFullIdent(sov.getFundingOppIdent());
		
		sov.setFoppFullName(sov.getFundingOppName());
		
		if(!sov.openFundingOppPlanner())
		{
			log.error("Could Not open FOpp Planner!");
			return false;
		}
		
		if(!sov.openStepDetails(false, "Grant Agreement Created (Award)"))
		{
			log.error("Could Not open Grant Agreement Created Award details!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IStepsConst.step_Prop_EFormIdent_Id, "CFP_FY14_DOE_Grant_Agreement/Amendment 1.4 for 4.1x"))
		{
			log.error("Could not change eForm for Grant Agreement Created (Award)");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
		
		if(!sov.openStepDetails(false, "Grant Agreement Approved (Award)"))
		{
			log.error("Could Not open Grant Agreement Approved Award Details!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IStepsConst.step_Prop_EFormIdent_Id, "CFP_FY14_DOE_Grant_Agreement/Amendment 1.4 for 4.1x"))
		{
			log.error("Could not change eForm for Grant Agreement Approved (Award)");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
		
		return true;
	}
	
	public static boolean sovChangeAndActivateIPASSNotif(Sov sov, String paIndex) throws Exception {
		
		sov.setFoppFullIdent(sov.getFundingOppIdent());
		
		sov.setFoppFullName(sov.getFundingOppName());
		
		sov.expandAnObject("Quarterly Financial Report (Initial Post Award Submission)");

		
		if(!ClicksUtil.clickLinksById("main:programPlanner:_idJsp31:planner:0:6:".concat(paIndex).concat(":5:0:2:t2g")))
		{
			log.error("Could not Expand Notification for IPASS of: ".concat(sov.getFoppFullIdent()));
			return false;
		}
		
		if(!ClicksUtil.clickImageByAlt("Edit Notification Quarterly Report Submission 5"))
		{
			log.error("Could not Open Notification Quarterly Report Submission 5 for IPASS of: ".concat(sov.getFoppFullIdent()));
			return false;
		}
		
		if(!GeneralUtil.toggleCheckBoxById("main:editNotification:notification:activeCheck", true))
		{
			log.error("Could not Activate Notification Quarterly Report Submission 5 for IPASS of: ".concat(sov.getFoppFullIdent()));
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("main:editNotification:notification:eventSelect", "Post-Award Submission Due"))
		{
			log.error("Could not set PA Sub Due Event for Quarterly Report Submission 5 for IPASS of: ".concat(sov.getFoppFullIdent()));
			return false;
		}

		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
		
		
		if(!ClicksUtil.clickImageByAlt("Edit Notification Quarterly Report Submission 10"))
		{
			log.error("Could not Open Notification Quarterly Report Submission 10 for IPASS of: ".concat(sov.getFoppFullIdent()));
			return false;
		}
		
		if(!GeneralUtil.toggleCheckBoxById("main:editNotification:notification:activeCheck", true))
		{
			log.error("Could not Activate Notification Quarterly Report Submission 10 for IPASS of: ".concat(sov.getFoppFullIdent()));
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("main:editNotification:notification:eventSelect", "Post-Award Submission Due"))
		{
			log.error("Could not set PA Sub Due Event for Quarterly Report Submission 10 for IPASS of: ".concat(sov.getFoppFullIdent()));
			return false;
		}		

		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);	
		
		
		if(!ClicksUtil.clickImageByAlt("Edit Notification Quarterly Report Submission 25"))
		{
			if(!ClicksUtil.clickImageByAlt("Edit Notification Quarterly Report Submission 20"))
			{
				log.error("Could not Open Notification Quarterly Report Submission 25 -20 for IPASS of: ".concat(sov.getFoppFullIdent()));
				return false;
			}
			
		}
		
		if(!GeneralUtil.toggleCheckBoxById("main:editNotification:notification:activeCheck", true))
		{
			log.error("Could not Activate Notification Quarterly Report Submission 25 -20 for IPASS of: ".concat(sov.getFoppFullIdent()));
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("main:editNotification:notification:eventSelect", "Post-Award Submission Due"))
		{
			log.error("Could not set PA Sub Due Event for Quarterly Report Submission 25-20 for IPASS of: ".concat(sov.getFoppFullIdent()));
			return false;
		}		

		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
		
		
		
		return true;
	}
	
	public static boolean sovAllGroupsFromEvaluationSteps(Sov sov, Integer[] evalIndex, Integer[] paEvalIndex, String[] steps, String paIndex) throws Exception {
		
		sov.setFoppFullIdent(sov.getFundingOppIdent());
		
		sov.setFoppFullName(sov.getFundingOppName());
		
		if(!sov.openFundingOppPlanner())
		{
			log.error("Could Not open FOpp Planner!");
			return false;
		}
		
		for (Integer indx : evalIndex) {
			
			if(!sov.openStepDetails(false, steps[indx].concat(" (Review)")))
			{
				log.error("Could Not open Step details: ".concat(steps[indx]));
				return false;
			}
			
			if(!ClicksUtil.clickButtons(IClicksConst.m2mRemoveAllBtn))
			{
				log.error("Could not click Remove All Button!");
				return false;			
			}
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
			
		}
		
		sov.expandAnObject("Quarterly Financial Reports Loop (Post-Award)");
		
		ClicksUtil.clickLinksById("main:programPlanner:_idJsp31:planner:0:6:".concat(paIndex).concat(":5:t2g"));
		
		for (Integer indx : paEvalIndex) {
			
			if(!sov.openStepDetails(false, steps[indx].concat(" (Review)")))
			{
				log.error("Could Not open Step details: ".concat(steps[indx]));
				return false;
			}
			
			if(!ClicksUtil.clickButtons(IClicksConst.m2mRemoveAllBtn))
			{
				log.error("Could not click Remove All Button!");
				return false;			
			}
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn);
			
		}
		
		
		return true;
	}
	
	
	public static boolean openFOSubmision(Sov sov) throws Exception {
		
		FOProject foProj = new FOProject();
		
		foProj.setProjFOFullName(sov.getFoProjName());
		
		ClicksUtil.clickLinks(IClicksConst.openSubmissionListLnk);
		
		if(GeneralUtil.isSelectListExists("g3-form:applicant"))
		{
			
			GeneralUtil.selectInDropdownList("g3-form:applicant", sov.getApplicantNumber());
			
			GeneralUtil.takeANap(2.0);
			
		}
		
		if(!FrontOfficeUtil.openFO_SubmissionWithStepOrProjectFullName(sov.getFoProjName(), sov.getCurrStepName()))
		{
			log.error("Could Not Open Submission in FO: ".concat(sov.getCurrStepName()));
			return false;
		}
		return true;
	}
	
	public static boolean openEvaluationStep(Sov sov, String status) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openAssignedSubmissionListLnk);

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);		
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
		
		FiltersUtil.filterList(IFiltersConst.grantManagement_ProjectName_Lbl, sov.getProjAndSubName(), IFiltersConst.exact);
		
		FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_StepName_Lbl, sov.getCurrStepName(), IFiltersConst.exact);
		
		FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_SubmissionStatus_Lbl, "", IFiltersConst.submissionsStatus_All_StatusSubView);
		
		FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_Version_Lbl, "", IFiltersConst.submissionVersion_LatestVersion);
		
		if(!TablesUtil.openInTableByImageAlt(ITablesConst.fundingOpp_EvaluateSubmissionsTableId, sov.getCurrStepName(), "Evaluate Submission"))
		{
			log.error("Could not find Evaluation Step: ".concat(sov.getCurrStepName()));
			return false;
		}
		
		return true;
		
	}
	
	public static boolean openSubInMyAssignedSubmission(Sov sov) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openMyAssignedSubmissionListLnk);

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);		
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
		
		FiltersUtil.filterList(IFiltersConst.grantManagement_ProjectName_Lbl, sov.getProjAndSubName(), IFiltersConst.exact);
		
		FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_StepName_Lbl, sov.getCurrStepName(), IFiltersConst.exact);
		
		GeneralUtil.takeANap(0.5);
		
		if(!TablesUtil.openInTableByImageAlt(ITablesConst.fundingOpp_myAssignedSubmissionsTableId, sov.getCurrStepName(), "View Submission ".concat(sov.getProjAndSubName())))
		{
			log.error("Could not find Submission Step: ".concat(sov.getCurrStepName()));
			return false;
		}
		
		return true;
		
	}
	
	public static boolean openAppSubList(Sov sov, String status) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);		
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
		
		FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_ApplicantNumber_Lbl, sov.getApplicantNumber(), IFiltersConst.exact);
		


		int indx = -1;
		indx = TablesUtil.getRowIndex(ITablesConst.applicantsTableId, sov.getApplicantNumber());

		if (indx < 0)
		{
			log.error("No Items in PO Applicants List!");
			return false;			
		}
		
		if(!TablesUtil.openInTableByImage(ITablesConst.applicantsTableId, sov.getApplicantNumber(), 0))
		{
			log.error("Could Not open Applicant Submission List!");
			return false;
		}

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);		
		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
		
		FiltersUtil.filterList(IFiltersConst.grantManagement_ProjectName_Lbl, sov.getProjAndSubName(), IFiltersConst.exact);
		
		FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_StepName_Lbl, sov.getCurrStepName(), IFiltersConst.exact);
		
		FiltersUtil.extraFilterListByLabel(IFiltersConst.grantManagement_SubmissionStatus_Lbl, "", status);
		
		if(!TablesUtil.openInTableByImage(ITablesConst.applicantSubmissionTableId, sov.getCurrStepName(), 0))
		{
			log.error("Could Not open Submission Details in Applicant Submission List!");
			return false;
		}
		
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitApplication(Sov sov) throws Exception{
		
		if(!openFOSubmision(sov))
		{
			log.error("Could Not Open Submission in FO: ".concat(sov.getApplicantNumber()));
			return false;
		}
		
		if(ClicksUtil.clickLinks("8. Targeting and Ranking"))
		{
			GeneralUtil.selectRadioButton("g3-form:eFormFieldList:1:radioButtonSet","Title I was not changed in this amendment");
			
			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		}
		
		ClicksUtil.clickLinks("10. Submission Summary");
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit the Applicant Submission, it could be disabled!");
			return false;
		}
		
		
		return true;
	}
	
        public static boolean tedFY13_SubmitApplication(Sov sov) throws Exception{
		
		if(!openFOSubmision(sov))
		{
			log.error("Could Not Open Submission in FO: ".concat(sov.getApplicantNumber()));
			return false;
		}
		
		ClicksUtil.clickLinks("Submission Summary");
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit the Applicant Submission, it could be disabled!");
			return false;
		}
		
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitSuperApplicationESign(Sov sov) throws Exception{
		
		if(!openFOSubmision(sov))
		{
			log.error("Could Not Open Submission in FO: ".concat(sov.getApplicantNumber()));
			return false;
		}
		
		if(!GeneralUtil.setTextById(ISovConst.SuperAppeSignPwdId, "1234"))
		{
			log.error("Could not enter the eSign!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit the Super Application Submission, it could be disabled due to invalid pin number!");
			return false;
		}
		
		
		return true;
	}
	
	
	public static boolean cfpFY14_SubmitGrantApplicationESign(Sov sov, String approvedOrNot) throws Exception{
		
		if(!openFOSubmision(sov))
		{
			log.error("Could Not Open Submission in FO: ".concat(sov.getApplicantNumber()));
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:0:numericDropdown", approvedOrNot))
		{
			log.error("Could not select form the dropdown!");
			return false;
		}
		
		GeneralUtil.takeANap(1.0);
		
		String pwdTextId = "g3-form:eFormFieldList:2:password";
		
		if(approvedOrNot.equalsIgnoreCase("Not Approved"))
		{
			
			if(!GeneralUtil.setTextById("g3-form:eFormFieldList:1:textArea_Below", "Not Approving this application, testing SOV"))
			{
				log.error("Could not enter the reason for not Approving!");
				return false;
			}
			
			pwdTextId = "g3-form:eFormFieldList:3:password";
			
		}
		
		if(!GeneralUtil.setTextById(pwdTextId, "1234"))
		{
			log.error("Could not enter the pin!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit the Super Application Submission, it could be disabled due to invalid pin number!");
			return false;
		}
		
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitInitialReview(Sov sov, String approvedOrNot) throws Exception{
		
		if(!openEvaluationStep(sov, IFiltersConst.submissionsStatus_InProgress_StatusSubView))
		{
			log.error("error Opening!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:1:numericDropdown", approvedOrNot))
		{
			log.error("error Select from dropdown: ".concat(approvedOrNot));
			return false;
		}
		
		GeneralUtil.takeANap(0.5);
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit the Initial Review, it could be disabled due to invalid pin number!");
			return false;
		}		
		
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitCfpTeamReview(Sov sov) throws Exception{
		
		
		//Non - Critical step
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitPOApproval(Sov sov, String approvedOrNot) throws Exception{
		
		if(!openEvaluationStep(sov, IFiltersConst.submissionsStatus_InProgress_StatusSubView))
		{
			log.error("error Opening!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:1:numericDropdown", approvedOrNot))
		{
			log.error("error Select from dropdown: ".concat(approvedOrNot));
			return false;
		}
		
		GeneralUtil.takeANap(0.5);
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Program Offficer Approval, it could be disabled!");
			return false;
		}		
		
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitGrantAgreementCreated(Sov sov) throws Exception{
		
		if(!openSubInMyAssignedSubmission(sov)) {
			
			return false;
		}
		
		ClicksUtil.clickLinks("10. Submission Summary");
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Grant Agreement Created, it could be disabled!");
			return false;
		}		
		
		
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitFinanceGrantAgreementReview(Sov sov, String approvedOrNot) throws Exception{
		
		if(!openEvaluationStep(sov, IFiltersConst.submissionsStatus_InProgress_StatusSubView))
		{
			log.error("error Opening!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:0:numericDropdown", approvedOrNot))
		{
			log.error("error Select from dropdown: ".concat(approvedOrNot));
			return false;
		}
		
		GeneralUtil.takeANap(0.5);
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Finance Grant Agreement Review, it could be disabled!");
			return false;
		}		
		
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitDirectorGrantReview(Sov sov, String approvedOrNot) throws Exception{
		
		if(!openEvaluationStep(sov, IFiltersConst.submissionsStatus_InProgress_StatusSubView))
		{
			log.error("error Opening!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:0:numericDropdown", approvedOrNot))
		{
			log.error("error Select from dropdown: ".concat(approvedOrNot));
			return false;
		}
		
		GeneralUtil.takeANap(0.5);
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Director Grant Review, it could be disabled!");
			return false;
		}		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitCfoApprovalESign(Sov sov, String approvedOrNot) throws Exception{
		
		if(!openEvaluationStep(sov, IFiltersConst.submissionsStatus_InProgress_StatusSubView))
		{
			log.error("error Opening!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(ISovConst.SuperAppeSignPwdId, "1234"))
		{
			log.error("Could not enter the eSign!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:2:numericDropdown", approvedOrNot))
		{
			log.error("error Select from dropdown: ".concat(approvedOrNot));
			return false;
		}
		
		GeneralUtil.takeANap(0.5);
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Director Grant Review, it could be disabled!");
			return false;
		}		
		
		
		return true;
	}
	
	
public static boolean cfpFY13_SubmitCfoApprovalESign_cfOOnly(Sov sov, String approvedOrNot) throws Exception{
		
		if(!openEvaluationStep(sov, IFiltersConst.submissionsStatus_InProgress_StatusSubView))
		{
			log.error("error Opening!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(ISovConst.SuperAppeSignPwdId, "cfO0n1y"))
		{
			log.error("Could not enter the eSign!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:2:numericDropdown", approvedOrNot))
		{
			log.error("error Select from dropdown: ".concat(approvedOrNot));
			return false;
		}
		
		GeneralUtil.takeANap(0.5);
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Director Grant Review, it could be disabled!");
			return false;
		}		
		
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitSuperGrantAgreementESign(Sov sov) throws Exception{
		
		if(!openFOSubmision(sov))
		{
			log.error("Could Not Open Submission in FO: ".concat(sov.getApplicantNumber()));
			return false;
		}
		
		if(!GeneralUtil.setTextById(ISovConst.SuperAppeSignPwdId, "1234"))
		{
			log.error("Could not enter the eSign!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit the Super Application Submission, it could be disabled due to invalid pin number!");
			return false;
		}
		
		
		return true;
	}
	
	
	public static boolean cfpFY14_SubmitGrantAwardESign(Sov sov, String approvedOrNot) throws Exception{
		
		if(!openFOSubmision(sov))
		{
			log.error("Could Not Open Submission in FO: ".concat(sov.getApplicantNumber()));
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:0:numericDropdown", approvedOrNot))
		{
			log.error("Could not select form the dropdown!");
			return false;
		}
		
		GeneralUtil.takeANap(1.5);
		
		String pwdTextId = "g3-form:eFormFieldList:2:password";
		
		if(approvedOrNot.equalsIgnoreCase("Not Approved"))
		{
			
			if(!GeneralUtil.setTextById("g3-form:eFormFieldList:1:textArea_Below", "Not Approving this application, testing SOV"))
			{
				log.error("Could not enter the reason for not Approving!");
				return false;
			}
			
			pwdTextId = "g3-form:eFormFieldList:3:password";
			
		}
		
		if(!GeneralUtil.setTextById(pwdTextId, "1234"))
		{
			log.error("Could not enter the pin!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit the Super Application Submission, it could be disabled due to invalid pin number!");
			return false;
		}
		
		
		return true;
	}
	
	
public static boolean cfpFY14_SubmitGrantAwardESign_applicant(Sov sov, String approvedOrNot) throws Exception{
		
		if(!openFOSubmision(sov))
		{
			log.error("Could Not Open Submission in FO: ".concat(sov.getApplicantNumber()));
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:0:numericDropdown", approvedOrNot))
		{
			log.error("Could not select form the dropdown!");
			return false;
		}
		
		GeneralUtil.takeANap(1.5);
		
		String pwdTextId = "g3-form:eFormFieldList:2:password";
		
		if(approvedOrNot.equalsIgnoreCase("Not Approved"))
		{
			
			if(!GeneralUtil.setTextById("g3-form:eFormFieldList:1:textArea_Below", "Not Approving this application, testing SOV"))
			{
				log.error("Could not enter the reason for not Approving!");
				return false;
			}
			
			pwdTextId = "g3-form:eFormFieldList:3:password";
			
		}
		
		if(!GeneralUtil.setTextById(pwdTextId, "489727"))
		{
			log.error("Could not enter the pin!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit the Super Application Submission, it could be disabled due to invalid pin number!");
			return false;
		}
		
		
		return true;
	}
	


public static boolean cfpFY14_SubmitGrantAwardESign_applicant_JudithDenovo(Sov sov, String approvedOrNot) throws Exception{
	
	if(!openFOSubmision(sov))
	{
		log.error("Could Not Open Submission in FO: ".concat(sov.getApplicantNumber()));
		return false;
	}
	
	if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:0:numericDropdown", approvedOrNot))
	{
		log.error("Could not select form the dropdown!");
		return false;
	}
	
	GeneralUtil.takeANap(1.5);
	
	String pwdTextId = "g3-form:eFormFieldList:2:password";
	
	if(approvedOrNot.equalsIgnoreCase("Not Approved"))
	{
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:1:textArea_Below", "Not Approving this application, testing SOV"))
		{
			log.error("Could not enter the reason for not Approving!");
			return false;
		}
		
		pwdTextId = "g3-form:eFormFieldList:3:password";
		
	}
	
	if(!GeneralUtil.setTextById(pwdTextId, "091120"))
	{
		log.error("Could not enter the pin!");
		return false;
	}
	
	ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
	
	if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
	{
		log.error("Could not Submit the Super Application Submission, it could be disabled due to invalid pin number!");
		return false;
	}
	
	
	return true;
}
	public static boolean cfpFY14_SubmitPAQuarterlyFinancialReportInPo(Sov sov) throws Exception{
		
		if(!openSubInMyAssignedSubmission(sov)) {
			
			log.error("Could not open submission in My Assigned Project: ".concat(sov.getCurrStepName()));
			
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Could not click next Buttoon, the Button is unavailable!");
			return false;
		}
		
		if(GeneralUtil.isButtonExists("Recalculate"))
		{
			
			if(!ClicksUtil.clickButtons("Recalculate"))
			{
				log.error("Could not click on Calculate Button!");
				return false;
			}
			
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit the Super Application Submission, it could be disabled due to invalid pin number!");
			return false;
		}
		
		
		return true;
	}
	
	
	public static boolean cfpFY14_SubmitPAFinanceReportReview(Sov sov, String status, String approvedOrNot) throws Exception{
		
		if(!openEvaluationStep(sov, status))
		{
			log.error("error Opening!");
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Could not click next Buttoon, the Button is unavailable!");
			return false;
		}
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:18:dataGrid:1:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId)+"279:1_1:gridCell_NumericEntry", "50"))
		{
			log.error("could not find the TextFiled!");
			return false;
		}
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:20:dataGrid:1:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId)+":7_7:gridCell_NumericEntry", "50"))
		{
			log.error("could not find the TextFiled!");
			return false;
		}
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:20:dataGrid:1:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId)+":8_8:gridCell_NumericEntry", "1"))
		{
			log.error("could not find the TextFiled!");
			return false;
		}
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:20:dataGrid:1:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId)+":9_9:gridCell_NumericEntry", "1"))
		{
			log.error("could not find the TextFiled!");
			return false;
		}
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:20:dataGrid:1:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId)+":10_10:gridCell_NumericEntry", "2014"))
		{
			log.error("could not find the TextFiled!");
			return false;
		}
		
		if(!ClicksUtil.clickButtons("Recaculate"))
		{
			log.error("Could not click on ReCalculate Button!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:0:numericChildDropdown", approvedOrNot))
		{
			log.error("error Select from dropdown: ".concat(approvedOrNot));
			return false;
		}
		
		if(!GeneralUtil.toggleCheckBoxById("g3-form:eFormFieldList:4:booleanProperty", true))
		{
			log.error("Unable to check the box off to override the total payment and award!");
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit PA Finance Report Review, it could be disabled!");
			return false;
		}			
		
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitCfoGrantAgreementESign(Sov sov, String approvedOrNot) throws Exception{
		
		if(!openEvaluationStep(sov, IFiltersConst.submissionsStatus_InProgress_StatusSubView))
		{
			log.error("error Opening!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(ISovConst.SuperAppeSignPwdId, "1234"))
		{
			log.error("Could not enter the eSign!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:2:numericDropdown", approvedOrNot))
		{
			log.error("error Select from dropdown: ".concat(approvedOrNot));
			return false;
		}
		
		GeneralUtil.takeANap(0.5);
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Cfo Grant Agreement ESign, it could be disabled!");
			return false;
		}		
		
		
		return true;
	}
	
	
	
        public static boolean cfpFY13_SubmitCfoGrantAgreementESign_cfO0nly(Sov sov, String approvedOrNot) throws Exception{
		
		if(!openEvaluationStep(sov, IFiltersConst.submissionsStatus_InProgress_StatusSubView))
		{
			log.error("error Opening!");
			return false;
		}
		
		if(!GeneralUtil.setTextById(ISovConst.SuperAppeSignPwdId, "cfO0n1y"))
		{
			log.error("Could not enter the eSign!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:2:numericDropdown", approvedOrNot))
		{
			log.error("error Select from dropdown: ".concat(approvedOrNot));
			return false;
		}
		
		GeneralUtil.takeANap(0.5);
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Cfo Grant Agreement ESign, it could be disabled!");
			return false;
		}		
		
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitGrantAgreementApproved(Sov sov) throws Exception{
		
		if(!openSubInMyAssignedSubmission(sov)) {
			
			return false;
		}
		
		ClicksUtil.clickLinks("10. Submission Summary");
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Grant Agreement Approved, it could be disabled!");
			return false;
		}		
		
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitApprovedAward(Sov sov) throws Exception{
		
		if(!openAppSubList(sov, IFiltersConst.submissionsStatus_InProgress_StatusSubView))
		{
			log.error("Could not open the Submission Details in App Sub List!");
			return false;
		}
			
		
		if(!GeneralUtil.toggleCheckBoxById("g3-form:eFormFieldList:1:booleanProperty", true))
		{
			log.error("Could not check the Check box off!");
			return false;
		}
		
		GeneralUtil.takeANap(1.5);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Approved Award, it could be disabled!");
			return false;
		}				
		
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitFinanceCreateSubmissionSchedule(Sov sov) throws Exception{
		
		if(!openSubInMyAssignedSubmission(sov)) {
			
			return false;
		}
		
		if(!ClicksUtil.clickLinks("Submission Schedule"))
		{
			log.error("Could not FInd the SUbmission Schedule Menu!");
			return false;
		}
		
		if(!TablesUtil.openInTableByImage(ITablesConst.eFormListDataWithLetters, sov.getSubProjectName(), 1))
		{
			if(!TablesUtil.openInTableByImage(ITablesConst.eFormListDataWithLetters, sov.getSubProjectName(), 0))
			{
				log.error("Could Not Find Schedule Entry in Financial Create Submission Schedule: ".concat(sov.getSubProjectName()));
				return false;
			}
		}
		
		if(!GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, GeneralUtil.getNextYear()))
		{
			log.error("Could not set End Date");
			
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Could not click Next Button, the Button unavailable!");
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Approved Award, it could be disabled!");
			return false;
		}				
		
		return true;
	}
	
	
	
public static boolean cfpFY13_SubmitFinanceCreateSubmissionSchedule_New(Sov sov) throws Exception{
		
		if(!openSubInMyAssignedSubmission(sov)) {
			
			return false;
		}
		
		if(!ClicksUtil.clickLinks("Submission Schedule"))
		{
			log.error("Could not FInd the SUbmission Schedule Menu!");
			return false;
		}
		
		if(!ClicksUtil.clickLinks("Submission Summary"))
		{
			log.error("Could not FInd the SUbmission Summary!");
			return false;
		}
		
		
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Approved Award, it could be disabled!");
			return false;
		}				
		
		return true;
	}
	
	
	public static boolean cfpFY14_SubmitFinanceCreateSubmissionSchedule(Sov sov) throws Exception{
		
		if(!openSubInMyAssignedSubmission(sov)) {
			
			log.error("Could not open submission in My Assigned Project: ".concat(sov.getCurrStepName()));
			
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Could not click Next Button, the Button unavailable!");
			return false;
		}
		
		if(!TablesUtil.openInTableByImage(ITablesConst.eFormListDataWithLetters, sov.getSubProjectName(), 1))
		{
			log.error("Could Not Find Schedule Entry in Financial Create Submission Schedule: ".concat(sov.getSubProjectName()));
			return false;
		}
		
		if(!GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, GeneralUtil.getNextYear()))
		{
			log.error("Could not set End Date");
			
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Could not click Next Button, the Button unavailable!");
			return false;
		}
		
//		if(!ClicksUtil.clickLinks("Submission Summary"))
//		{
//			log.error("Could not FInd the SUbmission Schedule Menu!");
//			return false;
//		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Approved Award, it could be disabled!");
			return false;
		}				
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitPAQuarterlyFinancialReport(Sov sov) throws Exception{
		
		if(!openFOSubmision(sov))
		{
			log.error("Could Not Open Submission in FO: ".concat(sov.getApplicantNumber()));
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Could not click next Buttoon, the Button is unavailable!");
			return false;
		}
		
		if(!GeneralUtil.toggleCheckBoxById("g3-form:eFormFieldList:14:booleanProperty", true))
		{
			log.error("Could not Check the box off!");
			return false;
		}
		
		if(GeneralUtil.isButtonExists("Recalculate"))
		{
			
			if(!ClicksUtil.clickButtons("Recalculate"))
			{
				log.error("Could not click on Calculate Button!");
				return false;
			}
			
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:1:password", "1234"))
		{
			log.error("Could not enter the eSign!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit the Super Application Submission, it could be disabled due to invalid pin number!");
			return false;
		}
		
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitPAFinanceReportReview(Sov sov, String status, String approvedOrNot) throws Exception{
		
		if(!openEvaluationStep(sov, status))
		{
			log.error("error Opening!");
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Could not click next Buttoon, the Button is unavailable!");
			return false;
		}
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:20:dataGrid:1:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId)+":1_1:gridCell_NumericEntry", "1"))
		{
			log.error("could not find the TextFiled!");
			return false;
		}
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:22:dataGrid:1:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId)+":7_7:gridCell_NumericEntry", "1"))
		{
			log.error("could not find the TextFiled!");
			return false;
		}
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:22:dataGrid:1:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId)+":8_8:gridCell_NumericEntry", "1"))
		{
			log.error("could not find the TextFiled!");
			return false;
		}
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:22:dataGrid:1:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId)+":9_9:gridCell_NumericEntry", "1"))
		{
			log.error("could not find the TextFiled!");
			return false;
		}
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:22:dataGrid:1:".concat(ILBFunctionConst.lbf_DataGrid_MiddleOf_TagId)+":10_10:gridCell_NumericEntry", "2014"))
		{
			log.error("could not find the TextFiled!");
			return false;
		}
		
		if(!ClicksUtil.clickButtons("Recaculate"))
		{
			log.error("Could not click on ReCalculate Button!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:0:numericChildDropdown", approvedOrNot))
		{
			log.error("error Select from dropdown: ".concat(approvedOrNot));
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Cfo Grant Agreement ESign, it could be disabled!");
			return false;
		}			
		
		
		return true;
	}
	
	
	public static boolean cfpFY13_SubmitPATipGapPayment(Sov sov, String status) throws Exception{
		
		if(!openEvaluationStep(sov, status))
		{
			log.error("error Opening!");
			return false;
		}
		
		ClicksUtil.clickLinks("Submission Summary");
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit TipGap Payment, it could be disabled!");
			return false;
		}	
		
		
		return true;
	}
	
	//STart Nord FY 14	
	
	public static boolean nordFy14_SubmitGrantApplication(Sov sov) throws Exception{
		
		if(!openFOSubmision(sov))
		{
			log.error("Could Not Open Submission in FO: ".concat(sov.getApplicantNumber()));
			return false;
		}
		
		ClicksUtil.clickLinks("Submission Summary");
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit TipGap Payment, it could be disabled!");
			return false;
		}
		
		
		return true;
	}
	
	
	public static boolean nordFy14_SubmitGrantApplicationESign(Sov sov, String approvedOrNot) throws Exception{
		
		if(!openFOSubmision(sov))
		{
			log.error("Could Not Open Submission in FO: ".concat(sov.getApplicantNumber()));
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:0:numericDropdown", approvedOrNot))
		{
			log.error("Could not select form the dropdown!");
			return false;
		}
		
		GeneralUtil.takeANap(1.5);
		
		String pwdTextId = "g3-form:eFormFieldList:2:password";
		
		if(approvedOrNot.equalsIgnoreCase("Not Approved"))
		{
			
			if(!GeneralUtil.setTextById("g3-form:eFormFieldList:1:textArea_Below", "Not Approving this application, testing SOV"))
			{
				log.error("Could not enter the reason for not Approving!");
				return false;
			}
			
			pwdTextId = "g3-form:eFormFieldList:3:password";
			
		}
		
		if(!GeneralUtil.setTextById(pwdTextId, "1234"))
		{
			log.error("Could not enter the pin!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit the Super Application Submission, it could be disabled due to invalid pin number!");
			return false;
		}
		
		
		return true;
	}
	
	
	public static boolean nordFy14_SubmitProgramTeamReview(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean nordFy14_SubmitPOApproval(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean nordFy14_SubmitGrantAgreementCreated(Sov sov) throws Exception{
		
		if(!openSubInMyAssignedSubmission(sov)) {
			
			return false;
		}
		
		ClicksUtil.clickLinks("Submission Summary");
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Grant Agreement Approved, it could be disabled!");
			return false;
		}		
		
		
		return true;
	}
	
	
	public static boolean nordFy14_SubmitFinanceGrantAgreementReview(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean nordFy14_SubmitDirectorGrantReview(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean nordFy14_SubmitCfoApprovalESign(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean nordFY14_SubmitGrantAwardESign(Sov sov, String approvedOrNot) throws Exception{
		
		if(!openFOSubmision(sov))
		{
			log.error("Could Not Open Submission in FO: ".concat(sov.getApplicantNumber()));
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:0:numericDropdown", approvedOrNot))
		{
			log.error("Could not select form the dropdown!");
			return false;
		}
		
		GeneralUtil.takeANap(1.5);
		
		String pwdTextId = "g3-form:eFormFieldList:2:password";
		
		if(approvedOrNot.equalsIgnoreCase("Not Approved"))
		{
			
			if(!GeneralUtil.setTextById("g3-form:eFormFieldList:1:textArea_Below", "Not Approving this application, testing SOV"))
			{
				log.error("Could not enter the reason for not Approving!");
				return false;
			}
			
			pwdTextId = "g3-form:eFormFieldList:3:password";
			
		}
		
		if(!GeneralUtil.setTextById(pwdTextId, "1234"))
		{
			log.error("Could not enter the pin!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit the Super Application Submission, it could be disabled due to invalid pin number!");
			return false;
		}
		return true;
	}
	
	
	public static boolean nordFy14_SubmitCfoGrantAgreementESign(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean nordFy14_SubmitGrantAgreementApproved(Sov sov) throws Exception{
		
		if(!openSubInMyAssignedSubmission(sov)) {
			
			return false;
		}
		
		ClicksUtil.clickLinks("Submission Summary");
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Grant Agreement Approved, it could be disabled!");
			return false;
		}		
		
		
		return true;
	}
	
	
	public static boolean nordFy14_SubmitApprovedAward(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean nordFy14_SubmitFinanceCreateSubmissionSchedule(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean nordFy14_SubmitPAQuarterlyFinancialReport(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean nordFy14_SubmitPAFinanceReportReview(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean nordFy14_SubmitPATipGapPayment(Sov sov) throws Exception{
		
		
		return true;
	}
	
	//STart Tech Ed Equip FY 13	
	
	public static boolean techEdFy13_SubmitGrantApplication(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean techEdFy13_SubmitInitialReview(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean techEdFy13_SubmitProgramTeamReview(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean techEdFy13_SubmitGrantAgreementCreated(Sov sov) throws Exception{
		
		if(!openSubInMyAssignedSubmission(sov)) {
			
			return false;
		}
		
		if(!ClicksUtil.clickLinks("Admin Grant Information"))
		{
			log.error("unable to open Formlet: Admin Grant Information");
			return false;
		}
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:4:datePicker", GeneralUtil.getTextFieldValue("g3-form:eFormFieldList:2:datePicker")))
		{
			log.error("Unable to get Date or set Date!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		ClicksUtil.clickLinks("Submission Summary");
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Grant Agreement Approved, it could be disabled!");
			return false;
		}		
		
		
		return true;
	}
	
	
	
public static boolean techEdFy13_SubmitGrantAgreementCreated_new(Sov sov) throws Exception{
		
		if(!openSubInMyAssignedSubmission(sov)) {
			
			return false;
		}
		
		if(!ClicksUtil.clickLinks("Admin Grant Information"))
		{
			log.error("unable to open Formlet: Admin Grant Information");
			return false;
		}
		
//		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:4:datePicker", GeneralUtil.getTextFieldValue("g3-form:eFormFieldList:2:datePicker")))
//		{
//			log.error("Unable to get Date or set Date!");
//			return false;
//		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		ClicksUtil.clickLinks("Submission Summary");
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Grant Agreement Approved, it could be disabled!");
			return false;
		}		
		
		
		return true;
	}
	
	public static boolean techEdFy13_SubmitFinanceGrantAgreementReview(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean techEdFy13_SubmitDirectorGrantReview(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean techEdFy13_SubmitCfoApprovalESign(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean techEdFy13_SubmitGrantAwardESign(Sov sov, String approvedOrNot) throws Exception{
		
		if(!openFOSubmision(sov))
		{
			log.error("Could Not Open Submission in FO: ".concat(sov.getApplicantNumber()));
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:0:numericDropdown", approvedOrNot))
		{
			log.error("Could not select form the dropdown!");
			return false;
		}
		
		GeneralUtil.takeANap(1.5);
		
		String pwdTextId = "g3-form:eFormFieldList:2:password";
		
		if(approvedOrNot.equalsIgnoreCase("Not Approved"))
		{
			
			if(!GeneralUtil.setTextById("g3-form:eFormFieldList:1:textArea_Below", "Not Approving this application, testing SOV"))
			{
				log.error("Could not enter the reason for not Approving!");
				return false;
			}
			
			pwdTextId = "g3-form:eFormFieldList:3:password";
			
		}
		
		if(!GeneralUtil.setTextById(pwdTextId, "1234"))
		{
			log.error("Could not enter the pin!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit the Super Application Submission, it could be disabled due to invalid pin number!");
			return false;
		}
		
		
		return true;
	}
	
	
	public static boolean techEdFy13_SubmitCfoGrantAgreementESign(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean techEdFy13_SubmitGrantAgreementApproved(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean techEdFy13_SubmitApprovedAward(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean techEdFy13_SubmitFinanceCreateSubmissionSchedule(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean techEdFy13_SubmitPAQuarterlyFinancialReport(Sov sov) throws Exception{
		
		if(!openFOSubmision(sov))
		{
			log.error("Could Not Open Submission in FO: ".concat(sov.getApplicantNumber()));
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Could not click next Buttoon, the Button is unavailable!");
			return false;
		}
		
		if(!GeneralUtil.toggleCheckBoxById("g3-form:eFormFieldList:14:booleanProperty", true))
		{
			log.error("Could not Check the box off!");
			return false;
		}
		
		if(!ClicksUtil.clickButtons("Recalculate"))
		{
			log.error("Could not click on Calculate Button!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!GeneralUtil.doesImageExistsAnyWhereInTable(ILBFunctionConst.lbf_AttachmentList_Table_Id, "Remove this Attachment"))
		{
			
			DocumentsUtil.upload_Attachments("Attachment", "SOV", "Adobe.pdf");
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Unable to click Next Button on the Attachments List!");
			return false;
		}
		
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:1:password", "1234"))
		{
			log.error("Could not enter the eSign!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit the Super Application Submission, it could be disabled due to invalid pin number!");
			return false;
		}
		
		
		return true;
	}
	
public static boolean techEdFy13_SubmitPAQuarterlyFinancialReport_SOV(Sov sov) throws Exception{
		
		if(!openFOSubmision(sov))
		{
			log.error("Could Not Open Submission in FO: ".concat(sov.getApplicantNumber()));
			return false;
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Could not click next Buttoon, the Button is unavailable!");
			return false;
		}
		
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:12:textBox", "abcde"))
		{
			log.error("Could not enter the eSign!");
			return false;
		}
		
		if(!GeneralUtil.toggleCheckBoxById("g3-form:eFormFieldList:14:booleanProperty", true))
		{
			log.error("Could not Check the box off!");
			return false;
		}
		
		if(!ClicksUtil.clickButtons("Recalculate"))
		{
			log.error("Could not click on Calculate Button!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!GeneralUtil.doesImageExistsAnyWhereInTable(ILBFunctionConst.lbf_AttachmentList_Table_Id, "Remove this Attachment"))
		{
			
			DocumentsUtil.upload_Attachments("Attachment", "SOV", "Adobe.pdf");
		}
		
		if(!ClicksUtil.clickButtons(IClicksConst.nextBtn))
		{
			log.error("Unable to click Next Button on the Attachments List!");
			return false;
		}
		
		
		if(!GeneralUtil.setTextById("g3-form:eFormFieldList:1:password", "355941"))
		{
			log.error("Could not enter the eSign!");
			return false;
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit the Super Application Submission, it could be disabled due to invalid pin number!");
			return false;
		}
		
		
		return true;
	}
	public static boolean techEdFy13_SubmitPAProgramTeamReviewPaymentRequest(Sov sov, String approvedOrNot) throws Exception{
		
		if(!openEvaluationStep(sov, IFiltersConst.submissionsStatus_InProgress_StatusSubView))
		{
			log.error("error Opening!");
			return false;
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList("g3-form:eFormFieldList:1:numericDropdown", approvedOrNot))
		{
			log.error("error Select from dropdown: ".concat(approvedOrNot));
			return false;
		}
		
		GeneralUtil.takeANap(0.5);
		
		ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);
		
		if(!ClicksUtil.clickButtons(IClicksConst.submitBtn))
		{
			log.error("Could not Submit Director Grant Review, it could be disabled!");
			return false;
		}		
		
		
		return true;
	}
	
	
	public static boolean techEdFy13_SubmitPAFinanceReportReview(Sov sov) throws Exception{
		
		
		return true;
	}
	
	
	public static boolean techEdFy13_SubmitPATipGapPayment(Sov sov) throws Exception{
		
		
		return true;
	}

}
