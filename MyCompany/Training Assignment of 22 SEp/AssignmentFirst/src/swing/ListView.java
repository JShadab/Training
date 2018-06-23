 package swing;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.filechooser.FileSystemView;

public class ListView
{
	private JList<File> lstFile;
	private boolean isChecked = true;
	private ToolbarStack toolBarStack;
	private JFrame frame;
	private int count;
	private JLabel lblStatus = new JLabel();;
	private CardPane crdPane;
	JButton btnBack,btnForword;

	public ListView(CardPane cardPane, ToolbarStack tool) {
		this.crdPane = cardPane;
		this.toolBarStack = tool;
		// setLayout(new BorderLayout());
		creatingList();
		ScrollPane scrollpane = new ScrollPane();
		scrollpane.add(lstFile);
	}



	private void creatingList() {
		lstFile = new JList<File>(new ListModel(new File("My Computer")));
		lstFile.setCellRenderer(new RenderingList());

		// listFile.setLayoutOrientation(JList.VERTICAL_WRAP);
		lstFile.setVisibleRowCount(-1);
		lstFile.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {

					File file = lstFile.getModel().getElementAt(
							lstFile.getSelectedIndex());
					toolBarStack.getStackBack().push(file);
					btnBack.setEnabled(true);
					btnForword.setEnabled(true);
					generateEvent(file);

//					if(toolBarStack.getStackBack().isEmpty()){
//						Toolbar toolbar= new Toolbar();
//					toolbar.getBtnBack().setEnabled(false);
//						
//					}
//					
					if (file.isDirectory()) {
						crdPane.setView(true);
						// utils.getBack().push(file);
						lstFile.setModel(new ListModel(file));
					} else {
						try {
							if (Desktop.isDesktopSupported()) {
								Desktop.getDesktop().browse(file.toURI());
							}

						} catch (IOException e1) {
							
							
							
							JOptionPane.showMessageDialog(null,
									"Unknown File Type", "Error",
									JOptionPane.INFORMATION_MESSAGE);
					
							
						}
					}

				}
			}

		});
	}
/**
 * 
 * @param file pass when mouse click event is generated 
 */
	protected void generateEvent(File file) {

		if (!isChecked) {
			String str = file.toString();
			int i = str.lastIndexOf('\\');
			String filePath = str.substring(i + 1);
			AddressBar.txtAddressBar.setText(filePath);
			frame.setTitle(filePath);
		} 
			if (file.isDirectory()) {
				String str = file.toString();
				//int i = str.lastIndexOf('\\');
				//String filePath = str.substring(i + 1);
				AddressBar.txtAddressBar.setText(str);
				frame.setTitle(str);
			}
		   if (file.getParentFile() == null) {
			
				AddressBar.txtAddressBar.setText(file.getPath());
				frame.setTitle(file.getPath());

			}
		}
	/**
	 * 
	 * @param frame setting
	 */

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public void setBtnBack(JButton btnBack) {
		this.btnBack = btnBack;
	}
/**
 * 
 * @return JList
 */
	public JList<File> getList() {
		return lstFile;
	}

	/**
	 * 
	 * @author ravi.chakravarti
	 *
	 */
	public class ListModel extends AbstractListModel<File> {

		ArrayList<File> arrayLst;

		public ListModel(File f) {

			arrayLst = new ArrayList<File>();

			if (f.getName().equals("My Computer")) {
				File files[] = File.listRoots();
				count = files.length;

				lblStatus.setText("Total Objects:" + " " + count);
				for (File file : files) {

					arrayLst.add(file);
				}

			} else {
				initList(f);

				if (crdPane.isView()) {

					crdPane.setView(false);
					crdPane.getExpTable().getTable()
							.setModel(crdPane.getExpTable().new TableModel(f));

					crdPane.getExpTable()
							.getTable()
							.getColumnModel()
							.getColumn(0)
							.setCellRenderer(
									crdPane.getExpTable().new RenderingTable());

				}
			}

		}
/**
 * 
 * @param f file is listed and then add to list
 */
		private void initList(File f) {
			if (f.isDirectory()) {
				arrayLst.clear();
				File files[] = f.listFiles();
				count = files.length;
				lblStatus.setText("Total Objects:" + " " + count);
				for (File file : files) {
					arrayLst.add(file);
				}
			}

		}

		@Override
		public int getSize() {
			
			return arrayLst.size();
		}

		@Override
		public File getElementAt(int index) {

			return arrayLst.get(index);
		}

	}

/**
 * 
 * @author ravi.chakravarti
 *class for rendering 
 */
	public class RenderingList extends JLabel implements
			ListCellRenderer<File> {

		@Override
		public Component getListCellRendererComponent(
				JList<? extends File> list, File value, int index,
				boolean isSelected, boolean cellHasFocus) {

			FileSystemView fsv = FileSystemView.getFileSystemView();
			Icon icn = fsv.getSystemIcon(value);
			setIcon(icn);
			
			if ((value.getParentFile()) == null) {
				setText(value.getPath());
				return this;

			}
			// address of last clicked directory 
			String str = value.toString();
			int i = str.lastIndexOf('\\');
			String filePath = str.substring(i + 1);
			setText(filePath);

			return this;
		}

	}

	public JLabel getStatusLable() {
		return lblStatus;
	}



	public void setBtnForward(JButton forward) {
		this.btnForword=forward;
		
	}

}
