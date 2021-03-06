package raytracer;

/**
 * The type Camera.
 */
public class Camera {
	private final Point eye;
	private final Vector vx;
	private final Vector vy;
	private final Vector vz;

	private final double windowDistance;
	private final double windowWidth;
	private final double windowHeight;

	private final double rows;
	private final double cols;

    /**
     * Instantiates a new Camera.
     *
     * @param eye    the eye
     * @param center the center
     * @param up     the up
     * @param fovy   the fovy
     * @param cols   the cols
     * @param rows   the rows
     */
    public Camera(Point eye, Point center, Vector up, double fovy, int cols, int rows) {
		fovy = Math.toRadians(fovy);
		double fovx = fovy * cols / rows;

		Vector at = new Vector(eye, center);
		vz = at.negate().normalize();
		vx = up.cross(vz).normalize();
		vy = vz.cross(vx);

		this.eye = eye;
		this.cols = cols;
		this.rows = rows;

		windowDistance = 1.0;
		windowHeight = Math.sin(fovy / 2.0) * windowDistance * 2.0;
		windowWidth = Math.sin(fovx / 2.0) * windowDistance * 2.0;

//		Log.debug("  Viewframe:");
//		Log.debug("    Org: " + eye);
//		Log.debug("    X:   " + vx);
//		Log.debug("    Y:   " + vy);
//		Log.debug("    Z:   " + vz);
//
//		Log.debug("    Window width: " + windowWidth);
//		Log.debug("          height: " + windowHeight);
	}

    /**
     * Gets ray.
     *
     * @param col the col
     * @param row the row
     * @return the ray
     */
    public Ray getRay(int col, int row) {
		return getRay(col, row, 0.5, 0.5);
	}

    /**
     * Gets ray.
     *
     * @param col              the col
     * @param row              the row
     * @param pixelAdjustmentX the pixel adjustment x
     * @param pixelAdjustmentY the pixel adjustment y
     * @return the ray
     */
    public Ray getRay(int col, int row, double pixelAdjustmentX, double pixelAdjustmentY) {
		double x = (((double)col + pixelAdjustmentX) / cols) * windowWidth - (windowWidth / 2.0);
		double y = (((double)row + pixelAdjustmentY) / rows) * windowHeight - (windowHeight / 2.0);

		Vector v = new Vector(eye, convertCoords(new Point(x, y, -windowDistance)));

//		Log.debug("  Generating ray:");
//		Log.debug("    Window coordinates: (" + x + ", " + y + ")");
//		Log.debug("    Passes through window point: " + v);

		Ray ray = new Ray(eye, v);
//		Log.debug("    Final ray: " + ray);

		return ray;
	}

    /**
     * Convert coords point.
     *
     * @param p the p
     * @return the point
     */
    public Point convertCoords(Point p) {
		Vector v = convertCoords(new Vector(p.x, p.y, p.z));
		return new Point(v.x, v.y, v.z);
	}

    /**
     * Convert coords vector.
     *
     * @param p the p
     * @return the vector
     */
    public Vector convertCoords(Vector p) {
		Matrix rT = new Matrix(new double[][]{
				{vx.x, vy.x, vz.x, 0},
				{vx.y, vy.y, vz.y, 0},
				{vx.z, vy.z, vz.z, 0},
				{0, 0, 0, 1}
		});
		Matrix tInv = new Matrix(new double[][]{
				{1, 0, 0, eye.x},
				{0, 1, 0, eye.y},
				{0, 0, 1, eye.z},
				{0, 0, 0, 1}
		});

		Matrix matrix = tInv.times(rT);
		return matrix.times(new Vector(p.x, p.y, p.z));
	}
}
