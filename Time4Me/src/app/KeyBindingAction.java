package app;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class KeyBindingAction extends AbstractAction {

	private static final long serialVersionUID = 7941575517398059679L;
	private int actionType;
	
	public KeyBindingAction() {
		super();
		actionType = 0;
	}
	
	public KeyBindingAction(int actionType) {
		super();
		this.actionType = actionType;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (actionType) {
			case 0:
				System.out.println("no");
				break;
			case 1:
				incrMonth();
				break;
			case 2:
				decrMonth();
				break;
			case 3:
				jumpMonth();
				break;
			case 4:
				jumpToday();
				break;
			default:
				return;
		}
	}
	
	private void incrMonth() {
		Driver.getApp().getMView().incMonth();
	}
	
	private void decrMonth() {
		Driver.getApp().getMView().decMonth();
	}
	
	private void jumpMonth() {
		//make window, ask for date MM/YYYY, button closes window and calls
		//jumpToMonth with parsed date.
		JFrame frame = new JFrame("Enter Date");
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		frame.setSize(new Dimension(270, 105));
		
		JLabel prompt = new JLabel("Please enter the target date MM/YYYY");
		content.add(prompt, BorderLayout.NORTH);
		
		TextField input = new TextField(15);
		content.add(input, BorderLayout.CENTER);
		
		JButton submit = new JButton("OK");
		submit.addActionListener(e -> {
			String[] strDate = input.getText().split("/");
			Driver.getApp().getMView()
			.jumpToMonth(Integer.parseInt(strDate[0])-1, Integer.parseInt(strDate[1]));
			frame.setVisible(false);
		});
		content.add(submit, BorderLayout.SOUTH);
		
		frame.setVisible(true);
	}
	
	private void jumpToday() {
		Driver.getApp().getMView().focusToday();
	}

}
