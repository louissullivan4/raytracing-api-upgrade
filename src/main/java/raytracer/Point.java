package raytracer;

/**
 * The type Point.
 */
public class Point {
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
     * Instantiates a new Point.
     *
     * @param x the x
     * @param y the y
     * @param z the z
     */
    public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

    /**
     * Distance to double.
     *
     * @param p the p
     * @return the double
     */
    public double distanceTo(Point p) {
		return Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y) + (p.z - z)*(p.z - z));
	}

    /**
     * Plus point.
     *
     * @param v the v
     * @return the point
     */
    public Point plus(Vector v) {
		return new Point(x + v.x, y + v.y, z + v.z);
	}

	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}
}
