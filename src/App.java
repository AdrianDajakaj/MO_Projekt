import database.Database;
import mainFrame.MainFrame;
import registration.Register;
import transfers.*;
import  mainPanel.*;
import registration.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class App {
    public static void main(String[] args) throws IOException, FontFormatException {
        Map<String,String> nadawcaDane = new HashMap<>();
        nadawcaDane.put("kod", "1234");
        nadawcaDane.put("nr konta","96 1160 2202 0000 0003 5630 0252");//nr konta z ktorego aktualnie korzystamy
        nadawcaDane.put("kontosrodki","1399");//srodki na koncie z ktorego aktualnie korzystamy
        nadawcaDane.put("nrkonta1","96 1160 2202 0000 0003 5630 0252");//nr konta glownego
        nadawcaDane.put("nrkonta2","66 1252 4202 0003 1234 5423 0157");//nr konta oszczednosciowego
        nadawcaDane.put("konto1srodki","1397.93");
        nadawcaDane.put("konto2srodki","249.99");
        nadawcaDane.put("nazwa odbiorcy","Adam");
        nadawcaDane.put("nazwa odbiorcy cd","Kowalski");
        nadawcaDane.put("miejscowosc","Kraków");
        nadawcaDane.put("kod pocztowy","30-348");
        nadawcaDane.put("ulica","Grota-Roweckiego");
        nadawcaDane.put("nr domu","37/17");
        //Transfer t1 = new TransferFactory(new MainFrame(),nadawcaDane).getTransfer(TransferFactory.TransferType.ZLECENIESTALE);
        //MainPanel m = new MainPanel(new MainFrame());
        //Statement st = Database.connectToDatabase("SystemBank", "root", "Adix_23/09/1999");
        //Database.addUser(st,"user2","user2pass","user9@gmail.com","1234");
        //new Register(new MainFrame());
        //new registration.Register(new MainFrame());
        new registration.Login(new MainFrame());
    }
}
