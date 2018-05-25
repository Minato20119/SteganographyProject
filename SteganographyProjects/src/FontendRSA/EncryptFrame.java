/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FontendRSA;

import BackendRSA.RSAUtil;
import BackendRSA.SentSite;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Minato
 */
public class EncryptFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public EncryptFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        buttonOpenImage = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        pathOpenImage = new javax.swing.JTextField();
        pathSaveImage = new javax.swing.JTextField();
        buttonSaveImage = new javax.swing.JButton();
        pathFileText = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        plainText = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        publicKey = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        cipherText = new javax.swing.JTextArea();
        buttonEncrypt = new javax.swing.JButton();
        buttonClose = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        cipherBinary = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        imageAfterStego = new javax.swing.JLabel();
        imageBeforeStego = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("ẨN MÃ");
        jLabel1.setToolTipText("");

        buttonOpenImage.setText("Open Image");
        buttonOpenImage.setMaximumSize(new java.awt.Dimension(91, 25));
        buttonOpenImage.setMinimumSize(new java.awt.Dimension(91, 25));
        buttonOpenImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOpenImageActionPerformed(evt);
            }
        });

        jButton2.setText("Open File Text");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        pathOpenImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathOpenImageActionPerformed(evt);
            }
        });

        pathSaveImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathSaveImageActionPerformed(evt);
            }
        });

        buttonSaveImage.setText("Save Image");
        buttonSaveImage.setMaximumSize(new java.awt.Dimension(90, 25));
        buttonSaveImage.setMinimumSize(new java.awt.Dimension(90, 25));
        buttonSaveImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveImageActionPerformed(evt);
            }
        });

        pathFileText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathFileTextActionPerformed(evt);
            }
        });

        plainText.setColumns(20);
        plainText.setRows(5);
        jScrollPane1.setViewportView(plainText);

        jLabel2.setText("PLAIN TEXT");

        jLabel3.setText("PUBLIC KEY");

        publicKey.setColumns(20);
        publicKey.setRows(5);
        jScrollPane2.setViewportView(publicKey);

        jLabel4.setText("CIPHER TEXT");

        cipherText.setColumns(20);
        cipherText.setRows(5);
        jScrollPane3.setViewportView(cipherText);

        buttonEncrypt.setText("ẨN MÃ");
        buttonEncrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEncryptActionPerformed(evt);
            }
        });

        buttonClose.setText("CLOSE");
        buttonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCloseActionPerformed(evt);
            }
        });

        jLabel5.setText("CIPHER BINARY");

        cipherBinary.setColumns(20);
        cipherBinary.setRows(5);
        jScrollPane4.setViewportView(cipherBinary);

        jLabel6.setText("Minato");

        imageAfterStego.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        imageBeforeStego.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText("Ảnh chưa nhúng tin");

        jLabel8.setText("Ảnh đã nhúng tin");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(540, 540, 540)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(171, 171, 171))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(368, 368, 368)
                        .addComponent(buttonEncrypt, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                        .addComponent(buttonClose, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(buttonSaveImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(buttonOpenImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5))
                                .addGap(54, 54, 54)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pathSaveImage)
                            .addComponent(pathFileText)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane4)
                            .addComponent(pathOpenImage, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(imageBeforeStego, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(imageAfterStego, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 23, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(176, 176, 176))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(14, 14, 14)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buttonOpenImage, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(pathOpenImage))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pathSaveImage)
                            .addComponent(buttonSaveImage, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pathFileText, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel2)
                                .addGap(60, 60, 60)
                                .addComponent(jLabel3)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel5)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(buttonEncrypt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonClose, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(53, 53, 53))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(262, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(imageBeforeStego, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imageAfterStego, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonOpenImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOpenImageActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(chooser);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You choose to open this file: "
                    + chooser.getSelectedFile().getName());
            pathOpenImage.setText(chooser.getSelectedFile().getAbsolutePath());
        }

        if (!"".equals(pathOpenImage.getText())) {
            try {

                BufferedImage image = ImageIO.read(new File(pathOpenImage.getText()));

                Image dimg = image.getScaledInstance(imageBeforeStego.getWidth(), imageBeforeStego.getHeight(),
                        Image.SCALE_SMOOTH);

                ImageIcon imageOpen = new ImageIcon(dimg);

                imageBeforeStego.setIcon(imageOpen);

            } catch (IOException ex) {
                Logger.getLogger(EncryptFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_buttonOpenImageActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Text File", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(chooser);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You choose to open this file: "
                    + chooser.getSelectedFile().getName());
            pathFileText.setText(chooser.getSelectedFile().getAbsolutePath());
            try {
                plainText.setText(SentSite.inputTextFile(pathFileText.getText()));
            } catch (IOException ex) {
                Logger.getLogger(EncryptFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void pathSaveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathSaveImageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pathSaveImageActionPerformed

    private void buttonSaveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveImageActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "png");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Specify a file to save");
        int returnVal = chooser.showSaveDialog(chooser);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You choose to save this file: "
                    + chooser.getSelectedFile().getName());
            pathSaveImage.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_buttonSaveImageActionPerformed

    private void pathOpenImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathOpenImageActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_pathOpenImageActionPerformed

    private void pathFileTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathFileTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pathFileTextActionPerformed

    private void buttonEncryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEncryptActionPerformed
        // TODO add your handling code here:

        if (pathFileText.getText().equals("") && plainText.getText().equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Please enter text OR choose file text!", "WARNING",
                    JOptionPane.WARNING_MESSAGE);
        }

        if (publicKey.getText().equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Please input public key!", "WARNING",
                    JOptionPane.WARNING_MESSAGE);
        }

        if (pathSaveImage.getText().equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Please choose save file new image!", "WARNING",
                    JOptionPane.WARNING_MESSAGE);
        }

        if (!plainText.getText().equals("") && !publicKey.getText().equals("") && !pathSaveImage.getText().equals("")) {

            String textEncrypt = "";
            try {
                textEncrypt = RSAUtil.getTextToEncrypt(plainText.getText(), publicKey.getText());
            } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException ex) {
                JOptionPane.showMessageDialog(null,
                        "Public key is invalid!", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(EncryptFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            cipherText.setText(textEncrypt);

            String textBinary = SentSite.convertTextToBinary(textEncrypt);
            cipherBinary.setText(textBinary);

            BufferedImage image = SentSite.inputImage(pathOpenImage.getText());
            SentSite.getValueRGB(image, textBinary);

            // Write new image
            SentSite.writeNewImage(image, pathSaveImage.getText());

            // Preview image stego
            Image dimg = image.getScaledInstance(imageAfterStego.getWidth(), imageAfterStego.getHeight(),
                    Image.SCALE_SMOOTH);
            ImageIcon imageStego = new ImageIcon(dimg);
            imageAfterStego.setIcon(imageStego);

            JOptionPane.showMessageDialog(null,
                    "Encrypt complete!", "INFORMATION",
                    JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_buttonEncryptActionPerformed

    private void buttonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCloseActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_buttonCloseActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EncryptFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new EncryptFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonClose;
    private javax.swing.JButton buttonEncrypt;
    private javax.swing.JButton buttonOpenImage;
    private javax.swing.JButton buttonSaveImage;
    private javax.swing.JTextArea cipherBinary;
    private javax.swing.JTextArea cipherText;
    private javax.swing.JLabel imageAfterStego;
    private javax.swing.JLabel imageBeforeStego;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField pathFileText;
    private javax.swing.JTextField pathOpenImage;
    private javax.swing.JTextField pathSaveImage;
    private javax.swing.JTextArea plainText;
    private javax.swing.JTextArea publicKey;
    // End of variables declaration//GEN-END:variables
}
