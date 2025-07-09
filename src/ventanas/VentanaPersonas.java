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

public class VentanaPersonas extends JFrame implements ActionListener {
    private Coordinador coordinator;
    private PersonDao personDao = new PersonDao();

    JLabel lblDocument, lblName, lblPhone, titleLabel;
    JTextField txtDocument, txtName, txtPhone;
    JButton btnRegister, btnQuery, btnUpdate, btnDelete, btnList, btnClear;
    JTextArea resultArea;
    JScrollPane scroll;

    public VentanaPersonas() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("GESTIONAR PERSONAS");
        setSize(480, 540);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        titleLabel = new JLabel("GESTIONAR PERSONAS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(0, 15, 480, 35);
        add(titleLabel);

        lblDocument = new JLabel("Documento:");
        lblDocument.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDocument.setBounds(30, 60, 90, 25);
        add(lblDocument);

        txtDocument = new JTextField();
        txtDocument.setBounds(120, 60, 90, 25);
        add(txtDocument);

        lblPhone = new JLabel("Teléfono:");
        lblPhone.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPhone.setBounds(230, 60, 70, 25);
        add(lblPhone);

        txtPhone = new JTextField();
        txtPhone.setBounds(300, 60, 120, 25);
        add(txtPhone);

        lblName = new JLabel("Nombre:");
        lblName.setFont(new Font("Arial", Font.PLAIN, 14));
        lblName.setBounds(30, 100, 90, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(120, 100, 300, 25);
        add(txtName);

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

    public void setCoordinador(Coordinador coordinator) {
        this.coordinator = coordinator;
    }

    private boolean validateAllFields() {
        String doc = txtDocument.getText().trim();
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();

        if (doc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo Documento no puede estar vacío.");
            txtDocument.requestFocus();
            return false;
        }
        if (!doc.matches("\\d{6,}")) {
            JOptionPane.showMessageDialog(this, "El documento debe ser numérico y tener al menos 6 dígitos.");
            txtDocument.requestFocus();
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
        if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo Teléfono no puede estar vacío.");
            txtPhone.requestFocus();
            return false;
        }
        if (!phone.matches("\\d{7,}")) {
            JOptionPane.showMessageDialog(this, "El teléfono debe ser numérico y tener al menos 7 dígitos.");
            txtPhone.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            if (!validateAllFields()) return;
            if (coordinator.personExists(txtDocument.getText().trim())) {
                resultArea.setText("ESTA PERSONA YA EXISTE");
            }else {
                PersonVo person = saveData();
                coordinator.registerPerson(person);
            }
            clearFields();
        }else if(e.getSource() == btnQuery){
            if (!validateAllFields()) return;
            PersonVo person = personDao.query(txtDocument.getText().trim());
            if (person != null) {
                resultArea.setText("Resultado: \n" + person.showInfo());
            }else {
                resultArea.setText("Persona no encontrada");
            }
            clearFields();

        } else if (e.getSource() == btnUpdate) {
            if (!validateAllFields()) return;
            PersonVo person  = saveData();
            if (person != null) {
                coordinator.updatePerson(person);
                resultArea.setText("Persona actualizada");
            }
            clearFields();

        } else if (e.getSource() == btnDelete) {
            if (!validateAllFields()) return;
            String doc = txtDocument.getText().trim();
            if (!doc.isEmpty()) {
                try {
                    coordinator.deletePerson(doc);
                    resultArea.setText("Persona eliminada");
                }catch (RuntimeException ex){
                    resultArea.setText(ex.getMessage());
                }

            }
            clearFields();

        } else if (e.getSource() == btnList) {
            String list = coordinator.listPerson();
            resultArea.setText(list);
            clearFields();

        } else if (e.getSource() == btnClear) {
            clearFields();
            resultArea.setText("");
        }
    }

    public PersonVo saveData() {
        String doc = txtDocument.getText().trim();
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();

        if (doc.isEmpty() || name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tiene que llenar todos los campos");
            return null;
        }

            PersonVo p = new PersonVo();
            p.setDocument(doc);
            p.setName(name);
            p.setPhone(phone);

        return p;
    }

    public void clearFields(){
        txtDocument.setText("");
        txtName.setText("");
        txtPhone.setText("");

    }

    public void validateFields(){
        if  (txtDocument.getText().trim().isEmpty() || txtName.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tiene que llenar todos los campos");
        }
    }

}
