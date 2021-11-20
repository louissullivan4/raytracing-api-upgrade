package raytracer;

/**
 * The type Vector.
 */
public class Vector {
    /**
     * The X.
     */
    public double x, /**
     * The Y.
     */
    y, /**
     * The Z.
     */
    z;

    /**
     * Instantiates a new Vector.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     */
    public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

    /**
     * Instantiates a new Vector.
     *
     * @param p the p
     */
    public Vector(Point p) {
		this(p.x, p.y, p.z);
	}

    /**
     * Create a new vector from point1 to point2.
     *
     * @param from the from
     * @param to   the to
     */
    public Vector(Point from, Point to) {
		this(to.x - from.x, to.y - from.y, to.z - from.z);
	}

    /**
     * Normalize vector.
     *
     * @return the vector
     */
    public Vector normalize() {
		double magnitude = magnitude();
		double divisor;
		if(magnitude == 0) {
			Log.error("Trying to normalize a Vector with magnitude 0.");
			divisor = Double.POSITIVE_INFINITY;
		}
		else divisor = 1 / magnitude;

		return this.times(divisor);
	}

    /**
     * Magnitude double.
     *
     * @return the double
     */
    public double magnitude() {
		return Math.sqrt(this.dot(this));
	}

    /**
     * Plus vector.
     *
     * @param v the v
     * @return the vector
     */
    public Vector plus(Vector v) {
		return new Vector(x + v.x, y + v.y, z + v.z);
	}

    /**
     * Minus vector.
     *
     * @param v the v
     * @return the vector
     */
    public Vector minus(Vector v) {
		return new Vector(x - v.x, y - v.y, z - v.z);
	}

    /**
     * Negate vector.
     *
     * @return the vector
     */
    public Vector negate() {
		return times(-1);
	}

    /**
     * Times vector.
     *
     * @param scalar the scalar
     * @return the vector
     */
    public Vector times(double scalar) {
		return new Vector(x * scalar, y * scalar, z * scalar);
	}

    /**
     * Cross vector.
     *
     * @param v the v
     * @return the vector
     */
    public Vector cross(Vector v) {
		return new Vector(((y * v.z) - (z * v.y)),
						  ((z * v.x) - (x * v.z)),
						  ((x * v.y) - (y * v.x)));
	}

    /**
     * Dot double.
     *
     * @param v the v
     * @return the double
     */
    public double dot(Vector v) {
		return (x * v.x) + (y * v.y) + (z * v.z);
	}

    /**
     * Halfway vector.
     *
     * @param v1 the v 1
     * @param v2 the v 2
     * @return the vector
     */
    public static Vector halfway(Vector v1, Vector v2) {
		return v1.plus(v2).normalize();
	}

	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
