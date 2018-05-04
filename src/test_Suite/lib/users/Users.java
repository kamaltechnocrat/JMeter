package test_Suite.lib.users;

import static watij.finders.SymbolFactory.*;

import java.util.Hashtable;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.ui.ITablesConst;
import test_Suite.constants.users.IUAPConst;
import test_Suite.constants.users.IUsersConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.dialogs.ConfirmDialog;
import watij.elements.Div;
import watij.elements.Table;
import watij.elements.Tables;
import watij.runtime.ie.IE;

public class Users {

	private static Log log = LogFactory.getLog(Users.class);

	private int UserBeat;
	private int UserIndex;
	private String[][] arrUser;
	private String Type;
	private String group;
	private String usrName;
	private String userFullId;
	private String primaryOrg;
	private String userFName;
	private String userLName;
	private String userStatus;
	private String userType;
	private String groupType;

	boolean retValue;

	// *********** Constractors **************************

	public Users(int UserBeat, int UserIndex, String Type) {
		this.setUserBeat(UserBeat);
		this.setUserIndex(UserIndex);
		this.setArrUser(IUsersConst.users[this.UserIndex]);
		this.setType(Type);
		this.primaryOrg = "G3";
		this.setUserType("Program Office Users");
		this.setUserStatus("Active Users");

		this.setUsrName(IUsersConst.users[this.UserIndex][0][0]);
		this.setUserFName(IUsersConst.users[this.UserIndex][0][1]);
		this.setUserLName(IUsersConst.users[this.UserIndex][0][2]);
		
		this.setGroup(IUsersConst.users[this.UserIndex][1][0]);

		this.setUserFullId(IUsersConst.users[this.UserIndex][0][2] + this.UserBeat
				+ ", " + IUsersConst.users[this.UserIndex][0][1]
				+ this.UserBeat + " (qa_"
				+ IUsersConst.users[this.UserIndex][0][0] + this.UserBeat
				+ IUsersConst.users[this.UserIndex][0][3] + ")");

	}

	public Users(int UserBeat, String[][] arrUser, String Type, String userType) {
		this.setUserBeat(UserBeat);
		this.setArrUser(arrUser);
		this.setType(Type);
		this.setPrimaryOrg("G3");
		this.setUserType(userType);
		this.setUserStatus("Active Users");		

		this.setUsrName(this.arrUser[0][0]);
		this.setUserFName(this.arrUser[0][1]);
		this.setUserLName(this.arrUser[0][2]);
		
		this.setGroup(this.arrUser[1][0]);

		this.setUserFullId(this.getUserLName().concat(String.valueOf(this.UserBeat)).concat(", ").concat(this.getUserFName()).concat(String.valueOf(this.UserBeat)).concat(" (qa_").concat(this.getUsrName()).concat(String.valueOf(this.UserBeat)).concat(arrUser[0][3].concat(")")));
	}

	public Users(int UserBeat, String[][] arrUser, String Type, String userType, String orgName, String userStatus, String grpType) {
		this.setUserBeat(UserBeat);
		this.setArrUser(arrUser);
		this.setType(Type);
		this.setPrimaryOrg(orgName);
		this.setUserType(userType);
		this.setUserStatus(userStatus);		

		this.setUsrName(this.arrUser[0][0]);
		this.setUserFName(this.arrUser[0][1]);
		this.setUserLName(this.arrUser[0][2]);
		
		this.setGroup(this.arrUser[1][0]);
		this.setGroupType(grpType);

		this.setUserFullId(this.getUserLName().concat(String.valueOf(this.UserBeat)).concat(", ").concat(this.getUserFName()).concat(String.valueOf(this.UserBeat)).concat(" (qa_").concat(this.getUsrName()).concat(String.valueOf(this.UserBeat)).concat(arrUser[0][3].concat(")")));
	}

