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

    private void compartilhar() {
        driver.get("https://www.facebook.com/story.php?story_fbid=151392173553983&id=101008055259062");
        sleep(5);
        WebElement element = driver.findElement(By.xpath("//div[@aria-label='Envie isso para amigos ou publique na sua linha do tempo']"));

        element.click();
        sleep(5);
        WebElement element2 = driver.findElement(By.xpath("//*[@class=\"o8rfisnq j83agx80 cbu4d94t tvfksri0 aov4n071 bi6gxh9e l9j0dhe7\"]"));
        element2.click();
        sleep(5);
//        WebElement element3 = driver.findElement(By.xpath("//*[@class=\"hu5pjgll lzf7d6o1 sp_nfy-8GYyygN sx_ec566c\"]"));
        WebElement element3 = driver.findElement(By.xpath("//*[@class=\"hu5pjgll lzf7d6o1 sp_rngbGqpmQgW sx_00f5db\"]"));
        println("VAI" + element3.getText());
        element3.click();
        sleep(5);
        WebElement element4 = driver.findElement(By.xpath("//*[@class=\"d2edcug0 hpfvmrgz qv66sw1b c1et5uql b0tq1wua a8c37x1j keod5gw0 nxhoafnm aigsh9s9 d9wwppkn fe6kdd0r mau55g9w c8b282yb hrzyx87i jq4qci2q a3bd9o3v ekzkrbhg oo9gr5id hzawbc8m\"]"));
        element4.click();

//  escolher qual grupo compartilhar

        WebElement wePublicacoes = driver.findElement(By.xpath("//*[@class=\"rq0escxv l9j0dhe7 du4w35lb j83agx80 cbu4d94t buofh1pr tgvbjcpo muag1w35 enqfppq2\"]"));
        List<WebElement> elements = wePublicacoes.findElements(By.tagName("div"));

        System.out.println("wePublicacoes"+wePublicacoes.getSize());
        System.out.println("ELEMENTES"+elements.size());


        //Envie isso para amigos ou publique na sua linha do tempo
    }
    private void inicializarNavegador() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("headless");
        System.setProperty("webdriver.chrome.driver", "/opt/chromedriver");
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

        info("GRUPOS DA CONTA:");
        recursos.stream().forEach(e -> info(e.toString()));

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
            for (; indexElement < elementsSize && controladorLoopService.canContinuarLoopElementos(); indexElement++) {
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
