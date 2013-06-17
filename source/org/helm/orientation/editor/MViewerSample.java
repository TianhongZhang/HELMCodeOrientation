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
import org.helm.editor.editor.MacroMoleculeViewer;
import org.helm.notation.MonomerFactory;

/**
 *
 * @author ZHANGTIANHONG
 */
public class MViewerSample extends JFrame {

    private MacroMoleculeViewer viewer;

    public MViewerSample() {
        setTitle("HELM Viewer Sample");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        
        //create viewer
        viewer = new MacroMoleculeViewer(false);

        //create buttons
        JPanel buttonPanel = createButtonPanel();

        setLayout(new BorderLayout());
        getContentPane().add(viewer, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }
    
    /**
     * create a panel to hold the setNotation and getNotation Buttons
     * @return JPanel
     */
    private JPanel createButtonPanel() {
        JButton setNotationButton = new JButton("setNotation()");
        setNotationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String notation = JOptionPane.showInputDialog(null, "Please enter HELM notation here");
                if (notation != null && notation.length() > 0) {
                    viewer.setNotation(notation);
                } else {
                    viewer.setNotation("");
                }
            }
        });

        JButton getNotationButton = new JButton("getNotation()");
        getNotationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, viewer.getNotation());
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

    public static void main(String[] args) {
        try {
            //initialize monomer database
            MonomerFactory.getInstance();
            
            MViewerSample sample = new MViewerSample();
            sample.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        } 
    }
}
