package br.com.munif.fraguas.projects.icorretor.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Foto entity.
 */
public class FotoDTO implements Serializable {

    private String id;

    private String descricao;

    @Lob
    private byte[] imagem;
    private String imagemContentType;

    private Long imovelId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return imagemContentType;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
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

        FotoDTO fotoDTO = (FotoDTO) o;
        if(fotoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fotoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FotoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", imagem='" + getImagem() + "'" +
            "}";
    }
}
