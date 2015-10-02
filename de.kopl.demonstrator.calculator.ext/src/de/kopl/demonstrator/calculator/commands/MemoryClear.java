package de.kopl.demonstrator.calculator.commands;

import de.kopl.demonstrator.calculator.Calculator;
import de.kopl.demonstrator.calculator.CalculatorEngine;

public class MemoryClear extends AbstractCommand {

	public MemoryClear(String buttonLabel, String buttonTooltip) {
		super(buttonLabel, buttonTooltip);
	}

	@Override
	public void evaluate(Calculator calculator, CalculatorEngine calculatorEngine, String baseString) {
		calculatorEngine.updateMemoryString("");
		calculator.appendHistoryEntry("_ > Memory\n");

	}

}
