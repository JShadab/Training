
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Splash extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3905306917272974277L;
	private JPanel pnlMain;
	// private ParentFrame frame;
	private DefaultListModel<String> listModel;
	private JList<String> list;

	private JButton btnCancel;
	private JButton btnNew;
	private JButton btnOpen;
	private JButton btnRecent;
	private JButton btnClose;
	private HashSet<String> recentPrj;
	ArrayList<String> arrayList;
	int posX = 0;
	int posY = 0;

	public Splash() {
		setSize(750, 500);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);

		pnlMain = new JPanel(new BorderLayout());
		// frame = new ParentFrame();

		ImageIcon image = new ImageIcon(getClass().getResource("/resources/images/Welcome_blanks.png"));
		JLabel label = new JLabel("", image, JLabel.CENTER);

		btnCancel = new JButton(new ImageIcon(getClass().getResource("/resources/images/cancel_btn.png")));
		btnCancel.addActionListener(this);
		btnCancel.setFocusable(false);
		btnCancel.setBounds(710, 10, 30, 30);

		btnNew = new JButton(new ImageIcon(getClass().getResource("/resources/images/new_Icon.png")));
		btnNew.addActionListener(this);
		btnNew.setBounds(40, 146, 160, 120);
		btnNew.setFocusable(false);

		btnOpen = new JButton(new ImageIcon(getClass().getResource("/resources/images/open_Icon.png")));
		btnOpen.addActionListener(this);
		btnOpen.setFocusable(false);
		btnOpen.setBounds(220, 146, 160, 120);

		btnRecent = new JButton(new ImageIcon(getClass().getResource("/resources/images/recent_Icon.png")));
		btnRecent.addActionListener(this);
		btnRecent.setFocusable(false);
		btnRecent.setBounds(40, 284, 160, 120);

		btnClose = new JButton(new ImageIcon(getClass().getResource("/resources/images/close_Icon.png")));
		btnClose.addActionListener(this);
		btnClose.setFocusable(false);
		btnClose.setBounds(220, 284, 160, 120);

		JPanel pnl = new JPanel();
		pnl.setLayout(null);
		pnl.setPreferredSize(new Dimension(400, 400));

		listModel = new DefaultListModel<String>();
		recentPrj = getRecentProjects();
		arrayList = new ArrayList<>();

		Iterator<String> iterator = recentPrj.iterator();

		while (iterator.hasNext()) {
			String project = iterator.next();
			arrayList.add(project);

			int index = project.lastIndexOf("\\");
			char[] cArr = project.toCharArray();

			String prjName = "";
			for (int i = index + 1; i < cArr.length - 4; i++) {
				prjName = prjName + cArr[i];
			}
			listModel.addElement(prjName);
		}
		list = new JList<String>(listModel);
		list.setBounds(420, 134, 280, 280);
		list.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 210)));
		list.setBackground(new Color(245, 245, 245));

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

			}
		});

		pnl.add(btnNew);
		pnl.add(btnOpen);
		pnl.add(btnRecent);
		pnl.add(btnClose);
		pnl.add(btnCancel);
		pnl.add(list);
		pnlMain.add(btnNew);
		pnlMain.add(btnOpen);
		pnlMain.add(btnRecent);
		pnlMain.add(btnClose);
		pnlMain.add(list);
		pnlMain.add(btnCancel);
		add(pnlMain, BorderLayout.WEST);
		pnlMain.add(label, BorderLayout.CENTER);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				posX = e.getX();
				posY = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseDragged(e);
				setLocation(e.getXOnScreen() - posX, e.getYOnScreen() - posY);
			}
		});
		mouseAction();
		setVisible(true);
	}

	public void mouseAction() {

		btnNew.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnNew.setOpaque(true);
				btnNew.setFocusable(false);
				btnNew.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				btnNew.setCursor(new Cursor(Cursor.HAND_CURSOR));
				revalidate();
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnNew.setOpaque(false);
				btnNew.setFocusable(false);
				btnNew.setBorder(null);
			}
		});
		btnOpen.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnOpen.setOpaque(true);
				btnOpen.setFocusable(false);
				btnOpen.setCursor(new Cursor(Cursor.HAND_CURSOR));
				btnOpen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				revalidate();
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnOpen.setOpaque(false);
				btnOpen.setFocusable(false);
				btnOpen.setBorder(null);
			}
		});
		btnRecent.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnRecent.setOpaque(true);
				btnRecent.setFocusable(false);
				btnRecent.setCursor(new Cursor(Cursor.HAND_CURSOR));
				btnRecent.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				revalidate();
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnRecent.setOpaque(false);
				btnRecent.setFocusable(false);
				btnRecent.setBorder(null);
			}
		});
		btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnClose.setOpaque(true);
				btnClose.setFocusable(false);
				btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
				btnClose.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				revalidate();
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnClose.setOpaque(false);
				btnClose.setFocusable(false);
				btnClose.setBorder(null);
			}
		});
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);

			}
		});
	}

	private HashSet<String> getRecentProjects() {
		HashSet<String> arrayList = null;

		try {
			String temp = System.getProperty("user.home") + "\\PetroWellFlow+\\recentPrj.txt";
			File file = new File(temp);
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			arrayList = (HashSet<String>) in.readObject();

			in.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			return new HashSet<String>();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return new HashSet<String>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new HashSet<String>();
		}

		return arrayList;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(btnNew)) {

			setAlwaysOnTop(false);
			dispose();

		}
		if (e.getSource().equals(btnOpen)) {
			setAlwaysOnTop(false);

		}
		if (e.getSource().equals(btnClose)) {
			this.dispose();
			System.exit(0);
		}
		if (e.getSource().equals(btnRecent)) {
		}
		if (e.getSource().equals(btnCancel)) {
			this.dispose();
			System.exit(0);
		}

	}

	public static void main(String[] args) {
		new Splash();
	}
}
