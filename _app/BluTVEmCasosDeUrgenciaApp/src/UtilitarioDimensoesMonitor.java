
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * @description CLASSE RESPONSÁVEL POR PEGAS AS DIMENSÕES DO MONITOR/TELA EM QUE A APLICAÇÃO SERÁ EXECUTADA
 * @author Rodrigo José de Souza Silva
 * @author João Benedito dos Santos Junior, Ph.D.
 */

public class UtilitarioDimensoesMonitor {

    private final Dimension dimensaoMonitor;
    private final int largura, altura;

    public UtilitarioDimensoesMonitor() {
        dimensaoMonitor = Toolkit.getDefaultToolkit().getScreenSize();
        this.largura = dimensaoMonitor.width;
        this.altura = dimensaoMonitor.height;
    }

    public int getDimensionWidth() {
        return largura;
    }

    public int getDimensionHeight() {
        return altura;
    }
}
