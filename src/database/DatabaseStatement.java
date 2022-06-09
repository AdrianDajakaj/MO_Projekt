package database;

import java.sql.Statement;

public interface DatabaseStatement {
    public Statement st= Database.connectToDatabase("SystemBank", "root", "Adix_23/09/1999");
}
