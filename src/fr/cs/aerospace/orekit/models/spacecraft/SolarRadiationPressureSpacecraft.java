package fr.cs.aerospace.orekit.models.spacecraft;

import org.spaceroots.mantissa.geometry.Vector3D;

import fr.cs.aerospace.orekit.forces.perturbations.SolarRadiationPressure;
import fr.cs.aerospace.orekit.time.AbsoluteDate;

/** Adapted container for the SolarRadiationPressure force model.
 * 
 * @see SolarRadiationPressure
 * @author F. Maussion
 */
public interface SolarRadiationPressureSpacecraft {
  
// TODO Define correctly the signature of these methods (currently wrong)
	  /** Get the surface.
	   * @param direction direction of the flux
	   * @return surface (m<sup>2</sup>)
	   */
	  public double getSurface(Vector3D direction, AbsoluteDate t);

	  /** Get the absorption coefficients vector.
	   * @param direction direction of the light flux
	   * @return absorption coefficients vector
	   */
	  public Vector3D getAbsCoef(Vector3D direction, AbsoluteDate t);

	  /** Get the specular reflection coefficients vector.
	   * @param direction direction of the light flux
	   * @return specular reflection coefficients vector
	   */
	  public Vector3D getReflectionCoef(Vector3D direction, AbsoluteDate t);

}
