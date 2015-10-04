package de.kopl.demonstrator.calculator.commands;

import de.kopl.demonstrator.calculator.Calculator;
import de.kopl.demonstrator.calculator.CalculatorEngine;

public class Clear extends AbstractCommand {

	public Clear(String buttonLabel, String buttonTooltip) {
		super(buttonLabel, buttonTooltip);
	}

	@Override
	public void evaluate(Calculator calculator, CalculatorEngine calculatorEngine, String baseString) {
		baseString = "0";
		calculatorEngine.setOperatorString("");
		calculatorEngine.setPendingCommand(null);
		calculatorEngine.setClearDisplay(false);
		calculatorEngine.updateCalculatorDisplay(baseString);
	}

}
