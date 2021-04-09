package service;

import org.openqa.selenium.WebElement;

public class ControladorLoopService {

    private static ControladorLoopService uniqueInstance;

    private Integer posicaoAtual;
    private Integer posicaoAnterior;
    private Integer contadorAriaLabelRemoverCurtir;
    private Integer maxPosicao;
    private Integer maxRemoverCurtir;

    private ControladorLoopService() {
        inicializarVariaveis();
    }

    public static ControladorLoopService getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ControladorLoopService();
        }
        return uniqueInstance;
    }

    public void inicializarVariaveis() {
        inicializarVariaveis(30000, 3);
    }

    public void inicializarVariaveis(Integer maxPosicaoP, Integer maxRemoverCurtirP) {
        contadorAriaLabelRemoverCurtir = 0;
        posicaoAtual = 1;
        posicaoAnterior = 0;
        maxPosicao = maxPosicaoP;
        maxRemoverCurtir = maxRemoverCurtirP;
    }

    public boolean canContinuarLoop() {
        return posicaoAtual < maxPosicao && contadorAriaLabelRemoverCurtir < maxRemoverCurtir;
    }

    public Integer getPosicaoAtual() {
        return posicaoAtual;
    }

    public void atualizarPosicaoAtual(WebElement webElement) {
        if (webElement.getLocation() != null && webElement.getLocation().getY() != 0) {
            posicaoAtual = webElement.getLocation().getY();
            if (posicaoAtual != posicaoAnterior) {
                posicaoAnterior = posicaoAtual;
            }
        }
    }

    public Integer incrementarContadorAriaLabelRemoverCurtir() {
        return ++contadorAriaLabelRemoverCurtir;
    }
}