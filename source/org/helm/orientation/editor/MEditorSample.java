package org.helm.orientation.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.helm.editor.editor.MacromoleculeEditor;
import org.helm.notation.MonomerFactory;

/**
 *
 * @author ZHANGTIANHONG
 */
public class MEditorSample extends JFrame {
    
    private MacromoleculeEditor editor;
    
    public MEditorSample() throws Exception {
        setTitle("HELM Editor Sample");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        
        //create editor
        editor = new MacromoleculeEditor();
        
        //create buttons
        JPanel buttonPanel = createButtonPanel();
        
        setJMenuBar(editor.getMenuBar());
        setLayout(new BorderLayout());
        getContentPane().add(BorderLayout.CENTER, editor.getContentComponent());
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
               
        pack();
    }

    /**
     * create a panel to hold the setNotation and getNotation Buttons
     *
     * @return JPanel
     */
    private JPanel createButtonPanel() {
        JButton setNotationButton = new JButton("setNotation()");
        setNotationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String notation = JOptionPane.showInputDialog(null, "Please enter HELM notation here");
                if (notation != null && notation.length() > 0) {
                    editor.setNotation(notation);
                }
            }
        });
        
        JButton getNotationButton = new JButton("getNotation()");
        getNotationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, editor.getNotation());
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(setNotationButton);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(getNotationButton);
        buttonPanel.add(Box.createHorizontalStrut(5));
        return buttonPanel;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            //initialize monomer database
            MonomerFactory.getInstance();
            
            MEditorSample mEditor = new MEditorSample();
            mEditor.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
