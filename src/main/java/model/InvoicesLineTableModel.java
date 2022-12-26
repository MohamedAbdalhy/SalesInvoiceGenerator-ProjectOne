package model;

import javax.swing.table.DefaultTableModel;

import view.GUI;
import view.GUI;


public class InvoicesLineTableModel {

    public static DefaultTableModel setInvoicesLineTableModel(GUI myGui) {
        DefaultTableModel newTable = new DefaultTableModel() {};
        newTable = (DefaultTableModel) myGui.getInvoicesLineTable().getModel();
        myGui.getInvoicesLineTable().setDefaultEditor(Object.class, null);
        return newTable;
    }
}
