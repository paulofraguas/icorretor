package br.com.munif.fraguas.projects.icorretor.domain;


import org.hibernate.envers.Audited;
import br.com.munif.framework.vicente.domain.BaseEntity;



import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Proposta.
 */
@Entity
@Table(name = "proposta")
//@Audited
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class Proposta extends BaseEntity {

    @Column(name = "valor", precision=10, scale=2)
    private BigDecimal valor;

    @Column(name = "dia")
    private LocalDate dia;

    @Column(name = "observacao")
    private String observacao;

    @ManyToOne
    private Imovel imovel;


    public Proposta(){

    }

    public Proposta(BigDecimal valor,LocalDate dia,String observacao){
        this.valor=valor;
        this.dia=dia;
        this.observacao=observacao;
    }




    public BigDecimal getValor() {
        return valor;
    }

    public Proposta valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDia() {
        return dia;
    }

    public Proposta dia(LocalDate dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public String getObservacao() {
        return observacao;
    }

    public Proposta observacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public Proposta imovel(Imovel imovel) {
        this.imovel = imovel;
        return this;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove


    @Override
    public String toString() {
        return 
            super.toString() +
            ", valor='" + getValor() + "'" +
            ", dia='" + getDia() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
