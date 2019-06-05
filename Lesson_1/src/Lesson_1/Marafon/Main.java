package Lesson_1.Marafon;

/*
1. Разобраться с имеющимся кодом;

2. Добавить класс Team, который будет содержать название команды,
массив из четырех участников (в конструкторе можно сразу указыватьвсех участников ),
метод для вывода информации о членах команды, прошедших дистанцию, метод вывода информации обо всех членах команды.

3. Добавить класс Course (полоса препятствий), в котором будут находиться массив препятствий и метод,
который будет просить команду пройти всю полосу;
В итоге должно быть что-то вроде:

public static void main(String[] args) {
Course c = new Course(...); // Создаем полосу препятствий
Team team = new Team(...); // Создаем команду
c.doIt(team); // Просим команду пройти полосу
team.showResults(); // Показываем результаты
*/

public class Main {
    public static void main(String[] args) {
        Course course = new Course(); // Создаем полосу препятствий
        Team team = new Team("Winners"); // Создаем команду
        course.doIt(team); // Просим команду пройти полосу
        team.showResults(); // Показываем результаты





    /*
    public static void main(String[] args) {
        Competitor[] competitors = {new Human("Боб"), new Cat("Барсик"), new Dog("Бобик")};
        Obstacle[] course = {new Cross(80), new Wall(2), new Wall(1), new Cross(120)};
        for (Competitor c : competitors) {
            for (Obstacle o : course) {
                o.doIt(c);
                if (!c.isOnDistance()) break;
            }
        }
        for (Competitor c : competitors) {
            c.info();
        }
    }
*/
    }
}