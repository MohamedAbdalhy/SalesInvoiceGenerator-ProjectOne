package view;

import controller.mainController;
import java.io.FileNotFoundException;
import model.InvoiceHeader;
import model.InvoiceLine;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        MyGUI myGui;
        myGui = new MyGUI();
        myGui.loadFiles();
        myGui.setVisible(true);
        myGui.setLocations();
        myGui.setResizable(false);
       myGui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        new mainController(new InvoiceHeader(), new InvoiceLine(), myGui);
    }
}
