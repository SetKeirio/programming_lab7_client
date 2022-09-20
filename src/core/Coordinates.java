package core;

import java.io.Serializable;
import java.util.Objects;

/**
 * Source class Coordinates
 */
public class Coordinates implements Serializable {
    private int x; //Значение поля должно быть больше -697
    private Integer y; //Поле не может быть null
    private static final long serialVersionUID = 21L;
    public Coordinates(int x, Integer y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return y.hashCode() + x % 128;
    }
}
