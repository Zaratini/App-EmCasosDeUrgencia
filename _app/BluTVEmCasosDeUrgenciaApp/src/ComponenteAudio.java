
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

//CLASSE RESPONSÁVEL POR SELECIONAR O SOM EMITIDO PELA TABELA
public class ComponenteAudio {

    String audioSelecao = "file:///C:/blutv/channels/ch45/resident/applications/app18/audio/audio1.mp3";

    private MediaPlayer mediaPlayer;

    public void somSelecao() {

        mediaPlayer = new MediaPlayer(new Media(audioSelecao));

        mediaPlayer.setVolume(1);
        mediaPlayer.play();

    }

}
