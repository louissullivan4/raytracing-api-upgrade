package raytracer;

import raytracer.*;
import raytracer.pigments.Finish;
import raytracer.pigments.Pigment;
import raytracer.shapes.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Api {

    private int cols, rows;
    private Camera camera;
    private final ArrayList<Light> lightsList = new ArrayList<>();
    private final ArrayList<Pigment> pigmentsList = new ArrayList<>();
    private final ArrayList<Finish> finishesList = new ArrayList<>();
    private final ArrayList<Shape> shapesList = new ArrayList<>();


    void createView(String eyePosition, String centerOfScene, String upDirection, double fieldOfView){
        //Created the 3d position of the eye point
        List<Double> eyePositionList = createPointVals(eyePosition);
        Double eyePositionX = eyePositionList.get(0);
        Double eyePositionY = eyePositionList.get(1);
        Double eyePositionZ = eyePositionList.get(2);
        Point eyepositionPoint  = new Point(eyePositionX, eyePositionY, eyePositionZ);

        //Created the center of scene point
        List<Double> centerOfSceneList = createPointVals(centerOfScene);
        Double centerOfSceneX = centerOfSceneList.get(0);
        Double centerOfSceneY = centerOfSceneList.get(1);
        Double centerOfSceneZ = centerOfSceneList.get(2);
        Point centerOfScenePoint  = new Point(centerOfSceneX, centerOfSceneY, centerOfSceneZ);

        //Created the up direction point
        List<Double> upDirList = createPointVals(upDirection);
        Double upDirListX = upDirList.get(0);
        Double upDirListY = upDirList.get(1);
        Double upDirListZ = upDirList.get(2);
        Vector upDirListPoint  = new Vector(upDirListX, upDirListY, upDirListZ);

        camera = new Camera(eyepositionPoint, centerOfScenePoint, upDirListPoint, fieldOfView, cols, rows);
    }

    void createLight(String lightPosition, String color, String attenuationFactor){
        //Created the 3d position of the light
        List<Double> positionList = createPointVals(lightPosition);
        Double lightPositionX = positionList.get(0);
        Double lightPositionY = positionList.get(1);
        Double lightPositionZ = positionList.get(2);
        Point lightPositionPoint  = new Point(lightPositionX, lightPositionY, lightPositionZ);

        //Created the rgb values
        String[] rgbSplit = color.split(",");
        List<Float> rgbFloatList = new ArrayList<>();
        for (String number : rgbSplit) {
            rgbFloatList.add(Float.parseFloat(number.trim()));
        }
        float red = rgbFloatList.get(0);
        float green = rgbFloatList.get(1);
        float blue = rgbFloatList.get(2);
        Color rgbColor = new Color(ColorUtil.clamp(red), ColorUtil.clamp(green), ColorUtil.clamp(blue));

        //Created the attenuation factor
        String[] afSplit = attenuationFactor.split(",");
        List<Float> afFloatList = new ArrayList<>();
        for (String number : afSplit) {
            afFloatList.add(Float.parseFloat(number.trim()));
        }
        float attenuationFactorA = afFloatList.get(0);
        float attenuationFactorB = afFloatList.get(1);
        float attenuationFactorC = afFloatList.get(2);

        if (lightsList.size() == 0){
            lightsList.add(new Light(lightPositionPoint, rgbColor, attenuationFactorA, attenuationFactorB, attenuationFactorC));
        }
        else{
            lightsList.add(new AmbientLight(lightPositionPoint, rgbColor, attenuationFactorA, attenuationFactorB, attenuationFactorC));
        }

    }

    private List<Double> createPointVals(String stringPoints){
        String[] pointsSplit = stringPoints.split(",");
        List<Double> pointsDoubleList = new ArrayList<>();
        for (String number : pointsSplit) {
            pointsDoubleList.add(Double.parseDouble(number.trim()));
        }
        return pointsDoubleList;
    }




}
