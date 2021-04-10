package app;

import com.google.common.base.Strings;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
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

    public default void println(String mensagem) {
        if (!Strings.isNullOrEmpty(mensagem)) {
            System.out.println("----- " + mensagem);
        }
    }

    public default void info(String info) {
        logger.info(info);
    }

    public default void error(Exception exception) {
        println(exception.getClass().getName());
        try {
            throw exception;
        } catch (StaleElementReferenceException e) {
            System.out.print("----StaleElementReferenceException");
        } catch (ElementClickInterceptedException e) {
            System.out.print("----ElementClickInterceptedException");
        } catch (WebDriverException e) {
            System.out.print("----WebDriverException");
        } catch (Exception e) {
            println("----exception.getClass().getName() -> "+exception.getClass().getName());
            error(exception.getMessage());
        }
    }

    public default void error(String error) {
        logger.error(error);
    }
}