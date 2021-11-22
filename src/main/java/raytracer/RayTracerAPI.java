package raytracer;

import raytracer.pigments.Finish;
import raytracer.pigments.Pigment;
import raytracer.pigments.SolidPigment;
import raytracer.shapes.Plane;
import raytracer.shapes.Shape;
import raytracer.shapes.Sphere;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Ray tracer api.
 */
public class RayTracerAPI {

    private final int cols;
    private final int rows;
    private Camera camera;
    private final ArrayList<Light> lightsList = new ArrayList<>();
    private final ArrayList<Pigment> pigmentsList = new ArrayList<>();
    private final ArrayList<Finish> finishesList = new ArrayList<>();
    private final ArrayList<Shape> shapesList = new ArrayList<>();

    /**
     * The Shape.
     */
    Shape shape;

    /**
     * The constant MAX_RECURSION_LEVEL.
     */
    public static final int MAX_RECURSION_LEVEL = 5;
    /**
     * The constant BACKGROUND_COLOR.
     */
    public static final Color BACKGROUND_COLOR = Color.GRAY;

    /**
     * Instantiates a new Ray tracer api.
     *
     * @param height the height of the output file
     * @param width the width of the output file
     */
    public RayTracerAPI(int width, int height) {
        if (width == 0 || height == 0){
            throw new IllegalArgumentException("Null arguments are not allowed");
        }
        this.cols = width;
        this.rows = height;
    }

