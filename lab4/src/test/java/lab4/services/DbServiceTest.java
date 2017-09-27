package lab4.services;

import org.testng.annotations.Test;

public class DbServiceTest {
    @Test
    public void testGetNewConnection() throws Exception {
        DbService.getNewConnection();
    }

}