package fr.cs.orekit.orbits;

import fr.cs.orekit.frames.Frame;
import fr.cs.orekit.time.AbsoluteDate;
import fr.cs.orekit.utils.PVCoordinates;
import java.io.Serializable;

/**
 * This class handles orbits around a central body.

 * <p>
 * In OREKIT architecture, an Orbit is only a state at a specific date.
 * Orbit evolution is represented by the {@link
 * fr.cs.orekit.propagation.Ephemeris Ephemeris} interface,
 * which contains only the {@link Ephemeris#getSpacecraftState(AbsoluteDate)} method to
 * provide new states for new dates. This interface can be implemented by
 * several means like file-based interpolation, analytical model or numerical
 * integration.
 * </p>

 * <p>
 * This class handles periodic orbits (it does neither handle parabolic nor
 * hyperbolic orbits). Several different internal representations can
 * be used for the parameters, the more general one being {@link
 * EquinoctialParameters equinoctial parameters} which can handle
 * circular and equatorial orbits without problem and have
 * singularities only for purely retrograd orbit (inclination = &pi;).
 * </p>

 * <p>
 * For the sake of numerical stability, only the always non-ambiguous classical
 * keplerian elements are provided ({@link #getE() eccentricity} and {@link #getI()
 * inclination}, not the potentialy ambiguous ones like perigee argument or right
 * ascension of ascending node. If these elements are needed, the user must
 * explicitely convert the parameters to {@link KeplerianParameters keplerian
 * parameters}, if he considers the orbit is sufficiently non-circular or
 * non-equatorial.
 * </p>
 * <p>
 * The instance <code>Orbit</code> is guaranted to be immutable.
 * </p>
 * @see     OrbitalParameters
 * @see     fr.cs.orekit.propagation.Ephemeris
 * @version $Id:Orbit.java 1310 2007-07-05 16:04:25Z luc $
 * @author  L. Maisonobe
 * @author  G. Prat
 * @author  F.Maussion

 */

public class Orbit implements Serializable {

    /** Serializable UID. */
    private static final long serialVersionUID = -1020615214010191133L;

    /** Date of the current state. */
    private final AbsoluteDate t;

    /** Orbital parameters state. */
    private final OrbitalParameters parameters;

    /** Create a new instance from date and orbital parameters
     * @param t  date
     * @param parameters orbital parameters
     */
    public Orbit(AbsoluteDate t, OrbitalParameters parameters) {
        this.t = t;
        this.parameters = parameters;
    }

    /** Get the date.
     * @return date
     */
    public AbsoluteDate getDate() {
        return t;
    }

    /** Get the orbital parameters.
     * @return orbital parameters
     */
    public OrbitalParameters getParameters() {
        return parameters;
    }

    /** Get the inertial frame.
     * @return the frame
     */
    public Frame getFrame() {
        return parameters.getFrame();
    }

    /** Get the semi-major axis.
     * @return semi-major axis (m)
     */
    public double getA() {
        return parameters.getA();
    }

    /** Get the first component of the eccentricity vector (as per equinoctial parameters).
     * @return e cos(&omega; + &Omega;), first component of eccentricity vector
     * @see #getE()
     */
    public double getEx(){
        return parameters.getEquinoctialEx();
    }

    /** Get the second component of the eccentricity vector (as per equinoctial parameters).
     * @return e sin(&omega; + &Omega;), second component of the eccentricity vector
     * @see #getE()
     */
    public double getEy(){
        return parameters.getEquinoctialEy();
    }

    /** Get the first component of the inclination vector (as per equinoctial parameters).
     * @return tan(i/2) cos(&Omega;), first component of the inclination vector
     * @see #getI()
     */
    public double getHx(){
        return parameters.getHx();
    }

    /** Get the second component of the inclination vector (as per equinoctial parameters).
     * @return tan(i/2) sin(&Omega;), second component of the inclination vector
     * @see #getI()
     */
    public double getHy(){
        return parameters.getHy();
    }

    /** Get the true latitude argument (as per equinoctial parameters).
     * @return v + &omega; + &Omega; true latitude argument (rad)
     * @see #getLE()
     * @see #getLM()
     */
    public double getLv(){
        return parameters.getLv();
    }

    /** Get the eccentric latitude argument (as per equinoctial parameters).
     * @return E + &omega; + &Omega; eccentric latitude argument (rad)
     * @see #getLv()
     * @see #getLM()
     */
    public double getLE(){
        return parameters.getLE();
    }

    /** Get the mean latitude argument (as per equinoctial parameters).
     * @return M + &omega; + &Omega; mean latitude argument (rad)
     * @see #getLv()
     * @see #getLE()
     */
    public double getLM(){
        return parameters.getLM();
    }


    // Additional orbital elements

    /** Get the eccentricity.
     * @return eccentricity
     * @see #getEx()
     * @see #getEy()
     */
    public double getE() {
        return parameters.getE();
    }

    /** Get the inclination.
     * @return inclination (rad)
     * @see #getHx()
     * @see #getHy()
     */
    public double getI() {
        return parameters.getI();
    }

    /** Get the {@link PVCoordinates}.
     * Compute the position and velocity of the satellite. This method caches its
     * results, and recompute them only when the method is called with a new value
     * for mu. The result is provided as a reference to the internally cached
     * {@link PVCoordinates}, so the caller is responsible to copy it in a separate
     * {@link PVCoordinates} if it needs to keep the value for a while.
     * @param mu central attraction coefficient (m<sup>3</sup>/s<sup>2</sup>)
     * @return pvCoordinates in inertial frame (reference to an
     * internally cached pvCoordinates which can change)
     */
    public PVCoordinates getPVCoordinates(double mu) {
        return parameters.getPVCoordinates(mu);
    }

    /**  Returns a string representation of this Orbit object
     * @return a string representation of this object
     */
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append('{');
        sb.append(t.toString());
        sb.append(' ');
        sb.append(parameters.toString());
        sb.append('}');
        return sb.toString();
    }

}
