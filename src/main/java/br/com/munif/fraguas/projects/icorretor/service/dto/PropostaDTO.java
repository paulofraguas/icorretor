package br.com.munif.fraguas.projects.icorretor.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Proposta entity.
 */
public class PropostaDTO implements Serializable {

    private String id;

    private BigDecimal valor;

    private LocalDate dia;

    private String observacao;

    private Long imovelId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Long getImovelId() {
        return imovelId;
    }

    public void setImovelId(Long imovelId) {
        this.imovelId = imovelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PropostaDTO propostaDTO = (PropostaDTO) o;
        if(propostaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), propostaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PropostaDTO{" +
            "id=" + getId() +
            ", valor='" + getValor() + "'" +
            ", dia='" + getDia() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
