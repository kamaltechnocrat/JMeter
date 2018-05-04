/**
 * 
 */
package test_Suite.tests.cases;

import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static watij.finders.SymbolFactory.text;
import static watij.finders.SymbolFactory.id;

import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.constants.cases.IOrgConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.cases.Organizations;
import test_Suite.lib.eForms.EForm;

import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.HtmlFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import watij.runtime.ie.IE;
import org.fest.swing.annotation.*;

/**
 * @author apankov
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class CreateOrgHierarchyNG implements IOrgConst {
	private static Log log = LogFactory.getLog(CreateOrgHierarchyNG.class);
	IE ie;

	private enum EPos {
		orgRoot, // = 0
		orgEast, // = 1
		orgWest, // = 2
		orgEastBoston, // = 3
		orgEastNewYork, // = 4
		orgWestLA, // = 5
		orgWestSeattle
		// = 6
	}

	// Org form names
	private String frmId = "OrgForm";
	private String frmTitles[] = { frmId, frmId, frmId };
	// Org tree names
	private String orgId[] = { IGeneralConst.primG3_OrgRoot, ORG_EAST,
			ORG_WEST, ORG_EAST_BOSTON, ORG_EAST_NEWYORK, ORG_WEST_LA,
			ORG_WEST_SEATTLE };
	// Following six parameters all set to the same values for simplicity
	// In case if different values are required - use arrays instead
	private String orgForm = frmId;
	private boolean orgInhParent = false;
	private String orgType = ORG_TYPE_DEFAULT;
	private String orgStatus = IGeneralConst.statusActive;
	private String orgDefLocale = IPreTestConst.Language;
	private String orgOfficer = IPreTestConst.adminProgPOOfficer;

	private String orgFullNames[][] = {
			{ "Grantium G3", "Grantium G3", "Grantium G3" },
			{ ORG_EAST, ORG_EAST, ORG_EAST }, { ORG_WEST, ORG_WEST, ORG_WEST },
			{ ORG_EAST_BOSTON, ORG_EAST_BOSTON, ORG_EAST_BOSTON },
			{ ORG_EAST_NEWYORK, ORG_EAST_NEWYORK, ORG_EAST_NEWYORK },
			{ ORG_WEST_LA, ORG_WEST_LA, ORG_WEST_LA },
			{ ORG_WEST_SEATTLE, ORG_WEST_SEATTLE, ORG_WEST_SEATTLE } };

	private String orgShortNames[][] = { { "G3", "G3", "G3" },
			{ ORG_EAST, ORG_EAST, ORG_EAST }, { ORG_WEST, ORG_WEST, ORG_WEST },
			{ ORG_EAST_BOSTON, ORG_EAST_BOSTON, ORG_EAST_BOSTON },
			{ ORG_EAST_NEWYORK, ORG_EAST_NEWYORK, ORG_EAST_NEWYORK },
			{ ORG_WEST_LA, ORG_WEST_LA, ORG_WEST_LA },
			{ ORG_WEST_SEATTLE, ORG_WEST_SEATTLE, ORG_WEST_SEATTLE } };

	@BeforeClass
	public void setUp() {

	}

	/**
	 * Parent method
	 */
	@Test(groups = { "CasesNG" })
	public void createOrgHierarchyNG() throws Exception {
		openPO();
		// createOrgForm();
		createOrgTree();
		// closeBrowser();
	}

	/**
	 * Open Browser and open G3 PO Log in as admin user
	 */
	private void openPO() {
		try {
			// IEUtil.openNewBrowser();
			// ie = IEUtil.getActiveIE();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginPO();
		} catch (Exception e) {

			log.error("Unexpected Exception in naavigateToLookups() ", e);
			throw new RuntimeException("Unexpected Exception", e);
		} finally {
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		}
	}

	/**
	 * Method to Associate a Form with Organization Note: form type to be
	 * selected = 'Organization'
	 */
	@SuppressWarnings("unused")
	private void createOrgForm() {
		try {
			ClicksUtil.clickLinks(IClicksConst.openFormsListLnk);
			ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/")
					.set(frmId);
			ClicksUtil.clickButtons(IClicksConst.filterBtn);
			if (!ie.link(text, frmId).exists()) {
				ClicksUtil.clickImage(IClicksConst.newImg);
				EForm frm = new EForm();
				frm.setEFormId(frmId);
				frm.setEFormType(IEFormsConst.organization_FormTypeName);
				frm.setPrimaryOrg(IGeneralConst.primG3_OrgRoot);
				frm.setOrgAccess(IGeneralConst.org_Access_Public);
				frm.setEFormTitles(frmTitles);
				frm.createOrUpdateForm();
			}
		} catch (Exception ex) {
			log.debug("ERROR in createOrgForms() " + ex.getMessage());
		}
	}

	/**
	 * Method to create a sample Organizations tree Method can be modified to
	 * use Setters form Organizations class each time instead of function call
	 * Org() - using setters will be less error prone compare to calling methods
	 * with so many parameters
	 */
	private void createOrgTree() {
		try {
			// Navigate to Organizations screen
			ClicksUtil.clickLinks(IClicksConst.openOrganizationsList);
			// create level1 children orgs
			ArrayList<Organizations> orgLevel1Arr = new ArrayList<Organizations>();
			// create level2 first set of children
			ArrayList<Organizations> orgChildrenWest = new ArrayList<Organizations>();
			// create level2 first set of children
			ArrayList<Organizations> orgChildrenEast = new ArrayList<Organizations>();

			// create Organizations from second level East
			Organizations orgBoston = Org(orgId[EPos.orgEastBoston.ordinal()],
					orgForm, orgInhParent, orgType, orgStatus, orgDefLocale,
					orgOfficer, orgFullNames[EPos.orgEastBoston.ordinal()],
					orgShortNames[EPos.orgEastBoston.ordinal()], null,
					orgId[EPos.orgEast.ordinal()],
					HtmlFormsUtil.ECreateUpdate.objectCreate.ordinal());

			Organizations orgNewYork = Org(
					orgId[EPos.orgEastNewYork.ordinal()], orgForm,
					orgInhParent, orgType, orgStatus, orgDefLocale, orgOfficer,
					orgFullNames[EPos.orgEastNewYork.ordinal()],
					orgShortNames[EPos.orgEastNewYork.ordinal()], null,
					orgId[EPos.orgEast.ordinal()],
					HtmlFormsUtil.ECreateUpdate.objectCreate.ordinal());

			orgChildrenEast.add(orgBoston);
			orgChildrenEast.add(orgNewYork);

			// create Organizations West
			Organizations orgLA = Org(orgId[EPos.orgWestLA.ordinal()], orgForm,
					orgInhParent, orgType, orgStatus, orgDefLocale, orgOfficer,
					orgFullNames[EPos.orgWestLA.ordinal()],
					orgShortNames[EPos.orgWestLA.ordinal()], null,
					orgId[EPos.orgWest.ordinal()],
					HtmlFormsUtil.ECreateUpdate.objectCreate.ordinal());

			Organizations orgSeattle = Org(
					orgId[EPos.orgWestSeattle.ordinal()], orgForm,
					orgInhParent, orgType, orgStatus, orgDefLocale, orgOfficer,
					orgFullNames[EPos.orgWestSeattle.ordinal()],
					orgShortNames[EPos.orgWestSeattle.ordinal()], null,
					orgId[EPos.orgWest.ordinal()],
					HtmlFormsUtil.ECreateUpdate.objectCreate.ordinal());

			orgChildrenWest.add(orgLA);
			orgChildrenWest.add(orgSeattle);

			// create Organization East
			Organizations orgEast = Org(orgId[EPos.orgEast.ordinal()], orgForm,
					orgInhParent, orgType, orgStatus, orgDefLocale, orgOfficer,
					orgFullNames[EPos.orgEast.ordinal()],
					orgShortNames[EPos.orgEast.ordinal()], orgChildrenEast,
					orgId[EPos.orgRoot.ordinal()],
					HtmlFormsUtil.ECreateUpdate.objectCreate.ordinal());
			// create Organization West
			Organizations orgWest = Org(orgId[EPos.orgWest.ordinal()], orgForm,
					orgInhParent, orgType, orgStatus, orgDefLocale, orgOfficer,
					orgFullNames[EPos.orgWest.ordinal()],
					orgShortNames[EPos.orgWest.ordinal()], orgChildrenWest,
					orgId[EPos.orgRoot.ordinal()],
					HtmlFormsUtil.ECreateUpdate.objectCreate.ordinal());

			orgLevel1Arr.add(orgEast);
			orgLevel1Arr.add(orgWest);

			// Update root level organization
			Organizations orgRoot = Org(orgId[EPos.orgRoot.ordinal()], orgForm,
					orgInhParent, orgType, orgStatus, orgDefLocale, orgOfficer,
					orgFullNames[EPos.orgRoot.ordinal()],
					orgShortNames[EPos.orgRoot.ordinal()], orgLevel1Arr, null,
					HtmlFormsUtil.ECreateUpdate.objectUpdate.ordinal());

			HtmlFormsUtil.manageOrg(orgRoot);
		} catch (Exception ex) {
			log.debug("ERROR in createOrgTree() " + ex.getMessage());
		}
	}

	/**
	 * Instantiates Organizations object and supplies parameters to fill
	 * Organization form
	 * 
	 * @param id
	 * @param form
	 * @param check
	 * @param type
	 * @param status
	 * @param deflocale
	 * @param officer
	 * @param fullnames
	 * @param shortnames
	 * @param children
	 * @param parent
	 */
	private Organizations Org(String id, String form, boolean check,
			String type, String status, String deflocale, String officer,
			String[] fullnames, String[] shortnames,
			ArrayList<Organizations> children, String parent, int actiontaken) {
		try {
			Organizations org = new Organizations();
			org.setOrgId(id);
			org.setOrgForm(form);
			org.setInheritParentChanges(check);
			org.setOrgType(type);
			org.setOrgStatus(status);
			org.setOrgDefLoc(deflocale);
			org.setOrgOfficer(officer);
			org.setOrgFullNames(fullnames);
			org.setOrgShortName(shortnames);
			org.setChildOrganizations(children);
			org.setParentOrg(parent);
			org.setUpdateOrCreate(actiontaken);
			return org;
		} catch (Exception ex) {
			log.debug("ERROR in addOrg() " + ex.getMessage());
			return null;
		}
	}

	/**
	 * Close PO application and close web browser
	 * 
	 */
	@SuppressWarnings("unused")
	private void closeBrowser() {
		try {
			// ie = IEUtil.getActiveIE();
			GeneralUtil.Logoff();
			IEUtil.closeBrowser();
		} catch (Exception ex) {
			log.debug("ERROR in closeBrowser() " + ex.getMessage());
		}
	}
}
