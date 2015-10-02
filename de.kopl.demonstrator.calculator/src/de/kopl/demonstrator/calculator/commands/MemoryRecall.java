package de.kopl.demonstrator.calculator.commands;

import de.kopl.demonstrator.calculator.Calculator;
import de.kopl.demonstrator.calculator.CalculatorEngine;

public class MemoryRecall extends AbstractCommand {

	public MemoryRecall(String buttonLabel, String buttonTooltip) {
		super(buttonLabel, buttonTooltip);
	}

	@Override
	public void evaluate(Calculator calculator, CalculatorEngine calculatorEngine, String baseString) {
		String tempString;
		tempString = calculatorEngine.getMemoryString();
		calculatorEngine.setClearDisplay(true);
		calculatorEngine.updateCalculatorDisplay(tempString);
	}

}
