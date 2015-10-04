package de.kopl.demonstrator.calculator.commands;

import de.kopl.demonstrator.calculator.Calculator;
import de.kopl.demonstrator.calculator.CalculatorEngine;

public class ClearEntry extends AbstractCommand {

	public ClearEntry(String buttonLabel, String buttonTooltip) {
		super(buttonLabel, buttonTooltip);
	}

	@Override
	public void evaluate(Calculator calculator, CalculatorEngine calculatorEngine, String baseString) {
		baseString = "0";
		calculatorEngine.setClearDisplay(false);
		calculatorEngine.updateCalculatorDisplay(baseString);
		// FIXME this should only clear the latest entered display value, not the whole history
	}

}
