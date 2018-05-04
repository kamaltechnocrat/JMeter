/**
 *  This Utilities will have a generic methods to help with various tasks
 */
package test_Suite.utils.users;

import java.util.*;

import org.apache.commons.logging.*;
import org.testng.Assert;

import static watij.finders.SymbolFactory.*;
import watij.elements.*;
import watij.runtime.ie.IE;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.*;
import test_Suite.constants.users.*;
import test_Suite.lib.users.Users;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.*;

/**
 * @author mshakshouki
 * 
 */
public class UsersAndGroupsUtil {

	private static Log log = LogFactory.getLog(UsersAndGroupsUtil.class);

	private static boolean retValue;

	static int rowIndex;
	
	static int usersCount;

	static Hashtable<Integer, String> filterValues;

	static Hashtable<Integer, String> modesValues;

	static Tables tables;

	static Table table;
	
	static Div pagenatorTable;

	static Divs divs;
	
	static Links links;
	
	public static String getTheProperLicenseKey(int usersCount, Users user) throws Exception {
		
		String licenseKey = "";
		
		if(usersCount > 60 && usersCount <= 80)
		{
			user.setUserBeat(80 - usersCount);
			
			return IConfigConst.licenseKey_80_Users;
		}
		else if(usersCount > 80 && usersCount <= 90)
		{
			user.setUserBeat(90 - usersCount);
			
			return IConfigConst.licenseKey_90_Users;
		}
		else if(usersCount > 90 && usersCount <= 95)
		{
			user.setUserBeat(95 - usersCount);
			
			return IConfigConst.licenseKey_95_Users;
		}
		else if(usersCount > 95 && usersCount <= 100)
		{
			user.setUserBeat(100 - usersCount);
			
			return IConfigConst.licenseKey_100_Users;
		}
		else if(usersCount > 100 && usersCount <= 150)
		{
			user.setUserBeat(150 - usersCount);
			
			return IConfigConst.licenseKey_150_Users;
		}
		else if(usersCount > 150 && usersCount <= 200)
		{
			user.setUserBeat(200 - usersCount);
			
			return IConfigConst.licenseKey_200_Users;
		}
		
		return licenseKey;
	}
	
	public static String initializePOFullUserId(String[][] arrUser, String userBeat) throws Exception {
		
		return "PO: " + arrUser[0][2] + userBeat.toString() + ", " + arrUser[0][1]+ userBeat.toString() + " (qa_" + arrUser[0][0]+ userBeat.toString() + arrUser[0][3] + ")";		
	}
	
	public static int getHowManyPOUsers(String usersStatus) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		usersCount = 0;
		
