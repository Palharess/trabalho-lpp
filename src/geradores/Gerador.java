package geradores;

import enums.Cmp;
import objetos.Ifs.If;
import objetos.Ifs.IfStmt;
import objetos.Ifs.PrototypeIfStmt;
import objetos.Methods.MethodBody;
import objetos.Methods.MethodCall;
import objetos.Nomes;
import objetos.NomesLista;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gerador {

    public static MethodBody gerarMethodBody(String methodBody) {
        MethodBody method =null;
        for(String line : methodBody.split("\n")){
            int dentroIf = 0;
            if(line.trim().startsWith("if")){
                dentroIf++;
                Pattern ifPattern = Pattern.compile("(?s)\\bif\\b.*?\\bend-if\\b");
                Matcher ifMatcher = ifPattern.matcher(methodBody);

                if(ifMatcher.find()) {
                    If ifStatement = gerarIf(ifMatcher.group());
                }





            }

        }
        return method;
    }

    public static If gerarIf(String texto){
        String linha = texto.split("\n")[0];

        If ifStatement = null;
        Cmp comparador = null;
        Nomes nomeEsquerda, nomeDireita;
        List<IfStmt> thenStmts = new ArrayList<>();
        List<IfStmt> elseStmts = new ArrayList<>();

        String[] split = linha.trim().split(" ");
        comparador = Cmp.valueOf(split[2]);
        nomeEsquerda = new Nomes(split[1]);
        nomeDireita = new Nomes(split[3]);

        String ifBody = texto.split("then")[1];

        gerarIfStmt(ifBody);



        return ifStatement;
    }

    private static void gerarIfStmt(String Texto){
        for (String line : Texto.split("\n")) {
            if (line.trim().contains("prototype")) {
                PrototypeIfStmt proto = gerarPrototype(line);
            } else if (line.trim().contains("(") && line.trim().contains(")")) {
                gerarMethodCall(line);
            } else if (line.trim().startsWith("return")) {
                //gerarReturn(line);
            }
        }
    }

    private static MethodCall gerarMethodCall(String line) {
        String[] formated = line.trim().split("\\(");
        String[] params = formated[1].replace(")", "").split(",");
        List<Nomes> parametros = new ArrayList<>();
        for (String param : params) {
            parametros.add(new Nomes(param));

        }

        String[] partes = formated[0].split("\\.");


        MethodCall methodCall = new MethodCall(new Nomes(partes[0]), new Nomes(partes[1]), new NomesLista(parametros));
        methodCall.append_result();
        return  null;


    }

    private static PrototypeIfStmt gerarPrototype(String line) {
        String[] split = line.trim().split(" ");
        Nomes nome = new Nomes(split[0].replace("._prototype", ""));

        Nomes valor = new Nomes(split[2]);
        PrototypeIfStmt proto = new PrototypeIfStmt(nome, valor);
        proto.append_result();
        return proto;
    }
}
