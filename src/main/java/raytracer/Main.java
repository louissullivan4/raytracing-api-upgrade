package raytracer;

import java.io.File;
import java.io.IOException;

/**
 * The type Main.
 */
public class Main {
	private static final String USAGE = "Usage:\n"+
			"java -cp src raytracer.Main infile bmpfile width height [-options]\n"+
			"\n"+
			"    where:\n"+
			"        bmpfile   - bmp output file name\n"+
			"        width     - image width (in pixels)\n"+
			"        height    - image hreight (in pixels)\n"+
			"        -aa       - use anti-aliasing (~4x slower)\n"+
			"        -multi    - use multi-threading (good for large, anti-aliased images)";

	/**
	 * The constant DEBUG.
	 */
	public static boolean DEBUG = false;
	/**
	 * The constant ANTI_ALIAS.
	 */
	public static boolean ANTI_ALIAS = false;
	/**
	 * The constant MULTI_THREAD.
	 */
	public static boolean MULTI_THREAD = false;

	private static void printUsage() {
		System.out.println(USAGE);
	}

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 * @throws IOException          the io exception
	 * @throws InterruptedException the interrupted exception
	 */
	public static void main(String[] args) throws IOException, InterruptedException {

		if(args.length < 3) {
			printUsage();
			System.exit(0);
		}
		//java -cp src raytracer.Main api03.bmp 400 300
		//javac src/raytracer/*.java src/raytracer/pigments/*.java src/raytracer/shapes/*.java
		// required arguments
		File outFile = new File(args[0]);
		int cols = Integer.parseInt(args[1]);
		int rows = Integer.parseInt(args[2]);

		//test02 to API
		RayTracerAPI apimade = new RayTracerAPI(cols, rows);
		apimade.createView("-5,-8,10", "0,0,0", "0,0,1", 40);

		apimade.createLight("0,0,0", "1,1,1", "1,0,0.00");
		apimade.createLight("0,-4,8", "0.6,0,0", "0,0,0.01");
		apimade.createLight("3.464,2,8", "0,0.6,0", "0,0,0.01");
		apimade.createLight("-3.464,2,9", "0,0,0.6", "0,0,0.01");

		apimade.createPigment("solid", "0.8,0.8,0.8");
		apimade.createPigment("solid", "0.8,0.8,0.8");

		apimade.createFinish("0.2", "0.9", "1.0", "1000", "0", "0", "0");
		apimade.createFinish("0.2", "0.6", "1.0", "1000", "0", "0", "0");

		apimade.createShape("sphere", 0, 1, "0,0,1", "2");
		apimade.createShape("plane", 1, 0, "0,0,1", "2.5");

		apimade.render(outFile);



//      test01 to API
//		File outFile = new File("api01.bmp");
//		int cols = 400;
//		int rows = 300;
//
//
//		RayTracerAPI apimade = new RayTracerAPI(cols, rows);
//		apimade.createView("0,0,0", "0,0,-1", "0,1,0", 30);
//
//		apimade.createLight("0,0,0", "0.2,0.2,0.2", "1,0,0");
//		apimade.createLight("10,100,10", "1.0,1.0,1.0", "1,0,0");
//		apimade.createLight("100,100,100", "1.0,1.0,1.0", "1,0,0");
//
//
//		apimade.createPigment("solid", "1,0,0");
//		apimade.createPigment("solid", "0,1,0");
//		apimade.createPigment("solid", "0,0,1");
//
//
//
//		apimade.createFinish("0.4", "0.6", "0", "1", "0", "0", "0");
//		apimade.createFinish("0.4", "0.6", "0.7", "500", "0", "0", "0");
//
//
//		apimade.createShape("sphere", 0, 0, "3,3,-15", "1");
//		apimade.createShape("sphere", 1, 0, "1,0,-15", "2");
//		apimade.createShape("sphere", 2, 1, "5,-5,-25", "3");
//		apimade.createShape("sphere", 2, 1, "-5,0,-30", "4");
//
//
//
//		apimade.draw(outFile);
	}
}
