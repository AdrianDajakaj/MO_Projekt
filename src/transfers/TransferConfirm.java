package transfers;

import database.Database;
import database.DatabaseStatement;
import mainFrame.MainFrame;
import mainPanel.MainPanel;
import timer.AppTimer;
import timer.MouseAction;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Vector;

public class TransferConfirm {
    private JTextField appCodeTxt1;
    private JTextField appCodeTxt2;
    private JTextField appCodeTxt3;
    private JTextField appCodeTxt4;
    private Vector<JTextField> appCode;
    private JLabel appCodeWarning;
    private JButton cancelButton;
    private JButton confirmTrasferButton;
    private JRadioButton printTransferConfirmationRadioButton;
    private JPanel transferConfirmPanel;
    private JLabel chooseFolderLabel;
    private JTree fileTree;
    private JScrollPane fileScrollPane;
    private String transferPanelTitle;
    private JLabel transferPanelTitleLabel;
    private JLabel timeLabel;
    private JPanel timerPanel;
    private KeyAdapter numbersOnly;
    private boolean isTransferConfirmation;
    private boolean confirmButtonValid;
    private Vector<Boolean> confirmButtonValidation;
    private String appCodeStr;
    private String path;
    private MainFrame frame;
    private JPanel otherPanel;
    private Map<String,String> senderData;
    private Map<String,String> receiverData;
    private Map<String,String> transferData;
    private PdfGenerator pdfGenerator;

