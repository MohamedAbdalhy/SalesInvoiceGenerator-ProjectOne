package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.fileOperations;
import model.InvoiceHeader;
import model.InvoiceLine;
import view.GUI;
import view.GUI;

public class mainController implements ActionListener, KeyListener {

    private InvoiceHeader invoiceHeader;
    private InvoiceLine invoiceLine;
    private GUI myGui;
    private model.fileOperations fileOperations;
    private TablesController loadTablesContents;

    public volatile static ArrayList<InvoiceHeader> invoices = new ArrayList<>();
    public volatile static int selectedRow = 0;
    public volatile static int maxNumberOfExistedInvoices = 0;
    private InvoiceTableListener invoiceTableListener;
    private InvoicesLineTableListener invoicesLineTableListener;
    private FileMenuItemsListener fileMenuItemsListener;
    //private InvoiceDateTextFieldListener invoiceDateTextFieldListener;
    private CustomerNameTextFieldListener customerNameTextFieldListener;

    public mainController(InvoiceHeader invoiceHeader, InvoiceLine invoiceLine, GUI myGui) {
        this.invoiceHeader = invoiceHeader;
        this.invoiceLine = invoiceLine;
        this.myGui = myGui;
        fileOperations = new fileOperations(this.myGui);

        invoicesLineTableListener = new InvoicesLineTableListener(myGui);
        invoiceTableListener = new InvoiceTableListener(myGui, fileOperations, invoicesLineTableListener);

        fileMenuItemsListener = new FileMenuItemsListener(myGui, fileOperations, invoiceTableListener);


        //invoiceDateTextFieldListener = new InvoiceDateTextFieldListener(gui);
        customerNameTextFieldListener = new CustomerNameTextFieldListener(myGui);

        turnOnAllActionListerners(myGui);

        loadTablesContents = new TablesController();
        
    }

    private void turnOnAllActionListerners(GUI myGui) {
        myGui.getLoadFile().addActionListener(fileMenuItemsListener);
        myGui.getLoadFile().setActionCommand("Load Files");

        myGui.getSaveFile().addActionListener(fileMenuItemsListener);
        myGui.getSaveFile().setActionCommand("Save File");

        myGui.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);



        myGui.getCustomerNameTextField().addActionListener(customerNameTextFieldListener);

        myGui.getCreateNewInvoiceButton().addActionListener(this);
        myGui.getCreateNewInvoiceButton().setActionCommand("Create New Invoice");

        myGui.getCreateNewInvoiceOK().addActionListener(this);
        myGui.getCreateNewInvoiceOK().setActionCommand("Confirm creating new invoice");

        myGui.getCreateNewInvoiceCancel().addActionListener(this);
        myGui.getCreateNewInvoiceCancel().setActionCommand("Discard creating new invoice");

        myGui.getDeleteInvoiceButton().addActionListener(this);
        myGui.getDeleteInvoiceButton().setActionCommand("Delete Invoice");

        myGui.getAddItemButton().addActionListener(this);
        myGui.getAddItemButton().setActionCommand("Add Item");

        myGui.getNewItemPrice().addKeyListener(this);

        myGui.getAddItemDialogOK().addActionListener(this);
        myGui.getAddItemDialogOK().setActionCommand("Confirm adding an item");
        
        myGui.getAddItemDialogCancel().addActionListener(this);
        myGui.getAddItemDialogCancel().setActionCommand("Discard adding an item");



