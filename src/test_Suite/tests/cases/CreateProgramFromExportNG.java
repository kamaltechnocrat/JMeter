/**
 * Thi sscript uses exported Program to regenerate new Program
 * bypassing the Import functionality.
 * Script provides teh sample of how to reuse XML files for Sanity/Regression testing
 * * The limitation of this script is that it does not take the file from the sipped archive
 * * which is totally doible but requires usage of additional libraries in Java
 */
package test_Suite.tests.cases;

import static watij.finders.SymbolFactory.id;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import test_Suite.constants.preTest.IPreTestConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.lib.workflow.Program;
import test_Suite.lib.workflow.ProgramSteps;
import test_Suite.lib.cases.XPathHelper;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.HtmlFormsUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.HtmlFormsUtil.ECreateUpdate;
import watij.runtime.ie.IE;

import org.fest.swing.annotation.*;

/**
 * @author apankov
 * 
 */
@GUITest
@Test(singleThreaded = true)
public class CreateProgramFromExportNG {
	private static Log log = LogFactory.getLog(CreateProgramFromExportNG.class);
	
	IE ie;
	
	public static final int ELEMENT_NODE = 1;
	
	DocumentBuilderFactory factory = null;
	
	DocumentBuilder docbuilder = null;

	private static final String exportFilePath = "C:\\Programs\\Project\\grantium_qa_watij\\src\\test_Suite\\xml_Files\\programs\\";
	
	private static final String exportFileName = "A-Gnrl-Prog-F";
	
	String dummystepnames[] = { "Dummy text", "Dummy text", "Dummy text" };
	
	Program prog;
	
	ArrayList<ProgramSteps> progSteps;
	
	String dateTimeFormat[];

	@BeforeClass
	public void setUp() {

	}

	@Test(groups = { "CasesNG" })
	/*
	 * Parent method
	 */
	public void createProgramFromExportNG() throws Exception {

		loadExportFile();
		openPO();
		createProgram();
	}

