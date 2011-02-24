package org.mitchwork.trains;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Trains
{
    public static void computePaths(City source)
    {
        source.minDistance = 0.;
        PriorityQueue<City> cityQueue = new PriorityQueue<City>();
	    cityQueue.add(source);
        while (!cityQueue.isEmpty()) {
            City u = cityQueue.poll();
            // Visit each edge exiting u
            for (Route e : u.neighbours) {
                City v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    cityQueue.remove(v);
                    v.minDistance = distanceThroughU ;
                    v.previous = u;
                    cityQueue.add(v);
                }
            }
        }
    }

    public static List<City> getShortestPathTo(City target)
    {
        List<City> path = new ArrayList<City>();
        for (City city = target; city != null; city = city.previous)
            path.add(city);

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args)
    {
        City a = new City("A");
        City b = new City("B");
        City c = new City("C");
        City d = new City("D");
        City e = new City("E");
        a.neighbours = new Route[]{ new Route(b, 5),
                                     new Route(d, 5),
                                     new Route(e, 7)};
        b.neighbours = new Route[]{ new Route(c, 4)};
        c.neighbours = new Route[]{ new Route(d, 8),
                                    new Route(e, 2)};
        d.neighbours = new Route[]{ new Route(c, 8),
                                     new Route(e, 6)};
        e.neighbours = new Route[]{ new Route(b, 3) };

        City[] vertices = { a, b, c, d, e };

        computePaths(a);
        for (City v : vertices) {
            System.out.println("Distance to " + v + ": " + v.minDistance);
            List<City> path = getShortestPathTo(v);
            System.out.println("Path: " + path);
        }
    }
}