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
        alertarFim();
    }

    private void compartilhar() {
        sleep(2);
//        driver.get("https://www.facebook.com/story.php?story_fbid=151392173553983&id=101008055259062");
        driver.get("https://www.facebook.com/story.php?story_fbid=138714348155099&id=101008055259062");
        sleep(5);
        WebElement element = driver.findElement(By.xpath("//div[@aria-label='Envie isso para amigos ou publique na sua linha do tempo']"));
        System.out.println(isClicavel(element));
        element.click();
        sleep(5);
        WebElement element2 = driver.findElement(By.xpath("//*[@class=\"o8rfisnq j83agx80 cbu4d94t tvfksri0 aov4n071 bi6gxh9e l9j0dhe7\"]"));
        System.out.println(isClicavel(element2));
        element2.click();
        sleep(5);
//        WebElement element3 = driver.findElement(By.xpath("//*[@class=\"hu5pjgll lzf7d6o1 sp_nfy-8GYyygN sx_ec566c\"]"));
//        WebElement element3 = driver.findElement(By.xpath("//*[@class=\"hu5pjgll lzf7d6o1 sp_rngbGqpmQgW sx_00f5db\"]"));

//        WebElement element3 = driver.findElement(By.xpath("//*[@class=\"hu5pjgll lzf7d6o1 sp_kYH09d4KWTa sx_61f2b2\"]"));
//        WebElement element3 = driver.findElement(By.xpath("//*[@class=\"hu5pjgll lzf7d6o1\"]"));
//        WebElement element3 = driver.findElement(By.xpath("//*[@id=\"jsc_c_2x\"]"));

        WebElement element3 = driver.findElement(By.xpath("//label[@aria-label='Compartilhar como']"));
//        while (!isClicavel(element3)){
//            System.out.println("nao é clicavel");
//            element3 = element2;
//        }

        System.out.println(isClicavel(element3));
        element3.click();
//        element3.is
        sleep(5);
//        WebElement element4 = driver.findElement(By.xpath("//*[@class=\"d2edcug0 hpfvmrgz qv66sw1b c1et5uql b0tq1wua a8c37x1j keod5gw0 nxhoafnm aigsh9s9 d9wwppkn fe6kdd0r mau55g9w c8b282yb hrzyx87i jq4qci2q a3bd9o3v ekzkrbhg oo9gr5id hzawbc8m\"]"));
        WebElement element4 = driver.findElement(By.xpath("//*[@class=\"oajrlxb2 g5ia77u1 qu0x051f esr5mh6w e9989ue4 r7d6kgcz rq0escxv nhd2j8a9 j83agx80 p7hjln8o kvgmc6g5 oi9244e8 oygrvhab h676nmdw cxgpxx05 dflh9lhu sj5x9vvc scb9dxdr i1ao9s8h esuyzwwr f1sip0of lzcic4wl l9j0dhe7 abiwlrkh p8dawk7l bp9cbjyn dwo3fsh8 btwxx1t3 pfnyh3mw du4w35lb\"]"));
        element4.click();

        String nomeDoGrupo = "grupo do halerson rafael";
        WebElement inputProcurarGrupos = driver.findElement(By.xpath("//input[@aria-label='Procurar grupos']"));
        sleep(2);
        digitar(inputProcurarGrupos, nomeDoGrupo);
        sleep(5);



        //  escolher qual grupo compartilhar

//        WebElement wePublicacoes = driver.findElement(By.xpath("//*[@class=\"rq0escxv l9j0dhe7 du4w35lb j83agx80 cbu4d94t buofh1pr tgvbjcpo muag1w35 enqfppq2\"]"));
        List<WebElement> grupos = driver.findElements(By.xpath("//*[@class=\"qzhwtbm6 knvmm38d\"]"));

        for (WebElement webElement : grupos) {
            sleep(3);
            System.out.println("webElement.getText: "+webElement.getText());
            if(webElement.getText().equalsIgnoreCase(nomeDoGrupo)) {
                webElement.click();
                break;
            }
        }

        sleep(10);

        WebElement checkIncluirPubOriginal = driver.findElement(By.xpath("//*[@class=\"oajrlxb2 rq0escxv f1sip0of hidtqoto nhd2j8a9 datstx6m kvgmc6g5 cxmmr5t8 oygrvhab hcukyx3x b5wmifdl lzcic4wl jb3vyjys rz4wbd8a qt6c0cv9 a8nywdso pmk7jnqg j9ispegn kr520xx4 k4urcfbm\"]"));
        System.out.println(checkIncluirPubOriginal.getText());
        checkIncluirPubOriginal.click();

        sleep(5);
        driver.findElement(By.xpath("//div[@aria-label='Publicar']")).click();
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
        digitar(driver.findElement(By.id("email")), conta.getEmail());
        sleep(1);
        digitar(driver.findElement(By.id("pass")), conta.getPasswd());
        sleep(1);
        driver.findElement(By.tagName("button")).click();
    }

    private void percorrerRecursos(List<Recurso> recursos) {
        sleep(20);
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

    private void alertarFim(){
        driver.get("https://www.soundjay.com/button/sounds/beep-01a.mp3");
    }
}
