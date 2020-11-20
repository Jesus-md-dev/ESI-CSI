package es.uca.gii.csi20.mdj.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmMain {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMain window = new FrmMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public FrmMain() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Titulo");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mitNew = new JMenu("Nuevo");
		menuBar.add(mitNew);
		
		JMenuItem mitNewCaso = new JMenuItem("Caso");
		mitNewCaso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IfrCaso ifrCaso = new IfrCaso();
				ifrCaso.setBounds(10,27,244,192);
				frame.getContentPane().add(ifrCaso);
				ifrCaso.setVisible(true);
			}
		});
		mitNew.add(mitNewCaso);
		
		JMenu mitSearch = new JMenu("Buscar");
		menuBar.add(mitSearch);
		
		JMenuItem mitSearchCaso = new JMenuItem("Caso");
		mitSearch.add(mitSearchCaso);
		frame.getContentPane().setLayout(null);
	}

}
