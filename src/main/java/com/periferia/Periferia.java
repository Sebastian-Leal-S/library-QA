package com.periferia;

import com.epam.healenium.SelfHealingDriver;
import com.periferia.constantes.Navegador;
import com.periferia.driver_manager.SeleniumDriver;
import com.periferia.evidencia.GenerarEvidencia;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Librería interna Equipo Automatización para pruebas QA en Periferia IT Group.
 *
 * @author Periferia IT Group - Automation Equipment
 */
public class Periferia {

    protected static SelfHealingDriver driver;
    protected static final int TIMEOUT = 3;
    protected static Logger log = LogManager.getLogger(Periferia.class);

    private Periferia() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Obtiene el controlador de Selenium.
     *
     * @return El controlador de Selenium.
     */
    public static SelfHealingDriver getDriver() {
        return driver;
    }

    /**
     * Configura y inicializa el controlador de Selenium.
     *
     * @param navegador El navegador que se utilizará para la prueba.
     * @return El controlador de Selenium configurado y listo para usar.
     * @throws WebDriverException Si ocurre un error al inicializar el controlador de Selenium.
     */
    public static SelfHealingDriver setUp(Navegador navegador) {
        try {
            log.info("Inicializando el driver... con el navegador {}", navegador);
            driver = SeleniumDriver.initDriver(navegador);
            log.info("Driver inicializado correctamente");
        }catch (WebDriverException e){
            log.fatal("Error al inicializar el controlador: {}", e.getMessage());
            throw new WebDriverException("No se pudo inicializar el driver", e);
        }
        return driver;
    }

    /**
     * Configura y inicializa el controlador de Selenium con el navegador especificado y navega a la URL proporcionada.
     *
     * @param navegador El navegador que se utilizará para la prueba.
     * @param url       La URL a la que se desea navegar.
     * @return El controlador de Selenium configurado y listo para usar.
     * @throws WebDriverException Si ocurre un error al inicializar el controlador de Selenium.
     */
    public static SelfHealingDriver setUp(Navegador navegador, String url) {
        try {
            log.info("Inicializando el driver... con el navegador {}", navegador);
            driver = SeleniumDriver.initDriver(navegador);
            log.info("Driver inicializado correctamente");
            Periferia.goUrl(url);
        }catch (WebDriverException e){
            log.fatal("Error al inicializar el controlador: {}", e.getMessage());
            throw new WebDriverException("No se pudo inicializar el driver", e);
        }
        return driver;
    }

    /**
     * Configura y inicializa el controlador de Selenium con el navegador especificado.
     *
     * @param navegador El nombre del navegador que se utilizará para la prueba (por ejemplo, "CHROME", "FIREFOX" o "EDGE").
     * @return El controlador de Selenium configurado y listo para usar.
     * @throws IllegalArgumentException Si el nombre del navegador no es válido.
     * @throws WebDriverException Si ocurre un error al inicializar el controlador de Selenium.
     */
    public static SelfHealingDriver setUp(String navegador) {
        try {
            Navegador navegadorEmun = Navegador.valueOf(navegador.toUpperCase());

            log.info("Inicializando el driver... con el navegador {}", navegadorEmun);
            driver = SeleniumDriver.initDriver(navegadorEmun);
            log.info("Driver inicializado correctamente");
        }catch (IllegalArgumentException e){
            log.fatal("El navegador no es valido: {}", e.getMessage());
            throw new IllegalArgumentException("El navegador no es valido", e);
        }catch (WebDriverException e){
            log.fatal("Error al inicializar el controlador: {}", e.getMessage());
            throw new WebDriverException("No se pudo inicializar el driver", e);
        }
        return driver;
    }

