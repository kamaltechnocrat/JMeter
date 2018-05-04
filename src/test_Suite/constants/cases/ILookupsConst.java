package test_Suite.constants.cases;

public interface ILookupsConst {
	
	//Lookups screen - Filter controls
	public static final String filterLookupStatus = "/0:dropdownFilterItem/";
	public static final String filterLookupName   = "/1:stringFilterItem/";
	public static final String filterLookupMode   = "1:stringFilterMode";
	public static final String filterLookupOrg    = "/2:dropdownFilterItem/";
	
	// Add/View Lookup screen
	public static final String fieldLookupIdent = "/lookupConstant/";
	public static final String fieldLookupName = "/lookupName/";
	public static final String primOrg_Drpdwn_Id = "/primaryOrganization/";
	public static final String orgAccess_Drpdwn_Id = "/organizationAccess/";
	public static final String childLookup_Drpdwn_Id = "/childLookup/";
	
	//add/View Lookup Values Page
	public static final String lookupValue_Id_TxtFld_Id = "/constant_text/";
	public static final String lookupValue_Code_TxtFld_Id = "/code_text/";
	public static final String lookupValue_Locale_TxtFld_StartId = "/:";
	public static final String lookupValue_Locale_TxtFld_EndId = "locale_text/";
	public static final String lookupValue_Active_Chkbox_Id = "/disable_checkbox/";
	public static final String lookupValue_Available_M2M_Id = "addLookupValue:lookupValueForm:availableChildLookupValues";
	public static final String lookupValue_Selected_M2M_Id = "addLookupValue:lookupValueForm:assignedChildLookupValues";	
	public static final String messageSpanId = "addLookup:lookupForm:globalMessages";

	
	public static final String valueConstantText = "lookupValueForm:constant_text";
	public static final String valueCodeText = "lookupValueForm:code_text";
	public static final String valueLocale1Text = "lookupValueForm:localizedLookupValuesTable:0:locale_text";
	public static final String valueLocale2Text = "lookupValueForm:localizedLookupValuesTable:1:locale_text";
	public static final String valueLocale3Text = "lookupValueForm:localizedLookupValuesTable:2:locale_text";
	public static final String valueLocale4Text = "lookupValueForm:localizedLookupValuesTable:3:locale_text";
	public static final String valueDisableCh   = "/disable_checkbox/";
	public static final String valueDisableCh2   = "/:disablecheckbox/";
	public static final String openLookupValuesList = "Open Lookup Values List";
	public static final String openLookupDetails    = "Open Lookup Detail";
	
	public static final String lookupPrefix = "-Gnrl-";
	public static final String lookupIdent = "Lookup";
	public static final String lookupValueIdent = "LookupValue";
	
	public static final String lookupPostChild = "-Child";
	public static final String lookupPostParent = "-Parent";
	
	public static final String lookupValueIconAlt = "Open Lookup Values List - ";
	
	public static final String addLookupValueIconAlt = "Add Lookup Value";
	
    public static final String[] currencySymbol={"$","€","¥","£","Fr.","USD","CAD","EUR","JPY","GBP","AUD","CHF"}; 
    
    public static final String lookupName= "Currencies";
    
    public static final String [] currencyLookupValues = { "INR", "IND"};
    
	public static final String lookupList_disableChbx = "lookupListForm:listLookupsTable:0:disablecheckbox";
	public static final String lookupList_enableChbx = "lookupListForm:listLookupsTable:0:enablecheckbox";
	public enum fieldsLookupTable
	{
		Active, // =0
		LookupValues,
		LookupName
		
	}
	
	public enum valueFields
	{
		Const, // = 0
		Code,
		Locale,
		DisableCheck
	}
	
	public enum CurrencySymbol
	{
		DOLLOR("$"),
		EURO("€"),
		YENYUAN("¥"),
		POUND("£"),
		SWISSFRANCE("Fr."),
		USD("USD"),
		CAD("CAD"),
		EUR("EUR"),
		JPY("JPY"),
		GBP("GBP"),
		AUD("AUD"),
		CHF("CHF");

		String value;
		private CurrencySymbol(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}

		
	}
	
	
	
	
	public static enum ELookupsTypes
	{
		generic,
		parent,
		child		
	}
}


