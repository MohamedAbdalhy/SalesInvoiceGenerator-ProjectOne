package controller;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import view.MyGUI;


public class InvoiceTableListener implements ListSelectionListener {

    private model.fileOperations fileOperations;
    private MyGUI view = null;
    private InvoicesLineTableListener invoicesLineTableListener;

    public InvoiceTableListener(MyGUI view, model.fileOperations fileOperations, InvoicesLineTableListener invoicesLineTableListener) {
        this.view = view;
        this.fileOperations = fileOperations;
        this.invoicesLineTableListener = invoicesLineTableListener;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (mainController.invoices.size() >= 1) {
            if (!e.getValueIsAdjusting()) {
                mainController.selectedRow = view.getInvoiceTable().getSelectedRow();
                TablesController.loadInvoicesLineTable(view, mainController.invoices);
                InvoicesLineController.updater(view, mainController.invoices, mainController.selectedRow);

            }
        }
    }
}
