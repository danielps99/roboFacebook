package service;

import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Date;
import java.util.TimerTask;

public class TimerLoopService extends TimerTask {

    public final Long INTERVALO = 180000L;
    private Date ultimoLoop;
    private ChromeDriver driver;

    public TimerLoopService(ChromeDriver driver) {
        this.driver = driver;
        atualizarUltimoLoop();
    }

    @Override
    public void run() {
        long diferencaTempo = new Date().getTime() - ultimoLoop.getTime();
        if (diferencaTempo > INTERVALO) {
            driver.get("https://www.soundjay.com/button/sounds/button-8.mp3");
        }
    }

    public void atualizarUltimoLoop() {
        ultimoLoop = new Date();
    }
}