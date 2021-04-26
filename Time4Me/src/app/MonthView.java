package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class MonthView {

	private int month;
	private int year;
	private JPanel mainPanel, menuBar, calPanel;
	private JPanel[][] days;
	private Calendar cal, today;
	private int height, width, daySide, gap, barHeight, buttonWidth, barSpace;
	private JButton incrButton, decrButton;
	private JFrame frame;
	private JLabel monthName;
	
	public MonthView(JFrame frame) {
		this.frame = frame;
		cal = Calendar.getInstance();
		cal.setTime(new Date());
		today = Calendar.getInstance();
		today.setTime(new Date());
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		monthName = new JLabel();
		mainPanel = new JPanel();
		menuBar = new JPanel();
		calPanel = new JPanel();
		daySide = 75;
		gap = 6;
		barHeight = 50;
		buttonWidth = 60;
		height = (6 * daySide) + (7 * gap) + barHeight;
		width = (7 * daySide) + (8 * gap);
		barSpace = width - (2 * buttonWidth);
		
		mainPanel.setSize(new Dimension(width, height));
		menuBar.setSize(new Dimension(width, barHeight));
		calPanel.setSize(new Dimension(width, height - barHeight));
		
		createView();
	}
	
	private void createView() {
		//make menubar
		menuBar.setLayout(new BorderLayout());
		JPanel leftButtonPanel = new JPanel();
		JPanel rightButtonPanel = new JPanel();
		JPanel centerContentPanel = new JPanel();
		
		//make stuff in panels
		decrButton = new JButton("Prev");
		decrButton.addActionListener(e -> {decMonth();});
		leftButtonPanel.add(decrButton);
		incrButton = new JButton("Next");
		incrButton.addActionListener(e -> {incMonth();});
		rightButtonPanel.add(incrButton);
		
		centerContentPanel.setSize(new Dimension(barSpace, barHeight));
		
		menuBar.add(leftButtonPanel, BorderLayout.WEST);
		menuBar.add(centerContentPanel, BorderLayout.CENTER);
		menuBar.add(rightButtonPanel, BorderLayout.EAST);
		
		//make cal
		makeCal();
		
		//add label
		centerContentPanel.add(monthName);
		
		//make main
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(menuBar, BorderLayout.NORTH);
		mainPanel.add(calPanel, BorderLayout.CENTER);
		
		//add keybindings
		mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke("RIGHT"), "incrMonth");
		mainPanel.getActionMap().put("incrMonth", new KeyBindingAction(1));
		
		mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke("LEFT"), "decrMonth");
		mainPanel.getActionMap().put("decrMonth", new KeyBindingAction(2));
		
		mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke("J"), "jumpMonth");
		mainPanel.getActionMap().put("jumpMonth", new KeyBindingAction(3));
		
		mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke("T"), "focusToday");
		mainPanel.getActionMap().put("focusToday", new KeyBindingAction(4));
	}
	
	public void decMonth() {
		month--;
		if (month < cal.getActualMinimum(Calendar.MONTH)) {
			month = cal.getActualMaximum(Calendar.MONTH);
			year--;
		}
		
		makeCal();
		frame.revalidate();
	}
	public void incMonth() {
		month++;
		if (month > cal.getActualMaximum(Calendar.MONTH)) {
			month = cal.getActualMinimum(Calendar.MONTH);
			year++;
		}
		
		makeCal();
		frame.revalidate();
	}
	
	private void makeCal() {
		cal.set(year, month, 1);
		monthName.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) 
				+ " " + cal.get(Calendar.YEAR));
		
		boolean checkToday = false;
		if ((year == today.get(Calendar.YEAR)) && (month == today.get(Calendar.MONTH))) {
			checkToday = true;
		}
		
		int firstDay = cal.get(Calendar.DAY_OF_WEEK);
		int curDay = 1;
		days = new JPanel[6][7];
		
		calPanel.removeAll();
		
		calPanel.setLayout(new GridLayout(6, 7));
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				days[i][j] = new JPanel();
				days[i][j].setSize(new Dimension(daySide + (gap / 2), 
						daySide + (gap / 2)));
				
				if (curDay == 1) {
					if ((j+1) == firstDay) {
						JButton dayBtn = new JButton("1");
						int dayNum = curDay;
						int monthNum = month;
						int yearNum = year;
						dayBtn.addActionListener(e -> {new DayView(dayNum, monthNum, yearNum);});
						dayBtn.setPreferredSize(new Dimension(daySide, daySide));
						if (checkToday && (curDay == today.get(Calendar.DATE))) {
							dayBtn.setBackground(dayBtn.getBackground().darker());
						}
						days[i][j].add(dayBtn);
						curDay++;
					}
				} else if (curDay <= cal.getActualMaximum(Calendar.DATE)) {
					JButton dayBtn = new JButton("" + curDay);
					int dayNum = curDay;
					int monthNum = month;
					int yearNum = year;
					dayBtn.addActionListener(e -> {new DayView(dayNum, monthNum, yearNum);});
					dayBtn.setPreferredSize(new Dimension(daySide, daySide));
					if (checkToday && (curDay == today.get(Calendar.DATE))) {
						dayBtn.setBackground(dayBtn.getBackground().darker());
					}
					days[i][j].add(dayBtn);
					curDay++;
				}
				
				calPanel.add(days[i][j]);
			}
		}
	}
	
	public JPanel getView() {
		return this.mainPanel;
	}
	
	public void jumpToMonth(int tMonth, int tYear) {
		month = tMonth;
		year = tYear;
		
		makeCal();
		frame.revalidate();
	}
	
	public void focusToday() {
		jumpToMonth(today.get(Calendar.MONTH), today.get(Calendar.YEAR));
	}
}