	public Users(int UserBeat, String[][] arrUser,String userType) {
		this.setUserBeat(UserBeat);
		this.setArrUser(arrUser);
		this.setType("Users");
		this.setPrimaryOrg("G3");
		this.setUserType(userType);
		this.setUserStatus("Active Users");		

		this.setUsrName(this.arrUser[0][0].concat(String.valueOf(this.getUserBeat())));
		this.setUserFName(this.arrUser[0][1].concat(String.valueOf(this.getUserBeat())));
		this.setUserLName(this.arrUser[0][2].concat(String.valueOf(this.getUserBeat())));
		
		this.setGroup(this.arrUser[1][0]);

		this.setUserFullId(this.getUserLName().concat(", ").concat(this.getUserFName()).concat(" (qa_").concat(this.getUsrName()).concat(arrUser[0][3]).concat(")"));
	}

	public Users() {
	}

	// ------------------ End of Constractor ---------------

	// *************** Users Creation *********************

	public boolean createUser() throws Exception {
		IE ie = IEUtil.getActiveIE();

		retValue = false;

		ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);

		if (GeneralUtil.isImageExistsBySrc(IClicksConst.newImg)) {
			for (int i = 1; i <= this.UserBeat; i++) {

				// Check if User exists already
				if (!findInList(IFiltersConst.administration_UserName_Lbl,
						this.arrUser[0][0] + Integer.toString(i),
						ITablesConst.usersTableId)) {

					// Check for Add New User Icon
					if (GeneralUtil.isImageExistsBySrc(IClicksConst.newImg)) {
						ClicksUtil.clickImage(IClicksConst.newImg);
						ie.textField(name, "/login_name/").set(
								this.arrUser[0][0] + Integer.toString(i));
						
						if(this.getUserType() == "External Reviewers")
						{
							ie.selectList(id,"/user_type/").select("External Reviewer");
						}
						ie.selectList(name, "/language/").select(
								IPreTestConst.English);
						
						ie.selectList(name, "/salutation/").select(
								IPreTestConst.Salutation);
						
						ie.textField(name, "/first_name/").set(
								this.arrUser[0][1] + Integer.toString(i));
						
						ie.textField(name, "/last_name/").set(
								this.arrUser[0][2] + Integer.toString(i));
						
						ie.textField(name, "/email_address/").set("qa_"
								+ this.arrUser[0][0] + Integer.toString(i)
								+ this.arrUser[0][3]);
						
						ie.textField(name, "/emailConfirm/").set("qa_"
								+ this.arrUser[0][0] + Integer.toString(i)
								+ this.arrUser[0][3]);
						ie.textField(name, "/password/").set(IPreTestConst.Pwd);
						
						ie.textField(name, "/passwordConfirm/").set(
								IPreTestConst.Pwd);
						ie.textField(name, "/confirm_question/").set(
								IPreTestConst.Question);
						ie.textField(name, "/confirm_answer/").set(
								IPreTestConst.Answer);
						ie.textField(name, "/organization/").set(
								IPreTestConst.PO_Organization);
						ie.textField(name, "/position/").set(
								IPreTestConst.PO_Position);
						ie.textField(name, "/phone_numbe/").set(
								IPreTestConst.Phone);
						ie.textField(name, "/address1/").set(
								IPreTestConst.Address);
						ie.textField(name, "/city/").set(IPreTestConst.City);
						ie.textField(name, "/province_other/").set(
								IPreTestConst.Province);
						ie.selectList(name, "/country/").select(
								IPreTestConst.Country);
						ie.textField(name, "/postal_code/").set(
								IPreTestConst.PCode);
						ClicksUtil.clickButtons(IClicksConst.saveBtn);
						ClicksUtil
								.clickButtons(IClicksConst.userBackToUsersListBtn);
						retValue = true;
						log.debug(this.arrUser[0][0] + Integer.toString(i)
								+ ", Has being Created");
					} else {
						log.info("Could not find Add New User Icon!");
						retValue = false;
					}
				} else {
					log.debug(this.arrUser[0][0] + Integer.toString(i)
							+ ", Exists in List");
					retValue = true;
				}
			}
		} else {
			log.info("Could not find add new user icon!");
			retValue = false;
		}

