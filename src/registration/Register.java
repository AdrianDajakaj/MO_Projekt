package registration;

import database.Database;
import database.DatabaseStatement;
import mainFrame.MainFrame;
import transfers.LimitJTextField;
import transfers.OnlyNumbers;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Statement;
import java.util.Vector;

public class Register {
    private JPanel registerPanel;
    private JLabel panelTitleLabel;
    private JTextField usernameTxt;
    private JPasswordField passwordTxt;
    private JLabel passwordWarning;
    private JLabel usernameWarning;
    private JTextField mailTxt;
    private JTextField appCodeTxt1;
    private JTextField appCodeTxt2;
    private JTextField appCodeTxt3;
    private JTextField nameTxt;
    private JTextField surnameTxt;
    private JTextField townTxt;
    private JTextField homeNumber;
    private JTextField homeNumberCd;
    private JTextField postCodeTxt1;
    private JTextField postCodeTxt2;
    private JTextField streetTxt;
    private JTextField appCodeTxt4;
    private JTextField peselTxt;
    private JButton cancelButton;
    private JButton nextButton;
    private JLabel emailWarning;
    private JLabel nameWarning;
    private JLabel surnameWarning;
    private JLabel peselWarning;
    private JTextField phoneNumberTxt;
    private JLabel phoneNumberWarning;
    private JLabel townWarning;
    private JLabel postCodeWarning;
    private JLabel streetWarning;
    private JLabel homeNumberWarning;
    private JLabel RODOWarning;
    private JLabel standardWarning;
    private JLabel savingWarning;
    private JLabel appCodeWarning;
    private KeyAdapter numbersOnly;
    private MainFrame frame;
    private JLabel[] warnings = {appCodeWarning,usernameWarning,passwordWarning,peselWarning,nameWarning,surnameWarning,emailWarning,phoneNumberWarning,
            townWarning,postCodeWarning,streetWarning,homeNumberWarning};
    private Vector<JTextField> appCode;
    private boolean isPostCodeValid;
    private boolean isappCodeValid;
    private Vector<Boolean> validation;
    private boolean isPeselValid;
    private boolean isPhoneValid;
    private JPanel loginPanel;

