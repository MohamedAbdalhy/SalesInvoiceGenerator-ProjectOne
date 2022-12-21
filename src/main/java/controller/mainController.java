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

public class mainController implements ActionListener, KeyListener {

    private InvoiceHeader invoiceHeader;
    private InvoiceLine invoiceLine;
    private GUI gui;
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

    public mainController(InvoiceHeader invoiceHeader, InvoiceLine invoiceLine, GUI gui) {
        this.invoiceHeader = invoiceHeader;
        this.invoiceLine = invoiceLine;
        this.gui = gui;
        fileOperations = new fileOperations(this.gui);

        invoicesLineTableListener = new InvoicesLineTableListener(gui);
        invoiceTableListener = new InvoiceTableListener(gui, fileOperations, invoicesLineTableListener);

        fileMenuItemsListener = new FileMenuItemsListener(gui, fileOperations, invoiceTableListener);


        //invoiceDateTextFieldListener = new InvoiceDateTextFieldListener(gui);
        customerNameTextFieldListener = new CustomerNameTextFieldListener(gui);

        turnOnAllActionListerners(gui);

        loadTablesContents = new TablesController();
        
    }

    private void turnOnAllActionListerners(GUI gui) {
        gui.getLoadFile().addActionListener(fileMenuItemsListener);
        gui.getLoadFile().setActionCommand("Load Files");

        gui.getSaveFile().addActionListener(fileMenuItemsListener);
        gui.getSaveFile().setActionCommand("Save File");

        gui.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);



        gui.getCustomerNameTextField().addActionListener(customerNameTextFieldListener);

        gui.getCreateNewInvoiceButton().addActionListener(this);
        gui.getCreateNewInvoiceButton().setActionCommand("Create New Invoice");

        gui.getCreateNewInvoiceOK().addActionListener(this);
        gui.getCreateNewInvoiceOK().setActionCommand("Confirm creating new invoice");

        gui.getCreateNewInvoiceCancel().addActionListener(this);
        gui.getCreateNewInvoiceCancel().setActionCommand("Discard creating new invoice");

        gui.getDeleteInvoiceButton().addActionListener(this);
        gui.getDeleteInvoiceButton().setActionCommand("Delete Invoice");

        gui.getAddItemButton().addActionListener(this);
        gui.getAddItemButton().setActionCommand("Add Item");

        gui.getNewItemPrice().addKeyListener(this);

        gui.getAddItemDialogOK().addActionListener(this);
        gui.getAddItemDialogOK().setActionCommand("Confirm adding an item");
        
        gui.getAddItemDialogCancel().addActionListener(this);
        gui.getAddItemDialogCancel().setActionCommand("Discard adding an item");



        gui.getDeleteItemButton().addActionListener(this);
        gui.getDeleteItemButton().setActionCommand("Delete Item");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Create New Invoice")) {
            if (gui.getFocusOwner() != null) {
                InvoicesHeaderController.showCreateNewInvoiceDialog(gui);
            }
        }

        if (e.getActionCommand().equals("Confirm creating new invoice")) {
                gui.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
                InvoicesHeaderController.addNewInvoice(gui, invoices);
                gui.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);
            }


        if (e.getActionCommand().equals("Discard creating new invoice")) {
            //clear name and date text field
            //hide new invoice dialog box
                gui.getNewCustomerName().setText("");
                gui.getNewInvoiceDateField().setText("");
                GUI.getNewInvoiceDialog().setVisible(false);
            }

        if (e.getActionCommand().equals( "Delete Invoice")) {
                if (gui.getFocusOwner() != null) {
                    gui.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
                    InvoicesHeaderController.deleteSelectedInvoice(gui, invoices);
                    gui.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);
                }
            }

        if (e.getActionCommand().equals( "Add Item")) {
                if (gui.getFocusOwner() != null) {
                    InvoicesLineController.showNewItemDialog(gui);
                }
            }

        if (e.getActionCommand().equals( "Confirm adding an item")) {
                InvoicesLineController.addNewItem(gui, invoices);
                InvoicesHeaderController.calculateInvoiceTableTotal(invoices);
                InvoicesHeaderController.updateTotalPriceCoulmn(gui, invoices);
                InvoicesLineController.updater(gui, invoices, selectedRow);
                TablesController.loadInvoicesLineTable(gui, invoices);

                int sizeOfinvoicesLinesForTheSelectedInvoice = invoices.get(gui.getInvoiceTable().getSelectedRow()).getInvoicerow().size();
                gui.getInvoicesLineTable().setRowSelectionInterval((sizeOfinvoicesLinesForTheSelectedInvoice - 1), (sizeOfinvoicesLinesForTheSelectedInvoice - 1));
                GUI.getAddItemDialog().setVisible(false);
            }

        if (e.getActionCommand().equals( "Discard adding an item")) {
                gui.getNewItemName().setText("");
                gui.getNewItemPrice().setText("");
                gui.getNewItemPriceSpinner().setValue((Object) 1);
                GUI.getAddItemDialog().setVisible(false);
            }

        if (e.getActionCommand().equals( "Delete Item")) {
                if (gui.getFocusOwner() != null) {
                    InvoicesLineController.deleteItem(gui, invoices);
                    InvoicesHeaderController.calculateInvoiceTableTotal(invoices);
                    InvoicesHeaderController.updateTotalPriceCoulmn(gui, invoices);
                    InvoicesLineController.updater(gui, invoices, selectedRow);
                    TablesController.loadInvoicesLineTable(gui, invoices);

                    int sizeOfinvoicesLinesForTheSelectedInvoice = invoices.get(gui.getInvoiceTable().getSelectedRow()).getInvoicerow().size();

                    if (sizeOfinvoicesLinesForTheSelectedInvoice > 0) {
                        gui.getInvoicesLineTable().setRowSelectionInterval((sizeOfinvoicesLinesForTheSelectedInvoice - 1), (sizeOfinvoicesLinesForTheSelectedInvoice - 1));
                    }
                }
            }

        if (e.getActionCommand().equals( "Cancel Any Changes")){
                if ((model.fileOperations.InvoiceHeaderFile != null) && (model.fileOperations.InvoiceLineFile != null)) {
                    gui.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
                    invoices = fileOperations.readFile();
                    InvoicesHeaderController.calculateInvoiceTableTotal(invoices);
                    TablesController.loadInvoicesHeaderTable(gui, invoices);

                    maxNumberOfExistedInvoices = 0;
                    fileOperations.getMaxNumberOfExistedInvoices(maxNumberOfExistedInvoices, invoices);
                    gui.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);

                    if (invoices.size() >= 1) {
                        gui.getInvoiceTable().setRowSelectionInterval(0, 0);
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
            Float.parseFloat(gui.getNewItemPrice().getText() + price);
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
