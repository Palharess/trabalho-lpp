package objetos.Ifs;

import interfaces.CallMethod;
import objetos.Methods.MethodCall;

public class MethodCallIfStmt extends IfStmt implements CallMethod {
    private MethodCall methodCall;

    public MethodCallIfStmt(MethodCall methodCall) {
        this.methodCall = methodCall;
    }
}
