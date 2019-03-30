package ru.itis.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.models.Point;

import java.util.Random;

@RestController
public class SimpleController {

    @RequestMapping(value = "/point", method = RequestMethod.GET)
    public Point[] getRandomPoint() {
        Point[] points = new Point[5];

        points[0] = Point.builder()
                .x(312)
                .y(68)
                .build();

        points[1] = Point.builder()
                .x(313)
                .y(79)
                .build();

        points[2] = Point.builder()
                .x(321)
                .y(67)
                .build();

        points[3] = Point.builder()
                .x(321)
                .y(80)
                .build();

        points[4] = Point.builder()
                .x(317)
                .y(73)
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

        Random random = new Random();
        points[4].setX(random.nextInt(10) * xScale);
        points[4].setY(random.nextInt(10) * yScale);

        return points;
    }
}
