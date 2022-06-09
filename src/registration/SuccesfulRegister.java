package registration;

import database.Database;
import database.DatabaseStatement;
import mainFrame.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SuccesfulRegister {
    private JLabel panelTitleLabel;
    private JLabel ordinaryAccountNr;
    private JLabel savingAccountNr;
    private JButton powrótDoEkranuLogowaniaButton;
    private JPanel sucessfulPanel;
    String ordinaryAccountNumber="";
    String savingsAccountNumber="";
    private MainFrame frame;
    private String userName;
    private JPanel loginPanel;
    public SuccesfulRegister(JPanel loginPanel1,MainFrame mainFrame,String userName1){
        loginPanel = loginPanel1;
        userName = userName1;
        frame = mainFrame;
        do {
            ordinaryAccountNumber = "";
            ordinaryAccountNumber+="PL";
            for (int i = 1; i <= 26; ++i) {
                int num = getRandomNumberUsingNextInt(0, 9);
                ordinaryAccountNumber += String.valueOf(num);
            }
        } while (Database.verifyOrdinaryAccountNumber(DatabaseStatement.st, ordinaryAccountNumber));
        do {
            savingsAccountNumber = "";
            savingsAccountNumber+="PL";
            for (int i = 1; i <= 26; ++i) {
                int num = getRandomNumberUsingNextInt(0, 9);
                savingsAccountNumber += String.valueOf(num);
            }
        } while (Database.verifySavingsAccountNumber(DatabaseStatement.st, savingsAccountNumber));
        ordinaryAccountNr.setText(ordinaryAccountNumber);
        savingAccountNr.setText(savingsAccountNumber);
        Database.addSavingsAccountNumber(DatabaseStatement.st,userName,savingsAccountNumber);
        Database.addOrdinaryAccountNumber(DatabaseStatement.st,userName,ordinaryAccountNumber);
        setPowrótDoEkranuLogowaniaButton();
    }
    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public JPanel getSucessfulPanel(){
        return sucessfulPanel;
    }
    void setPowrótDoEkranuLogowaniaButton(){
        powrótDoEkranuLogowaniaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getjFrame().setContentPane(loginPanel);
                frame.getjFrame().setVisible(true);
            }
        });
    }
}

