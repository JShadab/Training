package swing;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class TableView {
	private JTable tbl;
	private ToolbarStack toolbarStack;
	private CardPane crdpane;
	private JFrame frame;
	boolean isChecked = true;
	JButton btnBack,btnForword;
	File firstFile = new File("My Computer");
/**
 * 
 * @param cardPane argument of type CardPane
 * @param tool argument of type ToolbarStack
 */
	public TableView(CardPane cardPane, ToolbarStack tool) {
		this.toolbarStack = tool;
		this.crdpane = cardPane;
		tbl = new JTable(new TableModel(firstFile));
		tbl.setShowGrid(false);

		TableColumnModel tblColumnModel = tbl.getColumnModel();
		tblColumnModel.getColumn(0).setCellRenderer(new RenderingTable());

		tbl.setCellSelectionEnabled(true);
		tbl.addMouseListener(new MouseAdapter() {
			/**
			 * when click mouse click event generated 
			 */
			 		 
			 			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					int column = target.getSelectedColumn();
					File file = (File) tbl.getModel().getValueAt(row, column);
					toolbarStack.getStackBack().push(file);
					btnBack.setEnabled(true);
					btnForword.setEnabled(true);
					generateEvent(file);
//					if(toolbarStack.getStackBack().isEmpty()){
//						Toolbar toolbar= new Toolbar();
//					toolbar.getBtnBack().setEnabled(false);
//						
//					}

					if (file.isDirectory()) {
						crdpane.setView(true);
						toolbarStack.getStackBack().push(file);
						
						tbl.setModel(new TableModel(file));
						tbl.getColumnModel().getColumn(0)
								.setCellRenderer(new RenderingTable());
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
	 * @param isChecked
	 */
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
/**
 * 
 * @param file pass when event is generated
 * use for set path Address to Addressbar and  and titlebar
 */
	protected void generateEvent(File file) {
		if (isChecked) {
			AddressBar.txtAddressBar.setText(file.toString());
			frame.setTitle(file.toString());
		}

		if (file.isDirectory()) {
			String str = file.toString();
//			int i = str.lastIndexOf('\\');
//			String filePath = str.substring(i + 1);
			AddressBar.txtAddressBar.setText(str);
			frame.setTitle(str);
		}
		if (file.getParentFile() == null) {
			AddressBar.txtAddressBar.setText(file.getPath());
			frame.setTitle(file.getPath());

		}
	}

	public void setFirstFile(File firstFile) {
		this.firstFile = firstFile;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JTable getTable() {
		return tbl;
	}

	// inner class
	public class TableModel extends AbstractTableModel {

		String header[] = { "Name", "Type", "Total Size", "Free Space" };
		Object[][] data;

		public TableModel(File file) {

			if (file.getName().equals("My Computer")) {
				File files[] = File.listRoots();

			
				data = new Object[files.length][getColumnCount()];

				for (int i = 0; i < files.length; i++) {
					data[i][0] = files[i];
					data[i][1] = "Local Disk";
					data[i][2] = (files[i].getTotalSpace())
							/ (1024 * 1024 * 1024) + " " + "GB";
					data[i][3] = (files[i].getFreeSpace())
							/ (1024 * 1024 * 1024) + " " + "GB";
				}
			} else {
				initTable(file);

				if (crdpane.isView()) {
					crdpane.setView(false);
					crdpane.getList().getList()
							.setModel(crdpane.getList().new ListModel(file));

				}
			}
			fireTableDataChanged();
		}

		public void initTable(File file) {

			if (file.isDirectory()) {

				File[] f = file.listFiles();
				data = new Object[f.length][getColumnCount()];

				for (int i = 0; i < f.length; i++) {
					data[i][0] = f[i];
					if (f[i].isDirectory()) {
						data[i][1] = "File Folder";
						data[i][2] = " ";
						data[i][3] = "  ";
					} else {
						String extension = f[i].getAbsolutePath();
						int index = extension.lastIndexOf('.');
						String fileType = extension.substring(index + 1);
						int bytes = (int) f[i].length();
						data[i][1] = fileType + " " + "File";
						data[i][2] = bytes / (1024) + " " + "KB";
						data[i][3] = "";
					}
				}
			}
			fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public int getColumnCount() {
			return header.length;
		}

		@Override
		public String getColumnName(int column) {
			// TODO Auto-generated method stub
			return header[column];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}

	}

	// class for Table rendering
	public class RenderingTable extends JLabel implements TableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			FileSystemView fsv = FileSystemView.getFileSystemView();
			Icon icn = fsv.getSystemIcon((File) value);
			setIcon(icn);

			if ((((File) value).getParentFile()) == null) {
				setText(((File) value).getPath());
				return this;

			}
			String str = value.toString();
			int i = str.lastIndexOf('\\');
			String filePath = str.substring(i + 1);
			setText(filePath);

			return this;
		}

	}
	public void setBtnBack(JButton btnBack) {
		this.btnBack = btnBack;
	}
	public void setBtnForword(JButton btnForword) {
		this.btnForword = btnForword;
	}

}
