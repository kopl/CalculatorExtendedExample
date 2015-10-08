package de.kopl.demonstrator.calculator;

import java.util.List;

import org.eclipse.swt.widgets.Control;

public class StyleProvider {

	public void applyStlying(Calculator calculator) {
		calculator.getMainControl().setBackground(ColorConstants.RED);
		calculator.getMainControl().setForeground(ColorConstants.BLACK);

		calculator.getMemory().setForeground(ColorConstants.YELLOW);
		calculator.getMemory().setBackground(ColorConstants.RED);

		calculator.getDisplay().setBackground(ColorConstants.YELLOW);
		calculator.getDisplay().setForeground(ColorConstants.RED);

		if (calculator.getStatus() != null) {
			calculator.getStatus().setForeground(ColorConstants.WHITE);
		}

		if (calculator.getHistory() != null) {
			calculator.getHistory().setBackground(ColorConstants.YELLOW);
			calculator.getHistory().setForeground(ColorConstants.RED);
		}
		
		List<Control> children = calculator.geButtons();
		for (Control control : children) {
			control.setForeground(ColorConstants.WHITE);
			control.setBackground(ColorConstants.BLUE);

		}
	}

}
