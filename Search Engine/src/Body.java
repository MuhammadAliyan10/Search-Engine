import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;

public class Body extends JFrame {
    Body() {
        // !GUI Edit
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
        setLayout(null);
        setSize(1285, 725);
        setLocationRelativeTo(null);
        setTitle("Google");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("Images/logo.png")).getImage());

        // !Main Heading
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("Images/Google.png"));
        JLabel image = new JLabel(icon);
        image.setBounds(335, 100, 600, 200);
        add(image);

        // Input Field
        JTextField searchInpuTextField = new JTextField();
        searchInpuTextField.setBounds(240, 370, 800, 40);
        searchInpuTextField.setPreferredSize(new Dimension(70, 30));
        searchInpuTextField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        searchInpuTextField.setForeground(new Color(232, 234, 237));
        searchInpuTextField.setCaretColor(new Color(189, 193, 198));
        searchInpuTextField.setBorder(BorderFactory.createLineBorder(new Color(189, 193, 198), 1));
        searchInpuTextField.setOpaque(false);
        add(searchInpuTextField);

        // Search Button
        ImageIcon buttonIcon = new ImageIcon("Images/search.png");

        JButton iconButton = new JButton(buttonIcon);
        iconButton.setBounds(260, 500, 100, 100);
        add(iconButton);

    }

    public static void main(String[] args) {
        new Body();
    }
}
