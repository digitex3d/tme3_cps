package filesprio;

public class PreConditionError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6583920812711380118L;

	public PreConditionError(String string) {
		super("Precondition error: " + string);
	}

}
