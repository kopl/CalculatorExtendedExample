package de.kopl.demonstrator.calculator.commands;

import de.kopl.demonstrator.calculator.Calculator;
import de.kopl.demonstrator.calculator.CalculatorEngine;

public class Zero extends AbstractCommand {

	public Zero(String buttonLabel, String buttonTooltip) {
		super(buttonLabel, buttonTooltip);
	}

	@Override
	public void evaluate(Calculator calculator, CalculatorEngine calculatorEngine, String baseString) {
		if (!baseString.equals("0") && baseString.length() < 29) {
			baseString = baseString + "0";
			calculator.appendHistoryEntry("0");
		}
		calculatorEngine.updateCalculatorDisplay(baseString);
		calculatorEngine.setClearDisplay(false);
	}

}
