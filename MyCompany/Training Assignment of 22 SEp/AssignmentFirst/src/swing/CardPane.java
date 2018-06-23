package swing;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.io.File;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/** class for management of cardLayout*/
public class CardPane extends JPanel {

   /**declaration of variable i, tblView and lstView */
	int i = 1;
	private TableView tblView;

	private ListView lstView;

	/**declaration of variable cardLayout and view */
	private CardLayout cardLayout;
	private boolean view;
	/** parameterized constructor of class CardPane*/
	public CardPane(ToolbarStack tool) {
		cardLayout = new CardLayout();
		setLayout(cardLayout);

		tblView = new TableView(this,tool);
		JTable table = tblView.getTable();
		JScrollPane tablePane = new JScrollPane(table);
		add(tablePane, "0");

		lstView = new ListView(this,tool);
		JList<File> list1=lstView.getList();
		
		JPanel pane2 = new JPanel(new BorderLayout());
		pane2.add(list1);
		add(pane2, "1");
	}

	/** getter method getExpTable*/
	public TableView getExpTable() {
		return tblView;
	}
	
	/** getter method getList*/
	public ListView getList() {
		return lstView;
	}
	/**getter method getCardLayout */
	public CardLayout getCardLayout() {
		return cardLayout;
	}
	
	/**getter method isView */
	public boolean isView() {
		return view;
	}
	/**setter method setView */
	public void setView(boolean view) {
		this.view = view;
	}
	
	
}
