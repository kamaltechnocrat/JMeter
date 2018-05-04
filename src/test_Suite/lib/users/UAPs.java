/*
 * UAPHelper.java
 *
 * Created on February 1, 2007, 500 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package test_Suite.lib.users;

/**
 *
 * @author mshakshouki
 */


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UAPs {    
     
	private static Log log = LogFactory.getLog(UAPs.class);
     
     public UAPs() {
     }
     
     public Iterator<String> initForthLevelUAP(String[][] forthLevelUAP) throws Exception {
    	 
    	 
    	 List<String> lis1 = new ArrayList<String>();
    	 
    	 for(int x=0; x<forthLevelUAP.length; x++)
    	 {
    		 for(int y=0; y<forthLevelUAP[x].length; y++)
    		 {
    			 lis1.add(forthLevelUAP[x][y]);
    		 }
    	 }    	 
    	 
    	 return lis1.iterator();
     }
     
     public Iterator<String> initThirdLevelUAP(String[][][] thirdLevelUAP) throws Exception {
    	 
    	 List<String> lis1 = new ArrayList<String>();
    	 
    	 for(int x=0; x<thirdLevelUAP.length; x++)
    	 {
    		 for(int y=0; y<thirdLevelUAP[x].length; y++)
    		 {
    			 for(int z=0; z<thirdLevelUAP[x][y].length; z++)
    			 {
    				 lis1.add(thirdLevelUAP[x][y][z]);
    			 }
    			 
    		 }
    	 }    	 
    	 
    	 return lis1.iterator();
     }
     
     public Iterator<String> initSecondLevelUAP(String[][][][] secondLevelUAP) throws Exception {
    	 
    	 List<String> lis1 = new ArrayList<String>();
    	 
    	 for(int x=0; x<secondLevelUAP.length; x++)
    	 {
    		 for(int y=0; y<secondLevelUAP[x].length; y++)
    		 {
    			 for(int z=0; z<secondLevelUAP[x][y].length; z++)
    			 {
    				 for(int i=0; i<secondLevelUAP[x][y][z].length; i++)
    				 {
    					 lis1.add(secondLevelUAP[x][y][z][i]);
    				 }
    				 
    			 }
    			 
    		 }
    	 }    	 
    	 
    	 return lis1.iterator();
     }
     
     public ArrayList<String> init2SecondLevelUAP(String[][][][] secondLevelUAP) throws Exception {
    	 
    	 ArrayList<String> lis1 = new ArrayList<String>();
    	 
    	 for(int x=0; x<secondLevelUAP.length; x++)
    	 {
    		 for(int y=0; y<secondLevelUAP[x].length; y++)
    		 {
    			 for(int z=0; z<secondLevelUAP[x][y].length; z++)
    			 {
    				 for(int i=0; i<secondLevelUAP[x][y][z].length; i++)
    				 {
    					 lis1.add(secondLevelUAP[x][y][z][i]);
    				 }
    				 
    			 }
    			 
    		 }
    	 }    	 
    	 
    	 return lis1;
     }
     
     public ArrayList<String[]> initializeUAPs(String[][][][] UAPs) throws Exception {
    	 
    	 ArrayList<String[]> list1 = new ArrayList<String[]>();
    	 
    	 log.debug(UAPs.length);
    	 
    	for (String[][][] strings : UAPs) {
    		
    		for (String[][] strings2 : strings) {
    			
    			for (String[] strings3 : strings2) {
    				
    				if(strings3.length > 0)
    				{
    					list1.add(strings3);
    				}
    				
				}
				
			}
			
    		
		} 
    	 
    	 return list1;
     }
     
     public Iterator<String> initFirstLevelUAP(String[][][][][] firstLevelUAP) throws Exception {
    	 
    	 List<String> lis1 = new ArrayList<String>();
    	 
    	 for(int x=0; x<firstLevelUAP.length; x++)
    	 {
    		 for(int y=0; y<firstLevelUAP[x].length; y++)
    		 {
    			 for(int z=0; z<firstLevelUAP[x][y].length; z++)
    			 {
    				 for(int i=0; i<firstLevelUAP[x][y][z].length; i++)
    				 {
    					 for(int j=0; j<firstLevelUAP[x][y][z][i].length; j++)
    					 {
    						 lis1.add(firstLevelUAP[x][y][z][i][j]);
    					 }
    					 
    				 }
    				 
    			 }
    			 
    		 }
    	 }    	 
    	 
    	 return lis1.iterator();
     }
    
}
