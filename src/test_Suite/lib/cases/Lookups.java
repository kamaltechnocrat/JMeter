/**
 * 
 */
package test_Suite.lib.cases;

import static watij.finders.SymbolFactory.*;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import watij.elements.Div;
import watij.runtime.ie.IE;
import test_Suite.constants.ui.*;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.cases.LookupUtil;
import test_Suite.utils.ui.*;
import test_Suite.constants.cases.*;

/**
 * @author apankov
 *  Lookups class to handle Lookups screens and lookup values screens
 */
public class Lookups implements ILookupsConst
{
	private static Log log = LogFactory.getLog(Lookups.class);
	private IE ie;
	private boolean newLookup;
	private String lookupIdent;
	private String lookupFullIdent;
	
	private String lookupCode; //used for values
	private String lookupFullCode;
	
	private String lookupName; //for values used as loclized name
	private String lookupFullName;
	
	private String lookupPrimeOrg;
	private String lookupOrgAccess;
	
	private String [][] lookupEntries;
	private String childLookup;
	
	private String lookupLetter;
	private String lookupValueLetter;
	private String lookupPrefix;
	private String lookupPostFix;
	
	private Lookups child = null;
	private Lookups parent = null;
	
	private List<Lookups> values;
	private List<Lookups> childLookupValues;
	
	private LinkedHashMap<String, Lookups> lhm;
	private Set<Map.Entry<String, Lookups>> set;
	
	
	boolean retValue;
	

	/**
	 * Default constructor 
	 * @throws Exception
	 */
	public Lookups()
	{ 
	}
	
	public Lookups(ArrayList<String> lookupParams, boolean isNewLookup, ELookupsTypes lookupType)
	{
		try {
			this.setLookupIdent(lookupParams.get(0));
			this.setLookupPrefix(lookupParams.get(1));
			this.setLookupPrimeOrg(lookupParams.get(2));
			this.setLookupOrgAccess(lookupParams.get(3));
			this.setNewLookup(isNewLookup);
			
			this.lhm = new LinkedHashMap<String, Lookups>();
			
			this.initializeLookup();
			
		} catch (Exception e) {
			Assert.fail("Unexpected error or exception: " + e.getMessage());
		}
		
	}
	
	/**
	 * @param lookupParams
	 * @param lookup
	 * @param isNewLookup
	 * @param lookupType
	 */
	public Lookups(ArrayList<String> lookupParams,Lookups lookup, boolean isNewLookup, ELookupsTypes lookupType)
	{
		try {
			this.setLookupName(lookupParams.get(0));
			this.setLookupFullName(lookupParams.get(0));
			this.setLookupPrefix(lookupParams.get(1).toUpperCase().replace(" ", "_"));
			this.setLookupFullCode(this.getLookupPrefix() + "_" + this.getLookupName().toUpperCase());
			this.setLookupFullIdent(this.getLookupFullCode());
			this.setLookupPrimeOrg(lookupParams.get(2));
			this.setLookupOrgAccess(lookupParams.get(3));
			this.setNewLookup(isNewLookup);
			
			lookup.lhm.put(this.getLookupFullName(), this);
			
		} catch (Exception e) {
			Assert.fail("Unexpected error or exception: " + e.getMessage());
		}
		
	}
	
	/**
	 * Other Constructor for Lookup
	 * @param ident
	 * @param isNewLookup
	 * @param isParent
	 */
	public Lookups(String ident, boolean isNewLookup, boolean isParent)
	{ 
		try {
			this.setLookupIdent(ident);
			this.setLookupPrefix(ILookupsConst.lookupPrefix);
			this.setLookupPrimeOrg("G3");
			this.setLookupOrgAccess("Public");
			this.setNewLookup(isNewLookup);
			
			this.lhm = new LinkedHashMap<String, Lookups>();
			
			if(isParent)
			{

				this.setLookupPostFix(lookupPostParent);
			}
			else
			{

				this.setLookupPostFix(lookupPostChild);
			}
			
			this.initializeLookup();
			
		} catch (Exception e) {
			Assert.fail("Unexpected error or exception: " + e.getMessage());
		}		
	}
	
	
	
