package ch.epfl.cs107.play.game.actor;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Emitter implements Actor {

	// Particles
	private List<Particle> particles;
	private int particleLimit;
	
	// Physical representation
	private Vector position;
	private Shape shape;
	private Graphics graphics;
	
	/**
	 * Creates a new Emitter without a graphical representation
	 * @param shape : shape of emitter
	 * @param position : absolute position of emitter
	 * @param particleLimit : max number of particles
	 */
	public Emitter(Shape shape, Vector position, int particleLimit) {
		particles = new ArrayList<>();
		this.shape = shape;
		this.position = position;
		this.particleLimit = particleLimit;
	}
	
	protected void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
	
	@Override
	public void draw(Canvas canvas) {
		// Draw particles
		for (Particle particle : particles) {
			particle.draw(canvas);
		}
		
		// If emitter has a graphical representation, draw it
		if (graphics != null) {
			graphics.draw(canvas);
		}
	}
	
	@Override
	public void update(float deltaTime) {
		
		// Remove expired particles
		List<Particle> removeQueue = new ArrayList<>();
		for (Particle particle : particles) {
			if (particle.isExpired()) {
				removeQueue.add(particle);
			}
		}
		for (Particle particle : removeQueue) {
			particles.remove(particle);
		}
		
		// Make new particles
		// Passes max number of particles to createParticles
		if (particles.size() < particleLimit) {
			createParticles(particleLimit - particles.size());
		}
		
		// Update particles
		for (Particle particle : particles) {
			particle.update(deltaTime);
		}
	}
	
	/**
	 * Creates n particles and adds them to the list
	 * @param number
	 */
	protected abstract void createParticles(int number);
	
	/**
	 * Adds particle to list of particles
	 * @param particle
	 */
	protected void addParticle(Particle particle) {
		if (particles.size() < particleLimit) {
			particles.add(particle);
		}
	}
	
	/**
	 * @return the shape of the particle (used in createParticles)
	 */
	protected Shape getShape() {
		return shape;
	}
	
	@Override
	public Transform getTransform() {
		return Transform.I.translated(position);
	}

	@Override
	public Vector getVelocity() {
		return Vector.ZERO;
	}
}
