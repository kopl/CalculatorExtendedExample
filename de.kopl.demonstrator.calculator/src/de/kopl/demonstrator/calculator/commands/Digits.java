package de.kopl.demonstrator.calculator.commands;

import de.kopl.demonstrator.calculator.Calculator;
import de.kopl.demonstrator.calculator.CalculatorEngine;

public class Digits extends AbstractCommand {

	private String digit;

	public Digits(String buttonLabel, String buttonTooltip) {
		super(buttonLabel, buttonTooltip);
		this.digit = buttonLabel;
	}

	@Override
	public void evaluate(Calculator calculator, CalculatorEngine calculatorEngine, String baseString) {
		if (baseString.equals("0") || baseString.equals("-0")) {
			baseString = "";
		}
		if (baseString.length() < 29) {
			String number = digit;
			baseString = baseString + number;
		}
		calculatorEngine.setClearDisplay(false);
		calculatorEngine.updateCalculatorDisplay(baseString);
	}

}
