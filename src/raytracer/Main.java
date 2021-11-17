package raytracer;

import java.io.File;
import java.io.IOException;

public class Main {
//	//			"        -test     - run in test mode (see below)\n"+
//	//			"        -noshadow - don't compute shadows\n"+
//	//			"        -noreflec - don't do reflections\n"+
//	//			"        -notrans  - don't do transparency\n"+
//	private static final String USAGE = """
//			Usage:
//			java -cp src raytracer.Main infile bmpfile width height [-options]
//
//			    where:
//			        infile    - input file name
//			        bmpfile   - bmp output file name
//			        width     - image width (in pixels)
//			        height    - image hreight (in pixels)
//			        -aa       - use anti-aliasing (~4x slower)
//			        -multi    - use multi-threading (good for large, anti-aliased images)""";
////			"        -nocap    - cylinders and cones are infinite";
//
	//public static boolean DEBUG = false;
	public static boolean ANTI_ALIAS = false;
	public static boolean MULTI_THREAD = false;
//
//
//	private static void printUsage() {
//		System.out.println(USAGE);
//	}

	public static void main(String[] args) throws IOException, InterruptedException {

		//java -cp src raytracer.Main api01.bmp 400 300
		// required arguments
		File outFile = new File("api01.bmp");
		int cols = 400;
		int rows = 300;


		Api apimade = new Api(cols, rows);
		apimade.createView("0,0,0", "0,0,-1", "0,1,0", 30);
		apimade.createLight("0,0,0", "0.2,0.2,0.2", "1,0,0");
		apimade.createPigment("solid", "1,0,0");
		apimade.createFinish(0.4f, 0.6f, 0.0f, 1, 0, 0, 0);
		apimade.createShape("sphere", 0, 0, "3, 3, -15", 1);

		apimade.draw(outFile);
	}
}
