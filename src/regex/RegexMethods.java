package regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMethods {

    public static List<String> pegaBlocos(String regex, String texto) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(texto);
        List<String> blocos = new ArrayList<>();
        while (matcher.find()) {
            blocos.add(matcher.group());
        }

        return blocos;
    }

    public static void pegaVars(String blocos, List<String> vars) {
        for(String line : blocos.split("\n")){
            if(line.trim().startsWith("vars")){
                vars.add(line);

            }
        }
    }

    public static void pegaParametrosMetodo(String metodo, List<String> metodoParams) {
        Matcher params = Pattern.compile("\\(.*?\\)").matcher(metodo);
        if(params.find()){
            String[] paramsArray = params.group().replace("(", "").replace(")", "").split(",");
            for(String param : paramsArray){
                metodoParams.add(param.trim());
            }
        }
    }


}
