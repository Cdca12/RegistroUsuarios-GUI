package registrousuarios.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import utils.*;

/**
 *
 * @author Carlos Contreras
 */
public class Main {

    public static void main(String[] args) {
        Menu ventanaPrincipal = new Menu();
        ventanaPrincipal.hazInterfaz();
        ventanaPrincipal.hazEscuchas();

    }

    public static class Menu extends JFrame implements ActionListener {

        JTextField txtNombre, txtEdad, txtEstatura;
        JRadioButton rdBtnNombre, rdBtnEdad, rdBtnEstatura, rdBtnEdadEstNombre;
        ButtonGroup grupoRdBtn;
        JButton btnAñadir, btnConsultar, btnLimpiar;

        Lista<Usuario> lista = new Lista<>();
        int criterioAux = UtilsCriterioOrdenamiento.POR_NOMBRE; // Default

        public Menu() {
            super("Registro de Usuarios");
            hazInterfaz();
            hazEscuchas();
        }

        private void hazInterfaz() {
            // Radio Button criterio de ordenamiento
            rdBtnNombre = new JRadioButton("Nombre", true);
            rdBtnEdad = new JRadioButton("Edad");
            rdBtnEstatura = new JRadioButton("Estatura");
            rdBtnEdadEstNombre = new JRadioButton("Edad-Estatura-Nombre");

            grupoRdBtn = new ButtonGroup();
            grupoRdBtn.add(rdBtnNombre);
            grupoRdBtn.add(rdBtnEdad);
            grupoRdBtn.add(rdBtnEstatura);
            grupoRdBtn.add(rdBtnEdadEstNombre);

            JPanel panelRdBtn = new JPanel();
//            panelRdBtn.setLayout(new BoxLayout(panelRdBtn, BoxLayout.X_AXIS));
            panelRdBtn.add(rdBtnNombre);
            panelRdBtn.add(rdBtnEdad);
            panelRdBtn.add(rdBtnEstatura);
            panelRdBtn.add(rdBtnEdadEstNombre);
            add(panelRdBtn, BorderLayout.NORTH);

            // Botones 
            btnAñadir = new JButton("Añadir");
            btnConsultar = new JButton("Consultar");
            btnLimpiar = new JButton("Limpiar");

            JPanel panelBtn = new JPanel();
//            panelBtn.setLayout(null);
            panelBtn.add(btnAñadir);
            panelBtn.add(btnConsultar);
            panelBtn.add(btnLimpiar);
            add(panelBtn, BorderLayout.SOUTH);

            // Formulario
            JPanel panelForm = new JPanel();
            panelForm.setLayout(new GridLayout(0, 2));
            panelForm.setSize(100, 100);
            panelForm.add(new JLabel("Nombre:", SwingConstants.RIGHT));
            JTextField txtNombre = new JTextField();
            panelForm.add(txtNombre);
            panelForm.add(new JLabel("Edad:", SwingConstants.RIGHT));
            JTextField txtEdad = new JTextField();
            panelForm.add(txtEdad);
            panelForm.add(new JLabel("Estatura:", SwingConstants.RIGHT));
            JTextField txtEstatura = new JTextField();
            panelForm.add(txtEstatura);
            add(panelForm, BorderLayout.CENTER);

            // Configuración JFrame
            setSize(new Dimension(400, 400));
            setLocationRelativeTo(null); // Centrar
            setResizable(false);
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        private void hazEscuchas() {
            rdBtnNombre.addActionListener(this);
            rdBtnEdad.addActionListener(this);
            rdBtnEstatura.addActionListener(this);
            rdBtnEdadEstNombre.addActionListener(this);

            btnAñadir.addActionListener(this);
            btnConsultar.addActionListener(this);
            btnLimpiar.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() instanceof JRadioButton) {
                JRadioButton RadioButton = (JRadioButton) evt.getSource();
                if (RadioButton == rdBtnNombre) {
                    criterioAux = UtilsCriterioOrdenamiento.POR_NOMBRE;
                }
                if (RadioButton == rdBtnEdad) {
                    criterioAux = UtilsCriterioOrdenamiento.POR_EDAD;
                }
                if (RadioButton == rdBtnEstatura) {
                    criterioAux = UtilsCriterioOrdenamiento.POR_ESTATURA;
                }
                if (RadioButton == rdBtnEdadEstNombre) {
                    criterioAux = UtilsCriterioOrdenamiento.POR_EDAD_ESTATURA_NOMBRE;
                }
                Ordenamiento.ordenamientoIntercambio(lista, criterioAux);
                limpiarCampos();
                return;
            }

            if (evt.getSource() instanceof JButton) {
                JButton Button = (JButton) evt.getSource();
                if (Button == btnAñadir) {
                    lista.InsertaOrdenado(new Usuario(
                            txtNombre.getText(),
                            Integer.parseInt(txtEdad.getText()),
                            Double.parseDouble(txtEstatura.getText()),
                            criterioAux));
                    limpiarCampos();
                    return;
                }
                if (Button == btnAñadir) {
                    limpiarCampos();
                }
                if (Button == btnLimpiar) {
                    limpiarCampos();
                }

            }

        }

        public void limpiarCampos() {
            txtNombre.setText("");
            txtEdad.setText("");
            txtEstatura.setText("");
        }
    }

}
