package br.com.munif.fraguas.projects.icorretor.domain;


import org.hibernate.envers.Audited;
import br.com.munif.framework.vicente.domain.BaseEntity;



import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Corretor.
 */
@Entity
@Table(name = "corretor")
//@Audited
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class Corretor extends BaseEntity {

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;


    public Corretor(){

    }

    public Corretor(String nome,String telefone){
        this.nome=nome;
        this.telefone=telefone;
    }




    public String getNome() {
        return nome;
    }

    public Corretor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public Corretor telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove


    @Override
    public String toString() {
        return 
            super.toString() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            "}";
    }
}
