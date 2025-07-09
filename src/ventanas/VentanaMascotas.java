package ventanas;

import controlador.Coordinador;
import dao.PetDao;
import dao.PersonDao;
import vo.PetVo;
import vo.PersonVo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaMascotas extends JFrame implements ActionListener {
    private Coordinador coordinator;
    private PetDao petDao = new PetDao();

    JLabel lblId, lblName, lblBreed, lblGender, titleLabel;
    JTextField txtId, txtName, txtBreed, txtGender;
    JButton btnRegister, btnQuery, btnUpdate, btnDelete, btnList, btnClear;
    JTextArea resultArea;
    JScrollPane scroll;

    public VentanaMascotas() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("GESTIONAR MASCOTAS");
        setSize(480, 540);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        titleLabel = new JLabel("GESTIONAR MASCOTAS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(0, 15, 480, 35);
        add(titleLabel);

        lblId = new JLabel("id Dueño:");
        lblId.setFont(new Font("Arial", Font.PLAIN, 14));
        lblId.setBounds(30, 60, 90, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(120, 60, 90, 25);
        add(txtId);

        lblBreed = new JLabel("Raza:");
        lblBreed.setFont(new Font("Arial", Font.PLAIN, 14));
        lblBreed.setBounds(230, 60, 70, 25);
        add(lblBreed);

        txtBreed = new JTextField();
        txtBreed.setBounds(300, 60, 120, 25);
        add(txtBreed);

        lblName = new JLabel("Nombre:");
        lblName.setFont(new Font("Arial", Font.PLAIN, 14));
        lblName.setBounds(30, 100, 90, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(120, 100, 90, 25);
        add(txtName);

        lblGender = new JLabel("Sexo:");
        lblGender.setFont(new Font("Arial", Font.PLAIN, 14));
        lblGender.setBounds(230, 100, 70, 25);
        add(lblGender);

        txtGender = new JTextField();
        txtGender.setBounds(300, 100, 120, 25);
        add(txtGender);

        btnRegister = new JButton("Registrar");
        btnRegister.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRegister.setBounds(30, 150, 170, 32);
        btnRegister.addActionListener(this);
        add(btnRegister);

        btnQuery = new JButton("Consultar");
        btnQuery.setFont(new Font("Arial", Font.PLAIN, 14));
        btnQuery.setBounds(210, 150, 170, 32);
        btnQuery.addActionListener(this);
        add(btnQuery);

        btnUpdate = new JButton("Actualizar");
        btnUpdate.setFont(new Font("Arial", Font.PLAIN, 14));
        btnUpdate.setBounds(30, 192, 170, 32);
        btnUpdate.addActionListener(this);
        add(btnUpdate);

        btnDelete = new JButton("Eliminar");
        btnDelete.setFont(new Font("Arial", Font.PLAIN, 14));
        btnDelete.setBounds(210, 192, 170, 32);
        btnDelete.addActionListener(this);
        add(btnDelete);

        btnList = new JButton("ConsultarLista");
        btnList.setFont(new Font("Arial", Font.PLAIN, 14));
        btnList.setBounds(30, 234, 350, 32);
        btnList.addActionListener(this);
        add(btnList);

        btnClear = new JButton("Limpiar");
        btnClear.setFont(new Font("Arial", Font.PLAIN, 14));
        btnClear.setBounds(150, 470, 170, 28);
        btnClear.addActionListener(this);
        add(btnClear);

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        scroll = new JScrollPane(resultArea);
        scroll.setBounds(30, 280, 390, 175);
        add(scroll);
    }

    private boolean validateAllFields() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String breed = txtBreed.getText().trim();
        String gender = txtGender.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo id Dueño no puede estar vacío.");
            txtId.requestFocus();
            return false;
        }
        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "El id Dueño debe ser numérico.");
            txtId.requestFocus();
            return false;
        }
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo Nombre no puede estar vacío.");
            txtName.requestFocus();
            return false;
        }
        if (!name.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]{2,}")) {
            JOptionPane.showMessageDialog(this, "El nombre solo puede contener letras y espacios.");
            txtName.requestFocus();
            return false;
        }
        if (breed.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo Raza no puede estar vacío.");
            txtBreed.requestFocus();
            return false;
        }
        if (!breed.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]{2,}")) {
            JOptionPane.showMessageDialog(this, "La raza solo puede contener letras y espacios.");
            txtBreed.requestFocus();
            return false;
        }
        if (gender.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo Sexo no puede estar vacío.");
            txtGender.requestFocus();
            return false;
        }
        if (!gender.equalsIgnoreCase("M") && !gender.equalsIgnoreCase("F")) {
            JOptionPane.showMessageDialog(this, "El sexo debe ser 'M' (masculino) o 'F' (femenino).");
            txtGender.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            if (!validateAllFields()) return;
            PetVo pet = saveData();
            try {
                coordinator.registerPet(pet);
                resultArea.setText("Mascota registrada");
            }catch (RuntimeException ex){
                resultArea.setText(ex.getMessage());
            }
            clearFields();

        } else if (e.getSource() == btnQuery) {
            if (!validateAllFields()) return;
            PetVo pet = petDao.query(txtName.getText().trim());

            if (pet != null) {
                resultArea.setText("Resultado: \n" + pet.showInfo());
            }else {
                resultArea.setText("Mascota no encontrada");
            }
            clearFields();

        } else if (e.getSource() == btnUpdate) {
            if (!validateAllFields()) return;
            PetVo pet  = saveData();
            if (pet != null) {
                coordinator.updatePet(pet);
                resultArea.setText("Mascota actualizada");
            }
            clearFields();

        } else if (e.getSource() == btnDelete) {
            if (!validateAllFields()) return;
            String doc = txtName.getText().trim();
            if (!doc.isEmpty()) {
                coordinator.deletePet(doc);
                resultArea.setText("Mascota eliminada");
            }
            clearFields();

        } else if (e.getSource() == btnList) {

            String list = coordinator.listPet();
            resultArea.setText(list);

        } else if (e.getSource() == btnClear) {
            clearFields();
            resultArea.setText("");
        }

    }

    public PetVo saveData(){
        PetVo m = new PetVo();
        m.setOwnerId(txtId.getText().trim());
        m.setName(txtName.getText().trim());
        m.setBreed(txtBreed.getText().trim());
        m.setGender(txtGender.getText().trim());
        return m;
    }

    public void clearFields(){
        txtId.setText("");
        txtName.setText("");
        txtBreed.setText("");
        txtGender.setText("");

    }

    public void validateFields(){
        if  (txtId.getText().trim().isEmpty() || txtName.getText().trim().isEmpty() || txtGender.getText().trim().isEmpty() || txtBreed.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tiene que llenar todos los campos");
        }
    }

    public void setCoordinador(Coordinador coordinator) {
        this.coordinator = coordinator;
    }

}
