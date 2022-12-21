package view;


import java.awt.*;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.fileOperations;
import model.InvoiceHeader;
import model.InvoicesHeaderTableModel;
import model.InvoicesLineTableModel;
import controller.*;

public class GUI extends JFrame {

    JFileChooser chooser;
    JOptionPane jOptionPaneMessage;
    private SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
    private model.fileOperations fileOperations = new fileOperations(this);

  //creating components
    private JButton AddItemButton;
    private static JDialog AddItemDialog;
    private JButton AddItemDialogCancel;
    public JButton AddItemDialogOK;
    private JButton CreateNewInvoiceButton;
    private JButton CreateNewInvoiceCancel;
    private static JDialog CreateNewInvoiceDialog;
    public JButton CreateNewInvoiceOK;
    private JLabel CustomerNameLabel;
    private JTextField CustomerNameTextField;
    private JButton DeleteInvoiceButton;
    private JButton DeleteItemButton;
    private JMenu FileMenu;
    private JLabel InvoiceDateLabel;
    private JTextField InvoiceDateTextField;
    private JLabel InvoiceNumberLabel;
    private JLabel InvoiceNumberStaticLabel;
    private JTable InvoiceTable;
    private JScrollPane InvoiceTableScrollPane;
    private JLabel InvoiceTotalLabel;
    private JLabel InvoiceTotalStaticLabel;
    private JLabel InvoicesItemsLabel;
    private JPanel InvoicesItemsPanel;
    private JTable InvoicesLineTable;
    private JScrollPane InvoicesLineTableScrollPane;
    private JLabel InvoicesTableLabel;
    private JPanel InvoicesTablePanel;
    private JMenuItem LoadFile;
    private JMenuBar MenuBar;
    private JTextField NewCustomerName;
    private JLabel NewCustomerNameLabel;
    private JTextField NewInvoiceDateField;
    private JLabel NewInvoiceDateLabel;
    private JLabel NewItemCountLabel;
    private JTextField NewItemName;
    private JLabel NewItemNameLabel;
    private JTextField NewItemPrice;
    private JLabel NewItemPriceLabel;
    private JSpinner NewItemPriceSpinner;
    private JMenuItem SaveFile;
    private JPopupMenu.Separator jSeparator1;

   //setters and getters
    public JMenuItem getLoadFile() {
        return LoadFile;
    }

    public JTable getInvoiceTable() {
        return InvoiceTable;
    }

    public JTable getInvoicesLineTable() {
        return InvoicesLineTable;
    }

    public JButton getAddItemButton() {
        return AddItemButton;
    }

    public JTextField getCustomerNameTextField() {
        return CustomerNameTextField;
    }

    public JButton getDeleteItemButton() {
        return DeleteItemButton;
    }

    public JTextField getInvoiceDateTextField() {
        return InvoiceDateTextField;
    }

    public JLabel getInvoiceTotalLabel() {
        return InvoiceTotalLabel;
    }

    public JMenuItem getSaveFile() {
        return SaveFile;
    }

    public JButton getCreateNewInvoiceButton() {
        return CreateNewInvoiceButton;
    }

    public JButton getDeleteInvoiceButton() {
        return DeleteInvoiceButton;
    }

