
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// CLASSE RESPONSÁVEL POR CRIAR E CONFIGURAR A TABELA COM AS URGÊNCIAS DA TELA PRINCIPAL DA APLICAÇÃO
public class ComponenteTabela {

    private final HBox painelTabela = new HBox(5);
    VBox painelTabelaFinal = new VBox(3);
    Urgencia urgenciaAtual = new Urgencia();
    ArrayList<TabelaUrgenciasDados> listaObjUrgencia = new ArrayList();
    double altura, largura;
    public ListView<TabelaUrgenciasDados> table;

    public VBox constroiPainelTabela(double largura, double altura) {
        this.altura = altura;
        this.largura = largura;
        painelTabela.setMinWidth(largura);
        painelTabela.setMinHeight(altura);
        
        table = new ListView<>();
        //table.setTableMenuButtonVisible(true);
    
        
        table.setMinHeight(altura * 0.1);
        table.setMinWidth(largura * 0.75);

        table.setMaxHeight(altura * 0.2);
        table.setMaxWidth(largura * 0.95);
        

        //Cria colunas
        //TableColumn painelUrgencia = new TableColumn<>();
        //painelUrgencia.setPrefWidth(largura * 0.14);

        //TableColumn descricao = new TableColumn<>();
        //descricao.setPrefWidth(largura * 0.48);
      
        //table.getColumns().addAll(painelUrgencia);
        //table.getColumns().addAll();

        //Associa Coluna com os metodos
        //painelUrgencia.setCellValueFactory(new PropertyValueFactory<>("painelUrgencia"));
        //descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        //table.setStyle("-fx-background-insets: 0 0 -2 0, 0, 2, 3;\n"
        //        + "-fx-opacity: 0.9;");

        table.getStylesheets().add("file:///C:/blutv/channels/ch45/resident/applications/app18/data/data2.css");

        painelTabela.getChildren().addAll(table);
        painelTabelaFinal.getChildren().addAll(painelTabela);

        return painelTabelaFinal;
    }

    public void avancaTabela(int scroll) {
        table.scrollTo(scroll);

    }

    public void recebeTodasUrgencias() {
        painelTabela.getChildren().clear();
        painelTabelaFinal.getChildren().clear();

        listaObjUrgencia.clear();
        HBox painelUrgencia;
        VBox painelLabel;
        Label nomeUrgencia;
        Label descricao;

        ComponenteControlador controler = new ComponenteControlador();
        ArrayList<Urgencia> listaUrgenciasCompleta;
        listaUrgenciasCompleta = controler.getListaObjUrgencias();


        for (int i = 0; i < listaUrgenciasCompleta.size(); i++) {

            Image teste = new Image(listaUrgenciasCompleta.get(i).getImagem());

            ImageView imageItem = new ImageView();
            imageItem.setImage(teste);
            imageItem.setFitHeight(altura * 0.085);
            imageItem.setFitWidth(largura * 0.070);

            nomeUrgencia = new Label("\n " + listaUrgenciasCompleta.get(i).getNome());
            descricao = new Label(listaUrgenciasCompleta.get(i).getDescricao());
            descricao.setPrefWidth(largura);
            descricao.setWrapText(true);


            painelLabel = new VBox();
            painelUrgencia = new HBox();

            painelLabel.getChildren().addAll(nomeUrgencia);
            painelUrgencia.getChildren().addAll(imageItem);

            //nomeUrgencia.setAlignment(Pos.CENTER);
            //descricao.setAlignment(Pos.CENTER);


            //nomeUrgencia.setStyle("-fx-alignment: CENTER;-fx-text-fill: black;-fx-font:24pt 'Teresias';");
            //descricao.setStyle("-fx-alignment: CENTER;-fx-text-fill: black;-fx-font:18pt 'Teresias';");

            TabelaUrgenciasDados tabela1 = new TabelaUrgenciasDados(painelUrgencia);
            tabela1.setXMLIndex(i);
            //TabelaUrgenciasDados tabela1 = new TabelaUrgenciasDados(painelUrgencia);
            
            tabela1.nome = nomeUrgencia;

            listaObjUrgencia.add(tabela1);

        }

        table.setItems(FXCollections.observableArrayList(listaObjUrgencia));
        table.setOrientation(Orientation.HORIZONTAL);
        table.getSelectionModel().selectFirst();
        
        table.setCellFactory(param -> new ListCell<TabelaUrgenciasDados>() {
            @Override
            protected void updateItem(TabelaUrgenciasDados item, boolean empty) {
                super.updateItem(item, empty);
                
                if(item != null){
                    HBox box= new HBox();
                    box.setSpacing(10) ;
                    VBox vbox = new VBox();
                    vbox.getChildren().add(new Label(item.getNome().getText()));
                    
                    ImageView imageview = new ImageView();
                    imageview.setImage(new Image(listaUrgenciasCompleta.get(item.getXMLIndex()).getImagem()));
                    imageview.setFitHeight(altura * 0.12);
                    imageview.setFitWidth(largura * 0.12);

                    //SETTING ALL THE GRAPHICS COMPONENT FOR CELL
                    box.getChildren().addAll(imageview, vbox); 
                    
                    setGraphic(box);
                }
            }
        });
               
        painelTabela.getChildren().addAll(table);
        painelTabelaFinal.getChildren().addAll(painelTabela);

    }
}
