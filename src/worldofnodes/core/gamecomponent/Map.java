package worldofnodes.core.gamecomponent;

import java.util.Vector;

/**
 * Created by beenotung on 12/19/14.
 */
public class Map {
    public NodeManager nodeManager;
    public AntManager antManager;
    
    public int xMin,yMin,xMax,yMax;

    public Map(int xMin, int yMin, int xMax, int yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
        nodeManager=new NodeManager();
        antManager=new AntManager();
    }


    public void init(int numNode){
                nodeManager.init(numNode);
        antManager.init();
    }
}
