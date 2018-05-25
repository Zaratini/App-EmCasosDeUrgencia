
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

// CLASSE RESPONSÁVE POR CONFIGURAR O PAINEL DA IMAGEM DA TELA COMO PROCEDER
public class ComponenteImagem {
    final ImageView imv = new ImageView();
    final HBox painelImagem = new HBox();

    public HBox constroiPainelImagens(double largura, double altura, Urgencia urgencia) {
        painelImagem.getChildren().clear();
        String imgComoProceder = urgencia.getImgComoProceder();
        final Image image;
        image = new Image(new File(imgComoProceder).toURI().toString(), largura * 0.71, altura * 0.53, false, false);

        imv.setImage(image);
        painelImagem.getChildren().add(imv);

        return painelImagem;
    }
}