		return retValue;

	}

	public boolean createSingleUser() throws Exception {
		IE ie = IEUtil.getActiveIE();
		retValue = false;
		ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);

		if (GeneralUtil.isImageExistsBySrc(IClicksConst.newImg)) {

			// Check if User exists already
			if (!findInList(IFiltersConst.administration_UserName_Lbl,
					this.arrUser[0][0], ITablesConst.usersTableId)) {

				// Check for Add New User Icon
				if (GeneralUtil.isImageExistsBySrc(IClicksConst.newImg)) {
					ClicksUtil.clickImage(IClicksConst.newImg);
					ie.textField(name, "/login_name/").set(this.arrUser[0][0]);
					ie.selectList(name, "/language/").select(
							IPreTestConst.English);
					ie.selectList(name, "/salutation/").select(
							IPreTestConst.Salutation);
					ie.textField(name, "/first_name/").set(this.arrUser[0][1]);
					ie.textField(name, "/last_name/").set(this.arrUser[0][2]);
					ie.textField(name, "/email_address/").set(
							this.arrUser[0][3]);
					ie.textField(name, "/emailConfirm/")
							.set(this.arrUser[0][3]);
					ie.textField(name, "/password/").set(IPreTestConst.Pwd);
					ie.textField(name, "/passwordConfirm/").set(
							IPreTestConst.Pwd);
					ie.textField(name, "/confirm_question/").set(
							IPreTestConst.Question);
					ie.textField(name, "/confirm_answer/").set(
							IPreTestConst.Answer);
					ie.textField(name, "/organization/").set(
							IPreTestConst.PO_Organization);
					ie.textField(name, "/position/").set(
							IPreTestConst.PO_Position);
					ie.textField(name, "/phone_numbe/")
							.set(IPreTestConst.Phone);
					ie.textField(name, "/address1/").set(IPreTestConst.Address);
					ie.textField(name, "/city/").set(IPreTestConst.City);
					ie.textField(name, "/province_other/").set(
							IPreTestConst.Province);
					ie.selectList(name, "/country/").select(
							IPreTestConst.Country);
					ie.textField(name, "/postal_code/")
							.set(IPreTestConst.PCode);
					ClicksUtil.clickButtons(IClicksConst.saveBtn);
					ClicksUtil.clickButtons(IClicksConst.userBackToUsersListBtn);
					retValue = true;
					log.debug(this.arrUser[0][0] + ", Has being Created");
				} else {
					log.info("Could not find Add New User Icon!");
					retValue = false;
				}
			} else {
				log.debug(this.arrUser[0][0] + ", Exists in List");
				retValue = true;
			}

		} else {
			log.info("Could not find add new user icon!");
			retValue = false;
		}

		return retValue;

	}

	// ---------------------- End of User Creation
	// -------------------------------

	// ****************** Overloaded Group Creations
	// *****************************

	public boolean createGroup(String group) throws Exception {
		IE ie = IEUtil.getActiveIE();
		retValue = false;

		this.Type = "Groups";
		ClicksUtil.clickLinks(IClicksConst.openGroupListLnk);
		ClicksUtil.clickImage(IClicksConst.newImg);
		ie.textField(id, IUsersConst.grp_Group_Name_Id).set(group);
		ie.selectList(id, IUsersConst.grp_Primary_Org_Id).select(this.primaryOrg);
		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		for (int i = 1; i <= this.UserBeat; i++) {
			ie.selectList(id, IUsersConst.grp_Avail_Users_Groups_Id).select(
					"/" + this.arrUser[0][0] + Integer.toString(i) + "/");
			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
		}
		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		return retValue;
	}

	public boolean createGroup() throws Exception {
		IE ie = IEUtil.getActiveIE();
		retValue = false;

		this.Type = "Groups";
		ClicksUtil.clickLinks(IClicksConst.openGroupListLnk);
		if (!findInList(IFiltersConst.administration_GroupName_Lbl, this.group,
				ITablesConst.groupsTableId)) {
			ClicksUtil.clickImage(IClicksConst.newImg);
			ie.textField(id, IUsersConst.grp_Group_Name_Id).set(this.group);
			if(this.getUserType() == "External Reviewers")
			{
				ie.selectList(id, IUsersConst.grpType_drpDown_id).select("External Reviewer");				
			}
			ie.selectList(id, IUsersConst.grp_Primary_Org_Id).select("/".concat(this.primaryOrg).concat("/"));
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			retValue = true;
			log.info(this.group + " Already Exists in List");
			retValue = true;
		}
		else
		{
			ClicksUtil.clickLinks(this.group);
			retValue = true;
		}
		return retValue;
	}

	// ----------------- End of Overloaded Group Creations --------------------

	// ***************** Overloaded Adding Group or Users to a Group
	// ****************

	public boolean addUsersToGroup() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		retValue = false;

		ClicksUtil.clickLinks(IClicksConst.openGroupListLnk);
		
		if (findInList(IFiltersConst.administration_GroupName_Lbl, this.group,ITablesConst.groupsTableId)) {
			
			ClicksUtil.clickLinks(this.group);
			
			ie.textField(id, IUsersConst.grp_Avail_Name_Search_Id).set(this.arrUser[0][0]);

			ClicksUtil.clickButtons(IClicksConst.searchBtn);

			GeneralUtil.takeANap(1.5);

			for (int i = 1; i <= this.UserBeat; i++) {

				if (GeneralUtil.findInM2MListById(IUsersConst.grp_Avail_Users_Groups_Id, this.arrUser[0][0]	+ Integer.toString(i))) {
					
					ie.selectList(name, IUsersConst.grp_Avail_Users_Groups_Id).select("/" + this.arrUser[0][0] + Integer.toString(i) + "/");
					
					ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn);

					GeneralUtil.takeANap(2.5);

					retValue = true;
					log.info("User has being associated to the group");
					
				} else {
					if (GeneralUtil.findInM2MListById(IUsersConst.grp_Selected_Users_Groups_Id, this.arrUser[0][0]	+ Integer.toString(i))) {
						
						retValue = true;
						log.info("User Already associated with the group");
						
					} else {
						retValue = false;
						log.info("User Not found in both M2Ms!");
						log.info("Check User status, may be Inactive!");
					}

				}

			}
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
		}

		return retValue;
	}

	public boolean setUserToGroup(String userIndex, String groupName, String userName, boolean addToGroup) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		retValue = false;
		int rowIndex = -1;
		
		ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);
		
		FiltersUtil.filterListByLabel(IFiltersConst.administration_UserName_Lbl, userName + userIndex, "Exact");
		
			
		rowIndex = TablesUtil.getRowIndex(ITablesConst.usersTableId, userName + userIndex);
		
		if (rowIndex > -1) {
			
			ie.div(id,"userListForm:listUsersTable").body(id,ITablesConst.usersTableId).row(rowIndex).image(src,IClicksConst.usersGroupsImg_src).click();
			
			rowIndex = -1;
			
			FiltersUtil.filterListByLabel(IFiltersConst.administration_GroupName_Lbl, groupName, "Exact");
			
			rowIndex = TablesUtil.getRowIndex(ITablesConst.groupsForUserTableId, groupName);
			
			if (rowIndex > -1) {
				
				if(ie.div(id,ITablesConst.groupsForUserDivId).body(id,ITablesConst.groupsForUserTableId).button(0).enabled()) 
				
				{
				
				
				
				if(addToGroup && ie.div(id,ITablesConst.groupsForUserDivId).body(id,ITablesConst.groupsForUserTableId).button(id,"userGroupForm:userGroupTable:0:notMemberOf").exists())
				{
					ie.div(id,ITablesConst.groupsForUserDivId).body(id,ITablesConst.groupsForUserTableId).button(0).click();
				}
				else if(ie.div(id,ITablesConst.groupsForUserDivId).body(id,ITablesConst.groupsForUserTableId).button(id,"userGroupForm:userGroupTable:0:memberOf").exists())
				{
					ie.div(id,ITablesConst.groupsForUserDivId).body(id,ITablesConst.groupsForUserTableId).button(0).click();
				}
				
				GeneralUtil.takeANap(1.000);
				
				ClicksUtil.clickButtonsById("userGroupForm:j_id_4k");
				
				return true;					
				
			}
				
			}			
			
		}
		
		return false;
	}

	public boolean removeUsersFromGroup(String userIndex, String groupName,
			String userName) throws Exception {
		IE ie = IEUtil.getActiveIE();
		retValue = false;

		ClicksUtil.clickLinks(IClicksConst.openGroupListLnk);

		if (findInList(IFiltersConst.administration_GroupName_Lbl, groupName,
				ITablesConst.groupsTableId)) {
			ClicksUtil.clickLinks(groupName);

			ie.textField(id, IUsersConst.grp_Avail_Name_Search_Id).set(
					this.arrUser[0][0]);

			ClicksUtil.clickButtons(IClicksConst.searchBtn);

			GeneralUtil.takeANap(1.0);

			if (GeneralUtil.findInM2MList(2, userName + userIndex)) {
				
				retValue = true;
				log.info("User had not being associated to the group");
				
			} else {
				if (GeneralUtil.findInM2MList(3, userName + userIndex)) {
					ie.selectList(name, "/selectedAppAccess/").select(
							"/" + userName + userIndex + "/");
					
					ClicksUtil.clickButtons(IClicksConst.m2mRemoveSelectedBtn);
					GeneralUtil.takeANap(0.5);
					ClicksUtil.clickButtons(IClicksConst.saveBtn);
					
					retValue = true;
					log.info("User has being associated with the group");
				} else {
					retValue = false;
					log.info("User Not found in both M2Ms!");
					log.info("Check User status, may be Inactive!");
				}

			}

			retValue = true;
		}

		return retValue;
	}

	public boolean addGroupsToGroup(String[] sourceGroupsList, String destGroup)
			throws Exception {
		IE ie = IEUtil.getActiveIE();
		retValue = false;

		ClicksUtil.clickLinks(IClicksConst.openGroupListLnk);
		
		if (findInList(IFiltersConst.administration_GroupName_Lbl, destGroup,
				ITablesConst.groupsTableId)) {
			
			ClicksUtil.clickLinks(destGroup);

			for (int i = 0; i < sourceGroupsList.length; i++) {

				ie.textField(id, IUsersConst.grp_Avail_Name_Search_Id).set(
						sourceGroupsList[i]);

				ClicksUtil.clickButtons(IClicksConst.searchBtn);

				GeneralUtil.takeANap(2.0);

				if (GeneralUtil.findInM2MList(1, "G: " + sourceGroupsList[i])) {
					ie.selectList(name, "/availableAppAccess/").select(
							
							"G: " + sourceGroupsList[i]);
					
					ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn);
					
					GeneralUtil.takeANap(0.5);
					
					ClicksUtil.clickButtons(IClicksConst.saveBtn);
					
					retValue = true;
					
					log.info("User has being associated to the group");
				} else {
					if (GeneralUtil.findInM2MList(2, "G: "
							+ sourceGroupsList[i])) {
						retValue = true;
						log.info("User Already associated with the group");
					} else {
						retValue = false;
						log.info("User Not found in both M2Ms!");
						log.info("Check User status, may be Inactive!");
					}

				}

			}
		}

		return retValue;
	}

	// ------------------ End of Adding group or User to A Group
	// ------------------

	// ****************** Setting up UAP to a Group or User
	
	
	//--------------------Unused--------------