        myGui.getDeleteItemButton().addActionListener(this);
        myGui.getDeleteItemButton().setActionCommand("Delete Item");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Create New Invoice")) {
            if (myGui.getFocusOwner() != null) {
                InvoicesHeaderController.showCreateNewInvoiceDialog(myGui);
            }
        }

        if (e.getActionCommand().equals("Confirm creating new invoice")) {
                myGui.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
                InvoicesHeaderController.addNewInvoice(myGui, invoices);
                myGui.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);
            }


        if (e.getActionCommand().equals("Discard creating new invoice")) {
            //clear name and date text field
            //hide new invoice dialog box
                myGui.getNewCustomerName().setText("");
                myGui.getNewInvoiceDateField().setText("");
                GUI.getNewInvoiceDialog().setVisible(false);
            }

        if (e.getActionCommand().equals( "Delete Invoice")) {
                if (myGui.getFocusOwner() != null) {
                    myGui.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
                    InvoicesHeaderController.deleteSelectedInvoice(myGui, invoices);
                    myGui.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);
                }
            }

        if (e.getActionCommand().equals( "Add Item")) {
                if (myGui.getFocusOwner() != null) {
                    InvoicesLineController.showNewItemDialog(myGui);
                }
            }

        if (e.getActionCommand().equals( "Confirm adding an item")) {
                InvoicesLineController.addNewItem(myGui, invoices);
                InvoicesHeaderController.calculateInvoiceTableTotal(invoices);
                InvoicesHeaderController.updateTotalPriceCoulmn(myGui, invoices);
                InvoicesLineController.updater(myGui, invoices, selectedRow);
                TablesController.loadInvoicesLineTable(myGui, invoices);

                int sizeOfinvoicesLinesForTheSelectedInvoice = invoices.get(myGui.getInvoiceTable().getSelectedRow()).getInvoicerow().size();
                myGui.getInvoicesLineTable().setRowSelectionInterval((sizeOfinvoicesLinesForTheSelectedInvoice - 1), (sizeOfinvoicesLinesForTheSelectedInvoice - 1));
                GUI.getAddItemDialog().setVisible(false);
            }

        if (e.getActionCommand().equals( "Discard adding an item")) {
                myGui.getNewItemName().setText("");
                myGui.getNewItemPrice().setText("");
                myGui.getNewItemPriceSpinner().setValue((Object) 1);
               GUI.getAddItemDialog().setVisible(false);
            }

        if (e.getActionCommand().equals( "Delete Item")) {
                if (myGui.getFocusOwner() != null) {
                    InvoicesLineController.deleteItem(myGui, invoices);
                    InvoicesHeaderController.calculateInvoiceTableTotal(invoices);
                    InvoicesHeaderController.updateTotalPriceCoulmn(myGui, invoices);
                    InvoicesLineController.updater(myGui, invoices, selectedRow);
                    TablesController.loadInvoicesLineTable(myGui, invoices);

                    int sizeOfinvoicesLinesForTheSelectedInvoice = invoices.get(myGui.getInvoiceTable().getSelectedRow()).getInvoicerow().size();

                    if (sizeOfinvoicesLinesForTheSelectedInvoice > 0) {
                        myGui.getInvoicesLineTable().setRowSelectionInterval((sizeOfinvoicesLinesForTheSelectedInvoice - 1), (sizeOfinvoicesLinesForTheSelectedInvoice - 1));
                    }
                }
            }

        if (e.getActionCommand().equals( "Cancel Any Changes")){
                if ((model.fileOperations.InvoiceHeaderFile != null) && (model.fileOperations.InvoiceLineFile != null)) {
                    myGui.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
                    invoices = fileOperations.readFile();
                    InvoicesHeaderController.calculateInvoiceTableTotal(invoices);
                    TablesController.loadInvoicesHeaderTable(myGui, invoices);

                    maxNumberOfExistedInvoices = 0;
                    fileOperations.getMaxNumberOfExistedInvoices(maxNumberOfExistedInvoices, invoices);
                    myGui.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);

                    if (invoices.size() >= 1) {
                        myGui.getInvoiceTable().setRowSelectionInterval(0, 0);
                    }
                }
            }
            
//            case "Save Items" -> {
//                 try {
//                    fileOperations.writeFile(Controller.invoices);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
        }



    @Override
    public void keyTyped(KeyEvent evnt) {
        char price = evnt.getKeyChar();
        if (Character.isLetter(price) && !evnt.isAltDown() && evnt.isShiftDown() && evnt.isControlDown()) {
            evnt.consume();
        }
        
        if ((price == 'f') || (price == 'd')) {
            evnt.consume();
        }
        
        try {
            Float.parseFloat(myGui.getNewItemPrice().getText() + price);
        } catch (NumberFormatException e) {
            evnt.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
