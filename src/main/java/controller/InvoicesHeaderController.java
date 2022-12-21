package controller;

import java.text.ParseException;
import java.util.ArrayList;

import model.*;
import view.GUI;


public class InvoicesHeaderController {


    static void updateDateCoulmn(GUI gui, ArrayList<InvoiceHeader> invoices) {
        InvoicesHeaderTableModel.setInvoicesHeaderTableModel(gui)
                .setValueAt(invoices.get(gui.getInvoiceTable().getSelectedRow()).getInoviceDate(), gui.getInvoiceTable().getSelectedRow(), 1);
    }

    static void updateCustomerNameCoulmn(GUI gui, ArrayList<InvoiceHeader> invoices) {
        InvoicesHeaderTableModel.setInvoicesHeaderTableModel(gui)
                .setValueAt(invoices.get(gui.getInvoiceTable().getSelectedRow()).getInoviceCustomerName(), gui.getInvoiceTable().getSelectedRow(), 2);
    }

    static void updateTotalPriceCoulmn(GUI gui, ArrayList<InvoiceHeader> invoices) {
        InvoicesHeaderTableModel.setInvoicesHeaderTableModel(gui)
                .setValueAt(invoices.get(gui.getInvoiceTable().getSelectedRow()).getInoviceTotal(), gui.getInvoiceTable().getSelectedRow(), 3);
    }

    static void showCreateNewInvoiceDialog(GUI gui) {
        //show create new invoice dialog box
        gui.setLocations();
        GUI.getNewInvoiceDialog().setVisible(true);
    }

    static void addNewInvoice(GUI gui, ArrayList<InvoiceHeader> invoices) {
        if (gui.getNewCustomerName().getText().equalsIgnoreCase("")) {
            GUI.getNewInvoiceDialog().setModal(false);
            GUI.setJOptionPaneMessagMessage(gui.getInvoicesTablePanel(), "Please Enter A Name For The Customer", "Empty Name Entered", "ERROR_MESSAGE");
            GUI.getNewInvoiceDialog().setModal(true);
            showCreateNewInvoiceDialog(gui);
        } else {
            try {
                mainController.maxNumberOfExistedInvoices++;

                InvoiceHeader newRow = new InvoiceHeader((mainController.maxNumberOfExistedInvoices), (gui.getDate().parse(gui.getNewInvoiceDateField().getText())), (gui.getNewCustomerName().getText()));
                invoices.add(newRow);
                TablesController.loadInvoicesHeaderTable(gui, invoices);
                GUI.getNewInvoiceDialog().setVisible(false);
                gui.getInvoiceTable().setRowSelectionInterval((invoices.size() - 1), (invoices.size() - 1));
                gui.getNewCustomerName().setText("");
                gui.getNewInvoiceDateField().setText("");
                InvoicesLineController.updater(gui, invoices, invoices.size() - 1);
                TablesController.loadInvoicesLineTable(gui, invoices);
            } catch (ParseException ex) {
                GUI.getNewInvoiceDialog().setModal(false);
                GUI.setJOptionPaneMessagMessage(gui.getInvoicesTablePanel(), "Please Enter A Valid Date", "Invalid Date Entered", "ERROR_MESSAGE");
                GUI.getNewInvoiceDialog().setModal(true);
                showCreateNewInvoiceDialog(gui);
            }

        }
    }

    static void deleteSelectedInvoice(GUI gui, ArrayList<InvoiceHeader> invoices) {
        int DeletedInvoice = gui.getInvoiceTable().getSelectedRow();

        if (DeletedInvoice == -1) {
            GUI.setJOptionPaneMessagMessage(gui.getInvoicesTablePanel(), "Please select invoice row you want to remove ", "Error", "ERROR_MESSAGE");
        } else {
            invoices.remove(DeletedInvoice);
            TablesController.loadInvoicesHeaderTable(gui, invoices);
            if (!invoices.isEmpty()) {
                gui.getInvoiceTable().setRowSelectionInterval((invoices.size() - 1), (invoices.size() - 1));
                InvoicesLineController.updater(gui, invoices, invoices.size() - 1);
                TablesController.loadInvoicesLineTable(gui, invoices);
            } else {
                while (InvoicesLineTableModel.setInvoicesLineTableModel(gui).getRowCount() > 0) {
                    InvoicesLineTableModel.setInvoicesLineTableModel(gui).removeRow(0);
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
