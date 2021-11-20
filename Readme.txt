RayTracer Upgrade
by Team 12 - 20/11/2021
------------------------------
This is an updated of a raytracing legacy code by Idris Mokhtarzada. The java features
have been updated to jdk-11, an api has been created to make the program
easier to use and also a maven build file has been created to make executable
JAR files.

Requirements
============
Java JDK 11.0 or higher is required to compile and run this RayTracer.
You will need the command line tools 'javac' and 'java'.


Building
========
To build add this to the command line:
javac src/main/java/raytracer/*.java src/main/java/raytracer/pigments/*.java src/main/java/raytracer/shapes/*java

Usage
=====
After making, you can simply run the application using java as follows:
java -cp src/main/java/ raytracer.Main

You can also run the executable JAR file in the target folder.

Implemented Features
====================
Basic Features:
* shapes: spheres, planes
* pigments: solid, checker
* shadows
* reflection
* refraction

NEW FEATURES:
* Api methods to create an image:
    - createView() - create the camera that looks at the image
    - createLight() - create the image lighting
    - createPigment() - create the pigments for the image
    - createShape() - add shapes to the image
    - render() - take everything from above and create the image


