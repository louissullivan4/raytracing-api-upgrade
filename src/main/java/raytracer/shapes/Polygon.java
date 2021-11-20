package raytracer.shapes;

import raytracer.*;

/**
 * The type Polygon.
 */
public class Polygon {
    /**
     * The A.
     */
    final double a, /**
     * The B.
     */
    b, /**
     * The C.
     */
    c, /**
     * The D.
     */
    d;
    /**
     * The Normal.
     */
    final Vector normal;
    /**
     * The Plane.
     */
    final Plane plane;

    /**
     * Instantiates a new Polygon.
     *
     * @param a the a
     * @param b the b
     * @param c the c
     * @param d the d
     */
    public Polygon(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;

		this.normal = new Vector(a, b, c).normalize();
		this.plane = new Plane(a, b, c, d);
	}
}
