package de.kopl.demonstrator.calculator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class Calculator {

	/*
	 * Initialize variables needed for this class.
	 */
	private Label displayText;

	private CalculatorEngine calculatorEngine;

	private Label statusText;

	private StyledText historyView;

	private Label memoryLabel;

	public static void main(String[] a) {
		Calculator calculator = new Calculator();
		calculator.open();
	}

	private void open() {
		Display display = new Display();
		Shell shell = new Shell(display, SWT.TITLE | SWT.CLOSE);

		createContents(shell);

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	public Calculator() {
		this.calculatorEngine = new CalculatorEngine(this);
	}

	protected Control createContents(final Shell container) {

		container.setBackground(getSystemColor(SWT.COLOR_BLACK));
		container.setForeground(getSystemColor(SWT.COLOR_BLACK));

		
		addMainMenu(container);
		initUiGrid(container);
		
		initCalculatorDisplay(container);
		addMemoryLabel(container);
		addCalculatorButtons(container);
		addSeparator(container);
		addHistoryView(container);
		addStatusBar(container);

		return container;
	}

	private void addHistoryView(Composite container) {
		historyView = new StyledText(container, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
	
		historyView.setBackground(getSystemColor(SWT.COLOR_RED));
		historyView.setForeground(getSystemColor(SWT.COLOR_YELLOW));

		Caret caret = new Caret(historyView, SWT.NONE);
		historyView.setCaret(caret);

		GridData layoutData = new GridData(SWT.FILL, SWT.TOP, true, false, 5, 1);
		layoutData.heightHint = 200;
		historyView.setEditable(false);
		historyView.setLayoutData(layoutData);
	}

	public void appendHistoryEntry(String toAppend) {
		historyView.append(toAppend);
	}

	private void addStatusBar(Composite container) {
		registerToolTips(container);
		statusText = new Label(container, SWT.RIGHT | SWT.NONE);
		statusText.setText("");
		statusText.setForeground(getSystemColor(SWT.COLOR_WHITE));
		statusText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 5, 1));
	}

	private void registerToolTips(Composite container) {
		Control[] children = container.getChildren();
		for (int i = 0; i < children.length; i++) {
			Control child = children[i];
			if (child instanceof Label) {
				final Label component = (Label) child;
				if (component.getToolTipText() == null) {
					continue;
				}
				component.addMouseTrackListener(new MouseTrackListener() {

					@Override
					public void mouseHover(MouseEvent e) {
						// do nothing
					}

					@Override
					public void mouseExit(MouseEvent e) {
						statusText.setText("");
					}

					@Override
					public void mouseEnter(MouseEvent e) {
						statusText.setText("[" + component.getText() + "] " + component.getToolTipText());
					}
				});
			}
		}
	}

	private void addEmptyPlaceholder(Composite container) {
		new Label(container, SWT.NONE);
	}

	private void addSeparator(Composite container) {
		Label separator = new Label(container, SWT.HORIZONTAL | SWT.SEPARATOR);
		separator.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 5, 1));
	}

	private void addCalculatorButtons(Composite container) {
		addClearingButtons(container);
		addMemoryManagementButtons(container);
		addAdvancedMathButtons(container);
		addNumPadButtons(container);
	}

	private void addNumPadButtons(Composite container) {
		add7Button(container);
		add8Button(container);
		add9Button(container);
		addMultiplyButton(container);
		addDivideButton(container);

		add4Button(container);
		add5Button(container);
		add6Button(container);
		addAddButton(container);
		addSubstractButton(container);

		add1Button(container);
		add2Button(container);
		add3Button(container);
		addEmptyPlaceholder(container);
		addEqualsButton(container);

		add0Button(container);
		addFloatingPointButton(container);
		addSignButton(container);
	}

	private void addSignButton(Composite container) {
		createCalculatorButton(container, Commands.CHANGE_SIGN, "+/-", "Change sign of value");
	}

	private void addFloatingPointButton(Composite container) {
		createCalculatorButton(container, Commands.DECIMAL_POINT, ".", "Decimal Point");
	}

	private void add0Button(Composite container) {
		createCalculatorButton(container, Commands.ZERO, "0", "Numeric Pad");
	}

	private void addEqualsButton(Composite container) {
		Label equalsButton = createCalculatorButton(container, Commands.EQUALS, "=", "Equals (get result)");
		equalsButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2));
	}

	private void add3Button(Composite container) {
		createCalculatorButton(container, Commands.THREE, "3", "Numeric Pad");
	}

	private void add2Button(Composite container) {
		createCalculatorButton(container, Commands.TWO, "2", "Numeric Pad");
	}

	private void add1Button(Composite container) {
		createCalculatorButton(container, Commands.ONE, "1", "Numeric Pad");
	}

	private void addAddButton(Composite container) {
		createCalculatorButton(container, Commands.ADDITION, "+", "Addition");
	}

	private void add6Button(Composite container) {
		createCalculatorButton(container, Commands.SIX, "6", "Numeric Pad");
	}

	private void add5Button(Composite container) {
		createCalculatorButton(container, Commands.FIVE, "5", "Numeric Pad");
	}

	private void add4Button(Composite container) {
		createCalculatorButton(container, Commands.FOUR, "4", "Numeric Pad");
	}

	private void addSubstractButton(Composite container) {
		createCalculatorButton(container, Commands.SUBTRACTION, "-", "Substraction");
	}

	private void add9Button(Composite container) {
		createCalculatorButton(container, Commands.NINE, "9", "Numeric Pad");
	}

	private void add8Button(Composite container) {
		createCalculatorButton(container, Commands.EIGHT, "8", "Numeric Pad");
	}

	private void add7Button(Composite container) {
		createCalculatorButton(container, Commands.SEVEN, "7", "Numeric Pad");
	}

	private void addMultiplyButton(Composite container) {
		createCalculatorButton(container, Commands.MULTIPLICATION, "*", "Multiplication");
	}

	private void addAdvancedMathButtons(Composite container) {
		addInverseButton(container);
		addSqrtButton(container);
		addSinusButton(container);
		addCosinusButton(container);
		addTangensButton(container);
		addSeparator(container);
	}

	private void addSqrtButton(Composite container) {
		createCalculatorButton(container, Commands.SQUARE_ROOT, "√", "Square root of value");
	}

	private void addInverseButton(Composite container) {
		createCalculatorButton(container, Commands.INVERSE, "1/x", "Inverse of value");
	}

	private void addSinusButton(Composite container) {
		createCalculatorButton(container, Commands.SINUS, "sin", "Sinus of value");
	}

	private void addCosinusButton(Composite container) {
		createCalculatorButton(container, Commands.COSINUS, "cos", "Cosinus of value");
	}

	private void addTangensButton(Composite container) {
		createCalculatorButton(container, Commands.TANGENS, "tan", "Tangens of value");
	}

	
	private void addDivideButton(Composite container) {
		createCalculatorButton(container, Commands.DIVISION, "/", "Division");
	}

	private void addClearingButtons(Composite container) {
		addEmptyPlaceholder(container);
		addEmptyPlaceholder(container);
		addClearEntryButton(container);
		addBackButton(container);
		addClearAllButton(container);
		addSeparator(container);
	}

	private void addMemoryLabel(Composite container) {
		memoryLabel = new Label(container, SWT.LEFT | SWT.NONE);
		memoryLabel.setBackground(getSystemColor(SWT.COLOR_BLACK));
		memoryLabel.setForeground(getSystemColor(SWT.COLOR_BLUE));
		
		memoryLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 5, 1));
		updateMemoryLabel("");
	}

	public void updateMemoryLabel(String memory) {
		memoryLabel.setText("M: " + memory);
	}

	private void addBackButton(Composite container) {
		createCalculatorButton(container, Commands.BACKSPACE, "←", "Backspace");
	}

	private Label createCalculatorButton(Composite container, final Commands backspace, String buttonLabel,
			String buttonToolTip) {
		Label button = new Label(container, SWT.NONE | SWT.FLAT | SWT.CENTER);
		button.setBackground(getSystemColor(SWT.COLOR_DARK_GRAY));
		button.setForeground(getSystemColor(SWT.COLOR_WHITE));
		
		Font font = new Font(Display.getCurrent(), "Arial", 14, SWT.NONE);
		button.setFont(font);

		GridData buttonLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		buttonLayoutData.widthHint = 40;
		buttonLayoutData.heightHint = 30;
		buttonLayoutData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
		button.setLayoutData(buttonLayoutData);
		button.setToolTipText(buttonToolTip);
		button.setText(buttonLabel);

		button.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
				calculatorEngine.evaluateCommand(backspace);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// do nothing
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// do nothing
			}
		});
		return button;
	}

	private void addClearEntryButton(Composite container) {
		createCalculatorButton(container, Commands.CLEAR_ENTRY, "CE", "Clear Entry");
	}

	private void addClearAllButton(Composite container) {
		createCalculatorButton(container, Commands.CLEAR, "C", "Clear All");
	}

	private void addMemoryManagementButtons(Composite container) {
		addSaveToMemoryButton(container);
		addClearMemoryButton(container);
		addAddValueToMemoryButton(container);
		addSubstractFromMemoryButton(container);
		addRecalMemoryValueButton(container);
		addSeparator(container);
	}

	private void addSubstractFromMemoryButton(Composite container) {
		createCalculatorButton(container, Commands.MEMORY_SUBSTRACT, "M-", "Subtract value from Memory");

	}

	private void addRecalMemoryValueButton(Composite container) {
		createCalculatorButton(container, Commands.MEMORY_RECALL, "MR", "Recall value in Memory");
	}

	private void addAddValueToMemoryButton(Composite container) {
		createCalculatorButton(container, Commands.MEMORY_ADD, "M+", "Add value to Memory");

	}

	private void addClearMemoryButton(Composite container) {
		createCalculatorButton(container, Commands.MEMORY_CLEAR, "MC", "Clear Memory");

	}

	private void addSaveToMemoryButton(Composite container) {
		createCalculatorButton(container, Commands.MEMORY_SAVE, "MS", "Save value to Memory");

	}

	private void addMainMenu(final Shell parent) {
		Menu menuBar = new Menu(parent, SWT.BAR);
		MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");

		Menu fileMenu = new Menu(parent, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("E&xit");
		fileExitItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				parent.dispose();

			}
		});

		MenuItem helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		helpMenuHeader.setText("&Help");

		Menu helpMenu = new Menu(parent, SWT.DROP_DOWN);
		helpMenuHeader.setMenu(helpMenu);

		MenuItem helpGetHelpItem = new MenuItem(helpMenu, SWT.PUSH);
		helpGetHelpItem.setText("&Get Help");

		parent.setMenuBar(menuBar);
	}

	private void initCalculatorDisplay(Composite container) {
		displayText = new Label(container, SWT.RIGHT | SWT.BORDER);

		Font font = new Font(Display.getCurrent(), "Monospaced", 20, SWT.NONE);
		displayText.setFont(font);

		displayText.setBackground(getSystemColor(SWT.COLOR_RED));
		displayText.setForeground(getSystemColor(SWT.COLOR_YELLOW));
		displayText.setText("0");
		displayText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 5, 1));
	}

	private Color getSystemColor(int colorConstant) {
		return Display.getCurrent().getSystemColor(colorConstant);
	}

	private void initUiGrid(final Composite parent) {
		final GridLayout calculatorGridLayout = new GridLayout();
		calculatorGridLayout.marginRight = 5;
		calculatorGridLayout.marginLeft = 5;
		calculatorGridLayout.marginBottom = 5;
		calculatorGridLayout.marginTop = 5;
		calculatorGridLayout.marginWidth = 10;
		calculatorGridLayout.marginHeight = 2;
		calculatorGridLayout.numColumns = 5;
		calculatorGridLayout.verticalSpacing = 4;
		calculatorGridLayout.makeColumnsEqualWidth = true;
		calculatorGridLayout.horizontalSpacing = 4;
		parent.setLayout(calculatorGridLayout);
	}

	public void setText(String displayString) {
		displayText.setText(displayString);
	}

	public void clearHistory() {
		historyView.setText("");
	}

	public void applyBackspaceToHistory() {
		String currentHistory = historyView.getText();
		if (currentHistory.length() < 1) {
			return;
		}
		String newHistory = currentHistory.substring(0, currentHistory.length() - 1);
		historyView.setText(newHistory);
	}

	public void replaceLatestHistoryEntry(String toReplace, String replacement) {
		String currentHistory = historyView.getText();
		int lastIndexOfToReplace = currentHistory.lastIndexOf(toReplace);
		if (lastIndexOfToReplace < 0) {
			return;
		}
		String newHistory = currentHistory.substring(0, lastIndexOfToReplace);
		newHistory += replacement;
		historyView.setText(newHistory);
	}

}