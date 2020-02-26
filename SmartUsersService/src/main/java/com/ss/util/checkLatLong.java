/**SmartSoftware User - Service */
/**
 * Description: checkLatLong
 * Name of Project: SmartSoftware
 * Created on: FEB 10, 2020
 * Modified on: FEB 10, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.util;
import java.util.ArrayList;
import java.util.List;

public class checkLatLong {

    public static List<Coordinate> points = new ArrayList<>();

    static {
    	points.add(new Coordinate(30.7289, 76.7178));
    	points.add(new Coordinate(30.7333, 76.7794));
    	points.add(new Coordinate(30.7040, 76.7296));
    	points.add(new Coordinate(30.5964, 76.8433));
    	points.add(new Coordinate(30.6942, 76.8606));
    }

    public static boolean PointIsInRegion(double x, double y)
    {
        int crossings = 0;
        Coordinate point = new Coordinate(x, y);
        // for each edge
        for (int i = 0; i < points.size(); i++)
        {
        	Coordinate a = points.get(i);
            int j = i + 1;
            if (j >= points.size())
            {
                j = 0;
            }
            Coordinate b = points.get(j);
            if (RayCrossesSegment(point, a, b))
            {
                crossings++;
            }
        }
        // odd number of crossings?
        return (crossings % 2 == 1);
    }

    public static boolean RayCrossesSegment(Coordinate point, Coordinate a, Coordinate b)
    {
        double px = point.getLongitude();
        double py = point.getLatitude();
        double ax = a.getLongitude();
        double ay = a.getLatitude();
        double bx = b.getLongitude();
        double by = b.getLatitude();
        if (ay > by)
        {
            ax = b.getLongitude();
            ay = b.getLatitude();
            bx = a.getLongitude();
            by = a.getLatitude();
        }
        // alter longitude to cater for 180 degree crossings
        if (px < 0) { px += 360; };
        if (ax < 0) { ax += 360; };
        if (bx < 0) { bx += 360; };

        if (py == ay || py == by) py += 0.00000001;
        if ((py > by || py < ay) || (px > Math.max(ax, bx))) return false;
        if (px < Math.min(ax, bx)) return true;

        double red = (ax != bx) ? ((by - ay) / (bx - ax)) : Float.MAX_VALUE;
        double blue = (ax != px) ? ((py - ay) / (px - ax)) : Float.MAX_VALUE;
        return (blue >= red);
    }
    
    public static double getDistanceFromLatLngInKm(Coordinate c1, Coordinate c2) {
        int R = 6371; // Radius of the earth in km

        double lat1 = c1.getLatitude();
        double lat2 = c2.getLatitude();

        double lon1 = c1.getLongitude();
        double lon2 = c2.getLongitude();

        double dLat = deg2rad(lat2-lat1); 
        double dLon = deg2rad(lon2-lon1);

        double a = 
          Math.sin(dLat/2) * Math.sin(dLat/2) +
          Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
          Math.sin(dLon/2) * Math.sin(dLon/2); 

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
        double d = R * c; // Distance in km
        return d;
  }

  public static double deg2rad(double deg) {
        return deg * (Math.PI/180);
  }
    
  public static void main(String[] args) 
  {
	  boolean result= checkLatLong.PointIsInRegion(30.657362558691506, 76.80554088302165);
	  if(!result)
	  {
		  for (int i = 0; i < points.size(); i++)
	      {
			  Coordinate point=points.get(i);
			  Double dis=getDistanceFromLatLngInKm(point,new Coordinate(30.657362558691506,76.80554088302165));
		      if(dis<7){
		    	  System.out.println("Distance is "+dis.intValue()+" Km");
		    	  break;
		      }
	      }
	  }
	  else
	  {
		  System.out.println("Lie In Region");
	  }
   }
  
}