    public TransferConfirm(MainFrame mainFrame, JPanel transferNextStepPanel, Map<String,String> senderData1, Map<String,String>receiverData1, Map<String,String> transferData1){
        frame = mainFrame;
        AppTimer appTimer = new AppTimer(timeLabel,frame);
        transferConfirmPanel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();
        otherPanel = transferNextStepPanel;
        senderData=senderData1;
        receiverData = receiverData1;
        transferData = transferData1;
        if(transferData.get("typ").equals("Przelew BLIK na telefon")) transferPanelTitle = transferData.get("typ");
        else {
            String[] arr = transferData.get("typ").split("\\s+");
            transferPanelTitle = arr[0] + " " + arr[1];
        }
        transferPanelTitleLabel.setText(transferPanelTitle);
        fileScrollPane.setVisible(false);
        chooseFolderLabel.setVisible(false);
        fileTree.setModel(new FileSystem());
        appCodeWarning.setVisible(false);
        isTransferConfirmation = false;
        confirmButtonValid = true;
        appCodeStr = String.valueOf(senderData.get("kod"));
        appCode = new Vector<>();
        appCode.add(appCodeTxt1);
        appCode.add(appCodeTxt2);
        appCode.add(appCodeTxt3);
        appCode.add(appCodeTxt4);
        numbersOnly = new OnlyNumbers().getKeyAdapter();

        for(JTextField t: appCode){
            t.setDocument(new LimitJTextField(1));
            t.addKeyListener(numbersOnly);
        }
        setCancelButton(cancelButton);
        setPrintTransferConfirmationRadioButton(printTransferConfirmationRadioButton);
        fileTree.setEditable(true);
        fileTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                File file = (File) fileTree.getLastSelectedPathComponent();
                if (file == null) path = "";
                else path = file.getPath();
            }
        });
        setConfirmTrasferButton(confirmTrasferButton);
    }

    void setPrintTransferConfirmationRadioButton(JRadioButton printTransferConfirmationRadioButton){
        printTransferConfirmationRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (state == ItemEvent.SELECTED) {
                    fileScrollPane.setVisible(true);
                    chooseFolderLabel.setVisible(true);
                    isTransferConfirmation = true;
                } else if (state == ItemEvent.DESELECTED) {
                    fileScrollPane.setVisible(false);
                    chooseFolderLabel.setVisible(false);
                    isTransferConfirmation = false;
                }
            }
        });
    }

    void setCancelButton(JButton cancelButton){
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getjFrame().setContentPane(otherPanel);
                frame.getjFrame().setVisible(true);
            }
        });
    }

    void setConfirmTrasferButton(JButton confirmTrasferButton){
        confirmTrasferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmButtonValidation = new Vector<Boolean>();
                for(JTextField t: appCode){
                    if(t.getText().length()==0){
                        confirmButtonValidation.add(false);
                    }
                    else confirmButtonValidation.add(true);
                }
                confirmButtonValid = !confirmButtonValidation.contains(false);
                if(!confirmButtonValid){
                    appCodeWarning.setText("Podaj czterocyfrowy kod");
                    appCodeWarning.setVisible(true);
                }
                else{
                    appCodeWarning.setVisible(false);
                    appCodeWarning.setText("Podany kod jest nieprawid??owy");
                    StringBuilder podanyKod = new StringBuilder();
                    for(JTextField t: appCode) podanyKod.append(t.getText());
                    if(!appCodeStr.equals(String.valueOf(podanyKod))){
                        appCodeWarning.setVisible(true);
                    }
                    else {
                        appCodeWarning.setVisible(false);
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String generationDate = dtf.format(now);
                        /*
                        st.executeUpdate("insert into OutgoingHistoryOrdinary values('"+operationDate+"','"+transferType+"','"+senderAccountNumber+"','"
                            +receiverAccountNumber+"','"+phoneNumber+"','"+transferAmount+"','"+transferCurrency+"','"+totalTransferCost+"','"
                            +transferTitle+"','"+startDate+"','"+endDate+"','"+transferCycle+"','"+transferCycleUnits+"','"+receiverFirstName+"','"
                            +receiverLastName+"','"+receiverTown+"','"+receiverPostCode+"','"+receiverStreet+"','"+receiverStreetNumber+"');");
                         */
                        if(transferPanelTitle.equals("Zlecenie sta??e")){
                            Database.addHistoryOrdinary(DatabaseStatement.st,"OutgoingHistoryOrdinary", generationDate, transferData.get("typ"),
                                        senderData.get("nr konta"), receiverData.get("nr konta"),"",  Double.parseDouble(transferData.get("kwota")),
                                        transferData.get("waluta"), Double.parseDouble(transferData.get("kwotaPLN")), transferData.get("tytul"),
                                        transferData.get("startdata"), transferData.get("enddata"), Integer.parseInt(transferData.get("cykle")),
                                        transferData.get("jednostkaczasu"),receiverData.get("nazwa odbiorcy"),receiverData.get("nazwa odbiorcy cd"),
                                    receiverData.get("miejscowosc"),receiverData.get("kod pocztowy"),receiverData.get("ulica"),receiverData.get("nr domu"));
                            double senderAmount = Double.parseDouble(senderData.get("kontosrodki"));
                            double transferAmount = Double.parseDouble(transferData.get("kwotaPLN"));
                            senderAmount = senderAmount - transferAmount;
                            Database.setOrdinaryAccountBalance(DatabaseStatement.st,senderData.get("username"),senderAmount);
                        }
                        else if(transferPanelTitle.equals("Przelew BLIK na telefon")){
                            Database.addHistoryOrdinary(DatabaseStatement.st,"OutgoingHistoryOrdinary", generationDate, transferData.get("typ"),
                                    senderData.get("nr konta"), receiverData.get("nr konta"),"",  Double.parseDouble(transferData.get("kwota")),
                                    transferData.get("waluta"), Double.parseDouble(transferData.get("kwotaPLN")), transferData.get("tytul"),
                                    "", "", 0,
                                    "",receiverData.get("nazwa odbiorcy"),receiverData.get("nazwa odbiorcy cd"),
                                    receiverData.get("miejscowosc"),receiverData.get("kod pocztowy"),receiverData.get("ulica"),receiverData.get("nr domu"));
                            double senderAmount = Double.parseDouble(senderData.get("kontosrodki"));
                            double transferAmount = Double.parseDouble(transferData.get("kwota"));
                            senderAmount = senderAmount - transferAmount;
                            Database.setOrdinaryAccountBalance(DatabaseStatement.st,senderData.get("username"),senderAmount);
                        }
                        else if(transferPanelTitle.equals("Przelew w??asny")){
                            Database.addHistoryOrdinary(DatabaseStatement.st,"OutgoingHistoryOrdinary", generationDate, transferData.get("typ"),
                                    senderData.get("nr konta"), receiverData.get("nr konta"),"",  Double.parseDouble(transferData.get("kwota")),
                                    transferData.get("waluta"), Double.parseDouble(transferData.get("kwotaPLN")), transferData.get("tytul"),
                                    "", "", 0,
                                    "",receiverData.get("nazwa odbiorcy"),receiverData.get("nazwa odbiorcy cd"),
                                    receiverData.get("miejscowosc"),receiverData.get("kod pocztowy"),receiverData.get("ulica"),receiverData.get("nr domu"));
                            double senderAmount = Double.parseDouble(senderData.get("kontosrodki"));
                            double transferAmount = Double.parseDouble(transferData.get("kwota"));
                            senderAmount = senderAmount - transferAmount;
                            Database.setOrdinaryAccountBalance(DatabaseStatement.st,senderData.get("username"),senderAmount);
                        }
                        else {
                            Database.addHistoryOrdinary(DatabaseStatement.st,"OutgoingHistoryOrdinary", generationDate, transferData.get("typ"),
                                    senderData.get("nr konta"), receiverData.get("nr konta"),"",  Double.parseDouble(transferData.get("kwota")),
                                    transferData.get("waluta"), Double.parseDouble(transferData.get("kwotaPLN")), transferData.get("tytul"),
                                    "", "", 0,
                                    "",receiverData.get("nazwa odbiorcy"),receiverData.get("nazwa odbiorcy cd"),
                                    receiverData.get("miejscowosc"),receiverData.get("kod pocztowy"),receiverData.get("ulica"),receiverData.get("nr domu"));
                            double senderAmount = Double.parseDouble(senderData.get("kontosrodki"));
                            double transferAmount = Double.parseDouble(transferData.get("kwotaPLN"));
                            senderAmount = senderAmount - transferAmount;
                            Database.setOrdinaryAccountBalance(DatabaseStatement.st,senderData.get("username"),senderAmount);
                        }
                        if(isTransferConfirmation){
                            if(transferPanelTitle.equals("Zlecenie sta??e")) {
                                try {
                                    pdfGenerator = new PdfFactory(generationDate,senderData,receiverData,transferData).getPdfGenerator(PdfFactory.PdfType.ZLECENIESTALE);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                } catch (FontFormatException fontFormatException) {
                                    fontFormatException.printStackTrace();
                                }
                            }
                            else if(transferPanelTitle.equals("Przelew BLIK na telefon")) {
                                try {
                                    pdfGenerator = new PdfFactory(generationDate,senderData,receiverData,transferData).getPdfGenerator(PdfFactory.PdfType.BLIK);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                } catch (FontFormatException fontFormatException) {
                                    fontFormatException.printStackTrace();
                                }
                            }
                            else if(transferPanelTitle.equals("Przelew w??asny")) {
                                try {
                                    pdfGenerator = new PdfFactory(generationDate,senderData,receiverData,transferData).getPdfGenerator(PdfFactory.PdfType.WLASNY);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                } catch (FontFormatException fontFormatException) {
                                    fontFormatException.printStackTrace();
                                }
                            }
                            else {
                                try {
                                    pdfGenerator = new PdfFactory(generationDate,senderData,receiverData,transferData).getPdfGenerator(PdfFactory.PdfType.STANDARD);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                } catch (FontFormatException fontFormatException) {
                                    fontFormatException.printStackTrace();
                                }
                            }
                            try {
                                pdfGenerator.generatePDF(path);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        MainPanel p = new MainPanel(senderData.get("username"),frame);
                    }
                }
            }
        });
    }

    JPanel getTransferConfirmPanel(){
        return transferConfirmPanel;
    }
}
