package de.kopl.demonstrator.calculator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import de.kopl.demonstrator.calculator.commands.*;

public class Calculator {

	public static void main(String[] a) {
		Calculator calculator = new Calculator();
		calculator.open();
	}

	private CalculatorEngine calculatorEngine;

	/*
	 * Initialize variables needed for this class.
	 */
	private Label displayText;

	private Label memoryLabel;

	private StyledText historyView;

	private StyleProvider styleProvider;

	private Label statusText;

	private Shell shell;

	private List<Control> buttons = new ArrayList<Control>();

	public Calculator() {
		this.calculatorEngine = new CalculatorEngine(this);
		this.styleProvider = new StyleProvider();
	}

	private void addCalculatorButtons(Composite container) {
		addClearingButtons(container);
		addMemoryManagementButtons(container);
		addAdvancedMathButtons(container);
	}

	private void addAdvancedMathButtons(Composite container) {
		createCalculatorButton(container, new Inverse("1/x", "Inverse of value"));
		createCalculatorButton(container, new SquareRoot("√", "Square root of value"));
		createCalculatorButton(container, new Sinus("sin", "Sinus of value"));
		createCalculatorButton(container, new Cosinus("cos", "Cosinus of value"));
		createCalculatorButton(container, new Tangens("tan", "Tangens of value"));
		addSeparator(container);
	}

	private void addClearingButtons(Composite container) {
		addEmptyPlaceholder(container);
		addEmptyPlaceholder(container);

		createCalculatorButton(container, new ClearEntry("CE", "Clear Entry"));
		createCalculatorButton(container, new BackSpace("←", "Backspace"));
		createCalculatorButton(container, new Clear("C", "Clear All"));

		addSeparator(container);
	}

	private void addEmptyPlaceholder(Composite container) {
		new Label(container, SWT.NONE);
	}

	private void addHistoryView(Composite container) {
		historyView = new StyledText(container, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);

		Caret caret = new Caret(historyView, SWT.NONE);
		historyView.setCaret(caret);

		GridData layoutData = new GridData(SWT.FILL, SWT.TOP, true, false, 5, 1);
		layoutData.heightHint = 200;
		historyView.setEditable(false);
		historyView.setLayoutData(layoutData);
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
			public void widgetDefaultSelected(SelectionEvent e) {
				parent.dispose();

			}

			@Override
			public void widgetSelected(SelectionEvent e) {
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

	private void addMemoryLabel(Composite container) {
		memoryLabel = new Label(container, SWT.LEFT | SWT.NONE);

		memoryLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 5, 1));
		updateMemoryLabel("");
	}

	private void addMemoryManagementButtons(Composite container) {
		createCalculatorButton(container, new MemorySave("MS", "Save value to Memory"));
		createCalculatorButton(container, new MemoryClear("MC", "Clear Memory"));
		createCalculatorButton(container, new MemoryAdd("M+", "Add value to Memory"));
		createCalculatorButton(container, new MemorySubstract("M-", "Subtract value from Memory"));
		createCalculatorButton(container, new MemoryRecall("MR", "Recall value in Memory"));
		addSeparator(container);
	}

