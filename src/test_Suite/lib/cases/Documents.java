/**
 * 
 */
package test_Suite.lib.cases;

import static watij.finders.SymbolFactory.*;
import test_Suite.constants.cases.IDocumentsConst;
import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import watij.runtime.ie.IE;

import java.io.*;

/**
 * @author mshakshouki
 * 
 */
public class Documents {

	/**
	 * 
	 */
	private String baseLetter;
	private String docName;
	private String docIdent;
	private String docPrefix;
	private String docType;
	private String filePath;
	private String fileName;
	private int docIndex;
	private String description;
	private String orgName;
	private String orgAccess;

	public Documents() {

	}

	public Documents(String letter, int index, String prefix) {

		this.setBaseLetter(letter);
		this.setDocIndex(index);
		this.setDocPrefix(prefix);

		this.setDocIdent(letter + prefix
				+ IDocumentsConst.document_DocFilesIdent[index]);

		this.setDocName(letter + prefix
				+ IDocumentsConst.document_DocFilesName[index]);
		
		this.setDocType(IDocumentsConst.document_DocTypes[index]);
		
		this.setFileName(IDocumentsConst.document_DocFiles[index]);
		
		this.setFilePath(IDocumentsConst.document_Doc_FilesPath);
		
		this.setOrgName("G3");		
		this.setOrgAccess("Public");
		this.setDescription("Description");
	}
	
	//------- Class Methods ----------
	
	
	public boolean compareDownloadedFile() throws Exception {
		
		File f1 = new File(GeneralUtil.getWorkspacePath() + IGeneralConst.docsFilesPath + this.getDocIdent() + ".doc");
		File f2 = new File(GeneralUtil.getWorkspacePath() + this.getFilePath() + this.getFileName() + ".doc");
		
		return GeneralUtil.fileContentsEquals(f1, f2);
		
		/*return GeneralUtil.fileContentsEquals(GeneralUtil.getWorkspacePath() + IGeneralConst.docsFilesPath + this.getDocIdent() + ".doc",
				GeneralUtil.getWorkspacePath() + this.getFilePath()
				+ this.getFileName() + ".doc");*/
	}
	
	
	public boolean selectDocumentInM2M() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(GeneralUtil.findInM2MList(0, this.getDocIdent()))
		{
			ie.selectList(id, "/availableDocument/").select(this.getDocIdent());
			
			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
			ClicksUtil.clickButtons(IClicksConst.backBtn);
			
			return true;
		}
		
		return false;
	}
	
	public void addTrailingAndLeadingSpaces() throws Exception {
		
		String trailSpace = "  ";
		String leadingSpace = "  ";
		
		this.setDocIdent(leadingSpace + this.getDocIdent() + trailSpace);
		
		this.setDocName(leadingSpace + this.getDocName() + trailSpace);
	}
	
	public boolean doesHaveTrailingSpaces(String str) throws Exception {
		
		//return str.(" ");
		
		char retChar = str.charAt(str.length() - 1);
		
		return Character.isLetter(retChar);		
		
	}
	
	public boolean doesHaveLeadingSpaces(String str) throws Exception {
		
		//return str.(" ");
		
		char retChar = str.charAt(0);
		
		return Character.isLetter(retChar);			
	}
	
	
	//---Getter and Setter --------------

	/**
	 * @return the baseLetter
	 */
	public String getBaseLetter() {
		return baseLetter;
	}

	/**
	 * @param baseLetter
	 *            the baseLetter to set
	 */
	public void setBaseLetter(String baseLetter) {
		this.baseLetter = baseLetter;
	}

	/**
	 * @return the docName
	 */
	public String getDocName() {
		return docName;
	}

	/**
	 * @param docName
	 *            the docName to set
	 */
	public void setDocName(String docName) {
		this.docName = docName;
	}

	/**
	 * @return the docIdent
	 */
	public String getDocIdent() {
		return docIdent;
	}

	/**
	 * @param docIdent
	 *            the docIdent to set
	 */
	public void setDocIdent(String docIdent) {
		this.docIdent = docIdent;
	}

	/**
	 * @return the docPrefix
	 */
	public String getDocPrefix() {
		return docPrefix;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setDocPrefix(String docPrefix) {
		this.docPrefix = docPrefix;
	}

	/**
	 * @return the docIndex
	 */
	public int getDocIndex() {
		return docIndex;
	}

	/**
	 * @param docIndex
	 *            the docIndex to set
	 */
	public void setDocIndex(int docIndex) {
		this.docIndex = docIndex;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the orgAccess
	 */
	public String getOrgAccess() {
		return orgAccess;
	}

	/**
	 * @param orgAccess the orgAccess to set
	 */
	public void setOrgAccess(String orgAccess) {
		this.orgAccess = orgAccess;
	}

	/**
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

}
