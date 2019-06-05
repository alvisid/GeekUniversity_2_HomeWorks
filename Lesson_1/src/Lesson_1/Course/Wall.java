package Lesson_1.Course;

import Lesson_1.Marafon.Competitor;
import Lesson_1.Marafon.Obstacle;

public class Wall extends Obstacle {
    int height;

    public Wall(int height) {
        this.height = height;
    }

    @Override
    public void doIt(Competitor competitor) {
        competitor.jump(height);
    }
}