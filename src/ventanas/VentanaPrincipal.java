package ventanas;

import controlador.Coordinador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private JButton btnPersons;
    private JButton btnPets;
    private Coordinador coordinator;
    private JLabel titleLabel;

    public VentanaPrincipal() {
        setTitle("Sistema veterinaria");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        titleLabel = new JLabel("Sistema veterinaria", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(0, 20, 500, 40);
        add(titleLabel);

        btnPersons = new JButton("Gestionar Personas");
        btnPersons.setFont(new Font("Arial", Font.PLAIN, 16));
        btnPersons.setBounds(65, 270, 180, 45);
        btnPersons.addActionListener(this);
        add(btnPersons);

        btnPets = new JButton("Gestionar Mascotas");
        btnPets.setFont(new Font("Arial", Font.PLAIN, 16));
        btnPets.setBounds(260, 270, 180, 45);
        btnPets.addActionListener(this);
        add(btnPets);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPersons){
            coordinator.showPersons();
        } else if (e.getSource() == btnPets) {
            coordinator.showPets();
        }
    }

    public void setCoordinador(Coordinador coordinator) {
        this.coordinator = coordinator;
    }
}
