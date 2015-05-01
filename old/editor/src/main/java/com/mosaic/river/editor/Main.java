package com.mosaic.river.editor;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class Main extends JFrame {

    public Main() {
        setTitle("Simple example");
        setSize(300, 200);



        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
//        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane pane = new JScrollPane();
//        JTextPane textpane = new JTextPane();
        JEditorPane textpane = new JEditorPane();

//        textpane.setContentType("text/html");
        textpane.setEditable(true);

        textpane.setBackground( Color.BLACK );
        textpane.setForeground( Color.WHITE );
        textpane.setCaretColor( Color.YELLOW );

//        textpane.getCaretPosition()
//        textpane.getDocument().

//        String cd = System.getProperty("user.dir") + "/";
//
//        try {
//            textpane.setPage("File:///" + cd + "test.html");
//        } catch (IOException e) {
//            System.out.println("Exception: " + e);
//        }

//        textpane.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        pane.getViewport().add(textpane);
        panel.add(pane);

        add(panel);



        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Main ex = new Main();
                ex.setVisible(true);
            }
        });
    }
}