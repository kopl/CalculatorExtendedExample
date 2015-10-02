package de.kopl.demonstrator.calculator.commands;

import de.kopl.demonstrator.calculator.Calculator;
import de.kopl.demonstrator.calculator.CalculatorEngine;

public class ChangeSign extends AbstractCommand {

	public ChangeSign(String buttonLabel, String buttonTooltip) {
		super(buttonLabel, buttonTooltip);
	}

	@Override
	public void evaluate(Calculator calculator, CalculatorEngine calculatorEngine, String baseString) {
		if (baseString.startsWith("-")) {
			baseString = trimLeadingMinusSign(baseString);
			calculator.replaceLatestHistoryEntry("-" + baseString, baseString);
		} else {
			baseString = "-" + baseString;
			calculator.replaceLatestHistoryEntry(trimLeadingMinusSign(baseString), baseString);
		}
		calculatorEngine.setClearDisplay(false);
		calculatorEngine.updateCalculatorDisplay(baseString);
	}

}
