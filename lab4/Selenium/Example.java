import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

import junit.framework.*;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;
import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class Example  extends TestCase{
    public class User{
        private String username;
        private String password;
        
        User(String username, String password){
            this.username = username;
            this.password = password;
        }
    }
    
    protected static ArrayList<User> USERS;
    protected static ArrayList<User> CASE_USERS;
    protected static ArrayList<User> CASE_PASSWORDS;
    protected static ArrayList<String> TEMP_IN_FARENHEIT;
    
    public static void main(String[] args) {
        
        String[] testCaseName = { Example.class.getName() };
        // junit.swingui.TestRunner.main(testCaseName);
        junit.textui.TestRunner.main(testCaseName);
        
    }
    
    @Before
     public void setUp(){
        USERS = new ArrayList<User>();
        USERS.add(new User("andy", "apple"));
        USERS.add(new User("bob", "bathtub"));
        USERS.add(new User("charley", "china"));
        
        CASE_USERS = new ArrayList<User>();
        CASE_USERS.add(new User("Andy", "apple"));
        CASE_USERS.add(new User("BoB", "bathtub"));
        CASE_USERS.add(new User("CHarley", "china"));
        
        CASE_PASSWORDS = new ArrayList<User>();
        CASE_PASSWORDS.add(new User("andy", "ApPLE"));
        CASE_PASSWORDS.add(new User("bob", "baThTub"));
        CASE_PASSWORDS.add(new User("charley", "CHiNa"));
        
        TEMP_IN_FARENHEIT = new ArrayList();
        TEMP_IN_FARENHEIT.add("0");
        TEMP_IN_FARENHEIT.add("50");
        TEMP_IN_FARENHEIT.add("75");
        TEMP_IN_FARENHEIT.add("90");
        TEMP_IN_FARENHEIT.add("100");
        TEMP_IN_FARENHEIT.add("150");
        TEMP_IN_FARENHEIT.add("200");
        TEMP_IN_FARENHEIT.add("250");
        TEMP_IN_FARENHEIT.add("300");
    }

    @Test
    public void testValidUsers() throws InterruptedException{
		System.out.println("Testing with correct usernammes and passwords");
        WebDriver driver = new HtmlUnitDriver();
        Thread.sleep(10000);
        for(User user : USERS){
            driver.get("http://apt-public.appspot.com/testing-lab-login.html");
            WebElement userNameField = driver.findElement(By.name("userId"));
            userNameField.clear();
            userNameField.sendKeys(user.username);
            WebElement passwordField = driver.findElement(By.name("userPassword"));
            passwordField.clear();
            passwordField.sendKeys(user.password);
            System.out.println("Credentials entered for username/password: " + user.username + "/" + user.password);
            passwordField.submit();
            try{
                Thread.sleep(500);
            }catch(InterruptedException e){
                System.out.println("Exception happens...");
            }
            
            assertTrue(driver.getTitle().contains("Online temperature conversion"));
        }
        System.out.println("All testcases with correct usernames and passwords passed!\n");
    }

    @Test
    public void testUsernameCaseValidUsers() throws InterruptedException{
		System.out.println("Testing if usernames are case insensitive");
        WebDriver driver = new HtmlUnitDriver();
        Thread.sleep(10000);
        for(User user : CASE_USERS){
            driver.get("http://apt-public.appspot.com/testing-lab-login.html");
            WebElement userNameField = driver.findElement(By.name("userId"));
            userNameField.clear();
            userNameField.sendKeys(user.username);
            WebElement passwordField = driver.findElement(By.name("userPassword"));
            passwordField.clear();
            passwordField.sendKeys(user.password);
            System.out.println("Credentials entered for username/password: " + user.username + "/" + user.password);
            passwordField.submit();

            Thread.sleep(500);


            assertTrue(driver.getTitle().contains("Online temperature conversion"));
        
        }
        System.out.println("All testcases with usernames(with incorrect case) passed!\n");
    }
    
    @Test
    public void testPasswordCaseValidUsers() throws InterruptedException{
		System.out.println("Testing if passwords are case sensitive");
        WebDriver driver = new HtmlUnitDriver();
        Thread.sleep(10000);
        for(User user : CASE_PASSWORDS){
            driver.get("http://apt-public.appspot.com/testing-lab-login.html");
            WebElement userNameField = driver.findElement(By.name("userId"));
            userNameField.clear();
            userNameField.sendKeys(user.username);
            WebElement passwordField = driver.findElement(By.name("userPassword"));
            passwordField.clear();
            passwordField.sendKeys(user.password);
            System.out.println("Credentials entered for username/password: " + user.username + "/" + user.password);
            passwordField.submit();

            Thread.sleep(500);

            assertFalse(driver.getTitle().contains("Online temperature conversion"));
        
        }
        System.out.println("All testcases with incorrect password were blocked. Testcases passed!\n");
    }

    @Test
    public void testPrecision() throws InterruptedException{
		System.out.println("Testing temperature conversion for two places of precision");
        WebDriver driver = new HtmlUnitDriver();
        Thread.sleep(10000);
        for(String temp : TEMP_IN_FARENHEIT){
            driver.get("http://apt-public.appspot.com/testing-lab-calculator.html");
            WebElement element = driver.findElement(By.name("farenheitTemperature"));
            element.clear();
            element.sendKeys(temp);
            element.submit();
            
            Thread.sleep(500);
            List<WebElement>  lines = driver.findElements(By.xpath("//*[contains(text(), 'Celsius')]"));
            String test = driver.findElement(By.cssSelector("h2")).getText();
            
            String[] array = test.split(" ");
            double result = Double.parseDouble(array[3]);
            double temp_in_f = Double.parseDouble(temp);
            double temp_in_c = (temp_in_f-32)*5/9;
            
            DecimalFormat df;
            if (temp_in_f>= 0 && temp_in_f<=212)
                df = new DecimalFormat("#.##");
            else
                df = new DecimalFormat("#.#");
            
            System.out.println("Expected Temperature: " +df.format(temp_in_c)+" / Result: "+df.format(result));
            assertEquals(df.format(temp_in_c),df.format(result));
            
        }
        System.out.println("All temperature precision testcases passed!\n");
    }
}
