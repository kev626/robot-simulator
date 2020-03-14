package to.us.kevinraneri.pathtracker.simulator.entity;

import lombok.Getter;
import lombok.Setter;
import org.newdawn.slick.Graphics;
import to.us.kevinraneri.pathtracker.simulator.util.MathUtil;

@Getter
@Setter
public abstract class Entity {

    protected int x;
    protected int y;
    protected double angle;

    public abstract void render(Graphics g);

    public void setAngle(double angle) {
        this.angle = MathUtil.wrapAngle(angle);
    }

}
