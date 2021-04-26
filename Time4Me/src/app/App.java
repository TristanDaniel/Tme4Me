package app;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

public class App {

	private JFrame frame;
	private Container content;
	private MonthView mView;
	
	public App() {
		frame = new JFrame("Time4Me");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		content = frame.getContentPane();
		
		mView = new MonthView(frame);
		
		content.add(mView.getView());
		//frame.setMinimumSize(new Dimension(625, 570));
		frame.setSize(new Dimension(mView.getView().getWidth(), mView.getView().getHeight()));
		frame.setVisible(true);
	}
	
	public MonthView getMView() {
		return mView;
	}
}
