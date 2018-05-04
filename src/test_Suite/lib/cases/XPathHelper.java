/**
 * 
 */
package test_Suite.lib.cases;

import org.apache.xalan.xpath.XObject;
import org.apache.xalan.xpath.XPath;
import org.apache.xalan.xpath.XPathProcessor;
import org.apache.xalan.xpath.XPathProcessorImpl;
import org.apache.xalan.xpath.xml.PrefixResolver;
import org.apache.xalan.xpath.xml.PrefixResolverDefault;
import org.apache.xalan.xpath.xml.XMLParserLiaison;
import org.apache.xalan.xpath.xml.XMLParserLiaisonDefault;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author apankov
 *
 */
public class XPathHelper {
	   XMLParserLiaison xpathSupport = null;
	   XPathProcessor xpathParser    = null;
	   PrefixResolver prefixResolver = null;
	/**
	 * XPath Helper default constructor method
	 */
	public XPathHelper() {
	      xpathSupport = new XMLParserLiaisonDefault();
	      xpathParser  = new XPathProcessorImpl(xpathSupport);
	}
	/**
	 * XPath Helper class main method
	 * @param xpath
	 * @param target
	 * @return
	 * @throws SAXException
	 */
	   public NodeList processXPath(String xpath, Node target) throws SAXException 
	   {
	      prefixResolver = new PrefixResolverDefault(target);
	      // create the XPath and initialize it
	      XPath xp = new XPath();
	      xpathParser.initXPath(xp, xpath, prefixResolver);
	      // now execute the XPath select statement
	      XObject list = xp.execute(xpathSupport, target, prefixResolver);
	      
	      // return the resulting node
	      return list.nodeset();
	   }
}
