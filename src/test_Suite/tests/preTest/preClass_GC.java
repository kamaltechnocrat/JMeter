/**
 * 
 */
package test_Suite.tests.preTest;

import org.testng.annotations.Test;

/**
 * @author mshakshouki
 * 
 * to run garbage collection
 *
 */
public class preClass_GC {
	
	@Test( groups ={ "PreClassNG" } ) 
	public void collectGarbage() throws Exception {
		
		System.gc();
	}

}
