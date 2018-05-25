
// CLASSE RESPOSÁVEL POR FORNECER OS OBJETOS URGÊNCIA
public class Urgencia {
    private int idUrgencia;
    private String nome;
    private String imagem;
    private String descricao;
    private String imgComoProceder;
    private String video;

    public Urgencia() {
    }

    public int getIdUrgencia() {
        return idUrgencia;
    }

    public void setidUrgencia(int idUrgencia) {
        this.idUrgencia = idUrgencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImgComoProceder() {
        return imgComoProceder;
    }

    public void setImgComoProceder(String imgComoProceder) {
        this.imgComoProceder = imgComoProceder;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
