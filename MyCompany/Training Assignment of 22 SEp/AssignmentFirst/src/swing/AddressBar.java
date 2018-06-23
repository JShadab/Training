package swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class AddressBar extends JPanel {

	 static JPanel pnlAddress;
	 static JToolBar toolbar;
	static  JButton  btnGo=new JButton("Go");
	public static JTextField txtAddressBar = new JTextField("My Computer");

	public AddressBar() {

		setLayout(new BorderLayout());
		Toolbar tool = new Toolbar();
		toolbar = tool.getToolBar();
		this.add(toolbar, BorderLayout.NORTH);

		// Design For AddressBar
		pnlAddress = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(2, 2, 2, 2);
		JLabel lbAddress = new JLabel("ADDRESS");
		gbc.weightx = 0.1;
		pnlAddress.add(lbAddress, gbc);


		gbc.weightx = 0.8;
		pnlAddress.add(txtAddressBar, gbc);

		ImageIcon iconGo = new ImageIcon(getClass().getResource(
				"..\\images\\go.png"));

		btnGo.setIcon(iconGo);
		btnGo.setBackground(Color.white);

		gbc.weightx = 0.1;
		pnlAddress.add(btnGo, gbc);

		this.add(pnlAddress);
		btnGo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String filePath=txtAddressBar.getText();
				
			}
		});

	}

	AddressBar getTopPanel() {
		return this;
	}
	public static JButton getBtnGo() {
		return btnGo;
	}

	public void setTxtAddressBar(String address) {
		txtAddressBar.setText(address);
	}

	JToolBar getToolBar() {
		return toolbar;
	}
}
