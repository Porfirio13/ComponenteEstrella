package componente_final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class Guardar_D extends JPanel {
    private JTextField campoNombreProducto;
    private JButton botonGuardar, botonModificar;
    private JList<String> listaProductos;
    private DefaultListModel<String> modeloLista;
    private Map<String, Integer> calificacionesProductos;
    private Estrella_F panelEstrellas;
    private String productoSeleccionado;

    public Guardar_D() {
        setLayout(new BorderLayout());

        JPanel panelEntrada = new JPanel(new FlowLayout());

        campoNombreProducto = new JTextField(15);
        botonGuardar = new JButton("Guardar Calificación");
        botonModificar = new JButton("Modificar Calificación");

        panelEntrada.add(new JLabel("Producto:"));
        panelEntrada.add(campoNombreProducto);
        panelEntrada.add(botonGuardar);
        panelEntrada.add(botonModificar);

        modeloLista = new DefaultListModel<>();
        listaProductos = new JList<>(modeloLista);

        calificacionesProductos = new HashMap<>();

        panelEstrellas = new Estrella_F();
        panelEstrellas.setPreferredSize(new Dimension(160, 50));

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BorderLayout());
        panelDerecho.add(new JScrollPane(listaProductos), BorderLayout.CENTER);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(panelEntrada, BorderLayout.NORTH);
        panelPrincipal.add(panelEstrellas, BorderLayout.CENTER);

        add(panelPrincipal, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.EAST);

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreProducto = campoNombreProducto.getText().trim();
                int calificacion = panelEstrellas.getEstrellasSeleccionadas();
                
                if (!nombreProducto.isEmpty() && calificacion > 0) {
                    calificacionesProductos.put(nombreProducto, calificacion);
                    actualizarListaProductos();
                    campoNombreProducto.setText("");
                    panelEstrellas.restablecerEstrellas();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese un nombre de producto y calificación.");
                }
            }
        });

        botonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productoSeleccionado = listaProductos.getSelectedValue();
                if (productoSeleccionado != null) {
                    String nombreProducto = productoSeleccionado.split(" - ")[0].replace("Producto: ", "").trim();
                    Integer calificacionActual = calificacionesProductos.get(nombreProducto);

                    if (calificacionActual != null) {
                        campoNombreProducto.setText(nombreProducto);
                        panelEstrellas.establecerEstrellas(calificacionActual);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor seleccione un producto de la lista.");
                }
            }
        });
    }

    private void actualizarListaProductos() {
        modeloLista.clear();
        for (Map.Entry<String, Integer> entry : calificacionesProductos.entrySet()) {
            String nombreProducto = entry.getKey();
            int calificacion = entry.getValue();
            modeloLista.addElement("Producto: " + nombreProducto + " - Calificación: " + (calificacion * 2));
        }
    }

    class Estrella_F extends JPanel {
        private int estrellasSeleccionadas = 0;
        private final int tamañoEstrella = 30;
        private final Color colorEstrellaLlena = Color.YELLOW;
        private final Color colorEstrellaVacia = Color.GRAY;

        public Estrella_F() {
            setPreferredSize(new Dimension(tamañoEstrella * 5, tamañoEstrella + 10));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    estrellasSeleccionadas = e.getX() / tamañoEstrella + 1;
                    repaint();
                }
            });
        }

        public int getEstrellasSeleccionadas() {
            return estrellasSeleccionadas;
        }

        public void establecerEstrellas(int estrellas) {
            estrellasSeleccionadas = estrellas;
            repaint();
        }

        public void restablecerEstrellas() {
            estrellasSeleccionadas = 0;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            for (int i = 0; i < 5; i++) {
                g2d.setColor(i < estrellasSeleccionadas ? colorEstrellaLlena : colorEstrellaVacia);
                dibujarEstrella(g2d, i * tamañoEstrella, 5, tamañoEstrella, tamañoEstrella);
            }
        }

        private void dibujarEstrella(Graphics2D g, int x, int y, int ancho, int alto) {
            int[] puntosX = {
                x + ancho / 2, x + (int)(ancho * 0.38), x,
                x + (int)(ancho * 0.3), x + (int)(ancho * 0.15),
                x + ancho / 2, x + ancho - (int)(ancho * 0.15),
                x + ancho - (int)(ancho * 0.3), x + ancho,
                x + ancho - (int)(ancho * 0.38)
            };

            int[] puntosY = {
                y, y + (int)(alto * 0.35), y + (int)(alto * 0.35),
                y + (int)(alto * 0.6), y + alto,
                y + (int)(alto * 0.75), y + alto,
                y + (int)(alto * 0.6), y + (int)(alto * 0.35),
                y + (int)(alto * 0.35)
            };
            g.fillPolygon(puntosX, puntosY, puntosX.length);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Guardar y Modificar Productos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        Guardar_D guardarPanel = new Guardar_D();
        frame.add(guardarPanel);

        frame.setVisible(true);
    }
}
