package core;

import java.io.Serializable;

/**
 * Source class. Enum with all Difficulty level constants
 */
public enum Difficulty implements Serializable {
    VERY_EASY,
    NORMAL,
    HOPELESS,
    TERRIBLE;
    private static final long serialVersionUID = 22L;
    public static String getNames(){
        String answer = "";
        for (Difficulty d: Difficulty.values()){
            answer += d.name() + ", ";
        }
        return answer.substring(0, answer.length() - 2);
    }
}
