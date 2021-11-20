package raytracer;

import java.io.File;
import java.io.IOException;

/**
 * The type Main.
 */
public class Main {
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

	/**
	 * The entry point of application.
	 *
	 *
	 * @throws IOException          the io exception
	 * @throws InterruptedException the interrupted exception
	 */
	public static void main(String[] args) throws IOException, InterruptedException {


		//HOW TO BUILD AND RUN FILE
		//javac src/main/java/raytracer/*.java src/main/java/raytracer/pigments/*.java src/main/java/raytracer/shapes/*java
		//java -cp src/main/java/ raytracer.Main api01.bmp 400 300

		//test02 to API
		RayTracerAPI apimade = new RayTracerAPI(400, 300);
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

		apimade.render("api01.bmp");



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
//		apimade.draw("api02.bmp", 400, 300);
	}
}
