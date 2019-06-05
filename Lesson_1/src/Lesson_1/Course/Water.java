package Lesson_1.Course;

import Lesson_1.Marafon.Competitor;
import Lesson_1.Marafon.Obstacle;

public class Water extends Obstacle {
    int length;

    public Water(int length) {
        this.length = length;
    }

    @Override
    public void doIt(Competitor competitor) {
        competitor.swim(length);
    }
}