		Integer pageNumber = 1;		
		
		ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);
		
		FiltersUtil.filterListByLabel(IFiltersConst.administration_StatusType_Lbl, "", usersStatus);
		
		pagenatorTable = ie.div(id, ITablesConst.paginationDivId);
		
		if(pagenatorTable.span(text, pageNumber.toString()).exists())
		{
			pagenatorTable.span(text, pageNumber.toString()).click();
		}
		
		usersCount += TablesUtil.howManyEntriesInTable(ITablesConst.usersTableId);
		
		Spans spans = pagenatorTable.spans();
		
		if(spans != null && spans.length() > 0)
		{			
			for (Span span : spans) {
				
				span.click();
				
				usersCount += TablesUtil.howManyEntriesInTable(ITablesConst.usersTableId);
				
			}
		}		
		
		return usersCount;
		
	}

	public boolean findInNewM2M(String nameToFind) throws Exception {

		IE ie = IEUtil.getActiveIE();

		retValue = false;

		ie.textField(id, "").set(nameToFind);

		return retValue;
	}

	public static boolean createUsersOnly(Users users, ArrayList<String[]> iterUaps,
			boolean[] rights) throws Exception {

		IE ie = IEUtil.getActiveIE();

		String emailAdd;

		ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);

		for (int i = 1; i <= users.getUserBeat(); i++) {
			Assert
					.assertTrue(
							GeneralUtil.isImageExistsBySrc(IClicksConst.newImg),
							"Fail: could not find Add Icon, login user may not have proper UAPs or reached Active Users Limits");

			emailAdd = "qa_" + users.getArrUser()[0][0] + Integer.toString(i)
					+ users.getArrUser()[0][3];

			if (!doesObjectExistsInList(IFiltersConst.administration_UserName_Lbl,
					users.getArrUser()[0][0] + Integer.toString(i),
					ITablesConst.usersTableId, "Users")) {
				ClicksUtil.clickImage(IClicksConst.newImg);

				ie.textField(name, "/login_name/").set(
						users.getArrUser()[0][0] + Integer.toString(i));

				ie.selectList(name, "/language/").select(IPreTestConst.English);

				ie.selectList(name, "/salutation/").select(
						IPreTestConst.Salutation);

				ie.textField(name, "/first_name/").set(
						users.getArrUser()[0][1] + Integer.toString(i));

				ie.textField(name, "/last_name/").set(
						users.getArrUser()[0][2] + Integer.toString(i));

				ie.textField(name, "/email_address/").set(emailAdd);

				ie.textField(name, "/emailConfirm/").set(emailAdd);

				ie.textField(name, "/password/").set(IPreTestConst.Pwd);

				ie.textField(name, "/passwordConfirm/").set(IPreTestConst.Pwd);

				ie.textField(name, "/confirm_question/").set(
						IPreTestConst.Question);

				ie.textField(name, "/confirm_answer/")
						.set(IPreTestConst.Answer);

				ie.textField(name, "/organization/").set(
						IPreTestConst.PO_Organization);

				ie.textField(name, "/position/").set(IPreTestConst.PO_Position);

				ie.textField(name, "/phone_numbe/").set(IPreTestConst.Phone);

				ie.textField(name, "/address1/").set(IPreTestConst.Address);

				ie.textField(name, "/city/").set(IPreTestConst.City);

				ie.textField(name, "/province_other/").set(
						IPreTestConst.Province);

				ie.selectList(name, "/country/").select(IPreTestConst.Country);

				ie.textField(name, "/postal_code/").set(IPreTestConst.PCode);

				ClicksUtil.clickButtons(IClicksConst.saveBtn);

				ClicksUtil.clickButtons(IClicksConst.userBackToUsersListBtn);

			}

		}
		return true;
		
	}
	
	public static boolean createAssociateApplicantRole(String grpName) throws Exception {
		
		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks(IClicksConst.openGroupListLnk);
		
		ClicksUtil.clickImage(IClicksConst.newImg);
		
		if(!GeneralUtil.selectFullStringInDropdownList(IUsersConst.grpType_drpDown_id, "Front Office"))
		{
			log.error("Could not select Group Type Front Office!");
			
			return false;
		}
		
		GeneralUtil.takeANap(0.5);
		
		if(!ie.checkbox(id,IUsersConst.grpAssocApp_CheckboxId).exists())
		{
			log.error("Could not find Associate Applicants Checkbox");
			return false;
		}
		
		ie.checkbox(id,IUsersConst.grpAssocApp_CheckboxId).set();
		
		ie.textField(id,IUsersConst.grp_Group_Name_Id).set(grpName);
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		return true;
	}
	
	

	public static boolean createGroupOnly(Users users, ArrayList<String[]> iterUaps,
			boolean[] rights) throws Exception {

		IE ie = IEUtil.getActiveIE();

		users.setType("Groups");

		ClicksUtil.clickLinks(IClicksConst.openGroupListLnk);

		if (!doesObjectExistsInList(IFiltersConst.administration_GroupName_Lbl,
				users.getGroup(), ITablesConst.groupsTableId, "Groups")) {
			ClicksUtil.clickImage(IClicksConst.newImg);

			ie.textField(name, IUsersConst.grp_Group_Name_Id).set(
					users.getGroup());

			ie.selectList(id, IUsersConst.grp_Primary_Org_Id).select("G3"); // (users.getPrimaryOrg());

			ClicksUtil.clickButtons(IClicksConst.saveBtn);

		} else {
			ClicksUtil.clickLinks(users.getGroup());
		}		

		// Add Access Rights
		
		addAccessRightsUAPNew(iterUaps, rights);

		retValue = true;

		return retValue;
		
	}
	
	public static boolean createEmptyGroupOnly(String groupName) throws Exception {
		
		try{

			IE ie = IEUtil.getActiveIE();

			Assert.assertTrue(ClicksUtil.clickLinks(IClicksConst.openGroupListLnk), "Fail: Couldn't open Groups page!");

			Assert.assertTrue(ClicksUtil.clickImage(IClicksConst.newImg), "Fail: Couldn't click New img!");

			ie.textField(name, IUsersConst.grp_Group_Name_Id).set(groupName);

			ie.selectList(id, IUsersConst.grpType_drpDown_id).select(IUsersConst.grp_FOType_dd);

			Assert.assertTrue(ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn), "Fail: Couldn't click Save and Back button!");

		}catch (Exception e) {

			log.error("Unexpected Exception: " + e);
			return false;
		}

		return true;
	}

	
