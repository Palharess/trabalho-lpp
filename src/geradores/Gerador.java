package geradores;

import enums.Cmp;
import enums.Op;
import interfaces.CallMethod;
import objetos.*;
import objetos.Ifs.*;
import objetos.Methods.MethodBody;
import objetos.Methods.MethodCall;
import objetos.args.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.Main.addLinha;
import static main.Main.updateLineByIndex;

public class Gerador {
    public static int linhas = 0;
    public static boolean metodoAtribuido = false;

    public static MethodBody gerarMethodBody(String methodBody) {
        MethodBody method =null;
        boolean dentroIf = false;
        int contador = 0;
        for(String line : methodBody.split("\n")){
            if(line.contains("end-if")){
                dentroIf = false;
                contador = 0;
            }
            else if(line.trim().startsWith("if")) dentroIf = true;
            else{
                if(dentroIf && contador == 0){
                    contador++;
                    Pattern ifPattern = Pattern.compile("(?s)\\bif\\b.*?\\bend-if\\b");
                    Matcher ifMatcher = ifPattern.matcher(methodBody);
                    if(ifMatcher.find()) {
                        If ifStatement = gerarIf(ifMatcher.group());
                    }

                }
                else if (line.contains("return") && contador == 0) {
                    gerarReturn(line);
                }
                else if (line.contains("=") && contador == 0) {
                    gerarAtribuicao(line);
                }
                else if(line.contains("(") && line.contains(")") && !line.contains("=") && contador == 0){
                    gerarMethodCall(line);
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

        String ifThenBody = texto.split("then")[1].split("end-if")[0];
        String ifElseBody = "";
        if(ifThenBody.contains("else")){
            ifElseBody = texto.split("else")[1];
            ifThenBody = ifThenBody.split("else")[0];
        }


        ///processar if header
        gerarArg(nomeEsquerda.getNome());
        gerarArg(nomeDireita.getNome());
        addLinha(split[2]);

        //aquiaquiaqui

        String newLine = "if " + -1 ;
        int ifIndex = addLinha(newLine);


        Gerador.linhas = 0;


        for(String line : ifThenBody.split("\n")){

            if(!line.trim().isEmpty()){
                thenStmts.add(gerarIfStmt(line));
            }

        }
        updateLineByIndex("src/resultado.txt", ifIndex, "if " + Gerador.linhas);


        if(!(ifElseBody.isEmpty())){


            newLine = "else " + -1;
            int elseIndex = addLinha(newLine);

            Gerador.linhas = 0;
            for (String line : ifElseBody.split("\n")) {
                if (!line.trim().isEmpty() && !line.trim().startsWith("else") && !line.trim().startsWith("end-if")){
                    elseStmts.add(gerarIfStmt(line));
                }
            }
            updateLineByIndex("src/resultado.txt", elseIndex, "else " + Gerador.linhas);
        }
        addLinha("end-if");

        IfPai = new If(nomeEsquerda, comparador, nomeDireita, thenStmts, elseStmts);



        return IfPai;
    }

    private static IfStmt gerarIfStmt(String Texto){
        for (String line : Texto.split("\n")) {
            if (line.trim().contains("prototype")) {
                return gerarPrototype(line);
            } else if (line.trim().contains("(") && line.trim().contains(")") && !line.trim().contains("=")) {
                MethodCall methodCall = gerarMethodCall(line);
                return new MethodCallIfStmt(methodCall);
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
        Gerador.metodoAtribuido = true;

        if(separado[0].contains(".")){ // se tiver setando att de objetp
            String[] partes = separado[0].split("\\.");
            Nomes nome = new Nomes(partes[0]);
            Nomes atributo = new Nomes(partes[1]);
            Lhs lhs = new Lhs(nome, atributo);
            Attr attr = gerarLinhaAttr(separado[1]);
            attr.setLhs(lhs);
            attr.append_result_store(1);
            Gerador.metodoAtribuido = false;


        }
        else{

            Nomes nome = new Nomes(separado[0]);
            Attr attr = gerarLinhaAttr(separado[1]);
            attr.setLhs(new Lhs(nome));
            AttrIfStmt attrIfStmt = new AttrIfStmt(attr);
            addLinha("store " + nome.getNome().trim());
            Gerador.linhas += 1;
            Gerador.metodoAtribuido = false;
            return attrIfStmt;
//            if(attr.getArg() instanceof MethodCallArg){
//                MethodCallArg arg = (MethodCallArg) attr.getArg();
//                MethodCall method = arg.getMethodCall();
//
//                System.out.println(attr.getLhs().getName().getNome() + " = " + method.getNomeMethod().getNome() + "()");
//            }
//            if(attr.getArg() instanceof NameArg){
//                NameArg arg = (NameArg) attr.getArg();
//                System.out.println(attr.getLhs().getName().getNome() + " = " + arg.getNome().getNome() );
//            }
//            if(attr.getArg() instanceof NumberArg) {
//                NumberArg arg = (NumberArg) attr.getArg();
//                System.out.println(attr.getLhs().getName().getNome() + " = " + arg.getNumber().getNumero());
//            }
//
//
        }




        return null;
    }

    private static Attr gerarLinhaAttr(String linha) {
        String[] split = linha.trim().split(" ");
        if(linha.contains("+") || linha.contains("-") || linha.contains("*") || linha.contains("/")) {
            Nomes nomeEsquerdo = new Nomes(split[0]);
            String op = (split[1]);
            Nomes nomeDireito = new Nomes(split[2]);
            Arg argGerado1 = gerarArg(split[0]);
            Arg argGerado2 = gerarArg(split[2]);

            Attr attr = new Attr(argGerado1, op, argGerado2);
            //como foi soma tem que mostrar
            attr.append_result();
            return attr;
        }
        else{
            Arg arg = gerarArg(linha);
            return new Attr(arg);


        }

    }

    public static Arg gerarArg(String linha) {
        if(linha.trim().contains("(") && linha.trim().contains(")")){

            MethodCall methodCall = gerarMethodCall(linha);
            return new MethodCallArg(methodCall);
        }
        else if(linha.trim().contains(".")){
            String[] partes = linha.split("\\.");
            Nomes nome = new Nomes(partes[0]);
            Nomes atributo = new Nomes(partes[1]);
            NameArg nameArg = new NameArg(nome, atributo);
            addLinha("load " + nome.getNome().trim() + "\nget " + atributo.getNome().trim());
            Gerador.linhas += 2;
            return nameArg;
        }

        else if(linha.trim().contains("new")){
            String[] split = linha.trim().split(" ");
            Nomes nome = new Nomes(split[1]);
            ObjectCreationArg objectCreationArg = new ObjectCreationArg(new ObjectCreation(nome));
            addLinha("new " + nome.getNome());
            Gerador.linhas += 1;
            return objectCreationArg;

        }
        else{
            try {
                int valor = Integer.parseInt(linha.trim());
                return new NumberArg(new Numeros(valor));
            } catch (NumberFormatException e) {
                return new NameArg(new Nomes(linha.trim()));
            }
        }




    }

    private static ReturnIfStmt gerarReturn(String line) {
        String[] split = line.trim().split(" ");
        Nomes nome = new Nomes(split[1]);
        ReturnIfStmt ifStmt = new ReturnIfStmt(nome);
        ifStmt.append_result();
        return ifStmt;
    }

    private static MethodCall  gerarMethodCall(String line) {
        String[] formated = line.trim().split("\\(");
        String[] params = formated[1].replace(")", "").split(",");
        List<Nomes> parametros = new ArrayList<>();
        for (String param : params) {
            if(param.equals("")){
                continue;
            }
            parametros.add(new Nomes(param));

        }

        String[] partes = formated[0].split("\\.");


        MethodCall methodCall = new MethodCall(new Nomes(partes[0]), new Nomes(partes[1]), new NomesLista(parametros));
        methodCall.append_result();
        return  methodCall;


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
