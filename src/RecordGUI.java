import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import com.sun.org.apache.regexp.internal.RE;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RecordGUI {

	private JFrame frame;
	JButton btnNewButton;
	JButton btnRecord;
	private Record rec;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecordGUI window = new RecordGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RecordGUI() {
		rec = new Record();
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		btnRecord = new JButton("Record");
		springLayout.putConstraint(SpringLayout.SOUTH, btnRecord, -92, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnRecord, -108, SpringLayout.EAST, frame.getContentPane());
		btnRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rec.recording) {
					//program is recording so pause the recording and show to play button
					rec.pause();
					btnRecord.setText("Record");
					//there is a new recording at this stage
					btnNewButton.setEnabled(true);
				}else {
					//program is not recording so  we start recording
					rec.record();
					btnRecord.setText("Stop Recording");
					btnNewButton.setEnabled(false);//prevent playing recording while recording is in progress
				}
			}
		});
		frame.getContentPane().add(btnRecord);
		
		btnNewButton = new JButton("Play Recording");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!rec.istargetSampleEmpty)
					rec.playRecording();
			}
		});
		btnNewButton.setEnabled(false);
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 0, SpringLayout.NORTH, btnRecord);
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, -71, SpringLayout.WEST, btnRecord);
		frame.getContentPane().add(btnNewButton);
	}
}
