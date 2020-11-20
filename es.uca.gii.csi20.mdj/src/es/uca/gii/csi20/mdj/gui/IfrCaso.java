package es.uca.gii.csi20.mdj.gui;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import es.uca.gii.csi20.mdj.data.Caso;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IfrCaso extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtTitulo;
	private JTextField txtDescripcion;
	private JTextField txtImportancia;
	private Caso cCaso_ = null;


	/**
	 * Create the frame.
	 */
	public IfrCaso() {
		setResizable(true);
		setClosable(true);
		setTitle("Caso");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setBounds(10, 11, 46, 14);
		getContentPane().add(lblTitulo);
		
		txtTitulo = new JTextField();
		txtTitulo.setBounds(81, 8, 86, 20);
		getContentPane().add(txtTitulo);
		txtTitulo.setColumns(10);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(81, 39, 86, 20);
		getContentPane().add(txtDescripcion);
		txtDescripcion.setColumns(10);
		
		txtImportancia = new JTextField();
		txtImportancia.setBounds(81, 70, 86, 20);
		getContentPane().add(txtImportancia);
		txtImportancia.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setVerticalAlignment(SwingConstants.BOTTOM);
		lblDescripcion.setBounds(10, 42, 61, 14);
		getContentPane().add(lblDescripcion);
		
		JLabel lblImportancia = new JLabel("Importancia");
		lblImportancia.setBounds(10, 73, 61, 14);
		getContentPane().add(lblImportancia);
		
		JButton butSave = new JButton("Guardar");
		butSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					if(txtTitulo.getText().isEmpty())
						throw new IllegalStateException("T�tulo Vacio");
					if(txtDescripcion.getText().isEmpty())
						throw new IllegalStateException("Descripci�n Vacia");
					if(txtImportancia.getText().isEmpty())
						throw new IllegalStateException("Importancia Vacia");
					
					if(cCaso_ == null)
						cCaso_ = Caso.Create(txtTitulo.getText(), txtDescripcion.getText(), Integer.parseInt(txtImportancia.getText()));
					else {
						cCaso_.setTitulo(txtTitulo.getText());
						cCaso_.setDescripcion(txtDescripcion.getText());
						cCaso_.setImportancia(Integer.parseInt(txtImportancia.getText()));
						cCaso_.Update();
					}
				} catch(IllegalStateException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage() ,"Error", JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Se ha introducido un valor no num�rico\n " + ex.getMessage() ,"Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex) { 
					JOptionPane.showMessageDialog(null, ex.getMessage() ,"Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		butSave.setBounds(78, 101, 89, 23);
		getContentPane().add(butSave);

	}
}
