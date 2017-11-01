package br.com.munif.fraguas.projects.icorretor.service.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Imovel entity.
 */
public class ImovelDTO implements Serializable {

    private String id;

    private String codigoSub100;

    private String endereco;

    private String bairro;

    private String descricao;

    private String tipo;

    private BigDecimal preco;

    private BigDecimal areaConstruida;

    private BigDecimal areaTerreno;

    private String observacoes;

    private Long corretorId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigoSub100() {
        return codigoSub100;
    }

    public void setCodigoSub100(String codigoSub100) {
        this.codigoSub100 = codigoSub100;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getAreaConstruida() {
        return areaConstruida;
    }

    public void setAreaConstruida(BigDecimal areaConstruida) {
        this.areaConstruida = areaConstruida;
    }

    public BigDecimal getAreaTerreno() {
        return areaTerreno;
    }

    public void setAreaTerreno(BigDecimal areaTerreno) {
        this.areaTerreno = areaTerreno;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Long getCorretorId() {
        return corretorId;
    }

    public void setCorretorId(Long corretorId) {
        this.corretorId = corretorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImovelDTO imovelDTO = (ImovelDTO) o;
        if(imovelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imovelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImovelDTO{" +
            "id=" + getId() +
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
