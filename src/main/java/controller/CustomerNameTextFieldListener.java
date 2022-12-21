package controller;

import java.awt.event.*;

import view.GUI;

public class CustomerNameTextFieldListener implements ActionListener {

    private GUI view = null;

    public CustomerNameTextFieldListener(GUI view) {
        this.view = view;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!((mainController.invoices.get(mainController.selectedRow).getInoviceCustomerName()).equals((view.getCustomerNameTextField().getText())))) {
            InvoicesLineController.changeCustomerNameTextField(view, mainController.invoices);
        }
    }



}
