package de.kopl.demonstrator.calculator.commands;

import de.kopl.demonstrator.calculator.Calculator;
import de.kopl.demonstrator.calculator.CalculatorEngine;

public class Addition extends AbstractCommand {

	public Addition(String buttonLabel, String buttonTooltip) {
		super(buttonLabel, buttonTooltip);
	}

	@Override
	public void evaluate(Calculator calculator, CalculatorEngine calculatorEngine, String baseString) {
		updateCalc(calculator, calculatorEngine);
		calculator.appendHistoryEntry("\n + ");
		calculatorEngine.setClearDisplay(true);
	}

	@Override
	protected Double getResult(Double valA, Double valB) {
		return valA + valB;
	}

}