	/**
	 * Constructor for Lookup Value
	 * @param valLetter
	 * @param isChild
	 * @param lookup
	 */
	public Lookups(String valLetter, boolean isChild, Lookups lookup, String[] childValues)
	{ 
		try {
			this.setLookupValueLetter(lookup.getLookupLetter());
			this.setLookupIdent(ILookupsConst.lookupValueIdent);
			this.setLookupPrefix(ILookupsConst.lookupPrefix);			
			this.setLookupLetter(valLetter);
			
			if(isChild)
			{
				this.setLookupPostFix(lookupPostChild);
			}
			else
			{
				this.setLookupPostFix(lookupPostParent);
			}
			
			this.initializeLookupValue();
			
			lookup.lhm.put(this.getLookupFullName(), this);
			
			if(childValues != null)
			{
				this.lhm = new LinkedHashMap<String, Lookups>();
				
				for(String childVal : childValues)
				{
					this.lhm.put(childVal, lookup.getChild().getLhm().get(childVal));
				}
			}
			
		} catch (Exception e) {
			Assert.fail("Unexpected error or exception: " + e.getMessage());
		}
		
	}
	
	//class methods
	

	
	public void initializeLookup() throws Exception {
		
		try {
			
			IE ie = IEUtil.getActiveIE();
			
			ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk);
			
			this.setLookupName(this.getLookupIdent());
			
			String tempName;
			
			if(this.isNewLookup())
			{
				tempName = this.getLookupPrefix() + this.getLookupIdent() + this.getLookupPostFix();
				
				tempName = tempName.replace("-", " ");
				this.setLookupLetter(LookupUtil.getNewBaseLetter(tempName));
				
				this.setLookupFullIdent(this.getLookupLetter() + this.getLookupPrefix() + this.getLookupIdent() + this.getLookupPostFix());
				this.setLookupFullCode(this.getLookupFullIdent());
				this.setLookupFullName(this.getLookupFullIdent().replace("-", " "));
			}
			else
			{
				FiltersUtil.filterListByLabel(IFiltersConst.administration_LookupName_Lbl, this.getLookupName(), IFiltersConst.exact);
				
				ClicksUtil.clickLinks(this.getLookupName());
				
				this.setLookupFullName(this.getLookupName());				
				this.setLookupFullIdent(ie.textField(id, fieldLookupIdent).getContents());
				this.setLookupPrimeOrg(GeneralUtil.getSelectedItemValueInDropdwonById(primOrg_Drpdwn_Id));
				this.setLookupOrgAccess(GeneralUtil.getSelectedItemValueInDropdwonById(orgAccess_Drpdwn_Id));
				
				ClicksUtil.clickButtons(IClicksConst.lookup_BackToLookupsListBtn);
				
				//this.initializeExistingLookupValue();
			}
			
		} catch (Exception e) {
			Assert.fail("Unexpected error or exception: " + e.getMessage());
		}
	}
	
	public void addToLinkedMap() throws Exception {
		
		try {
			
		} catch (Exception e) {
			Assert.fail("Unexpected error or exception: " + e.getMessage());
		}
	}
	
	public void initializeExistingLookupValue() throws Exception {
		try {
			
			IE ie = IEUtil.getActiveIE();
			
			ClicksUtil.clickImageByAlt(openLookupValuesList + IGeneralConst.dASH + this.getLookupFullName());
			
			int valuesCount = TablesUtil.howManyEntriesInTable(ITablesConst.lookupValueTableId);
			
			Div tDiv = TablesUtil.tableDiv();
			
			if(valuesCount > 0)
			{
				
				this.lhm = new LinkedHashMap<String, Lookups>();
				for(int x=0; x < valuesCount; x++)
				{	
					String tmp = tDiv.body(id, ITablesConst.lookupValueTableId).row(x).cell(1).innerText();
					
					Lookups lookup = new Lookups();
					lookup.setLookupFullName(tmp);
					
					ClicksUtil.clickLinks(tmp);
					
					lookup.setLookupFullCode(ie.textField(id,valueConstantText).getContents());
					lookup.setLookupFullIdent(ie.textField(id,valueCodeText).getContents());
					
					ClicksUtil.clickButtons(IClicksConst.lookup_BackToLookupValuesListBtn);					
					
					this.lhm.put(tmp, lookup);					
				}	
			}
			
			ClicksUtil.clickButtons(IClicksConst.lookup_BackToLookupsListBtn);
			
			return;
			
		} catch (Exception e) {
			Assert.fail("Unexpected error or exception: " + e.getMessage());
		}
	}
	
	public void initializeLookupValue() throws Exception {
		
		try {			
			
			String tempName = this.getLookupLetter() + this.getLookupPrefix() + this.getLookupIdent() + this.getLookupPostFix() + "-" + this.getLookupValueLetter();
			
			this.setLookupFullIdent(tempName);
			this.setLookupFullCode(this.getLookupFullIdent());
			
			tempName = tempName.replace("-", " ");
			this.setLookupFullName(tempName);		
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected error or exception: " + e.getMessage());
		}
	}
	
	public void addNewLookupAndValues() throws Exception {
		
		try {
			
			IE ie = IEUtil.getActiveIE();
			
			if(this.isNewLookup())
			{
				ClicksUtil.clickImage(IClicksConst.newImg);
				
				ie.textField(id, fieldLookupIdent).set(this.getLookupFullIdent());
				ie.textField(id, fieldLookupName).set(this.getLookupFullName());
				ie.selectList(id, primOrg_Drpdwn_Id).select(this.getLookupPrimeOrg());
				ie.selectList(id, orgAccess_Drpdwn_Id).select(this.getLookupOrgAccess());
				
				if(this.getChild() != null)
				{
					ie.selectList(id, childLookup_Drpdwn_Id).select(this.child.getLookupFullName());
				}
				
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
				ClicksUtil.clickButtons(IClicksConst.lookup_BackToLookupsListBtn);
			}			
			
			if(this.getLhm() != null)
			{
				this.setSet(this.getLhm().entrySet());
				
				ClicksUtil.clickImageByAlt(openLookupValuesList + IGeneralConst.dASH + this.getLookupFullName());
				
				ClicksUtil.clickImage(IClicksConst.newImg);
				
				for (Map.Entry<String, Lookups> entry : this.set) {
					
					ie.textField(id,valueConstantText).set(entry.getValue().getLookupFullIdent());
					ie.textField(id, valueCodeText).set(entry.getValue().getLookupFullCode());
					
					ie.textField(id,valueLocale1Text).set(entry.getValue().getLookupFullName());
					
					if(ie.textField(id,valueLocale2Text).exists())
					{
						ie.textField(id,valueLocale2Text).set(entry.getValue().getLookupFullName());
					}
					
					if(ie.textField(id, valueLocale3Text).exists())
					{
						ie.textField(id,valueLocale3Text).set(entry.getValue().getLookupFullName());
					}
					
					if(ie.textField(id, valueLocale4Text).exists())
					{
						ie.textField(id,valueLocale4Text).set(entry.getValue().getLookupFullName());
					}
					
					selectChildLookupsValues(entry.getValue());
					
					ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
				}
				
				ClicksUtil.clickButtons(IClicksConst.lookup_BackToLookupValuesListBtn);
				ClicksUtil.clickButtons(IClicksConst.lookup_BackToLookupsListBtn);
			}
			
			
		} catch (Exception e) {
			Assert.fail("Unexpected error or exception: " + e.getMessage());
		}
	}
	
	public void selectChildLookupsValues(Lookups val) throws Exception {
		
		try {
			
			IE ie = IEUtil.getActiveIE();
			
			if(val.getLhm() != null)
			{
				val.setSet(val.getLhm().entrySet());
				
				for(Map.Entry<String, Lookups> entry : val.set)
				{
					ie.selectList(id,lookupValue_Available_M2M_Id).select(entry.getValue().getLookupFullIdent());
					ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
				}
				
				ClicksUtil.clickButtons(IClicksConst.saveBtn);
			}
			
			return;			
			
		} catch (Exception e) {
			Assert.fail("Unexpected error or exception: " + e.getMessage());
		}
	}
	
	public void createNewLookup() throws Exception
	{
		ie = IEUtil.getActiveIE();
		ClicksUtil.clickImage(IClicksConst.newImg);

		if(lookupIdent != null)
			ie.textField(id, fieldLookupIdent).set(lookupIdent);
		ie.textField(id, fieldLookupName).set(lookupName);
		ie.selectList(id, primOrg_Drpdwn_Id).select(lookupPrimeOrg);
		if(lookupOrgAccess != null)
			ie.selectList(id, orgAccess_Drpdwn_Id).select(lookupOrgAccess);
		if(childLookup != null)
			ie.selectList(id, childLookup_Drpdwn_Id).select(childLookup);
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		if(ie.span(id, messageSpanId).exists())
			log.info("Either Lookup Constant or Lookup Name Already Exist");
		if(lookupEntries != null && lookupEntries.length > 0)
			addLookupValues(lookupEntries);

	}
	
	private void addLookupValues(String[][] lookupEntries) throws Exception
	{
		try
		{
			int numberOfEntries = lookupEntries.length;
			ClicksUtil.clickButtons(IClicksConst.backBtn);
			String lookupSearch = "/" + lookupName + "/";
			if(ie.image(alt, lookupSearch).exists())//Find Image element by portion of ALT TAG
			{
				log.info("Lookup exists: " + lookupName);
				ClicksUtil.clickImageByAlt(openLookupValuesList + IGeneralConst.dASH + lookupName);
				ClicksUtil.clickImage(IClicksConst.newImg);
				for(int i=0; i<numberOfEntries; i++)
				{
					ie.textField(id, valueConstantText).set(lookupEntries[i][valueFields.Const.ordinal()]);
					
					ie.textField(id, valueCodeText).set(lookupEntries[i][valueFields.Code.ordinal()]);
					
					ie.textField(id, valueLocale1Text).set(lookupEntries[i][valueFields.Locale.ordinal()]);
					
					if(ie.textField(id, valueLocale2Text).exists())
					{
						ie.textField(id, valueLocale2Text).set(lookupEntries[i][valueFields.Locale.ordinal()]);
					}					
					
					if(ie.textField(id, valueLocale3Text).exists())
					{
						ie.textField(id, valueLocale3Text).set(lookupEntries[i][valueFields.Locale.ordinal()]);
					}					
					
					if(ie.textField(id, valueLocale4Text).exists())
					{
						ie.textField(id, valueLocale4Text).set(lookupEntries[i][valueFields.Locale.ordinal()]);
					}
						
					if(Integer.valueOf(lookupEntries[i][valueFields.DisableCheck.ordinal()]) == 1)
					{
						ie.checkbox(id, valueDisableCh).set();
					}						
					else
					{
						ie.checkbox(id, valueDisableCh).clear();
					}
						
					if(i < numberOfEntries-1)
					{
						ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
					}						
					else
					{
						ClicksUtil.clickButtons(IClicksConst.saveBtn);
					}
						
				}
			}
			else
				log.info("Lookup : " + lookupName + " does not exist in the List. Unable to add Lookup Values.");
		}
		catch(Exception ex)
		{
			log.debug("ERROR in addLookupValues(): " + ex.getMessage());
		}
		
	}
	
	public boolean openLookup(String lookUpName, int details) throws Exception {
		retValue = false;
		
		ClicksUtil.clickLinks(IClicksConst.openLookupsListsLnk);
		
		
		
		
		return retValue;
	}
	
	// Setters and getters
	
	/**
	 * @return the childLookup
	 */
	public String getChildLookup() {
		return childLookup;
	}

	/**
	 * @param childLookup the childLookup to set
	 */
	public void setChildLookup(String childLookup) {
		this.childLookup = childLookup;
	}

	public String getLookupIdent() {
		return lookupIdent;
	}

	public void setLookupIdent(String lookupIdent) {
		this.lookupIdent = lookupIdent;
	}	
	
	public String getLookupName() {
		return lookupName;
	}

	public void setLookupName(String lookupName) {
		this.lookupName = lookupName;
	}
	
	public String getLookupPrimeOrg() {
		return lookupPrimeOrg;
	}
	
	/**
	 * @param lookupPrimeOrg the lookupPrimeOrg to set
	 */
	public void setLookupPrimeOrg(String lookupPrimeOrg) {
		this.lookupPrimeOrg = lookupPrimeOrg;
	}
	
	public String getLookupOrgAccess() {
		return lookupOrgAccess;
	}

	public void setLookupOrgAccess(String lookupOrgAccess) {
		this.lookupOrgAccess = lookupOrgAccess;
	}
	
	public String [][] getLookupEntries() {
		return lookupEntries;
	}

	public void setLookupEntries(String [][] lookupEntries) {
		this.lookupEntries = lookupEntries;
	}

	/**
	 * @return the lookupFullIdent
	 */
	public String getLookupFullIdent() {
		return lookupFullIdent;
	}

	/**
	 * @param lookupFullIdent the lookupFullIdent to set
	 */
	public void setLookupFullIdent(String lookupFullIdent) {
		this.lookupFullIdent = lookupFullIdent;
	}

	/**
	 * @return the lookupCode
	 */
	public String getLookupCode() {
		return lookupCode;
	}

	/**
	 * @param lookupCode the lookupCode to set
	 */
	public void setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
	}

	/**
	 * @return the lookupFullCode
	 */
	public String getLookupFullCode() {
		return lookupFullCode;
	}

	/**
	 * @param lookupFullCode the lookupFullCode to set
	 */
	public void setLookupFullCode(String lookupFullCode) {
		this.lookupFullCode = lookupFullCode;
	}

	/**
	 * @return the lookupFullName
	 */
	public String getLookupFullName() {
		return lookupFullName;
	}

	/**
	 * @param lookupFullName the lookupFullName to set
	 */
	public void setLookupFullName(String lookupFullName) {
		this.lookupFullName = lookupFullName;
	}

	/**
	 * @return the lookupLetter
	 */
	public String getLookupLetter() {
		return lookupLetter;
	}

	/**
	 * @param lookupLetter the lookupLetter to set
	 */
	public void setLookupLetter(String lookupLetter) {
		this.lookupLetter = lookupLetter;
	}

	/**
	 * @return the lookupValueLetter
	 */
	public String getLookupValueLetter() {
		return lookupValueLetter;
	}

	/**
	 * @param lookupValueLetter the lookupValueLetter to set
	 */
	public void setLookupValueLetter(String lookupValueLetter) {
		this.lookupValueLetter = lookupValueLetter;
	}

	/**
	 * @return the lookupPrefix
	 */
	public String getLookupPrefix() {
		return lookupPrefix;
	}

	/**
	 * @param lookupPrefix the lookupPrefix to set
	 */
	public void setLookupPrefix(String lookupPrefix) {
		this.lookupPrefix = lookupPrefix;
	}

	/**
	 * @return the lookupPostFix
	 */
	public String getLookupPostFix() {
		return lookupPostFix;
	}

	/**
	 * @param lookupPostFix the lookupPostFix to set
	 */
	public void setLookupPostFix(String lookupPostFix) {
		this.lookupPostFix = lookupPostFix;
	}

	/**
	 * @return the child
	 */
	public Lookups getChild() {
		return child;
	}

	/**
	 * @param child the child to set
	 */
	public void setChild(Lookups child) {
		this.child = child;
	}

	/**
	 * @return the parent
	 */
	public Lookups getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Lookups parent) {
		this.parent = parent;
	}

	/**
	 * @return the values
	 */
	public List<Lookups> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<Lookups> values) {
		this.values = values;
	}

	/**
	 * @return the newLookup
	 */
	public boolean isNewLookup() {
		return newLookup;
	}

	/**
	 * @param newLookup the newLookup to set
	 */
	public void setNewLookup(boolean newLookup) {
		this.newLookup = newLookup;
	}

	/**
	 * @return the childLookupValues
	 */
	public List<Lookups> getChildLookupValues() {
		return childLookupValues;
	}

	/**
	 * @param childLookupValues the childLookupValues to set
	 */
	public void setChildLookupValues(List<Lookups> childLookupValues) {
		this.childLookupValues = childLookupValues;
	}

	/**
	 * @return the lhm
	 */
	public LinkedHashMap<String, Lookups> getLhm() {
		return lhm;
	}

	/**
	 * @param lhm the lhm to set
	 */
	public void setLhm(LinkedHashMap<String, Lookups> lhm) {
		this.lhm = lhm;
	}

	/**
	 * @return the set
	 */
	public Set<Map.Entry<String, Lookups>> getSet() {
		return set;
	}

	/**
	 * @param set the set to set
	 */
	public void setSet(Set<Map.Entry<String, Lookups>> set) {
		this.set = set;
	}

}
