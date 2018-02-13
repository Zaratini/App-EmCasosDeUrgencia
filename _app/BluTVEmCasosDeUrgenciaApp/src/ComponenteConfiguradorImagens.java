import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//CLASSE RESPONSÁVEL PELA BUSCA DE IMAGENS
public class ComponenteConfiguradorImagens {

    private ImageView imagem;
    private String pastaPlataformaBluTV;

    public ImageView getImage(String imageName, double width, double height, boolean PreserveRatio) {
        try {
            pastaPlataformaBluTV = "file:///C:/blutv/channels/ch45/resident/applications/app18/image/" + imageName;
            imagem = new ImageView(new Image(pastaPlataformaBluTV));

            imagem.setFitWidth(width);
            imagem.setFitHeight(height);
            imagem.setPreserveRatio(PreserveRatio);

            return imagem;
        } catch (Exception e) {
            System.out.println("BluTV Message: não foi possível localizar imagem " + imageName + ".");
        }
        return null;
    }
}
