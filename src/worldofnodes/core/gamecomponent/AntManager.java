package worldofnodes.core.gamecomponent;

import myutils.Vector2D;

import java.util.Vector;

/**
 * Created by beenotung on 12/19/14.
 */
public class AntManager {
    private Vector<Ant> ants;
    public AntManager(){
        ants=new Vector<Ant>();        
    }
    public void addAnt(Ant ant){
        ants.add(ant);        
    }
    public void addNewAnt(){
        ants.add(new Ant());
    }
}
