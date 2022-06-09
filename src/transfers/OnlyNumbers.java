package transfers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OnlyNumbers{
    KeyAdapter keyAdapter;
    public OnlyNumbers(){
        keyAdapter = new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        };
    }

    public KeyAdapter getKeyAdapter() {
        return keyAdapter;
    }
}
