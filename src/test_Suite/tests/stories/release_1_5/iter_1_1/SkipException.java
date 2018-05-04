/**
 * 
 */
package test_Suite.tests.stories.release_1_5.iter_1_1;


/**
 * @author mshakshouki
 *
 */
public class SkipException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5730954556791379718L;

	/**
	 * 
	 */
	public SkipException() {}

	/**
	 * @param message
	 */
	public SkipException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SkipException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SkipException(String message, Throwable cause) {
		super(message, cause);
	}

}
