package model;

import javax.swing.table.DefaultTableModel;

import view.GUI;


public class InvoicesHeaderTableModel {


    public static DefaultTableModel setInvoicesHeaderTableModel(GUI gui) {

        DefaultTableModel newTable = new DefaultTableModel() {};
        newTable = (DefaultTableModel) gui.getInvoiceTable().getModel();
        gui.getInvoiceTable().setDefaultEditor(Object.class, null);
        return newTable;
    }

}
