package de.kopl.demonstrator.calculator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class ColorConstants {

	public static final Color GREEN = getSystemColor(SWT.COLOR_GREEN);
	public static final Color WIDGET_BACKGROUND = getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
	public static final Color DARG_GRAY = getSystemColor(SWT.COLOR_DARK_GRAY);
	public static final Color BLUE = getSystemColor(SWT.COLOR_BLUE);
	public static final Color WHITE = getSystemColor(SWT.COLOR_WHITE);
	public static final Color YELLOW = getSystemColor(SWT.COLOR_YELLOW);
	public static final Color BLACK = getSystemColor(SWT.COLOR_BLACK);
	public static final Color RED = getSystemColor(SWT.COLOR_RED);

	private static Color getSystemColor(int colorConstant) {
		return Display.getCurrent().getSystemColor(colorConstant);
	}

}
