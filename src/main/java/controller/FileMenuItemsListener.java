package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.*;
import view.MyGUI;
public class FileMenuItemsListener implements ActionListener{

    private model.fileOperations fileOperations;
    private InvoiceTableListener invoiceTableListener;
    private MyGUI myGui;

    public FileMenuItemsListener(MyGUI myGui, model.fileOperations fileOperations, InvoiceTableListener invoiceTableListener) {
        this.myGui = myGui;
        this.fileOperations = fileOperations;
        this.invoiceTableListener = invoiceTableListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Load Files"))
        {
            int Headercount =InvoicesHeaderTableModel.setInvoicesHeaderTableModel(myGui).getRowCount();
            for (int i=0;i<Headercount;i++)
            {
                InvoicesHeaderTableModel.setInvoicesHeaderTableModel(myGui).removeRow(0);
            }

            int lineCount =InvoicesHeaderTableModel.setInvoicesHeaderTableModel(myGui).getRowCount();
            for (int i=0;i<lineCount;i++)
            {
                InvoicesHeaderTableModel.setInvoicesHeaderTableModel(myGui).removeRow(0);
            }

            myGui.getInvoiceTotalLabel().setText("");
            fileOperations.getPath();
            if ((model.fileOperations.InvoiceHeaderFile != null) && (model.fileOperations.InvoiceLineFile != null)) {
                mainController.invoices = fileOperations.readFile();
                fileOperations.main(mainController.invoices);
                InvoicesHeaderController.calculateInvoiceTableTotal(mainController.invoices);
                TablesController.loadInvoicesHeaderTable(myGui, mainController.invoices);
                fileOperations.getMaxNumberOfExistedInvoices(mainController.maxNumberOfExistedInvoices, mainController.invoices);
            }
        }else if (e.getActionCommand().equals("Save File"))
        {
            try {
                fileOperations.writeFile(mainController.invoices);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
