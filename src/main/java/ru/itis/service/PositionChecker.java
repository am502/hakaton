package ru.itis.service;

import ru.itis.ApolloniusSolver;
import ru.itis.dao.DistanceDao;
import ru.itis.models.Circle;
import ru.itis.models.Distance;
import ru.itis.models.Point;

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

    public Point getLastPosition() {
        List<Distance> distances = distanceDao.getLastDistances();
        Point result = calculateThreeCircleIntersection(distances.get(0).getDist(),
                distances.get(1).getDist(), distances.get(2).getDist());
        return result;
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
