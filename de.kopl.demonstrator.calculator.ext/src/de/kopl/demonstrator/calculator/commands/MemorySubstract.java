package de.kopl.demonstrator.calculator.commands;

import de.kopl.demonstrator.calculator.Calculator;
import de.kopl.demonstrator.calculator.CalculatorEngine;

public class MemorySubstract extends AbstractCommand {

	public MemorySubstract(String buttonLabel, String buttonTooltip) {
		super(buttonLabel, buttonTooltip);
	}

	@Override
	public void evaluate(Calculator calculator, CalculatorEngine calculatorEngine, String baseString) {
		String tempMemoryString;
		if (calculatorEngine.getMemoryString().length() == 0) {
			if (calculatorEngine.getDisplayString().startsWith("-")) {
				tempMemoryString = calculatorEngine.getDisplayString().substring(1,
						calculatorEngine.getDisplayString().length());
				tempMemoryString = trimString(tempMemoryString);
			} else if (calculatorEngine.getDisplayString().equals("0.0")
					|| calculatorEngine.getDisplayString().equals("0")
					|| calculatorEngine.getDisplayString().equals("0.")
					|| calculatorEngine.getDisplayString().equals("-0.0")) {
				tempMemoryString = "0";
			} else {
				tempMemoryString = "-" + calculatorEngine.getDisplayString();
				tempMemoryString = trimString(calculatorEngine.getDisplayString());
			}
		} else {
			tempMemoryString = doCalc(calculatorEngine.getMemoryString(), calculatorEngine.getDisplayString());
		}
		calculatorEngine.updateMemoryString(tempMemoryString);
		calculatorEngine.setClearDisplay(true);
		calculator.appendHistoryEntry("Memory - " + calculatorEngine.getDisplayString() + " = " + tempMemoryString
				+ " > Memory\n");
	}

	@Override
	protected Double getResult(Double valA, Double valB) {
		return valA - valB;
	}
}
