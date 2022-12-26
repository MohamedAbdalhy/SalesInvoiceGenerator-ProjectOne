package controller;

import java.util.ArrayList;

import model.*;
import view.GUI;
import view.GUI;

public class InvoicesLineController {

    public static void updater(GUI myGui, ArrayList<InvoiceHeader> invoices, int selectedRow) {
        if (selectedRow != -1) {
            myGui.getInvoiceNumberLabel().setText(Integer.toString(invoices.get(selectedRow).getInoviceNumber()));
            myGui.getInvoiceDateTextField().setText(myGui.getDate().format(invoices.get(selectedRow).getInoviceDate()));
            myGui.getCustomerNameTextField().setText(invoices.get(selectedRow).getInoviceCustomerName());
            myGui.getInvoiceTotalLabel().setText(Float.toString(invoices.get(selectedRow).getInoviceTotal()));
        }
    }

//    public static void dateValidator(GUI gui, ArrayList<InvoiceHeader> invoices) {
//        int choice = gui.showYesNoCancelDialog(gui.getInvoicesItemsPanel(), "Do you want to save date changes?", "Confirmation");
//        switch (choice) {
//            case 0 -> {
//                try {
//                    if (!gui.getInvoiceDateTextField().getText().matches("^\\d{2}\\-\\d{2}\\-\\d{4}")) {
//                        throw new Exception();
//                    }
//
//                    fileOperations.date.setLenient(false);
//                    fileOperations.date.parse(gui.getInvoiceDateTextField().getText());
//                    invoices.get(gui.getInvoiceTable().getSelectedRow()).setInoviceDate(gui.getDate().parse(gui.getInvoiceDateTextField().getText()));
//                    InvoicesHeaderController.updateDateCoulmn(gui, invoices);
//                    gui.getInvoiceDateTextField().requestFocus();
//                } catch (Exception ex) {
//                    GUI.setJOptionPaneMessagMessage(gui.getInvoicesItemsPanel(), "Please enter a valid date (e.g 06-06-2022)", "wrong date", "ERROR_MESSAGE");
//                    gui.getInvoiceDateTextField().setText(gui.getDate().format(invoices.get(gui.getInvoiceTable().getSelectedRow()).getInoviceDate()));
//                    gui.getInvoiceDateTextField().requestFocus();
//                }
//            }
//            case 1 ->
//                gui.getInvoiceDateTextField().requestFocus();
//            default -> {
//                gui.getInvoiceDateTextField().setText(gui.getDate().format(invoices.get(mainController.selectedRow).getInoviceDate()));
//                gui.getInvoiceDateTextField().requestFocus();
//            }
//        }
//    }

    public static void changeCustomerNameTextField(GUI myGui, ArrayList<InvoiceHeader> invoices) {
        int choice = myGui.showYesNoCancelDialog(myGui.getInvoicesItemsPanel(), "Do you want to save new customer name?", "Confirmation");
        switch (choice) {
            case 0 -> {
                invoices.get(myGui.getInvoiceTable().getSelectedRow()).setInoviceCustomerName(myGui.getCustomerNameTextField().getText());
                InvoicesHeaderController.updateCustomerNameCoulmn(myGui, invoices);
                myGui.getCustomerNameTextField().requestFocus();
            }
            case 1 ->
                myGui.getCustomerNameTextField().requestFocus();
            default -> {
                myGui.getCustomerNameTextField().setText(invoices.get(mainController.selectedRow).getInoviceCustomerName());
                myGui.getCustomerNameTextField().requestFocus();
            }
        }
    }

    static void addNewItem(GUI myGui, ArrayList<InvoiceHeader> invoices) {
        String itemName;
        float price = 0;
        int count = 0;

        boolean flag = false;
        itemName = myGui.getNewItemName().getText();

        if (itemName.equalsIgnoreCase("")) {
            GUI.getAddItemDialog().setVisible(false);
            GUI.setJOptionPaneMessagMessage(myGui.getInvoicesItemsPanel(), "Please enter a valid name", "Empty Item Name", "ERROR_MESSAGE");
            showNewItemDialog(myGui);
        } else if (myGui.getNewItemPrice().getText().equalsIgnoreCase("")) {
            GUI.getAddItemDialog().setVisible(false);
            GUI.setJOptionPaneMessagMessage(myGui.getInvoicesItemsPanel(), "Please enter a price", "Empty Price", "ERROR_MESSAGE");
            showNewItemDialog(myGui);
        } else {
            
            try {
                price = Float.parseFloat(myGui.getNewItemPrice().getText());
                myGui.getNewItemPriceSpinner().commitEdit();
                count = (Integer) myGui.getNewItemPriceSpinner().getValue();
            } catch (Exception e) {
                flag = true;
                e.printStackTrace();
            }

            if (!flag) {
                InvoiceHeader temp = invoices.get(myGui.getInvoiceTable().getSelectedRow());
                InvoiceLine newItem = new InvoiceLine(itemName, price, count, temp);
                temp.getInvoicerow().add(newItem);
            }

            myGui.getNewItemName().setText("");
            myGui.getNewItemPrice().setText("");
            myGui.getNewItemPriceSpinner().setValue((Object) 1);
        }
    }

    static void showNewItemDialog(GUI myGui) {
        myGui.setLocations();
        GUI.getAddItemDialog().setTitle("Add new item to invoice " + myGui.getInvoiceNumberLabel().getText());
        GUI.getAddItemDialog().setVisible(true);
    }

    static void deleteItem(GUI myGui, ArrayList<InvoiceHeader> invoices) {
        if (myGui.getInvoicesLineTable().getSelectedRow() >= 0) {
            int rowToBeDeleted;
            rowToBeDeleted = myGui.getInvoicesLineTable().getSelectedRow();
            invoices.get(myGui.getInvoiceTable().getSelectedRow()).getInvoicerow().remove(rowToBeDeleted);
        }
    }
}
