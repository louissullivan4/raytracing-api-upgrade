package raytracer.shapes;

import raytracer.*;

/**
 * The type Disc.
 */
public class Disc extends Shape {
	private Point center;
	private Vector normal;
	private double radius;

	/**
	 * Instantiates a new Disc.
	 *
	 * @param center the center
	 * @param normal the normal
	 * @param radius the radius
	 */
	public Disc(Point center, Vector normal, double radius) {
		this.center = center;
		this.normal = normal;
		this.radius = radius;

		Log.warn("Disc shape is not supported. This shape will be ignored.");
	}

	@Override
	public RayHit intersect(Ray ray) {
		return null;
	}
}