    public Register(JPanel loginPanel1, MainFrame mainFrame){
        loginPanel = loginPanel1;
        frame = mainFrame;
        Statement st= Database.connectToDatabase("SystemBank", "root", "Adix_23/09/1999");
        numbersOnly = new OnlyNumbers().getKeyAdapter();
        setLabels();
        setPostcodeTxt(postCodeTxt1);
        setPostcodeTxt(postCodeTxt2);
        setPeselTxt();
        setPhoneNumberTxt();
        setAppCode();
        setNextButton();
        homeNumber.addKeyListener(numbersOnly);
        homeNumberCd.addKeyListener(numbersOnly);
        setCancelButton();
        frame.getjFrame().setContentPane(registerPanel);
        frame.getjFrame().setVisible(true);
    }
    void setLabels(){
        for(JLabel w:warnings) w.setVisible(false);
    }
    void setPeselTxt(){
        peselTxt.setDocument(new LimitJTextField(11));
        peselTxt.addKeyListener(numbersOnly);
        peselTxt.addKeyListener(new KeyAdapter() {
            String s1 = "";
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()>='0'&& e.getKeyChar()<='9'){
                    s1+=e.getKeyChar();
                }
                if(e.getKeyChar()==KeyEvent.VK_BACK_SPACE&&s1.length()>0){
                    s1 = s1.substring(0,s1.length()-1);
                }
                isPeselValid = (s1.length() == 11);
                peselWarning.setText("Niepoprawny PESEL");
                peselWarning.setVisible(!isPeselValid);
            }
        });
    }
    void setPhoneNumberTxt(){
        phoneNumberTxt.setDocument(new LimitJTextField(9));
        phoneNumberTxt.addKeyListener(numbersOnly);
        phoneNumberTxt.addKeyListener(new KeyAdapter() {
            String s1 = "";
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()>='0'&& e.getKeyChar()<='9'){
                    s1+=e.getKeyChar();
                }
                if(e.getKeyChar()==KeyEvent.VK_BACK_SPACE&&s1.length()>0){
                    s1 = s1.substring(0,s1.length()-1);
                }
                isPhoneValid = (s1.length() == 9);
                phoneNumberWarning.setText("Niepoprawny nr. telefonu");
                phoneNumberWarning.setVisible(!isPhoneValid);
            }
        });
    }
    void setPostcodeTxt(JTextField postcodeTxt){
        if(postcodeTxt==postCodeTxt1) postcodeTxt.setDocument(new LimitJTextField(2));
        else postcodeTxt.setDocument(new LimitJTextField(3));
        postcodeTxt.addKeyListener(numbersOnly);
        postcodeTxt.addKeyListener(new KeyAdapter() {
            String s1 = "";
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()>='0'&& e.getKeyChar()<='9'){
                    s1+=e.getKeyChar();
                }
                if(e.getKeyChar()==KeyEvent.VK_BACK_SPACE&&s1.length()>0){
                    s1 = s1.substring(0,s1.length()-1);
                }
                if(postcodeTxt==postCodeTxt1) isPostCodeValid = (s1.length() == 2);
                else isPostCodeValid = (s1.length()==3);
                postCodeWarning.setText("Niepoprawny kod pocztowy");
                postCodeWarning.setVisible(!isPostCodeValid);
            }
        });
    }
    void setCancelButton(){
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getjFrame().setContentPane(loginPanel);
                frame.getjFrame().setVisible(true);
            }
        });
    }
    void setAppCode(){
        appCode = new Vector<>();
        appCode.add(appCodeTxt1);
        appCode.add(appCodeTxt2);
        appCode.add(appCodeTxt3);
        appCode.add(appCodeTxt4);

        for(JTextField t: appCode){
            t.setDocument(new LimitJTextField(1));
            t.addKeyListener(numbersOnly);
        }

        for(JTextField t: appCode){
            t.setDocument(new LimitJTextField(1));
            t.addKeyListener(numbersOnly);
        }
    }

    void setNextButton() {
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validation = new Vector<>();
                for(JTextField t: appCode){
                    if(t.getText().length()==0){
                        validation.add(false);
                    }
                    else validation.add(true);
                }
                isappCodeValid = !validation.contains(false);
                if(!isappCodeValid){
                    appCodeWarning.setText("Podaj czterocyfrowy kod");
                    appCodeWarning.setVisible(true);
                }
                else {
                    appCodeWarning.setVisible(false);
                    StringBuilder podanyKod = new StringBuilder();
                    for (JTextField t : appCode) podanyKod.append(t.getText());
                    if (usernameTxt.getText().length() == 0) {
                        validation.add(false);
                        usernameWarning.setText("To pole jest wymagane");
                        usernameWarning.setVisible(true);
                    } else {
                        validation.add(true);
                        usernameWarning.setVisible(false);
                    }
                    if (passwordTxt.getText().length() == 0) {
                        validation.add(false);
                        passwordWarning.setText("To pole jest wymagane");
                        passwordWarning.setVisible(true);
                    } else {
                        validation.add(true);
                        passwordWarning.setVisible(false);
                    }
                    if (passwordTxt.getText().length() < 10) {
                        validation.add(false);
                        passwordWarning.setText("Zbyt krótkie hasło");
                        passwordWarning.setVisible(true);
                    } else {
                        validation.add(true);
                        passwordWarning.setVisible(false);
                    }
                    if(mailTxt.getText().length()==0){
                        emailWarning.setVisible(true);
                        validation.add(false);
                    }
                    else{
                        emailWarning.setVisible(false);
                        validation.add(true);
                    }
                    if (nameTxt.getText().length() == 0) {
                        validation.add(false);
                        nameWarning.setVisible(true);
                    } else {
                        validation.add(true);
                        nameWarning.setVisible(false);
                    }
                    if (surnameTxt.getText().length() == 0) {
                        validation.add(false);
                        surnameWarning.setVisible(true);
                    } else {
                        validation.add(true);
                        surnameWarning.setVisible(false);
                    }
                    if (peselTxt.getText().length() == 0) {
                        validation.add(false);
                        peselWarning.setText("To pole jest wymagane");
                        peselWarning.setVisible(true);
                    } else {
                        validation.add(true);
                        peselWarning.setVisible(false);
                    }
                    if (phoneNumberTxt.getText().length() == 0) {
                        validation.add(false);
                        phoneNumberWarning.setText("To pole jest wymagane");
                        phoneNumberWarning.setVisible(true);
                    } else {
                        validation.add(true);
                        phoneNumberWarning.setVisible(false);
                    }
                    if (townTxt.getText().length() == 0) {
                        validation.add(false);
                        townWarning.setVisible(true);
                    } else {
                        validation.add(true);
                        townWarning.setVisible(false);
                    }
                    if (postCodeTxt1.getText().length() == 0 || postCodeTxt2.getText().length() == 0) {
                        validation.add(false);
                        postCodeWarning.setText("To pole jest wymagane");
                        postCodeWarning.setVisible(true);
                    } else {
                        validation.add(true);
                        postCodeWarning.setVisible(false);
                    }
                    if (streetTxt.getText().length() == 0) {
                        validation.add(false);
                        streetWarning.setVisible(true);
                    } else {
                        validation.add(true);
                        streetWarning.setVisible(false);
                    }
                    if (homeNumber.getText().length() == 0) {
                        validation.add(false);
                        homeNumberWarning.setVisible(true);
                    } else {
                        validation.add(true);
                        homeNumberWarning.setVisible(false);
                    }
                    if(!validation.contains(false)){
                        if (Database.isUsernameTaken(DatabaseStatement.st, usernameTxt.getText())) {
                            validation.add(false);
                            usernameWarning.setText("Nazwa użytkownika zajęta");
                            usernameWarning.setVisible(true);
                        } else {
                            validation.add(true);
                            usernameWarning.setVisible(false);
                        }
                        if(!validation.contains(false)){
                            Database.addUser(DatabaseStatement.st,usernameTxt.getText(),passwordTxt.getText(),mailTxt.getText(),String.valueOf(podanyKod));
                            String homenum;
                            if(homeNumberCd.getText().length()!=0){
                                homenum = homeNumber.getText()+"/"+homeNumberCd.getText();
                            }
                            else homenum = homeNumber.getText();
                            Database.addUserData(DatabaseStatement.st,usernameTxt.getText(),passwordTxt.getText(),mailTxt.getText(),peselTxt.getText(),
                                    phoneNumberTxt.getText(),townTxt.getText(),postCodeTxt1.getText()+"-"+postCodeTxt2.getText(),streetTxt.getText(),homenum);
                            SuccesfulRegister sc = new SuccesfulRegister(loginPanel,frame,usernameTxt.getText());
                            frame.getjFrame().setContentPane(sc.getSucessfulPanel());
                            frame.getjFrame().setVisible(true);
                        }
                    }
                }
            }
        });
    }
    JPanel getRegisterPanel(){
        return registerPanel;
    }
}
