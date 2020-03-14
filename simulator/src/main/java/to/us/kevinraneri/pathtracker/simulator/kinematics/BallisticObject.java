package to.us.kevinraneri.pathtracker.simulator.kinematics;

import lombok.Getter;
import org.newdawn.slick.geom.Vector2f;

import static to.us.kevinraneri.pathtracker.simulator.util.MathUtil.sameSign;

@Getter
public class BallisticObject {

    public float x, y;
    public Vector2f velocity = new Vector2f();
    public float mass, g;
    public float frictionCoeff, staticFrictionCoeff;
    public float normalForce;
    public float frictionMagnitude;

    public BallisticObject(float mass, float g, float frictionCoeff, float staticFrictionCoeff) {
        this.mass = mass;
        this.g = g;
        this.frictionCoeff = frictionCoeff;
        this.staticFrictionCoeff = staticFrictionCoeff;

        this.normalForce = mass * g; // Mass and G don't change, so this is constant;
        this.frictionMagnitude = normalForce * frictionCoeff;
    }

    /**
     * Updates kinematics calculations
     * @param forceX Input force X
     * @param forceY Input force Y
     * @param timeDelta Time (in seconds) since last call
     * @return A vector representing current velocity
     */
    public Vector2f update(float forceX, float forceY, float timeDelta) {
        Vector2f forceVector = new Vector2f(forceX, forceY);
        if (velocity.length() < 0.01) {
            // apply static friction
            float staticMax = normalForce * staticFrictionCoeff;

            // No force if force vector is smaller than static friction
            if (staticMax >= forceVector.length()) {
                forceVector = new Vector2f();
            }
            // no friction force in any case. It's just a cap for starting.
        }

        Vector2f frictionForceVector = velocity.negate()
                .normalise()
                .scale(frictionMagnitude);

        forceVector.add(frictionForceVector);

        float accelX = forceVector.x / mass;
        float accelY = forceVector.y / mass;

        float deltaVX = accelX * timeDelta;
        float deltaVY = accelY * timeDelta;

        float oldVX = velocity.x;
        float oldVY = velocity.y;

        float newVX = velocity.x + deltaVX;
        float newVY = velocity.y + deltaVY;

        if (!sameSign(oldVX, newVX)) newVX = 0;
        if (!sameSign(oldVY, newVY)) newVY = 0;

        velocity.x = newVX;
        velocity.y = newVY;

        if (Math.abs(velocity.x) < 0.2) velocity.x = 0;
        if (Math.abs(velocity.y) < 0.2) velocity.y = 0;

        return velocity;
    }

}