    /**
     * Configura y inicializa el controlador de Selenium con el navegador especificado y navega a la URL proporcionada.
     *
     * @param navegador El nombre del navegador que se utilizará para la prueba (por ejemplo, "CHROME", "FIREFOX" o "EDGE").
     * @param url La URL a la que se desea navegar.
     * @return El controlador de Selenium configurado y listo para usar.
     * @throws IllegalArgumentException Si el nombre del navegador no es válido.
     * @throws WebDriverException Si ocurre un error al inicializar el controlador de Selenium.
     */
    public static SelfHealingDriver setUp(String navegador, String url) {
        try {
            Navegador navegadorEmun = Navegador.valueOf(navegador.toUpperCase());

            log.info("Inicializando el driver... con el navegador {}", navegadorEmun);
            driver = SeleniumDriver.initDriver(navegadorEmun);
            log.info("Driver inicializado correctamente");
            Periferia.goUrl(url);
        }catch (IllegalArgumentException e){
            log.fatal("El navegador no es valido: {}", e.getMessage());
            throw new IllegalArgumentException("El navegador no es valido", e);
        }catch (WebDriverException e){
            log.fatal("Error al inicializar el controlador: {}", e.getMessage());
            throw new WebDriverException("No se pudo inicializar el driver", e);
        }
        return driver;
    }

    /**
     * Cierra el controlador de Selenium si está inicializado.
     */
    public static void tearDown() {
        if (driver != null){
            driver.quit();
            log.info("Driver cerrado correctamente");
        }
    }

    /**
     * Navega a la URL proporcionada.
     *
     * @param url La URL a la que se desea navegar.
     * @throws WebDriverException Si ocurre un error al navegar a la URL.
     */
    public static void goUrl(String url) {
        try {
            driver.get(url);
            log.info("Navegando a la url: {}", url);
        }catch (WebDriverException e){
            log.fatal("Error al navegar a la url: {}", e.getMessage());
            throw new WebDriverException("No se pudo navegar a la url", e);
        }
    }


