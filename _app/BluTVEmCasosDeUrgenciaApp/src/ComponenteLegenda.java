
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @description CLASSE RESPONSÁVEL POR MONTAR AS LEGENDAS DAS TELAS DA APLICAÇÃO
 * @author Felipe Moraes
 * @author Rodrigo Zaratini
 */

public class ComponenteLegenda {

    private final VBox painelLegendaPrincipal = new VBox(5);
    private final VBox painelLegendaComoProceder = new VBox(5);
    private final VBox painelLegendaVideo = new VBox(5);

    public ComponenteLegenda() {
        iniciaComponentes();
    }
    
    private ImageView setasEsqDir;
    private final ComponenteConfiguradorImagens imagem = new ComponenteConfiguradorImagens();

    private void iniciaComponentes() {
        
        setasEsqDir = imagem.getImage("image12.png", 36, 14, true);
    }

    public VBox constroiPainelLegendaPrincipal(double largura, double altura) {

        painelLegendaPrincipal.setMinWidth(largura);
        painelLegendaPrincipal.setMinHeight(altura);

        Label textoSetasEsqDir;

        HBox painelBotoes = new HBox(10);
        painelBotoes.setSpacing(5);
        // painelBotoes.setTranslateY(30);
        
        //Circulos
        Circle circleAzul,circleVermelho, circleVerde;
     
        circleAzul = new Circle(10);
        circleAzul.setFill(Color.DARKBLUE);
        
        circleVermelho= new Circle(10);
        circleVermelho.setFill(Color.RED);
        
        circleVerde= new Circle(10);
        circleVerde.setFill(Color.GREEN);

        Label labelAzul = new Label();
        Label labelVermelho = new Label();
        Label labelVerde = new Label();
        
        labelAzul.setText("VÍDEO INFORMATIVO");
        labelVermelho.setText("SAIR");
        labelVerde.setText("COMO AGIR");

        labelAzul.setStyle("-fx-font-size: 15px;-fx-font-weight: bold;-fx-text-fill: black;-fx-background-color: transparent; -fx-padding:3 10 3 5;");
        labelVerde.setStyle("-fx-font-size: 15px;-fx-font-weight: bold;-fx-text-fill: black;-fx-background-color: transparent; -fx-padding:3 10 3 5;");
        labelVermelho.setStyle("-fx-font-size: 15px;-fx-font-weight: bold;-fx-text-fill: black;-fx-background-color: transparent; -fx-padding:3 10 3 5;");
               
        textoSetasEsqDir = new Label("NAVEGAR ENTRE AS OPÇÕES");
        textoSetasEsqDir.setStyle("-fx-font-size: 15px;-fx-font-weight: bold;-fx-text-fill: black;-fx-background-color: transparent; -fx-padding:3 10 3 5;");

        painelBotoes.getChildren().addAll(setasEsqDir, textoSetasEsqDir, circleVerde, labelVerde, circleAzul, labelAzul,circleVermelho , labelVermelho);
        painelBotoes.setAlignment(Pos.CENTER);
        
        painelLegendaPrincipal.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-background-radius: 30 30 30 30");
        painelLegendaPrincipal.setPadding(new Insets(17));
        painelLegendaPrincipal.getChildren().addAll(painelBotoes);

        return painelLegendaPrincipal;
    }

    public VBox constroiPainelLegendaComoProceder(double largura, double altura) {

        painelLegendaComoProceder.setMinWidth(largura);
        painelLegendaComoProceder.setMinHeight(altura);

        HBox painelBotoes = new HBox(10);
        painelBotoes.setSpacing(10);

        //Circulos
        Circle circleAzul,circleVermelho;
     
        circleAzul = new Circle(10);
        circleAzul.setFill(Color.DARKBLUE);
        
        circleVermelho= new Circle(10);
        circleVermelho.setFill(Color.RED);

        Label labelAzul = new Label();
        Label labelVermelho = new Label();
        
        labelAzul.setText("VÍDEO INFORMATIVO");
        labelVermelho.setText("VOLTAR");
        
        labelAzul.setStyle("-fx-font-size: 15px;-fx-font-weight: bold;-fx-text-fill: black;-fx-background-color: transparent; -fx-padding:3 10 3 5;");
        labelVermelho.setStyle("-fx-font-size: 15px;-fx-font-weight: bold;-fx-text-fill: black;-fx-background-color: transparent; -fx-padding:3 10 3 5;");

        painelBotoes.getChildren().addAll(circleAzul, labelAzul,circleVermelho, labelVermelho);
        painelBotoes.setAlignment(Pos.CENTER);

        painelLegendaComoProceder.setStyle("-fx-background-color: rgba(255,255,255,0.5);-fx-background-radius: 30 30 30 30");
        painelLegendaComoProceder.setPadding(new Insets(17));
        painelLegendaComoProceder.getChildren().addAll(painelBotoes);

        return painelLegendaComoProceder;
    }

    public VBox constroiPainelLegendaVideo(double largura, double altura) {

        painelLegendaVideo.setMinWidth(largura);
        painelLegendaVideo.setMinHeight(altura);

        HBox painelBotoes = new HBox(10);
        painelBotoes.setSpacing(10);
        // painelBotoes.setTranslateY(30);
        
        //Circulos
        Circle circleAmarelo,circleAzul,circleVermelho, circleVerde;
     
        circleAmarelo= new Circle(10);
        circleAmarelo.setFill(Color.YELLOW);
        
        circleAzul = new Circle(10);
        circleAzul.setFill(Color.DARKBLUE);
        
        circleVermelho= new Circle(10);
        circleVermelho.setFill(Color.RED);
        
        circleVerde= new Circle(10);
        circleVerde.setFill(Color.GREEN);
        
        Label labelAmarelo = new Label();
        Label labelAzul = new Label();
        Label labelVermelho = new Label();
        Label labelVerde = new Label();
 
        labelVermelho.setText("VOLTAR");
        labelVerde.setText("COMO AGIR");
        labelAmarelo.setText("STOP");
        labelAzul.setText("PLAY / PAUSE");

        labelVerde.setStyle("-fx-font-size: 15px;-fx-font-weight: bold;-fx-text-fill: black;-fx-background-color: transparent; -fx-padding:3 10 3 5;");
        labelVermelho.setStyle("-fx-font-size: 15px;-fx-font-weight: bold;-fx-text-fill: black;-fx-background-color: transparent; -fx-padding:3 10 3 5;");
        labelAmarelo.setStyle("-fx-font-size: 15px;-fx-font-weight: bold;-fx-text-fill: black;-fx-background-color: transparent; -fx-padding:3 10 3 5;");
        labelAzul.setStyle("-fx-font-size: 15px;-fx-font-weight: bold;-fx-text-fill: black;-fx-background-color: transparent; -fx-padding:3 10 3 5;");
        
        
        painelBotoes.getChildren().addAll(circleAzul,labelAzul,circleAmarelo,labelAmarelo,circleVerde, labelVerde,circleVermelho, labelVermelho);
        painelBotoes.setAlignment(Pos.CENTER);
        
        painelLegendaVideo.setStyle("-fx-background-color: rgba(255,255,255,0.5);-fx-background-radius: 30 30 30 30");
        painelLegendaVideo.setPadding(new Insets(17));
        painelLegendaVideo.getChildren().addAll(painelBotoes);

        return painelLegendaVideo;
    }
}
