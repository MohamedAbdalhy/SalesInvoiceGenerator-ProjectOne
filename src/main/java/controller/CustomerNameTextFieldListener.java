package controller;

import java.awt.event.*;

import view.MyGUI;

public class CustomerNameTextFieldListener implements ActionListener {

    private MyGUI view = null;

    public CustomerNameTextFieldListener(MyGUI view) {
        this.view = view;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!((mainController.invoices.get(mainController.selectedRow).getInoviceCustomerName()).equals((view.getCustomerNameTextField().getText())))) {
            InvoicesLineController.changeCustomerNameTextField(view, mainController.invoices);
        }
    }



}
