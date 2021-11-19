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


public class Api {

    private int cols, rows;
    private Camera camera;
    private final ArrayList<Light> lightsList = new ArrayList<>();
    private final ArrayList<Pigment> pigmentsList = new ArrayList<>();
    private final ArrayList<Finish> finishesList = new ArrayList<>();
    private final ArrayList<Shape> shapesList = new ArrayList<>();

    Shape shape;

    public static final int MAX_RECURSION_LEVEL = 5;
    public static final Color BACKGROUND_COLOR = Color.GRAY;

    public Api(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
    }

    public void render(File outFile){
        try {
            this.draw(outFile);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    void createView(String eyePosition, String centerOfScene, String upDirection, double fieldOfView){
        //Created the 3d position of the eye point
        List<Double> eyePositionList = createDoubleVals(eyePosition);
        double eyePositionX = eyePositionList.get(0);
        double eyePositionY = eyePositionList.get(1);
        double eyePositionZ = eyePositionList.get(2);
        Point eyePositionPoint  = new Point(eyePositionX, eyePositionY, eyePositionZ);

        //Created the center of scene point
        List<Double> centerOfSceneList = createDoubleVals(centerOfScene);
        double centerOfSceneX = centerOfSceneList.get(0);
        double centerOfSceneY = centerOfSceneList.get(1);
        double centerOfSceneZ = centerOfSceneList.get(2);
        Point centerOfScenePoint  = new Point(centerOfSceneX, centerOfSceneY, centerOfSceneZ);

        //Created the up direction point
        List<Double> upDirList = createDoubleVals(upDirection);
        double upDirListX = upDirList.get(0);
        double upDirListY = upDirList.get(1);
        double upDirListZ = upDirList.get(2);
        Vector upDirListPoint  = new Vector(upDirListX, upDirListY, upDirListZ);

        camera = new Camera(eyePositionPoint, centerOfScenePoint, upDirListPoint, fieldOfView, cols, rows);
    }

    void createLight(String lightPosition, String color, String attenuationFactor){

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

        if (lightsList.size() == 0){
            lightsList.add(new AmbientLight(lightPositionPoint, rgbColor, attenuationFactorA, attenuationFactorB, attenuationFactorC));
        }
        else{
            lightsList.add(new Light(lightPositionPoint, rgbColor, attenuationFactorA, attenuationFactorB, attenuationFactorC));
        }

    }

    void createPigment(String pigmentType, String color){
        List<Float> rgbFloatList = createFloatVals(color);
        float red = rgbFloatList.get(0);
        float green = rgbFloatList.get(1);
        float blue = rgbFloatList.get(2);
        Color rgbColor = new Color(ColorUtil.clamp(red), ColorUtil.clamp(green), ColorUtil.clamp(blue));

        if (Objects.equals(pigmentType, "solid")){
            pigmentsList.add(new SolidPigment(rgbColor));
        }
    }

    void createFinish(String ambient, String diffuse, String specular, String shiny, String mirror, String transparency, String refraction){
        float ambientf = Float.parseFloat(ambient);
        float diffusef = Float.parseFloat(diffuse);
        float specularf = Float.parseFloat(specular);
        float shinyf = Float.parseFloat(shiny);
        float mirrorf = Float.parseFloat(mirror);
        float transparencyf = Float.parseFloat(transparency);
        float refractionf = Float.parseFloat(refraction);
        finishesList.add(new Finish(ambientf, diffusef, specularf, shinyf, mirrorf, transparencyf, refractionf));
    }

    void createShape(String shapeName, int pigmentNum, int finishNum, String positionPoint, String shapeSize){
        if ("sphere".equals(shapeName)) {
            List<Double> positionList = createDoubleVals(positionPoint);
            double shapePositionX = positionList.get(0);
            double shapePositionY = positionList.get(1);
            double shapePositionZ = positionList.get(2);
            Point shapePoint  = new Point(shapePositionX, shapePositionY, shapePositionZ);

            double shapeSizeDouble = Double.parseDouble(shapeSize);
            shape = new Sphere(shapePoint, shapeSizeDouble);
        }
        else if ("plane".equals(shapeName)) {
            List<Double> positionList = createDoubleVals(positionPoint);
            double shapePositionX = positionList.get(0);
            double shapePositionY = positionList.get(1);
            double shapePositionZ = positionList.get(2);
            float shapeSizeFloat = Float.parseFloat(shapeSize);

            shape = new Plane(shapePositionX, shapePositionY, shapePositionZ, shapeSizeFloat);
        }
        shape.setMaterial(pigmentsList.get(pigmentNum), finishesList.get(finishNum));
        shapesList.add(shape);
    }

    private List<Double> createDoubleVals(String stringPoints){
        String[] pointsSplit = stringPoints.split(",");
        List<Double> pointsDoubleList = new ArrayList<>();
        for (String number : pointsSplit) {
            pointsDoubleList.add(Double.parseDouble(number.trim()));
        }
        return pointsDoubleList;
    }

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
//			Log.debug("Checking light " + i + ":");
            light = lightsList.get(i);
            Vector lightRayVec = new Vector(hit.point, light.location);
            Ray lightRay = new Ray(hit.point, lightRayVec);
            lightRay.t = lightRayVec.magnitude();

//			Log.debug("  light ray = " + lightRay);
            RayHit obstruction = findHit(lightRay);
            if(obstruction == null) {
                // not in the shadow
                //              add the basic Phong shading for this light
                //                (diffuse, specular components)
//				Log.debug("  Light is visible:");

                Color c = light.getColor(hit, lightRay);
//				Log.debug("  final color   = " + c);
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
//			Log.debug("    Testing object " + shape + ": " + (h == null?"missed":"hit"));
            if(h != null && h.t < ray.t) {
//				Log.debug("      hit at t=" + h.t + ". point=" + h.point);
                hit = h;
                ray.t = h.t;
            }
        }

        return hit;
    }

    private Color trace(Ray ray, int depth) {
//		Log.debug("Tracing ray " + ray);

        RayHit hit = findHit(ray);

        if(hit != null) {
            return shade(hit, depth);
        }

        // missed everything. return background color
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


    public Color getPixelColor(int col, int row) {
        int bmpRow = rows-1 - row;
//		Log.debug("Tracing ray (col=" + col + ", row=" + row + ")");
//		Log.debug("  [Note: In bmp format this is row " + bmpRow + "]");

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
