package de.kopl.demonstrator.calculator;

import java.util.List;

import org.eclipse.swt.widgets.Control;

public class StyleProvider {

	public void applyStlying(Calculator calculator) {
		calculator.getMainControl().setBackground(ColorConstants.BLACK);
		calculator.getMainControl().setForeground(ColorConstants.DARG_GRAY);

		calculator.getMemory().setForeground(ColorConstants.DARG_GRAY);
		calculator.getMemory().setBackground(ColorConstants.BLACK);

		calculator.getDisplay().setBackground(ColorConstants.YELLOW);
		calculator.getDisplay().setForeground(ColorConstants.RED);

		if (calculator.getStatus() != null) {
			calculator.getStatus().setForeground(ColorConstants.DARG_GRAY);
		}

		if (calculator.getHistory() != null) {
			calculator.getHistory().setBackground(ColorConstants.DARG_GRAY);
			calculator.getHistory().setForeground(ColorConstants.WHITE);
		}
		
		List<Control> children = calculator.geButtons();
		for (Control control : children) {
			control.setForeground(ColorConstants.WHITE);
			control.setBackground(ColorConstants.BLUE);

		}
	}

}
