package main;

import geradores.Gerador;
import objetos.Methods.MethodBody;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
            List<String> metodosParams = new ArrayList<>();


            pegaVars(blocos, classVars);
            addClass(blocos, classVars);
            String newLine;
            int i = 0;
            for(String metodo : metodos){
                blocos = blocos.replace(metodo, "");

                addMethodLinha(metodo, metodosParams);

                List<String> metodoVars = new ArrayList<>();
                pegaVarsMetodo(metodo, metodoVars);
                for(String var : metodoVars){
                    addLinha(var);
                }

                List<String> metodoParams = new ArrayList<>();
                pegaParametrosMetodo(metodo, metodoParams);

                List<String> metodoBody = pegaBlocos("begin[\\s\\S]+?end-method", metodo);
                newLine = "begin";
                addLinha(newLine);


                MethodBody methodBody = Gerador.gerarMethodBody(metodoBody.get(0));


                i++;


            newLine = "end-method";
            addLinha(newLine);

            }



            String terminou = "end-class";
            addLinha(terminou);

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

        System.out.println(mainVars);
        //aqui começa o main
        addLinha("main()");
        for(String var : mainVars){
            addLinha(var);
        }
        List<String> mainBody = pegaBlocos("begin[\\s\\S]+end", main.get(0));
        System.out.println(mainBody.getFirst());
        addLinha("begin");
        for(String linha: mainBody){
            Gerador.gerarMethodBody(linha);
        }
        addLinha("end");




    }

    public static void addMethodLinha(String metodo, List<String> metodosParams) {
        String newLine;
        String nomeMetodo = metodo.split("\\(")[0].split("method")[1].trim();
        newLine = "method " + nomeMetodo +"(";
        pegaParametrosMetodo(metodo, metodosParams);
        if(metodosParams.size() > 0){
            newLine += metodosParams.get(0);
            for(int j = 1; j < metodosParams.size(); j++){
                newLine += ", " + metodosParams.get(j);
            }
        }
        newLine += ")";
        addLinha(newLine);
        metodosParams.clear();
    }

    public static void addClass(String blocos, List<String> classVars) {
        String nomeClasse = blocos.split("\n")[0].split("class")[1].trim();
        String newLine = "class " + nomeClasse;
        for(String var : classVars){
            newLine += "\n" + var;
        }
        addLinha(newLine);
    }

    public static int addLinha(String newLine) {
        try (FileWriter fw = new FileWriter("src/resultado.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {


            bw.write(newLine);
            bw.newLine();
            List<String> lines = Files.readAllLines(Paths.get("src/resultado.txt"));
            int currentLineCount = lines.size();
            System.out.println("Linha adicionada com sucesso!");
            return currentLineCount;

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            return -1;
        }
    }

    public static void updateLineByIndex(String filePath, int lineIndex, String newLineContent) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            if (lineIndex >= 0 && lineIndex < lines.size()) {
                lines.set(lineIndex, newLineContent);

                Files.write(Paths.get(filePath), lines);
                System.out.println("Line at index " + lineIndex + " updated successfully.");
            } else {
                System.out.println("Invalid line index: " + lineIndex);
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }


}