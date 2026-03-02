package src.models;

import java.util.Date;

public class Secretaria extends Usuario {

    public Secretaria(String nome, String login, String senha) {
        super(nome, login, senha);
    }

    public Curriculo geraCurriculo() {
        Curriculo curriculo = new Curriculo();
        curriculo.setDataInicio(new Date());
        return curriculo;
    }
}
