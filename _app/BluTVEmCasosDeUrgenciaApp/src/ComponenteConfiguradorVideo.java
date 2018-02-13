
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

// CLASSE RESPONSÁVEL PELA BUSCA E EXIBIÇÃO DE VÍDEOS
public class ComponenteConfiguradorVideo {

    private MediaView video;
    private String pastaPlataformaBluTV;
    private Media media;
    private MediaPlayer player;

    public MediaView retornaVideo(Urgencia urgencia, double largura, double altura) {

        try {
            pastaPlataformaBluTV = urgencia.getVideo();
            media = new Media(pastaPlataformaBluTV);
            player = new MediaPlayer(media);
            video = new MediaView(player);
            System.err.printf(urgencia.getVideo());

            video.setFitWidth(largura);
            video.setFitHeight(altura);
            player.play();

            return video;

        } catch (Exception e) {
            System.out.println("BluTV Message: não foi possível localizar video " + urgencia.getVideo() + ".");
        }
        return null;
    }
}
