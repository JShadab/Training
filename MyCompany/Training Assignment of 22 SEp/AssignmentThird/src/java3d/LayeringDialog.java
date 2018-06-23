package java3d;

import java.awt.GridBagConstraints; 
import java.awt.GridBagLayout; 
import java.awt.Insets; 
import java.awt.Point; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;

import javax.swing.Box; 
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton; 
import javax.swing.JComboBox;
import javax.swing.JDialog; 
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class LayeringDialog extends JDialog
implements ActionListener {
	private GridData grid;
	private ThreeDCanvas canvas;

	private JComboBox cmbLayer;
	private JComboBox cmbDirection;
	private JButton btnBack;
	private JButton btnForward;
	private JButton btnFastBack;
	private JButton btnFastForward;
	private JButton btnStop;
	private JButton btnReset;
	private JButton btnClose;

	private boolean isStoped=false;

	/**
	 * 
	 * @param owner parameter type JFrame
	 * @param title parameter type String
	 */
	
public LayeringDialog(JFrame owner, String title) {
    super(owner,title);

    setSize(350, 150);

    setLocation(new Point(0, owner.getHeight()-155));

    setResizable(false);

    grid=((DisplayGUI)owner).getGrid();

    canvas=((DisplayGUI)owner).getCanvas();

    creatingDialog();

    addListeners();
}
/**
 * Method to add Listeners
 */
private void addListeners() {
    cmbDirection.addActionListener(this);
    cmbLayer.addActionListener(this);

    btnBack.addActionListener(this);
    btnForward.addActionListener(this);
    btnFastBack.addActionListener(this);
    btnFastForward.addActionListener(this);
    btnStop.addActionListener(this);

    btnReset.addActionListener(this);
    btnClose.addActionListener(this);
}

@Override
public void actionPerformed(ActionEvent e) {
    Object objSource=e.getSource();
    Thread t=null;
    if(objSource.equals(cmbDirection))
    {
        if(cmbDirection.getSelectedIndex()>0){
            DefaultComboBoxModel cmbLayerModel=
            	(DefaultComboBoxModel)cmbLayer.getModel();
            cmbLayerModel.removeAllElements();

            if(cmbDirection.getSelectedIndex()==1){
                int NX=grid.getNX();
                for(int i=1; i<=NX; i++){
                    cmbLayerModel.addElement(i);
                }
            }else if(cmbDirection.getSelectedIndex()==2){
                int NY=grid.getNY();
                for(int i=1; i<=NY; i++){
                    cmbLayerModel.addElement(i);
                }
            }
            else if(cmbDirection.getSelectedIndex()==3){
                int NZ=grid.getNZ();
                for(int i=1; i<=NZ; i++){
                    cmbLayerModel.addElement(i);
                }
            }

            cmbLayer.repaint();
        }else{
            DefaultComboBoxModel cmbLayerModel=(DefaultComboBoxModel)cmbLayer.getModel();
            cmbLayerModel.removeAllElements();
            cmbLayer.repaint();
        }
    }
    else if(objSource.equals(cmbLayer)){
        showGridLayering(0);

    }

    else if(objSource.equals(btnForward)){
        showGridLayering(1);
    }
    else if(objSource.equals(btnBack)){
        showGridLayering(-1);
    }
    else if (objSource.equals(btnFastForward)) {
        isStoped=false;
        if (cmbDirection.getSelectedIndex() > 0) {
                 t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while (Integer.parseInt(cmbLayer.getSelectedItem().toString()) < ((cmbDirection.getSelectedIndex()==1)?grid.getNX():((cmbDirection.getSelectedIndex()==2)?grid.getNY():grid.getNZ()))) {
                            if(isStoped)
                                return;
                            showGridLayering(1);
                            if(Integer.parseInt(cmbLayer.getSelectedItem().toString()) == ((cmbDirection.getSelectedIndex()==1)?grid.getNX():((cmbDirection.getSelectedIndex()==2)?grid.getNY():grid.getNZ()))){
                                return;
                            }
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
                t.start();
        }
    }
    else if (objSource.equals(btnFastBack)) {
        isStoped=false;
        if (cmbDirection.getSelectedIndex() > 0) {
             t = new Thread(new Runnable()    {  public void run() {
                while (Integer.parseInt(cmbLayer.getSelectedItem().toString()) > 0) {
                 if(isStoped)
                      return;
                    showGridLayering(-1);
                            if(Integer.parseInt(cmbLayer.getSelectedItem().toString())==1)
                                return;
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            } } } });
                t.start();     }    }
  
    else if(objSource.equals(btnReset)){
        cmbDirection.setSelectedIndex(0);
        DefaultComboBoxModel cmbLayerModel=(DefaultComboBoxModel)cmbLayer.getModel();
        cmbLayerModel.removeAllElements();
        cmbLayer.repaint();
        canvas.showLayer(0);
    }
    else if(objSource.equals(btnClose)){
        this.dispose();
    }
    else if(objSource.equals(btnStop)){

 t.interrupt(); 
 isStoped=true; 
 }
    }

private void showGridLayering(int i) {
    if(cmbDirection.getSelectedIndex()>0){
        DefaultComboBoxModel cmbLayerModel=(DefaultComboBoxModel)cmbLayer.getModel();
        if(cmbLayerModel.getSize()>0){
            int layer=Integer.parseInt(cmbLayer.getSelectedItem().toString());
            if(cmbDirection.getSelectedIndex()==1){

                if((layer+i)<=grid.getNX() && (layer+i)>0){
                    canvas.showLayer(layer+i);
                    cmbLayer.setSelectedItem(layer+i);
                }

            }else if(cmbDirection.getSelectedIndex()==2){

                if((layer+i)<=grid.getNY() && (layer+i)>0){
                    canvas.showLayer(grid.getNX()+layer+i);
                    cmbLayer.setSelectedItem(layer+i);
                }
            }else if(cmbDirection.getSelectedIndex()==3){

                if((layer+i)<=grid.getNZ() && (layer+i)>0){
                    canvas.showLayer(grid.getNX()+grid.getNY()+layer+i);
                    cmbLayer.setSelectedItem(layer+i);
                }
            }
        }
    }else{
        canvas.showLayer(0);
    }
}
/**
 * method to create Dialog when click layering
 */
private void creatingDialog() {
    setLayout(new GridBagLayout());
    GridBagConstraints gbc=new GridBagConstraints();

    Insets ins=new Insets(10, 2, 10, 2);

    gbc.anchor=GridBagConstraints.FIRST_LINE_START;
    gbc.fill=GridBagConstraints.BOTH;
    gbc.insets=ins;

    gbc.gridx=0;
    gbc.gridy=0;
    add(new JLabel("Direction :"),gbc);

    cmbDirection=new JComboBox(new Object[]{"Select","X-Direction","Y-Directiion","Z-Direction"});
    gbc.gridx=1;
    gbc.gridwidth=2;
    add(cmbDirection,gbc);

    gbc.gridx=2;
    gbc.gridwidth=1;
    add(Box.createHorizontalStrut(10),gbc);

    gbc.gridx=3;
    add(new JLabel("   Layer :"),gbc);

    cmbLayer=new JComboBox();
    gbc.gridx=4;
    add(cmbLayer,gbc);

    btnFastBack=new JButton("<<<");
    gbc.gridx=0;
    gbc.gridy=1;
    add(btnFastBack,gbc);

    btnBack=new JButton("<<");
    gbc.gridx=1;
    add(btnBack,gbc);

    btnStop=new JButton("...");
    gbc.gridx=2;
    add(btnStop,gbc);

    btnForward=new JButton(">>");
    gbc.gridx=3;
    add(btnForward,gbc);

    btnFastForward=new JButton(">>>");
    gbc.gridx=4;
    add(btnFastForward,gbc);

    gbc.gridx=0;
    gbc.gridy=2;
    gbc.gridwidth=2;
    add(Box.createHorizontalStrut(50),gbc);

    btnReset=new JButton("Reset");
    gbc.gridx=1;
    gbc.gridwidth=1;
    add(btnReset,gbc);

    gbc.gridx=2;
    add(Box.createHorizontalStrut(50),gbc);

    btnClose=new JButton("Close");
    gbc.gridx=3;
    add(btnClose,gbc);
}


}
