package raytracer.shapes;

import raytracer.*;
import raytracer.pigments.Finish;
import raytracer.pigments.Pigment;

import java.awt.Color;


/**
 * The type Shape.
 */
public abstract class Shape {
    /**
     * The Pigment.
     */
    public Pigment pigment;
    /**
     * The Finish.
     */
    public Finish finish;

    /**
     * Sets material.
     *
     * @param pigment the pigment
     * @param finish  the finish
     */
    public final void setMaterial(Pigment pigment, Finish finish) {
		this.pigment = pigment;
		this.finish = finish;
	}

    /**
     * Intersect ray hit.
     *
     * @param ray the ray
     * @return the ray hit
     */
    public abstract RayHit intersect(Ray ray);

    /**
     * Contains boolean.
     *
     * @param p the p
     * @return the boolean
     */
    public boolean contains(Point p) {
		return false;
	}

    /**
     * Gets color.
     *
     * @param p the p
     * @return the color
     */
    public final Color getColor(Point p) {
		return pigment.getColor(p);
	}
}
