package interpretador;

import java.io.*;
import java.util.*;

public class BoolMain {
    private Map<String, BoolClasse> classes;
    private Map<String, Object> varsGlobais;
    private Deque<Object> pilha;
    private List<String> instrucoesMain;
    private List<BoolObject> objetosCriados; // Para o coletor de lixo
    private String coletorCorAtual; // "red" ou "black"
    private int contadorInstrucoes;
    private Deque<ExecutionContext> pilhaContextos;


    public BoolMain() {
        classes = new HashMap<>();
        varsGlobais = new HashMap<>();
        pilha = new ArrayDeque<>();
        instrucoesMain = new ArrayList<>();
        objetosCriados = new ArrayList<>();
        coletorCorAtual = "red";
        contadorInstrucoes = 0;
        pilhaContextos = new ArrayDeque<>();

        // Cria o objeto 'io'
        criarObjetoIO();
    }

    private void criarObjetoIO() {
        BoolClasse ioClasse = new BoolClasse("io");
        BoolMethod printMethod = new BoolMethod("print");
        // O método 'print' será tratado de forma especial durante a execução
        ioClasse.adicionarMetodo(printMethod);
        BoolObject ioObjeto = new BoolObject(ioClasse);
        varsGlobais.put("io", ioObjeto);
    }

    public void executar(String arquivo) throws IOException {
        lerArquivo(arquivo);
        executarMain();
    }

    private void lerArquivo(String arquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        String linha;
        String estado = "NONE";
        BoolClasse classeAtual = null;
        BoolMethod metodoAtual = null;

        while ((linha = reader.readLine()) != null) {
            linha = linha.trim();
            if (linha.isEmpty()) continue;

            // Identifica o estado atual baseado nas palavras-chave
            if (linha.startsWith("class")) {
                estado = "CLASS";
                String[] partes = linha.split("\\s+");
                String nomeClasse = partes[1];
                classeAtual = new BoolClasse(nomeClasse);
                classes.put(nomeClasse, classeAtual);
            } else if (linha.equals("end-class")) {
                estado = "NONE";
                classeAtual = null;
            } else if (linha.startsWith("vars") && estado.equals("CLASS")) {
                String[] partes = linha.substring(5).split(",");
                for (String var : partes) {
                    classeAtual.adicionarAtributo(var.trim());
                }
            } else if (linha.startsWith("method")) {
                estado = "METHOD";
                String[] partes = linha.split("\\s+|\\(|\\)");
                String nomeMetodo = partes[1];
                metodoAtual = new BoolMethod(nomeMetodo);
                if (partes.length > 2 && !partes[2].isEmpty()) {
                    for (int i = 2; i < partes.length; i++) {
                        if(partes[i].contains(",")){
                            partes[i] = partes[i].replace(",", "");
                        }
                        metodoAtual.adicionarParametro(partes[i].trim());
                    }
                }
                classeAtual.adicionarMetodo(metodoAtual);
            } else if (linha.equals("end-method")) {
                estado = "CLASS";
                metodoAtual = null;
            } else if (linha.equals("begin")) {
                // Nada a fazer
            } else if (linha.equals("end")) {
                // Nada a fazer
            } else if (linha.startsWith("vars") && estado.equals("METHOD")) {
                String[] partes = linha.substring(5).split(",");
                for (String var : partes) {
                    metodoAtual.adicionarVariavelLocal(var.trim());
                }
            } else if (estado.equals("METHOD")) {
                metodoAtual.adicionarInstrucao(linha);
            } else if (linha.startsWith("main()")) {
                estado = "MAIN";
            } else if (estado.equals("MAIN")) {
                instrucoesMain.add(linha);
            }
        }
        reader.close();
    }

    private void executarMain() {
        ExecutionContext contexto = new ExecutionContext(varsGlobais);
        pilhaContextos.push(contexto);
        executarInstrucoes(instrucoesMain, contexto);
        pilhaContextos.pop();
    }

    private void executarInstrucoes(List<String> instrucoes, ExecutionContext contexto) {
        Boolean pulouIf = false;
        for (int i = 0; i < instrucoes.size(); i++) {
            String instrucao = instrucoes.get(i);
                contadorInstrucoes++;
            String[] partes = instrucao.split("\\s+");
            String comando = partes[0];

            if (comando.equals("if")) {
                int n = Integer.parseInt(partes[1]);
                boolean condicao = (boolean) pilha.pop();
                if (!condicao) {
                    pulouIf = true;
                    i += n; // Salta 'n' instruções
                }
            } else if (comando.equals("else")) {
                int n = Integer.parseInt(partes[1]);
                if(!pulouIf) i += n; // Salta 'n' instruções
            } else if(!comando.equals("end-if")) {
                interpretarInstrucao(instrucao, contexto);
            }
            if (contexto.deveRetornar()) {
                break;
            }

            // Chama o coletor de lixo a cada 5 instruções
            if (contadorInstrucoes % 5 == 0) {
                executarColetorDeLixo();
            }
        }
    }


