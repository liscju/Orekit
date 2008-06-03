package fr.cs.orekit.orbits;

import fr.cs.orekit.errors.OrekitException;
import fr.cs.orekit.frames.Frame;
import fr.cs.orekit.time.AbsoluteDate;
import fr.cs.orekit.utils.PVCoordinates;


/** This class holds cartesian orbital parameters.

 * <p>
 * The parameters used internally are the cartesian coordinates:
 *   <ul>
 *     <li>x</li>
 *     <li>y</li>
 *     <li>z</li>
 *     <li>xDot</li>
 *     <li>yDot</li>
 *     <li>zDot</li>
 *   </ul>
 * contained in {@link PVCoordinates}.
 * </p>

 * <p>
 * Note that the implementation of this class delegates all non-cartesian related
 * computations ({@link #getA()}, {@link #getEquinoctialEx()}, ...) to an underlying
 * instance of the {@link EquinoctialOrbit} class. This implies that using this class
 * only for analytical computations which are always based on non-cartesian
 * parameters is perfectly possible but somewhat sub-optimal.
 * </p>
 * <p>
 * The instance <code>CartesianOrbit</code> is guaranteed to be immutable.
 * </p>
 * @see     Orbit
 * @version $Id:CartesianParameters.java 1310 2007-07-05 16:04:25Z luc $
 * @author  L. Maisonobe
 * @author  G. Prat
 * @author  F. Maussion
 * @author  V. Pommier-Maurussane
 */
public class CartesianOrbit extends Orbit {

    /** Serializable UID. */
    private static final long serialVersionUID = -6035381767203311530L;

    /** Underlying equinoctial orbit providing non-cartesian elements. */
    private final EquinoctialOrbit equinoctial;

    /** Constructor from cartesian parameters.
     * @param pvCoordinates the position and velocity of the satellite.
     * @param frame the frame in which the {@link PVCoordinates} are defined
     * @param date date of the orbital parameters
     * @param mu central attraction coefficient (m<sup>3</sup>/s<sup>2</sup>)
     */
    public CartesianOrbit(final PVCoordinates pvCoordinates, final Frame frame,
                               final AbsoluteDate date, final double mu) {
        super(pvCoordinates, frame, date, mu);
        equinoctial = new EquinoctialOrbit(pvCoordinates, frame, date, mu);
    }

    /** Constructor from any kind of orbital parameters.
     * @param op orbital parameters to copy
     * @param frame the frame in which the {@link PVCoordinates} are defined
     * @param date date of the orbital parameters
     * @param mu central attraction coefficient (m<sup>3</sup>/s<sup>2</sup>)
     * @exception OrekitException if some specific error occurs
     */
    public CartesianOrbit(final Orbit op) 
        throws OrekitException {
        super(op.getFrame(), op.getDate(), op.getMu());
        equinoctial = new EquinoctialOrbit(op);
    }

    /** Get the semi-major axis.
     * @return semi-major axis (m)
     */
    public double getA() {
        return equinoctial.getA();
    }

    /** Get the eccentricity.
     * @return eccentricity
     */
    public double getE() {
        return equinoctial.getE();
    }

    /** Get the inclination.
     * @return inclination (rad)
     */
    public double getI() {
        return equinoctial.getI();
    }

    /** Get the first component of the eccentricity vector.
     * @return first component of the eccentricity vector
     */
    public double getEquinoctialEx() {
        return equinoctial.getEquinoctialEx();
    }

    /** Get the second component of the eccentricity vector.
     * @return second component of the eccentricity vector
     */
    public double getEquinoctialEy() {
        return equinoctial.getEquinoctialEy();
    }

    /** Get the first component of the inclination vector.
     * @return first component of the inclination vector.
     */
    public double getHx() {
        return equinoctial.getHx();
    }

    /** Get the second component of the inclination vector.
     * @return second component of the inclination vector.
     */
    public double getHy() {
        return equinoctial.getHy();
    }

    /** Get the true latitude argument.
     * @return true latitude argument (rad)
     */
    public double getLv() {
        return equinoctial.getLv();
    }

    /** Get the eccentric latitude argument.
     * @return eccentric latitude argument.(rad)
     */
    public double getLE() {
        return equinoctial.getLE();
    }

    /** Get the mean latitude argument.
     * @return mean latitude argument.(rad)
     */
    public double getLM() {
        return equinoctial.getLM();
    }

    /**  Returns a string representation of this Orbit object.
     * @return a string representation of this object
     */
    public String toString() {
        return "cartesian parameters: " + getPVCoordinates().toString();
    }

}