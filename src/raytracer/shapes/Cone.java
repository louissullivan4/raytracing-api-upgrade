package raytracer.shapes;

import raytracer.*;

/**
 * The type Cone.
 */
public class Cone extends Shape {
	private Point apex;
	private Vector axis;
	private double radius;

	/**
	 * Instantiates a new Cone.
	 *
	 * @param apex   the apex
	 * @param axis   the axis
	 * @param radius the radius
	 */
	public Cone(Point apex, Vector axis, double radius) {
		this.apex = apex;
		this.axis = axis;
		this.radius = radius;

		Log.warn("Cone shape is not supported. This shape will be ignored.");
	}

	public RayHit intersect(Ray ray) {
		return null;
	}
}
