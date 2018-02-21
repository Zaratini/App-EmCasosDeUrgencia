
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sun.audio.AudioPlayer;

/**
 *
 * @author Felipe
 * @author Rodrigo Zaratini 
 */

// CLASSE PRINCIPAL DA APLICAÇÃO EM CASOS DE URGÊNCIA
public class BluTVEmCasosDeUrgenciaApp extends Application implements BluTVRemoteDeviceListener {

    //CONTROLE VIRTUAL
    private BluTVRemoteDevice blutvEventController;
    
    //Marcadores responsaveis pelas transições dos paineis
    private final int cima, baixo = 2;
    int contaLinhas = 0;
    int esquerda = 1, direita = 2;
    int contadorSelecao = 1;
    
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
    
    //TÍTULO
    private final Label labelTitulo = new Label();
    private final VBox painelTitulo = new VBox();
    
    //LEGENDAS
    private VBox painelLegendaPrincipal = new VBox();
    private VBox painelLegendaComoProceder = new VBox();
    private VBox painelLegendaVideo = new VBox();
    
    //URGÊNCIA SELECIONADA NA TABELA
    Urgencia urgenciaAtual= new Urgencia();
    int scroll;
    
    //PAINEL TABELA
    private final ComponenteTabela tabela = new ComponenteTabela();
    private VBox painelTabela = new VBox();
    
    //PAINEL RODAPE
    private ComponenteRodape rodape = new ComponenteRodape();
    private HBox painelRodape = new HBox();
    private String urgencia = "";
    
    //PAINEL IMAGENS
    private ComponenteImagem imagemSobre = new ComponenteImagem();
    private HBox painelImagem = new HBox();
    
    //PAINEL VÍDEO
    private ComponenteConfiguradorVideo videoSobre = new ComponenteConfiguradorVideo();
    private HBox painelVideo = new HBox();
    private MediaView ivVideo = null;
    
    //COMPONENTES
    private final ComponenteConfiguradorImagens imagem = new ComponenteConfiguradorImagens();
    private final ComponenteLegenda legenda = new ComponenteLegenda();
    private final ComponenteAudio audioNavigation = new ComponenteAudio();
    private final ComponenteControlador controler = new ComponenteControlador();
    
    //MARCADORES DAS TELAS
    private int telaAtual, telaPrincipal, telaComoProceder, telaVideo;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public BluTVEmCasosDeUrgenciaApp() {
        this.cima = 1;
    }

    @Override
    public void start(Stage planoPrincipalAplicacao) throws Exception {
        
        Class c = this.getClass();          // if you want to use the current class

        System.out.println("Package: "+c.getPackage()+"\nClass: "+c.getSimpleName()+"\nFull Identifier: "+c.getName());
        
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
        grupoObjetosCenaPrincipal.getChildren().addAll(planoFundo, painelTitulo, painelLegendaPrincipal, painelTabela, painelRodape);

        //inicializa a cena passando os Objetos e as dimensões da tela
        cena = new Scene(grupoObjetosCenaPrincipal, larguraTela, alturaTela);
        cena.setFill(null);

        //PLANO PRINCIPAL RECBE A CENA
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

        planoFundo.setStyle("-fx-opacity: 1;");

        //labelTitulo.setText("EM CASOS DE URGÊNCIA");
        //labelTitulo.setStyle("-fx-font-weight: bold; -fx-text-fill: rgb(35,158,163);-fx-font-size: 40px;");
        //painelTitulo.setAlignment(Pos.CENTER);

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

        painelTitulo.getChildren().addAll(labelTitulo);
        painelLegendaPrincipal = legenda.constroiPainelLegendaPrincipal(larguraTela, alturaTela);
        
        painelTabela = tabela.constroiPainelTabela((double) larguraTela, (double) alturaTela);
        preparaTabelaComUrgencias();
        
        //painelRodape = rodape.constroiPainelRodape(larguraTela, alturaTela);
        painelLegendaComoProceder = legenda.constroiPainelLegendaComoProceder(larguraTela, alturaTela);
        painelLegendaVideo = legenda.constroiPainelLegendaVideo(larguraTela, alturaTela);
        
    }
    
