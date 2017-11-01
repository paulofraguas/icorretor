package br.com.munif.fraguas.projects.icorretor.domain;


import org.hibernate.envers.Audited;
import br.com.munif.framework.vicente.domain.BaseEntity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Imovel.
 */
@Entity
@Table(name = "imovel")
//@Audited
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class Imovel extends BaseEntity {

    @Column(name = "codigo_sub_100")
    private String codigoSub100;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "preco", precision=10, scale=2)
    private BigDecimal preco;

    @Column(name = "area_construida", precision=10, scale=2)
    private BigDecimal areaConstruida;

    @Column(name = "area_terreno", precision=10, scale=2)
    private BigDecimal areaTerreno;

    @Column(name = "observacoes")
    private String observacoes;

    @OneToMany(mappedBy = "imovel")
    @JsonIgnore
    private Set<Foto> fotos = new HashSet<>();

    @OneToMany(mappedBy = "imovel")
    @JsonIgnore
    private Set<Proposta> propostas = new HashSet<>();

    @ManyToOne
    private Corretor corretor;


    public Imovel(){

    }

    public Imovel(String codigoSub100,String endereco,String bairro,String descricao,String tipo,BigDecimal preco,BigDecimal areaConstruida,BigDecimal areaTerreno,String observacoes){
        this.codigoSub100=codigoSub100;
        this.endereco=endereco;
        this.bairro=bairro;
        this.descricao=descricao;
        this.tipo=tipo;
        this.preco=preco;
        this.areaConstruida=areaConstruida;
        this.areaTerreno=areaTerreno;
        this.observacoes=observacoes;
    }




    public String getCodigoSub100() {
        return codigoSub100;
    }

    public Imovel codigoSub100(String codigoSub100) {
        this.codigoSub100 = codigoSub100;
        return this;
    }

    public void setCodigoSub100(String codigoSub100) {
        this.codigoSub100 = codigoSub100;
    }

    public String getEndereco() {
        return endereco;
    }

    public Imovel endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public Imovel bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getDescricao() {
        return descricao;
    }

    public Imovel descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public Imovel tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Imovel preco(BigDecimal preco) {
        this.preco = preco;
        return this;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getAreaConstruida() {
        return areaConstruida;
    }

    public Imovel areaConstruida(BigDecimal areaConstruida) {
        this.areaConstruida = areaConstruida;
        return this;
    }

    public void setAreaConstruida(BigDecimal areaConstruida) {
        this.areaConstruida = areaConstruida;
    }

    public BigDecimal getAreaTerreno() {
        return areaTerreno;
    }

    public Imovel areaTerreno(BigDecimal areaTerreno) {
        this.areaTerreno = areaTerreno;
        return this;
    }

    public void setAreaTerreno(BigDecimal areaTerreno) {
        this.areaTerreno = areaTerreno;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Imovel observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Set<Foto> getFotos() {
        return fotos;
    }

    public Imovel fotos(Set<Foto> fotos) {
        this.fotos = fotos;
        return this;
    }

    public Imovel addFoto(Foto foto) {
        this.fotos.add(foto);
        foto.setImovel(this);
        return this;
    }

    public Imovel removeFoto(Foto foto) {
        this.fotos.remove(foto);
        foto.setImovel(null);
        return this;
    }

    public void setFotos(Set<Foto> fotos) {
        this.fotos = fotos;
    }

    public Set<Proposta> getPropostas() {
        return propostas;
    }

    public Imovel propostas(Set<Proposta> propostas) {
        this.propostas = propostas;
        return this;
    }

    public Imovel addProposta(Proposta proposta) {
        this.propostas.add(proposta);
        proposta.setImovel(this);
        return this;
    }

    public Imovel removeProposta(Proposta proposta) {
        this.propostas.remove(proposta);
        proposta.setImovel(null);
        return this;
    }

    public void setPropostas(Set<Proposta> propostas) {
        this.propostas = propostas;
    }

    public Corretor getCorretor() {
        return corretor;
    }

    public Imovel corretor(Corretor corretor) {
        this.corretor = corretor;
        return this;
    }

    public void setCorretor(Corretor corretor) {
        this.corretor = corretor;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove


    @Override
    public String toString() {
        return 
            super.toString() +
            ", codigoSub100='" + getCodigoSub100() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", preco='" + getPreco() + "'" +
            ", areaConstruida='" + getAreaConstruida() + "'" +
            ", areaTerreno='" + getAreaTerreno() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            "}";
    }
}
