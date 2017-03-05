/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author Mike
 */
public class Spatial {

    public double x;
    public double y;
    public double z;

    /**
     * default constructor, zero in all dimensions
     */
    public Spatial() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * constructor with dimensions specified for container class spatial
     *
     * @param x
     * @param y
     * @param z
     */
    public Spatial(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    ///////////////////////// END CONSTRUCTORS/////////////////////
    public Spatial copy() {
        return new Spatial(this.x, this.y, this.z);
    }

    @Override
    public String toString() {
        String s = "";
        s += "X: " + this.x + "\n";
        s += "Y: " + this.y + "\n";
        s += "Z: " + this.z + "\n\n";
        return s;
    }

    public void add(Spatial s) {
        this.x += s.x;
        this.y += s.y;
        this.z += s.z;
    }

    public void add(double d) {
        this.x += d;
        this.y += d;
        this.z += d;
    }

    public void add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void multiply(Spatial s) {
        this.x *= s.x;
        this.y *= s.y;
        this.z *= s.z;
    }

    public void multiply(double d) {
        this.x *= d;
        this.y *= d;
        this.z *= d;
    }

    public void multiply(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
    }
}
