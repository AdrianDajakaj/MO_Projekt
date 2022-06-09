package mainPanel;
import database.Database;
import database.DatabaseStatement;
import mainFrame.MainFrame;
import registration.Login;
import registration.Register;
import timer.AppTimer;
import timer.MouseAction;
import transfers.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainPanel {
    private JPanel mainPanel;
    private JLabel panelTitleLabel;
    private JLabel userBalance;
    private JPanel timerPanel;
    private JLabel timeLabel;
    private JButton standardButton;
    private JButton logOutButton;
    private JButton foreignButton;
    private JButton blikButton;
    private JButton standingOrderButton;
    private JButton ownButton;
    private MainFrame frame;
    private  Map<String,String> senderData;
    public MainPanel(String userName, MainFrame mainFrame){
        frame = mainFrame;
        AppTimer appTimer = new AppTimer(timeLabel,frame);
        mainPanel.addMouseMotionListener(new MouseAction(appTimer));
        appTimer.start();
        senderData = new HashMap<>();
        Map<String,String> userData = Database.getUserData(DatabaseStatement.st,userName);
        senderData.put("username",userName);
        senderData.put("kod", Database.getAppCode(DatabaseStatement.st,userName));
        senderData.put("nr konta",Database.getMainAccountNumber(DatabaseStatement.st,userName));
        senderData.put("kontosrodki",String.valueOf(Math.round(Database.getOrdinaryAccountBalance(DatabaseStatement.st,userName)*100.0)/100.0));
        senderData.put("nrkonta1",Database.getMainAccountNumber(DatabaseStatement.st,userName));
        senderData.put("nrkonta2",Database.getSavingAccountNumber(DatabaseStatement.st,userName));
        senderData.put("konto1srodki",String.valueOf(Math.round(Database.getOrdinaryAccountBalance(DatabaseStatement.st,userName)*100.0)/100.0));
        senderData.put("konto2srodki",String.valueOf(Math.round(Database.getSavingsAccountBalance(DatabaseStatement.st,userName)*100.0)/100.0));
        senderData.put("nazwa odbiorcy",userData.get("First name"));
        senderData.put("nazwa odbiorcy cd",userData.get("Last name"));
        senderData.put("miejscowosc",userData.get("Town"));
        senderData.put("kod pocztowy",userData.get("Postcode"));
        senderData.put("ulica",userData.get("Street"));
        senderData.put("nr domu",userData.get("Street number"));
        setStandardButton();
        setBlikButton();
        setForeignButton();
        setStandingOrderButton();
        setOwnButton();
        setLogOutButton();
        frame.getjFrame().setContentPane(mainPanel);
        frame.getjFrame().setVisible(true);
    }
    void setStandardButton(){
        standardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Register reg = new Register(loginPanel,frame);
                try {
                    Transfer t1 = new TransferFactory(mainPanel,new MainFrame(),senderData).getTransfer(TransferFactory.TransferType.KRAJOWY);
                    frame.getjFrame().dispose();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (FontFormatException fontFormatException) {
                    fontFormatException.printStackTrace();
                }
            }
        });
    }
    void setForeignButton(){
        foreignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Register reg = new Register(loginPanel,frame);
                try {
                    Transfer t1 = new TransferFactory(mainPanel,new MainFrame(),senderData).getTransfer(TransferFactory.TransferType.ZAGRANICZNY);
                    frame.getjFrame().dispose();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (FontFormatException fontFormatException) {
                    fontFormatException.printStackTrace();
                }
            }
        });
    }
    void setBlikButton(){
        blikButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Register reg = new Register(loginPanel,frame);
                try {
                    Transfer t1 = new TransferFactory(mainPanel,new MainFrame(),senderData).getTransfer(TransferFactory.TransferType.TELEFONBLIK);
                    frame.getjFrame().dispose();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (FontFormatException fontFormatException) {
                    fontFormatException.printStackTrace();
                }
            }
        });
    }
    void setStandingOrderButton(){
        standingOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Register reg = new Register(loginPanel,frame);
                try {
                    Transfer t1 = new TransferFactory(mainPanel,new MainFrame(),senderData).getTransfer(TransferFactory.TransferType.ZLECENIESTALE);
                    frame.getjFrame().dispose();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (FontFormatException fontFormatException) {
                    fontFormatException.printStackTrace();
                }
            }
        });
    }
    void setOwnButton(){
        ownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Register reg = new Register(loginPanel,frame);
                try {
                    Transfer t1 = new TransferFactory(mainPanel,new MainFrame(),senderData).getTransfer(TransferFactory.TransferType.WLASNY);
                    frame.getjFrame().dispose();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (FontFormatException fontFormatException) {
                    fontFormatException.printStackTrace();
                }
            }
        });
    }
    void setLogOutButton(){
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login l = new Login(frame);
                frame.getjFrame().dispose();
            }
        });
    }
    public JPanel getMainPanel(){
        return mainPanel;
    }

}
