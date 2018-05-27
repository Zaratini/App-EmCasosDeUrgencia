
import java.util.ArrayList;

/**
 * @description CLASSE RESPONSÁVEL POR ATRIBUIR AS INFORMAÇÕES DO XML Á OBJETOS EM MEMÓRIA
 * @author Felipe Moraes
 */

public class ComponenteControlador {
    ArrayList<Urgencia> listaUrgencias = new ArrayList<>();

    public ComponenteControlador() {
        constroiListaUrgencias();
    }

    private void constroiListaUrgencias() {
        BluTVXMLParser aplicacao = new BluTVXMLParser("Resident", "45", "18", "data0.xml");
        String[] id = aplicacao.returnValuesList("urgencia", "idUrgencia");
        String[] nome = aplicacao.returnValuesList("urgencia", "nome");
        String[] imagem = aplicacao.returnValuesList("urgencia", "imagem");
        String[] descricao = aplicacao.returnValuesList("urgencia", "descricao");
        String[] imgComoProceder = aplicacao.returnValuesList("urgencia", "imgComoProceder");
        String[] video = aplicacao.returnValuesList("urgencia", "video");

        for (int i = 0; i < nome.length && nome[i] != null; i++) {
            Urgencia urgenciaAtual = new Urgencia();

            urgenciaAtual.setidUrgencia(Integer.parseInt(id[i]));
            urgenciaAtual.setNome(nome[i]);
            urgenciaAtual.setImagem(imagem[i]);
            urgenciaAtual.setDescricao(descricao[i]);
            urgenciaAtual.setImgComoProceder(imgComoProceder[i]);
            urgenciaAtual.setVideo(video[i]);

            listaUrgencias.add(urgenciaAtual);
        }

    }

    public ArrayList<Urgencia> getListaUrgencias() {
        return listaUrgencias;
    }
}
