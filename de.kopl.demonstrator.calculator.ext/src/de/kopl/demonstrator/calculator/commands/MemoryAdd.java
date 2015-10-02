package de.kopl.demonstrator.calculator.commands;

import de.kopl.demonstrator.calculator.Calculator;
import de.kopl.demonstrator.calculator.CalculatorEngine;

public class MemoryAdd extends AbstractCommand {

	public MemoryAdd(String buttonLabel, String buttonTooltip) {
		super(buttonLabel, buttonTooltip);
	}

	@Override
	public void evaluate(Calculator calculator, CalculatorEngine calculatorEngine, String baseString) {
		String tempMemoryString;
		if (calculatorEngine.getMemoryString().length() == 0) {
			tempMemoryString = trimString(calculatorEngine.getDisplayString());
		} else {
			tempMemoryString = doCalc(calculatorEngine.getMemoryString(), calculatorEngine.getDisplayString());
		}
		calculatorEngine.updateMemoryString(tempMemoryString);
		calculatorEngine.setClearDisplay(true);
		calculator.appendHistoryEntry("Memory + " + calculatorEngine.getDisplayString() + " = " + tempMemoryString
				+ " > Memory\n");
	}

	
	@Override
	protected Double getResult(Double valA, Double valB) {
		return valA + valB;
	}
}
