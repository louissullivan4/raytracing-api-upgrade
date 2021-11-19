package raytracer;

/**
 * The type Log.
 */
public class Log {
    /**
     * Error.
     *
     * @param msg the msg
     */
    public static void error(String msg) {
		System.err.println("ERROR: " + msg);
	}

    /**
     * Warn.
     *
     * @param msg the msg
     */
    public static void warn(String msg) {
		System.out.println("Warning: " + msg);
	}

    /**
     * Info.
     *
     * @param msg the msg
     */
    public static void info(String msg) {
		System.out.println(msg);
	}

    /**
     * Debug.
     *
     * @param msg the msg
     */
    public static void debug(String msg) {
		if(Main.DEBUG) System.out.println(msg);
	}
}
