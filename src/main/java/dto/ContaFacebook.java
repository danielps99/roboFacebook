package dto;

import java.util.List;

public class ContaFacebook {
    private String email;
    private String passwd;
    private List<Recurso> recursos;
    private Compartilhavel compartilhavel;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    public Compartilhavel getCompartilhavel() {
        return compartilhavel;
    }

    public void setCompartilhavel(Compartilhavel compartilhavel) {
        this.compartilhavel = compartilhavel;
    }

    public boolean isCompartilhavel() {
        return compartilhavel != null;
    }
}
