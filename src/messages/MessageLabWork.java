package messages;

import core.Coordinates;
import core.Difficulty;
import core.Person;

import java.io.Serializable;

public class MessageLabWork implements Serializable {

    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private double minimalPoint; //Значение поля должно быть больше 0
    private long personalQualitiesMaximum; //Значение поля должно быть больше 0
    private Difficulty difficulty; //Поле не может быть null
    private Person author; //Поле может быть null

    public MessageLabWork(String name, Coordinates coordinates, double minimalPoint, long personalQualitiesMaximum, Difficulty difficulty, Person author) {
        this.name = name;
        this.coordinates = coordinates;
        this.minimalPoint = minimalPoint;
        this.personalQualitiesMaximum = personalQualitiesMaximum;
        this.difficulty = difficulty;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public double getMinimalPoint() {
        return minimalPoint;
    }

    public long getPersonalQualitiesMaximum() {
        return personalQualitiesMaximum;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Person getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "MessageLabWork{" +
                "name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", minimalPoint=" + minimalPoint +
                ", personalQualitiesMaximum=" + personalQualitiesMaximum +
                ", difficulty=" + difficulty +
                ", author=" + author +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof MessageLabWork){
            MessageLabWork work = (MessageLabWork) o;
            return name.equals(work.getName()) && coordinates.equals(work.getCoordinates())
                    && (minimalPoint == work.getMinimalPoint()) && (personalQualitiesMaximum == work.getPersonalQualitiesMaximum())
                    && difficulty.equals(work.getDifficulty()) && author.equals(work.getAuthor());
        }
        return false;
        //return Double.compare(labWork.minimalPoint, minimalPoint) == 0 && personalQualitiesMaximum == labWork.personalQualitiesMaximum && Objects.equals(id, labWork.id) && Objects.equals(name, labWork.name) && Objects.equals(coordinates, labWork.coordinates) && Objects.equals(creationDate, labWork.creationDate) && difficulty == labWork.difficulty && Objects.equals(author, labWork.author);
    }
}
