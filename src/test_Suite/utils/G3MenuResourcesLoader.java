/**
 * 
 */
package test_Suite.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author mshakshouki
 *
 */
public class G3MenuResourcesLoader {

    private static final Locale runtimeLocale = new Locale(System.getProperty("browser.ui.locale", "en_US"));
    private static final ResourceBundle bundle = ResourceBundle.getBundle("test_Suite.g3_MenuLists", runtimeLocale);

    /**
     * Returns a resource appropriate to the runtime locale, which is selected using the JVM property
     * "browser.ui.locale", which should be set to a valid locale string, such as "en_US".
     * @param resourceId_ the key to look up in the resource bundle
     * @return the value associated for the key localized to the browser.ui.locale
     */
    
    
    public static String getString(G3MenuResources resourceId_)
    {    	
        return bundle.getString(resourceId_.getKey());
    }

    public static List<String> getAllKeys()
    {
    	List<String> lst = new ArrayList<String>();
    	Enumeration<String> en = bundle.getKeys();
    	while(en.hasMoreElements())
    	{
    		lst.add(bundle.getString(en.nextElement()));
    	}
    	return lst;
    }
}
