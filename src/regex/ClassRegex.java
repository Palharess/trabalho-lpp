package regex;

import objetos.Classes.ClassDef;
import objetos.Nomes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassRegex implements Regex {
    public static final String CLASS_REGEX = "^\\s*class\\s+([a-zA-Z]+)$";
    private Matcher matcher_classe;


    public boolean verifica(String linha){
        Pattern pattern = Pattern.compile(CLASS_REGEX);
        Matcher matcher = pattern.matcher(linha);
        this.matcher_classe = matcher;
        return matcher.find();
    }

    public void criaObjeto(){
        String nome = matcher_classe.group(1);
        Nomes className = new Nomes(nome);
        ClassDef classDef = new ClassDef(className);
        System.out.println("criado");

    }


    public ClassRegex() {
    }









}
