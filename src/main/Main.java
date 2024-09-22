package main;

import geradores.Gerador;
import objetos.Methods.MethodBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static regex.RegexMethods.*;

public class Main {

    public static void main(String[] args) {
        String filePath = "src/teste.txt";
        String content = "";


        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.printf("Error reading the file: %s.\n", e.getMessage());
        }

        List<String> main = pegaBlocos("main\\(\\)[\\s\\S]+?end$", content);
        List<String> classes = pegaBlocos("(?s)class\\s+\\w+.*?end-class", content);
        List<String> mainVars = new ArrayList<>();



        for (String blocos : main) {
            for(String line : blocos.split("\n")){
                if(line.trim().startsWith("vars")){
                    mainVars.add(line);

                }
            }
        }
        for(String blocos : classes){
            List<String> classVars = new ArrayList<>();
            List<String> metodos = pegaBlocos("method\\s+\\w+\\s*\\([^)]*\\)[\\s\\S]+?end-method", blocos);

            for(String metodo : metodos){
                blocos = blocos.replace(metodo, "");

                String nomeMetodo = metodo.split("\\(")[0].split("method")[1].trim();

                List<String> metodoVars = new ArrayList<>();
                pegaVars(metodo, metodoVars);

                List<String> metodoParams = new ArrayList<>();
                pegaParametrosMetodo(metodo, metodoParams);

                List<String> metodoBody = pegaBlocos("begin[\\s\\S]+?end-method", metodo);


                MethodBody methodBody = Gerador.gerarMethodBody(metodoBody.get(0));




            }

            pegaVars(blocos, classVars);

        }



//        while (classes.find()) {
//            String classContent = classes.group();
//            String[] classLines = classContent.split("\n");
//
//            for (String line : classLines) {
//                if (line.trim().startsWith("vars")) {
//                    classVars.add(line);
//
//                }
//            }
//        }
//        mainVars.forEach(System.out::println);
    }




}