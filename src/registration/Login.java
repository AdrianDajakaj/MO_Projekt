package registration;

import database.Database;
import database.DatabaseStatement;
import mainFrame.MainFrame;
import mainPanel.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Login {
    private JLabel panelTitleLabel;
    private JTextField loginTxt;
    private JPasswordField passwordTxt;
    private JButton loginButton;
    private JPanel loginPanel;
    private JLabel usernameWarning;
    private JLabel passwordWarning;
    private JLabel loginWarning;
    private JButton registerButton;
    private MainFrame frame;
    private Vector<Boolean> validation;
    public Login(MainFrame mainFrame){
        frame = mainFrame;
        loginWarning.setVisible(false);
        passwordWarning.setVisible(false);
        usernameWarning.setVisible(false);
        setRegisterButton();
        setLoginButton();
        frame.getjFrame().setContentPane(loginPanel);
        frame.getjFrame().setVisible(true);
    }
    void setRegisterButton(){
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register reg = new Register(loginPanel,frame);
                frame.getjFrame().setContentPane(reg.getRegisterPanel());
                frame.getjFrame().setVisible(true);
            }
        });
    }
    void setLoginButton(){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validation = new Vector<>();
                if(loginTxt.getText().length()==0){
                    usernameWarning.setVisible(true);
                    validation.add(false);
                }
                else{
                    usernameWarning.setVisible(false);
                    validation.add(true);
                }
                if(passwordTxt.getText().length()==0){
                    passwordWarning.setVisible(true);
                    validation.add(false);
                }
                else{
                    passwordWarning.setVisible(false);
                    validation.add(true);
                }
                if(!verificationData()){
                    loginWarning.setVisible(true);
                    validation.add(false);
                }else{
                    loginWarning.setVisible(false);
                    validation.add(true);
                }
                if(!validation.contains(false)){
                    MainPanel m = new MainPanel(loginTxt.getText(),frame);
                    frame.getjFrame().setContentPane(m.getMainPanel());
                    frame.getjFrame().setVisible(true);
                }
            }
        });
    }
    boolean verificationData(){
        if(loginTxt.getText().length()==0) return false;
        if(passwordTxt.getText().length()==0) return false;
        if(!Database.verifyUser(DatabaseStatement.st,loginTxt.getText(),passwordTxt.getText())) return false;
        else return true;
    }
}
