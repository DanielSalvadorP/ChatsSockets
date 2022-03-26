package Chatss;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class Cliente extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	static Socket skCliente;
	static DataOutputStream flujo;
	static final String HOST ="192.168.1.227";
    static final int PUERTO=5000;
    static int cerrar = 0;
    static String msjE = "";
    static String msjS="";
    
    static JButton btnNewButton;
    static JTextArea textArea;
    private JToolBar toolBar;
    private JLabel lblCliente;
    /**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public Cliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textArea = new JTextArea();
		contentPane.add(textArea, BorderLayout.CENTER);
		
		toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.SOUTH);
		
		textField = new JTextField();
		toolBar.add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Enviar");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLUE);
		toolBar.add(btnNewButton);
		
		lblCliente = new JLabel("CLIENTE  ");
		contentPane.add(lblCliente, BorderLayout.NORTH);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
		            final String claveEncriptacion = "secreto!";
					msjS=textField.getText().trim();
					 String datosOriginales = msjS;
			            Encriptador encriptador = new Encriptador();
			            String encriptado = encriptador.encriptar(datosOriginales, claveEncriptacion);
					flujo.writeUTF(encriptado);
					textArea.setText(textArea.getText().trim()+"\n"+"Yo: "+msjS);;
				}catch(Exception e1){
		    		System.out.println(e1.getMessage());
				}
			}
		});
		
 
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente frame = new Cliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		 try {
	         final String claveEncriptacion = "secreto!";
   			 skCliente =new Socket (HOST,PUERTO);
   			 flujo =new DataOutputStream(skCliente.getOutputStream());
   			 DataInputStream x=new DataInputStream(skCliente.getInputStream());
   			 
   			 while(!msjE.equals("salir")){
   				 msjE=x.readUTF();
   				String datosOriginales =msjE;
   	            Encriptador encriptador = new Encriptador();
   	         String desencriptado = encriptador.desencriptar(datosOriginales, claveEncriptacion);
  				 textArea.setText(textArea.getText().trim()+"\n"+"Servidor: "+desencriptado);
   			
   			 }
   		 }
   		 catch (Exception e) {
   			 System.out.println(e.getMessage());
   		 } 
	}

}