    //PREPARA A TABELA DAS URGÊNCIAS
    private void preparaTabelaComUrgencias(){
         tabela.recebeTodasUrgencias();
         tabela.table.getSelectionModel().selectFirst();
    }
          
    //AJUSTA A POSIÇÃO DOS PAINÉIS NA TELA
    private void ajustaPosicaoPaineis() {

        painelTitulo.setLayoutY(alturaTela * 0.15);
        painelTitulo.setLayoutX(larguraTela * 0.153);
        
        // Legenda centralizada:(LARGUDA DA TELA - LARGURA DA LEGENDA) / 2
        painelLegendaPrincipal.setLayoutX((larguraTela - painelLegendaPrincipal.getWidth()) / 2);
        //painelLegendaPrincipal.setLayoutX(larguraTela * 0.2);
        painelLegendaPrincipal.setLayoutY(alturaTela * 0.9);
       
        painelTabela.setLayoutY(alturaTela * 0.45);
        painelTabela.setLayoutX(larguraTela * 0.14);

        painelRodape.setLayoutX(larguraTela * 0.8);
        painelRodape.setLayoutY(alturaTela * 0.905);
        
        painelLegendaComoProceder.setLayoutX(larguraTela * 0.35);
        painelLegendaComoProceder.setLayoutY(alturaTela * 0.9);
        
        painelVideo.setLayoutY(alturaTela * 0.3);
        painelVideo.setLayoutX(larguraTela * 0.22);
        
        painelLegendaVideo.setLayoutX(larguraTela * 0.3);
        painelLegendaVideo.setLayoutY(alturaTela * 0.9);

    }

    //AJUSTA O TAMANHO DOS PAINÉIS NA TELA
    private void ajustaTamanhoPaineis() {

        painelTitulo.setMinWidth(larguraTela * 0.7);
        painelTitulo.setMinHeight(alturaTela * 0.15);

        //Configurações Antigas Legenda
        //painelLegendaPrincipal.setMinWidth(larguraTela * 1);
        //painelLegendaPrincipal.setMinHeight(alturaTela * 0.06);
        
        painelLegendaPrincipal.setMinWidth(larguraTela * 0.05);
        painelLegendaPrincipal.setMinHeight(alturaTela * 0.06);

        painelTabela.setMinWidth(larguraTela * 0.33);
        painelTabela.setMinHeight(alturaTela * 0.4);

        painelRodape.setMinWidth(larguraTela * 0.42);
        painelRodape.setMinHeight(alturaTela * 0.05);
        
        painelLegendaComoProceder.setMinWidth(larguraTela * 0.05);
        painelLegendaComoProceder.setMinHeight(alturaTela * 0.06);
        
        painelVideo.setMinWidth(larguraTela * 0.3);
        painelVideo.setMinHeight(alturaTela * 0.4);
        
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
                }
                else if (telaAtual == telaComoProceder) {
                    telaAtual = telaPrincipal;
                    invocaTelaPrincipal();                    
                }
                else if (telaAtual == telaVideo) {
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
                    tabela.table.getSelectionModel().select(0);

                    telaAtual = telaComoProceder;                
                }   
                else if(telaAtual == telaVideo){
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
                    tabela.table.getSelectionModel().select(0);

                    telaAtual = telaVideo;
                }
                else if(telaAtual == telaComoProceder){
                    
                    invocaTelaVideo();
                    telaAtual = telaVideo;
                
                }
                
                else if (ivVideo.getMediaPlayer().getStatus()== MediaPlayer.Status.PLAYING){
                        
                        ivVideo.getMediaPlayer().pause();
                }  
                
