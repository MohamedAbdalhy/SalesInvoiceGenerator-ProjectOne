package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.*;
import view.GUI;
import view.GUI;


public class TablesController {

    public static void loadInvoicesHeaderTable(GUI myGui, ArrayList<InvoiceHeader> invoices) {
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Object InvoiceHeaderData[] = new Object[4];
        int count =InvoicesHeaderTableModel.setInvoicesHeaderTableModel(myGui).getRowCount();
        for (int i=0;i<count;i++)
        {
            InvoicesHeaderTableModel.setInvoicesHeaderTableModel(myGui).removeRow(0);
        }

        for (int i = 0; i < invoices.size(); i++) {
            InvoiceHeaderData[0] = invoices.get(i).getInoviceNumber();
            String date = simpleDateFormat.format(invoices.get(i).getInoviceDate());
            InvoiceHeaderData[1] = date;
            InvoiceHeaderData[2] = invoices.get(i).getInoviceCustomerName();
            InvoiceHeaderData[3] = invoices.get(i).getInoviceTotal();
            InvoicesHeaderTableModel.setInvoicesHeaderTableModel(myGui).addRow(InvoiceHeaderData);
        }
    }

    public static void loadInvoicesLineTable(GUI myGui, ArrayList<InvoiceHeader> invoices) {
            float total;
            Object InvoiceLineData[] = new Object[5];
            int selectedRow = myGui.getInvoiceTable().getSelectedRow();
            InvoicesLineTableModel.setInvoicesLineTableModel(myGui).setRowCount(0);

            for (int j = 0; j < invoices.get(selectedRow).getInvoicerow().size(); j++) {
                total = ((invoices.get(selectedRow).getInvoicerow().get(j).getItemPrice()) * (invoices.get(selectedRow).getInvoicerow().get(j).getItemCount()));
                invoices.get(selectedRow).getInvoicerow().get(j).setItemTotal(total);
                InvoiceLineData[0] = invoices.get(selectedRow).getInvoicerow().get(j).getMainInvoice().getInoviceNumber();
                InvoiceLineData[1] = invoices.get(selectedRow).getInvoicerow().get(j).getItemName();
                InvoiceLineData[2] = invoices.get(selectedRow).getInvoicerow().get(j).getItemPrice();
                InvoiceLineData[3] = invoices.get(selectedRow).getInvoicerow().get(j).getItemCount();
                InvoiceLineData[4] = invoices.get(selectedRow).getInvoicerow().get(j).getItemTotal();
                InvoicesLineTableModel.setInvoicesLineTableModel(myGui).addRow(InvoiceLineData);
            }
        }
    }