//	public boolean setUAP() throws Exception {
//		IE ie = IEUtil.getActiveIE();
//		retValue = false;
//
//		ClicksUtil.clickLinks(IClicksConst.openGroupListLnk);
//		if (findInList(IFiltersConst.administration_GroupName_Lbl,
//				this.arrUser[1][0], ITablesConst.groupsTableId)) {
//			ClicksUtil.clickLinks(this.arrUser[1][0]);
////			ie.button(value, "Access Rights").click();
//			ClicksUtil.clickLinks("Access Rights");
//			for (int i = 0; i < this.arrUser[1].length - 1; i++) {
//				ie.table(id, ITablesConst.groupAccessRightsTableId)
//						.radio(
//								name,
//								ITablesConst.groupAccessRightsRadioName
//										+ this.arrUser[1][i + 1]
//										+ ":radioButtons", "2").set();
//			}
//			ClicksUtil.clickButtons(IClicksConst.saveBtn);
//			retValue = true;
//		}
//		return retValue;
//	}
//
//	public boolean setReadUAP() throws Exception {
//		IE ie = IEUtil.getActiveIE();
//		retValue = false;
//
//		ClicksUtil.clickLinks(IClicksConst.openGroupListLnk);
//		if (findInList(IFiltersConst.administration_GroupName_Lbl,
//				this.arrUser[1][0], ITablesConst.groupsTableId)) {
//			ClicksUtil.clickLinks(this.arrUser[1][0]);
////			ie.button(value, "Access Rights").click();
//			ClicksUtil.clickLinks("Access Rights");
//			for (int i = 0; i < this.arrUser[1].length - 1; i++) {
//				ie.table(id, ITablesConst.groupAccessRightsTableId)
//						.radio(
//								name,
//								ITablesConst.groupAccessRightsRadioName
//										+ this.arrUser[1][i + 1]
//										+ ":radioButtons", "1").set();
//			}
//			ClicksUtil.clickButtons(IClicksConst.saveBtn);
//			retValue = true;
//		}
//		return retValue;
//	}
	//---------------------------------------------

	private boolean findInList(String fldlabel, String name, String tableId)
			throws Exception {

		retValue = false;

		Hashtable<String, String> filterValues = new Hashtable<String, String>();

		filterValues.put(fldlabel, name);

		log.info(this.Type);
			Hashtable<String, String> modeValues = new Hashtable<String, String>();
			
			if(this.Type == "Groups")
			{
				
				if(this.getGroupType() != null)
				{
					modeValues.put(IFiltersConst.administration_GroupType_Lbl, this.getGroupType());
				}
				
			}
			else
			{

				modeValues.put(IFiltersConst.administration_StatusType_Lbl,
						IFiltersConst.users_AllUsers_StatusView);
				
				modeValues.put(IFiltersConst.administration_UserType_Lbl, this.getUserType());
				
			}

			FiltersUtil.filterListByLabel(filterValues, modeValues, false);

		if (TablesUtil.findInTable(tableId, name))
			retValue = true;

		return retValue;

	}

	public void setGroupUAPRights(Iterator<Object> iterList, boolean[] uapRights)
			throws Exception {
		IE ie = IEUtil.getActiveIE();

		Tables tables;
		Table table;

		String uapType;

		ClicksUtil.clickLinks(IClicksConst.openGroupListLnk);

		if (findInList(IFiltersConst.administration_GroupName_Lbl,
				this.arrUser[1][0], ITablesConst.groupsTableId)) {

			ClicksUtil.clickLinks(this.arrUser[1][0]);

//			ie.button(value, "Access Rights").click();
			ClicksUtil.clickLinks("Access Rights");
			
			tables = ie.div(id, IUAPConst.groupAccessRightsDivId).tables();

			while (iterList.hasNext()) {
				uapType = iterList.next().toString();
				log.info(uapType);

				for (int i = 0; i < tables.length(); i++) {
					table = tables.table(i);

					if (table.innerText().matches(uapType)) {
						for (int x = 0; x < uapRights.length; x++) {
							table.checkbox(x).set(uapRights[x]);
							i += tables.length() - 1;
						}

					}
				}

			}

			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			retValue = true;
		}
	}

	// Some Utilities

	public void taggleUserStatusInList(String userIndx, boolean userStat)
			throws Exception {

		ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);

		Hashtable<String, String> filterValues = new Hashtable<String, String>();
		Hashtable<String, String> modeValues = new Hashtable<String, String>();
		
		filterValues.put(IFiltersConst.administration_UserName_Lbl,
				this.arrUser[0][0]);
		modeValues.put(IFiltersConst.administration_StatusType_Lbl, "All Users");
		
		FiltersUtil.filterListByLabel(filterValues, modeValues, false);

		int rowIndex = TablesUtil.getRowIndex(ITablesConst.usersTableId,
				this.arrUser[0][0] + userIndx);

		Thread dialogClicker = new Thread() {
			@Override
			public void run() {
				try {
					IE ie2 = IEUtil.getActiveIE();
					ConfirmDialog dialog1 = ie2.confirmDialog();
					while (dialog1 == null) {
						log.debug("can't yet get handle on confirm dialog1");
						GeneralUtil.takeANap(0.250);
					}

					dialog1.ok();
					log.debug("got confirm Dialog1 and clicked OK.");					
					//ie2 = null;
				} catch (Exception e) {
					throw new RuntimeException("Unexpected exception", e);
				} 
			}
		};

		dialogClicker.start();
		log.debug("started dialog clicker thread");

		Div tDiv = TablesUtil.tableDiv();
		
		if (userStat) {
			tDiv.body(id, ITablesConst.usersTableId).row(rowIndex).checkbox(0)
					.set();
			
			//GeneralUtil.takeANap(1.0);
		} else {
			tDiv.body(id, ITablesConst.usersTableId).row(rowIndex).checkbox(0)
					.clear();
			
			//GeneralUtil.takeANap(1.0);
		}
		
		GeneralUtil.takeANap(1.000);
		
		dialogClicker = null;
		//ie = IEUtil.getActiveIE();
		//GeneralUtil.takeANap(1.0);
		
		return;

	}

	public String getFullId(String usrIndx) throws Exception {
		String fullId;

		fullId = this.arrUser[0][2] + usrIndx + ", " + this.arrUser[0][1]
				+ usrIndx + " (qa_" + this.arrUser[0][0] + usrIndx
				+ this.arrUser[0][3] + ")";
		return fullId;
	}

	public boolean taggleUserStatusInAccnt(String userIndx, boolean userStat)
			throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);

		retValue = false;

		Hashtable<String, String> filterValues = new Hashtable<String, String>();
		Hashtable<String, String> modeValues = new Hashtable<String, String>();

		filterValues.put(IFiltersConst.administration_UserName_Lbl,
				this.arrUser[0][0]);
		modeValues.put(IFiltersConst.administration_StatusType_Lbl, "All Users");
		
		FiltersUtil.filterListByLabel(filterValues, modeValues, false);

		if (TablesUtil.findInTable(ITablesConst.usersTableId,
				this.arrUser[0][0] + userIndx)) {
			
			log.debug(this.getFullId(userIndx));
			
			ClicksUtil.clickLinks(this.getFullId(userIndx));			
//			ClicksUtil.clickButtons(IClicksConst.openUserAccountBtn);
			ClicksUtil.clickLinks("User Account");
			
			if ((userStat) && ie.checkbox(0).enabled()) {
				ie.checkbox(0).set();
				retValue = true;
			}

			else if (ie.checkbox(0).enabled()) {
				ie.checkbox(0).clear();
				retValue = true;
			}

			ClicksUtil.clickButtons(IClicksConst.saveBtn);
		}

		return retValue;

	}

	public void setGroupUAP() throws Exception {

	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getUserBeat() {
		return UserBeat;
	}

	public void setUserBeat(int userBeat) {
		if(userBeat != 0)
		{
			this.UserBeat = userBeat;
		}
		
	}

	public int getUserIndex() {
		return UserIndex;
	}

	public void setUserIndex(int userIndex) {
		UserIndex = userIndex;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getUserFullId() {
		return userFullId;
	}

	public void setUserFullId(String userFullId) {
		this.userFullId = userFullId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return Type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		Type = type;
	}

	/**
	 * @return the arrUser
	 */
	public String[][] getArrUser() {
		return arrUser;
	}

	/**
	 * @param arrUser
	 *            the arrUser to set
	 */
	public void setArrUser(String[][] arrUser) {
		this.arrUser = arrUser;
	}

	/**
	 * @return the primaryOrg
	 */
	public String getPrimaryOrg() {
		return primaryOrg;
	}

	/**
	 * @param primaryOrg
	 *            the primaryOrg to set
	 */
	public void setPrimaryOrg(String primaryOrg) {
		this.primaryOrg = primaryOrg;
	}

	/**
	 * @return the userFName
	 */
	public String getUserFName() {
		return userFName;
	}

	/**
	 * @param userFName the userFName to set
	 */
	public void setUserFName(String userFName) {
		this.userFName = userFName;
	}

	/**
	 * @return the userLName
	 */
	public String getUserLName() {
		return userLName;
	}

	/**
	 * @param userLName the userLName to set
	 */
	public void setUserLName(String userLName) {
		this.userLName = userLName;
	}

	/**
	 * @return the userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the groupType
	 */
	public String getGroupType() {
		return groupType;
	}

	/**
	 * @param groupType the groupType to set
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

}
