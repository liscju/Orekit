package fr.cs.aerospace.orekit.perturbations;

import org.spaceroots.mantissa.geometry.Vector3D;
import fr.cs.aerospace.orekit.frames.Frame;
import fr.cs.aerospace.orekit.models.perturbations.Atmosphere;
import fr.cs.aerospace.orekit.models.spacecraft.AtmosphereDragSpacecraft;
import fr.cs.aerospace.orekit.orbits.OrbitDerivativesAdder;
import fr.cs.aerospace.orekit.time.AbsoluteDate;
import fr.cs.aerospace.orekit.utils.PVCoordinates;

/** Atmospheric drag force model.
 * @version $Id: Drag.java 1038 2006-10-03 08:03:34 +0000 (mar., 03 oct. 2006) fabien $
 * @author E. Delente
 */

public class Drag implements ForceModel {

  /** Simple constructor.
   * @param atmosphere atmospheric model
   * @param spacecraft spacecraft
   */
  public Drag(Atmosphere atmosphere, AtmosphereDragSpacecraft spacecraft) {
    this.atmosphere = atmosphere;
    this.spacecraft = spacecraft;
  }

  /** Compute the contribution of the drag to the perturbing acceleration.
   * @param date current date
   * @param pvCoordinates the position end velocity
   * @param Attitude current attitude
   * @param adder object where the contribution should be added
   */
  public void addContribution(AbsoluteDate date,
		                      PVCoordinates pvCoordinates, Frame frame,
                              OrbitDerivativesAdder adder) {

    double   rho       = atmosphere.getDensity(date, pvCoordinates.getPosition());
    Vector3D vAtm      = atmosphere.getVelocity(date, pvCoordinates.getPosition());
    Vector3D incidence = Vector3D.subtract(vAtm, pvCoordinates.getVelocity());
    double   v2        = Vector3D.dotProduct(incidence, incidence);
    incidence.normalizeSelf();
    double   k         = rho * v2 * spacecraft.getSurface(incidence, date)
                       / (2 * spacecraft.getMass());
    Vector3D cD        = spacecraft.getDragCoef(incidence, date);

    // Additition of calculated accelration to adder
    adder.addXYZAcceleration(k * cD.getX(), k * cD.getY(), k * cD.getZ());

  }

  public SWF[] getSwitchingFunctions() {
    return null;
  }

  /** Atmospheric model */
  private Atmosphere atmosphere;

  /** Spacecraft. */
  private AtmosphereDragSpacecraft spacecraft;

}
