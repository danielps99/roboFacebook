package dto;

import service.ControladorLoopService;

import java.util.HashSet;
import java.util.List;

public class Compartilhavel {

    private String url;
    private Boolean incluirPubOriginal;
    private HashSet<String> nomesGrupos;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIncluirPubOriginal() {
        return incluirPubOriginal;
    }

    public void setIncluirPubOriginal(Boolean incluirPubOriginal) {
        this.incluirPubOriginal = incluirPubOriginal;
    }

    public HashSet<String> getNomesGrupos() {
        return nomesGrupos;
    }

    public void setNomesGrupos(HashSet<String> nomesGrupos) {
        this.nomesGrupos = nomesGrupos;
    }
}
