package ch.epfl.cs107.play.game.actor.particles;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Particle;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class GravityWellParticle extends Particle {
	
	private ShapeGraphics graphics;
	private static final String COLOR = "0xf2dc5d";
	
	private float force;
	private float angle;
	private float length;
	
	/**
	 * Creates a new GravityWellParticle
	 * @param position
	 * @param force : the norm of the force of the gravity well
	 * @param angle : the direction of the gravity well
	 * @param length : the distance the particle has to travel
	 */
	public GravityWellParticle(Vector position, float force, float angle, float length) {
		super(position, Vector.ZERO, calculateAcceleration(force, angle), angle, 0.0f, 0.0f, false);
		
		this.force = force;
		this.angle = angle;
		this.length = length;
		
		// Make graphics
		Polyline particleShape = new Polyline(new Vector(0.0f, 0.0f), new Vector(0.1f, 0.0f));
		graphics = new ShapeGraphics(particleShape, Color.decode(COLOR), Color.decode(COLOR), 0.05f, 0.5f, 99.0f);
		graphics.setParent(this);
		
		
		setDuration(calculateDuration());
	}

	public GravityWellParticle(GravityWellParticle other) {
		super(other);
		this.graphics = other.graphics;
	}
	
	/**
	 * Calculates acceleration of particles 
	 * @param force
	 * @param angle
	 * @return the rotated and scaled vector
	 */
	private static Vector calculateAcceleration(float force, float angle) {
		Vector acc = new Vector(1.0f, 0.0f);
		acc = acc.rotated(angle);
		acc = acc.mul(force);
		return acc;
	}
	
	/**
	 * Returns the duration of the particle such that it expires at the edge of the gravity well
	 * @return the duration in seconds
	 */
	private float calculateDuration() {
		float acceleration = calculateAcceleration(force, angle).getLength();
		
		return (float)Math.sqrt(2.0f*length/acceleration);
	}
	
	@Override
	public void draw(Canvas canvas) {
		if (!isExpired()) {
			graphics.draw(canvas);
		}
	}

	@Override
	public Particle copy() {
		return new GravityWellParticle(this);
	}

}