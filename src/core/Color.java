package core;

import java.io.Serializable;

/**
 * Source class color. Enum with all Color constants
 */
public enum Color implements Serializable {
    GREEN,
    RED,
    BLACK,
    BLUE,
    WHITE;
    private static final long serialVersionUID = 20L;
    /**
     * Get all variants of color names.
     * @return answer
     */
    public static String getNames(){
        String answer = "";
        for (Color c: Color.values()){
            answer += c.name() + ", ";
        }
        return answer.substring(0, answer.length() - 2);
    }
}
