package raytracer;

/**
 * The type Ray.
 */
public class Ray {
    /**
     * The Origin.
     */
    public final Point origin;
    /**
     * The Direction.
     */
    public final Vector direction;
    /**
     * The T.
     */
    public double t;

    /**
     * Instantiates a new Ray.
     *
     * @param origin    the origin
     * @param direction the direction
     */
    public Ray(Point origin, Vector direction) {
		this(origin, direction, true);
	}

    /**
     * Instantiates a new Ray.
     *
     * @param origin         the origin
     * @param direction      the direction
     * @param adjustForError the adjust for error
     */
    public Ray(Point origin, Vector direction, boolean adjustForError) {
		this.t = Double.POSITIVE_INFINITY;

		this.direction = direction.normalize();

		if(adjustForError) origin = origin.plus(this.direction.times(0.001));

		this.origin = origin;
	}

    /**
     * Intersects double.
     *
     * @param p the p
     * @return the double
     */
    public Double intersects(Point p) {
		double t = Double.POSITIVE_INFINITY;
		return t;
	}

    /**
     * Gets end.
     *
     * @param t the t
     * @return the end
     */
    public Point getEnd(double t) {
		return origin.plus(direction.times(t));
	}

    /**
     * Gets end.
     *
     * @return the end
     */
    public Point getEnd() {
		return getEnd(this.t);
	}

	public String toString() {
		return "Org:" + origin + " Dir:" + direction + " t:" + t;
	}
}
