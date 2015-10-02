package de.kopl.demonstrator.calculator;

import java.util.List;

import org.eclipse.swt.widgets.Control;

public class StyleProvider {

	public void applyStlying(Calculator calculator) {
		calculator.getMainControl().setBackground(ColorConstants.WIDGET_BACKGROUND);
		calculator.getMainControl().setForeground(ColorConstants.BLACK);
		
		calculator.getMemory().setForeground(ColorConstants.DARG_GRAY);
		calculator.getMemory().setBackground(ColorConstants.WIDGET_BACKGROUND);
		
		calculator.getDisplay().setBackground(ColorConstants.WHITE);
		calculator.getDisplay().setForeground(ColorConstants.BLACK);
		
		List<Control> children = calculator.geButtons();
		for (Control control : children) {
				control.setForeground(ColorConstants.DARG_GRAY);
				control.setBackground(ColorConstants.WHITE);
			
		}
	}
	
	
	
}
