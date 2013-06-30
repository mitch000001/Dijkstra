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
    public boolean followsOn(City city) {
        for (Route r : neighbours) {
            if (r.target.equals(city)) {
                return true;
            }
        }
        return false;
    }
    public boolean followsOn(String cityname) {
        for (Route r : neighbours) {
            if (r.target.name.equals(cityname)) {
                return true;
            }
        }
        return false;
    }
}
