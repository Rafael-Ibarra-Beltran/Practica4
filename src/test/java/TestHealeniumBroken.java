import com.epam.healenium.SelfHealingDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestHealeniumBroken {

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
    public void testBrokenLocator() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        System.out.println("1. Página web abierta.");

        // 1. Inyectamos JavaScript para simular un cambio en la interfaz (modificamos el ID original)
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('my-text-id').setAttribute('id', 'my-text-id-modificado');");
        System.out.println("2. ID del elemento modificado en el DOM a 'my-text-id-modificado'.");

        // 2. Interacción usando el locator original (interviene en esta parte).
        System.out.println("3. Intentando interactuar con el locator original: By.id(\"my-text-id\")...");
        WebElement textInput = driver.findElement(By.id("my-text-id"));
        textInput.sendKeys("Texto recuperado por Healenium");

        // 3. Mensaje de validación profesional
        System.out.println("4. RESULTADO EXITOSO: Se detectó el cambio, se aplicó self-healing correctamente y logró ingresar el texto.");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            System.out.println("5. Cerrando el navegador y finalizando la prueba.");
            driver.quit();
        }
    }
}