package ru.romanzes.kammerer.entities;

public enum Operator {
	ADD("+"), SUB("-"), MUL("*"), DIV("/"), MOD("%"), EQL("=="), NEQ("!="), LES("<"), GRT(">");
	
	private final String sign;

    private Operator(final String sign) {
        this.sign = sign;
    }
    
    @Override
    public String toString() {
    	return sign;
    }
}