    private void interpretarInstrucao(String instrucao, ExecutionContext contexto) {
        String[] partes = instrucao.split("\\s+");
        String comando = partes[0];

        switch (comando) {
            case "const":
                int valorConst = Integer.parseInt(partes[1]);
                pilha.push(valorConst);
                break;
            case "load":
                String nomeVariavel = partes[1];
                Object valorLoad = contexto.getVariavel(nomeVariavel);
                if (valorLoad == null) {
                    valorLoad = varsGlobais.get(nomeVariavel);
                }
                if (valorLoad == null) {
                    throw new RuntimeException("Variável não encontrada: " + nomeVariavel);
                }
                pilha.push(valorLoad);
                break;
            case "store":
                String nomeVariavelStore = partes[1];
                Object valorStore = pilha.pop();
                if (contexto.possuiVariavel(nomeVariavelStore)) {
                    contexto.setVariavel(nomeVariavelStore, valorStore);
                } else {
                    varsGlobais.put(nomeVariavelStore, valorStore);
                }
                break;
            case "add":
                int v2Add = (int) pilha.pop();
                int v1Add = (int) pilha.pop();
                pilha.push(v1Add + v2Add);
                break;
            case "sub":
                int v2Sub = (int) pilha.pop();
                int v1Sub = (int) pilha.pop();
                pilha.push(v1Sub - v2Sub);
                break;
            case "mul":
                int v2Mul = (int) pilha.pop();
                int v1Mul = (int) pilha.pop();
                pilha.push(v1Mul * v2Mul);
                break;
            case "div":
                int v2Div = (int) pilha.pop();
                int v1Div = (int) pilha.pop();
                pilha.push(v1Div / v2Div);
                break;
            case "new":
                String nomeClasse = partes[1];
                BoolClasse classe = classes.get(nomeClasse);
                if (classe == null) {
                    throw new RuntimeException("Classe não encontrada: " + nomeClasse);
                }
                BoolObject objeto = new BoolObject(classe);
                objetosCriados.add(objeto); // Adiciona à lista para o coletor de lixo
                pilha.push(objeto);
                break;
            case "get":
                String nomeAtributoGet = partes[1];
                BoolObject objetoGet = (BoolObject) pilha.pop();
                Object valorAtributo = objetoGet.getAtributo(nomeAtributoGet);
                if (valorAtributo == null) {
                    throw new RuntimeException("Atributo não encontrado: " + nomeAtributoGet);
                }
                pilha.push(valorAtributo);
                break;
            case "set":
                String nomeAtributoSet = partes[1];
                BoolObject objetoSet = (BoolObject) pilha.pop();
                Object valorAtributoSet = pilha.pop();
                if(nomeAtributoSet.equals("_prototype")){
                    BoolObject prototype = (BoolObject) valorAtributoSet;
                    objetoSet.setPrototype(prototype);
                    break;
                }
                objetoSet.setAtributo(nomeAtributoSet, valorAtributoSet);
                break;
            case "call":
                String nomeMetodo = partes[1];
                BoolObject objetoChamada = (BoolObject) pilha.pop();
                if (objetoChamada.getClasse().getNome().equals("io") && nomeMetodo.equals("print")) {
                    // Trata o método io.print
                    Object valorParaImprimir = pilha.pop();
                    System.out.println(valorParaImprimir);
                    pilha.push(0); // Retorna 0
                } else {
                    executarMetodo(objetoChamada, nomeMetodo);
                }
                break;
            case "pop":
                pilha.pop();
                break;
            case "vars":
                // Inicializa as variáveis declaradas com zero
                for (int i = 1; i < partes.length; i++) {
                    String nomeVar = partes[i].replace(",", "").trim();
                    contexto.setVariavel(nomeVar, 0);
                }
                break;
            case "ret":
                // Retorna do método atual
                if (!pilha.isEmpty()) {
                    Object retorno = pilha.pop();
                    contexto.setRetorno(retorno);
                } else {
                    contexto.setRetorno(null);
                }
                contexto.setRetornar(true); // Sinaliza que deve retornar ao contexto anterior
                break;
            case "gt":
                int v2Gt = (int) pilha.pop();
                int v1Gt = (int) pilha.pop();
                pilha.push(v1Gt > v2Gt);
                break;
            case "ge":
                int v2Ge = (int) pilha.pop();
                int v1Ge = (int) pilha.pop();
                pilha.push(v1Ge >= v2Ge);
                break;
            case "lt":
                int v2Lt = (int) pilha.pop();
                int v1Lt = (int) pilha.pop();
                pilha.push(v1Lt < v2Lt);
                break;
            case "le":
                int v2Le = (int) pilha.pop();
                int v1Le = (int) pilha.pop();
                pilha.push(v1Le <= v2Le);
                break;
            case "ne":
                int v2Ne = (int) pilha.pop();
                int v1Ne = (int) pilha.pop();
                pilha.push(v1Ne != v2Ne);
                break;
            case "eq":
                int v2Eq = (int) pilha.pop();
                int v1Eq = (int) pilha.pop();
                pilha.push(v1Eq == v2Eq);
                break;
            default:
                throw new RuntimeException("Instrução desconhecida: " + comando);
        }
    }


