package app;

import com.google.common.base.Strings;
import org.openqa.selenium.WebElement;
import service.ControladorLoopService;

import java.util.HashSet;

public class Publicacao implements ICommons {

    private StringBuilder texto;
    private boolean isFinalizado;
    private boolean isPatrocinado;
    private int adicionarTextoContador;
    private HashSet<String> adicionados;
    private ControladorLoopService controladorLoopService;

    public Publicacao() {
        inicializarVariaveis();
    }

    private void inicializarVariaveis() {
        texto = new StringBuilder();
        isFinalizado = false;
        isPatrocinado = false;
        adicionarTextoContador = 0;
        adicionados = new HashSet<String>();
        controladorLoopService = ControladorLoopService.getInstance();
    }

    public void adicionarTexto(WebElement webElement) {
        if (isFinalizado) {
            inicializarVariaveis();
        }

        adicionarTextoContador++;
        String ariaLabel = webElement.getAttribute("aria-label");
        if (ariaLabel != null && ariaLabel.equalsIgnoreCase("curtir")) {
            isFinalizado = true;
        } else {
            if (ariaLabel != null && ariaLabel.equalsIgnoreCase("remover curtir")) {
                Integer c = controladorLoopService.incrementarContadorAriaLabelRemoverCurtir();
                info("Remover Curtir" + c);
            }
            String webElementText = webElement.getText();
            if (!Strings.isNullOrEmpty(webElementText)) {
                String parte = webElementText.trim().replaceAll(System.lineSeparator(), " ");
//                if (!Constantes.palavrasAIgnorar.contains(parte)) {
                if (controladorLoopService.getRecurso().getPalavrasAIgnorar().contains(parte)) {
                    adicionados.add(parte);
                }
            }
        }
    }

    public boolean canCurtir() {
        if (!isFinalizado) {
            return false;
        }
        for (String s : adicionados) {
            texto.append(s);
        }
//        for (String naoCurtirTexto : Constantes.naoCurtirPalavras) {
        for (String naoCurtirTexto : controladorLoopService.getRecurso().getNaoCurtirPalavras()) {
            if (texto.toString().toLowerCase().contains(naoCurtirTexto)) {
                return false;
            }
        }

//        if (!Constantes.palavrasPreferidas.isEmpty()) {
        if (!controladorLoopService.getRecurso().getPalavrasPreferidas().isEmpty()) {
//            for (String preferido : Constantes.palavrasPreferidas) {
            for (String preferido : controladorLoopService.getRecurso().getPalavrasPreferidas()) {
                if (texto.toString().toLowerCase().contains(preferido)) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    public void imprimetexto() {
        String posicao = "Pos.Y=" + controladorLoopService.getPosicaoAtual().toString() + ", ";
        logger.info(posicao + this.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(isPatrocinado ? "Patrocidado, " : "");
        sb.append("Contador ").append(adicionarTextoContador).append(", ");
        sb.append(texto);
        return sb.toString();
    }
}