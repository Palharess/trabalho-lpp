package objetos.Ifs;

import objetos.Methods.MethodCall;

public class MethodCallIfStmt extends IfStmt{
    private MethodCall methodCall;

    public MethodCallIfStmt(MethodCall methodCall) {
        this.methodCall = methodCall;
    }
}
