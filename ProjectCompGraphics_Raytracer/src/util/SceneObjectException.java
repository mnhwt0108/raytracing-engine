
package util;

public class SceneObjectException extends Exception
{
	/**
	 * Constructs a SceneObjectException object with the specified message.
	 * 
	 * @param message
	 *            the exception's detail message.
	 */
	public SceneObjectException(String message)
	{
		super(message);
	}

	/**
	 * Constructs a SceneObjectException object from the specified exception.
	 * 
	 * @param exception
	 *            the exception to wrap.
	 */
	public SceneObjectException(Throwable exception)
	{
		super(exception);
	}
}
