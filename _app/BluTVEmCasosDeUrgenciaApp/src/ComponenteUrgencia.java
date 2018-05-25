
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// CLASSE RESPONSÁVEL POR CRIAR E CONFIGURAR A TABELA COM AS URGÊNCIAS DA TELA PRINCIPAL DA APLICAÇÃO
public class ComponenteUrgencia {
    private HBox painelListaUrgencias = new HBox(5);
    public ListView<Urgencia> listaUrgencias;
    
    double altura, largura;
    

    public HBox constroiPainelLista(double largura, double altura) {
        this.altura = altura;
        this.largura = largura;
        painelListaUrgencias.setMinWidth(largura);
        painelListaUrgencias.setMinHeight(altura);

        listaUrgencias = new ListView<>();

        listaUrgencias.setMinHeight(altura * 0.25);
        listaUrgencias.setMinWidth(largura * 1.5);

        listaUrgencias.setMaxHeight(altura * 0.28);
        listaUrgencias.setMaxWidth(largura * 0.95);

        listaUrgencias.getStylesheets().add("file:///C:/blutv/channels/ch45/resident/applications/app18/data/data2.css");
        
        listaUrgencias.setOrientation(Orientation.HORIZONTAL);

        painelListaUrgencias.getChildren().addAll(listaUrgencias);

        return painelListaUrgencias;
    }

    public void recebeImagemUrgencias() {       
        painelListaUrgencias.getChildren().clear();
        ArrayList<Urgencia> listaUrgenciasCompleta = new ComponenteControlador().getListaUrgencias();

        listaUrgencias.setItems(FXCollections.observableArrayList(listaUrgenciasCompleta));

        listaUrgencias.setCellFactory(param -> new ListCell<Urgencia>() {
            @Override
            protected void updateItem(Urgencia item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {
                    HBox box = new HBox();
                    box.setSpacing(10);
                    VBox vbox = new VBox();
                    vbox.getChildren().add(new Label(item.getNome()));

                    ImageView imageview = new ImageView();
                    imageview.setImage(new Image(item.getImagem()));
                    imageview.setFitHeight(altura * 0.245);
                    imageview.setFitWidth(largura * 0.165);

                    //SETTING ALL THE GRAPHICS COMPONENT FOR CELL
                    box.getChildren().addAll(imageview);

                    setGraphic(box);
                }
            }
        });

        painelListaUrgencias.getChildren().addAll(listaUrgencias);
    }
}