	private void addNumPadButtons(Composite container) {
		createCalculatorButton(container, new Digits("7", "Numeric Pad"));
		createCalculatorButton(container, new Digits("8", "Numeric Pad"));
		createCalculatorButton(container, new Digits("9", "Numeric Pad"));
		createCalculatorButton(container, new Multiplication("*", "Multiplication"));
		createCalculatorButton(container, new Division("/", "Division"));

		createCalculatorButton(container, new Digits("4", "Numeric Pad"));
		createCalculatorButton(container, new Digits("5", "Numeric Pad"));
		createCalculatorButton(container, new Digits("6", "Numeric Pad"));
		createCalculatorButton(container, new Addition("+", "Addition"));
		createCalculatorButton(container, new Substraction("-", "Substraction"));

		createCalculatorButton(container, new Digits("1", "Numeric Pad"));
		createCalculatorButton(container, new Digits("2", "Numeric Pad"));
		createCalculatorButton(container, new Digits("3", "Numeric Pad"));
		addEmptyPlaceholder(container);
		Label equalsButton = createCalculatorButton(container, new Equals("=", "Equals (get result)"));
		equalsButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2));

		createCalculatorButton(container, new Zero("0", "Numeric Pad"));
		createCalculatorButton(container, new DecimalPoint(".", "Decimal Point"));
		createCalculatorButton(container, new ChangeSign("+/-", "Change sign of value"));
	}

	private void addSeparator(Composite container) {
		Label separator = new Label(container, SWT.HORIZONTAL | SWT.SEPARATOR);
		separator.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 5, 1));
	}

	private void addStatusBar(Composite container) {
		this.statusText = new Label(container, SWT.RIGHT | SWT.NONE);
		this.statusText.setText("");
		for (Control child : buttons) {
			if (child instanceof Label) {
				final Label component = (Label) child;
				if (component.getToolTipText() == null) {
					continue;
				}
				final Label tipTarget = statusText;
				component.addMouseTrackListener(new MouseTrackListener() {

					@Override
					public void mouseEnter(MouseEvent e) {
						tipTarget.setText("[" + component.getText() + "] " + component.getToolTipText());
					}

					@Override
					public void mouseExit(MouseEvent e) {
						tipTarget.setText("");
					}

					@Override
					public void mouseHover(MouseEvent e) {
						// do nothing
					}
				});
			}
		}

		statusText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 5, 1));
	}

	public void appendHistoryEntry(String toAppend) {
		historyView.append(toAppend);
	}

	public void applyBackspaceToHistory() {
		String currentHistory = historyView.getText();
		if (currentHistory.length() < 1) {
			return;
		}
		String newHistory = currentHistory.substring(0, currentHistory.length() - 1);
		historyView.setText(newHistory);
	}

	public void clearHistory() {
		historyView.setText("");
	}

	private Label createCalculatorButton(Composite container, final AbstractCommand command) {
		Label button = new Label(container, SWT.NONE | SWT.FLAT | SWT.CENTER);
		buttons.add(button);

		Font font = new Font(Display.getCurrent(), "Arial", 14, SWT.NONE);
		button.setFont(font);

		GridData buttonLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		buttonLayoutData.widthHint = 40;
		buttonLayoutData.heightHint = 30;
		buttonLayoutData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
		button.setLayoutData(buttonLayoutData);
		button.setToolTipText(command.getButtonToolTip());
		button.setText(command.getButtonLabel());

		button.addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// do nothing
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// do nothing
			}

			@Override
			public void mouseUp(MouseEvent e) {
				calculatorEngine.evaluateCommand(command);
				updateHistory(command);
			}

		});
		return button;
	}

	private void updateHistory(AbstractCommand command) {
		String currentDisplayString = calculatorEngine.getDisplayString();

		if (command instanceof Clear || command instanceof ClearEntry) {
			clearHistory();
			appendHistoryEntry("0");
			return;
		}
		if (command instanceof ChangeSign) {
			if (currentDisplayString.startsWith("-")) {
				currentDisplayString = command.trimLeadingMinusSign(currentDisplayString);
				replaceLatestHistoryEntry("-" + currentDisplayString, currentDisplayString);
			} else {
				currentDisplayString = "-" + currentDisplayString;
				replaceLatestHistoryEntry(command.trimLeadingMinusSign(currentDisplayString), currentDisplayString);
			}
			return;
		}
		if (command instanceof DecimalPoint) {
			if (currentDisplayString.indexOf(".") == -1 && currentDisplayString.length() < 29) {
				appendHistoryEntry(".");
			}
			return;
		}

		if (command instanceof BackSpace) {
			if (currentDisplayString.length() >= 0) {
				applyBackspaceToHistory();
			}
			return;
		}

		if (command instanceof Digits || command instanceof Zero) {
			if (currentDisplayString.equals("0") || currentDisplayString.equals("-0")) {
				applyBackspaceToHistory();
			}
			if (currentDisplayString.length() < 29) {
				String number = command.getButtonLabel();
				appendHistoryEntry(number);
			}
			return;
		}
		if (command instanceof MemoryAdd) {
			appendHistoryEntry("\nMemory + " + calculatorEngine.getDisplayString() + " = "
					+ calculatorEngine.getMemoryString() + " > Memory\n");
			return;
		}
		if (command instanceof MemoryClear) {
			appendHistoryEntry("\n_ > Memory\n");
			return;
		}
		if (command instanceof MemoryRecall) {
			appendHistoryEntry("\nMemory > " + calculatorEngine.getMemoryString() + "\n");
			return;

		}
		if (command instanceof MemorySave) {
			appendHistoryEntry("\n"+ calculatorEngine.getMemoryString() + " > Memory\n");
			return;

		}
		if (command instanceof MemorySubstract) {
			appendHistoryEntry("\nMemory - " + calculatorEngine.getDisplayString() + " = "
					+ calculatorEngine.getMemoryString() + " > Memory\n");
			return;
		}
		if (command instanceof Equals) {
			appendHistoryEntry("\n= " + calculatorEngine.getDisplayString());
			return;
		}
		appendHistoryEntry(" " + command.getButtonLabel() + " ");
		return;

	}

	protected Control createMainContents(final Shell container) {

		addMainMenu(container);
		initUiGrid(container);

		initCalculatorDisplay(container);
		addMemoryLabel(container);
		addCalculatorButtons(container);
		addNumPadButtons(container);
		addSeparator(container);
		addHistoryView(container);

		return container;
	}

	private void initCalculatorDisplay(Composite container) {
		displayText = new Label(container, SWT.RIGHT | SWT.BORDER);

		Font font = new Font(Display.getCurrent(), "Monospaced", 20, SWT.NONE);
		displayText.setFont(font);

		displayText.setText("0");
		displayText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 5, 1));
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

	private void open() {
		Display display = new Display();
		shell = new Shell(display, SWT.TITLE | SWT.CLOSE);
		initContents();
		styleProvider.applyStlying(this);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private void initContents() {
		createMainContents(shell);
		addStatusBar(shell);
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

	public void setText(String displayString) {
		displayText.setText(displayString);
	}

	public void updateMemoryLabel(String memory) {
		memoryLabel.setText("M: " + memory);
	}

	public Shell getMainControl() {
		return shell;
	}

	public Control getMemory() {
		return memoryLabel;
	}

	public Control getDisplay() {
		return displayText;
	}

	public Control getStatus() {
		return statusText;
	}

	public Control getHistory() {
		return historyView;
	}

	public List<Control> geButtons() {
		return buttons;
	}

}