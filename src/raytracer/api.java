package raytracer;

import raytracer.*;
import raytracer.pigments.Finish;
import raytracer.pigments.Pigment;
import raytracer.shapes.Shape;

import java.util.ArrayList;
import java.util.List;


public class api {

    private Camera camera;
    private final ArrayList<Light> lightsList = new ArrayList<>();
    private final ArrayList<Pigment> pigmentsList = new ArrayList<>();
    private final ArrayList<Finish> finishesList = new ArrayList<>();
    private final ArrayList<Shape> shapesList = new ArrayList<>();

    static void createView(String eyePosition){
        List<Double> eyePositionList = createPointVals(eyePosition);
        Double x = eyePositionList.get(0);
        Double y = eyePositionList.get(1);
        Double z = eyePositionList.get(2);
        Point eyepositionCreated  = new Point(x, y, z);
        camera = new Camera(eyepositionCreated, centerOfScene, upDirection, fieldOfView);
    }

    static private List<Double> createPointVals(String stringPoints){
        String[] pointsSplit = stringPoints.split(",");
        List<Double> pointsDoubleList = new ArrayList<>();
        for (String number : pointsSplit) {
            pointsDoubleList.add(Double.parseDouble(number.trim()));
        }
        return pointsDoubleList;
    }




}
