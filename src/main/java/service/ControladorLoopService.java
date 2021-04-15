package service;

import dto.Recurso;
import org.openqa.selenium.WebElement;

public class ControladorLoopService {

    private static ControladorLoopService uniqueInstance;

    private Integer posicaoAtual;
    private Integer posicaoAnterior;
    private Integer contadorAriaLabelRemoverCurtir;
    private Integer maxPosicao;
    private Integer maxRemoverCurtir;

    private Recurso recurso;

    private ControladorLoopService() {
        inicializarVariaveis(null);
    }

    public static ControladorLoopService getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ControladorLoopService();
        }
        return uniqueInstance;
    }

    public void inicializarVariaveis(Recurso recurso) {
        this.recurso = recurso;
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

    public boolean canContinuarLoopElementos() {
        return contadorAriaLabelRemoverCurtir < maxRemoverCurtir;
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

    public Recurso getRecurso() {
        return recurso;
    }
}