    /**
     * Render - calls draw to create the image from the arraylists
     *
     * @param filename the name of the output file
     */
    public void render(String filename){
        File outFile = new File(filename);
        try {
            this.draw(outFile);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Create view.
     *
     * @param eyePosition   the 3d position of the eye
     * @param centerOfScene the center of scene
     * @param upDirection   the up direction
     * @param fieldOfView   the field of view
     */
    public void createView(String eyePosition, String centerOfScene, String upDirection, double fieldOfView){
        if (eyePosition == null || centerOfScene == null || upDirection == null || fieldOfView == 0){
            throw new IllegalArgumentException("Null arguments are not allowed");
        }
        try {
            //Created the 3d position of the eye point
            List<Double> eyePositionList = createDoubleVals(eyePosition);
            double eyePositionX = eyePositionList.get(0);
            double eyePositionY = eyePositionList.get(1);
            double eyePositionZ = eyePositionList.get(2);
            Point eyePositionPoint = new Point(eyePositionX, eyePositionY, eyePositionZ);

            //Created the center of scene point
            List<Double> centerOfSceneList = createDoubleVals(centerOfScene);
            double centerOfSceneX = centerOfSceneList.get(0);
            double centerOfSceneY = centerOfSceneList.get(1);
            double centerOfSceneZ = centerOfSceneList.get(2);
            Point centerOfScenePoint = new Point(centerOfSceneX, centerOfSceneY, centerOfSceneZ);

            //Created the up direction vector
            List<Double> upDirList = createDoubleVals(upDirection);
            double upDirListX = upDirList.get(0);
            double upDirListY = upDirList.get(1);
            double upDirListZ = upDirList.get(2);
            Vector upDirListPoint = new Vector(upDirListX, upDirListY, upDirListZ);

            //Create the new camera using all these values
            camera = new Camera(eyePositionPoint, centerOfScenePoint, upDirListPoint, fieldOfView, cols, rows);
        } catch(NumberFormatException e){
            Log.error("createView() parameters are incorrect.");
            System.exit(0);
        }
    }

    /**
     * Create light.
     *
     * @param lightPosition     the 3d position of the light
     * @param color             the color
     * @param attenuationFactor the attenuation factor
     */
    public void createLight(String lightPosition, String color, String attenuationFactor){
        if (lightPosition == null || color == null || attenuationFactor == null ){
            throw new IllegalArgumentException("Null arguments are not allowed");
        }
        try{
            //Created the 3d position of the light
            List<Double> positionList = createDoubleVals(lightPosition);
            double lightPositionX = positionList.get(0);
            double lightPositionY = positionList.get(1);
            double lightPositionZ = positionList.get(2);
            Point lightPositionPoint  = new Point(lightPositionX, lightPositionY, lightPositionZ);

            //Created the rgb values
            List<Float> rgbFloatList = createFloatVals(color);
            float red = rgbFloatList.get(0);
            float green = rgbFloatList.get(1);
            float blue = rgbFloatList.get(2);
            Color rgbColor = new Color(ColorUtil.clamp(red), ColorUtil.clamp(green), ColorUtil.clamp(blue));

            //Created the attenuation factor
            List<Float> afFloatList = createFloatVals(attenuationFactor);
            float attenuationFactorA = afFloatList.get(0);
            float attenuationFactorB = afFloatList.get(1);
            float attenuationFactorC = afFloatList.get(2);

            // if the light is the first light in the list create and ambient light otherwise create a normal light
            if (lightsList.size() == 0){
                lightsList.add(new AmbientLight(lightPositionPoint, rgbColor, attenuationFactorA, attenuationFactorB, attenuationFactorC));
            }
            else{
                lightsList.add(new Light(lightPositionPoint, rgbColor, attenuationFactorA, attenuationFactorB, attenuationFactorC));
            }
        } catch(NumberFormatException e){
            Log.error("createLight() parameters are incorrect.");
            System.exit(0);
        }
    }

    /**
     * Create pigment.
     *
     * @param pigmentType the pigment type
     * @param color       the color
     */
    public void createPigment(String pigmentType, String color){
        if (pigmentType == null || color == null ){
            throw new IllegalArgumentException("Null arguments are not allowed");
        }
        // Created the rgb values
        try{
            List<Float> rgbFloatList = createFloatVals(color);
            float red = rgbFloatList.get(0);
            float green = rgbFloatList.get(1);
            float blue = rgbFloatList.get(2);
            Color rgbColor = new Color(ColorUtil.clamp(red), ColorUtil.clamp(green), ColorUtil.clamp(blue));

            // Currently only supporting type solid.
            if (Objects.equals(pigmentType, "solid")){
                pigmentsList.add(new SolidPigment(rgbColor));
            }
        } catch(NumberFormatException e){
            Log.error("createPigment() parameters are incorrect.");
            System.exit(0);
        }
    }

    /**
     * Create finish.
     *
     * @param ambient      the ambient
     * @param diffuse      the diffuse
     * @param specular     the specular
     * @param shiny        the shiny
     * @param mirror       the mirror
     * @param transparency the transparency
     * @param refraction   the refraction
     */
    public void createFinish(String ambient, String diffuse, String specular, String shiny, String mirror, String transparency, String refraction){
        if (ambient == null || diffuse == null || specular == null || shiny == null || mirror == null || transparency == null || refraction == null ){
            throw new IllegalArgumentException("Null arguments are not allowed");
        }
        try{
        //Convert each string to float
            float ambientf = Float.parseFloat(ambient);
            float diffusef = Float.parseFloat(diffuse);
            float specularf = Float.parseFloat(specular);
            float shinyf = Float.parseFloat(shiny);
            float mirrorf = Float.parseFloat(mirror);
            float transparencyf = Float.parseFloat(transparency);
            float refractionf = Float.parseFloat(refraction);
            //create finish from these values
            finishesList.add(new Finish(ambientf, diffusef, specularf, shinyf, mirrorf, transparencyf, refractionf));
        } catch(NumberFormatException e){
            Log.error("createFinish() parameters are incorrect.");
            System.exit(0);
        }
    }

    /**
     * Create shape - only Sphere and Plane supported
     *
     * @param shapeName     the shape name
     * @param pigmentNum    the pigment num
     * @param finishNum     the finish num
     * @param positionPoint the position point
     * @param shapeSize     the shape size
     */
    public void createShape(String shapeName, int pigmentNum, int finishNum, String positionPoint, String shapeSize){
        if (shapeName == null || positionPoint == null || shapeSize == null ){
            throw new IllegalArgumentException("Null arguments are not allowed");
        }
        try{
            if ("sphere".equals(shapeName)) {
                List<Double> positionList = createDoubleVals(positionPoint);
                double shapePositionX = positionList.get(0);
                double shapePositionY = positionList.get(1);
                double shapePositionZ = positionList.get(2);
                Point shapePoint = new Point(shapePositionX, shapePositionY, shapePositionZ);

                double shapeSizeDouble = Double.parseDouble(shapeSize);
                shape = new Sphere(shapePoint, shapeSizeDouble);
            } else if ("plane".equals(shapeName)) {
                List<Double> positionList = createDoubleVals(positionPoint);
                double shapePositionX = positionList.get(0);
                double shapePositionY = positionList.get(1);
                double shapePositionZ = positionList.get(2);
                float shapeSizeFloat = Float.parseFloat(shapeSize);

                shape = new Plane(shapePositionX, shapePositionY, shapePositionZ, shapeSizeFloat);
            }
            else{
                Log.error("createShape() parameters are incorrect.");
                System.exit(0);
            }
            shape.setMaterial(pigmentsList.get(pigmentNum), finishesList.get(finishNum));
            shapesList.add(shape);
        } catch(NumberFormatException | IndexOutOfBoundsException e){
            Log.error("createShape() parameters are incorrect.");
            System.exit(0);
        }
    }
    /**
     * createDoubleVals - used to convert string to list of doubles
     *
     * @return pointsDoubleList
     */
    private List<Double> createDoubleVals(String stringPoints){
        String[] pointsSplit = stringPoints.split(",");
        List<Double> pointsDoubleList = new ArrayList<>();
        for (String number : pointsSplit) {
            pointsDoubleList.add(Double.parseDouble(number.trim()));
        }
        return pointsDoubleList;
    }
    /**
     * createFloatVals - used to convert string to list of floats
     *
     * @return valFloatList
     */
    private List<Float> createFloatVals(String stringPoints) {
        String[] strSplit = stringPoints.split(",");
        List<Float> valFloatList = new ArrayList<>();
        for (String number : strSplit) {
            valFloatList.add(Float.parseFloat(number.trim()));
        }
        return valFloatList;
    }

    private Color shade(RayHit hit, int depth) {
        Color color = Color.BLACK;

        // ambient light source
        Light light = lightsList.get(0);
        if(light != null && hit.shape.finish.amb > 0) {
            color = ColorUtil.blend(color, ColorUtil.intensify(hit.shape.getColor(hit.point), light.getColor(hit, null)));
        }

        for(int i = 1;i < lightsList.size();i++) {
			Log.debug("Checking light " + i + ":");
            light = lightsList.get(i);
            Vector lightRayVec = new Vector(hit.point, light.location);
            Ray lightRay = new Ray(hit.point, lightRayVec);
            lightRay.t = lightRayVec.magnitude();

			Log.debug("  light ray = " + lightRay);
            RayHit obstruction = findHit(lightRay);
            if(obstruction == null) {
				Log.debug("  Light is visible:");

                Color c = light.getColor(hit, lightRay);
				Log.debug("  final color   = " + c);
                color = ColorUtil.blend(color, c);
            }
        }

        if(depth <= MAX_RECURSION_LEVEL) {
            if(hit.shape.finish.isReflective()) {
                color = ColorUtil.blend(color, ColorUtil.intensify(trace(hit.getReflectionRay(), depth+1), hit.shape.finish.refl));
            }

            if(hit.shape.finish.isTransmittive()) {
                color = ColorUtil.blend(color, ColorUtil.intensify(trace(hit.getTransmissionRay(), depth+1), hit.shape.finish.trans));
            }
        }

        return color;
    }

    private RayHit findHit(Ray ray) {
        RayHit hit = null;

        for(Shape shape: shapesList) {
            RayHit h = shape.intersect(ray);
			Log.debug("    Testing object " + shape + ": " + (h == null?"missed":"hit"));
            if(h != null && h.t < ray.t) {
				Log.debug("      hit at t=" + h.t + ". point=" + h.point);
                hit = h;
                ray.t = h.t;
            }
        }

        return hit;
    }

    private Color trace(Ray ray, int depth) {
		Log.debug("Tracing ray " + ray);

        RayHit hit = findHit(ray);

        if(hit != null) {
            return shade(hit, depth);
        }
        // missed everything return background color
        return BACKGROUND_COLOR;
    }


    private void draw(File outFile) throws IOException, InterruptedException {
        final BufferedImage image = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);

        long start = System.currentTimeMillis();

        if(Main.MULTI_THREAD) {
            final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
            final AtomicInteger remaining = new AtomicInteger(rows * cols);
            for(int r = 0;r < rows; r++) {
                for(int c = 0;c < cols; c++) {
                    final int cc = c;
                    final int rr = r;
                    executor.execute(() -> image.setRGB(cc, rr, getPixelColor(cc, rr).getRGB()));
                }
            }

            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.MINUTES);
        } else {
            for(int r = 0;r < rows; r++) {
                if(r % 5 == 0) Log.info((rows - r) + " rows left to trace.");
                for(int c = 0;c < cols; c++) {
                    image.setRGB(c, r, getPixelColor(c, r).getRGB());
                }
            }
        }

        Log.info("Finished in: " + (System.currentTimeMillis()-start) + "ms");

        ImageIO.write(image, "bmp", outFile);
    }


    /**
     * Gets pixel color.
     *
     * @param col the col
     * @param row the row
     * @return the pixel color
     */
    public Color getPixelColor(int col, int row) {
        int bmpRow = rows-1 - row;
		Log.debug("Tracing ray (col=" + col + ", row=" + row + ")");
		Log.debug("  [Note: In bmp format this is row " + bmpRow + "]");

        if(Main.ANTI_ALIAS) {
            Ray ray = camera.getRay(col, bmpRow, 0, 0);
            Color c1 = trace(ray, 0);
            ray = camera.getRay(col, bmpRow, .5, 0);
            Color c2 = trace(ray, 0);
            ray = camera.getRay(col, bmpRow, 0, .5);
            Color c3 = trace(ray, 0);
            ray = camera.getRay(col, bmpRow, .5, .5);
            Color c4 = trace(ray, 0);

            return ColorUtil.average(c1, c2, c3, c4);
        } else {
            Ray ray = camera.getRay(col, bmpRow);
            return trace(ray, 0);
        }
    }
}
