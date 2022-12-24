package model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;

import com.google.common.io.Files;
import view.MyGUI;
import controller.mainController;
public class fileOperations {

    public static File InvoiceHeaderFile = new File(System.getProperty("user.dir") + "/InvoiceHeader.csv");
    public static File InvoiceLineFile = new File(System.getProperty("user.dir") + "/InvoiceLine.csv");
    public static SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
    private MyGUI myGui;

    public fileOperations(MyGUI myGui) {
        this.myGui = myGui;
    }
    public void getPath() {
        String fileExtension;
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(myGui);
        if(result==JFileChooser.APPROVE_OPTION){
            String path = fc.getSelectedFile().getPath();

            fileExtension =Files.getFileExtension(path);
            if(fileExtension.equals("csv"))
            {
                InvoiceHeaderFile=new File(path);
            }
            else
            {
                MyGUI.setJOptionPaneMessagMessage(myGui, "Wrong file extension", "Invoice header", "Error_Message");
                getPath();
            }
        }

    }

    public ArrayList<InvoiceHeader> readFile() {
        String inoviceLine;
        String[] invoicesHeader;
        String invoiceNumberStr;
        String invoiceDateStr;
        String invoiceCustomerName;
        int invoiceNumber;
        Date invoiceDate;
        InvoiceHeader header;
        InvoiceLine line;
        String[] invoicesLine;
        String invoiceItemName;
        String itemPriceStr;
        String itemCountStr;
        float itemPrice;
        int itemCount;
        InvoiceHeader temporary;
        ArrayList<InvoiceHeader> invoices = null;

        try {
            if (InvoiceHeaderFile != null && InvoiceLineFile != null) {
                invoices = new ArrayList<>();
                FileReader file = new FileReader(InvoiceHeaderFile);
                BufferedReader bufferReader = new BufferedReader(file);
                inoviceLine = bufferReader.readLine();

                while (inoviceLine != null) {
                    invoicesHeader = inoviceLine.split(",");
                    if (invoicesHeader.length != 3) {
                        System.out.println("error1");
                        throw new Exception("wrong format");
                    }

                    invoiceNumberStr = invoicesHeader[0];
                    if (!invoiceNumberStr.matches("^\\d+$")) {
                        System.out.println("error1");
                        throw new Exception("wrong format");
                    }

                    invoiceDateStr = invoicesHeader[1];
                    if (!invoiceDateStr.matches("^\\d{2}\\-\\d{2}\\-\\d{4}$")) {
                        System.out.println("error1");
                        throw new Exception("wrong format");
                    }

                    invoiceCustomerName = invoicesHeader[2];
                    myGui.getDate().setLenient(false);
                    invoiceDate = myGui.getDate().parse(invoiceDateStr);
                    invoiceNumber = Integer.parseInt(invoiceNumberStr);
                    header = new InvoiceHeader(invoiceNumber, invoiceDate, invoiceCustomerName);
                    invoices.add(header);

                    inoviceLine = bufferReader.readLine();
                }

                bufferReader.close();
                file.close();
            }
        } catch (Exception e) {
            InvoiceHeaderFile = null;
            InvoiceLineFile = null;
            invoices.clear();
            MyGUI.setJOptionPaneMessagMessage(myGui, "Data in this file is in wrong format,choose another file", "Error", "ERROR_MESSAGE");
            getPath();
        }

        try {
            if (InvoiceHeaderFile != null && InvoiceLineFile != null) {
                FileReader file = new FileReader(InvoiceLineFile);
                BufferedReader bufferReader = new BufferedReader(file);
                inoviceLine = bufferReader.readLine();

                while (inoviceLine != null){

                    invoicesLine = inoviceLine.split(",");
                    if (invoicesLine.length != 4) {
                        throw new Exception("wrong format");
                    }

                    invoiceNumberStr = invoicesLine[0];
                    if (!invoiceNumberStr.matches("^\\d+$")){
                        throw new Exception("wrong format");
                    }

                    invoiceItemName = invoicesLine[1];
                    itemPriceStr = invoicesLine[2];
                    itemCountStr = invoicesLine[3];
                    if (!(itemCountStr.matches("^\\d+$"))||Integer.parseInt(itemCountStr)<1){
                        throw new Exception("wrong format");
                    }

                    invoiceNumber = Integer.parseInt(invoiceNumberStr);
                    itemPrice = Float.parseFloat(itemPriceStr);
                    itemCount = Integer.parseInt(itemCountStr);

                    temporary = findParentHeader(invoiceNumber, invoices);
                    line = new InvoiceLine(invoiceItemName, itemPrice, itemCount, temporary);
                    temporary.getInvoicerow().add(line);

                    inoviceLine = bufferReader.readLine();
                }

                bufferReader.close();
                file.close();
            }
        } catch (Exception e) {
            InvoiceHeaderFile = null;
            InvoiceLineFile = null;

            invoices.clear();
            MyGUI.setJOptionPaneMessagMessage(myGui, "Data in this file is in wrong format,choose another file", "Error", "ERROR_MESSAGE");
            getPath();
        }
        return invoices;
    }