    public JLabel getInvoiceNumberLabel() {
        return InvoiceNumberLabel;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public JPanel getInvoicesItemsPanel() {
        return InvoicesItemsPanel;
    }

    public JPanel getInvoicesTablePanel() {
        return InvoicesTablePanel;
    }

    public JTextField getNewInvoiceDateField() {
        return NewInvoiceDateField;
    }

    public static JDialog getNewInvoiceDialog() {
        return CreateNewInvoiceDialog;
    }

    public static JDialog getAddItemDialog() {
        return AddItemDialog;
    }

    public JButton getAddItemDialogCancel() {
        return AddItemDialogCancel;
    }

    public JButton getCreateNewInvoiceOK() {
        return CreateNewInvoiceOK;
    }

    public JTextField getNewCustomerName() {
        return NewCustomerName;
    }

    public JButton getCreateNewInvoiceCancel() {
        return CreateNewInvoiceCancel;
    }

    public JButton getAddItemDialogOK() {
        return AddItemDialogOK;
    }

    public JSpinner getNewItemPriceSpinner() {
        return NewItemPriceSpinner;
    }
    public JTextField getNewItemName() {
        return NewItemName;
    }
    public JTextField getNewItemPrice() {
        return NewItemPrice;
    }
    public static void setJOptionPaneMessagMessage(Component component, String message, String title, String messageType) {
        int messageTypeInteger = 0;
        switch (messageType) {
            case "ERROR_MESSAGE" ->
                messageTypeInteger = 0;
            case "QUESTION_MESSAGE" ->
                messageTypeInteger = 3;
            case "WARNING_MESSAGE" ->
                messageTypeInteger = 2;
            case "INFORMATION_MESSAGE" ->
                messageTypeInteger = 1;
        }
        JOptionPane.showMessageDialog(component, message, title, messageTypeInteger);
    }
    public int showYesNoCancelDialog(Component component, String message, String title) {
        Object[] yesNoCancel = {"Yes", "No", "Cancel"};
        return JOptionPane.showOptionDialog(component, message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, yesNoCancel, yesNoCancel[0]);
    }
    public void setLocations() {
        CreateNewInvoiceDialog.setLocation(InvoiceTable.getLocationOnScreen().x + 50, InvoiceTable.getLocationOnScreen().y + 50);
        AddItemDialog.setLocation(InvoicesLineTable.getLocationOnScreen().x + 100, InvoicesLineTable.getLocationOnScreen().y);
    }

    public void loadFiles() throws FileNotFoundException {
        while (InvoicesHeaderTableModel.setInvoicesHeaderTableModel(this).getRowCount() > 0) {
            InvoicesHeaderTableModel.setInvoicesHeaderTableModel(this).removeRow(0);
        }

        while (InvoicesLineTableModel.setInvoicesLineTableModel(this).getRowCount() > 0) {
            InvoicesLineTableModel.setInvoicesLineTableModel(this).removeRow(0);
        }

        getInvoiceTotalLabel().setText("");
        ArrayList<InvoiceHeader> invoices = new ArrayList<>();
        mainController.invoices = fileOperations.readFile();
        fileOperations.main(mainController.invoices);
        InvoicesHeaderController.calculateInvoiceTableTotal(mainController.invoices);
        TablesController.loadInvoicesHeaderTable(this, mainController.invoices);
        fileOperations.getMaxNumberOfExistedInvoices(mainController.maxNumberOfExistedInvoices, mainController.invoices);

    }
    public GUI() {
        initComponents();

        chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
        chooser.setFileFilter(filter);
        jOptionPaneMessage = new JOptionPane();
        setNames();

    }
    private void frameGui(){
        setTitle("Sales Invoice Generator");
        setLocation(new java.awt.Point(0, 0));
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(1200, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
    }
    private void fileMenuGUI(){
        FileMenu.setText("File");

        LoadFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        LoadFile.setText("Load");

        FileMenu.add(LoadFile);

        SaveFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        SaveFile.setText("Save");
        SaveFile.setEnabled(true);

        FileMenu.add(SaveFile);


        MenuBar.add(FileMenu);

        setJMenuBar(MenuBar);
    }
    private void setNames(){
        CustomerNameTextField.setName("CustomerNameTextField");
        InvoiceDateTextField.setName("InvoiceDateTextField");
        InvoicesLineTable.setName("InvoicesLineTable");
        InvoiceTable.setName("InvoiceTable");

    }
    private void invoiceDialogGUI(){
        CreateNewInvoiceDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        CreateNewInvoiceDialog.setTitle("Create New Invoice");
        CreateNewInvoiceDialog.setBounds(new java.awt.Rectangle(100, 100, 100, 100));
        CreateNewInvoiceDialog.setMinimumSize(new java.awt.Dimension(400, 200));
        CreateNewInvoiceDialog.setModal(true);
        CreateNewInvoiceDialog.setSize(new java.awt.Dimension(100, 100));
    }
    private void invoiceHeaderGUI(){
        NewCustomerNameLabel.setText("Customer Name:");
        NewInvoiceDateField.setEditable(true);
        NewInvoiceDateLabel.setText("Invoice Date:");
        CreateNewInvoiceOK.setText("OK");
        CreateNewInvoiceCancel.setText("Cancel");
        CreateNewInvoiceButton.setText("Create New Invoice");
        InvoicesTablePanel.add(CreateNewInvoiceButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 570, -1, -1));
        DeleteInvoiceButton.setText("Delete Invoice");
        InvoicesTablePanel.add(DeleteInvoiceButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 570, -1, -1));
        DeleteInvoiceButton.setFont(new Font("Arial", Font.BOLD, 12));

        InvoiceTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "No.", "Date", "Customer", "Total"
                }
        ));
        InvoiceTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        InvoiceTableScrollPane.setViewportView(InvoiceTable);
        if (InvoiceTable.getColumnModel().getColumnCount() > 0) {
            InvoiceTable.getColumnModel().getColumn(0).setResizable(false);
        }
        InvoiceTable.getAccessibleContext().setAccessibleName("InvoiceTable");

        InvoicesTablePanel.add(InvoiceTableScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 69, 540, 480));

        getContentPane().add(InvoicesTablePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 650));

        InvoicesItemsPanel.setPreferredSize(new java.awt.Dimension(521, 472));
        InvoicesItemsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        InvoiceNumberStaticLabel.setText("Invoice Number");
        InvoicesItemsPanel.add(InvoiceNumberStaticLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));
        InvoiceNumberStaticLabel.setFont(new Font("Arial", Font.BOLD, 12));
        InvoicesItemsPanel.add(InvoiceNumberLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 6, -1, -1));
        InvoiceNumberLabel.setFont(new Font("Arial", Font.BOLD, 12));

        InvoiceDateLabel.setText("Invoice Date");
        InvoicesItemsPanel.add(InvoiceDateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 44, -1, -1));
        InvoiceDateLabel.setFont(new Font("Arial", Font.BOLD, 12));

        CustomerNameLabel.setText("Customer Name");
        InvoicesItemsPanel.add(CustomerNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 85, -1, -1));
        CustomerNameLabel.setFont(new Font("Arial", Font.BOLD, 12));

        InvoiceTotalStaticLabel.setText("Invoice Total");
        InvoicesItemsPanel.add(InvoiceTotalStaticLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 123, -1, -1));
        InvoiceTotalStaticLabel.setFont(new Font("Arial", Font.BOLD, 12));
    }
    private void itemInvoiceGui(){
        AddItemDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        AddItemDialog.setTitle("Add New Item");
        AddItemDialog.setAlwaysOnTop(false);
        AddItemDialog.setBounds(new java.awt.Rectangle(100, 100, 100, 100));
        AddItemDialog.setLocation(new java.awt.Point(600, 250));
        AddItemDialog.setMinimumSize(new java.awt.Dimension(400, 200));
        AddItemDialog.setModal(true);
        AddItemDialog.setSize(new java.awt.Dimension(100, 100));

        NewItemNameLabel.setText("Item Name:");

        NewItemPriceLabel.setText("Item Price:");

        AddItemDialogOK.setText("OK");

        AddItemDialogCancel.setText("Cancel");

        NewItemPriceSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 50, 1));

        NewItemCountLabel.setText("Item Count:");
        InvoicesItemsLabel.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        InvoicesItemsLabel.setText("Invoice Items");
        InvoicesItemsPanel.add(InvoicesItemsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 180, -1, -1));
        InvoicesItemsLabel.setFont(new Font("Arial", Font.BOLD, 12));

        InvoiceDateTextField.setEditable(true);
        InvoicesItemsPanel.add(InvoiceDateTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 41, 390, -1));
        InvoiceDateTextField.setFont(new Font("Arial", Font.BOLD, 12));

        CustomerNameTextField.setEditable(true);
        InvoicesItemsPanel.add(CustomerNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 82, 390, -1));
        CustomerNameTextField.setFont(new Font("Arial", Font.BOLD, 12));
        InvoicesItemsPanel.add(InvoiceTotalLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 123, -1, -1));
        InvoiceTotalLabel.setFont(new Font("Arial", Font.BOLD, 12));

        InvoicesLineTableScrollPane.setMinimumSize(new java.awt.Dimension(100, 100));

        InvoicesLineTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "No.", "Item Name", "Item Price", "Count", "Item Total"
                }
        ));
        InvoicesLineTable.setFocusTraversalPolicyProvider(true);
        InvoicesLineTable.setMinimumSize(new java.awt.Dimension(100, 100));
        InvoicesLineTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        InvoicesLineTableScrollPane.setViewportView(InvoicesLineTable);
        InvoicesLineTable.getAccessibleContext().setAccessibleName("InvoicesLineTable");

        InvoicesItemsPanel.add(InvoicesLineTableScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 203, 590, 350));

        DeleteItemButton.setText("Delete Item");

        InvoicesItemsPanel.add(DeleteItemButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 570, 150, -1));
        DeleteItemButton.setFont(new Font("Arial", Font.BOLD, 12));

        AddItemButton.setText("Add Item");

        InvoicesItemsPanel.add(AddItemButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 570, -1, -1));
        AddItemButton.setFont(new Font("Arial", Font.BOLD, 12));

        getContentPane().add(InvoicesItemsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, 620, 620));

    }
    private void invoiceHeaderComponentLayout(){
        javax.swing.GroupLayout CreateNewInvoiceDialogLayout = new javax.swing.GroupLayout(CreateNewInvoiceDialog.getContentPane());
        CreateNewInvoiceDialog.getContentPane().setLayout(CreateNewInvoiceDialogLayout);
        CreateNewInvoiceDialogLayout.setHorizontalGroup(
                CreateNewInvoiceDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(CreateNewInvoiceDialogLayout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(CreateNewInvoiceOK, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                                .addComponent(CreateNewInvoiceCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(78, 78, 78))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CreateNewInvoiceDialogLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(CreateNewInvoiceDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(NewCustomerNameLabel)
                                        .addComponent(NewInvoiceDateLabel))
                                .addGap(35, 35, 35)
                                .addGroup(CreateNewInvoiceDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(NewInvoiceDateField, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                                        .addComponent(NewCustomerName))
                                .addGap(45, 45, 45))
        );
        CreateNewInvoiceDialogLayout.setVerticalGroup(
                CreateNewInvoiceDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(CreateNewInvoiceDialogLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(CreateNewInvoiceDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(NewCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(NewCustomerNameLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CreateNewInvoiceDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(NewInvoiceDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(NewInvoiceDateLabel))
                                .addGap(26, 26, 26)
                                .addGroup(CreateNewInvoiceDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(CreateNewInvoiceOK)
                                        .addComponent(CreateNewInvoiceCancel))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
    private void itemComponentLayout(){
        javax.swing.GroupLayout AddItemDialogLayout = new javax.swing.GroupLayout(AddItemDialog.getContentPane());
        AddItemDialog.getContentPane().setLayout(AddItemDialogLayout);
        AddItemDialogLayout.setHorizontalGroup(
                AddItemDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(AddItemDialogLayout.createSequentialGroup()
                                .addGroup(AddItemDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(AddItemDialogLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(AddItemDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(NewItemNameLabel)
                                                        .addComponent(NewItemPriceLabel)
                                                        .addComponent(NewItemCountLabel))
                                                .addGap(35, 35, 35)
                                                .addGroup(AddItemDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(NewItemName)
                                                        .addComponent(NewItemPrice)
                                                        .addGroup(AddItemDialogLayout.createSequentialGroup()
                                                                .addComponent(NewItemPriceSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 45, Short.MAX_VALUE)
                                                                .addGap(71, 71, 71))))
                                        .addGroup(AddItemDialogLayout.createSequentialGroup()
                                                .addGap(75, 75, 75)
                                                .addComponent(AddItemDialogOK, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(38, 38, 38)
                                                .addComponent(AddItemDialogCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(22, Short.MAX_VALUE))
        );
        AddItemDialogLayout.setVerticalGroup(
                AddItemDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(AddItemDialogLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(AddItemDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(NewItemName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(NewItemNameLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(AddItemDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(NewItemPriceLabel)
                                        .addComponent(NewItemPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(AddItemDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(NewItemCountLabel)
                                        .addComponent(NewItemPriceSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(AddItemDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(AddItemDialogOK)
                                        .addComponent(AddItemDialogCancel))
                                .addContainerGap(26, Short.MAX_VALUE))
        );

    }
    private void fontStyle(){
        InvoicesTableLabel.setFont(new Font("Arial", Font.BOLD, 12));
        InvoiceNumberStaticLabel.setFont(new Font("Arial", Font.BOLD, 12));
        CreateNewInvoiceButton.setFont(new Font("Arial", Font.BOLD, 12));
        DeleteInvoiceButton.setFont(new Font("Arial", Font.BOLD, 12));
    }
    private void invoiceTableGUI(){
        InvoicesTablePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        InvoicesTableLabel.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        InvoicesTableLabel.setText("Invoices Table");
        InvoicesTableLabel.setToolTipText("");
        InvoicesTablePanel.add(InvoicesTableLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));
        InvoicesTableLabel.setFont(new Font("Arial", Font.BOLD, 12));
    }
    private void initComponents() {

        CreateNewInvoiceDialog = new JDialog();
        NewCustomerName = new JTextField();
        NewCustomerNameLabel = new JLabel();
        NewInvoiceDateField = new JTextField();
        NewInvoiceDateLabel = new JLabel();
        CreateNewInvoiceOK = new JButton();
        CreateNewInvoiceCancel = new JButton();
        AddItemDialog = new JDialog();
        NewItemName = new JTextField();
        NewItemNameLabel = new JLabel();
        NewItemPriceLabel = new JLabel();
        AddItemDialogOK = new JButton();
        AddItemDialogCancel = new JButton();
        NewItemPrice = new JTextField();
        NewItemPriceSpinner = new JSpinner();
        NewItemCountLabel = new JLabel();
        InvoicesTablePanel = new JPanel();
        InvoicesTableLabel = new JLabel();
        CreateNewInvoiceButton = new JButton();
        DeleteInvoiceButton = new JButton();
        InvoiceTableScrollPane = new JScrollPane();
        InvoiceTable = new JTable();
        InvoicesItemsPanel = new JPanel();
        InvoiceNumberStaticLabel = new JLabel();
        InvoiceNumberLabel = new JLabel();
        InvoiceDateLabel = new JLabel();
        CustomerNameLabel = new JLabel();
        InvoiceTotalStaticLabel = new JLabel();
        InvoicesItemsLabel = new JLabel();
        InvoiceDateTextField = new JTextField();
        CustomerNameTextField = new JTextField();
        InvoiceTotalLabel = new JLabel();
        InvoicesLineTableScrollPane = new JScrollPane();
        InvoicesLineTable = new JTable();
        DeleteItemButton = new JButton();
        AddItemButton = new JButton();
        MenuBar = new JMenuBar();
        FileMenu = new JMenu();
        LoadFile = new JMenuItem();
        SaveFile = new JMenuItem();
        jSeparator1 = new JPopupMenu.Separator();
        //calling GUI methods
        invoiceDialogGUI();
        invoiceHeaderComponentLayout();
        fontStyle();
        itemComponentLayout();
        fontStyle();
        ((JSpinner.DefaultEditor) NewItemPriceSpinner.getEditor()).getTextField().setEditable(false);
        frameGui();
        invoiceTableGUI();
        invoiceHeaderGUI();
        itemInvoiceGui();
        fileMenuGUI();
        pack();
    }

}
