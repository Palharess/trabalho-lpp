import regex.ClassRegex;
import regex.Regex;
import regex.functions.GeraDicionario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Map<Class<?>, String> dicionario = GeraDicionario.getDicionario();



        String nome = "src/teste.txt";

        try {
            FileReader arq = new FileReader(nome);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            while (linha != null) {
                for (Map.Entry<Class<?>, String> entry : dicionario.entrySet()) {
                    String regex = entry.getValue();
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(linha);
                    if (matcher.find()) {

                        Class<?> classe = entry.getKey();
                        Regex obj = (Regex) classe.getDeclaredConstructor().newInstance();
                        obj.criaObjeto();


                    }
                }

                linha = lerArq.readLine();
            }

            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
