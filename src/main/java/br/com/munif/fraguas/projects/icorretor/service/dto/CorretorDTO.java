package br.com.munif.fraguas.projects.icorretor.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Corretor entity.
 */
public class CorretorDTO implements Serializable {

    private String id;

    private String nome;

    private String telefone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorretorDTO corretorDTO = (CorretorDTO) o;
        if(corretorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), corretorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorretorDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            "}";
    }
}
