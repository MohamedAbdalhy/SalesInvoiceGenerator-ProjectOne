package model;

import javax.swing.table.DefaultTableModel;

import view.MyGUI;


public class InvoicesLineTableModel {

    public static DefaultTableModel setInvoicesLineTableModel(MyGUI myGui) {
        DefaultTableModel newTable = new DefaultTableModel() {};
        newTable = (DefaultTableModel) myGui.getInvoicesLineTable().getModel();
        myGui.getInvoicesLineTable().setDefaultEditor(Object.class, null);
        return newTable;
    }
}
