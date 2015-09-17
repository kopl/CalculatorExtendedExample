package de.kopl.demonstrator.calculator;

public enum Commands {

	// @formatter:off
	BACKSPACE("<"), 
	CLEAR("C"), 
	MEMORY_CLEAR("MC"), 
	CLEAR_ENTRY("C"), 
	INVERSE("INVERSE"), 
	SQUARE_ROOT("SQUARE ROOT"), 
	MEMORY_RECALL("MR"), 
	CHANGE_SIGN("-"), 
	DECIMAL_POINT("."), 
	ZERO("0"), 
	MEMORY_SAVE("MS"), 
	MEMORY_ADD("M+"), 
	MEMORY_SUBSTRACT("M-"), 
	MULTIPLICATION("*"), 
	ADDITION("+"),
	SUBTRACTION("-"), 
	DIVISION("/"), 
	EQUALS("="), 
	THREE("3"), 
	TWO("2"), 
	ONE("1"), 
	SIX("6"), 
	FIVE("5"), 
	FOUR("4"), 
	NINE("9"), 
	EIGHT("8"), 
	SEVEN("7"), 
	SINUS("sin"), 
	COSINUS("cos"), 
	TANGENS("tan");
	private String value;

	// @formatter:on
	Commands(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
