package de.kopl.demonstrator.calculator.commands;

import de.kopl.demonstrator.calculator.Calculator;
import de.kopl.demonstrator.calculator.CalculatorEngine;
import de.kopl.demonstrator.calculator.Constants;

public abstract class AbstractCommand {

	private String buttonLabel;
	private String buttonToolTip;

	public AbstractCommand(String buttonLabel, String buttonTooltip) {
		this.buttonLabel = buttonLabel;
		this.buttonToolTip = buttonTooltip;
	}

	public abstract void evaluate(Calculator calculator, CalculatorEngine calculatorEngine, String baseString);

	public String trimLeadingMinusSign(String tempString) {
		return tempString.substring(1, tempString.length());
	}

	/*
	 * This method updates the operator and display strings, and the pending calculation flag.
	 */
	protected void updateCalc(Calculator calculator, CalculatorEngine calculatorEngine) {

		String tempString = calculatorEngine.getDisplayString();

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
		boolean isEqualsCommand = this.getClass().equals(Equals.class);

		if (calculatorEngine.getOperatorString().length() == 0) {
			if (!isEqualsCommand) {
				tempString = trimString(tempString);
				if (tempString.startsWith(Constants.ERROR_STRING)) {
					calculatorEngine.setClearDisplay(true);
					calculatorEngine.setOperatorString("");
					calculatorEngine.setPendingCommand(null);
				} else {
					calculatorEngine.setOperatorString(tempString);
					calculatorEngine.setPendingCommand(this);
					calculatorEngine.setClearDisplay(true);
				}
			}
			return;
		}

		// There is an operator and a display value, so do the calculation.

		AbstractCommand pendingCommand = calculatorEngine.getPendingCommand();
		calculatorEngine.setDisplayString(pendingCommand.doCalc(calculatorEngine.getOperatorString(), tempString));

		/*
		 * If '=' was pressed or result was invalid, reset pending calculation flag and operator value. Otherwise, set
		 * new calculation flag so calculations can be chained.
		 */
		if (isEqualsCommand || calculatorEngine.getDisplayString().startsWith(Constants.ERROR_STRING)) {
			calculatorEngine.setOperatorString("");
			calculatorEngine.setPendingCommand(null);
		} else {
			calculatorEngine.setOperatorString(calculatorEngine.getDisplayString());
			calculatorEngine.setPendingCommand(this);
		}

		// Set the clear display flag and show the result.
		calculatorEngine.setClearDisplay(true);
		calculator.setText(calculatorEngine.getDisplayString());
	}

	/*
	 * This method formats a string.
	 */
	protected String trimString(final String newString) {
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
	protected String doCalc(final String valAString, final String valBString) {
		String resultString = Constants.ERROR_STRING + Constants.NAN_STRING;
		Double valA = 0.0;
		Double valB = 0.0;

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

		if (!isSingleArgumentCommand()) {
			if (valBString.length() > 0) {
				try {
					valB = Double.parseDouble(valBString);
				} catch (NumberFormatException e) {
					return resultString;
				}
			} else {
				return resultString;
			}
		}

		Double valAnswer = getResult(valA, valB);

		// Convert answer to string and format it before return.
		resultString = valAnswer.toString();
		resultString = trimString(resultString);
		return resultString;
	}

	protected Double getResult(Double valA, Double valB) {
		throw new UnsupportedOperationException(this.getClass() + " command not implemented.");
	}

	protected boolean isSingleArgumentCommand() {
		return false;
	}

	public String getButtonToolTip() {
		return this.buttonToolTip;
	}

	public String getButtonLabel() {
		return this.buttonLabel;
	}

}
