package de.kopl.demonstrator.calculator;

public class CalculatorEngine {

	// A flag to check if display should be cleared on the next keystroke
	private boolean clearDisplay = true;

	// The three calculator registers.
	private String displayString = "0";
	private String operatorString = new String();
	private String memoryString = new String();

	// A variable to store the pending calculation
	private Commands pendingCommand = null;

	private Calculator calculator;

	public CalculatorEngine(Calculator calculator) {
		this.calculator = calculator;
	}

	public void evaluateCommand(final Commands command) {
		String tempString = new String();
		String tempMemoryString = new String();

		if (!clearDisplay) {
			tempString = displayString;
		}

		switch (command) {
		case MEMORY_SAVE: // Save to Memory
			tempMemoryString = trimString(displayString);
			updateMemoryString(tempMemoryString);
			clearDisplay = true;
			break;

		case MEMORY_ADD: // Add to Memory
			if (memoryString.length() == 0) {
				tempMemoryString = trimString(displayString);
			} else {
				tempMemoryString = doCalc(memoryString, displayString, Commands.ADDITION);
			}
			updateMemoryString(tempMemoryString);
			clearDisplay = true;
			break;

		case MEMORY_SUBSTRACT: // Subtract from Memory
			if (memoryString.length() == 0) {
				if (displayString.startsWith("-")) {
					tempMemoryString = displayString.substring(1, displayString.length());
					tempMemoryString = trimString(tempMemoryString);
				} else if (displayString.equals("0.0") || displayString.equals("0") || displayString.equals("0.")
						|| displayString.equals("-0.0")) {
					tempMemoryString = "0";
				} else {
					tempMemoryString = command + displayString;
					tempMemoryString = trimString(displayString);
				}
			} else {
				tempMemoryString = doCalc(memoryString, displayString, Commands.SUBTRACTION);
			}
			updateMemoryString(tempMemoryString);
			clearDisplay = true;
			break;

		case MEMORY_CLEAR: // Clear Memory
			updateMemoryString("");
			break;

		case MEMORY_RECALL: // Recall Memory to Display
			tempString = memoryString;
			clearDisplay = true;
			updateCalculatorDisplay(tempString);
			break;

		case BACKSPACE: // Backspace
			if (tempString.length() < 1) {
				tempString = "";
			} else {
				tempString = tempString.substring(0, tempString.length() - 1);
			}
			clearDisplay = false;
			updateCalculatorDisplay(tempString);
			break;

		case CLEAR: // Clear
			tempString = "0";
			operatorString = "";
			pendingCommand = null;
			clearDisplay = false;
			updateCalculatorDisplay(tempString);
			break;

		case CLEAR_ENTRY: // Clear Entry
			tempString = "0";
			clearDisplay = false;
			updateCalculatorDisplay(tempString);
			break;

		case ADDITION:
			updateCalc(command);
			clearDisplay = true;
			break;

		case SUBTRACTION:
			updateCalc(command);
			clearDisplay = true;
			break;

		case MULTIPLICATION:
			updateCalc(command);
			clearDisplay = true;
			break;

		case DIVISION:
			updateCalc(command);
			clearDisplay = true;
			break;

		case EQUALS:
			updateCalc(command);
			clearDisplay = true;
			break;

		case CHANGE_SIGN: // Change Sign
			if (tempString.startsWith("-")) {
				tempString = trimLeadingMinusSign(tempString);
			} else {
				tempString = command + tempString;
			}
			clearDisplay = false;
			updateCalculatorDisplay(tempString);
			break;

		case DECIMAL_POINT: // Can't have two decimal points.
			if (tempString.indexOf(".") == -1 && tempString.length() < 29) {
				tempString = tempString + command;
			}
			updateCalculatorDisplay(tempString);
			clearDisplay = false;
			break;

		case ZERO: // Don't want 00 to be entered.
			if (!tempString.equals("0") && tempString.length() < 29) {
				tempString = tempString + command;
			}
			updateCalculatorDisplay(tempString);
			clearDisplay = false;
			break;

		default: // Default case is for the digits 1 through 9.
			if (tempString.equals("0") || tempString.equals("-0")) {
				tempString = "";
			}
			if (tempString.length() < 29) {
				tempString = tempString + command;
			}
			clearDisplay = false;
			updateCalculatorDisplay(tempString);
			break;
		}

	}

