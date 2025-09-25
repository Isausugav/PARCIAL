import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OperadorLogistico extends JFrame {
    private JTextField txtCodigo, txtCliente, txtPeso, txtDistancia;
    private JComboBox<String> cmbTipo;
    private DefaultTableModel modelo;
    private ArrayList<Envio> listaEnvios;

    public OperadorLogistico() {
        setTitle("Operador Logístico");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        listaEnvios = new ArrayList<>();

        // Panel superior con iconos
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));

        ImageIcon iconAdd = new ImageIcon(getClass().getResource("agregar.png"));
        Image imgAdd = iconAdd.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton btnAgregar = new JButton(new ImageIcon(imgAdd));

        ImageIcon iconDel = new ImageIcon(getClass().getResource("eliminar.png"));
        Image imgDel = iconDel.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton btnEliminar = new JButton(new ImageIcon(imgDel));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.NORTH);

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(3, 4, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panelForm.add(new JLabel("Número:"));
        txtCodigo = new JTextField();
        panelForm.add(txtCodigo);

        panelForm.add(new JLabel("Tipo:"));
        cmbTipo = new JComboBox<>(new String[]{"Terrestre", "Aéreo", "Marítimo"});
        panelForm.add(cmbTipo);

        panelForm.add(new JLabel("Cliente:"));
        txtCliente = new JTextField();
        panelForm.add(txtCliente);

        panelForm.add(new JLabel("Distancia en Km:"));
        txtDistancia = new JTextField();
        panelForm.add(txtDistancia);

        panelForm.add(new JLabel("Peso:"));
        txtPeso = new JTextField();
        panelForm.add(txtPeso);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        panelForm.add(btnGuardar);
        panelForm.add(btnCancelar);

        add(panelForm, BorderLayout.CENTER);

        // Tabla
        modelo = new DefaultTableModel(new String[]{
                "Tipo","Código","Cliente","Peso","Distancia","Costo"},0);
        JTable tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.SOUTH);

        // Eventos
        btnGuardar.addActionListener(e -> agregarEnvio());
        btnAgregar.addActionListener(e -> agregarEnvio());
        btnEliminar.addActionListener(e -> eliminarEnvio());
        btnCancelar.addActionListener(e -> limpiarCampos());
    }

    private void agregarEnvio() {
        try {
            String codigo = txtCodigo.getText();
            String cliente = txtCliente.getText();
            double peso = Double.parseDouble(txtPeso.getText());
            double distancia = Double.parseDouble(txtDistancia.getText());
            String tipo = (String)cmbTipo.getSelectedItem();

            Envio envio;
            switch (tipo) {
                case "Aéreo":
                    envio = new EnvioAereo(codigo, cliente, peso, distancia);
                    break;
                case "Marítimo":
                    envio = new EnvioMaritimo(codigo, cliente, peso, distancia);
                    break;
                default:
                    envio = new EnvioTerrestre(codigo, cliente, peso, distancia);
            }
            listaEnvios.add(envio);
            actualizarTabla();
            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,"Revisa los valores numéricos.");
        }
    }

    private void eliminarEnvio() {
        String codigo = JOptionPane.showInputDialog(this,"Ingrese el código a eliminar:");
        if(codigo!=null) {
            boolean eliminado = listaEnvios.removeIf(e->e.getCodigo().equals(codigo));
            if(eliminado) JOptionPane.showMessageDialog(this,"Envío eliminado.");
            else JOptionPane.showMessageDialog(this,"Código no encontrado.");
            actualizarTabla();
        }
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        for(Envio envio:listaEnvios){
            String tipo;
            if(envio instanceof EnvioAereo) tipo="Aéreo";
            else if(envio instanceof EnvioMaritimo) tipo="Marítimo";
            else tipo="Terrestre";

            modelo.addRow(new Object[]{
                    tipo,
                    envio.getCodigo(),
                    envio.getCliente(),
                    envio.getPeso(),
                    envio.getDistancia(),
                    envio.calcularTarifa()
            });
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtCliente.setText("");
        txtPeso.setText("");
        txtDistancia.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OperadorLogistico().setVisible(true));
    }
}
