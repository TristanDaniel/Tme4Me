package app;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class DayView {

	private int day, month, year;
	private JFrame frame;
	private Container content;
	
	private static final String[] months = {"January", "February",
			"March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December"};
	
	public DayView(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
		
		frame = new JFrame(this.day + " " + months[this.month] + ", " + this.year);
		content = frame.getContentPane();
		
		createView();
		
		frame.setVisible(true);
	}
	
	private void createView() {
		
	}
}
