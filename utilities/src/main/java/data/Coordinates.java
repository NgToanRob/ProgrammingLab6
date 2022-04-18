package data;

import java.io.Serializable;

/**
 * Coordinates is a class that has two fields: x and y
 */
public class Coordinates implements Serializable {
    private int x;
    private float y;

    public Coordinates(int x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of the organization
     *
     * @return The x coordinate of the organization.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the organization
     *
     * @return The y-coordinate of the organization.
     */
    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int hashCode() {
        return ((Float) y).hashCode() + (int) x;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Coordinates) {
            Coordinates coordinatesObj = (Coordinates) obj;
            return (x == coordinatesObj.getX()) && ((Float) y).equals(coordinatesObj.getY());
        }
        return false;
    }
}
