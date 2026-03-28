import com.epam.healenium.SelfHealingDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestHealenium {

    private SelfHealingDriver driver;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        WebDriver delegate = new ChromeDriver(options);

        driver = SelfHealingDriver.create(delegate);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testWebFormHealenium() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        System.out.println("1. Página web abierta correctamente con Healenium.");

        driver.findElement(By.id("my-text-id")).sendKeys("Prueba con Healenium");
        driver.findElement(By.name("my-password")).sendKeys("PasswordSeguro123");
        driver.findElement(By.name("my-textarea")).sendKeys("Primera ejecución para registrar localizadores.");
        System.out.println("2. Información ingresada.");

        Select dropdown = new Select(driver.findElement(By.name("my-select")));
        dropdown.selectByVisibleText("Two");
        System.out.println("3. Opción seleccionada en dropdown.");

        WebElement checkbox = driver.findElement(By.id("my-check-2"));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }

        driver.findElement(By.id("my-radio-2")).click();

        WebElement datePicker = driver.findElement(By.name("my-date"));
        datePicker.sendKeys("10/25/2023");
        System.out.println("4. Interacción completada con checkbox, radio y date picker.");

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        System.out.println("5. Botón submit presionado.");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("submitted-form"));

        WebElement successMessage = driver.findElement(By.xpath("//*[contains(text(), 'Received!')]"));
        String messageText = successMessage.getText();

        if (messageText.contains("Received!")) {
            System.out.println("6. VALIDACIÓN EXITOSA: El formulario se envió correctamente.");
        } else {
            System.out.println("6. ERROR: El formulario no se validó correctamente.");
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}