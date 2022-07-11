import javax.swing.*;
import java.awt.event.*;

public class Main {
    public Main(){
        JFrame f = new JFrame("Main");
        JButton loginButton = new JButton("Login Pengguna");
        JButton registrasiButton = new JButton("Registrasi Pengguna Baru");
        JButton viewDataButton = new JButton("Lihat Data Pengguna");

        loginButton.setBounds(45, 70, 200, 30);
        registrasiButton.setBounds(45, 120, 200, 30);
        viewDataButton.setBounds(45, 170, 200, 30);

        Controller c = new Controller();

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                c.menuLogin();
            }
        });

        registrasiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                c.menuRegistrasi();
            }
        });
        
        viewDataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                c.menuViewData();
            }
        });


        f.add(loginButton);
        f.add(registrasiButton);
        f.add(viewDataButton);


        f.setSize(300, 400);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        new Main();
    }
}
