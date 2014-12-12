package worldofnodes.core.gamecomponent;

import myutils.Vector2D;
import worldofnodes.core.graph.Vertex;

import java.awt.*;

/**
 * Created by beenotung on 12/10/14.
 */
public class Ant {
    public static final String STATE_MOVING = "State: Moving";
    public static final String STATE_ARRIVED = "State: Arrived";
    public static final int DEFAULT_WIDTH = 5;
    public static final int DEFAULT_HEIGHT = 5;
    public static final Color DEFAULT_COLOR = Color.red;
    public String state;
    public Vector2D location, velocity;
    public Vertex destVertex, srcVertex;
    public Color color;
    public int width, height;

    public Ant() {
        super();
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        color = DEFAULT_COLOR;
        state = STATE_ARRIVED;
    }

    public Ant(Vertex srcVertex) {
        this();
        this.srcVertex = srcVertex;
        location = srcVertex.location.clone();
    }

    public void tick() {
        switch (state) {
            case STATE_ARRIVED:
                destVertex = srcVertex.getRandomNeighbours();
                state = STATE_MOVING;
                break;
            case STATE_MOVING:
                velocity
                        = Vector2D.subtract(destVertex.location, location);
                velocity.max(1f);
                if (velocity.getMagnitude() == 0) state = STATE_ARRIVED;
                location = location.add(location, velocity);
                break;
        }
    }
}