package dto;

import service.ControladorLoopService;

import java.util.HashSet;
import java.util.List;

public class Compartilhavel {

    private String url;
    private HashSet<String> nomesGrupos;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashSet<String> getNomesGrupos() {
        return nomesGrupos;
    }

    public void setNomesGrupos(HashSet<String> nomesGrupos) {
        this.nomesGrupos = nomesGrupos;
    }
}
