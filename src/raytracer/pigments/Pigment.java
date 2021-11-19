package raytracer.pigments;

import raytracer.Point;

import java.awt.Color;


/**
 * The interface Pigment.
 */
public interface Pigment {
    /**
     * Gets color.
     *
     * @param p the p
     * @return the color
     */
    public Color getColor(Point p);
}
