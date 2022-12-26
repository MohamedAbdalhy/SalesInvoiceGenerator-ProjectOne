package model;

import javax.swing.table.DefaultTableModel;

import view.GUI;
import view.GUI;


public class InvoicesHeaderTableModel {


    public static DefaultTableModel setInvoicesHeaderTableModel(GUI myGui) {

        DefaultTableModel newTable = new DefaultTableModel() {};
        newTable = (DefaultTableModel) myGui.getInvoiceTable().getModel();
        myGui.getInvoiceTable().setDefaultEditor(Object.class, null);
        return newTable;
    }

}
