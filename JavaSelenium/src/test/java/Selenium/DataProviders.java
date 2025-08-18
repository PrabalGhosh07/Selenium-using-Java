package Selenium;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	@DataProvider(name = "logInTestData")
    public Object[][] logInData() throws IOException {
        return Excelutils.getTestData("src/test/resources/RegisterData.xlsx", "LoginSheet");
    }

    @DataProvider(name = "registerData")
    public Object[][] registerData() throws IOException {
        return Excelutils.getTestData("src/test/resources/RegisterData.xlsx", "RegisterSheet");
    }

}
