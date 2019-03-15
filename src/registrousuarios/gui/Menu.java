package registrousuarios.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import utils.*;

/**
 *
 * @author Carlos Contreras
 */
public class Menu extends JFrame implements ActionListener {

    private JTextField txtNombre, txtEdad, txtEstatura;
    private JRadioButton rdBtnNombre, rdBtnEdad, rdBtnEstatura, rdBtnEdadEstNombre;
    private ButtonGroup grupoRdBtn;
    private JButton btnAñadir, btnConsultar, btnLimpiar;
    private JDialog modalResultados;

    private Lista<Usuario> lista = new Lista<>();
    private int criterioAux = UtilsCriterioOrdenamiento.POR_NOMBRE; // Default

    public Menu() {
        super("Registro de Usuarios");
        hazInterfaz();
        hazEscuchas();
    }

    private void hazInterfaz() {
//
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
        panelRdBtn.setLayout(new FlowLayout());
        panelRdBtn.add(rdBtnNombre);
        panelRdBtn.add(rdBtnEdad);
        panelRdBtn.add(rdBtnEstatura);
        panelRdBtn.add(rdBtnEdadEstNombre);
        add(panelRdBtn, BorderLayout.NORTH);


        // Formulario
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(0, 2, 20, 10));
        panelForm.add(new JLabel("Nombre  ", SwingConstants.RIGHT));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Edad  ", SwingConstants.RIGHT));
        txtEdad = new JTextField();
        panelForm.add(txtEdad);
        panelForm.add(new JLabel("Estatura  ", SwingConstants.RIGHT));
        txtEstatura = new JTextField();
        panelForm.add(txtEstatura);
        add(panelForm, BorderLayout.CENTER);

        // Botones 
        btnAñadir = new JButton("Añadir");
        btnConsultar = new JButton("Consultar");
        btnLimpiar = new JButton("Limpiar");

        JPanel panelBtn = new JPanel();
        panelBtn.add(btnAñadir);
        panelBtn.add(btnConsultar);
        panelBtn.add(btnLimpiar);
        add(panelBtn, BorderLayout.SOUTH);
        
        // Configuración JFrame
        setSize(400, 200);
        setLocationRelativeTo(null); // Centrar
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        txtNombre.requestFocus();
    }

    private void hazEscuchas() {
        // RadioButton
        rdBtnNombre.addActionListener(this);
        rdBtnEdad.addActionListener(this);
        rdBtnEstatura.addActionListener(this);
        rdBtnEdadEstNombre.addActionListener(this);

        // Button
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
            return;
        }

        if (evt.getSource() instanceof JButton) {
            JButton Button = (JButton) evt.getSource();
            if (Button == btnAñadir) {
                lista.InsertaOrdenado(new Usuario(txtNombre.getText(), Integer.parseInt(txtEdad.getText()), Double.parseDouble(txtEstatura.getText()), criterioAux));
                limpiarCampos();
                return;
            }
            if (Button == btnConsultar) {
                new ModalResultados();
                return;
            }
            if (Button == btnLimpiar) {
                limpiarCampos();
                return;
            }
        }

    }

    private void limpiarCampos() {
        txtNombre.setText(null);
        txtEdad.setText(null);
        txtEstatura.setText(null);
        txtNombre.requestFocus();
    }

    private class ModalResultados extends JDialog {

        private DefaultTableModel modeloTabla;
        private JTable tablaResultados;
        private JScrollPane scrollPane;
        private String[] nombres;
        private Integer[] edades;
        private Double[] estaturas;

        public ModalResultados() {
            setTitle("Tabla de Resultados");
            modeloTabla = new DefaultTableModel();
            tablaResultados = new JTable(modeloTabla);
            scrollPane = new JScrollPane(tablaResultados);

            rellenarResultados();
            add(scrollPane);

            setSize(300, 400);
            setModal(true);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private void rellenarResultados() {
            nombres = new String[lista.Length()];
            edades = new Integer[lista.Length()];
            estaturas = new Double[lista.Length()];

            Nodo<Usuario> aux = lista.getFrente();
            int i = 0;
            while (aux != null) {
                nombres[i] = aux.Info.getNombre();
                edades[i] = aux.Info.getEdad();
                estaturas[i] = aux.Info.getEstatura();
                i++;
                aux = aux.getSig();
            }
            modeloTabla.addColumn("Nombre", nombres);
            modeloTabla.addColumn("Edad", edades);
            modeloTabla.addColumn("Estatura", estaturas);
        }

    }
}
