package demo;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Reservation extends JApplet {

	public void init() {
		// Put your code here
	}

	public void start() {

		Container con = getContentPane();

		con.setLayout(new FlowLayout(FlowLayout.CENTER));

		JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 50, 50));

		JButton btnBookTicket = new JButton("Book Ticket");
		JButton btnCancelTicket = new JButton("Cancel Ticket");
		JButton btnTrainEnquiry = new JButton("Train Enquiry");
		JButton btnFairEnquiry = new JButton("Fair Enquiry");

		btnBookTicket.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				bookTicket();

			}
		});

		btnCancelTicket.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cancelTicket();

			}
		});

		btnTrainEnquiry.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				trainEnquiry();

			}
		});

		btnFairEnquiry.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fairEnquiry();

			}
		});

		buttonPanel.add(btnBookTicket);
		buttonPanel.add(btnCancelTicket);
		buttonPanel.add(btnTrainEnquiry);
		buttonPanel.add(btnFairEnquiry);

		getContentPane().add(buttonPanel);

		setLocation(200, 100);
		setSize(500, 500);

	}

	private void bookTicket() {

		final JDialog dialog = new JDialog(
				SwingUtilities.windowForComponent(this));
		dialog.setTitle("Book a ticket");

		dialog.getContentPane().setLayout(new FlowLayout());

		JLabel lbSource = new JLabel("Source");
		JLabel lbDestination = new JLabel("Destination");
		JLabel lbName = new JLabel("Name");
		JLabel lbAge = new JLabel("Age");
		JLabel lbGender = new JLabel("Gender");
		JLabel lbDate = new JLabel("Date");

		JTextField txtSource = new JTextField(15);
		JTextField txtDestination = new JTextField(15);
		JTextField txtName = new JTextField(15);
		JTextField txtAge = new JTextField(15);
		JTextField txtGender = new JTextField(15);
		JTextField txtDate = new JTextField(15);

		JButton btnBook = new JButton("Book");

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});

		JPanel panel = new JPanel(new GridLayout(7, 2, 25, 15));

		panel.add(lbSource);
		panel.add(txtSource);

		panel.add(lbDestination);
		panel.add(txtDestination);

		panel.add(lbName);
		panel.add(txtName);

		panel.add(lbAge);
		panel.add(txtAge);

		panel.add(lbGender);
		panel.add(txtGender);

		panel.add(lbDate);
		panel.add(txtDate);

		panel.add(btnBook);
		panel.add(btnExit);

		dialog.add(panel);

		// dialog.setModal(true);

		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		dialog.setModal(true);
		dialog.setSize(500, 400);
		dialog.setVisible(true);
	}

	private void cancelTicket() {

		final JDialog dialog = new JDialog(
				SwingUtilities.windowForComponent(this));

		dialog.setTitle("Cancel a ticket");

		dialog.getContentPane().setLayout(new FlowLayout());

		JLabel lbPNR = new JLabel("Enter PNR");
		JTextField txtPNR = new JTextField(15);

		JButton btnBook = new JButton("Cancel Ticket");

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});

		JPanel panel = new JPanel(new GridLayout(2, 2, 25, 15));

		panel.add(lbPNR);
		panel.add(txtPNR);

		panel.add(btnBook);
		panel.add(btnExit);

		dialog.add(panel);

		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		dialog.setModal(true);
		dialog.setSize(500, 400);
		dialog.setVisible(true);

	}

	private void trainEnquiry() {

		final JDialog dialog = new JDialog(
				SwingUtilities.windowForComponent(this));

		dialog.setTitle("Train Enquiry");

		dialog.getContentPane().setLayout(new FlowLayout());

		JLabel lbPNR = new JLabel("Enter Train Name/Number");
		JTextField txtPNR = new JTextField(25);

		JButton btnBook = new JButton("Find Train");

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});

		JPanel panel = new JPanel(new GridLayout(2, 2, 25, 15));

		panel.add(lbPNR);
		panel.add(txtPNR);

		panel.add(btnBook);
		panel.add(btnExit);

		dialog.add(panel);

		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		dialog.setModal(true);
		dialog.setSize(700, 400);
		dialog.setVisible(true);

	}

	private void fairEnquiry() {

		final JDialog dialog = new JDialog(
				SwingUtilities.windowForComponent(this));

		dialog.setTitle("Fair Enquiry");

		dialog.getContentPane().setLayout(new FlowLayout());

		JLabel lbPNR = new JLabel("Enter Train Name/Number");
		JTextField txtPNR = new JTextField(25);

		JLabel lbClass = new JLabel("Class");
		JComboBox<String> cmbClass = new JComboBox<>(new String[] { "SLEEPER",
				"AC-2", "AC-3", "2-S" });

		JButton btnBook = new JButton("Find Train");

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});

		JPanel panel = new JPanel(new GridLayout(3, 2, 25, 15));

		panel.add(lbPNR);
		panel.add(txtPNR);
		
		panel.add(lbClass);
		panel.add(cmbClass);

		panel.add(btnBook);
		panel.add(btnExit);

		dialog.add(panel);

		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		dialog.setModal(true);
		dialog.setSize(700, 400);
		dialog.setVisible(true);

	}
}
