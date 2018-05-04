/**
 * Steps:
 * 1. Export Users
 * 2. Check exporting data in XML file for Organization node and its value
 */
package test_Suite.tests.stories.release_2_0.iter_4;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;


import test_Suite.lib.cases.XPathHelper;

import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.constants.cases.IGeneralConst;
import watij.dialogs.*;
import watij.runtime.ie.IE;


/**
 * @author apankov
 * Relies on PreTestNG
 */
@Test(singleThreaded = true)
public class S2592_01NG {
	private static Log log = LogFactory.getLog(S2592_01NG.class);
	
    DocumentBuilderFactory factory = null;
    DocumentBuilder docbuilder     = null;
    private static final String xQuery = "*//OrganizationalHierarchy//PrimaryOrganization//HierarchicalOrganization[@identifier]";
    
	private static final String exportFilePath = "C:\\Programs\\Project\\grantium_qa_watij\\src\\test_Suite\\temp_downloads\\";
	private static final String exportFileName = "ExportUsers";
	IE ie;

	@BeforeClass  
	public void setUp() {
		
	} 

	/**
	 * Parent Method follows script steps
	 */
	@Test(groups = { "Iter_24" })
	public void s2592_01NG() {
		openPO();
		exportObject();
		checkFile();
	}
	
	/**
	 * Open Browser and open G3 PO
	 * Log in as admin user
	 */
	private void openPO()
	{
		try
		{
			IEUtil.openNewBrowser();
			ie = IEUtil.getActiveIE();
			GeneralUtil.navigateToPO();
			GeneralUtil.loginPO();
		}
		catch (Exception e) {	
			
			log.error("Unexpected Exception in naavigateToLookups() ", e);
			throw new RuntimeException("Unexpected Exception", e);
		} 
	}
	
	private void exportObject()
	{
		try
		{
			ClicksUtil.clickLinks(IClicksConst.openUsersListLnk);
			log.info("Go Users");
			
			new Thread(new Runnable() { 
				public void run() { 
					try { 
							ClicksUtil.clickImageByAlt(IClicksConst.exportUsers);
							ClicksUtil.clickButtons(IClicksConst.m2MAllForBtn);
							ClicksUtil.clickButtons("Export Users");
							GeneralUtil.takeANap(5.000);
							IEUtil.closeBrowser();
						} 
					catch (Exception e) { 
						throw new RuntimeException("Unexpected exception",e);
					} 
				} 
			}).start();
						GeneralUtil.takeANap(1.0);
						
						IE ie2 = IEUtil.getActiveIE();
						FileDownloadDialog dialog1 = ie2.fileDownloadDialog();
						if(dialog1 != null)
							log.info("download file dialog identified");
						else
						{
							while(dialog1 == null)
								log.info("can't yet get handle on file dialog1");
						}
						dialog1.save(exportFilePath+exportFileName+IGeneralConst.FILE_EXT_XML);
						dialog1.waitUntilDownloadComplete();
						log.info("download file complete");	
						ie2 = IEUtil.getActiveIE();
						ie2.close();

		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in exportObject() " + ex.getMessage());
		}
	}
	/**
	 * Close browser after all done
	 */
	private void checkFile()
	{
		try
		{	
			Element node     = null;
			String nodeAttr  = null;
			NamedNodeMap map      = null;
			this.factory    = DocumentBuilderFactory.newInstance();
			this.docbuilder = this.factory.newDocumentBuilder();
			Document doc = this.docbuilder.parse(new File(exportFilePath+exportFileName+IGeneralConst.FILE_EXT_XML));
		    log.info("Source XML document found");
		    NodeList nList = lookupChildNodes(xQuery, doc.getDocumentElement());
		    log.info("Organizational Hierarchy nodes found " + nList.getLength());
		    
		    for(int i=0; i<nList.getLength(); i++)
		    {
		    	node = (Element)nList.item(i);
		    	map = node.getAttributes();
			
		    	nodeAttr = (map.getNamedItem("identifier")).getNodeValue();
		    	log.info("Primary Organization: " + nodeAttr);
		    	
		    	if(nodeAttr.equals("G3"))
		    		log.warn("TEST PASSED");
		    	else
		    		log.warn("TEST FAILED");
		    	
		    }
		}
		catch(Exception ex)
		{
			log.debug("ERROR in checkFile() " + ex.getMessage());
		}
	}
	
	/**
	 * initializes and supplies parameters to parse XML content for matching nodes, 
	 * returns NodeList of matched nodes for further manipulations
	 * @param xq
	 * @param source
	 * @return NodeList
	 * @throws Exception
	 */
    public NodeList lookupChildNodes(String xq, Element source) throws Exception 
    {
    	XPathHelper xpathHelper = new XPathHelper();
    	NodeList nl = xpathHelper.processXPath(xq, source);
    	   
    	return nl;
    }
}
