/**
 * 
 */
package test_Suite.lib.authentications;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.authentications.IAuthenConst;

/**
 * @author mshakshouki
 *
 */
public class Authens {
	private static Log log = LogFactory.getLog(Authens.class);
	
	private IAuthenConst.EAuthProviderTypes ePO;
	private IAuthenConst.EAuthProviderTypes eFO;
	
	private String poSiteBase;
	private String foSiteBase;
	
	private String poUserName;
	private String poPassword;
	private String foUserName;
	private String foPassword;

	/**
	 * 
	 */
	public Authens() {
	}
	
	/**
	 * 
	 */
	public Authens(IAuthenConst.EAuthProviderTypes po, IAuthenConst.EAuthProviderTypes fo) {
		try {

			this.setEPO(po);
			this.setEFO(fo);			

			Properties p = new Properties();
			
			p.load(new FileInputStream(new File(
					"src/test_Suite/deployment_path.properties")));
			
			String base = p.getProperty("siteBase");
			
			this.setPoSiteBase(this.initSiteBase(base) + "programOffice.jsf");
			
			this.setFoSiteBase(this.initSiteBase(base) + "frontOffice.jsf");
			
			log.info("Auth Initialized!");
			
		} catch (Exception e) {
			Assert.fail("Unexpected Exception: ", e);
		}
	}
	
	public String initSiteBase(String url) throws Exception {
		
		
		String[] arr = url.split("/");
		String newUrl = "";
		
		for (String string : arr) {
			
			newUrl += string + "/";
			
		}		
		return newUrl;
	}
	

	/**
	 * @return the poUserName
	 */
	public String getPoUserName() {
		return poUserName;
	}

	/**
	 * @param poUserName the poUserName to set
	 */
	public void setPoUserName(String poUserName) {
		this.poUserName = poUserName;
	}

	/**
	 * @return the poPassword
	 */
	public String getPoPassword() {
		return poPassword;
	}

	/**
	 * @param poPassword the poPassword to set
	 */
	public void setPoPassword(String poPassword) {
		this.poPassword = poPassword;
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
	 * @return the poSiteBase
	 */
	public String getPoSiteBase() {
		return poSiteBase;
	}

	/**
	 * @param poSiteBase the poSiteBase to set
	 */
	public void setPoSiteBase(String poSiteBase) {
		this.poSiteBase = poSiteBase;
	}

	/**
	 * @return the foSiteBase
	 */
	public String getFoSiteBase() {
		return foSiteBase;
	}

	/**
	 * @param foSiteBase the foSiteBase to set
	 */
	public void setFoSiteBase(String foSiteBase) {
		this.foSiteBase = foSiteBase;
	}

	/**
	 * @return the ePO
	 */
	public IAuthenConst.EAuthProviderTypes getEPO() {
		return ePO;
	}

	/**
	 * @param epo the ePO to set
	 */
	public void setEPO(IAuthenConst.EAuthProviderTypes epo) {
		ePO = epo;
	}

	/**
	 * @return the eFO
	 */
	public IAuthenConst.EAuthProviderTypes getEFO() {
		return eFO;
	}

	/**
	 * @param efo the eFO to set
	 */
	public void setEFO(IAuthenConst.EAuthProviderTypes efo) {
		eFO = efo;
	}

}
