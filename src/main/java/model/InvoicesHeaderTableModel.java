package model;

import javax.swing.table.DefaultTableModel;

import view.MyGUI;


public class InvoicesHeaderTableModel {


    public static DefaultTableModel setInvoicesHeaderTableModel(MyGUI myGui) {

        DefaultTableModel newTable = new DefaultTableModel() {};
        newTable = (DefaultTableModel) myGui.getInvoiceTable().getModel();
        myGui.getInvoiceTable().setDefaultEditor(Object.class, null);
        return newTable;
    }

}