    private void executarMetodo(BoolObject objeto, String nomeMetodo) {
        BoolMethod metodo = encontrarMetodo(objeto, nomeMetodo);
        if (metodo == null) {
            throw new RuntimeException("Método não encontrado: " + nomeMetodo);
        }

        // Cria um novo contexto de execução
        ExecutionContext contextoMetodo = new ExecutionContext();
        contextoMetodo.setVariavel("self", objeto);

        // Retira os parâmetros da pilha
        List<String> parametros = metodo.getParametros();
        List<Object> argumentos = new ArrayList<>();
        for (int i = 0; i < parametros.size(); i++) {
            argumentos.add(0, pilha.pop()); // Inverte a ordem
        }
        // Atribui os parâmetros
        for (int i = 0; i < parametros.size(); i++) {
            contextoMetodo.setVariavel(parametros.get(i), argumentos.get(i));
        }

        // Adiciona variáveis locais
        for (String varLocal : metodo.getVariaveisLocais()) {
            contextoMetodo.setVariavel(varLocal, null);
        }
        pilhaContextos.push(contextoMetodo);

        // Executa as instruções do método
        executarInstrucoes(metodo.getInstrucoes(), contextoMetodo);

        // Obtém o valor de retorno
        Object retorno = contextoMetodo.getRetorno();
        if (retorno != null) {
            pilha.push(retorno);
        }
    }

    private BoolMethod encontrarMetodo(BoolObject objeto, String nomeMetodo) {
        BoolClasse classeAtual = objeto.getClasse();
        while (classeAtual != null) {
            BoolMethod metodo = classeAtual.getMetodo(nomeMetodo);
            if (metodo != null) {
                return metodo;
            }
            // Segue o _prototype se existir
            if (objeto.getPrototype() != null) {
                objeto = objeto.getPrototype();
                classeAtual = objeto.getClasse();
            } else {
                classeAtual = null;
            }
        }
        return null;
    }

    private void executarColetorDeLixo() {
        String novaCor = coletorCorAtual.equals("red") ? "black" : "red";
        // Fase de marcação
        marcarObjetos(novaCor);
        // Fase de coleta
        coletarObjetos(novaCor);
        coletorCorAtual = novaCor;
    }

    private void marcarObjetos(String cor) {
        // Marca objetos na pilha
        for (Object obj : pilha) {
            if (obj instanceof BoolObject) {
                marcarObjetoRecursivo((BoolObject) obj, cor);
            }
        }
        // Marca objetos nas variáveis globais
        for (Object obj : varsGlobais.values()) {
            if (obj instanceof BoolObject) {
                marcarObjetoRecursivo((BoolObject) obj, cor);
            }
        }
        for (ExecutionContext contexto : pilhaContextos) {
            for (Object obj : contexto.getVariaveis().values()) {
                if (obj instanceof BoolObject) {
                    marcarObjetoRecursivo((BoolObject) obj, cor);
                }
            }
        }
        // Pode adicionar marcação em outros lugares se necessário
    }

    private void marcarObjetoRecursivo(BoolObject objeto, String cor) {
        if (!objeto.getMarkColor().equals(cor)) {
            objeto.setMarkColor(cor);
            // Marca atributos que são objetos
            for (Object valor : objeto.getAtributos().values()) {
                if (valor instanceof BoolObject) {
                    marcarObjetoRecursivo((BoolObject) valor, cor);
                }
            }
            // Marca o _prototype
            if (objeto.getPrototype() != null) {
                marcarObjetoRecursivo(objeto.getPrototype(), cor);
            }
        }
    }

    private void coletarObjetos(String cor) {
        Iterator<BoolObject> iterator = objetosCriados.iterator();
        while (iterator.hasNext()) {
            BoolObject obj = iterator.next();
            if (!obj.getMarkColor().equals(cor)) {
                iterator.remove();

                // Remover referências e realizar limpeza se necessário
            }
        }
    }

    public static void main(String[] args) {
//        if (args.length != 1) {
//            System.out.println("Uso: java BoolMain arquivo.boolc");
//            return;
//        }

        BoolMain interpretador = new BoolMain();
        try {
            interpretador.executar("src/resultado.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
