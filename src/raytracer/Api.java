package raytracer;

import raytracer.pigments.Finish;
import raytracer.pigments.Pigment;
import raytracer.pigments.SolidPigment;
import raytracer.shapes.Plane;
import raytracer.shapes.Shape;
import raytracer.shapes.Sphere;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Api {

    private int cols, rows;
    private Camera camera;
    private final ArrayList<Light> lightsList = new ArrayList<>();
    private final ArrayList<Pigment> pigmentsList = new ArrayList<>();
    private final ArrayList<Finish> finishesList = new ArrayList<>();
    private final ArrayList<Shape> shapesList = new ArrayList<>();


    void createView(String eyePosition, String centerOfScene, String upDirection, double fieldOfView){
        //Created the 3d position of the eye point
        List<Double> eyePositionList = createDoubleVals(eyePosition);
        Double eyePositionX = eyePositionList.get(0);
        Double eyePositionY = eyePositionList.get(1);
        Double eyePositionZ = eyePositionList.get(2);
        Point eyepositionPoint  = new Point(eyePositionX, eyePositionY, eyePositionZ);

        //Created the center of scene point
        List<Double> centerOfSceneList = createDoubleVals(centerOfScene);
        Double centerOfSceneX = centerOfSceneList.get(0);
        Double centerOfSceneY = centerOfSceneList.get(1);
        Double centerOfSceneZ = centerOfSceneList.get(2);
        Point centerOfScenePoint  = new Point(centerOfSceneX, centerOfSceneY, centerOfSceneZ);

        //Created the up direction point
        List<Double> upDirList = createDoubleVals(upDirection);
        Double upDirListX = upDirList.get(0);
        Double upDirListY = upDirList.get(1);
        Double upDirListZ = upDirList.get(2);
        Vector upDirListPoint  = new Vector(upDirListX, upDirListY, upDirListZ);

        camera = new Camera(eyepositionPoint, centerOfScenePoint, upDirListPoint, fieldOfView, cols, rows);
    }

    void createLight(String lightPosition, String color, String attenuationFactor){
        //Created the 3d position of the light
        List<Double> positionList = createDoubleVals(lightPosition);
        Double lightPositionX = positionList.get(0);
        Double lightPositionY = positionList.get(1);
        Double lightPositionZ = positionList.get(2);
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
            lightsList.add(new Light(lightPositionPoint, rgbColor, attenuationFactorA, attenuationFactorB, attenuationFactorC));
        }
        else{
            lightsList.add(new AmbientLight(lightPositionPoint, rgbColor, attenuationFactorA, attenuationFactorB, attenuationFactorC));
        }

    }

    void createPigment(String pigmentType, String color){
        List<Float> rgbFloatList = createFloatVals(color);
        float red = rgbFloatList.get(0);
        float green = rgbFloatList.get(1);
        float blue = rgbFloatList.get(2);
        Color rgbColor = new Color(ColorUtil.clamp(red), ColorUtil.clamp(green), ColorUtil.clamp(blue));

        if (Objects.equals(pigmentType, "Solid")){
            pigmentsList.add(new SolidPigment(rgbColor));
        }

    }

    void createFinish(float ambient, float diffuse, float specular, float shiny, float mirror, float transparency, float refraction){
        finishesList.add(new Finish(ambient, diffuse, specular, shiny, mirror, transparency, refraction));
    }

    void createShape(String shapeName, int pigmentNum, int finishNum, String positionPoint, int shapeSize){
        Shape shape;
        if ("sphere".equals(shapeName)) {
            List<Double> positionList = createDoubleVals(positionPoint);
            Double shapePositionX = positionList.get(0);
            Double shapePositionY = positionList.get(1);
            Double shapePositionZ = positionList.get(2);
            Point shapePoint  = new Point(shapePositionX, shapePositionY, shapePositionZ);
            shape = new Sphere(shapePoint, shapeSize);
        }
        else if ("plane".equals(shapeName)) {
            List<Double> positionList = createDoubleVals(positionPoint);
            Double shapePositionX = positionList.get(0);
            Double shapePositionY = positionList.get(1);
            Double shapePositionZ = positionList.get(2);
            shape = new Plane(shapePositionX, shapePositionY, shapePositionZ, shapeSize);
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

    private List<Float> createFloatVals(String stringPoints){
        String[] strSplit = stringPoints.split(",");
        List<Float> valFloatList = new ArrayList<>();
        for (String number : strSplit) {
            valFloatList.add(Float.parseFloat(number.trim()));
        }
        return valFloatList;
    }









}
