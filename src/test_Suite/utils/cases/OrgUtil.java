/**
 * 
 */
package test_Suite.utils.cases;

/**
 * @author mshakshouki
 *
 */

import static watij.finders.FinderFactory.attribute;
import static watij.finders.SymbolFactory.*;
import watij.dialogs.ConfirmDialog;
import watij.elements.HtmlElement;
import watij.elements.HtmlElements;
import watij.elements.Image;
import watij.elements.Images;
import watij.elements.Table;
import watij.elements.Tables;
import watij.runtime.ie.IE;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.cases.IOrgConst;
import test_Suite.constants.cases.IOrgConst.EOrgPlannerObjects;
import test_Suite.constants.ui.*;
import test_Suite.utils.ui.*;
import test_Suite.lib.cases.Organizations;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;


public class OrgUtil {
	
	private static Log log = LogFactory.getLog(OrgUtil.class);
	
	static boolean retValue;
	
	static int objIndex;
	static int retIndex;
	static int startIndx;
	
	static Tables tables;
	static Table table;
	static Images images;
	static Image image;
	
	
	public static boolean openNewChildOrg(String parentOrg) throws Exception {
		IE ie = IEUtil.getActiveIE();
		retValue = false;
		expandAllNodesInOrgPlanner();
		
		objIndex = getObjectIndexInOrgPlanner(parentOrg, 0);		
		
		
		if(objIndex > -1)
		{
			objIndex = getObjectIndexInOrgPlanner("Child Organizations",objIndex);
			if(objIndex > -1)
			{
				table = ie.span(id,IOrgConst.org_Planner_SpanId).tables().table(objIndex);
				images = table.images();
				for(int i=0; i<images.length();i++)
				{
					if(images.image(i).alt().contains("Add Sub-Organization"))
					{
						images.image(i).click();
						retValue = true;
						break;
					}
				}
			}
		}	
		
		return retValue;		
	}
	
	public static boolean openParentOrgPlanner(String parentOrg) throws Exception {
		retValue = false;
		
		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		Hashtable<String, String> dropDownHash = new Hashtable<String, String>();
		
		if (!ClicksUtil.clickLinks(IClicksConst.openOrganizationsList))
		{
			log.error("cannot click on link " .concat(IClicksConst.openOrganizationsList));
			return false;			
		}
		
		hashTable.put(IFiltersConst.administration_OrgIdent_Lbl, parentOrg);
		
		dropDownHash.put(IFiltersConst.administration_Status_Lbl, "All");
		dropDownHash.put(IFiltersConst.administration_Type_Lbl, "All");		
		
		FiltersUtil.filterListByLabel(hashTable, dropDownHash, false);
		
		TablesUtil.findInTable(ITablesConst.org_Org_TableId, new String[] {parentOrg});
		
		if (!ClicksUtil.clickLinks(parentOrg))
		{
			log.error("cannot click on link parent Org");
			return false;			
		}
		
		retValue = true;
		return retValue;
	}
	
	
	
	public static boolean changeOrgOfficer(String OrgId, String officerName) throws Exception {
		
		retValue = false;
		objIndex = -1;
		
		openParentOrgPlanner(OrgId);
		
		objIndex = getObjectIndexInOrgPlanner(OrgId, 0);
		
		if(objIndex > -1)
		{
			Assert.assertTrue(openObjectDetailsInOrgPlanner(objIndex), "Fail: Could Not find Org to drill down");
			
			Assert.assertTrue(GeneralUtil.selectInDropdownList(IOrgConst.org_Officer_Drpdwn_Id, officerName), "Fail: Could not find Officer in List");
			
			retValue = true;
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			ClicksUtil.clickButtons(IClicksConst.backBtn);
		}
		
		
		return retValue;
	}
	