	private String trimLeadingMinusSign(String tempString) {
		return tempString.substring(1, tempString.length());
	}

	private void updateCalculatorDisplay(String tempString) {
		if (!displayString.equals(tempString)) {
			displayString = tempString;
			calculator.setText(displayString);
		}
	}

	private void updateMemoryString(String tempString) {
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

	/*
	 * This method updates the operator and display strings, and the pending calculation flag.
	 */
	private void updateCalc(Commands command) {

		String tempString = displayString;

		/*
		 * If there is no display value, the keystroke is deemed invalid and nothing is done.
		 */
		if (tempString.length() == 0) {
			return;
		}

		/*
		 * If there is no operator value, only calculation key presses are considered valid. Check that the display
		 * value is valid and if so, move the display value to the operator. No calculation is done.
		 */
		if (operatorString.length() == 0) {
			if (command != Commands.EQUALS) {
				tempString = trimString(tempString);
				if (tempString.startsWith(Constants.ERROR_STRING)) {
					clearDisplay = true;
					operatorString = "";
					pendingCommand = null;
				} else {
					operatorString = tempString;
					pendingCommand = command;
					clearDisplay = true;
				}
			}
			return;
		}

		// There is an operator and a display value, so do the calculation.
		displayString = doCalc(operatorString, tempString, pendingCommand);

		/*
		 * If '=' was pressed or result was invalid, reset pending calculation flag and operator value. Otherwise, set
		 * new calculation flag so calculations can be chained.
		 */
		if (command == Commands.EQUALS || displayString.startsWith(Constants.ERROR_STRING)) {
			pendingCommand = null;
			operatorString = "";
		} else {
			pendingCommand = command;
			operatorString = displayString;
		}

		// Set the clear display flag and show the result.
		clearDisplay = true;
		calculator.setText(displayString);
	}

	/*
	 * This method formats a string.
	 */
	private String trimString(final String newString) {
		String tempString = newString;

		// Value is not a number
		if (tempString.equals("NaN")) {
			tempString = Constants.ERROR_STRING + Constants.NAN_STRING;
			return tempString;
		}
		// Value is infinity
		if (tempString.equals("Infinity") || tempString.equals("-Infinity")) {
			tempString = Constants.ERROR_STRING + Constants.INFINITY_STRING;
			return tempString;
		}
		// Value is -0
		if (tempString.equals(-0.0)) {
			tempString = "0";
			return tempString;
		}
		// Trim unnecessary trailing .0
		if (tempString.endsWith(".0")) {
			tempString = tempString.substring(0, tempString.length() - 2);
		}
		// String is too long to display
		if (tempString.length() > 28) {
			tempString = Constants.ERROR_STRING + Constants.LONG_STRING;
		}

		return tempString;
	}

	/*
	 * This method converts the operator and display strings to double values and performs the calculation.
	 */
	private String doCalc(final String valAString, final String valBString, final Commands command) {
		String resultString = Constants.ERROR_STRING + Constants.NAN_STRING;
		Double valA = 0.0;
		Double valB = 0.0;
		Double valAnswer = 0.0;

		// Make sure register strings are numbers
		if (valAString.length() > 0) {
			try {
				valA = Double.parseDouble(valAString);
			} catch (NumberFormatException e) {
				return resultString;
			}
		} else {
			return resultString;
		}

		if (valBString.length() > 0) {
			try {
				valB = Double.parseDouble(valBString);
			} catch (NumberFormatException e) {
				return resultString;
			}
		} else {
			return resultString;
		}

		switch (command) {
		case ADDITION: // Addition
			valAnswer = valA + valB;
			break;

		case SUBTRACTION: // Subtraction
			valAnswer = valA - valB;
			break;

		case DIVISION: // Division
			valAnswer = valA / valB;
			break;

		case MULTIPLICATION: // Multiplication
			valAnswer = valA * valB;
			break;

		default: // Do nothing - this should never happen
			break;

		}

		// Convert answer to string and format it before return.
		resultString = valAnswer.toString();
		resultString = trimString(resultString);
		return resultString;
	}

}