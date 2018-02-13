
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// CLASSE RESPONSÁVEL POR CONFIGURAR A TABELA
public class TabelaUrgenciasDados {

    HBox painelUrgencia = new HBox();
    VBox painelLabel = new VBox();
    ImageView image = new ImageView();
    Label nome = new Label();
    UtilitarioDimensoesMonitor dimension = new UtilitarioDimensoesMonitor();

    public TabelaUrgenciasDados(HBox painelUrgencia) {
        this.painelUrgencia = painelUrgencia;
    }

    public HBox getPainelUrgencia() {
        return painelUrgencia;
    }

    public void setPainelUrgencia(HBox painelUrgencia) {
        this.painelUrgencia = painelUrgencia;
    }

    public Label getNome() {
        return nome;
    }

    public void setNome(Label nome) {
        this.nome = nome;
    }

}
