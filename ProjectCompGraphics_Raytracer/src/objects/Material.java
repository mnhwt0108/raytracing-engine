package objects;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.vecmath.Vector3d;

public class Material implements Serializable
{
	private static final long serialVersionUID = 1L;

	/** Color that reflects when the object is hit by a ray */
	public Vector3d diffuseColor = new Vector3d(0.8, 0.8, 0.8);

	/** Color that reflects specular rays */
	public Vector3d specularColor = new Vector3d(1, 1, 1);

	/** Index of illumination intensity when hit by a ray */
	public double diffuseIndex = 1;

	/** Index of how much is reflected when a ray hits an object s */
	public double specularIndex = 0.5;

	/** General ambient intensity of reflection when not hit by a ray */
	public double ambientIntensity = 0.2;

	/** Alpha index for the object */
	public double alpha = 1.0;

	/** Proportion of ray that is refracted */
	public double refractionIndex = 1.0;

	/** Proportion of ray that is reflected */
	public double reflectionIndex = 0;

	/** Object brightness */
	public double shininess = 0.2;

	/** Texture, if one exists */
	private BufferedImage texture = null;

	public Material(Material m)
	{
		set(m);
	}

	public Material()
	{
	}

	@Override
	public String toString()
	{
		return "Material(diffuseColor=" + diffuseColor + ", specularColor="
				+ specularColor + ", diffuseIndex=" + diffuseIndex
				+ ", specularIndex=" + specularIndex + ", ambientIntensity="
				+ ambientIntensity + ", transparency=" + alpha
				+ ", refractionIndex=" + refractionIndex + ", reflectionIndex="
				+ reflectionIndex + ")";
	}

	public void set(Material m)
	{
		this.diffuseColor = m.diffuseColor;
		this.specularColor = m.specularColor;
		this.diffuseIndex = m.diffuseIndex;
		this.specularIndex = m.specularIndex;
		this.ambientIntensity = m.ambientIntensity;
		this.alpha = m.alpha;
		this.refractionIndex = m.refractionIndex;
		this.reflectionIndex = m.reflectionIndex;
		this.shininess = m.shininess;
	}

}
