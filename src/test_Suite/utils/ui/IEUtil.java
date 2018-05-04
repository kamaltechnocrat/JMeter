/*
 * IEUtil.java
 *
 * Created on January 30, 2007, 4:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package test_Suite.utils.ui;

/**
 *
 * @author mshakshouki
 */

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.utils.cases.GeneralUtil;
import watij.runtime.ie.IE;

public abstract class IEUtil {

	private static Log log = LogFactory.getLog(IEUtil.class); 
            
    private static IE ie;
    
    private static IE child_ie;
    
    public static IE getActiveIE() throws Exception {
     	
    	setIEVersion();
    	
    	if (ie==null)
    	{
    		ie = new IE();
    		ie.start();
    	}
        
    	return ie;
    }
    
    public static IE getActiveChildIE() throws Exception {
    	
    	if(ie==null) {
    		ie = getActiveIE();
    	}

		child_ie = ie.childBrowser();
		return child_ie;
    }
    
    public static void openNewBrowser() throws Exception {       	
    	setIEVersion();
        if (ie!=null)
        {
        	log.warn("openNewBrowser called, but an open browser exists. Existing browser will be closed");
        	
        	ie.close();
        	log.warn("openNewBrowser called, but an open browser exists. Existing browser has been closed");
        	ie = null;
        }
		
        ie = new IE(); 
    	ie.start(); 
    	
    	//in 6.0 and later no way to get the locale in FO, used this way to get the locale once and for all
    	
    	GeneralUtil.getCurrentLocaleNew();
    }
    
    public static void closeBrowser() {
    	try
    	{
    		if (ie!=null)
    		{
    			ie.close();    			
    			//this ie can no longer be used.
    			ie = null;
    			GeneralUtil.takeANap(0.5);
    		}
    	}
    	catch (Exception e)
    	{
    		log.error("Unable to close browser", e);
    		throw new RuntimeException("Unable to close browser", e);
    	}
    }
    
    public static void setIEVersion() throws Exception {

		Properties p = new Properties();

		p.load(new FileInputStream(new File(IGeneralConst.propertiesFile)));			
		
	    System.setProperty(IGeneralConst.IE_Version,p.getProperty(IGeneralConst.IEVersion));
    }
    
}
