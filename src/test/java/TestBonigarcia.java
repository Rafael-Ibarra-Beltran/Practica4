import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestBonigarcia {

    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testWebForm() {
        // 1. Abrir la página web
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        System.out.println("1. Página web abierta correctamente.");

        // 2. Ingresar información en los campos text input, password y textarea
        WebElement textInput = driver.findElement(By.id("my-text-id"));
        textInput.sendKeys("Automatización con TestNG");

        WebElement passwordInput = driver.findElement(By.name("my-password"));
        passwordInput.sendKeys("PasswordSeguro123");

        WebElement textarea = driver.findElement(By.name("my-textarea"));
        textarea.sendKeys("Este es un texto ingresado para la práctica 4 usando Maven y TestNG.");
        System.out.println("2. Información ingresada en Text input, Password y Textarea.");

        // 3. Seleccionar una opción en Dropdown (select)
        Select dropdown = new Select(driver.findElement(By.name("my-select")));
        dropdown.selectByVisibleText("Two");
        System.out.println("3. Opción seleccionada en el Dropdown.");

        // 4. Interactuar con Checkbox, Radio button y Date picker
        WebElement checkbox = driver.findElement(By.id("my-check-2"));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }

        WebElement radioButton = driver.findElement(By.id("my-radio-2"));
        radioButton.click();

        WebElement datePicker = driver.findElement(By.name("my-date"));
        datePicker.sendKeys("10/25/2023");
        System.out.println("4. Interacción completada con Checkbox, Radio button y Date picker.");

        // 5. Presionar el botón Submit
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();
        System.out.println("5. Botón Submit presionado. Esperando respuesta...");

        // 6. Validar que la acción se haya ejecutado correctamente
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Esperamos a que la URL contenga "submitted-form" para asegurar que cargó la nueva página
        wait.until(ExpectedConditions.urlContains("submitted-form"));

        // Buscamos cualquier elemento en la página que contenga el texto de confirmación "Received!"
        WebElement successMessage = driver.findElement(By.xpath("//*[contains(text(), 'Received!')]"));
        String messageText = successMessage.getText();

        if (messageText.contains("Received!")) {
            System.out.println("6. VALIDACIÓN EXITOSA: El formulario se envió correctamente. Mensaje en pantalla: " + messageText);
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