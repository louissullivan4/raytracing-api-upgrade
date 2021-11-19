package raytracer.shapes;

import raytracer.*;

import java.util.List;

/**
 * The type Bezier.
 */
public class Bezier extends Shape {
	private List<Point> points;

	/**
	 * Instantiates a new Bezier.
	 *
	 * @param points the points
	 */
	public Bezier(List<Point> points) {
		this.points = points;

		Log.warn("Bezier shape is not supported. This shape will be ignored.");
	}

	@Override
	public RayHit intersect(Ray ray) {
		return null;
	}
}