	/**
	 * Open Browser and open G3 PO Log in as admin user
	 */
	private void openPO() {
		try {
			IEUtil.openNewBrowser();
			ie = IEUtil.getActiveIE();
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

	private void loadExportFile() {
		try {
			this.factory = DocumentBuilderFactory.newInstance();
			this.docbuilder = this.factory.newDocumentBuilder();
			Document doc = this.docbuilder.parse(new File(exportFilePath
					+ exportFileName + IGeneralConst.FILE_EXT_XML));
			log.info("Source XML document found");

			Element docNode = doc.getDocumentElement();
			if (docNode.getNodeName().equals("Program")) {
				log.info("Document node name is correct: "
						+ docNode.getNodeName());
				setProgramDetails(docNode);
			} else
				log.warn("Document node name is correct for thos script: "
						+ docNode.getNodeName());
		} catch (Exception ex) {
			log.debug("ERROR in loadExportFile() " + ex.getMessage());
		}
	}

	private void setProgramDetails(Element docElement) {
		try {
			Element node = null;
			NamedNodeMap map = null;
			prog = new Program();
			for (int i = 0; i < docElement.getChildNodes().getLength(); i++) {
				if (docElement.getChildNodes().item(i).getNodeType() == ELEMENT_NODE) {
					// Start to do assignements usieng Program object setters
					node = (Element) docElement.getChildNodes().item(i);
					map = node.getAttributes();
					// log.info("child node name in docElement node is: " +
					// node.getNodeName());
					if (node.getNodeName().equals("ProgramManager")) {
						// This is bad becasue node value and the value in
						// dropdown do not match directly
						if ((map.getNamedItem("manager")).getNodeValue()
								.equals("Admin"))
							prog
									.setProgramOfficer(IPreTestConst.adminProgPOOfficer);
						log.info("Set Program Officer: "
								+ prog.getProgramOfficer());
					}
					if (node.getNodeName().equals("Note"))
						prog.setProgName((map.getNamedItem("note"))
								.getNodeValue());
					if (node.getNodeName().equals("ProgramFormName"))
						prog.setProgramFormName((map.getNamedItem("name"))
								.getNodeValue());
					if (node.getNodeName().equals("PublicationFormName"))
						prog.setPublicationFormName((map.getNamedItem("name"))
								.getNodeValue());
					if (node.getNodeName().equals("CasePrefix"))
						prog.setProgPreFix((map.getNamedItem("prefix"))
								.getNodeValue());
					if (node.getNodeName().equals("DisableFoProjectCreation"))
						prog.setCreationFOProjDisable(Boolean.valueOf((map
								.getNamedItem("value")).getNodeValue()));
					if (node.getNodeName().equals("ApplicantFormRequired"))
						prog.setCompleteAFRequired(Boolean.valueOf((map
								.getNamedItem("value")).getNodeValue()));
					if (node.getNodeName().equals("ProgramApprovalProcess"))
						prog.setProgFundOpportunityProcess((map
								.getNamedItem("identifier")).getNodeValue());
					// Find org hierarchy properties
					if (node.getNodeName().equals("OrganizationalHierarchy")) {
						String xQueryForOrg = "//PrimaryOrganization//*";
						NodeList nList = lookupChildNodes(xQueryForOrg, node);
						if (nList != null && nList.getLength() > 0) {
							log
									.info("NodeList 'PrimaryOrganization' contains: "
											+ nList.getLength() + " nodes");
							for (int j = 0; j < nList.getLength(); j++) {
								NamedNodeMap childMap = nList.item(j)
										.getAttributes();
								if (nList.item(j).getNodeName().equals(
										"HierarchicalOrganization"))
									prog.setPrimaryOrg((childMap
											.getNamedItem("identifier"))
											.getNodeValue());
								if (nList.item(j).getNodeName().equals(
										"OrganizationAccessType")) {
									if ((childMap.getNamedItem("typeKey"))
											.getNodeValue()
											.endsWith(
													"G3_ORGANIZATION_ACCESS_LEVEL_PUBLIC"))
										prog.setOrgAccess("Public");
								}
							}
						} else
							log.info("NodeList is NULL - query is incorrect");
					}
					// Setting values for all Dates/Times

					if (node.getNodeName().equals("StartDate")) {
						dateTimeFormat = processTimeStamp((map
								.getNamedItem("date")).getNodeValue());
						prog.setStartDate(dateTimeFormat[0] + "/"
								+ dateTimeFormat[1] + "/" + dateTimeFormat[2]);

					}
					if (node.getNodeName().equals("EndDate")) {
						dateTimeFormat = processTimeStamp((map
								.getNamedItem("date")).getNodeValue());
						prog.setEndDate(dateTimeFormat[0] + "/"
								+ dateTimeFormat[1] + "/" + dateTimeFormat[2]);
					}
					if (node.getNodeName().equals("RegistrationStartDate")) {
						dateTimeFormat = processTimeStamp((map
								.getNamedItem("date")).getNodeValue());
						prog.setProgRegistrOpens(dateTimeFormat[0] + "/"
								+ dateTimeFormat[1] + "/" + dateTimeFormat[2]);
						prog.setProgRegistrOpenHH(dateTimeFormat[3]);
						prog.setProgRegistrOpenMM(dateTimeFormat[4]);
					}
					if (node.getNodeName().equals("RegistrationEndDate")) {
						dateTimeFormat = processTimeStamp((map
								.getNamedItem("date")).getNodeValue());
						prog.setProgRegistrCloses(dateTimeFormat[0] + "/"
								+ dateTimeFormat[1] + "/" + dateTimeFormat[2]);
						prog.setProgRegistrCloseHH(dateTimeFormat[3]);
						prog.setProgRegistrCloseMM(dateTimeFormat[4]);
					}
					// Find out how many Program Names associated with the
					// program
					if (node.getNodeName().equals("ProgramTexts")) {
						String xQueryForNames = "//ProgramTexts//*";
						NodeList nListNames = lookupChildNodes(xQueryForNames,
								node);
						log.info("NodeList 'ProgramTexts' has: "
								+ nListNames.getLength() + " locale elements");
						prog.setProgFullNames(programTexts(nListNames));
					}
					// Find out Program Administrators and Program Staff
					if (node.getNodeName().equals("ProgramStaff")) {
						String xQueryForStaff = "//ProgramStaff//*";
						NodeList nListStaff = lookupChildNodes(xQueryForStaff,
								node);
						log.info("NodeList 'ProgramStaff' has: "
								+ nListStaff.getLength() + " staff entries");
						setAdminStaff(nListStaff);
					}

				}

			}
			// Need to set what to do with this object (either to create or
			// update)
			prog.setCreateOrUpdate(ECreateUpdate.objectCreate.ordinal());
			String xQueryForSteps = "//Step";
			NodeList nListSteps = lookupChildNodes(xQueryForSteps, docElement);
			log.info("NodeList 'nListSteps' has: " + nListSteps.getLength()
					+ " steps");
			if (nListSteps.getLength() > 0)
				setProgramSteps(nListSteps);
		} catch (Exception ex) {
			log.debug("ERROR in setProgramDetails() " + ex.getMessage());
		}
	}

	private void setAdminStaff(NodeList programStaff) {
		try {
			String progAdmins = String.valueOf("");
			String progStaff = String.valueOf("");
			for (int i = 0; i < programStaff.getLength(); i++) {
				NamedNodeMap mapStaff = programStaff.item(i).getAttributes();
				String type = mapStaff.getNamedItem("staffType").getNodeValue();
				String group = mapStaff.getNamedItem("group").getNodeValue();
				// The following block can be done as a separate method with
				// less code, I just do not have spare time to do it, sorry
				if (type.endsWith("ADMIN")) {
					if (i < (programStaff.getLength() - 1))
						progAdmins += (group + ",");
					else
						progAdmins += (group);
				} else if (type.endsWith("STAFF")) {
					if (i < (programStaff.getLength() - 1))
						progStaff += (group + ",");
					else
						progStaff += (group + ",");
				}
			}
			prog.setProgAdmin(progAdmins.split(","));
			prog.setProgStaff(setStaff(progStaff.split(",")));
		} catch (Exception ex) {
			log.debug("ERROR in setAdminStaff() " + ex.getMessage());
		}
	}

	// The next method has hardcoded values for ShowAll and Org from Program
	// Staff screen, should be replaced with data pulled from XML
	private Hashtable<String, Object> setStaff(String[] staffmembers) {
		Hashtable<String, Object> staff = new Hashtable<String, Object>();
		staff.put(HtmlFormsUtil.staffShowAll, false);
		staff.put(HtmlFormsUtil.staffOrg, IGeneralConst.primG3_OrgRoot);
		staff.put(HtmlFormsUtil.staffGroups, staffmembers);

		return staff;
	}

	private void setProgramSteps(NodeList steps) {
		try {
			ArrayList<ProgramSteps> arrSteps = new ArrayList<ProgramSteps>();
			for (int i = 0; i < steps.getLength(); i++) {
				Element stepNode = (Element) steps.item(i);
				arrSteps.add(stepDetails(stepNode));
			}
			prog.setProgSteps(arrSteps);
		} catch (Exception ex) {
			log.debug("ERROR in setProgramSteps() " + ex.getMessage());
		}
	}

	private ProgramSteps stepDetails(Element step) throws Exception {
		try {
			ProgramSteps stp = new ProgramSteps();
			stp.setProgId(prog.getProgName());
			NamedNodeMap mapStep = step.getAttributes();

			stp.setStepIsCritical(Boolean.valueOf(mapStep.getNamedItem(
					"critical").getNodeValue()));
			stp
					.setStepId(mapStep.getNamedItem("stepIdentifier")
							.getNodeValue());

			Element nd = null;
			for (int i = 0; i < step.getChildNodes().getLength(); i++) {
				if (step.getChildNodes().item(i).getNodeType() == ELEMENT_NODE) {
					nd = (Element) step.getChildNodes().item(i);
					NamedNodeMap m = nd.getAttributes();
					if (nd.getNodeName().equals("StepNotes"))
						stp.setStepNotes(nd.getNodeValue());
					if (nd.getNodeName().equals("StepAdministrator"))
						stp.setStepProjOfficerGroup(m.getNamedItem("group")
								.getNodeValue());
					if (nd.getNodeName().equals("StartDate")) {
						dateTimeFormat = processTimeStamp((m
								.getNamedItem("date")).getNodeValue());
						stp.setStepStartDate(dateTimeFormat[0] + "/"
								+ dateTimeFormat[1] + "/" + dateTimeFormat[2]);
					}
					if (nd.getNodeName().equals("EndDate")) {
						dateTimeFormat = processTimeStamp((m
								.getNamedItem("date")).getNodeValue());
						stp.setStepEndDate(dateTimeFormat[0] + "/"
								+ dateTimeFormat[1] + "/" + dateTimeFormat[2]);
					}
					if (nd.getNodeName().equals("StepTexts")) {
						String xQueryForNames = "//StepTexts";
						NodeList nListStepNames = lookupChildNodes(
								xQueryForNames, nd);
						log.info("NodeList 'StepTexts' has: "
								+ nListStepNames.getLength()
								+ " locale elements");
						stp.setStepNames(dummystepnames);
					}
					stp.setStepForm("Sub"); // Dummy thing to save some time for
											// demo
					// Simply becasue I do not have time anymore (Alex)

				}
			}

			stp.setStepAction(ECreateUpdate.objectCreate.ordinal());

			return stp;
		} catch (Exception ex) {
			log.info("ERROR occured in stepDetails() " + ex.getMessage());
			return null;
		}
	}

	private void createProgram() {
		try {
			if (prog != null) {
				ClicksUtil.clickLinks(IClicksConst.openFundingOppsListLnk);
				ie.textField(id, "/" + IFiltersConst.txtFilterItem + "/").set(
						prog.getProgName());
				ClicksUtil.clickButtons(IClicksConst.filterBtn);
				HtmlFormsUtil.fillProgramDetails(prog);
			} else
				log.info("Program object is null");
		} catch (Exception ex) {
			log.debug("ERROR in etProgramDetails() " + ex.getMessage());
		}
	}

	/**
	 * initializes and supplies parameters to parse XML content for matching
	 * nodes, returns NodeList of matched nodes for further manipulations
	 * 
	 * @param xq
	 * @param source`
	 * @return NodeList
	 * @throws Exception
	 */
	public NodeList lookupChildNodes(String xq, Element source)
			throws Exception {
		XPathHelper xpathHelper = new XPathHelper();
		NodeList nl = xpathHelper.processXPath(xq, source);

		return nl;
	}

	/**
	 * Forms String array to pass into a setter for Program object
	 * 
	 * @param locales
	 * @return String[]
	 * @throws Exception
	 */
	private String[] programTexts(NodeList locales) throws Exception {
		String localTexts = String.valueOf("");
		for (int i = 0; i < locales.getLength(); i++) {
			NamedNodeMap mapLocales = locales.item(i).getAttributes();
			String progLocalName = mapLocales.getNamedItem("Text")
					.getNodeValue();
			if (i < (locales.getLength() - 1))
				localTexts += (progLocalName + ",");
			else
				localTexts += progLocalName;
		}
		log.info("Locales = " + localTexts);
		return localTexts.split(",");
	}

	/**
	 * Converts supplied string format like "2008.10.11 AD 23:59:00 EDT" into
	 * String array {"11","10","2007","23","59"}
	 * 
	 * @param tStamp
	 * @return String[]
	 * @throws Exception
	 */
	public String[] processTimeStamp(String tStamp) throws Exception {
		String pattern1 = "yyyy.MM.dd G HH:mm:ss z";
		String pattern2 = "dd-MM-yyyy-HH-mm";

		SimpleDateFormat df1 = new SimpleDateFormat(pattern1);
		SimpleDateFormat df2 = new SimpleDateFormat(pattern2);
		Date dt = df1.parse(tStamp);
		String tmpValue = df2.format(dt);
		log.info("tmpValue: " + tmpValue);

		return tmpValue.split("-");
	}
}
