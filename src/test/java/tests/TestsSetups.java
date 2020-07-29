package tests;

import databaseConnect.JDBCConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import utils.Log;

public class TestsSetups {
    @BeforeEach
    public void setUp(TestInfo testInfo) {
        Log.info("------- Started test: " + testInfo.getDisplayName() + " -------");
    }


    @AfterEach
    public void tearDown(TestInfo testInfo) {
        JDBCConnection.closeConnection();
        Log.info("------- Finished test: " + testInfo.getDisplayName() + " -------");
    }
}
