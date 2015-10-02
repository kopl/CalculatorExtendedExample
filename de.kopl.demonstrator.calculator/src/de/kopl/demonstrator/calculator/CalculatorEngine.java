package de.kopl.demonstrator.calculator;

import de.kopl.demonstrator.calculator.commands.AbstractCommand;

public class CalculatorEngine {

	// A flag to check if display should be cleared on the next keystroke
	private boolean clearDisplay = true;

	// The three calculator registers.
	private String displayString = "0";
	private String operatorString = new String();
	private String memoryString = new String();

	// A variable to store the pending calculation
	private AbstractCommand pendingCommand = null;

	private Calculator calculator;

	public CalculatorEngine(Calculator calculator) {
		this.calculator = calculator;
	}

	public void evaluateCommand(final AbstractCommand command) {
		String baseString = new String();

		if (!clearDisplay) {
			baseString = displayString;
		}
		command.evaluate(calculator, this, baseString);
	}

	public void updateCalculatorDisplay(String tempString) {
		if (!displayString.equals(tempString)) {
			displayString = tempString;
			calculator.setText(displayString);
		}
	}

	public void updateMemoryString(String tempString) {
		if (tempString.startsWith(Constants.ERROR_STRING)) {
			if (!displayString.equals(tempString)) {
				displayString = tempString;
				calculator.setText(displayString);
			}
		} else {
			memoryString = tempString;
			calculator.updateMemoryLabel(memoryString);
		}
	}

	public String getMemoryString() {
		return memoryString;
	}

	public void setClearDisplay(boolean b) {
		this.clearDisplay = b;
	}

	public void setOperatorString(String string) {
		operatorString = string;
	}

	public void setPendingCommand(AbstractCommand command) {
		this.pendingCommand = command;
	}

	public String getDisplayString() {
		return displayString;
	}

	public String getOperatorString() {
		return operatorString;
	}

	public AbstractCommand getPendingCommand() {
		return pendingCommand;
	}

	public void setDisplayString(String displayString) {
		this.displayString = displayString;
	}

}