package app;

import com.google.common.base.Strings;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public interface ICommons {

    final static Random random = new Random();
    public static final Logger logger = LoggerFactory.getLogger(ICommons.class);

    public default void sleep(Integer multiplicador) {
        try {
            Long tempoEmMilisegundos = Long.valueOf((random.nextInt(500) + 500) * multiplicador);
            Thread.sleep(Long.valueOf(tempoEmMilisegundos));
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public default void digitar(WebElement webElementInput, String texto) {
        char[] chars = texto.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            try {
                Thread.sleep(random.nextInt(250) + 250);
            } catch (InterruptedException e) {
            } finally {
                webElementInput.sendKeys(chars[i]+"");
            }
        }
    }

    public default boolean isClicavel(WebElement webElement) {
        return webElement.isDisplayed() && webElement.isEnabled();
    }
    public default void println(String mensagem) {
        if (!Strings.isNullOrEmpty(mensagem)) {
            System.out.println("----- " + mensagem);
        }
    }

    public default void info(String info) {
        logger.info(info);
    }

    public default void error(Exception exception) {
        try {
            throw exception;
        } catch (StaleElementReferenceException e) {
            println("----StaleElementReferenceException");
        } catch (ElementClickInterceptedException e) {
            println("----ElementClickInterceptedException");
        } catch (WebDriverException e) {
            println("----WebDriverException");
        } catch (Exception e) {
            println("----exception.getClass().getName() -> "+exception.getClass().getName());
            error(exception.getMessage());
        }
    }

    public default void error(String error) {
        logger.error(error);
    }
}