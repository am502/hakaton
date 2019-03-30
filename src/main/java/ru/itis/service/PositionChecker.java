package ru.itis.service;

import ru.itis.ApolloniusSolver;
import ru.itis.dao.DistanceDao;
import ru.itis.models.Circle;
import ru.itis.models.Distance;

import java.util.List;

public class PositionChecker {
    private DistanceDao distanceDao;
    public static double x1 = 0;
    public static double y1 = 0;
    public static double x2 = 4.4;
    public static double y2 = 0;
    public static double x3 = 4.4;
    public static double y3 = 7;

    public PositionChecker() {
        distanceDao = new DistanceDao();
    }

    public Circle getLastPosition() {
        List<Distance> distances = distanceDao.getLastDistances();
        Circle result = ApolloniusSolver.solveApollonius(
                new Circle(new double[]{x1, y1}, distances.get(0).getDist()),
                new Circle(new double[]{x2, y2}, distances.get(0).getDist()),
                new Circle(new double[]{x3, y3}, distances.get(0).getDist()),
                -1, -1, -1);
        return result;
    }
}
