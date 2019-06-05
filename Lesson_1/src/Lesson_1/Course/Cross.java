package Lesson_1.Course;

import Lesson_1.Marafon.Competitor;
import Lesson_1.Marafon.Obstacle;

public class Cross extends Obstacle {
    int length;

    public Cross(int length) {
        this.length = length;
    }

    @Override
    public void doIt(Competitor competitor) {
        competitor.run(length);
    }
}