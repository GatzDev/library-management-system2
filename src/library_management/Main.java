package library_management;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        TerminalInterface terminalInterface = new TerminalInterface();
        terminalInterface.run();
    }
}
