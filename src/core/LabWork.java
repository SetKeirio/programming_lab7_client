package core;

import java.time.ZonedDateTime;

/**
 * Source main class LabWork.
 */
public class LabWork implements Comparable<LabWork>{
    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setMinimalPoint(double minimalPoint) {
        this.minimalPoint = minimalPoint;
    }

    public void setPersonalQualitiesMaximum(long personalQualitiesMaximum) {
        this.personalQualitiesMaximum = personalQualitiesMaximum;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double minimalPoint; //Значение поля должно быть больше 0
    private long personalQualitiesMaximum; //Значение поля должно быть больше 0
    private Difficulty difficulty; //Поле не может быть null
    private Person author; //Поле может быть null

    public LabWork(Integer id, String name, Coordinates coordinates, ZonedDateTime creationDate, double minimalPoint, long personalQualitiesMaximum, Difficulty difficulty, Person author) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.minimalPoint = minimalPoint;

        this.personalQualitiesMaximum = personalQualitiesMaximum;
        this.difficulty = difficulty;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabWork labWork = (LabWork) o;
        return Integer.compare(((LabWork) o).getId(), getId()) == 0;
        //return Double.compare(labWork.minimalPoint, minimalPoint) == 0 && personalQualitiesMaximum == labWork.personalQualitiesMaximum && Objects.equals(id, labWork.id) && Objects.equals(name, labWork.name) && Objects.equals(coordinates, labWork.coordinates) && Objects.equals(creationDate, labWork.creationDate) && difficulty == labWork.difficulty && Objects.equals(author, labWork.author);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "LabWork{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate.toLocalDate() +
                ", minimalPoint=" + minimalPoint +
                ", personalQualitiesMaximum=" + personalQualitiesMaximum +
                ", difficulty=" + difficulty +
                ", author=" + author +
                '}';
    }

    /**
     * Standard comparing algorithm (by id)
     * @param o another labwork
     * @return comparison result
     */
    @Override
    public int compareTo(LabWork o) {
        return id.compareTo(o.getId());
    }
}
