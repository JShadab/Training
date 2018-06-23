package swing;

import java.awt.Component;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import swing.ListView.ListModel;

public class TreeView implements TreeSelectionListener {
	public JTree tree;
	private JFrame frame;
	private JScrollPane treeView;
	private ToolbarStack toolBarStack;
	private CardPane crdpane;
	DefaultMutableTreeNode nodeAddress = new DefaultMutableTreeNode(
			"My Computer");

	// Parameterized Constructors of class TreeView
	public TreeView(CardPane cardPane, ToolbarStack tool, JFrame explorer) {
		this(cardPane, tool);
		this.frame = explorer;
	}

	public TreeView(CardPane cardPane, ToolbarStack tool) {
		// Create the nodes.
		this.toolBarStack = tool;
		this.crdpane = cardPane;
		DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(
				"My Computer");
		nodeCreation(treeNode);
		// Create a tree that allows one selection at a time.
		tree = new JTree(treeNode);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		// renderer
		tree.setCellRenderer(new TreeRendering());

		// Listen for when the selection changes.
		tree.addTreeSelectionListener(this);

		// Create the scroll pane and add the tree to it.
		treeView = new JScrollPane(tree,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	}

	// Return an object of tree wrapped in scrollpane.
	JScrollPane getTree() {
		return treeView;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();
		
			File f= new File(node.toString());
			toolBarStack.getStackBack().push(f);
		generateEvent(e);

		if (node == null) {
			return;
		}
		File file = new File(node.toString());
		if (file.toString().equals("My Computer")) {
			
			TableView expTable = new TableView(crdpane, toolBarStack);
			crdpane.getExpTable().getTable()
					.setModel((expTable).new TableModel(file));
			TableColumnModel model = crdpane.getExpTable().getTable()
					.getColumnModel();
			model.getColumn(0).setCellRenderer(expTable.new RenderingTable());
			
			ListView listView= new ListView(crdpane, toolBarStack);
			crdpane.getList().getList().setModel((listView).new ListModel(file));
		
			
			
		}
		if (file.isDirectory()) {
			crdpane.setView(true);
			toolBarStack.getStackBack().push(file);
			TableView expTable = new TableView(crdpane, toolBarStack);

			crdpane.getExpTable().getTable()
					.setModel((expTable).new TableModel(file));
			TableColumnModel model = crdpane.getExpTable().getTable()
					.getColumnModel();
			model.getColumn(0).setCellRenderer(expTable.new RenderingTable());

			for (File files : file.listFiles()) {

				if (files.isDirectory()) {
					DefaultMutableTreeNode folder = new DefaultMutableTreeNode(
							files);
					node.add(folder);
				}
			}
		}
	}

	private void generateEvent(TreeSelectionEvent e) {
		nodeAddress = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		File file = new File(nodeAddress.toString());
		if (file.isDirectory()) {
			String str = file.toString();
			//int i = str.lastIndexOf('\\');
		//	String filePath = str.substring(i + 1);
			AddressBar.txtAddressBar.setText(str);
			frame.setTitle(str);
		}
		if (file.getParentFile() == null) {

			AddressBar.txtAddressBar.setText(file.getPath());
			frame.setTitle(file.getPath());

		}
	}

	private void nodeCreation(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode drive = null;
		File files[] = File.listRoots();
		for (File file : files) {
			drive = new DefaultMutableTreeNode(file);
			top.add(drive);
		}
	}

	class TreeRendering extends JLabel implements TreeCellRenderer {
		final ImageIcon myComputerIcon = new ImageIcon(getClass().getResource(
				"/images/MyComputer1.png"));

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean selected, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
				Object userObject = ((DefaultMutableTreeNode) value)
						.getUserObject();

				if (value.toString().equals("My Computer")) {
					setIcon(myComputerIcon);
					setText(value.toString());
					setFont(tree.getFont());
					setOpaque(false);
					return this;
				}
				if (userObject instanceof File) {
					File file = (File) userObject;

					if (file.getParentFile() == null) {
						setText(file.getPath());
					} else {
						String str = file.toString();
						int i = str.lastIndexOf('\\');
						String filePath = str.substring(i + 1);
						setText(filePath);
					}

					FileSystemView fsv = FileSystemView.getFileSystemView();
					Icon icn = fsv.getSystemIcon(file);
					setIcon(icn);
				}

			}
			return this;

		}
	}

}
