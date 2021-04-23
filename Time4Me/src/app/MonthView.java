package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MonthView {

	private int month;
	private int year;
	private JPanel mainPanel, menuBar, calPanel;
	private JPanel[][] days;
	private Calendar cal;
	private int height, width, daySide, gap, barHeight, buttonWidth, barSpace;
	private JButton incrButton, decrButton;
	private JFrame frame;
	
	public MonthView(JFrame frame) {
		this.frame = frame;
		cal = Calendar.getInstance();
		cal.setTime(new Date());
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		mainPanel = new JPanel();
		menuBar = new JPanel();
		calPanel = new JPanel();
		daySide = 75;
		gap = 10;
		barHeight = 50;
		buttonWidth = 60;
		height = (6 * daySide) + (7 * gap) + barHeight;
		width = (7 * daySide) + (8 * gap);
		barSpace = width - (2 * buttonWidth);
		
		mainPanel.setMinimumSize(new Dimension(width, height));
		menuBar.setMinimumSize(new Dimension(width, barHeight));
		calPanel.setMinimumSize(new Dimension(width, height - barHeight));
		
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
		
		centerContentPanel.setMinimumSize(new Dimension(barSpace, barHeight));
		
		menuBar.add(leftButtonPanel, BorderLayout.WEST);
		menuBar.add(centerContentPanel, BorderLayout.CENTER);
		menuBar.add(rightButtonPanel, BorderLayout.EAST);
		
		//make cal
		makeCal();
		
		//make main
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(menuBar, BorderLayout.NORTH);
		mainPanel.add(calPanel, BorderLayout.CENTER);
	}
	
	private void decMonth() {
		month--;
		if (month < cal.getActualMinimum(Calendar.MONTH)) {
			month = cal.getActualMaximum(Calendar.MONTH);
			year--;
		}
		
		makeCal();
		frame.revalidate();
	}
	private void incMonth() {
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
		int firstDay = cal.get(Calendar.DAY_OF_WEEK);
		int curDay = 1;
		days = new JPanel[6][7];
		
		calPanel.removeAll();
		
		calPanel.setLayout(new GridLayout(6, 7));
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				days[i][j] = new JPanel();
				days[i][j].setMinimumSize(new Dimension(daySide + (gap / 2), 
						daySide + (gap / 2)));
				
				if (curDay == 1) {
					if ((j+1) == firstDay) {
						JButton dayBtn = new JButton("1");
						int dayNum = curDay;
						dayBtn.addActionListener(e -> {new DayView(dayNum);});
						dayBtn.setMinimumSize(new Dimension(daySide, daySide));
						days[i][j].add(dayBtn);
						curDay++;
					}
				} else if (curDay <= cal.getActualMaximum(Calendar.DATE)) {
					JButton dayBtn = new JButton("" + curDay);
					int dayNum = curDay;
					dayBtn.addActionListener(e -> {new DayView(dayNum);});
					dayBtn.setMinimumSize(new Dimension(daySide, daySide));
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
}