	public static boolean selectAndOpenOrgEForm(String orgIdent, String orgEFormIdent) throws Exception {
		
		objIndex = -1;
		
		openParentOrgPlanner(orgIdent);
		
		objIndex = getObjectIndexInOrgPlanner(orgIdent, 0);
		
		if(objIndex > -1)
		{
			Assert.assertTrue(openObjectDetailsInOrgPlanner(objIndex), "Fail: Could Not find Org to drill down");
			
			String ddStr = GeneralUtil.getSelectedItemValueInDropdwonById(IOrgConst.org_Form_DrpDwn_Id);
			
			if (!ddStr.equals("0"))
			{
				Thread dialogClicker = new Thread() {
					@SuppressWarnings("static-access")
					@Override
					public void run() {
						try {
							
							IE ie = IEUtil.getActiveIE();
							
							ConfirmDialog dialog1 = ie.confirmDialog();
							
							while (dialog1 == null) 
							{
								log.debug("can't yet get handle on confirm dialog1");
								
								this.sleep(250);
							}

							dialog1.ok();
							
							log.debug("got confirm Dialog1 and clicked OK.");
							
						} catch (Exception e) {
							throw new RuntimeException("Unexpected exception", e);
						}
					}
				};

				dialogClicker.start();
				log.debug("started dialog clicker thread");
				
				Assert.assertTrue(GeneralUtil.selectInDropdownList(IOrgConst.org_Form_DrpDwn_Id, orgEFormIdent), "Fail: Could not find Org eForm in List");
				
				GeneralUtil.takeANap(1.000);
				
				dialogClicker = null;
			}
			
			Assert.assertTrue(GeneralUtil.selectInDropdownList(IOrgConst.org_Form_DrpDwn_Id, orgEFormIdent), "Fail: Could not find Org eForm in List");
			
			Assert.assertTrue(GeneralUtil.selectInDropdownList(IOrgConst.org_Officer_Drpdwn_Id, "Shak"), "Fail: Could not find Org Officer in List");
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
			
			expandAllNodesInOrgPlanner();
			
			HtmlElement ele = getListItem("Organization Form", 0);
			
			ele.image(0).click();
			
			return true;
		}
		
		return false;
	}
	

	
	public static boolean openOrgEForm(String orgIdent) throws Exception {
		
		
		IE ie = IEUtil.getActiveIE();
		
		objIndex = -1;
		
		openParentOrgPlanner(orgIdent);
		
		expandAllNodesInOrgPlanner();
		
		if(!ie.link(id,"j_id_1v:planner:0_0:viewOrganizationForm").exists())
		{
			log.error("Could not Find Org eForm to open!");
			return false;
		}
		
		ie.link(id,"j_id_1v:planner:0_0:viewOrganizationForm").click();
		
//		tables = ie.span(id,IOrgConst.org_Planner_SpanId).tables();
//		
//		table = tables.table(1);
//		
//		log.info(table.innerText());
//		
//		if (table.link(id, "/viewOrganizationForm/").exists())
//		{
//			table.link(id, "/viewOrganizationForm/").click();
//			return true;
//		}	
		
		return true;
		
		
		}
	
	public static boolean selectAndOpenOrgEForm(String orgIdent) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		objIndex = -1;
		
		openParentOrgPlanner(orgIdent);
				
		tables = ie.span(id,IOrgConst.org_Planner_SpanId).tables();
			
		table = tables.table(1);
			
		log.info(table.innerText());
			
