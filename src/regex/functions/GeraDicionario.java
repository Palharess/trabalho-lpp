package regex.functions;

import regex.ClassRegex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeraDicionario {
    private static Map<Class<?>, String> dicionario = new HashMap<>();
    static {
        try {
            dicionario.put(ClassRegex.class, ClassRegex.class.getField("CLASS_REGEX").get(ClassRegex.class).toString());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

    public static Map<Class<?>, String> getDicionario() {
        return dicionario;
    }






}
