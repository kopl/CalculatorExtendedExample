package de.kopl.demonstrator.calculator.commands;

import de.kopl.demonstrator.calculator.Calculator;
import de.kopl.demonstrator.calculator.CalculatorEngine;

public class DecimalPoint extends AbstractCommand {

	public DecimalPoint(String buttonLabel, String buttonTooltip) {
		super(buttonLabel, buttonTooltip);
	}

	@Override
	public void evaluate(Calculator calculator, CalculatorEngine calculatorEngine, String baseString) {
		if (baseString.indexOf(".") == -1 && baseString.length() < 29) {
			baseString = baseString + ".";
			calculator.appendHistoryEntry(".");
		}
		calculatorEngine.updateCalculatorDisplay(baseString);
		calculatorEngine.setClearDisplay(false);
	}

}
