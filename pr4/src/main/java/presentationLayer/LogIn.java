package presentationLayer;

import businessLogicLayer.*;

import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LogIn extends JFrame {
    private JTextField textField;
    private JPasswordField passwordField;

    EmployeeView employee;
    public int employeeView = 1;

    public LogIn(DeliveryServiceBLL deliveryService) {
        getContentPane().setLayout(null);
        employee = new EmployeeView(deliveryService);

        deliveryService.addObserver(employee);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setTitle("Food Delivery Management System");
        this.setBounds(100, 100, 500, 230);
        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel.setBounds(216, 25, 73, 43);
        getContentPane().add(lblNewLabel);

        JButton btnReg = new JButton("Register");
        btnReg.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnReg.setBounds(300, 100, 100, 21);
        btnReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pass = new String(passwordField.getPassword());
                int found = 0;
                for (UserBLL i : deliveryService.Users) {
                    if (i.getUsername().equals(textField.getText()) && i.getPassword().equals(pass))
                    {JOptionPane.showMessageDialog(null, "Client already registered.", null, JOptionPane.INFORMATION_MESSAGE);
                    found = 1;}
                    }
                if(found == 0)
                {deliveryService.registerUser(textField.getText(), pass);
                JOptionPane.showMessageDialog(null, "Client registered.", null, JOptionPane.INFORMATION_MESSAGE);}

            }

        });
        getContentPane().add(btnReg);

        JButton btnAdmin = new JButton("Administrator");
        btnAdmin.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnAdmin.setBounds(40, 148, 117, 21);
        btnAdmin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String pass = new String(passwordField.getPassword());
                if (textField.getText().equals("admin") && pass.equals("admin")) {
                    AdministratorView a = new AdministratorView(deliveryService);
                } else {
                    JOptionPane.showMessageDialog(null, "User not found.", null, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        getContentPane().add(btnAdmin);

        JButton btnClient = new JButton("Client");
        btnClient.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnClient.setBounds(190, 148, 117, 21);
        btnClient.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int found = 0;
                String pass = new String(passwordField.getPassword());
//                if (deliveryService.Users.isEmpty()) {
//                    deliveryService.registerUser(textField.getText(), pass);
//                    JOptionPane.showMessageDialog(null, "Client registered.", null, JOptionPane.INFORMATION_MESSAGE);
//                }
                for (UserBLL i : deliveryService.Users) {
                    if (i.getUsername().equals(textField.getText()) && i.getPassword().equals(pass)) {
                        ClientView c = new ClientView(deliveryService, i);
                        found = 1;
                    }
                }
                if (found == 0) {
                    JOptionPane.showMessageDialog(null, "User not found.", null, JOptionPane.INFORMATION_MESSAGE);
                }
            }

        });
        getContentPane().add(btnClient);

        JButton btnEmployee = new JButton("Employee");
        btnEmployee.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (employeeView == 1) {
                    employee.frame.setVisible(true);
                    employeeView = 0;
                } else {
                    employee.frame.dispose();
                    employeeView = 1;
                }
            }
        });
        btnEmployee.setBounds(335, 148, 117, 21);
        getContentPane().add(btnEmployee);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblUsername.setBounds(40, 80, 73, 13);
        getContentPane().add(lblUsername);

        JLabel lblPassw = new JLabel("Password");
        lblPassw.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblPassw.setBounds(40, 110, 73, 13);
        getContentPane().add(lblPassw);

        textField = new JTextField();
        textField.setBounds(112, 77, 128, 19);
        getContentPane().add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(112, 103, 128, 19);
        passwordField.setEchoChar('*');
        getContentPane().add(passwordField);
        this.setVisible(true);
    }
}