package de.kopl.demonstrator.calculator.commands;

import de.kopl.demonstrator.calculator.Calculator;
import de.kopl.demonstrator.calculator.CalculatorEngine;

public class MemorySave extends AbstractCommand {

	public MemorySave(String buttonLabel, String buttonTooltip) {
		super(buttonLabel, buttonTooltip);
	}

	@Override
	public void evaluate(Calculator calculator, CalculatorEngine calculatorEngine, String baseString) {
		String tempMemoryString;
		tempMemoryString = trimString(calculatorEngine.getDisplayString());
		calculatorEngine.updateMemoryString(tempMemoryString);
		calculatorEngine.setClearDisplay(true);
		calculator.appendHistoryEntry(tempMemoryString + " > Memory\n");

	}
}
