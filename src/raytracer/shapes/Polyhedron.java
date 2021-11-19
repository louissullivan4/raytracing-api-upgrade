package raytracer.shapes;

import raytracer.*;

import java.util.List;

/**
 * The type Polyhedron.
 */
public class Polyhedron extends Shape {
	private List<Polygon> faces;

	/**
	 * Instantiates a new Polyhedron.
	 *
	 * @param faces the faces
	 */
	public Polyhedron(List<Polygon> faces) {
		this.faces = faces;

		Log.warn("Polyhedron shape is not supported. This shape will be ignored.");
	}

	@Override
	public RayHit intersect(Ray ray) {
		return null;
	}
}
