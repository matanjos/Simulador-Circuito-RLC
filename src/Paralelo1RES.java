
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jonas-ws
 */
public class Paralelo1RES extends javax.swing.JFrame {
    /**
     * Creates new form Menu
     */
    public Paralelo1RES() {
        initComponents();
        
    }
    public Paralelo1RES(RLCparalelo c2){
        double K;
        Dimension size = new Dimension(1280,700 );
        this.setSize(1280, 700);
        this.setPreferredSize(size);
        DecimalFormat nf = new DecimalFormat("#.####");
        initComponents();
        String path1 = "/imagens/8,24semNum.png";
        imgSe.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon(getClass().getResource(path1)).getImage().getScaledInstance(imgSe.getWidth(),imgSe.getHeight(), Image.SCALE_SMOOTH)));
    
        imgSe.add(fonteCorrente);
        imgSe.add(R1);
        imgSe.add(R2);
        imgSe.add(L);
        imgSe.add(C);
    
        fonteCorrente.setLocation((int)(imgSe.getWidth()*0), (int)(imgSe.getHeight()*0.55));
        R1.setLocation((int)(imgSe.getWidth()*0.2), (int)(imgSe.getHeight()*0.55));
        R2.setLocation((int)(imgSe.getWidth()*0.55), (int)(imgSe.getHeight()*0.55));
        L.setLocation((int)(imgSe.getWidth()*0.7), (int)(imgSe.getHeight()*0.55));
        C.setLocation((int)(imgSe.getWidth()*0.34), (int)(imgSe.getHeight()*0.55));

        R1.setText(nf.format(c2.R1) + " Ω");
        R1.setFont(new Font(R1.getFont().getName(), Font.PLAIN, fontSize(R1)));   

        R2.setText(nf.format(c2.R2) + " Ω");
        R2.setFont(new Font(R2.getFont().getName(), Font.PLAIN, fontSize(R2)));   

        fonteCorrente.setText(nf.format(c2.fonteCorrente) + " A");
        fonteCorrente.setFont(new Font(fonteCorrente.getFont().getName(), Font.PLAIN, fontSize(fonteCorrente)));   

        C.setText(nf.format(c2.C1) + " F");
        C.setFont(new Font(C.getFont().getName(), Font.PLAIN, fontSize(C)));   

        L.setText(nf.format(c2.L1) + " H");
        L.setFont(new Font(L.getFont().getName(), Font.PLAIN, fontSize(L)));   

        V0.setText("I(0) ="+ nf.format(c2.I0) +" A");
        V0.setFont(new Font(V0.getFont().getName(), Font.PLAIN, fontSize(V0)));

        c2.alfa=c2.calcularALFA(c2.R2,c2.C1);
        c2.omega=c2.calcularOMEGA(c2.L1,c2.C1);
        
        c2.calcularS1(c2.alfa, c2.omega);
        
        K=c2.I0 /c2.C1;
        c2.B=c2.sistemaLinear(1.0, 1.0, c2.I0, c2.s2, c2.s1,K);
        c2.A=c2.calcularA(0.0, 1.0, c2.B, 1.0);
        
        FreqRval.setText(nf.format(c2.omega) + " rad/s");
        FreqNval.setText(nf.format(c2.alfa) + " Np/s");

        switch (c2.Amortecimento(c2.alfa,c2.omega )) {
            case 1:
                Amortecimentoval.setText("Supercrítico");
                if(c2.A != 0 && c2.B != 0)
                    ResNaturalval.setText("I(t) = "+nf.format(c2.B)+"*e^("+nf.format(c2.s2)+")*t + ("+nf.format(c2.A)+"*e^("+nf.format(c2.s1)+")*t) A" );
                else if(c2.A == 0)
                    ResNaturalval.setText("I(t) = "+nf.format(c2.B)+"*e^("+nf.format(c2.s2)+")*t A" );
                else
                    ResNaturalval.setText("I(t) = "+nf.format(c2.A)+"*e^("+nf.format(c2.s1)+")*t A" );

                break;
            case 2:
                Amortecimentoval.setText("Crítico");
                c2.A = c2.I0;
                c2.B = c2.A*c2.alfa + c2.I0/c2.C1;
                if(c2.A != 0 && c2.B != 0)
                    ResNaturalval.setText("I(t) = ("+nf.format(c2.A)+"t + ("+nf.format(c2.B)+"))e^-("+nf.format(c2.s2)+")*t A" );
                else if(c2.A == 0)
                    ResNaturalval.setText("I(t) = ("+nf.format(c2.B)+")e^-("+nf.format(c2.s2)+")*t A" );
                else
                    ResNaturalval.setText("I(t) = ("+nf.format(c2.A)+"t)e^-("+nf.format(c2.s2)+")*t A" );

                //Montar a equação(String, igual acima)
                break;
            case 3:
                Amortecimentoval.setText("Subamortecimento");
                c2.A = c2.I0;
                c2.B = (c2.alfa*c2.A)/c2.s2; //LEMBRAR DE FALAR O MOTIVO DE di/dt = 0 (INDUTOR NÃO SEGURA TENSÃO E CAPACITOR ESTÁ ABERTO)
                //JOptionPane.showMessageDialog(null, c2.A);

                if(c2.A != 0 && c2.B != 0)
                    ResNaturalval.setText("I(t) = e^"+nf.format(c2.s1)+"t("+nf.format(c2.A)+"Cos("+nf.format(c2.s2)+"t) + ("+nf.format(c2.B)+")Sen("+nf.format(c2.s2)+"t) A" );
                else if(c2.A == 0)
                    ResNaturalval.setText("I(t) = e^"+nf.format(c2.s1)+"t("+nf.format(c2.B)+"Sen("+nf.format(c2.s2)+"t) A" );
                else
                    ResNaturalval.setText("I(t) = e^"+nf.format(c2.s1)+"t("+nf.format(c2.B)+"t) A" );


                break;
            default:
                break;
        }
        
    }

    public int fontSize(javax.swing.JLabel Label){
        String labelText = (Label.getText());
        int componentWidth = Label.getWidth();
        Font labelFont = Label.getFont();
        int stringWidth = Label.getFontMetrics(labelFont).stringWidth(labelText);
        double widthRatio = (double)componentWidth / (double)stringWidth;
        int newFontSize = (int)(labelFont.getSize() * widthRatio);
        int componentHeight = Label.getHeight();
        return Math.min(newFontSize, componentHeight)-5;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        imgSe = new javax.swing.JLabel();
        fonteCorrente = new javax.swing.JLabel();
        R1 = new javax.swing.JLabel();
        R2 = new javax.swing.JLabel();
        L = new javax.swing.JLabel();
        C = new javax.swing.JLabel();
        V0 = new javax.swing.JLabel();
        Amortecimentoval = new javax.swing.JLabel();
        Amortecimento = new javax.swing.JLabel();
        ResNaturalval = new javax.swing.JLabel();
        ResNatural = new javax.swing.JLabel();
        FreqN = new javax.swing.JLabel();
        FreqNval = new javax.swing.JLabel();
        FreqR = new javax.swing.JLabel();
        FreqRval = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Circuitos RLC - Projeto");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(1200, 700));
        setResizable(false);
        setSize(new java.awt.Dimension(1000, 700));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setLabelFor(this);
        jLabel2.setText("Paralelo (Sem resposta de Degrau)");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(328, 328, 328)
                .addComponent(jLabel2)
                .addContainerGap(591, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        imgSe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        fonteCorrente.setFont(new java.awt.Font("Cambria Math", 0, 28)); // NOI18N
        fonteCorrente.setText("80 V");

        R1.setFont(new java.awt.Font("Cambria Math", 0, 28)); // NOI18N
        R1.setText("30 Ω");

        R2.setFont(new java.awt.Font("Cambria Math", 0, 28)); // NOI18N
        R2.setText("10 Ω");

        L.setFont(new java.awt.Font("Cambria Math", 0, 28)); // NOI18N
        L.setText("0.25 H");

        C.setFont(new java.awt.Font("Cambria Math", 0, 28)); // NOI18N
        C.setText("0.25 F");

        V0.setFont(new java.awt.Font("Cambria Math", 0, 24)); // NOI18N
        V0.setText("I(0) = 4 A");

        Amortecimentoval.setFont(new java.awt.Font("Cambria Math", 0, 18)); // NOI18N

        Amortecimento.setFont(new java.awt.Font("Cambria Math", 3, 18)); // NOI18N
        Amortecimento.setText("Tipo de Amortecimento:");

        ResNaturalval.setFont(new java.awt.Font("Cambria Math", 0, 18)); // NOI18N
        ResNaturalval.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ResNaturalval.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        ResNatural.setFont(new java.awt.Font("Cambria Math", 3, 18)); // NOI18N
        ResNatural.setText("Resposta Natural:");

        FreqN.setFont(new java.awt.Font("Cambria Math", 3, 18)); // NOI18N
        FreqN.setText("Frequência de Neper:");

        FreqNval.setFont(new java.awt.Font("Cambria Math", 0, 18)); // NOI18N

        FreqR.setFont(new java.awt.Font("Cambria Math", 3, 18)); // NOI18N
        FreqR.setText("Frequência Ressonante:");

        FreqRval.setFont(new java.awt.Font("Cambria Math", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(imgSe, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(158, 158, 158)
                                .addComponent(V0, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(FreqR, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FreqRval, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(FreqN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FreqNval, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(ResNaturalval, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ResNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(Amortecimento)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(Amortecimentoval, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(fonteCorrente)
                                .addGap(33, 33, 33)
                                .addComponent(R1))
                            .addComponent(C))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(R2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(L)))))
                .addContainerGap(426, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(131, 131, 131)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(imgSe, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(V0)
                        .addGap(0, 108, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(FreqRval, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FreqR, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(FreqNval, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FreqN, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ResNatural, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ResNaturalval, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Amortecimentoval, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Amortecimento, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(C)
                    .addComponent(L))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fonteCorrente)
                            .addComponent(R1))
                        .addGap(110, 110, 110))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(R2)
                        .addGap(95, 95, 95))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookannfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Amortecimento;
    private javax.swing.JLabel Amortecimentoval;
    private javax.swing.JLabel C;
    private javax.swing.JLabel FreqN;
    private javax.swing.JLabel FreqNval;
    private javax.swing.JLabel FreqR;
    private javax.swing.JLabel FreqRval;
    private javax.swing.JLabel L;
    private javax.swing.JLabel R1;
    private javax.swing.JLabel R2;
    private javax.swing.JLabel ResNatural;
    private javax.swing.JLabel ResNaturalval;
    private javax.swing.JLabel V0;
    private javax.swing.JLabel fonteCorrente;
    private javax.swing.JLabel imgSe;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
