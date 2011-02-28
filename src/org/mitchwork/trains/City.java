package org.mitchwork.trains;

import java.util.Vector;

class City implements Comparable<City>
{
    public final String name;
    public Vector<Route> neighbours;
    public double minDistance = Double.POSITIVE_INFINITY;
    public City previous;
    public City(String argName) { name = argName; }
    public String toString() { return name; }
    public int compareTo(City other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}
