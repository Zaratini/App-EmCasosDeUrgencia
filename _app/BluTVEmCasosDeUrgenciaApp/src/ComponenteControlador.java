
import java.util.ArrayList;

// CLASSE RESPONSÁVEL POR ATRIBUIR AS INFORMAÇÕES DO XML Á OBJETOS EM MEMÓRIA
public class ComponenteControlador {

    ArrayList<Urgencia> listaObjUrgencias = new ArrayList<>();

    public ComponenteControlador() {

        constroiObjetosUrgencias();
    }

    private void constroiObjetosUrgencias() {
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

            listaObjUrgencias.add(urgenciaAtual);
        }

    }

    public ArrayList<Urgencia> getListaObjUrgencias() {
        return listaObjUrgencias;
    }

    public void setListaObjUrgencia(ArrayList<Urgencia> listaObjUrgencias) {
        this.listaObjUrgencias = listaObjUrgencias;
    }

    public Urgencia getUrgenciaSelecionada(String nomeUrgencia) {
        Urgencia urgenciaAtual = new Urgencia();

        for (int i = 0; i < listaObjUrgencias.size(); i++) {

            if (listaObjUrgencias.get(i).getNome().equals(nomeUrgencia.trim())) {
                urgenciaAtual = listaObjUrgencias.get(i);
                return urgenciaAtual;
            }
        }
        return urgenciaAtual;
    }
}