			if (table.link(id, "/viewOrganizationForm/").exists())
			{
				table.link(id, "/viewOrganizationForm/").click();
				return true;
			}			
		
		
		return false;
	}
	public static boolean addNewChildOrg(Organizations childOrg) throws Exception {
		IE ie = IEUtil.getActiveIE();
		retValue = false;
		
		openParentOrgPlanner(childOrg.getParentOrg());
		
		if(openNewChildOrg(childOrg.getParentOrg()))
		{
			ie.textField(id,IOrgConst.org_Ident_TxtFld_Id).set(childOrg.getOrgId());
			
			if(childOrg.isOrgFormIncluded())
			{
				ie.selectList(id,IOrgConst.org_Form_DrpDwn_Id).select(childOrg.getOrgForm());
			}
			
			if(childOrg.isInheritParentChanges())
			{
				ie.checkbox(id,IOrgConst.inhParent_Chkbox_Id).set(childOrg.isInheritParentChanges());			
			}
			else
			{
				ie.selectList(id,IOrgConst.org_Type_Drpdwn_Id).select(childOrg.getOrgType());
				ie.selectList(id,IOrgConst.org_Status_Drpdwn_Id).select(childOrg.getOrgStatus());
				ie.selectList(id,IOrgConst.org_DefaultLocale_Drpdwn_Id).select(childOrg.getOrgDefLoc());
				ie.selectList(id,IOrgConst.org_Officer_Drpdwn_Id).select(childOrg.getOrgOfficer());			
			}
			
			for(int i=0; i<childOrg.getOrgFullNames().length; i++)
			{
				if(ie.textField(id, IOrgConst.org_FullName_TxtFld_StartId + Integer.toString(i) + IOrgConst.org_FullName_TxtFld_EndId).exists())
				{
					ie.textField(id, IOrgConst.org_FullName_TxtFld_StartId + Integer.toString(i) + IOrgConst.org_FullName_TxtFld_EndId).set(childOrg.getOrgFullNames()[i]);
					ie.textField(id, IOrgConst.org_ShortName_TxtFld_StartId + Integer.toString(i) + IOrgConst.org_ShortName_TxtFld_EndId).set(childOrg.getOrgShortNames()[i]);				
				}			
			}
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			ClicksUtil.clickButtons(IClicksConst.backBtn);
			retValue = true;
		}		
		
		return retValue;
	}
	
	public static boolean openObjectDetailsInOrgPlanner(int objIndex) throws Exception {
		retValue = false;
		
		expandAllNodesInOrgPlanner();
		
		HtmlElement ele = getListItem_byInt(objIndex);
		
		images = ele.images();
		for(int i=0; i<images.length(); i++)
		{
			if(images.image(i).alt().contains(IOrgConst.orgPlanner_OrgNodeImageAlt))
			{
				images.image(i).click();
				retValue = true;
				break;
			}
		}
		return retValue;
	}
	
	
	
	public static boolean addLookupMappingToRootOrg() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if (!ClicksUtil.clickLinksByTitle("Add Lookup Mapping"))
		{
			log.error("could not add lookup mapping !");
			return false;
		}
		
		ie.selectList(id, IOrgConst.lookupView_organizationLookupMapping).select("Activity Types");
		
		GeneralUtil.takeANap(0.5);
		
		if(!ClicksUtil.clickButtons(IClicksConst.m2MAllForBtn))
		{
			log.error("could not click button " .concat(IClicksConst.m2MAllForBtn));
			return false;
		}
		
		
		if(!ClicksUtil.clickButtons(IClicksConst.saveAndBackBtn))
			return false;
		
		return true;
	}
	
	public static HtmlElement getListItem(String innerText, int startIndex) throws Exception {
	
		HtmlElement parentNode = getListItem_byInt(startIndex);

		HtmlElements eles = parentNode.htmlElements(tag, "li");

		for (HtmlElement ele : eles) {

			if(ele.span(0).innerText().trim().toLowerCase().contains(innerText.toLowerCase())) {

				return ele;
			}			
		}
		return null;
	}
	
	
	public static HtmlElement getListItem_byInt(int index) throws Exception {

		IE ie = IEUtil.getActiveIE();

		HtmlElements eles = ie.div(attribute("class", IGeneralConst.planner_div_class)).htmlElements(tag, "li");

		int counter = 0;
		for (HtmlElement ele : eles) {

			if (counter == index){
				return ele;
			}
			counter++;
		}
		return null;
	}
	
	public static boolean openObjectDetailsInOrgPlanner(EOrgPlannerObjects eOrgObj) throws Exception {
		retValue = false;
		
		HtmlElement ele = getListItem_byInt(eOrgObj.ordinal());
		
		images = ele.images();
		
		for(int i=0; i<images.length(); i++)
		{
			if(images.image(i).alt().contains(IOrgConst.orgPlanner_OrgNodeImageAlt))
			{
				images.image(i).click();
				retValue = true;
				break;
			}
		}
		return retValue;
	}
	
	public static int getObjectIndexInOrgPlanner(String objName, int startIndex) throws Exception {
		
		HtmlElement parentNode = getListItem_byInt(startIndex);
		
		HtmlElements eles = parentNode.htmlElements(tag, "li");

		int counter = 0;
		
		for (HtmlElement ele : eles) {

			if(ele.span(0).innerText().trim().toLowerCase().contains(objName.toLowerCase())) {

				return counter+startIndex;
			}	
			counter++;
			break;
		}
		log.error(objName + " not found!");
		return -1;
	}
	

	
	public static boolean expandAllNodesInOrgPlanner() throws Exception {
		
		if (ClicksUtil.clickButtons(IClicksConst.expandAll_Btn)){
			return true;
		}
		else
			return false;
		
//		IE ie = IEUtil.getActiveIE();
//		
//		if(!ie.span(id,IOrgConst.org_Planner_SpanId).exists())
//		{
//			log.error("could not find Organization Planner");
//			return false;
//		}
//		tables = ie.span(id, IOrgConst.org_Planner_SpanId).tables();
//		
//		table = tables.get(0);
//		
//		if(!table.innerText().startsWith(IOrgConst.orgPlanner_OrgNodeInnerText + IOrgConst.org_G3Root_Id))
//        {
//			log.error("could not find Organization G3");
//			return false;  
//        }
//		
//	 	
//		if(table.image(0).src().contains(IOrgConst.orgPlanner_MinusGif_Last_Id))
//		{
//			table.image(0).click();
//		}
//		
//		table.image(0).click();
//		return true;		
	}
	
	
	
	/* not used
	
	public static void fixBugInG3Org() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		openParentOrgPlanner(IOrgConst.org_G3Root_Id);
		
		openObjectDetailsInOrgPlanner(getObjectIndexInOrgPlanner(IOrgConst.org_G3Root_Id,0));
		
		ie.selectList(id, IOrgConst.org_Officer_Drpdwn_Id).select(IPreTestConst.ProgPOfficer);
		
		/*if(ie.textField(id, IOrgConst.org_FullName_TxtFld_StartId + "1" + IOrgConst.org_FullName_TxtFld_EndId).exists())
		{
			ie.textField(id, IOrgConst.org_FullName_TxtFld_StartId + "1" + IOrgConst.org_FullName_TxtFld_EndId).set(IOrgConst.org_G3Root_FullName);
			ie.textField(id, IOrgConst.org_ShortName_TxtFld_StartId + "1" + IOrgConst.org_ShortName_TxtFld_EndId).set(IOrgConst.org_G3Root_ShortName);				
		}
		
		if(ie.textField(id, IOrgConst.org_FullName_TxtFld_StartId + "2" + IOrgConst.org_FullName_TxtFld_EndId).exists())
		{
			ie.textField(id, IOrgConst.org_FullName_TxtFld_StartId + "2" + IOrgConst.org_FullName_TxtFld_EndId).set(IOrgConst.org_G3Root_FullName);
			ie.textField(id, IOrgConst.org_ShortName_TxtFld_StartId + "2" + IOrgConst.org_ShortName_TxtFld_EndId).set(IOrgConst.org_G3Root_ShortName);				
		}
		
//		ClicksUtil.clickButtons(IClicksConst.saveBtn);
//		ClicksUtil.clickButtons(IClicksConst.backBtn);
//		}
	
	
	public static void tuggleNodeInOrgPlanner(String nodeName, boolean bolAction, int nodeType) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		//ie.checkbox(id,IOrgConst.org_Planner_Recursive_ExpandId).set(true);
		
		tables = ie.span(id,IOrgConst.org_Planner_SpanId).tables();
		
		table = tables.get(0);
		
		table.image(0).click();
		
		if(table.image(0).src().endsWith(IOrgConst.orgPlanner_MinusGif_Last_Id))
		{
			table.image(0).click();
			ie.checkbox(id,IOrgConst.org_Planner_Recursive_ExpandId).set(true);
			table.image(0).click();
		}
		else
		{
			ie.checkbox(id,IOrgConst.org_Planner_Recursive_ExpandId).set(true);
			table.image(0).click();
		}
		
		objIndex = getObjectIndexInOrgPlanner(nodeName, startIndx);
		
		System.out.println(objIndex);
		
		/*if(nodeType == IOrgConst.EOrgPlannerNodeType.childOrg.ordinal())
		{
			startIndx = 0;
			objIndex = -1;
			tables = ie.span(id,IOrgConst.org_Planner_SpanId).tables();
			do {
				startIndx = getObjectIndexInOrgPlanner("Child Organization", startIndx);
				
				table = tables.get(startIndx);
				images = table.images();
				
				for (int i=0; i<images.length(); i++)
				{
					if((images.image(i).src().endsWith(IOrgConst.orgPlanner_PlusGif_Last_Id)) || (images.image(i).src().endsWith(IOrgConst.orgPlanner_PlusGif_Middle_Id)))
					{
						images.image(i).click();
					}
				}
				
				objIndex = getObjectIndexInOrgPlanner(nodeName, startIndx);
				table = tables.get(objIndex);
				images = table.images();
				
				for (int i=0; i<images.length(); i++)
				{
					if((images.image(i).src().endsWith(IOrgConst.orgPlanner_PlusGif_Last_Id)) ||(images.image(i).src().endsWith(IOrgConst.orgPlanner_PlusGif_Middle_Id)))
					{
						images.image(i).click();
					}
				}
				
			}while(objIndex <= -1);
			
		}*/
	//}
	
}
