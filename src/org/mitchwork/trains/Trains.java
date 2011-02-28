package org.mitchwork.trains;

import java.util.*;

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

    public static void main(String[] args)
    {
        City a = new City("A");
        City b = new City("B");
        City c = new City("C");
        City d = new City("D");
        City e = new City("E");
        a.neighbours = new Vector<Route>();
        a.neighbours.add(new Route(b, 5));
        a.neighbours.add(new Route(d, 5));
        a.neighbours.add(new Route(e, 7));
        b.neighbours = new Vector<Route>();
        b.neighbours.add(new Route(c, 4));
        c.neighbours = new Vector<Route>();
        c.neighbours.add(new Route(d, 8));
        c.neighbours.add(new Route(e, 2));
        d.neighbours = new Vector<Route>();
        d.neighbours.add(new Route(c, 8));
        d.neighbours.add(new Route(e, 6));
        e.neighbours = new Vector<Route>();
        e.neighbours.add(new Route(b, 3));

        City[] vertices = { a, b, c, d, e };
        HashMap<String,City> edges = new HashMap<String,City>();
        for (City _city : vertices) {
            edges.put(_city.name,_city);
        }
        String[] cities = new String[vertices.length];
        for (int i = 0;i < vertices.length;i++) {
            cities[i] = vertices[i].name;
        }

        computePaths(a);
        /**
        for (City v : vertices) {
            System.out.println("Distance to " + v + ": " + v.minDistance);
            List<City> path = getShortestPathTo(v);
            System.out.println("Path: " + path);
        }
        */
        Scanner scanner = new Scanner(System.in);
        String input = null;
        String[] inputsplit = null;
        while (true) {
            input = scanner.next();
            if (input.equalsIgnoreCase("quit")) {
                break;
            }
            inputsplit = input.split("-");
            Trip trip = new Trip();
            Route lastvisited = trip.route.lastElement();
            for (String str :inputsplit) {
                System.out.print(str + ' ');
                City city;
                if (edges.containsKey(str)) {
                    city = edges.get(str);
                } else {
                    System.out.println("NO SUCH City"); // TODO change name
                    break;
                }

                if (trip.route == null) {
                    trip.addRoute(edges.get(city.name));
                }

                System.out.println(trip.toString());
            }
            System.out.println(trip.distance);

        }

    }

}