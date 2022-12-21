package view;

import controller.mainController;
import java.io.FileNotFoundException;
import model.InvoiceHeader;
import model.InvoiceLine;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        GUI gui;
        gui = new GUI();
        gui.setVisible(true);
        gui.setLocations();
        gui.setResizable(false);
        gui.loadFiles();
       gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        new mainController(new InvoiceHeader(), new InvoiceLine(), gui);
    }
}
