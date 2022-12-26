package controller;

import java.text.ParseException;
import java.util.ArrayList;

import model.*;
import view.GUI;


public class InvoicesHeaderController {


    static void updateDateCoulmn(GUI myGui, ArrayList<InvoiceHeader> invoices) {
        InvoicesHeaderTableModel.setInvoicesHeaderTableModel(myGui)
                .setValueAt(invoices.get(myGui.getInvoiceTable().getSelectedRow()).getInoviceDate(), myGui.getInvoiceTable().getSelectedRow(), 1);
    }

    static void updateCustomerNameCoulmn(GUI myGui, ArrayList<InvoiceHeader> invoices) {
        InvoicesHeaderTableModel.setInvoicesHeaderTableModel(myGui)
                .setValueAt(invoices.get(myGui.getInvoiceTable().getSelectedRow()).getInoviceCustomerName(), myGui.getInvoiceTable().getSelectedRow(), 2);
    }

    static void updateTotalPriceCoulmn(GUI myGui, ArrayList<InvoiceHeader> invoices) {
        InvoicesHeaderTableModel.setInvoicesHeaderTableModel(myGui)
                .setValueAt(invoices.get(myGui.getInvoiceTable().getSelectedRow()).getInoviceTotal(), myGui.getInvoiceTable().getSelectedRow(), 3);
    }

    static void showCreateNewInvoiceDialog(GUI myGui) {
        //show create new invoice dialog box
        myGui.setLocations();
        GUI.getNewInvoiceDialog().setVisible(true);
    }

    static void addNewInvoice(GUI myGui, ArrayList<InvoiceHeader> invoices) {
        if (myGui.getNewCustomerName().getText().equalsIgnoreCase("")) {
            GUI.getNewInvoiceDialog().setModal(false);
            GUI.setJOptionPaneMessagMessage(myGui.getInvoicesTablePanel(), "Please Enter A Name For The Customer", "Empty Name Entered", "ERROR_MESSAGE");
            GUI.getNewInvoiceDialog().setModal(true);
            showCreateNewInvoiceDialog(myGui);
        } else {
            try {
                mainController.maxNumberOfExistedInvoices++;

                InvoiceHeader newRow = new InvoiceHeader((mainController.maxNumberOfExistedInvoices), (myGui.getDate().parse(myGui.getNewInvoiceDateField().getText())), (myGui.getNewCustomerName().getText()));
                invoices.add(newRow);
                TablesController.loadInvoicesHeaderTable(myGui, invoices);
                GUI.getNewInvoiceDialog().setVisible(false);
                myGui.getInvoiceTable().setRowSelectionInterval((invoices.size() - 1), (invoices.size() - 1));
                myGui.getNewCustomerName().setText("");
                myGui.getNewInvoiceDateField().setText("");
                InvoicesLineController.updater(myGui, invoices, invoices.size() - 1);
                TablesController.loadInvoicesLineTable(myGui, invoices);
            } catch (ParseException ex) {
                GUI.getNewInvoiceDialog().setModal(false);
                GUI.setJOptionPaneMessagMessage(myGui.getInvoicesTablePanel(), "Please Enter A Valid Date", "Invalid Date Entered", "ERROR_MESSAGE");
                GUI.getNewInvoiceDialog().setModal(true);
                showCreateNewInvoiceDialog(myGui);
            }

        }
    }

    static void deleteSelectedInvoice(GUI myGui, ArrayList<InvoiceHeader> invoices) {
        int DeletedInvoice = myGui.getInvoiceTable().getSelectedRow();

        if (DeletedInvoice == -1) {
            GUI.setJOptionPaneMessagMessage(myGui.getInvoicesTablePanel(), "Please select invoice row you want to remove ", "Error", "ERROR_MESSAGE");
        } else {
            invoices.remove(DeletedInvoice);
            TablesController.loadInvoicesHeaderTable(myGui, invoices);
            if (!invoices.isEmpty()) {
                myGui.getInvoiceTable().setRowSelectionInterval((invoices.size() - 1), (invoices.size() - 1));
                InvoicesLineController.updater(myGui, invoices, invoices.size() - 1);
                TablesController.loadInvoicesLineTable(myGui, invoices);
            } else {
                while (InvoicesLineTableModel.setInvoicesLineTableModel(myGui).getRowCount() > 0) {
                    InvoicesLineTableModel.setInvoicesLineTableModel(myGui).removeRow(0);
                }
            }
        }
    }

    public static void calculateInvoiceTableTotal(ArrayList<InvoiceHeader> invoices) {
        float total;
        for (int i = 0; i < invoices.size(); i++) {
            total = 0;
            for (int j = 0; j < invoices.get(i).getInvoicerow().size(); j++) {
                total = total + ((invoices.get(i).getInvoicerow().get(j).getItemPrice()) * (invoices.get(i).getInvoicerow().get(j).getItemCount()));
            }
            invoices.get(i).setInoviceTotal(total);
        }
    }
}
