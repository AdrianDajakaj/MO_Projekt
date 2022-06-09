package transfers;

import mainFrame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class TransferFactory {
    public enum TransferType {
        KRAJOWY, ZAGRANICZNY, ZLECENIESTALE, TELEFONBLIK, WLASNY;
    }
    private MainFrame frame;
    private Map<String,String> senderData;
    private JPanel cancelPanel;

    public TransferFactory(JPanel cancelPanel1,MainFrame mainFrame, Map<String,String> senderData1){
        cancelPanel = cancelPanel1;
        frame = mainFrame;
        senderData = senderData1;
    }
    public Transfer getTransfer(TransferType transferType) throws IOException, FontFormatException {
        return switch (transferType) {
            case KRAJOWY -> new StandardTransfer(cancelPanel,frame, senderData);
            case ZAGRANICZNY -> new ForeignStandardTransfer(cancelPanel,frame, senderData);
            case ZLECENIESTALE -> new StandingOrder(cancelPanel,frame, senderData);
            case TELEFONBLIK -> new BlikPhoneTransfer(cancelPanel,frame, senderData);
            case WLASNY -> new OwnTransfer(cancelPanel,frame,senderData);
        };
    }
}
