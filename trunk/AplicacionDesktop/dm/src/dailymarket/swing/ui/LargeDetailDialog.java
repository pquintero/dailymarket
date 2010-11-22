package dailymarket.swing.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import nl.jj.swingx.gui.modal.JModalDialog;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public class LargeDetailDialog extends JModalDialog {

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());

			LargeDetailDialog frame = new LargeDetailDialog(null, "Titulo", "Este es el mensaje principal", "Este es el detalle");

			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog
	 */
	public LargeDetailDialog(Window owner, String title, String message, String detail) {
		super(owner);
		this.setTitle(title);
		setBounds(100, 100, 311, 203);
		setAlwaysOnTop(true); 
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(owner);

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setPreferredSize(new Dimension(0, 40));
		getContentPane().add(titlePanel, BorderLayout.NORTH);

		JLabel messageLabel = new JLabel();
		URL url = LargeDetailDialog.class.getClassLoader().getResource("com/sun/java/swing/plaf/windows/icons/Inform.gif");
		Image image = Toolkit.getDefaultToolkit().getImage(url);
		//messageLabel.setIcon(SwingResourceManager.getIcon(LargeDetailDialog.class, "/com/sun/java/swing/plaf/windows/icons/Inform.gif"));
		messageLabel.setIcon(new ImageIcon(image));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setText(message);
		titlePanel.add(messageLabel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(0, 40));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		JButton acceptButton = new JButton();
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				LargeDetailDialog.this.setVisible(false);
				LargeDetailDialog.this.dispose();
			}
		});
		acceptButton.setMnemonic(KeyEvent.VK_A);
		acceptButton.setText("Aceptar");
		buttonPanel.add(acceptButton);

		setName("largeDetailDialogFrame");
		setResizable(false);
		messageLabel.setText(message);

		JTextPane f = new JTextPane();
		f.setText(detail);
		f.setEditable(false);
		//Estilos
		f.setBorder(new LineBorder(Color.black, 1, false));
		f.setBackground(getContentPane().getBackground());
		f.setFont(new Font("Arial", Font.TRUETYPE_FONT, 11));	
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setAlignment(set, StyleConstants.ALIGN_CENTER);
		f.setParagraphAttributes(set,true);

		getContentPane().add(f, BorderLayout.CENTER);
	}
}
