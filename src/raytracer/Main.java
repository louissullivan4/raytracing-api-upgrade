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
		//javac src/raytracer/*.java src/raytracer/pigments/*.java src/raytracer/shapes/*.java
		// required arguments
		File outFile = new File("api01.bmp");
		int cols = 400;
		int rows = 300;


		Api apimade = new Api(cols, rows);
		apimade.createView("0,0,0", "0,0,-1", "0,1,0", 30);

		apimade.createLight("0,0,0", "0.2,0.2,0.2", "1,0,0");
		apimade.createLight("10,100,10", "1.0,1.0,1.0", "1,0,0");
		apimade.createLight("100,100,100", "1.0,1.0,1.0", "1,0,0");


		apimade.createPigment("solid", "1,0,0");
		apimade.createPigment("solid", "0,1,0");
		apimade.createPigment("solid", "0,0,1");



		apimade.createFinish("0.4", "0.6", "0", "1", "0", "0", "0");
		apimade.createFinish("0.4", "0.6", "0.7", "500", "0", "0", "0");


		apimade.createShape("sphere", 0, 0, "3,3,-15", 1);
		apimade.createShape("sphere", 1, 0, "1,0,-15", 2);
		apimade.createShape("sphere", 2, 1, "5,-5,-25", 3);
		apimade.createShape("sphere", 2, 1, "-5,0,-30", 4);



		apimade.draw(outFile);
	}
}
//package raytracer;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Scanner;
//
//public class Main {
//	private static final String USAGE = "Usage:\n"+
//			"java -cp src raytracer.Main infile bmpfile width height [-options]\n"+
//			"\n"+
//			"    where:\n"+
//			"        infile    - input file name\n"+
//			"        bmpfile   - bmp output file name\n"+
//			"        width     - image width (in pixels)\n"+
//			"        height    - image hreight (in pixels)\n"+
////			"        -test     - run in test mode (see below)\n"+
////			"        -noshadow - don't compute shadows\n"+
////			"        -noreflec - don't do reflections\n"+
////			"        -notrans  - don't do transparency\n"+
//			"        -aa       - use anti-aliasing (~4x slower)\n"+
//			"        -multi    - use multi-threading (good for large, anti-aliased images)";
////			"        -nocap    - cylinders and cones are infinite";
//
//	public static boolean DEBUG = false;
//	public static boolean ANTI_ALIAS = false;
//	public static boolean MULTI_THREAD = false;
//
//
//	private static void printUsage() {
//		System.out.println(USAGE);
//	}
//
//	public static void main(String[] args) throws IOException, InterruptedException {
//		if(args.length < 4) {
//			printUsage();
//			System.exit(0);
//		}
//
//		// required arguments
//		File inFile = new File(args[0]);
//		File outFile = new File(args[1]);
//		int cols = Integer.parseInt(args[2]);
//		int rows = Integer.parseInt(args[3]);
//
//		// optional arguments
//		int i = 0;
//		for(String arg: args) {
//			if(i++ < 4) continue;
//			if("-test".equals(arg)) {
//				DEBUG = true;
//			} else if("-aa".equals(arg)) {
//				ANTI_ALIAS = true;
//			} else if("-multi".equals(arg)) {
//				MULTI_THREAD = true;
//			} else {
//				System.out.print("Unrecognized option: '" + arg + "' ignored.");
//			}
//		}
//
//		RayTracer rayTracer = new RayTracer(cols, rows);
//		rayTracer.readScene(inFile);
//		if(DEBUG) {
//			while(true) {
//				Scanner scanner = new Scanner(System.in);
//				System.out.println("Input column and row of pixel (relative to upper left corner):");
//				int col = scanner.nextInt();
//				int row = scanner.nextInt();
//				rayTracer.getPixelColor(col, row);
//			}
//		} else {
//			rayTracer.draw(outFile);
//		}
//	}
//}
