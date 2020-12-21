package es.uca.gii.csi20.mdj.gui;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import es.uca.gii.csi20.mdj.data.Caso;
import es.uca.gii.csi20.mdj.data.Estado;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class IfrCaso extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtTitulo;
	private JTextField txtDescripcion;
	private JTextField txtImportancia;
	private Caso _caso = null;
	
	public Caso getCaso() { return _caso; }

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public IfrCaso(Caso caso) throws Exception {
		setResizable(true);
		setClosable(true);
		setTitle("Caso");
		setBounds(100, 100, 300, 200);
		getContentPane().setLayout(null);
		
		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setBounds(10, 11, 46, 14);
		getContentPane().add(lblTitulo);
		
		txtTitulo = new JTextField();
		txtTitulo.setBounds(81, 8, 180, 20);
		getContentPane().add(txtTitulo);
		txtTitulo.setColumns(10);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(81, 39, 180, 20);
		getContentPane().add(txtDescripcion);
		txtDescripcion.setColumns(10);
		
		txtImportancia = new JTextField();
		txtImportancia.setBounds(81, 70, 180, 20);
		getContentPane().add(txtImportancia);
		txtImportancia.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setVerticalAlignment(SwingConstants.BOTTOM);
		lblDescripcion.setBounds(10, 42, 61, 14);
		getContentPane().add(lblDescripcion);
		
		JLabel lblImportancia = new JLabel("Importancia");
		lblImportancia.setBounds(10, 73, 61, 14);
		getContentPane().add(lblImportancia);
		
		JComboBox<Estado> cmbEstado = new JComboBox<Estado>();
		cmbEstado.setModel(
				new EstadoListModel(Estado.Select(null)));
		cmbEstado.setBounds(81, 101, 180, 22);
		getContentPane().add(cmbEstado);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(10, 105, 46, 14);
		getContentPane().add(lblEstado);
		
		_caso = caso;
		if(_caso != null) {
			txtTitulo.setText(_caso.getTitulo());
			txtDescripcion.setText(_caso.getDescripcion());
			txtImportancia.setText(Integer.toString(_caso.getImportancia()));
			cmbEstado.getModel().setSelectedItem(_caso.getEstado());
		}
		
		IfrCaso ifrCasoRef = this;
		
		JButton butSave = new JButton("Guardar");
		butSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Estado estado = (Estado) cmbEstado.getSelectedItem();
					
					if(txtTitulo.getText().isEmpty())
						throw new IllegalStateException("Título Vacío");
					if(txtDescripcion.getText().isEmpty())
						throw new IllegalStateException("Descripción Vacía");
					if(txtImportancia.getText().isEmpty())
						throw new IllegalStateException("Importancia Vacía");
					if(estado == null)
						throw new IllegalStateException("Estado sin elegir");
					
					if(_caso == null) {
						_caso = Caso.Create(txtTitulo.getText(), txtDescripcion.getText(), 
								Integer.parseInt(txtImportancia.getText()), estado);
						FrmMain.addIfrCaso(ifrCasoRef);
					}
					else {
						_caso.setTitulo(txtTitulo.getText());
						_caso.setDescripcion(txtDescripcion.getText());
						_caso.setImportancia(Integer.parseInt(txtImportancia.getText()));
						_caso.setEstado(estado);
						_caso.Update();
					}
				} catch(IllegalStateException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Se ha introducido un valor no numérico\n " + ex.getMessage() 
					, "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex) { 
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		butSave.setBounds(81, 134, 89, 23);
		getContentPane().add(butSave);
	}
}
