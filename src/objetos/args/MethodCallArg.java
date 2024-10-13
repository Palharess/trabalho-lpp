package objetos.args;

import interfaces.CallMethod;
import objetos.Methods.MethodCall;

public class MethodCallArg extends Arg implements CallMethod {
    private MethodCall methodCall;

    public MethodCallArg(MethodCall methodCall) {
        this.methodCall = methodCall;
    }

    public MethodCall getMethodCall() {
        return methodCall;
    }
}
