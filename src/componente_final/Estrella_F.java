/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package componente_final;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author jesuz
 */
public class Estrella_F extends JPanel {

    private int selectedStars = 0;
    private final int starSize = 30;
    private final Color fullStarColor = Color.YELLOW;
    private final Color emptyStarColor = Color.GRAY;
    private final JLabel scoreLabel;

    public Estrella_F() {
        setPreferredSize(new Dimension(starSize * 5, starSize + 40));

        // Inicializar puntuación
        scoreLabel = new JLabel("Puntuación: 0 / 10");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Configurar el panel y agregar la etiqueta
        setLayout(new BorderLayout());
        add(scoreLabel, BorderLayout.SOUTH);

        // Agregar un mouse listener para cambiar la calificación
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedStars = e.getX() / starSize + 1;
                updateScoreLabel();
                repaint();
            }
        });
    }

    private void updateScoreLabel() {
        int score = selectedStars * 2; // Convertir estrellas a escala de 10
        scoreLabel.setText("Puntuación: " + score + " / 10");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < 5; i++) {
            g2d.setColor(i < selectedStars ? fullStarColor : emptyStarColor);
            drawStar(g2d, i * starSize, 5, starSize, starSize);
        }
    }

    private void drawStar(Graphics2D g, int x, int y, int width, int height) {
        int[] xPoints = {
            x + width / 2, x + (int) (width * 0.38), x,
            x + (int) (width * 0.3), x + (int) (width * 0.15),
            x + width / 2, x + width - (int) (width * 0.15),
            x + width - (int) (width * 0.3), x + width,
            x + width - (int) (width * 0.38)
        };

        int[] yPoints = {
            y, y + (int) (height * 0.35), y + (int) (height * 0.35),
            y + (int) (height * 0.6), y + height,
            y + (int) (height * 0.75), y + height,
            y + (int) (height * 0.6), y + (int) (height * 0.35),
            y + (int) (height * 0.35)
        };
        g.fillPolygon(xPoints, yPoints, xPoints.length);
    }

}
