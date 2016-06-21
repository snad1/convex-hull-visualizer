package ca.horatiu.convex_hull_visualizer;

import android.util.Log;

import java.util.Arrays;
import java.util.Stack;

/**
 * Created by Horatiu on 18/06/2016.
 */
public class GrahamScan {
    private Coordinate [] arr;

    public GrahamScan(Coordinate [] points){
        this.arr = points;
    }

    public Coordinate [] solve(){
        int N = arr.length;
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int value = 0; //minimum value
        for(int x = 0; x < arr.length; x++){
            if (arr[x].getY() < minY || (arr[x].getY() == minY && arr[x].getX() < minX)){
                minY = arr[x].getY();
                minX = arr[x].getX();
                value = x;
            }
        }
        //You got the starting point, now you have to sort everything by the y coordinate!

        for(int x = 0; x < arr.length; x++){
            if (x == value)
                continue;
            if (arr[x].getX() == minX){
                arr[x].setAngle(0); //fix 0 -> 90 WAIT .. THIS SHOULD BE 90 FIX?
            }
            else{
                arr[x].setAngle(Math.atan2((double)(arr[x].getY()-minY), (double)(arr[x].getX() - minX)));
            }
        }

        Arrays.sort(arr);
        for(int x = 0; x < arr.length; x++){
            if (arr[x].getAngle() != -Double.MAX_VALUE){
                //System.out.println(arr[x]);
            }
            else{
                //Log.d("Gr", arr[x].toString());
            }
        }

        Stack<Coordinate> Q = new Stack<Coordinate>(); //perhaps remove the point (5,0)?
        Q.push(arr[0]);
        Q.push(arr[1]);
        for(int x = 2; x < N; x++){
            Q.push(arr[x]);
            if (N == 3) //odd..
                break;
            while(true){
                //check to see if arr[0]arr[1] vector angle with arr[1][2] is a right
                if (Q.size() < 3)
                    break;
                Coordinate p2 = Q.pop();
                Coordinate p1 = Q.pop();
                Coordinate p0 = Q.pop();
                Vector p0p1 = new Vector(p1.getX()-p0.getX(), p1.getY()-p0.getY());
                Vector p1p2 = new Vector(p2.getX()-p1.getX(), p2.getY()-p1.getY());
                if (p0p1.turnsLeft(p1p2)){
                    Q.push(p0);
                    Q.push(p1);
                    Q.push(p2);
                    break;
                }
                else{
                    //remove the last candidate
                    Q.push(p0);
                    Q.push(p2);
                }
            }
        }

        int size = Q.size();
        Coordinate [] hull = new Coordinate[size];
        for(int x = 0; x < size; x++)
            hull[x] = Q.pop();
        return hull;
    }
}
