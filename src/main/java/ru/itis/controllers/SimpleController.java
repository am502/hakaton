package ru.itis.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.models.Circle;
import ru.itis.models.Point;
import ru.itis.service.PositionChecker;

import java.util.Arrays;
import java.util.Random;

@RestController
public class SimpleController {
    private static PositionChecker positionChecker = new PositionChecker();

    @RequestMapping(value = "/point", method = RequestMethod.GET)
    public Point[] getRandomPoint() {
        Point[] points = new Point[4];

        points[0] = Point.builder()
                .x(0)
                .y(0)
                .build();

        points[1] = Point.builder()
                .x(4.4)
                .y(0)
                .build();

        points[2] = Point.builder()
                .x(4.4)
                .y(7)
                .build();

        points[3] = Point.builder()
                .x(2)
                .y(3.5)
                .build();

        double xMin = Integer.MAX_VALUE;
        double yMin = Integer.MAX_VALUE;
        double xMax = 0;
        double yMax = 0;
        for (Point point : points) {
            if (point.getX() < xMin) {
                xMin = point.getX();
            }
            if (point.getY() < yMin) {
                yMin = point.getY();
            }
            if (point.getX() > xMax) {
                xMax = point.getX();
            }
            if (point.getY() > yMax) {
                yMax = point.getY();
            }
        }

        double xScale = 960 / (xMax - xMin);
        double yScale = 540 / (yMax - yMin);

        for (Point point : points) {
            point.setX((point.getX() - xMin) * xScale);
            point.setY((point.getY() - yMin) * yScale);
        }

        Circle movingPoint = positionChecker.getLastPosition();
        points[3].setX((movingPoint.center[0]) * xScale);
        points[3].setY((movingPoint.center[1]) * yScale);

        System.out.println(movingPoint);
        return points;
    }
}
