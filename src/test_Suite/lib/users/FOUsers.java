package test_Suite.lib.users;

import static watij.finders.SymbolFactory.*;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.users.IApplicantTypesConst;
import test_Suite.constants.users.IApplicantsConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.elements.Div;
import watij.runtime.ie.IE;

public class FOUsers {
	
	private static Log log = LogFactory.getLog(FOUsers.class);
	
	private int foUserBeat = 0;
	private int startIndex = 0;
	private int endIndex = 0;
	
	private String foApplicants[] = null;
	private String foRegistrants[] = null;
	private String foOrgName = null;
	private String foRegistrantFullId = null;
	private String foFName;
	private String foLName;
	private String foEmail;
	private String foConfEmail;	
	private String foUserName;
	private String foPassword;
	private String foConfPassword;
	private String foQuestion;
	private String foAnswer;
	private String foLocale;
	private String applicantCategory = null;
	private String applicantType = null;
	
	private boolean retValue = false;
	
	private ArrayList<String> arrList;

	
	//**************** Constractors ***************************
	
	public FOUsers() {
	}
	
	public FOUsers(int foUserBeat, int startIndex, int endIndex) {
		setFoUserBeat(foUserBeat);
		
		setStartIndex(startIndex);
		setEndIndex(endIndex);
		
		setFoApplicants(IPreTestConst.FrontUsers[0]);
		setFoRegistrants(IPreTestConst.FrontUsers[1]);
		setFoOrgName(IPreTestConst.FO_OrgShortName);
		setFoRegistrantFullId("/" + getFoRegistrants()[2] + getFoUserBeat() + "/");
		this.setFoUserName(this.getFoRegistrants()[4] + Integer.toString(startIndex));
		this.setFoPassword(IPreTestConst.Pwd);
		this.setFoQuestion(IPreTestConst.Question);
		
	}
	
	public FOUsers(int foUserBeat) {
		
		setFoUserBeat(foUserBeat);		
		
		setFoApplicants(IPreTestConst.FrontUsers[0]);
		setFoRegistrants(IPreTestConst.FrontUsers[1]);
		setFoOrgName(IPreTestConst.FO_OrgShortName);
		setFoRegistrantFullId("/" + getFoRegistrants()[2] + foUserBeat + "/");
	}
	
	public FOUsers(String[] profile, String[] applicant, int userBeat) {	

		this.setFoUserBeat(userBeat);
		
		this.setStartIndex(1);
		this.setEndIndex(1);
		
		this.setFoApplicants(applicant);
		this.setFoRegistrants(profile);
		
		this.setFoOrgName(IPreTestConst.FO_OrgShortName);
		
		this.setFoFName(profile[0]);
		this.setFoLName(profile[1]);
		this.setFoEmail(profile[2]);
		this.setFoConfEmail(profile[3]);
		
		this.setFoLocale(profile[4]);
		
		this.setFoRegistrantFullId("/" + profile[1] + this.getFoUserBeat() + "/");
		this.setFoUserName(profile[5]);
		this.setFoPassword(profile[6]);
		this.setFoConfPassword(profile[7]);
		this.setFoQuestion(profile[8]);
		this.setFoAnswer(profile[9]);
		
	}
	
	
	//************ Methods*************************
	
	public void constractFORegistrantId() throws Exception {
		
		this.setFoRegistrantFullId("/" + getFoRegistrants()[2] + getFoUserBeat() + "/" );
	}
	
