
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * @description Classe principal da Aplicação EM CASOS DE URGÊNCIA
 * @author Felipe Moraes
 * @author Rodrigo Zaratini
 * @date May, 2018
 */
public class BluTVEmCasosDeUrgenciaApp extends Application implements BluTVRemoteDeviceListener {

    //CONTROLE VIRTUAL
    private BluTVRemoteDevice blutvEventController;

    //Esse grupo armazenara todos os painéis e objetos que irão compor a cena
    private final Group grupoObjetosCenaPrincipal = new Group();

    //Variaveis responsáveis por armazenar os dados, como, largura e altura do monitor do usuario
    private double larguraTela, alturaTela;

    //Visualizador de imagens, que apresentara o fundo da aplicação
    private ImageView planoFundo;

    //Cria uma cena que recebera todos painéis (cena principal)
    private Scene cena;

    //Cria-se um objeto que tem todas informações necessárias sobre o monitor do usuario
    private UtilitarioDimensoesMonitor dimensoesMonitor;

    //Nome Urgencia
    private Label labelUrgencia = new Label();
    private Label nomeUrgencia = new Label();
    private final VBox painelNomelUrgencia = new VBox();
    private VBox painelNomelUrgenciaListView = new VBox();

    //LEGENDAS
    private VBox painelLegendaPrincipal = new VBox();
    private VBox painelLegendaComoProceder = new VBox();
    private VBox painelLegendaVideo = new VBox();

    //URGÊNCIA SELECIONADA NA TABELA
    Urgencia urgenciaAtual = new Urgencia();

    //PAINEL LISTA
    private final ComponenteUrgencia urgencias = new ComponenteUrgencia();
    private HBox painelLista = new HBox();

    //PAINEL IMAGENS
    private ComponenteImagem imagemSobre = new ComponenteImagem();
    private HBox painelImagem = new HBox();

    //PAINEL VÍDEO
    private ComponenteConfiguradorVideo videoSobre = new ComponenteConfiguradorVideo();
    private HBox painelVideo = new HBox();
    private HBox painelVideoDescricao = new HBox();
    private Label descricaoUrgencia = new Label();
    private MediaView mediaView = null;
    private MediaPlayer mediaPlayer = null;

    //COMPONENTES
    private final ComponenteConfiguradorImagens imagem = new ComponenteConfiguradorImagens();
    private final ComponenteLegenda legenda = new ComponenteLegenda();
    private final ComponenteAudio audioNavigation = new ComponenteAudio();

