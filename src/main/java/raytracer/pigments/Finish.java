package raytracer.pigments;

/**
 * The type Finish.
 */
public class Finish {
    /**
     * The Amb.
     */
    public float amb, /**
     * The Diff.
     */
    diff, /**
     * The Spec.
     */
    spec, /**
     * The Shiny.
     */
    shiny, /**
     * The Refl.
     */
    refl, /**
     * The Trans.
     */
    trans, /**
     * The Ior.
     */
    ior;

    /**
     * Instantiates a new Finish.
     *
     * @param amb   the amb
     * @param diff  the diff
     * @param spec  the spec
     * @param shiny the shiny
     * @param refl  the refl
     * @param trans the trans
     * @param ior   the ior
     */
    public Finish(float amb, float diff, float spec, float shiny, float refl, float trans, float ior) {
		this.amb = amb;
		this.diff = diff;
		this.spec = spec;
		this.shiny = shiny;
		this.refl = refl;
		this.trans = trans;
		this.ior = ior;
	}

    /**
     * Is reflective boolean.
     *
     * @return the boolean
     */
    public boolean isReflective() {
		return refl > 0;
	}

    /**
     * Is transmittive boolean.
     *
     * @return the boolean
     */
    public boolean isTransmittive() {
		return trans > 0;
	}
}
