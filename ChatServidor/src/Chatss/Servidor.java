package Chatss;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JDesktopPane;
import javax.swing.JToolBar;

public class Servidor extends JFrame {
	
	private JPanel contentPane;
	private JTextField textField;
	
	static ServerSocket skServidor;
	static Socket skCliente;
	static DataOutputStream x;
	static final int PUERTO = 5000;
    static int closed=3;
    static String msjE="";
    static String msjS="";
	
    
    
    static JTextArea textArea;
    static JButton btnEnviar;
    private JToolBar toolBar;
    private JLabel lblServidor;
    /**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Servidor() {
		setAlwaysOnTop(true);
		
		
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
		
		btnEnviar = new JButton("Enviar");
		toolBar.add(btnEnviar);
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					final String claveEncriptacion = "secreto!";
					msjS=textField.getText().trim();
					Encriptador encriptador = new Encriptador();
					String datosOriginales=msjS;
					String encriptado = encriptador.encriptar(datosOriginales, claveEncriptacion);
					x.writeUTF(encriptado);
					textArea.setText(textArea.getText().trim()+"\n"+"Yo: "+msjS);;
				}catch(Exception e1){
		    		System.out.println(e1.getMessage());
				}
			}
		});
		btnEnviar.setBackground(Color.BLUE);
		btnEnviar.setForeground(Color.WHITE);
		
		lblServidor = new JLabel("SERVIDOR  ");
		contentPane.add(lblServidor, BorderLayout.NORTH);
    
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servidor frame = new Servidor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		try {
			final String claveEncriptacion = "secreto!";
    		skServidor=new ServerSocket(PUERTO);
   		 	System.out.println("escucho el puerto: "+PUERTO);
   		 	skCliente=skServidor.accept();
   		 	x=new DataOutputStream(skCliente.getOutputStream());
   		 	
   		 	DataInputStream flujo = new DataInputStream (skCliente.getInputStream()); 
   		 	while(!msjE.equals("salir")){
   		 		msjE=flujo.readUTF();
   		 	String datosOriginales = msjE;
   		 	Encriptador encriptador = new Encriptador();
   		 	String desencriptado = encriptador.desencriptar(datosOriginales, claveEncriptacion);
		 			textArea.setText(textArea.getText().trim()+"\n"+"Cliente: "+desencriptado);;
   		 		}

    	}catch(Exception e){
    		System.out.println(e.getMessage());
   	 	}
	}

}
