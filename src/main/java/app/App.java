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
import java.util.Scanner;

public class App implements ICommons {

    private ChromeDriver driver;
    private ControladorLoopService controladorLoopService;
    String xpathPostsPagina = "";

    public App() {
        controladorLoopService = ControladorLoopService.getInstance();
    }

    private void inicializarNavegador() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver(options);
    }

    public void start() {
        List<ContaFacebook> contas = ReaderJsonService.buscarUsuario();
        println("Escolha uma conta pelo numero:");
        for (int i = 0; i < contas.size(); i++) {
            println(i + ": " + contas.get(i).getEmail());
        }

        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        ContaFacebook contaFacebook = contas.get(index);
        println("Conta escolhida: " + contaFacebook.getEmail());

        inicializarNavegador();
        logar(contaFacebook);
        percorrerRecursos(contaFacebook.getRecursos());

        System.out.println(contaFacebook.getRecursos().size());
    }

    public void logar(ContaFacebook conta) {
        driver.get("https://www.facebook.com/");
        driver.findElement(By.id("email")).sendKeys(conta.getEmail());
        sleep(2);
        driver.findElement(By.id("pass")).sendKeys(conta.getPasswd());
        sleep(2);
        driver.findElement(By.tagName("button")).click();
        sleep(20);

        println("FINALIZOU curtidas do perfil");
    }

    private void percorrerRecursos(List<Recurso> recursos) {
        xpathPostsPagina = Constantes.xpathMainPaginaGrupos;
//        for (String urlGrupo : Constantes.urlsGruposPercorrer) {
        for (Recurso recurso : recursos) {
            controladorLoopService.inicializarVariaveis();
            info(recurso.toString());
            driver.get(recurso.getUrl());
            percorrerECurtir();
        }

        println("FINALIZOU GRUPOS");

//        driver.get("https://www.facebook.com/");
//        xpathPostsPagina = Constantes.xpathMainPerfil;
//        controladorLoopService.inicializarVariaveis(530000, 10);
//        percorrerECurtir();
    }

    private void percorrerECurtir() {
        sleep(30);
        driver.executeScript("window.scrollTo(0, 2000);");

        Publicacao publicacao = new Publicacao();
        int indexElement = 0;
        while (controladorLoopService.canContinuarLoop()) {
            driver.executeScript("window.scrollTo(0, " + controladorLoopService.getPosicaoAtual() + ");");
            sleep(5);
            WebElement wePublicacoes = buscarDivPublicacoes();
            List<WebElement> elements = wePublicacoes.findElements(By.tagName("div"));
            int elementsSize = elements.size();
            for (; indexElement < elementsSize; indexElement++) {
                WebElement webElement = elements.get(indexElement);
                try {
                    controladorLoopService.atualizarPosicaoAtual(webElement);
                    publicacao.adicionarTexto(webElement);
                } catch (StaleElementReferenceException e) {
                    println("StaleElementReferenceException error");
                }

                if (publicacao.canCurtir()) {
                    sleep(20);
                    try {
                        webElement.click();
                        publicacao.imprimetexto();
                        sleep(2);
                    } catch (StaleElementReferenceException e) {
                        println("StaleElementReferenceException on webElement.click");
                    } catch (Exception e) {
                        error(e);
                    }
                }
            }
        }
    }

    private WebElement buscarDivPublicacoes() {
//        return driver.findElement(By.xpath(Constantes.xpathMainPerfil));
//        return driver.findElement(By.xpath(Constantes.xpathMainPaginaCompartilhandoConhecimento));
        return driver.findElement(By.xpath(xpathPostsPagina));
    }
}