package ru.itis.service;

import ru.itis.ApolloniusSolver;
import ru.itis.dao.DistanceDao;
import ru.itis.models.Circle;
import ru.itis.models.Distance;
import ru.itis.models.Point;

import java.util.ArrayList;
import java.util.List;

public class PositionChecker {
    private DistanceDao distanceDao;
    private double EPSILON = 0.0001;
    public static double x0 = 0;
    public static double y0 = 0;
    public static double x1 = 4.4;
    public static double y1 = 0;
    public static double x2 = 4.4;
    public static double y2 = 7;

    public PositionChecker() {
        distanceDao = new DistanceDao();
    }

    public Circle getLastPosition() {
        final List<Distance> distances = distanceDao.getLastDistances();
        Circle result = cos(new ArrayList<Circle>() {{
            add(new Circle(new double[]{x0, y0}, distances.get(0).getDist()));
            add(new Circle(new double[]{x1, y1}, distances.get(1).getDist()));
            add(new Circle(new double[]{x2, y2}, distances.get(2).getDist()));
        }});
        return result;
    }

    private Circle cos(List<Circle> circles) {
        double cosA = (4.4 * 4.4 + circles.get(0).radius * circles.get(0).radius - circles.get(1).radius * circles.get(1).radius) /
                (2 * 4.4 * circles.get(0).radius);

        double cosB = (7 * 7 + circles.get(2).radius * circles.get(2).radius - circles.get(1).radius * circles.get(1).radius) /
                (2 * 7 * circles.get(2).radius);
        double x = cosA * circles.get(0).radius;
        double y = cosB * circles.get(2).radius;
        return new Circle(new double[]{x, 7-y}, 5);
    }

    private Circle find(List<Circle> circles) {
        double W, Z, x, y, y2;
        W = circles.get(0).radius * circles.get(0).radius - circles.get(1).radius * circles.get(1).radius - circles.get(0).center[0] * circles.get(0).center[0] - circles.get(0).center[1] * circles.get(0).center[1] + circles.get(1).center[0] * circles.get(1).center[0] + circles.get(1).center[1] * circles.get(1).center[1];
        Z = circles.get(1).radius * circles.get(1).radius - circles.get(2).radius * circles.get(2).radius - circles.get(1).center[0] * circles.get(1).center[0] - circles.get(1).center[1] * circles.get(1).center[1] + circles.get(2).center[0] * circles.get(2).center[0] + circles.get(2).center[1] * circles.get(2).center[1];

        x = (W * (circles.get(2).center[1] - circles.get(1).center[1]) - Z * (circles.get(1).center[1] - circles.get(0).center[1])) / (2 * ((circles.get(1).center[0] - circles.get(0).center[0]) * (circles.get(2).center[1] - circles.get(1).center[1]) - (circles.get(2).center[0] - circles.get(1).center[0]) * (circles.get(1).center[1] - circles.get(0).center[1])));
        y = (W - 2 * x * (circles.get(1).center[0] - circles.get(0).center[0])) / (2 * (circles.get(1).center[1] - circles.get(0).center[1]));
        //y2 is a second measure of y to mitigate errors
        y2 = (Z - 2 * x * (circles.get(2).center[0] - circles.get(1).center[0])) / (2 * (circles.get(2).center[1] - circles.get(1).center[1]));

        y = (y + y2) / 2;
        return new Circle(new double[]{x, y}, 5);
    }

    private Circle findCenter(List<Circle> circles) {
        double top = 0;
        double bot = 0;
        for (int i = 0; i < 3; i++) {
            Circle c = circles.get(i);
            Circle c2, c3;
            if (i == 0) {
                c2 = circles.get(1);
                c3 = circles.get(2);
            } else if (i == 1) {
                c2 = circles.get(0);
                c3 = circles.get(2);
            } else {
                c2 = circles.get(0);
                c3 = circles.get(1);
            }

            double d = c2.center[0] - c3.center[0];

            double v1 = (c.center[0] * c.center[0] + c.center[1] * c.center[1]) - (c.radius * c.radius);
            top += d * v1;

            double v2 = c.center[1] * d;
            bot += v2;

        }

        double y = (double) top / (2 * bot);
        Circle c1 = circles.get(0);
        Circle c2 = circles.get(1);
        top = c2.radius * c2.radius + c1.center[0] * c1.center[0] + c1.center[1] * c1.center[1] -
                c1.radius * c1.radius - c2.center[0] * c2.center[0] - c2.center[1] * c2.center[1] - 2 * (c1.center[1] - c2.center[1]) * y;
        bot = c1.center[0] - c2.center[0];
        double x = top / (2 * bot);

        return new Circle(new double[]{x, y}, 5);

    }

    private Point calculateThreeCircleIntersection(double r0,
                                                   double r1,
                                                   double r2) {
        double a, dx, dy, d, h, rx, ry;
        double point2_x, point2_y;

        /* dx and dy are the vertical and horizontal distances between
         * the circle centers.
         */
        dx = x1 - x0;
        dy = y1 - y0;

        /* Determine the straight-line distance between the centers. */
        d = Math.sqrt((dy * dy) + (dx * dx));

        /* Determine the distance from point 0 to point 2. */
        a = ((r0 * r0) - (r1 * r1) + (d * d)) / (2.0 * d);

        /* Determine the coordinates of point 2. */
        point2_x = x0 + (dx * a / d);
        point2_y = y0 + (dy * a / d);

        /* Determine the distance from point 2 to either of the
         * intersection points.
         */
        h = Math.sqrt((r0 * r0) - (a * a));

        /* Now determine the offsets of the intersection points from
         * point 2.
         */
        rx = -dy * (h / d);
        ry = dx * (h / d);

        /* Determine the absolute intersection points. */
        double intersectionPoint1_x = point2_x + rx;
        double intersectionPoint2_x = point2_x - rx;
        double intersectionPoint1_y = point2_y + ry;
        double intersectionPoint2_y = point2_y - ry;

        /* Lets determine if circle 3 intersects at either of the above intersection points. */
        dx = intersectionPoint1_x - x2;
        dy = intersectionPoint1_y - y2;
        double d1 = Math.sqrt((dy * dy) + (dx * dx));

        dx = intersectionPoint2_x - x2;
        dy = intersectionPoint2_y - y2;
        double d2 = Math.sqrt((dy * dy) + (dx * dx));

        return Point.builder().x(d1).y(d2).build();
    }
}