    //MARCADORES DAS TELAS
    private int telaAtual, telaPrincipal, telaComoProceder, telaVideo;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage planoPrincipalAplicacao) throws Exception {

        Class c = this.getClass();          // if you want to use the current class

        System.out.println("Package: " + c.getPackage() + "\nClass: " + c.getSimpleName() + "\nFull Identifier: " + c.getName());

        planoPrincipalAplicacao.initStyle(StageStyle.UNDECORATED);

        //INICIA CONTROLE REMOTO
        //java.awt.Desktop.getDesktop().open(new File("C:\\blutv\\platform\\remotedevicesmanager\\BluTVRemoteDevicesManager.jar"));
        //Método que configura o layout da aplicação
        iniciaLayoutPrincipal();

        //INICIA OS MARCADORES DE CADA TELA
        telaAtual = telaPrincipal = 1;
        telaComoProceder = 2;
        telaVideo = 3;

        //Adiciona Objetos e painéis que irão compor a aplicação  no  grupo
        grupoObjetosCenaPrincipal.getChildren().addAll(planoFundo, painelNomelUrgencia, painelLegendaPrincipal, painelLista, painelNomelUrgenciaListView);

        //Inicializa a cena passando os Objetos e as dimensões da tela
        cena = new Scene(grupoObjetosCenaPrincipal, larguraTela, alturaTela);
        cena.setFill(null);

        //PLANO PRINCIPAL RECEBE A CENA
        planoPrincipalAplicacao.setScene(cena);
        planoPrincipalAplicacao.show();

        // Define a posição dos paineis.
        // Os componentes possuem tamanho e posição dinâmicos, então, para que a posição
        // seja calculada corretamente, primeiro os componentes devem ser exibidos.
        ajustaPosicaoPaineis();

        // Conexão com o controle remoto
        blutvEventController = new BluTVRemoteDevice();
        blutvEventController.addBluTVRemoteDeviceListener(this);

    }

    //Método responsável por configurar todo o layout da aplicação
    private void iniciaLayoutPrincipal() {
        dimensoesMonitor = new UtilitarioDimensoesMonitor();
        larguraTela = dimensoesMonitor.getDimensionWidth();
        alturaTela = dimensoesMonitor.getDimensionHeight();
        planoFundo = imagem.getImage("image0.jpg", larguraTela, alturaTela, false);

        constroiTodosPaineis();
        ajustaTamanhoPaineis();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(BluTVEmCasosDeUrgenciaApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //CONSTRÓI TODOS OS PAINÉIS
    private void constroiTodosPaineis() {

        painelNomelUrgencia.getChildren().addAll(labelUrgencia);
        painelNomelUrgenciaListView.getChildren().addAll(nomeUrgencia);
        painelLegendaPrincipal = legenda.constroiPainelLegendaPrincipal(larguraTela, alturaTela);

        painelLista = urgencias.constroiPainelLista((double) larguraTela, (double) alturaTela);
        preparaListaUrgencias();

        painelLegendaComoProceder = legenda.constroiPainelLegendaComoProceder(larguraTela, alturaTela);
        painelLegendaVideo = legenda.constroiPainelLegendaVideo(larguraTela, alturaTela);

    }

    //PREPARA A LISTA DAS URGÊNCIAS
    private void preparaListaUrgencias() {
        urgencias.recebeImagemUrgencias();
        urgencias.listaUrgencias.getSelectionModel().selectFirst();
        getUrgenciaListView();
    }

    //AJUSTA A POSIÇÃO DOS PAINÉIS NA TELA
    private void ajustaPosicaoPaineis() {

        painelLegendaPrincipal.setLayoutX((larguraTela - painelLegendaPrincipal.getWidth()) / 2);
        painelLegendaPrincipal.setLayoutY(alturaTela * 0.9);

        painelLista.setLayoutX(larguraTela * 0.05);
        painelLista.setLayoutY(alturaTela * 0.45);

        painelLegendaComoProceder.setLayoutX(larguraTela * 0.35);
        painelLegendaComoProceder.setLayoutY(alturaTela * 0.9);

        painelVideo.setLayoutY(alturaTela * 0.30);
        painelVideo.setLayoutX(larguraTela * 0.12);

        painelLegendaVideo.setLayoutX(larguraTela * 0.3);
        painelLegendaVideo.setLayoutY(alturaTela * 0.9);

        painelVideoDescricao.setLayoutY(alturaTela * 0.30);
        painelVideoDescricao.setLayoutX(larguraTela * 0.50);

    }

    //AJUSTA O TAMANHO DOS PAINÉIS NA TELA
    private void ajustaTamanhoPaineis() {

        painelNomelUrgencia.setMinWidth(larguraTela * 0.6);
        painelNomelUrgencia.setMinHeight(alturaTela * 0.15);

        painelLegendaPrincipal.setMinWidth(larguraTela * 0.05);
        painelLegendaPrincipal.setMinHeight(alturaTela * 0.06);

        painelLegendaComoProceder.setMinWidth(larguraTela * 0.05);
        painelLegendaComoProceder.setMinHeight(alturaTela * 0.06);

        painelVideo.setMinWidth(larguraTela * 0.3);
        painelVideo.setMinHeight(alturaTela * 0.3);

        painelLegendaVideo.setMinWidth(larguraTela * 0.05);
        painelLegendaVideo.setMinHeight(alturaTela * 0.06);

    }

    //MÉTODOS DOS BOTÕES DO CONTROLE REMOTO
    @Override
    public void BluTVRemoteDeviceButtonExit(RemoteDeviceEvent event) {
        blutvEventController.removeBluTVRemoteDeviceListener(this);
        System.exit(1);
    }

    @Override
    public void BluTVRemoteDeviceButtonOnOff(RemoteDeviceEvent event) {
        blutvEventController.removeBluTVRemoteDeviceListener(this);
        System.exit(1);
    }

    @Override
    public void BluTVRemoteDeviceButtonRed(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (telaAtual == telaPrincipal) {
                    System.exit(1);
                } else if (telaAtual == telaComoProceder) {
                    telaAtual = telaPrincipal;
                    invocaTelaPrincipal();
                } else if (telaAtual == telaVideo) {
                    telaAtual = telaPrincipal;
                    resetaPainelVideo();
                    invocaTelaPrincipal();
                }
            }
        });
    }

    public void BluTVRemoteDeviceButtonEdit(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonGreen(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (telaAtual == telaPrincipal) {
                    getUrgenciaSelecionada();
                    invocaTelaComoProceder();
                    telaAtual = telaComoProceder;
                } else if (telaAtual == telaVideo) {
                    resetaPainelVideo();
                    invocaTelaComoProceder();
                    telaAtual = telaComoProceder;
                }
            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonBlue(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (telaAtual == telaPrincipal) {
                    getUrgenciaSelecionada();
                    invocaTelaVideo();
                    telaAtual = telaVideo;
                } else if (telaAtual == telaComoProceder) {
                    invocaTelaVideo();
                    telaAtual = telaVideo;
                } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.pause();
                } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                    mediaPlayer.play();
                } else if (mediaPlayer.getStatus() == MediaPlayer.Status.STOPPED) {
                    mediaPlayer.play();
                }
            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonYellow(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.pause();
                mediaPlayer.seek(Duration.seconds(1));
            }
        });

    }

    @Override
    public void BluTVRemoteDeviceButtonUp(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            }
        });

    }

    @Override
    public void BluTVRemoteDeviceButtonLeft(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                int menuSize = urgencias.listaUrgencias.getItems().size();
                int selectedIndex = urgencias.listaUrgencias.getSelectionModel().getSelectedIndex();

                if (telaAtual == telaPrincipal) {
                    if (selectedIndex == 0) {
                        urgencias.listaUrgencias.getSelectionModel().select(menuSize - 1);
                        getUrgenciaListView();
                    } else {
                        urgencias.listaUrgencias.getSelectionModel().selectPrevious();
                        getUrgenciaListView();
                    }

                    getUrgenciaListView();
                    audioNavigation.somSelecao();
                }
            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonEnter(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                if (telaAtual == telaPrincipal) {
                    getUrgenciaSelecionada();
                    invocaTelaVideo();
                    telaAtual = telaVideo;
                }

            }
        });

    }

    @Override
    public void BluTVRemoteDeviceButtonRight(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                int menuSize = urgencias.listaUrgencias.getItems().size();
                int selectedIndex = urgencias.listaUrgencias.getSelectionModel().getSelectedIndex();

                if (telaAtual == telaPrincipal) {
                    if (selectedIndex >= (menuSize - 1)) {
                        urgencias.listaUrgencias.getSelectionModel().select(0);
                        getUrgenciaListView();
                    } else {
                        urgencias.listaUrgencias.getSelectionModel().selectNext();
                        getUrgenciaListView();
                    }

                    getUrgenciaListView();
                    audioNavigation.somSelecao();
                }
            }
        });

    }

    @Override
    public void BluTVRemoteDeviceButtonDown(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            }
        });

    }

    @Override
    public void BluTVRemoteDeviceButtonChannelDown(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonChannelUp(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonVolumeUp(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonVolumeDown(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonMute(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonOne(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonTwo(RemoteDeviceEvent rde) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonThree(RemoteDeviceEvent rde) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonFour(RemoteDeviceEvent rde) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonFive(RemoteDeviceEvent rde) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonSix(RemoteDeviceEvent rde) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonSeven(RemoteDeviceEvent rde) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonEight(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonNine(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonZero(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonChars(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void BluTVRemoteDeviceButtonMenu(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    //INVOCA A TELA: COMO PROCEDER
    private void invocaTelaComoProceder() {

        resetaPainelImagem();
        grupoObjetosCenaPrincipal.getChildren().clear();
        planoFundo = imagem.getImage("image01.jpg", larguraTela, alturaTela, false);
        nomeUrgencia();
        grupoObjetosCenaPrincipal.getChildren().addAll(planoFundo, painelNomelUrgencia, painelLegendaComoProceder, painelImagem);

    }

    //INVOCA A TELA PRINCIPAL
    private void invocaTelaPrincipal() {
        ajustaPosicaoPaineis();
        ajustaTamanhoPaineis();
        getUrgenciaListView();

        grupoObjetosCenaPrincipal.getChildren().clear();
        planoFundo = imagem.getImage("image0.jpg", larguraTela, alturaTela, false);
        grupoObjetosCenaPrincipal.getChildren().addAll(planoFundo, painelLegendaPrincipal, painelLista, painelNomelUrgenciaListView);

    }

    private void invocaTelaVideo() {
        resetaPainelVideo();
        nomeUrgencia();
        descricaoUrgencia();

        mediaView = videoSobre.retornaVideo(urgenciaAtual, (larguraTela * 0.65), (alturaTela * 0.35));
        mediaPlayer = mediaView.getMediaPlayer();
        //ivVideo.getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
        painelVideo.getChildren().add(mediaView);

        painelVideoDescricao.getChildren().add(descricaoUrgencia);

        grupoObjetosCenaPrincipal.getChildren().clear();
        planoFundo = imagem.getImage("image02.jpg", larguraTela, alturaTela, false);
        grupoObjetosCenaPrincipal.getChildren().addAll(planoFundo, painelNomelUrgencia, painelLegendaVideo, painelVideo, painelVideoDescricao);

    }

    //RECUPERA A URGÊNCIA SELECIONADA
    private void getUrgenciaSelecionada() {
        urgenciaAtual = urgencias.listaUrgencias.getSelectionModel().getSelectedItem();
    }

    //RECUPERA A URGÊNCIA SELECIONADA NO LIST VIEW
    private void getUrgenciaListView() {

        int menuSize = urgencias.listaUrgencias.getItems().size();
        int selectedIndex = urgencias.listaUrgencias.getSelectionModel().getSelectedIndex();
        String nome = urgencias.listaUrgencias.getSelectionModel().getSelectedItem().getNome();

        painelNomelUrgenciaListView.setMinWidth(larguraTela * 0.15);
        painelNomelUrgenciaListView.setMinHeight(alturaTela * 0.05);

        painelNomelUrgenciaListView.setStyle("-fx-background-color: transparent");
        painelNomelUrgenciaListView.setAlignment(Pos.CENTER);

        nomeUrgencia.setFont(Font.font("Trebuchet MS", 30));
        nomeUrgencia.setStyle("-fx-font-weight: bold; -fx-text-fill: #625d5e");

        if (selectedIndex == 0) {
            nomeUrgencia.setText(nome);
            painelNomelUrgenciaListView.setLayoutY(alturaTela * 0.72);
            painelNomelUrgenciaListView.setLayoutX(larguraTela * 0.07);

        } else if (selectedIndex == 1) {
            nomeUrgencia.setText(nome);
            painelNomelUrgenciaListView.setLayoutY(alturaTela * 0.72);
            painelNomelUrgenciaListView.setLayoutX(larguraTela * 0.24);

        } else if (selectedIndex == 2) {
            nomeUrgencia.setText(nome);
            painelNomelUrgenciaListView.setLayoutY(alturaTela * 0.72);
            painelNomelUrgenciaListView.setLayoutX(larguraTela * 0.42);

        } else if (selectedIndex == 3) {
            nomeUrgencia.setText(nome);
            painelNomelUrgenciaListView.setLayoutY(alturaTela * 0.72);
            painelNomelUrgenciaListView.setLayoutX(larguraTela * 0.60);

        } else if (selectedIndex == 4) {
            nomeUrgencia.setText(nome);
            painelNomelUrgenciaListView.setLayoutY(alturaTela * 0.72);
            painelNomelUrgenciaListView.setLayoutX(larguraTela * 0.76);
        }
    }

    //RECUPERA E POSICIONA O NOME DA URGÊNCIA
    private void nomeUrgencia() {

        labelUrgencia.setText(urgenciaAtual.getNome());
        labelUrgencia.setFont(Font.font("Trebuchet MS", 80));
        labelUrgencia.setStyle("-fx-font-weight: bold; -fx-text-fill: #625d5e");
        labelUrgencia.setPadding(new Insets(10, 10, 10, 10));

        painelNomelUrgencia.setLayoutX((larguraTela - painelNomelUrgencia.getWidth()) / 2);
        painelNomelUrgencia.setLayoutY(alturaTela * 0.1);
        painelNomelUrgencia.setStyle("-fx-background-color: rgba(131,191,208,0.5); -fx-background-radius: 30 30 30 30");
        painelNomelUrgencia.setAlignment(Pos.CENTER);

    }

    //RECUPERA A DESCRICICAO DA URGENCIA
    private void descricaoUrgencia() {
        descricaoUrgencia.setText(urgenciaAtual.getDescricao());
        descricaoUrgencia.setFont(Font.font("Trebuchet MS", 20));
        descricaoUrgencia.setStyle("-fx-font-weight: bold; -fx-text-fill: #625d5e");
        descricaoUrgencia.setMaxWidth(400);
        descricaoUrgencia.setMaxHeight(400);
        descricaoUrgencia.setWrapText(true);
        descricaoUrgencia.setTextAlignment(TextAlignment.JUSTIFY);
        descricaoUrgencia.setPadding(new Insets(10, 10, 50, 10));

    }

    //RESETA O PAINEL DA IMAGEM COMO PROCEDER E ATUALIZA COM A NOVA SELECIONADA
    private void resetaPainelImagem() {
        painelImagem.getChildren().clear();
        painelImagem = imagemSobre.constroiPainelImagens((larguraTela), (alturaTela), urgenciaAtual);
        painelImagem.setLayoutY(alturaTela * 0.30);
        painelImagem.setLayoutX(larguraTela * 0.12);
        painelImagem.setStyle("-fx-background-color: rgba(131,191,208,0.5); -fx-background-radius: 30 30 30 30");

    }

    //RESETA O PAINEL DO VÍDEO E O PAINEL DESCRICAO E  ATUALIZA COM O NOVO DE ACORDO COM A URGÊNCIA SELECIONADA
    private void resetaPainelVideo() {
        painelVideo.getChildren().clear();
        painelVideo.setStyle("-fx-background-color: black");
        painelVideo.setPadding(new Insets(10, 10, 10, 10));

        painelVideoDescricao.getChildren().clear();
        painelVideoDescricao.setStyle("-fx-background-color: rgba(131,191,208,0.5); -fx-background-radius: 30 30 30 30");

        if (mediaView != null) {
            mediaView.getMediaPlayer().stop();
        }
    }

    public void BluTVRemoteDeviceButtonApp(RemoteDeviceEvent rde) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
