package net.saifs.kek.evaluator;

public class ReturnContext {
    private Object returnValue = null;
    private boolean hasReturned = false;

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturn(Object object) {
        this.returnValue = object;
        this.hasReturned = true;
    }

    public void clear() {
        this.returnValue = null;
        this.hasReturned = false;
    }

    public boolean hasReturn() {
        return hasReturned;
    }
}