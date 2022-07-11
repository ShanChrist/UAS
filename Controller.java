import javax.swing.*;

import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Controller {
    static DatabaseHandler conn = new DatabaseHandler();
    static Controller c = new Controller();
    static User user;
    JTextField loginEmail, registrasiEmail, registrasiNama;
    JPasswordField loginPassword, registrasiPassword;
    String photoPath;
    JComboBox registrasiCategory, listCategory;

    public Controller() {
    }

    public void menuLogin() {

        JFrame f = new JFrame("Login Pengguna");
        ImageIcon icon = new ImageIcon("icon.jfif");
        // Image scaleImage = icon.getImage().getScaledInstance(20, 20, 20);
        f.add(new JLabel(icon));

        JLabel emailLabel = new JLabel("Email : ");
        JLabel passwordLabel = new JLabel("Password : ");
        loginEmail = new JTextField();
        loginPassword = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");

        emailLabel.setBounds(90, 70, 100, 30);
        loginEmail.setBounds(200, 70, 100, 30);
        passwordLabel.setBounds(90, 100, 100, 30);
        loginPassword.setBounds(200, 100, 100, 30);
        loginButton.setBounds(100, 150, 70, 30);
        backButton.setBounds(200, 150, 70, 30);

        f.add(emailLabel);
        f.add(loginEmail);
        f.add(passwordLabel);
        f.add(loginPassword);
        f.add(loginButton);
        f.add(backButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean success = cekUser(loginEmail.getText(), loginPassword.getText());
                if (!success) {
                    JOptionPane.showMessageDialog(null, "Email atau Password salah");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new Main();
            }
        });

        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public boolean cekUser(String email, String password) {
        conn.connect();
        conn.connect();
        String query = "SELECT * FROM users WHERE email='" + email + "'&&pass='" + password + "'";
        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("email").equals(email) && rs.getString("password").equals(password)) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public void menuRegistrasi() {
        user = new User();
        JFrame f = new JFrame("Registrasi Pengguna Baru");
        JLabel namaLabel = new JLabel("Nama : ");
        JLabel emailLabel = new JLabel("Email : ");
        JLabel passwordLabel = new JLabel("Password : ");
        JLabel categoryLabel = new JLabel("Category : ");

        registrasiNama = new JTextField();
        registrasiEmail = new JTextField();
        registrasiPassword = new JPasswordField();

        JButton fotoInput = new JButton("Input Foto");
        fotoInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    photoPath = fileChooser.getSelectedFile().getAbsolutePath();
                    fotoInput.setText(photoPath);
                }
            }
        });
        String[] category = { "Private Account", "Creator Account", "Business Account" };
        registrasiCategory = new JComboBox(category);

        JButton registrasiButton = new JButton("Registrasi");
        JButton backButton = new JButton("Back");

        namaLabel.setBounds(5, 30, 200, 20);
        emailLabel.setBounds(5, 50, 200, 20);
        passwordLabel.setBounds(5, 70, 200, 20);
        categoryLabel.setBounds(5, 90, 200, 20);

        registrasiNama.setBounds(75, 30, 200, 20);
        registrasiEmail.setBounds(75, 50, 200, 20);
        registrasiPassword.setBounds(75, 70, 200, 20);
        registrasiCategory.setBounds(75, 90, 200, 20);
        fotoInput.setBounds(5, 120, 200, 20);
        registrasiButton.setBounds(5, 160, 100, 20);
        backButton.setBounds(150, 160, 100, 20);

        f.add(namaLabel);
        f.add(emailLabel);
        f.add(passwordLabel);
        f.add(categoryLabel);
        f.add(registrasiNama);
        f.add(registrasiEmail);
        f.add(registrasiPassword);
        f.add(registrasiCategory);
        f.add(fotoInput);
        f.add(registrasiButton);
        f.add(backButton);

        registrasiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                getData();
                boolean success = insertData();
                if (!success) {
                    JOptionPane.showMessageDialog(null, "Gagal Registrasi");
                } else {
                    menuViewData();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new Main();
            }
        });

        f.setSize(600, 600);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public boolean insertData() {
        conn.connect();
        String query = "INSERT INTO users (nama, email, pass, category, photoPath) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, user.name);
            stmt.setString(2, user.email);
            stmt.setString(3, user.password);
            stmt.setInt(4, user.idCategory);
            stmt.setString(5, user.photo);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return (false);
        }
    }

    public void getData() {
        user.name = registrasiNama.getText();
        user.email = registrasiEmail.getText();
        user.password = registrasiPassword.getText();
        user.idCategory = registrasiCategory.getSelectedIndex();
        user.photo = photoPath;
    }

    public void menuViewData() {
        JFrame f = new JFrame("View Data");
        JLabel categoryLabel = new JLabel("Category : ");
        String[] category = { "Private Account", "Creator Account", "Business Account" };
        listCategory = new JComboBox(category);
        JButton viewButton = new JButton("View");

        categoryLabel.setBounds(5, 30, 200, 20);
        listCategory.setBounds(75, 30, 200, 20);
        viewButton.setBounds(5, 60, 100, 20);

        f.add(categoryLabel);
        f.add(listCategory);
        f.add(viewButton);

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = listCategory.getSelectedIndex();
                f.dispose();
                viewData(index, category);
            }
        });

        f.setSize(300, 400);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void viewData(int index, String[] category) {
        ArrayList<User> users = c.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).idCategory == index) {
                JOptionPane.showMessageDialog(null, "Nama  : " + users.get(i).name + "\n" +
                        "Email : " + users.get(i).email + "\n" + "Category : " + category[index]);
            }
        }
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        conn.connect();
        String query = "SELECT * FROM users";
        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                User user = new User();
                user.id = rs.getInt("id");
                user.name = rs.getString("nama");
                user.email = rs.getString("email");
                user.password = rs.getString("pass");
                user.idCategory = rs.getInt("category");
                user.photo = rs.getString("photoPath");
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (users);
    }

}