    /**
     * Encuentra un elemento web utilizando el localizador proporcionado.
     *
     * @param locator El localizador utilizado para encontrar el elemento web.
     * @return El elemento web encontrado.
     * @throws NoSuchElementException Si el elemento web no se encuentra en el tiempo de espera de 3 segundos.
     */
    public static WebElement findElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            log.debug("Elemento encontrado: {}", locator);
            return element;
        } catch (Exception e) {
            log.fatal("No se encontro el elemento: {}, dentro del tiempo de 3 seg. Falla {}", locator, e.getMessage());
            throw new NoSuchElementException("EL elemento no fue encontrado entre del tiempo de espera de 3 seg: " + locator);
        }
    }

    /**
     * Encuentra un elemento web utilizando el localizador proporcionado.
     *
     * @param locator El localizador utilizado para encontrar el elemento web.
     * @param tiempoEspera El tiempo de espera en segundos para encontrar el elemento web.
     * @return El elemento web encontrado.
     * @throws NoSuchElementException Si el elemento web no se encuentra en el tiempo de espera proporcionado.
     */
    public static WebElement findElement(By locator, int tiempoEspera) {
        WebDriverWait wait = new WebDriverWait(driver, tiempoEspera);
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            log.debug("Elemento encontrado: {}", locator);
            return element;
        } catch (Exception e) {
            log.fatal("No se encontro el elemento: {}, dentro del tiempo de {} seg. Falla {}", locator, tiempoEspera, e.getMessage());
            throw new NoSuchElementException("EL elemento "+ locator +" no fue encontrado entre del tiempo de espera de " + tiempoEspera + " seg");
        }
    }

    /**
     * Encuentra todos los elementos web que coinciden con el localizador proporcionado.
     *
     * @param locator El localizador utilizado para encontrar los elementos web.
     * @return Una lista de elementos web encontrados.
     * @throws NoSuchElementException Si los elementos web no se encuentran en el tiempo de espera de 3 segundos.
     */
    public static List<WebElement> findElements(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        try {
            List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            log.debug("Elementos encontrados: {}", locator);
            return elements;
        } catch (Exception e) {
            log.fatal("No se encontraron elementos: {}, dentro del tiempo de 3 seg. Falla {}", locator, e.getMessage());
            throw new NoSuchElementException("Los elementos no fueron encontrados entre del tiempo de espera de 3 seg: " + locator);
        }
    }

    /**
     * Encuentra todos los elementos web que coinciden con el localizador proporcionado.
     *
     * @param locator El localizador utilizado para encontrar los elementos web.
     * @param tiempoEspera El tiempo de espera en segundos para encontrar los elementos web.
     * @return Una lista de elementos web encontrados.
     * @throws NoSuchElementException Si los elementos web no se encuentran en el tiempo de espera proporcionado.
     */
    public static List<WebElement> findElements(By locator, int tiempoEspera) {
        WebDriverWait wait = new WebDriverWait(driver, tiempoEspera);
        try {
            List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            log.debug("Elementos encontrados: {}", locator);
            return elements;
        } catch (Exception e) {
            log.fatal("No se encontraron elementos: {}, dentro del tiempo de {} seg. Falla {}", locator, tiempoEspera, e.getMessage());
            throw new NoSuchElementException("Los elementos " + locator + " no fueron encontrados entre del tiempo de espera de " + tiempoEspera + " seg");
        }
    }

    /**
     * Realiza clic en el elemento web que coincide con el localizador proporcionado.
     *
     * @param locator El localizador utilizado para encontrar el elemento web.
     * @throws ElementNotInteractableException Si el elemento web no es interactuable en el tiempo de espera de 3 segundos.
     */
    public static void click(By locator) {
        try {
            WebElement element = findElement(locator);
            element.click();
            log.info("Click en el elemento: {}", locator);
        } catch (ElementNotInteractableException e) {
            log.error("No fue posible realizar clic sobre el elemento {}, en el tiempo 3 seg, por el error {}", locator, e.getMessage());
            throw e;
        }
    }

    /**
     * Realiza clic en el elemento web que coincide con el localizador proporcionado.
     *
     * @param locator El localizador utilizado para encontrar el elemento web.
     * @param tiempoEspera El tiempo de espera en segundos para encontrar el elemento web.
     * @throws ElementNotInteractableException Si el elemento web no es interactuable en el tiempo de espera proporcionado.
     */
    public static void click(By locator, int tiempoEspera) {
        try {
            WebElement element = findElement(locator, tiempoEspera);
            element.click();
            log.info("Click en el elemento: {}, dentro del tiempo {} seg", locator, tiempoEspera);
        } catch (ElementNotInteractableException e) {
            log.fatal("No fue posible realizar clic sobre el elemento {}, en el tiempo {}, por el error {}", locator, tiempoEspera, e.getMessage());
            throw e;
        }
    }

    /**
     * Realiza clic en el elemento web que coincide con el localizador proporcionado.
     *
     * @param locator El localizador utilizado para encontrar el elemento web.
     * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
     * @throws ElementNotInteractableException Si el elemento web no es interactuable en el tiempo de espera de 3 segundos.
     */
    public static void click(By locator, String mensajeEvidencia) {
        try {
            WebElement element = findElement(locator);
            element.click();
            GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
            log.info("Click en el elemento: {} y correcto guardo de evidencia", locator);
        } catch (Exception e) {
            GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
            log.fatal("No fue posible realizar clic sobre el elemento {}, en el tiempo 3 seg, por el error {}", locator, e.getMessage());
            throw e;
        }
    }

    /**
     * Realiza clic en el elemento web que coincide con el localizador proporcionado.
     *
     * @param locator El localizador utilizado para encontrar el elemento web.
     * @param tiempoEspera El tiempo de espera en segundos para encontrar el elemento web.
     * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
     * @throws ElementNotInteractableException Si el elemento web no es interactuable en el tiempo de espera proporcionado.
     */
    public static void click(By locator, int tiempoEspera, String mensajeEvidencia) {
        try {
            WebElement element = findElement(locator, tiempoEspera);
            element.click();
            GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
            log.info("Click en el elemento: {}, dentro del tiempo {} seg y correcto guardo de evidencia", locator, tiempoEspera);
        } catch (Exception e) {
            GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
            log.fatal("No fue posible realizar clic sobre el elemento {}, en el tiempo {}, por el error {}", locator, tiempoEspera, e.getMessage());
            throw e;
        }
    }

    /**
     * Envía texto al elemento web que coincide con el localizador proporcionado.
     *
     * @param locator El localizador utilizado para encontrar el elemento web.
     * @param texto El texto que se desea enviar al elemento web.
     * @throws ElementNotInteractableException Si el elemento web no es interactuable en el tiempo de espera de 3 segundos.
     */
    public static void sendKeys(By locator, String texto) {
        try {
            WebElement element = findElement(locator);
            element.sendKeys(texto);
            log.info("Envio de texto '{}' sobre el elemento: {}", texto, locator);
        } catch (Exception e) {
            log.fatal("No fue posible realizar el envio de texto sobre el elemento {}, en el tiempo 3 seg, por el error {}", locator, e.getMessage());
            throw e;
        }
    }

    /**
     * Envía texto al elemento web que coincide con el localizador proporcionado.
     *
     * @param locator El localizador utilizado para encontrar el elemento web.
     * @param texto El texto que se desea enviar al elemento web.
     * @param tiempoEspera El tiempo de espera en segundos para encontrar el elemento web.
     * @throws ElementNotInteractableException Si el elemento web no es interactuable en el tiempo de espera proporcionado.
     */
    public static void sendKeys(By locator, String texto, int tiempoEspera) {
        try {
            WebElement element = findElement(locator, tiempoEspera);
            element.sendKeys(texto);
            log.info("Envio de texto '{}' sobre el elemento: {}, dentro del tiempo {} seg", texto, locator, tiempoEspera);
        } catch (Exception e) {
            log.fatal("No fue posible realizar el envio de texto sobre el elemento {}, en el tiempo {}, por el error {}", locator, tiempoEspera, e.getMessage());
            throw e;
        }
    }

    /**
     * Envía texto al elemento web que coincide con el localizador proporcionado.
     *
     * @param locator El localizador utilizado para encontrar el elemento web.
     * @param texto El texto que se desea enviar al elemento web.
     * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
     * @throws ElementNotInteractableException Si el elemento web no es interactuable en el tiempo de espera de 3 segundos.
     */
    public static void sendKeys(By locator, String texto, String mensajeEvidencia) {
        try {
            WebElement element = findElement(locator);
            element.sendKeys(texto);
            GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
            log.info("Envio de texto '{}' sobre el elemento: {} y correcto guardo de evidencia", texto, locator);
        } catch (Exception e) {
            GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
            log.fatal("No fue posible realizar el envio de texto sobre el elemento {}, en el tiempo 3 seg, por el error {}", locator, e.getMessage());
            throw e;
        }
    }

    /**
     * Envía texto al elemento web que coincide con el localizador proporcionado.
     *
     * @param locator El localizador utilizado para encontrar el elemento web.
     * @param texto El texto que se desea enviar al elemento web.
     * @param tiempoEspera El tiempo de espera en segundos para encontrar el elemento web.
     * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
     * @throws ElementNotInteractableException Si el elemento web no es interactuable en el tiempo de espera proporcionado.
     */
    public static void sendKeys(By locator, String texto, int tiempoEspera, String mensajeEvidencia) {
        try {
            WebElement element = findElement(locator, tiempoEspera);
            element.sendKeys(texto);
            GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
            log.info("Envio de texto '{}' sobre el elemento: {}, dentro del tiempo {} seg y correcto guardo de evidencia", texto, locator, tiempoEspera);
        } catch (Exception e) {
            GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
            log.fatal("No fue posible realizar el envio de texto sobre el elemento {}, en el tiempo {}, por el error {}", locator, tiempoEspera, e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene el texto del elemento web que coincide con el localizador proporcionado.
     *
     * @param locator El localizador utilizado para encontrar el elemento web.
     * @return El texto del elemento web.
     * @throws NoSuchElementException Si el texto del elemento web no se encuentra en el tiempo de espera de 3 segundos.
     */
    public static String getText(By locator) {
        try {
            WebElement element = findElement(locator);
            String text = element.getText();
            log.info("Se obtuvo el texto '{}' sobre el elemento: {}", text, locator);
            return text;
        } catch (Exception e) {
            log.fatal("No fue posible obtener el texto sobre el elemento {}, en el tiempo 3 seg, por el error {}", locator, e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene el texto del elemento web que coincide con el localizador proporcionado.
     *
     * @param locator      El localizador utilizado para encontrar el elemento web.
     * @param tiempoEspera El tiempo de espera en segundos para encontrar el elemento web.
     * @return El texto del elemento web.
     * @throws NoSuchElementException Si el texto del elemento web no se encuentra en el tiempo de espera proporcionado.
     */
    public static String getText(By locator, int tiempoEspera) {
        try {
            WebElement element = findElement(locator, tiempoEspera);
            String text = element.getText();
            log.info("Se obtuvi texto sobre el elemento: {}, dentro del tiempo {} seg", locator, tiempoEspera);
            return text;
        } catch (Exception e) {
            log.fatal("No fue posible obtener el texto sobre el elemento {}, en el tiempo {}, por el error {}", locator, tiempoEspera, e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene el texto del elemento web que coincide con el localizador proporcionado.
     *
     * @param locator          El localizador utilizado para encontrar el elemento web.
     * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
     * @return El texto del elemento web.
     * @throws NoSuchElementException Si el texto del elemento web no se encuentra en el tiempo de espera de 3 segundos.
     */
    public static String getText(By locator, String mensajeEvidencia) {
        try {
            WebElement element = findElement(locator);
            String text = element.getText();
            GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
            log.info("Obtener texto sobre el elemento: {} y correcto guardo de evidencia", locator);
            return text;
        } catch (Exception e) {
            GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
            log.fatal("No fue posible obtener el texto sobre el elemento {}, en el tiempo 3 seg, por el error {}", locator, e.getMessage());
            throw e;
        }
    }

    /**
     * Obtiene el texto del elemento web que coincide con el localizador proporcionado.
     *
     * @param locator          El localizador utilizado para encontrar el elemento web.
     * @param tiempoEspera     El tiempo de espera en segundos para encontrar el elemento web.
     * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
     * @return El texto del elemento web.
     * @throws NoSuchElementException Si el texto del elemento web no se encuentra en el tiempo de espera proporcionado.
     */
    public static String getText(By locator, int tiempoEspera, String mensajeEvidencia) {
        try {
            WebElement element = findElement(locator, tiempoEspera);
            String text = element.getText();
            GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
            log.info("Obtener texto sobre el elemento: {}, dentro del tiempo {} seg y correcto guardo de evidencia", locator, tiempoEspera);
            return text;
        } catch (Exception e) {
            GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
            log.fatal("No fue posible obtener el texto sobre el elemento {}, en el tiempo {}, por el error {}", locator, tiempoEspera, e.getMessage());
            throw e;
        }
    }

    /**
     * Limpia el contenido del campo identificado por el locator especificado.
     *
     * @param locator El locator del elemento cuyo campo se desea limpiar.
     * @throws Exception Si no es posible limpiar el campo del elemento.
     */
    public static void clear(By locator) {
        try {
            WebElement element = findElement(locator);
            element.clear();
            log.info("Limpiar campo sobre el elemento: {}", locator);
        } catch (Exception e) {
            log.fatal("No fue posible limpiar el campo sobre el elemento {}, en el tiempo 3 seg, por el error {}", locator, e.getMessage());
            throw e;
        }
    }

    /**
     * Limpia el contenido del campo identificado por el locator especificado.
     *
     * @param locator El locator del elemento cuyo campo se desea limpiar.
     * @param tiempoEspera El tiempo de espera en segundos para encontrar el elemento web.
     * @throws Exception Si no es posible limpiar el campo del elemento.
     */
    public static void clear(By locator, int tiempoEspera) {
        try {
            WebElement element = findElement(locator, tiempoEspera);
            element.clear();
            log.info("Limpiar campo sobre el elemento: {}, dentro del tiempo {} seg", locator, tiempoEspera);
        } catch (Exception e) {
            log.fatal("No fue posible limpiar el campo sobre el elemento {}, en el tiempo {}, por el error {}", locator, tiempoEspera, e.getMessage());
            throw e;
        }
    }

    /**
     * Limpia el contenido del campo identificado por el locator especificado.
     *
     * @param locator El locator del elemento cuyo campo se desea limpiar.
     * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
     * @throws Exception Si no es posible limpiar el campo del elemento.
     */
    public static void clear(By locator, String mensajeEvidencia) {
        try {
            WebElement element = findElement(locator);
            element.clear();
            GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
            log.info("Limpiar campo sobre el elemento: {} y correcto guardo de evidencia", locator);
        } catch (Exception e) {
            GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
            log.fatal("No fue posible limpiar el campo sobre el elemento {}, en el tiempo 3 seg, por el error {}", locator, e.getMessage());
            throw e;
        }
    }

    /**
     * Limpia el contenido del campo identificado por el locator especificado.
     *
     * @param locator El locator del elemento cuyo campo se desea limpiar.
     * @param tiempoEspera El tiempo de espera en segundos para encontrar el elemento web.
     * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
     * @throws Exception Si no es posible limpiar el campo del elemento.
     */
    public static void clear(By locator, int tiempoEspera, String mensajeEvidencia) {
        try {
            WebElement element = findElement(locator, tiempoEspera);
            element.clear();
            GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
            log.info("Limpiar campo sobre el elemento: {}, dentro del tiempo {} seg y correcto guardo de evidencia", locator, tiempoEspera);
        } catch (Exception e) {
            GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
            log.fatal("No fue posible limpiar el campo sobre el elemento {}, en el tiempo {}, por el error {}", locator, tiempoEspera, e.getMessage());
            throw e;
        }
    }

    public static String obtenerTitulo() {
        try {
            return driver.getTitle();
        } catch (Exception e) {
            log.fatal("No fue posible obtener el titulo de la pagina, por el error {}", e.getMessage());
            throw new IllegalStateException("No se pudo obtener el título de la página.", e);
        }
    }

    public static String obtenerUrlActual() {
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            log.fatal("No fue posible obtener la url actual de la pagina, por el error {}", e.getMessage());
            throw new IllegalStateException("No se pudo obtener la URL actual de la página.", e);
        }
    }

    public static void paginaAtras() {
        try {
            driver.navigate().back();
        } catch (Exception e) {
            log.fatal("No fue posible navegar a la pagina anterior, por el error {}", e.getMessage());
            throw new IllegalStateException("No se pudo navegar a la página anterior.", e);
        }
    }

    public static void paginaAdelante() {
        try {
            driver.navigate().forward();
        } catch (Exception e) {
            log.fatal("No fue posible navegar a la pagina siguiente, por el error {}", e.getMessage());
            throw new IllegalStateException("No se pudo navegar a la página siguiente.", e);
        }
    }

    public static void actualizarPagina() {
        try {
            driver.navigate().refresh();
        } catch (Exception e) {
            log.fatal("No fue posible actualizar la pagina, por el error {}", e.getMessage());
            throw new IllegalStateException("No se pudo actualizar la página.", e);
        }
    }

    public static void cambiarFrame(String frameID) {
        try {
            driver.switchTo().frame(frameID);
        } catch (Exception e) {
            log.fatal("No fue posible cambiar de frame, por el error {}", e.getMessage());
            throw new IllegalStateException("No se pudo cambiar de frame.", e);
        }
    }

    public static void cambiarFrame(int index) {
        try {
            driver.switchTo().frame(index);
        } catch (Exception e) {
            log.fatal("No fue posible cambiar de frame, por el error {}", e.getMessage());
            throw new IllegalStateException("No se pudo cambiar de frame.", e);
        }
    }

    public static void cambiarFrame(By nombreFrame) {
        try {
            driver.switchTo().frame(findElement(nombreFrame));
        } catch (Exception e) {
            log.fatal("No fue posible cambiar de frame, por el error {}", e.getMessage());
            throw new IllegalStateException("No se pudo cambiar de frame.", e);
        }
    }

    public static void salirFrame() {
        try {
            driver.switchTo().parentFrame();
        } catch (Exception e) {
            log.fatal("No fue posible salir del frame, por el error {}", e.getMessage());
            throw new IllegalStateException("No se pudo salir del frame.", e);
        }
    }

    public static void printConsole(Object message){
        System.out.println(message);
    }
}