    public void writeFile(ArrayList<InvoiceHeader> invoices) throws IOException {

        int invoiceLinelines;
        int totalInvoiceLinelines = 0;
        int actualLine = 0;
        FileWriter fileWriter = new FileWriter(InvoiceHeaderFile);

        for (int i = 0; i < invoices.size(); i++) {
            String inoviceLine = invoices.get(i).getInoviceNumber() + "," + date.format(invoices.get(i).getInoviceDate()) + "," + invoices.get(i).getInoviceCustomerName();
            if (i != invoices.size() - 1) {
                inoviceLine += "\n";
            }
            fileWriter.write(inoviceLine);
        }

        fileWriter.close();
        fileWriter = new FileWriter(InvoiceLineFile);

        for (int i = 0; i < invoices.size(); i++) {
            totalInvoiceLinelines += invoices.get(i).getInvoicerow().size();
        }

        for (int i = 0; i < invoices.size(); i++) {
            invoiceLinelines = invoices.get(i).getInvoicerow().size();

            for (int j = 0; j < invoiceLinelines; j++) {
                String newLine = Integer.toString(invoices.get(i).getInvoicerow().get(j).getMainInvoice().getInoviceNumber()) + ",";
                newLine += invoices.get(i).getInvoicerow().get(j).getItemName() + ",";
                newLine += Float.toString(invoices.get(i).getInvoicerow().get(j).getItemPrice()) + ",";
                newLine += Integer.toString(invoices.get(i).getInvoicerow().get(j).getItemCount());
                actualLine++;

                if (! (totalInvoiceLinelines == actualLine)) {
                    newLine += "\n";
                }
                fileWriter.write(newLine);

            }
        }

        MyGUI.setJOptionPaneMessagMessage(myGui, "New data is saved", "Saved", "INFORMATION_MESSAGE");
        fileWriter.close();

    }

    private InvoiceHeader findParentHeader(int invoiceNumber, ArrayList<InvoiceHeader> invoices) {
        InvoiceHeader returnElement = null;
        for (int i = 0; i < invoices.size(); i++) {
            if (invoices.get(i).getInoviceNumber() == invoiceNumber) {
                returnElement = invoices.get(i);
            }
        }
        return returnElement;
    }

    public void getMaxNumberOfExistedInvoices(int maxNumberOfExistedInvoices, ArrayList<InvoiceHeader> invoices) {
        for (int i = 0; i < invoices.size(); i++) {
            if ((invoices.get(i).getInoviceNumber()) > mainController.maxNumberOfExistedInvoices) {
                mainController.maxNumberOfExistedInvoices = invoices.get(i).getInoviceNumber();
            }
        }
    }
    public void main(ArrayList<InvoiceHeader> invoices) {
        if (InvoiceHeaderFile != null && InvoiceLineFile != null) {
            for (int i = 0; i < invoices.size(); i++) {
                System.out.println("INVOICE " + invoices.get(i).getInoviceNumber() + " : ");

                String pattern = "MM-dd-yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date = simpleDateFormat.format(invoices.get(i).getInoviceDate());


                System.out.print(date + ", " + invoices.get(i).getInoviceCustomerName());
                System.out.println("");

                for (int j = 0; j < invoices.get(i).getInvoicerow().size(); j++) {
                    System.out.println(invoices.get(i).getInvoicerow().get(j).getItemName() + ", "
                            + invoices.get(i).getInvoicerow().get(j).getItemPrice() + ", "
                            + invoices.get(i).getInvoicerow().get(j).getItemCount());
                }

                System.out.println("");
            }
        }
    }
}