                else if (ivVideo.getMediaPlayer().getStatus()== MediaPlayer.Status.PAUSED){
                        
                        ivVideo.getMediaPlayer().play();
                }
                
                else if (ivVideo.getMediaPlayer().getStatus()== MediaPlayer.Status.STOPPED){
                        
                        ivVideo.getMediaPlayer().play();
                }  
            }
        });

    }

    @Override
    public void BluTVRemoteDeviceButtonYellow(RemoteDeviceEvent rde) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
             ivVideo.getMediaPlayer().pause();
             ivVideo.getMediaPlayer().seek(Duration.seconds(1));
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
                if (telaAtual == telaPrincipal) {
                    tabela.table.getSelectionModel().selectPrevious();
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
                    tabela.table.getSelectionModel().select(0);

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
               if (telaAtual == telaPrincipal) {
                    tabela.table.getSelectionModel().selectNext();
                    audioNavigation.somSelecao();
//                    tabela.setas.escalaBaixo();
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
        grupoObjetosCenaPrincipal.getChildren().addAll(planoFundo, painelTitulo, painelLegendaComoProceder, painelImagem, painelRodape);

    }

    //INVOCA A TELA PRINCIPAL
    private void invocaTelaPrincipal() {

        ajustaPosicaoPaineis();
        ajustaTamanhoPaineis();
 
        grupoObjetosCenaPrincipal.getChildren().clear();
        planoFundo = imagem.getImage("image0.jpg", larguraTela, alturaTela, false);
        grupoObjetosCenaPrincipal.getChildren().addAll(planoFundo, painelTitulo, painelLegendaPrincipal, painelTabela, painelRodape);

    }
    
    //INVOCA A TELA DO VÍDEO    
    private void invocaTelaVideo() {
        
        resetaPainelVideo();

        ivVideo = videoSobre.retornaVideo(urgenciaAtual, (larguraTela*0.75), (alturaTela*0.55));
        ivVideo.getMediaPlayer().setCycleCount(MediaPlayer.INDEFINITE);
        painelVideo.getChildren().add(ivVideo);
                
        grupoObjetosCenaPrincipal.getChildren().clear();
        planoFundo = imagem.getImage("image02.jpg", larguraTela, alturaTela, false);
        grupoObjetosCenaPrincipal.getChildren().addAll(planoFundo, painelTitulo, painelLegendaVideo, painelVideo, painelRodape);
        

    }
    
    //RECUPERA A URGÊNCIA SELECIONADA NA TABELA
    private void getUrgenciaSelecionada(){
        Urgencia urgencia = controler.getUrgenciaSelecionada(tabela.table.getSelectionModel().getSelectedItem().getNome().getText());
        urgenciaAtual = urgencia;
    }
    
    //RESETA O PAINEL DA IMAGEM COMO PROCEDER E ATUALIZA COM A NOVA SELECIONADA
    private void resetaPainelImagem(){
        painelImagem.getChildren().clear();
        painelImagem = imagemSobre.constroiPainelImagens((larguraTela*1.4), (alturaTela*1.8), urgenciaAtual);
        
        painelImagem.setLayoutY(alturaTela * 0.02);
        painelImagem.setLayoutX(larguraTela *0.002);
        
        //painelImagem = imagemSobre.constroiPainelImagens((larguraTela*1.24), (alturaTela*1.36), urgenciaAtual);
        
        //painelImagem.setLayoutY(alturaTela * 0.09);
        //painelImagem.setLayoutX(larguraTela *0.12);
        
    }
    
    //RESETA O PAINEL DO VÍDEO E ATUALIZA COM O NOVO DE ACORDO COM A URGÊNCIA SELECIONADA
    private void resetaPainelVideo(){

        painelVideo.getChildren().clear();
        
        if(ivVideo != null){
            ivVideo.getMediaPlayer().stop();
        }
        
    }

    public void BluTVRemoteDeviceButtonApp(RemoteDeviceEvent rde) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
