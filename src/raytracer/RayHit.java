package raytracer;

import raytracer.shapes.Shape;

/**
 * The type Ray hit.
 */
public class RayHit {
    /**
     * The Ray.
     */
    public final Ray ray;
    /**
     * The Shape.
     */
    public final Shape shape;
    /**
     * The T.
     */
    public final double t;
    /**
     * The Normal.
     */
    public final Vector normal;
    /**
     * The Point.
     */
    public final Point point;
	private final boolean incoming;

    /**
     * Instantiates a new Ray hit.
     *
     * @param ray      the ray
     * @param shape    the shape
     * @param normal   the normal
     * @param t        the t
     * @param entering the entering
     */
    public RayHit(Ray ray, Shape shape, Vector normal, double t, boolean entering) {
		this.ray = ray;
		this.shape = shape;
		this.t = t;
		this.normal = normal.normalize();
		this.point = ray.getEnd(t);
		this.incoming = entering;
	}

    /**
     * Instantiates a new Ray hit.
     *
     * @param ray          the ray
     * @param shape        the shape
     * @param normal       the normal
     * @param intersection the intersection
     * @param entering     the entering
     */
    public RayHit(Ray ray, Shape shape, Vector normal, Point intersection, boolean entering) {
		this.ray = ray;
		this.shape = shape;
		this.t = new Vector(ray.origin, intersection).magnitude();
		this.normal = normal.normalize();
		this.point = intersection;
		this.incoming = entering;
	}

    /**
     * Gets reflection ray.
     *
     * @return the reflection ray
     */
    public Ray getReflectionRay() {
		return new Ray(point, ray.direction.minus(normal.times(2.0*ray.direction.dot(normal))));
	}

    /**
     * Gets transmission ray.
     *
     * @return the transmission ray
     */
    public Ray getTransmissionRay() {
		Vector v = ray.direction.negate();
		Vector n = normal;
		double cosi = v.dot(n);
		double nint;
		if(incoming) nint = 1.0 / shape.finish.ior;
		else nint = shape.finish.ior;
		double cost = Math.sqrt(1.0 - nint*nint * (1 - cosi*cosi));

		return new Ray(point, n.times(nint * cosi - cost).minus(v.times(nint)));
	}
}
