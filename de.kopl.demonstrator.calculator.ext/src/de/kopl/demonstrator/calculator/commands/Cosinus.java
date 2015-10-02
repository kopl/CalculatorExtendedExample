package de.kopl.demonstrator.calculator.commands;

import de.kopl.demonstrator.calculator.Calculator;
import de.kopl.demonstrator.calculator.CalculatorEngine;

public class Cosinus extends AbstractCommand {

	public Cosinus(String buttonLabel, String buttonTooltip) {
		super(buttonLabel, buttonTooltip);
	}

	@Override
	public void evaluate(Calculator calculator, CalculatorEngine calculatorEngine, String baseString) {
		baseString = doCalc(calculatorEngine.getDisplayString(), "");
		calculatorEngine.setClearDisplay(true);
		calculatorEngine.updateCalculatorDisplay(baseString);
		calculator.appendHistoryEntry(" COS \n = " + baseString);
	}
	
	@Override
	protected boolean isSingleArgumentCommand() {
		return true;
	}

	@Override
	protected Double getResult(Double valA, Double valB) {
		return Math.cos(valA);
	}

}
