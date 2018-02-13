
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

// CLASSE RESPONSÁVEL POR AJUSTAR AS IMAGENS DE RODAPÉ CONTIDAS NA APLICAÇÃO
public class ComponenteRodape {

    private HBox painelPrincipal = new HBox(25);
    private ComponenteConfiguradorImagens imagem = new ComponenteConfiguradorImagens();
    private ImageView logoTVDI, logoCanal;

    public HBox constroiPainelRodape(double largura, double altura) {

        painelPrincipal.setMinWidth(largura);
        painelPrincipal.setMinHeight(altura);

        logoTVDI = imagem.getImage("image13.png", largura * 0.08, altura * 0.48, true);
        logoCanal = imagem.getImage("image14.png", largura * 0.08, altura * 0.48, true);

        painelPrincipal.setAlignment(Pos.CENTER_LEFT);

       // painelPrincipal.getChildren().addAll(logoTVDI, logoCanal);

        return painelPrincipal;

    }
}