	public boolean createPOApplicants() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		retValue = false;
		
		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);
		
		for(int i=this.startIndex; i<=this.endIndex; i++ )
		{
			FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ApplicantName_Lbl, this.foOrgName + Integer.toString(i), "Exact");
			
			if (!TablesUtil.findInTable(ITablesConst.applicantsTableId, this.foOrgName + Integer.toString(i)))
			{
				ClicksUtil.clickImage(IClicksConst.newImg);
				
				ie.selectList(id, IApplicantsConst.applicantType_DropDownId).select(IApplicantsConst.applicantType_Organization);
				
				GeneralUtil.takeANap(1.5);
				
				ie.textField(id, IApplicantsConst.applicantName_FldID).set(this.foOrgName + Integer.toString(i));
				
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				
				ClicksUtil.clickButtons(IClicksConst.poBackToApplicantsList);			
				
			}
			retValue = true;
		}
		
		return retValue;
	}
	
	
	public boolean createPORegistrant() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		retValue = false;
		
		int rowIndex = -1;
		
		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);
		
		for(int i=this.startIndex; i<=this.endIndex; i++ )
		{		
			
			FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ApplicantName_Lbl, this.foOrgName + Integer.toString(i),"Exact");
			
			rowIndex = TablesUtil.getRowIndex(ITablesConst.applicantsTableId, this.foOrgName + Integer.toString(i));
			
			if (rowIndex > -1)
			{
				ie.body(id, ITablesConst.applicantsTableId).row(rowIndex).cell(1).link(title,"Registrants List").click();
				
				if (!TablesUtil.findInTable(ITablesConst.applicantRegistrantsTableId, this.foRegistrants[4] + Integer.toString(i)))
				{
					ClicksUtil.clickImageByAlt("Create New Registrant for the Applicant");
					
					ie.textField(id,IProjectsConst.po_UserAccount_FName_TxtId).set(this.foRegistrants[1] + Integer.toString(i));
					
					ie.textField(id, IProjectsConst.po_UserAccount_LName_TxtId).set(this.foRegistrants[2] + Integer.toString(i));
					
					ie.textField(id, IProjectsConst.po_UserAccount_Email_TxtId).set(this.foRegistrants[0] + Integer.toString(i) + this.foRegistrants[3]);
					
					ie.textField(id, IProjectsConst.po_UserAccount_EmailConf_TxtId).set(this.foRegistrants[0] + Integer.toString(i) + this.foRegistrants[3]);
					
					ie.selectList(id, IProjectsConst.po_UserAccount_Language_DropdownId).select("/English/");
					
					ie.textField(id, IProjectsConst.po_UserAccount_UName_TxtId).set(this.foRegistrants[4] + Integer.toString(i));
					
					ie.textField(id, IProjectsConst.po_UserAccount_Pwd_TxtId).set(this.getFoPassword());
					
					ie.textField(id, IProjectsConst.po_UserAccount_PwdConf_TxtId).set(this.getFoPassword());
					
					ie.textField(id, IProjectsConst.po_UserAccount_Question_TxtId).set(this.getFoQuestion());
					
					ie.textField(id, IProjectsConst.po_UserAccount_Answer_TxtId).set(this.getFoQuestion());
					
					ClicksUtil.clickButtons(IClicksConst.saveBtn);	
					
					if(GeneralUtil.FindTextOnPage("The user name already exists."))
					{
						ClicksUtil.clickButtons(IClicksConst.backBtn);
						
						associateRegistrantInPO(i);
					}
					
				}
				else
				{
					log.info("Registrant Already Associated");
				}
				
				if(!GeneralUtil.isButtonExistsByValue(IClicksConst.backToListBtn))
				{
					ClicksUtil.clickButtons(IClicksConst.backBtn);
				}
				
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				
				retValue = true;
			}
		}			
		
		return retValue;
	}	
	
	public boolean addPORegistrant() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		retValue = false;
		
		int rowIndex = -1;
		
		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);
		
		FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ApplicantName_Lbl, this.foOrgName + Integer.toString(this.foUserBeat), "Exact");
		
		rowIndex = TablesUtil.getRowIndex(ITablesConst.applicantsTableId, this.foOrgName + Integer.toString(this.foUserBeat));
		
		if (rowIndex > -1)
		{
			ie.body(id, ITablesConst.applicantsTableId).row(rowIndex).cell(1).link(title, "Registrants List").click();
			
			ClicksUtil.clickImageByAlt("Add Existing Registrant for the Applicant");
			
			ie.textField(id,IApplicantsConst.registrant_ApplicantAddUser_LoginName_txtField).set(this.foRegistrants[4] + Integer.toString(this.foUserBeat));
			
			ie.textField(id, IApplicantsConst.registrant_ApplicantAddUser_Email_txtField).set(this.foRegistrants[0] + Integer.toString(this.foUserBeat) + this.foRegistrants[3]);
			
			ClicksUtil.clickButtons(IClicksConst.addRegistrantBtn);
			
			ClicksUtil.clickButtons(IClicksConst.backToListBtn);
			retValue = true;
		}		
		
		return retValue;
	}	
	
	public boolean addExistantRegistrantsInPO() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		int rowIndex = -1;
		
		ClicksUtil.clickLinks(IClicksConst.openPOApplicantListLnk);
		
		Div tDiv = TablesUtil.tableDiv();
		
		for(int x = this.startIndex; x <= this.endIndex; x++)
		{
			
			FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ApplicantName_Lbl, this.foOrgName + Integer.toString(x),"Exact");
			
			rowIndex = TablesUtil.getRowIndex(ITablesConst.applicantsTableId, this.foOrgName + Integer.toString(x));
			
			if (rowIndex > -1)
			{
				tDiv.body(id, ITablesConst.applicantsTableId).row(rowIndex).link(title, IClicksConst.poRegList_IconTtl).click();
				
				ClicksUtil.clickImageByAlt("Add Existing Registrant for the Applicant");
								
				ie.textField(id,IApplicantsConst.registrant_ApplicantAddUser_LoginName_txtField).set(this.foRegistrants[4] + Integer.toString(x));
				
				ie.textField(id, IApplicantsConst.registrant_ApplicantAddUser_Email_txtField).set(this.foRegistrants[0] + Integer.toString(x) + this.foRegistrants[3]);
				
				ClicksUtil.clickButtons(IClicksConst.addRegistrantBtn);
								
				ClicksUtil.clickButtons(IClicksConst.backToApplicantsListLnk);		
				
				
//				while (!ClicksUtil.clickButtons(IClicksConst.backToApplicantsListLnk)) {
//				
//				ClicksUtil.clickButtons("Back to List");				
//				}
//				
			}
			else {
				
				log.error("No Applicant in the List");				
				return false;
			}
			
		}		
		
		return true;
	}
	
	public boolean createNewProfile() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(null != this.getApplicantCategory())
		{
			if(GeneralUtil.isSelectListExists(IApplicantTypesConst.appTypeFO_DrpDown) && this.getApplicantCategory().equals(IApplicantTypesConst.EAppCategories.selfRepresented.toString()))
			{
				GeneralUtil.selectFullStringInDropdownList(IApplicantTypesConst.appTypeFO_DrpDown, this.getApplicantType());
			}
			
		}		
		
		ie.textField(id, IProjectsConst.fo_UserAccount_FName_TxtId).set(this.getFoFName());
		
		ie.textField(id, IProjectsConst.fo_UserAccount_LName_TxtId).set(this.getFoLName());
		
		ie.textField(id, IProjectsConst.fo_UserAccount_Email_TxtId).set(this.getFoEmail());
		
		ie.textField(id, IProjectsConst.fo_UserAccount_EmailConf_TxtId).set(this.getFoConfEmail());
		
		ie.selectList(id, IProjectsConst.fo_UserAccount_Language_TxtId).select(this.getFoLocale());
		
		ie.textField(id, IProjectsConst.fo_UserAccount_UName_TxtId).set(this.getFoUserName());
				
		if(ie.textField(id, IProjectsConst.fo_UserAccount_Pwd_TxtId).exists())
		{
			ie.textField(id, IProjectsConst.fo_UserAccount_Pwd_TxtId).set(this.getFoPassword());
		}
		else
		{
			ie.textField(id, IProjectsConst.fo_UserAccount_Pwd_TxtId).set(this.getFoPassword());
		}		
		
		ie.textField(id, IProjectsConst.fo_UserAccount_PwdConf_TxtId).set(this.getFoConfPassword());
		
		ie.textField(id, IProjectsConst.fo_UserAccount_Question_TxtId).set(this.getFoQuestion());
		
		ie.textField(id, IProjectsConst.fo_UserAccount_Answer_TxtId).set(this.getFoAnswer());
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		arrList = GeneralUtil.checkForErrorMessages();
		
		if(arrList == null || arrList.isEmpty())
		{
			return true;
		}
		else
		{
			for(String string : arrList)
			{
				log.error("Validation Error In FO User Profile: " + string);
			}
		}		
		
		return false;
	}
	
	public boolean createPONewProfile() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(null != this.getApplicantCategory())
		{
			if(GeneralUtil.isSelectListExists(IApplicantTypesConst.appTypeFO_DrpDown) && this.getApplicantCategory().equals(IApplicantTypesConst.EAppCategories.selfRepresented.toString()))
			{
				GeneralUtil.selectFullStringInDropdownList(IApplicantTypesConst.appTypeFO_DrpDown, this.getApplicantType());
			}
			
		}		
		
		ie.textField(id, IProjectsConst.po_UserAccount_FName_TxtId).set(this.getFoFName());
		
		ie.textField(id, IProjectsConst.po_UserAccount_LName_TxtId).set(this.getFoLName());
		
		ie.textField(id, IProjectsConst.po_UserAccount_Email_TxtId).set(this.getFoEmail());
		
		ie.textField(id, IProjectsConst.po_UserAccount_EmailConf_TxtId).set(this.getFoConfEmail());
		
		ie.selectList(id, IProjectsConst.po_UserAccount_Language_DropdownId).select(this.getFoLocale());
		
		ie.textField(id, IProjectsConst.po_UserAccount_UName_TxtId).set(this.getFoUserName());
				
		if(ie.textField(id, IProjectsConst.po_UserAccount_Pwd_TxtId).exists())
		{
			ie.textField(id, IProjectsConst.po_UserAccount_Pwd_TxtId).set(this.getFoPassword());
		}
		else
		{
			ie.textField(id, IProjectsConst.po_UserAccount_Pwd_TxtId).set(this.getFoPassword());
		}		
		
		ie.textField(id, IProjectsConst.po_UserAccount_PwdConf_TxtId).set(this.getFoConfPassword());
		
		ie.textField(id, IProjectsConst.po_UserAccount_Question_TxtId).set(this.getFoQuestion());
		
		ie.textField(id, IProjectsConst.po_UserAccount_Answer_TxtId).set(this.getFoAnswer());
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		arrList = GeneralUtil.checkForErrorMessages();
		
		if(arrList == null || arrList.isEmpty())
		{
			return true;
		}
		else
		{
			for(String string : arrList)
			{
				log.error("Validation Error In FO User Profile: " + string);
			}
		}		
		
		return false;
	}
	
	
	public boolean createProfile() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		retValue = false;
    	
    	Assert.assertTrue(ie.link(text,IClicksConst.createProfileLnk).exists(), "FAIL: could not find create Profile Link in FO!");
		
		//Watij having a hard time clicking links on splash page
		//need more that one click to get response!		
		while(!ie.textField(id,IProjectsConst.fo_UserAccount_FName_TxtId).exists()) {

			ClicksUtil.clickLinks(IClicksConst.createProfileLnk);
		}
		
		if(GeneralUtil.isSelectListExists(IApplicantTypesConst.appTypeFO_DrpDown) && this.getApplicantType() != null)
		{
			GeneralUtil.selectFullStringInDropdownList(IApplicantTypesConst.appTypeFO_DrpDown, this.getApplicantType());
		}
		
		ie.textField(id, IProjectsConst.fo_UserAccount_FName_TxtId).set(this.foRegistrants[1] + Integer.toString(this.startIndex));
		
		ie.textField(id, IProjectsConst.fo_UserAccount_LName_TxtId).set(this.foRegistrants[2] + Integer.toString(this.startIndex));
		
		ie.textField(id, IProjectsConst.fo_UserAccount_Email_TxtId).set(this.foRegistrants[0] + Integer.toString(this.startIndex) + this.foRegistrants[3]);
		
		ie.textField(id, IProjectsConst.fo_UserAccount_EmailConf_TxtId).set(this.foRegistrants[0] + Integer.toString(this.startIndex) + this.foRegistrants[3]);
		
		ie.selectList(id, IProjectsConst.fo_UserAccount_Language_TxtId).select("/English/");
		
		if(this.startIndex == 1)
		{
			ie.textField(id, IProjectsConst.fo_UserAccount_UName_TxtId).set(this.foRegistrants[4]);
		}
		else
		{
			ie.textField(id, IProjectsConst.fo_UserAccount_UName_TxtId).set(this.getFoUserName());
		}		
		
		ie.textField(id, IProjectsConst.fo_UserAccount_Pwd_TxtId).set(this.getFoPassword());
		
		ie.textField(id, IProjectsConst.fo_UserAccount_PwdConf_TxtId).set(this.getFoPassword());
		
		ie.textField(id, IProjectsConst.fo_UserAccount_Question_TxtId).set(IPreTestConst.Question);
		
		ie.textField(id, IProjectsConst.fo_UserAccount_Answer_TxtId).set(IPreTestConst.Answer);
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		arrList = GeneralUtil.checkForErrorMessages();
		
		if(arrList == null || arrList.isEmpty())
		{
			return true;
		}
		else
		{
			for(String string : arrList)
			{
				log.error("Validation Error In FO User Profile: " + string);
			}
		}		
		
		return false;
	}
	
	public boolean createProfiles() throws Exception {
		IE ie = IEUtil.getActiveIE();		
		retValue = false;
		
		for(int i=this.startIndex; i<=this.endIndex; i++)
		{
			ClicksUtil.clickLinks(IClicksConst.createProfileLnk);
			ie.textField(id, IProjectsConst.fo_UserAccount_FName_TxtId).set(this.foRegistrants[1] + Integer.toString(i));			
			ie.textField(id, IProjectsConst.fo_UserAccount_LName_TxtId).set(this.foRegistrants[2] + Integer.toString(i));			
			ie.textField(id, IProjectsConst.fo_UserAccount_Email_TxtId).set(this.foRegistrants[0] + Integer.toString(i) + this.foRegistrants[3]);			
			ie.textField(id, IProjectsConst.fo_UserAccount_EmailConf_TxtId).set(this.foRegistrants[0] + Integer.toString(i) + this.foRegistrants[3]);			
			ie.selectList(id, IProjectsConst.fo_UserAccount_Language_TxtId).select(IPreTestConst.Language);
			if(i == 1)
			{
				ie.textField(id, IProjectsConst.fo_UserAccount_UName_TxtId).set(this.foRegistrants[4]);
			}
			else
			{
				ie.textField(id, IProjectsConst.fo_UserAccount_UName_TxtId).set(this.foRegistrants[4] + Integer.toString(i));
			}			
			ie.textField(id, IProjectsConst.fo_UserAccount_Pwd_TxtId).set(IPreTestConst.Pwd);			
			ie.textField(id, IProjectsConst.fo_UserAccount_PwdConf_TxtId).set(IPreTestConst.Pwd);			
			ie.textField(id, IProjectsConst.fo_UserAccount_Question_TxtId).set(IPreTestConst.Question);			
			ie.textField(id, IProjectsConst.fo_UserAccount_Answer_TxtId).set(IPreTestConst.Answer);			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
			GeneralUtil.Logoff();
			GeneralUtil.logBack();
			GeneralUtil.logFOBack();
		}
		
		
		
		return retValue;
	}
	
	public boolean createFOApplicant()throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		retValue = false;
		
		if(this.startIndex == 1)
		{
			GeneralUtil.LoginAny(this.foRegistrants[4]);
		}
		else
		{
			GeneralUtil.LoginAny(this.foRegistrants[4] + Integer.toString(this.startIndex));
		}
		
		
		ClicksUtil.clickLinks(IClicksConst.openFOApplicantsListLnk);
		
		for(int i=this.startIndex; i<=this.endIndex; i++ )
		{
			if (GeneralUtil.isImageExistsBySrc(IClicksConst.newImg))
			{
				ClicksUtil.clickImage(IClicksConst.newImg);
				
				ie.selectList(id, IApplicantsConst.applicantTypeFO_DropDownId).selectList("Organization");
				
				ie.textField(name, IApplicantsConst.applicantNameFO_FldID).set(this.foOrgName + Integer.toString(i));
				
				ie.textField(name, IApplicantsConst.applicantNumberFO_FldID).set(foApplicants[4] + Double.toString(Math.random()).substring(2) + "-" + Integer.toString(i));
				
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				
				ClicksUtil.clickButtons(IClicksConst.backToListBtn);
				
				retValue = true;
				
			}	
		}
		
		return retValue;
	}
	
	public boolean doesFOUserExists() throws Exception
	{
		
		ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);
		
		
		return false;
	}
	
	public boolean associateRegistrantInPO(int beat) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickImage(IClicksConst.associateImg);
			
		ie.textField(id, IApplicantsConst.poRegistrantUserName).set(this.foRegistrants[4] + Integer.toString(beat));
				
		ie.textField(id, IApplicantsConst.poRegistrantEmailAdd).set(this.foRegistrants[0] + Integer.toString(beat) + this.foRegistrants[3]);
				
		ClicksUtil.clickButtons(IClicksConst.addRegistrantBtn);
				
		ClicksUtil.clickButtons(IClicksConst.backToListBtn);		
		
		return true;
	}
	
	
	//*********** Getter and Setter ***********************

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public String[] getFoApplicants() {
		return foApplicants;
	}

	public void setFoApplicants(String[] foApplicants) {
		this.foApplicants = foApplicants;
	}

	public String[] getFoRegistrants() {
		return foRegistrants;
	}

	public void setFoRegistrants(String[] foRegistrants) {
		this.foRegistrants = foRegistrants;
	}

	public int getFoUserBeat() {
		return foUserBeat;
	}

	public void setFoUserBeat(int foUserBeat) {
		this.foUserBeat = foUserBeat;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public String getFoOrgName() {
		return foOrgName;
	}

	public void setFoOrgName(String foOrgName) {
		this.foOrgName = foOrgName;
	}

	public String getFoRegistrantFullId() {
		return foRegistrantFullId;
	}

	public void setFoRegistrantFullId(String foRegistrantFullId) {
		this.foRegistrantFullId = foRegistrantFullId;
	}

	/**
	 * @return the foUserName
	 */
	public String getFoUserName() {
		return foUserName;
	}

	/**
	 * @param foUserName the foUserName to set
	 */
	public void setFoUserName(String foUserName) {
		this.foUserName = foUserName;
	}

	/**
	 * @return the foPassword
	 */
	public String getFoPassword() {
		return foPassword;
	}

	/**
	 * @param foPassword the foPassword to set
	 */
	public void setFoPassword(String foPassword) {
		this.foPassword = foPassword;
	}

	/**
	 * @return the foQuestion
	 */
	public String getFoQuestion() {
		return foQuestion;
	}

	/**
	 * @param foQuestion the foQuestion to set
	 */
	public void setFoQuestion(String foQuestion) {
		this.foQuestion = foQuestion;
	}

	/**
	 * @return the foFName
	 */
	public String getFoFName() {
		return foFName;
	}

	/**
	 * @param foFName the foFName to set
	 */
	public void setFoFName(String foFName) {
		this.foFName = foFName;
	}

	/**
	 * @return the foLName
	 */
	public String getFoLName() {
		return foLName;
	}

	/**
	 * @param foLName the foLName to set
	 */
	public void setFoLName(String foLName) {
		this.foLName = foLName;
	}

	/**
	 * @return the foEmail
	 */
	public String getFoEmail() {
		return foEmail;
	}

	/**
	 * @param foEmail the foEmail to set
	 */
	public void setFoEmail(String foEmail) {
		this.foEmail = foEmail;
	}

	/**
	 * @return the foConfEmail
	 */
	public String getFoConfEmail() {
		return foConfEmail;
	}

	/**
	 * @param foConfEmail the foConfEmail to set
	 */
	public void setFoConfEmail(String foConfEmail) {
		this.foConfEmail = foConfEmail;
	}

	/**
	 * @return the foConfPassword
	 */
	public String getFoConfPassword() {
		return foConfPassword;
	}

	/**
	 * @param foConfPassword the foConfPassword to set
	 */
	public void setFoConfPassword(String foConfPassword) {
		this.foConfPassword = foConfPassword;
	}

	/**
	 * @return the foAnswer
	 */
	public String getFoAnswer() {
		return foAnswer;
	}

	/**
	 * @param foAnswer the foAnswer to set
	 */
	public void setFoAnswer(String foAnswer) {
		this.foAnswer = foAnswer;
	}

	/**
	 * @return the foLocale
	 */
	public String getFoLocale() {
		return foLocale;
	}

	/**
	 * @param foLocale the foLocale to set
	 */
	public void setFoLocale(String foLocale) {
		this.foLocale = foLocale;
	}

	/**
	 * @return the applicantCategory
	 */
	public String getApplicantCategory() {
		return applicantCategory;
	}

	/**
	 * @param applicantCategory the applicantCategory to set
	 */
	public void setApplicantCategory(String applicantCategory) {
		this.applicantCategory = applicantCategory;
	}

	/**
	 * @return the applicantType
	 */
	public String getApplicantType() {
		return applicantType;
	}

	/**
	 * @param applicantType the applicantType to set
	 */
	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}
	
	

}
