package com.snad.convexhullvisualizer;

/**
 * Created by Horatiu on 20/06/2016.
 */
public class Hull {
    private Coordinate [] points;

    public Hull(Coordinate[] points){
        this.points = points;
    }

    public Coordinate [] getPoints(){
        return points;
    }
}
