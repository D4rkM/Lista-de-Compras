package br.com.listamercado.app;

import com.orm.SugarApp;
import com.orm.SugarRecord;

/**
 * Created by 16254861 on 25/10/2017.
 */
/** As classes de objetos que vão ter tabelas no banco herdam a classe SugarRecord*/
public class Produto extends SugarRecord{

    private String nome;
    private boolean ativo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    //Construtores obrigatórios para utilizar ORM
    public Produto(){}

    public Produto(String nome, Boolean ativo){
        this.nome = nome;
        this.ativo = ativo;
    }

}
