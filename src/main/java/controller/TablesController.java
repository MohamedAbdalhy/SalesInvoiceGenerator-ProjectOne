package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.*;
import view.GUI;


public class TablesController {

    public static void loadInvoicesHeaderTable(GUI gui, ArrayList<InvoiceHeader> invoices) {
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Object InvoiceHeaderData[] = new Object[4];
        //clear table
        int count =InvoicesHeaderTableModel.setInvoicesHeaderTableModel(gui).getRowCount();
        for (int i=0;i<count;i++)
        {
            InvoicesHeaderTableModel.setInvoicesHeaderTableModel(gui).removeRow(0);
        }

        //load invoice header row
        for (int i = 0; i < invoices.size(); i++) {
            InvoiceHeaderData[0] = invoices.get(i).getInoviceNumber();
            String date = simpleDateFormat.format(invoices.get(i).getInoviceDate());
            InvoiceHeaderData[1] = date;
            InvoiceHeaderData[2] = invoices.get(i).getInoviceCustomerName();
            InvoiceHeaderData[3] = invoices.get(i).getInoviceTotal();
            //adding row
            InvoicesHeaderTableModel.setInvoicesHeaderTableModel(gui).addRow(InvoiceHeaderData);
        }
    }

    public static void loadInvoicesLineTable(GUI gui, ArrayList<InvoiceHeader> invoices) {
            float total;
            Object InvoiceLineData[] = new Object[5];
            int selectedRow = gui.getInvoiceTable().getSelectedRow();
            InvoicesLineTableModel.setInvoicesLineTableModel(gui).setRowCount(0);//for updating items table
        //load invoice items
            for (int j = 0; j < invoices.get(selectedRow).getInvoicerow().size(); j++) {
                total = ((invoices.get(selectedRow).getInvoicerow().get(j).getItemPrice()) * (invoices.get(selectedRow).getInvoicerow().get(j).getItemCount()));
                invoices.get(selectedRow).getInvoicerow().get(j).setItemTotal(total);
                InvoiceLineData[0] = invoices.get(selectedRow).getInvoicerow().get(j).getMainInvoice().getInoviceNumber();
                InvoiceLineData[1] = invoices.get(selectedRow).getInvoicerow().get(j).getItemName();
                InvoiceLineData[2] = invoices.get(selectedRow).getInvoicerow().get(j).getItemPrice();
                InvoiceLineData[3] = invoices.get(selectedRow).getInvoicerow().get(j).getItemCount();
                InvoiceLineData[4] = invoices.get(selectedRow).getInvoicerow().get(j).getItemTotal();
                //adding row
                InvoicesLineTableModel.setInvoicesLineTableModel(gui).addRow(InvoiceLineData);
            }
        }
    }
//}
