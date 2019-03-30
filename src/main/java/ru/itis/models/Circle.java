package ru.itis.models;

public class Circle {
    public double[] center;
    public double radius;

    public Circle(double[] center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public String toString() {
        return String.format("Circle[x=%.2f,y=%.2f,r=%.2f]", center[0], center[1],
                radius);
    }
}