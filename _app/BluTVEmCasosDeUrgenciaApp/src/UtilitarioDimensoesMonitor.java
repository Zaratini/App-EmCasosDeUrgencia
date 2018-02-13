
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author Rodrigo José de Souza Silva
 * @author João Benedito dos Santos Junior, Ph.D.
 */

// CLASSE RESPONSÁVEL POR PEGAS AS DIMENSÕES DO MONITOR/TELA EM QUE A APLICAÇÃO SERÁ EXECUTADA
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
