package pagesObjects;

import com.epam.healenium.SelfHealingDriver;
import com.periferiaitgroup.Periferia;
import mapsObjects.TestMap;
import org.testng.Assert;

public class TestPage extends TestMap {

    public TestPage(SelfHealingDriver driver) {
        super(driver);
    }

    public void completeTest(String parameterOne) {
        Periferia.click(buttonColor, "Clic para cambiar color");
        Periferia.sendKeys(inputTest, parameterOne, "Envio de texto");
        Periferia.click(buttonText);

        Assert.assertEquals(parameterOne, Periferia.getText(textTest));
        Periferia.clear(inputTest);

        Periferia.sendKeys(inputTest, "Texto con erroes ortograficos");
        Periferia.click(buttonText, "Clic para cambiar texto");

        Periferia.revisarOrtografia(textTest);

        Periferia.sendKeys(inputTest, "Texto enviado");
        Periferia.click(buttonColor);
        Periferia.capturarEvidencia("Mensaje para generar evidencia");

        Periferia.isSelected(buttonColor);
    }

    public void clickTest() {
        Periferia.click(buttonColor);

        Periferia.click(buttonColor, 5);

        Periferia.click(buttonColor, "Click en el boton test.");

        Periferia.click(buttonColor, 5, "Click en el boton test, dentro del tiempo de espera proporcionado.");
    }

    public void sendKeyTest() {
        Periferia.sendKeys(inputTest, "Texto a enviar");

        Periferia.sendKeys(inputTest, "Texto a enviar", 5);

        Periferia.sendKeys(inputTest, "Texto a enviar", "Envio de texto");

        Periferia.sendKeys(inputTest, "Texto a enviar", 5, "Envio de texto");
    }

    public void getTextTest() {
        Periferia.printConsole("Texto_1: " + Periferia.getText(textTest));

        Periferia.printConsole("Texto_2: " + Periferia.getText(textTest, 5));
        Periferia.printConsole("Texto_3: " + Periferia.getText(textTest, "Obtener texto"));

        Periferia.printConsole("Texto_4: " + Periferia.getText(textTest, 5, "Obtener texto"));
    }

    public void clearTest() {
        Periferia.clear(inputTest);

        Periferia.clear(inputTest, 5);

        Periferia.clear(inputTest, "Limpiar campo");

        Periferia.clear(inputTest, 5, "Limpiar campo");
    }
}
