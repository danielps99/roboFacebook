package app;

import dto.ContaFacebook;
import dto.Recurso;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import service.ControladorLoopService;
import service.ReaderJsonService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App implements ICommons {

    private ChromeDriver driver;
    private ControladorLoopService controladorLoopService;

    public App() {
        controladorLoopService = ControladorLoopService.getInstance();
    }

    public void start() {
        ContaFacebook contaFacebook = ReaderJsonService.buscarContaFacebook();
        inicializarNavegador();
        logar(contaFacebook);
        percorrerRecursos(contaFacebook.getRecursos());
    }

    private void inicializarNavegador() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver(options);
    }

    public void logar(ContaFacebook conta) {
        info("Conta escolhida: " + conta.getEmail());
        driver.get("https://www.facebook.com/");
        driver.findElement(By.id("email")).sendKeys(conta.getEmail());
        sleep(2);
        driver.findElement(By.id("pass")).sendKeys(conta.getPasswd());
        sleep(2);
        driver.findElement(By.tagName("button")).click();
        sleep(20);
    }

    private void percorrerRecursos(List<Recurso> recursos) {
        for (Recurso recurso : recursos) {
            controladorLoopService.inicializarVariaveis(recurso);
            info(recurso.toString());
            driver.get(recurso.getUrl());
            percorrerECurtir(recurso);
        }
        println("FINALIZOU");
    }

    private void percorrerECurtir(Recurso recurso) {
        sleep(30);
        driver.executeScript("window.scrollTo(0, 2000);");

        Publicacao publicacao = new Publicacao();
        int indexElement = 0;
        while (controladorLoopService.canContinuarLoop()) {
            driver.executeScript("window.scrollTo(0, " + controladorLoopService.getPosicaoAtual() + ");");
            sleep(5);
            WebElement wePublicacoes = driver.findElement(By.xpath(recurso.getXpath()));
            List<WebElement> elements = wePublicacoes.findElements(By.tagName("div"));
            int elementsSize = elements.size();
            for (; indexElement < elementsSize; indexElement++) {
                WebElement webElement = elements.get(indexElement);
                try {
                    controladorLoopService.atualizarPosicaoAtual(webElement);
                    publicacao.adicionarTexto(webElement);
                } catch (Exception e) {
                    error(e);
                }

                if (publicacao.canCurtir()) {
                    sleep(20);
                    try {
                        webElement.click();
                        publicacao.imprimetexto();
                        sleep(2);
                    } catch (Exception e) {
                        error(e);
                    }
                }
            }
        }
    }
}