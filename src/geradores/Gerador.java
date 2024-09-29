package geradores;

import enums.Cmp;
import enums.Op;
import interfaces.CallMethod;
import objetos.Attr;
import objetos.Ifs.*;
import objetos.Lhs;
import objetos.Methods.MethodBody;
import objetos.Methods.MethodCall;
import objetos.Nomes;
import objetos.NomesLista;
import objetos.args.Arg;
import objetos.args.MethodCallArg;
import objetos.args.NameArg;

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

        If IfPai = null;
        Cmp comparador = null;
        Nomes nomeEsquerda, nomeDireita;
        List<IfStmt> thenStmts = new ArrayList<>();
        List<IfStmt> elseStmts = new ArrayList<>();

        String[] split = linha.trim().split(" ");
        comparador = Cmp.valueOf(split[2]);
        nomeEsquerda = new Nomes(split[1]);
        nomeDireita = new Nomes(split[3]);

        String ifThenBody = texto.split("then")[1].split("else")[0];
        String ifElseBody = texto.split("else")[1];

        IfPai = new If(nomeEsquerda, comparador, nomeDireita, thenStmts, elseStmts);

        for(String line : ifThenBody.split("\n")){

            if(!line.trim().isEmpty()){
                thenStmts.add(gerarIfStmt(line));
            }

        }


        return IfPai;
    }

    private static IfStmt gerarIfStmt(String Texto){
        for (String line : Texto.split("\n")) {
            if (line.trim().contains("prototype")) {
                return gerarPrototype(line);
            } else if (line.trim().contains("(") && line.trim().contains(")")) {
                return (IfStmt) gerarMethodCall(line);
            } else if (line.trim().startsWith("return")) {
                return gerarReturn(line);
            }
            else{
                return gerarAtribuicao(line);

            }
        }
        return null;
    }

    private static AttrIfStmt gerarAtribuicao(String line) {
        String separado[] = line.split("=");

        if(separado[0].contains(".")){
            String[] partes = separado[0].split("\\.");
            Nomes nome = new Nomes(partes[0]);
            Nomes atributo = new Nomes(partes[1]);
            Lhs lhs = new Lhs(nome, atributo);
            Attr attr = gerarLinhaAttr(separado[1]);
            AttrIfStmt attrIfStmt = new AttrIfStmt(attr,lhs);

        }
        else{
            Nomes nome = new Nomes(separado[0]);
            Arg arg = gerarArg(separado[1]);
//            AttrIfStmt attrIfStmt = new AttrIfStmt(attr);

        }




        return null;
    }

    private static Attr gerarLinhaAttr(String linha) {
        String[] split = linha.trim().split(" ");
        if(linha.contains("+") || linha.contains("-") || linha.contains("*") || linha.contains("/")) {
            Nomes nomeEsquerdo = new Nomes(split[0]);
            String op = (split[1]);
            Nomes nomeDireito = new Nomes(split[2]);
//            System.out.println(nomeEsquerdo.getNome() + " " + op + " " + nomeDireito.getNome());
            Attr attr = new Attr(nomeEsquerdo, op, nomeDireito);
            return attr;
        }
        else{
            Arg arg = gerarArg(linha);




        }
        return null;
    }

    private static Arg gerarArg(String linha) {
        if(linha.trim().contains(".")){
            String[] partes = linha.split("\\.");
            Nomes nome = new Nomes(partes[0]);
            Nomes atributo = new Nomes(partes[1]);
            NameArg nameArg = new NameArg(nome, atributo);
            return nameArg;
        }
        if(linha.trim().contains("(") && linha.trim().contains(")")){
            MethodCallArg methodCall = (MethodCallArg) gerarMethodCall(linha);


        }

        return null;
    }

    private static ReturnIfStmt gerarReturn(String line) {
        String[] split = line.trim().split(" ");
        Nomes nome = new Nomes(split[1]);
        ReturnIfStmt ifStmt = new ReturnIfStmt(nome);
        ifStmt.append_result();
        return ifStmt;
    }

    private static CallMethod gerarMethodCall(String line) {
        String[] formated = line.trim().split("\\(");
        String[] params = formated[1].replace(")", "").split(",");
        List<Nomes> parametros = new ArrayList<>();
        for (String param : params) {
            parametros.add(new Nomes(param));

        }

        String[] partes = formated[0].split("\\.");


        MethodCall methodCall = new MethodCall(new Nomes(partes[0]), new Nomes(partes[1]), new NomesLista(parametros));
        CallMethod callMethod = new MethodCallIfStmt(methodCall);
        methodCall.append_result();
        return  callMethod;


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
