package org.mitchwork.trains;

import java.util.Vector;

class Trip {
    City source;
    Vector<Route> route;
    int distance = 0;

    Trip() {
        this.source = null;
        this.route = null;
        this.distance = 0;
    }
    Trip(City c) {
        this.source = c;
        this.route = null;
        this.distance = 0;
    }
    public void addRoute(City c) {
        if (route == null) {
            this.source = c;
        } else {
            for (Route ro : route.lastElement().target.neighbours) {
                if (ro.target == c) {
                    this.route.add(ro);
                    this.distance += ro.weight;
                }
            }
        }
    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Route r : route) {
            s.append(r.target.name);
        }
        return s.toString();
    }
}
