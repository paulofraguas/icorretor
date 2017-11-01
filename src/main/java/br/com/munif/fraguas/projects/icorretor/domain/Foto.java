package br.com.munif.fraguas.projects.icorretor.domain;


import org.hibernate.envers.Audited;
import br.com.munif.framework.vicente.domain.BaseEntity;



import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Foto.
 */
@Entity
@Table(name = "foto")
//@Audited
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class Foto extends BaseEntity {

    @Column(name = "descricao")
    private String descricao;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @Column(name = "imagem_content_type")
    private String imagemContentType;

    @ManyToOne
    private Imovel imovel;


    public Foto(){

    }

    public Foto(String descricao,byte[] imagem){
        this.descricao=descricao;
        this.imagem=imagem;
    }




    public String getDescricao() {
        return descricao;
    }

    public Foto descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public Foto imagem(byte[] imagem) {
        this.imagem = imagem;
        return this;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }


    public String getImagemContentType() {
        return imagemContentType;
    }

    public Foto imagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
        return this;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public Foto imovel(Imovel imovel) {
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
            ", descricao='" + getDescricao() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", imagemContentType='" + imagemContentType + "'" +
            "}";
    }
}
