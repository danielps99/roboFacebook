package app;

import dto.Compartilhavel;
import dto.ContaFacebook;
import dto.Recurso;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import service.TimerLoopService;
import service.ControladorLoopService;
import service.ReaderJsonService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class App implements ICommons {

    private ChromeDriver driver;
    private ControladorLoopService controladorLoopService;
    private TimerLoopService timerLoopService;
    private Comando comando;

    public App(Comando comando) {
        controladorLoopService = ControladorLoopService.getInstance();
        this.comando = comando;
    }

    public void start() {
        ContaFacebook contaFacebook = ReaderJsonService.buscarContaFacebook();
        inicializarNavegador();
        logar(contaFacebook);
        if (!Comando.SOMENTE_LOGAR.equals(comando)) {
            if (contaFacebook.isCompartilhavel()) {
                percorrerECompartilhar(contaFacebook.getCompartilhavel());
            } else {
                percorrerRecursosECurtir(contaFacebook.getRecursos());
            }
        }
        alertarFim();
    }

    private void percorrerECompartilhar(Compartilhavel compartilhavel) {
        info("COMPARTILHAR");
        for (String nomeGrupo : compartilhavel.getNomesGrupos()) {
            try {
                compartilhar(compartilhavel.getUrl(), compartilhavel.getIncluirPubOriginal(), nomeGrupo);
            } catch (NoSuchElementException e) {
                info("NoSuchElementException " + nomeGrupo);
            } catch (Exception e ) {
                info("Exception " + e.getMessage());
            }
        }
    }
        private void compartilhar(String urlPost, Boolean incluirPubOriginal, String nomeGrupo) {
        driver.get(urlPost);
        sleep(5);

        driver.findElement(By.xpath("//div[@aria-label='Envie isso para amigos ou publique na sua linha do tempo']")).click();
        sleep(5);

        WebElement compartilharEmGrupo = driver.findElement(By.xpath("//*[@class=\"ow4ym5g4 auili1gw rq0escxv j83agx80 buofh1pr g5gj957u i1fnvgqd oygrvhab cxmmr5t8 hcukyx3x kvgmc6g5 tgvbjcpo hpfvmrgz qt6c0cv9 jb3vyjys l9j0dhe7 du4w35lb bp9cbjyn btwxx1t3 dflh9lhu scb9dxdr\"]"));
        compartilharEmGrupo.click();
        sleep(5);

        driver.findElement(By.xpath("//label[@aria-label='Compartilhar como']")).click();
        sleep(5);

        WebElement usuarioQueVaiCompartilhar = driver.findElement(By.xpath("//*[@class=\"oajrlxb2 g5ia77u1 qu0x051f esr5mh6w e9989ue4 r7d6kgcz rq0escxv nhd2j8a9 j83agx80 p7hjln8o kvgmc6g5 oi9244e8 oygrvhab h676nmdw cxgpxx05 dflh9lhu sj5x9vvc scb9dxdr i1ao9s8h esuyzwwr f1sip0of lzcic4wl l9j0dhe7 abiwlrkh p8dawk7l bp9cbjyn dwo3fsh8 btwxx1t3 pfnyh3mw du4w35lb\"]"));
        usuarioQueVaiCompartilhar.click();

        WebElement inputProcurarGrupos = driver.findElement(By.xpath("//input[@aria-label='Procurar grupos']"));
        sleep(2);
        digitar(inputProcurarGrupos, nomeGrupo);
        sleep(5);

        List<WebElement> grupos = driver.findElements(By.xpath("//*[@class=\"qzhwtbm6 knvmm38d\"]"));
        System.out.println("Percorrer "+grupos.size()+" grupos...");
        Boolean clicouNoGrupo = false;
        for (WebElement grupo : grupos) {
            sleep(3);
            if (grupo.getText().contains(nomeGrupo)) {
                grupo.click();
                clicouNoGrupo = true;
                break;
            }
        }
        sleep(10);

        if (incluirPubOriginal) {
            WebElement checkIncluirPubOriginal = driver.findElement(By.xpath("//*[@class=\"oajrlxb2 rq0escxv f1sip0of hidtqoto nhd2j8a9 datstx6m kvgmc6g5 cxmmr5t8 oygrvhab hcukyx3x b5wmifdl lzcic4wl jb3vyjys rz4wbd8a qt6c0cv9 a8nywdso pmk7jnqg j9ispegn kr520xx4 k4urcfbm\"]"));
            checkIncluirPubOriginal.click();
            sleep(5);
        }
        driver.findElement(By.xpath("//div[@aria-label='Publicar']")).click();
        sleep(10);
        info(clicouNoGrupo ? "COMPARTILHOU CORRETAMENTE: " : "PULOU: " + nomeGrupo);
    }

    private void inicializarTimerLoop() {
        Timer timer = new Timer();
        timerLoopService = new TimerLoopService(driver);
        timer.schedule(timerLoopService, 0, timerLoopService.INTERVALO);
    }

    private void inicializarNavegador() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        if (Comando.EXECUTAR_NAVEGADOR_FECHADO.equals(comando)) {
            options.addArguments("headless");
        }
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

    private void percorrerRecursosECurtir(List<Recurso> recursos) {
        info("CURTIR");
        sleep(20);
        info("GRUPOS DA CONTA:");
        recursos.stream().forEach(e -> info(e.toString()));

        inicializarTimerLoop();
        for (Recurso recurso : recursos) {
            controladorLoopService.inicializarVariaveis(recurso);
            info(recurso.toString());
            driver.get(recurso.getUrl());
            try {
                percorrerPublicacoesRecursoECurtir(recurso);
            } catch (Exception e) {
                info("Erro ao percorrer e curtir recurso: " + recurso.toString());
            }
        }
    }

    private void percorrerPublicacoesRecursoECurtir(Recurso recurso) {
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
                        timerLoopService.atualizarUltimoLoop();
                        sleep(2);
                    } catch (Exception e) {
                        error(e);
                    }
                }
            }
        }
    }

    private void alertarFim() {
        driver.get("https://www.soundjay.com/button/sounds/beep-01a.mp3");
        info("FINALIZOU");
    }
}