//	public static boolean checkUAPs() throws Exception {
//
//		try{
//			
//			IE ie = IEUtil.getActiveIE();
//			
////			spans = ie.span(id, IUAPConst.grp_fopp_SpanId).spans();
////			
////			for (Span span : spans){
////				
////				tables = span.tables();
//			
//			tables = ie.tables("class", "accessNodeRights");
//				
//				for (Table table : tables) {
//					
//				}
////			}
//
//		}catch (Exception e) {
//
//			log.error("Unexpected Exception: " + e);
//			return false;
//		}
//
//		return true;
//	}
	
	public static boolean createUsers(Users users, ArrayList<String[]> iterUaps,
			boolean[] rights) throws Exception {

		IE ie = IEUtil.getActiveIE();

		String emailAdd;

		ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);

		for (int i = 1; i <= users.getUserBeat(); i++) {
			Assert
					.assertTrue(
							GeneralUtil.isImageExistsBySrc(IClicksConst.newImg),
							"Fail: could not find Add Icon, login user may not have proper UAPs or reached Active Users Limits");

			emailAdd = "qa_" + users.getArrUser()[0][0] + Integer.toString(i)
					+ users.getArrUser()[0][3];

			if (!doesObjectExistsInList(IFiltersConst.administration_UserName_Lbl,
					users.getArrUser()[0][0] + Integer.toString(i),
					ITablesConst.usersTableId, "Users")) {
				ClicksUtil.clickImage(IClicksConst.newImg);

				ie.textField(name, "/login_name/").set(
						users.getArrUser()[0][0] + Integer.toString(i));

				ie.selectList(name, "/language/").select(IPreTestConst.English);

				ie.selectList(name, "/salutation/").select(
						IPreTestConst.Salutation);

				ie.textField(name, "/first_name/").set(
						users.getArrUser()[0][1] + Integer.toString(i));

				ie.textField(name, "/last_name/").set(
						users.getArrUser()[0][2] + Integer.toString(i));

				ie.textField(name, "/email_address/").set(emailAdd);

				ie.textField(name, "/emailConfirm/").set(emailAdd);

				ie.textField(name, "/password/").set(IPreTestConst.Pwd);

				ie.textField(name, "/passwordConfirm/").set(IPreTestConst.Pwd);

				ie.textField(name, "/confirm_question/").set(
						IPreTestConst.Question);

				ie.textField(name, "/confirm_answer/")
						.set(IPreTestConst.Answer);

				ie.textField(name, "/organization/").set(
						IPreTestConst.PO_Organization);

				ie.textField(name, "/position/").set(IPreTestConst.PO_Position);

				ie.textField(name, "/phone_numbe/").set(IPreTestConst.Phone);

				ie.textField(name, "/address1/").set(IPreTestConst.Address);

				ie.textField(name, "/city/").set(IPreTestConst.City);

				ie.textField(name, "/province_other/").set(
						IPreTestConst.Province);

				ie.selectList(name, "/country/").select(IPreTestConst.Country);

				ie.textField(name, "/postal_code/").set(IPreTestConst.PCode);

				ClicksUtil.clickButtons(IClicksConst.saveBtn);

				ClicksUtil.clickButtons(IClicksConst.userBackToUsersListBtn);

			}

		}

		users.setType("Groups");

		ClicksUtil.clickLinks(IClicksConst.openGroupListLnk);

		if (!doesObjectExistsInList(IFiltersConst.administration_GroupName_Lbl,
				users.getGroup(), ITablesConst.groupsTableId, "Groups")) {
			ClicksUtil.clickImage(IClicksConst.newImg);

			ie.textField(name, "/editGroupTab:access_name/").set(
					users.getGroup());

			ie.selectList(0).select("G3"); // (users.getPrimaryOrg());

			ClicksUtil.clickButtons(IClicksConst.saveBtn);

		} else {
			ClicksUtil.clickLinks(users.getGroup());
		}

		// Add users to Group

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

		ie.textField(id, IUsersConst.grp_Avail_Name_Search_Id).set(
				users.getArrUser()[0][0]);

		ClicksUtil.clickButtons(IClicksConst.searchBtn);

		GeneralUtil.takeANap(1.0);

		if (ie.selectList(name, IUsersConst.grp_Avail_Users_Groups_Id)
				.getAllContents().size() == users.getUserBeat()) {
			ClicksUtil.clickButtons(IClicksConst.m2mAddAllBtn);
		} else {
			for (int i = 1; i <= users.getUserBeat(); i++) {

				if (GeneralUtil.findInM2MList(1, users.getArrUser()[0][0]
						+ Integer.toString(i))) {
					ie.selectList(name, IUsersConst.grp_Avail_Users_Groups_Id)
							.select(
									"/" + users.getArrUser()[0][0]
											+ Integer.toString(i) + "/");

					ClicksUtil.clickButtons(IClicksConst.m2mAddSelectedBtn);

					GeneralUtil.takeANap(0.5);

					retValue = true;
					log.info("User has being associated to the group");
				} else {
					if (GeneralUtil.findInM2MList(2, users.getArrUser()[0][0]
							+ Integer.toString(i))) {
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

		// Add Access Rights
		
		addAccessRightsUAPNew(iterUaps, rights);

		retValue = true;

		return retValue;
	}
	
	public static void addAccessRightsUAPTopLevel(String topLevelUAP,	boolean[] rights) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.userAccessRightsLnk);
		
		Div tDiv = TablesUtil.tableDiv();

		TableRows rows = tDiv.body(id,IUAPConst.groupAccessRightsTopLevelTableId).rows();
		
		TableRow topLevelRow = null;
		
		
		
		for (TableRow row : rows) {
			
			if(row.innerText().startsWith(topLevelUAP))
			{
				topLevelRow = row;
				break;
			}			
		}
		
		if(null != topLevelRow)
		{
			int idx = 0;
			for (boolean bool : rights) 
			{
				idx++;
				if(bool)
				{
					if(topLevelRow.cell(idx).link(0).image(0).src().endsWith("off.gif"))
					{
						topLevelRow.cell(idx).link(0).click();
					}
				}
				else
				{
					if(topLevelRow.cell(idx).link(0).image(0).src().endsWith("on.gif"))
					{
						topLevelRow.cell(idx).link(0).click();
					}
				}								
			}			
		}		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
	}

//UNUSED:	
//	public static void addAccessRightsUAP(ArrayList<String[]> iterUaps,
//			boolean[] rights) throws Exception {
//
//		IE ie = IEUtil.getActiveIE();
//		
//		int chkIndex;
//
//		ClicksUtil.clickLinks(IClicksConst.userAccessRightsLnk);
//		
//		ClicksUtil.clickLinkBySpanId(IClicksConst.uap_GroupSpan_Id, iterUaps
//				.get(0)[0]);
//
//		divs = ie.div(id, IUAPConst.groupAccessRightsDivId).divs();
//		
//		tables = ie.div(id, IUAPConst.groupAccessRightsDivId).tables();
//		
//		if(iterUaps.get(0).length > 1)
//		{
//			for (Table table : tables) {
//
//				for (String string : iterUaps.get(0)) {
//
//					chkIndex = 0;
//
//					if (table.innerText().contains(string)) {
//						
//						for (Checkbox checkBox : table.checkboxes()) {
//
//							checkBox.set(rights[chkIndex]);
//							chkIndex += 1;
//						}
//					}
//				}
//			}
//		}
//		for (Div div : divs) {
//			
//			for (int x = 1; x < iterUaps.size(); x++) {			
//
//				if (div.innerText().startsWith(iterUaps.get(x)[0])) {
//					
//					tables = div.tables();
//
//					for (Table table : tables) {
//
//						for (String string : iterUaps.get(x)) {
//
//							chkIndex = 0;
//
//							if (table.innerText().contains(string)) {
//								for (Checkbox checkBox : table.checkboxes()) {
//
//									checkBox.set(rights[chkIndex]);
//									chkIndex += 1;
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		ClicksUtil.clickButtons(IClicksConst.saveBtn);
//
//	}
	
	/**
	 * @param iterUaps
	 * @param rights
	 * @throws Exception
	 */
	public static void addAccessRightsUAPNew(ArrayList<String[]> iterUaps,
			boolean[] rights) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickLinks(IClicksConst.userAccessRightsLnk);
		
		ClicksUtil.clickLinkByTableDiv(IClicksConst.uap_GroupTBody_Id, iterUaps
				.get(0)[0]);
		
		TableRows rows = ie.body(id, "groupTabbedForm:manageGroupTabbedPane:j_id_4w_data").rows();
		
		for (int i = 1; i < iterUaps.size(); i++) {
			
			log.warn(iterUaps.size());
			
			for (TableRow row : rows) {
				
				log.warn(row.innerText().trim());
				
				for (String string : iterUaps.get(i)) {
					
					log.warn(string);
					
					if (row.innerText().trim().equals(string))
					{
						
						addUAPToAccessTable(row,rights);
						
						break;
						
					}					
				}				
			}			
		}
		
		
//		if(iterUaps.get(0)[0].equals(IUAPConst.administerG3UAP_1st))
//		{
//			for (int i = 1; i < iterUaps.size(); i++) {
//				
//				String spanId = getSpanTables(iterUaps.get(i));
//				
//				if(null != spanId)
//				{		
//					Tables tables = ie.span(id,spanId).tables();
//					setTheRightTable(tables,iterUaps.get(i),rights);				
//				}
//			}
//		}
//		else
//		{		
//			
//			//set all spans and Tables from the Access Right Span
//			Divs divs = ie.div(id, IUAPConst.groupAccessRightsDivId).divs();			
//			
//			//Iterate through uap list 
//			//start by getting partial of the span id
//			
//			for (int i = 1; i < iterUaps.size(); i++) {	
//				
//				
//				Div div = divs.div(id,iterUaps.get(i)[0]);
//				
//				if(!div.exists())
//				{
//					log.error("The Span for The UAP does not exist!!, check the Id of the span");
//					
//					Assert.fail();
//				}
//				
//				Tables tables = div.tables();
//				
//				setTheRightTable(tables,iterUaps.get(i),rights);
//			}
//			
//		}					

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
	
	}
	
	public static String getSpanTables(String[] arrStr) throws Exception {	
		
		IE ie = IEUtil.getActiveIE();
		
		divs = ie.div(id, IUAPConst.groupAccessRightsDivId).divs();		
		
		String strToFind =arrStr[1];
		
		if("Basic Details".equals(strToFind))
		{
			strToFind = arrStr[2];
		}		
		
		for (Div div : divs) {
			
			log.debug(div.id());
			
			if(!div.id().equals(IUAPConst.groupAccessRightsDivId))
			{					
				
				if(div.innerText().contains(strToFind))
				{
					log.debug(strToFind);
					return div.id();
				}
				
			}
			
		}
		
		
		return null;
	}
	
	/**
	 * @param tables
	 * @param arrStr
	 * @return
	 * @throws Exception
	 */
	static void setTheRightTable(TableRows rows, String[] arrStr, boolean[] crud) throws Exception {		
		
		for (TableRow row : rows) {
			
			log.debug(row.innerText());

			for (String string : arrStr) {
				
				log.debug(string);

				if (row.innerText().trim().startsWith(string)) {
					
					log.debug(string);
					
					addUAPToAccessTable(row,crud);
					
					break;
				}

			}
		}
		
		return;
	}
	
	/**
	 * @param table
	 * @param crud
	 * @throws Exception
	 */
	static void addUAPToAccessTable(TableRow table,boolean[] crud) throws Exception {
		int chkIndex = 0;
		
		for (Checkbox checkBox : table.checkboxes()) {

			checkBox.set(crud[chkIndex]);
			chkIndex++;
		}
	}

	public static void updateUserEmail() throws Exception {

		IE ie = IEUtil.getActiveIE();

		String newEmail;
		String[] email;
		int usersCount;

		Integer pageCount = 1;

		ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);

		ClicksUtil.clickLinks(IClicksConst.showFiltersLnk);

		ClicksUtil.clickLinks(IClicksConst.clearFiltersLnk);
		
		Div tDiv = TablesUtil.tableDiv();

		do {

			if (ie.link(text, pageCount.toString()).exists()) {
				ie.link(text, pageCount.toString()).click();
			}
			pageCount += 1;

			usersCount = TablesUtil
					.howManyEntriesInTable(ITablesConst.usersTableId);

			for (int i = 0; i < usersCount; i++) {
				tDiv.body(id, ITablesConst.usersTableId).row(i).cell(IClicksConst.userNameColumn).link(0).click();

				email = ie.textField(id, "/email_address/").getContents()
						.split("@");

				email[0] = "qa_" + email[0];
				email[1] = "@grant-nds-06.grantium.com";

				newEmail = email[0] + email[1];

				ie.textField(id, "/email_address/").set(newEmail);

				ie.textField(id, "/emailConfirm/").set(newEmail);

				ClicksUtil.clickButtons(IClicksConst.saveBtn);

				ClicksUtil.clickButtons(IClicksConst.userBackToUsersListBtn);

			}

		} while (pagenatorTable.span(text, pageCount.toString()).exists());

	}
	
	public static boolean findGroupInList(String grpName, String grpType, String orgName) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openGroupListLnk);
		
		if(!filterGroupList(grpName, grpType, orgName))
		{
			log.error("Could not filter Group List!");
			return false;
		}
		
		return TablesUtil.findInTable(ITablesConst.groupsTableId, grpName);	
		
	}
	
	public static boolean filterGroupList(String grpName, String grpType, String orgName) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();
		
		
		hashTable.put(IFiltersConst.administration_GroupName_Lbl, grpName);
		
		hashTableDropdown.put(IFiltersConst.administration_GroupName_Lbl, IFiltersConst.startsWith);
		hashTableDropdown.put(IFiltersConst.administration_Organization_Lbl, orgName);
		hashTableDropdown.put(IFiltersConst.administration_GroupType_Lbl, grpType);
		
		return FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);	
		
	}
	
	public static void filterUserList(Users user) throws Exception {

		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> hashTableDropdown = new Hashtable<String, String>();
		
		hashTable.put(IFiltersConst.administration_FirstName_Lbl, user.getUserFName());
		hashTable.put(IFiltersConst.administration_LastName_Lbl, user.getUserLName());
		hashTable.put(IFiltersConst.administration_UserName_Lbl, user.getUsrName());
		
		hashTableDropdown.put(IFiltersConst.administration_UserType_Lbl, user.getUserType());
		hashTableDropdown.put(IFiltersConst.administration_StatusType_Lbl, user.getUserStatus());
		hashTableDropdown.put(IFiltersConst.administration_Organization_Lbl, user.getPrimaryOrg());
		
		FiltersUtil.filterListByLabel(hashTable, hashTableDropdown, false);
		
	}
	
	public static boolean openUserProfile(Users user) throws Exception {

		int rowIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);
		
		filterUserList(user);
		
		rowIndex = TablesUtil.getRowIndex(ITablesConst.usersTableId, user.getUsrName());
		
		Div tDiv = TablesUtil.tableDiv();

		if(rowIndex > -1)
		{
			tDiv.body(id,ITablesConst.usersTableId).row(rowIndex).image(alt, "/Open User Profile/").click();
			
			return true;
		}
		
		return false;
	}
	
	public static boolean changeUserEmail(String userName, String emailAddress) throws Exception {

		IE ie = IEUtil.getActiveIE();

		int rowIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);
		
		GeneralUtil.takeANap(1.5);
		
		FiltersUtil.filterListByLabel(IFiltersConst.administration_UserName_Lbl, userName, IFiltersConst.exact);

		rowIndex = TablesUtil.getRowIndex(ITablesConst.usersTableId, userName);
		
		Div tDiv = TablesUtil.tableDiv();

		if (rowIndex > -1) {
			
			log.debug("Row Index is: " + rowIndex);
			
			int col = TablesUtil.findColumnIndex(ITablesConst.usersTableHeaderId, "Name");
			
			if(!tDiv.body(id, ITablesConst.usersTableId).row(rowIndex).cell(col).link(0).exists())
			{
				log.error("the User Id Link could not be found in the List!");
				return false;
			}		
			
			tDiv.body(id, ITablesConst.usersTableId).row(rowIndex).cell(col).link(0).click();
			
			GeneralUtil.takeANap(0.5);
			
			ie.textField(id, "/email_address/").set(emailAddress);

			ie.textField(id, "/emailConfirm/").set(emailAddress);

			//ClicksUtil.clickButtons(IClicksConst.saveBtn);

			ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn); //comment when DD works in JSF2, uncomment below
			//ClicksUtil.clickButtons(IClicksConst.userBackToUsersListBtn);
			GeneralUtil.takeANap(0.5);

			return true;
		}
		
		return false;
	}

	public static boolean saveUserByUserName(String userName) throws Exception {

		int rowIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);

		FiltersUtil.filterListByLabel(IFiltersConst.administration_UserName_Lbl, userName,IFiltersConst.exact);

		rowIndex = TablesUtil.getRowIndex(ITablesConst.usersTableId, userName);
		
		Div tDiv = TablesUtil.tableDiv();

		if (rowIndex > -1) {
			tDiv.body(id, ITablesConst.usersTableId).row(rowIndex).cell(IClicksConst.userNameColumn).link(0).click();

			ClicksUtil.clickButtons(IClicksConst.saveBtn);

			ClicksUtil.clickButtons(IClicksConst.userBackToUsersListBtn);
			
			return true;
		}

		return false;
	}
	
	public static boolean doesActivationCheckBoxAvailableInUserAccount(String userName) throws Exception {
		
		IE ie = IEUtil.getActiveIE();

		Div tDiv = TablesUtil.tableDiv();

		int rowIndex = -1;

		rowIndex = TablesUtil.getRowIndex(ITablesConst.usersTableId, userName);
		
		tDiv.body(id, ITablesConst.usersTableId).row(rowIndex).cell(IClicksConst.userNameColumn).link(0).click();

		ClicksUtil.clickButtons(IClicksConst.openUserAccountBtn);
		
		if(ie.span(id, "main:A:B:D:K").table(0).row(0).checkbox(0).exists())
		{
			return true;
		}
		
		return false;
	}
	
	
	public static boolean doesActivationCheckBoxAvailableInList(String userName) throws Exception {
		
		Div tDiv = TablesUtil.tableDiv();

		int rowIndex = -1;

		rowIndex = TablesUtil.getRowIndex(ITablesConst.usersTableId, userName);
		
		if(tDiv.body(id,ITablesConst.usersTableId).row(rowIndex).checkbox(0).exists())
		{
			return true;
		}
		
		return false;
	}
	
	
	public static boolean doesActivationCheckBoxEnabledInList(String userName) throws Exception {
				
		if(!doesActivationCheckBoxAvailableInList(userName))
		{
			log.error("Could not find Checkbox!");
			return false;
		}

		int rowIndex = -1;

		rowIndex = TablesUtil.getRowIndex(ITablesConst.usersTableId, userName);
		
		Div tDiv = TablesUtil.tableDiv();

		if(tDiv.body(id,ITablesConst.usersTableId).row(rowIndex).checkbox(0).enabled())
		{
			return true;
		}
		
		return false;
	}

	private static boolean doesObjectExistsInList(String fieldLbl, String name,
			String tableId, String type) throws Exception {

		retValue = false;

		Hashtable<String, String> filterValues = new Hashtable<String, String>();
		Hashtable<String, String> modesValues = new Hashtable<String, String>();

		filterValues.put(fieldLbl, name);

		log.info(type);

		if (type == "Users") {

			modesValues.put(IFiltersConst.administration_StatusType_Lbl,
					IFiltersConst.users_AllUsers_StatusView);			
		}
		
		FiltersUtil.filterListByLabel(filterValues, modesValues, false);

		if (TablesUtil.findInTable(tableId, name))
			retValue = true;

		return retValue;

	}

	public static boolean unlockUser(String username) throws Exception {

		retValue = false;

		rowIndex = -1;

		ClicksUtil.clickLinks(IClicksConst.openLockedOutUsersLnk);

		rowIndex = TablesUtil.getRowIndexInTableWithPaginator(
				ITablesConst.lockedOutUsers_TableId, username);
		
		Div tDiv = TablesUtil.tableDiv();

		if (rowIndex > -1) {
			tDiv.body(id, ITablesConst.lockedOutUsers_TableId).row(rowIndex)
					.link(title, "Unlock User").click();

			retValue = true;
		}

		return retValue;
	}

}
