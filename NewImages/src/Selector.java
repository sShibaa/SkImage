import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JProgressBar;
import javax.swing.JToggleButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.Color;
import javax.swing.JTextField;

public class Selector {

	private JFrame frmSkimage;

	/**
	 * Launch the application.
	 */
	private static JToggleButton replacefile;
	private static String fileDir;
	public static boolean replaceFiles = false;
	private JTextField txtMadeBySshiba;
	
	
	public static void open() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Selector window = new Selector();
					window.frmSkimage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "ERROR: "+e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Selector() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		String laf = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(laf);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "ERROR: "+e.getMessage());
		}
		frmSkimage = new JFrame();
		try {
			URL url = new URL("https://minotar.net/helm/sShiba/512.png");
			Image image = ImageIO.read(url);
			frmSkimage.setIconImage(image);
		} catch (Exception e2) {JOptionPane.showMessageDialog(null, "ERROR: "+e2.getMessage());}
		
		frmSkimage.setTitle("Sk-Image");
		frmSkimage.setResizable(false);
		frmSkimage.setBounds(100, 100, 885, 478);
		frmSkimage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSkimage.getContentPane().setLayout(null);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Always on top");
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("always on top set to "+rdbtnNewRadioButton.isSelected());
				frmSkimage.setAlwaysOnTop(rdbtnNewRadioButton.isSelected());
			}
		});
		
		txtMadeBySshiba = new JTextField();
		txtMadeBySshiba.setBorder(null);
		txtMadeBySshiba.setHorizontalAlignment(SwingConstants.RIGHT);
		txtMadeBySshiba.setEditable(false);
		txtMadeBySshiba.setOpaque(false);
		txtMadeBySshiba.setFont(new Font("Tahoma", Font.ITALIC, 10));
		txtMadeBySshiba.setText("Made by sShiba. \r\nClick head to find links\r\n");
		txtMadeBySshiba.setBounds(655, 423, 188, 26);
		frmSkimage.getContentPane().add(txtMadeBySshiba);
		txtMadeBySshiba.setColumns(10);
		rdbtnNewRadioButton.setBounds(10, 390, 109, 23);
		frmSkimage.getContentPane().add(rdbtnNewRadioButton);
		
		JLabel skin_outline = DefaultComponentFactory.getInstance().createLabel("");
		skin_outline.setIcon(new ImageIcon(Selector.class.getResource("/assets/200xoutline.png")));
		skin_outline.setBounds(668, 11, 201, 200);
		frmSkimage.getContentPane().add(skin_outline);
		
		JLabel skimage = DefaultComponentFactory.getInstance().createLabel("");
		skimage.setIcon(new ImageIcon(Selector.class.getResource("/assets/200xdefault.png")));
		skimage.setBounds(668, 12, 201, 199);
		frmSkimage.getContentPane().add(skimage);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(661, 0, 26, 449);
		frmSkimage.getContentPane().add(separator);
		
		JButton process = new JButton("Process");
		process.setEnabled(false);
		process.setFont(new Font("Malgun Gothic Semilight", Font.BOLD, 35));
		process.setBounds(668, 267, 201, 146);
		frmSkimage.getContentPane().add(process);
		
		JProgressBar pbar = new JProgressBar();
		pbar.setStringPainted(true);
		pbar.setForeground(new Color(0, 120, 215));
		pbar.setBackground(Color.WHITE);
		pbar.setBounds(10, 424, 641, 14);
		frmSkimage.getContentPane().add(pbar);
		
		replacefile = new JToggleButton("Replace selected file");
		replacefile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replaceFiles = replacefile.isSelected();
				System.out.println("Replace files: "+replaceFiles);
			}
		});
		replacefile.setFont(new Font("Tahoma", Font.PLAIN, 17));
		replacefile.setBounds(668, 222, 198, 34);
		frmSkimage.getContentPane().add(replacefile);
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setBorder(null);
		fileChooser.setToolTipText("Pick a file");
		fileChooser.setDialogTitle("");
		fileChooser.setControlButtonsAreShown(false);
		fileChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(fileChooser.getSelectedFile().toURI());
				fileDir = fileChooser.getSelectedFile().toString();
				if (fileChooser.getSelectedFile().toString().endsWith(".png")) {
					process.setEnabled(true);
					BufferedImage image;
					try {
						image = ImageIO.read(fileChooser.getSelectedFile());
						skimage.setIcon(new ImageIcon(resizedImage(image, 200, 200)));
						skin_outline.setVisible(true);
					} catch (IOException e1) {JOptionPane.showMessageDialog(null, "ERROR: "+e1.getMessage());}
					
				} else {
					process.setEnabled(false);
					skimage.setIcon(new ImageIcon(Selector.class.getResource("/assets/200xunknown.png")));
					skin_outline.setVisible(false);
					//
					
				}
			}
		});
		fileChooser.setBounds(12, 0, 638, 397);
		frmSkimage.getContentPane().add(fileChooser);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI("https://namemc.com/profile/sShiba.3"));
					java.awt.Desktop.getDesktop().browse(new URI("https://youtube.com/sShiba"));
					java.awt.Desktop.getDesktop().browse(new URI("https://gcodingstudios.com/team/"));
					//process.setIcon(new ImageIcon(Selector.class.getResource("/assets/drawing.png")));
				} catch (IOException | URISyntaxException e) {JOptionPane.showMessageDialog(null, "ERROR: "+e.getMessage());}
				JOptionPane.showMessageDialog(null, "Thank you for checking out my other stuff!");
			}
		});
		try {
			URL url = new URL("https://minotar.net/cube/sShiba/32.png");
			Image image = ImageIO.read(url);
			lblNewLabel.setIcon(new ImageIcon(image));
		} catch (Exception e2) {JOptionPane.showMessageDialog(null, "ERROR: "+e2.getMessage());}
		
		//lblNewLabel.setIcon(new ImageIcon(Selector.class.getResource("/assets/200xunknown.png")));
		lblNewLabel.setBounds(843, 417, 32, 32);
		frmSkimage.getContentPane().add(lblNewLabel);
		
		process.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Preparing to process skin with these settings:");
				System.out.println("Replace Files: "+replaceFiles);
				System.out.println("Directory: "+fileDir);
				
				//BufferedImage image = ImageIO.read(fileChooser.getSelectedFile());
				BufferedImage iage = null;
				try {
					try {
						iage = ImageIO.read(fileChooser.getSelectedFile());
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					File PROGRAMFILES = new File(new String(System.getProperty("user.home")+"/Documents/SkImage/skimage.png/").replace('"', '/'));
					File PROGRAMFILESFOLDERS = new File(new String(System.getProperty("user.home")+"/Documents/SkImage/").replace('"', '/'));
					PROGRAMFILES.deleteOnExit();
					PROGRAMFILESFOLDERS.deleteOnExit();
					//System.out.println(PROGRAMFILES.toString());
					if (!PROGRAMFILES.exists()) {
						//if (PROGRAMFILESFOLDERS.exists()) {
							
						//} else {
							//PROGRAMFILESFOLDERS.createNewFile();
						PROGRAMFILESFOLDERS.mkdir();
							
						//}
					}
					try {
						PROGRAMFILES.createNewFile();
						ImageIO.write(resizedImage(iage, 32, 32), "PNG", PROGRAMFILES);
						Combine.imageToSkin(PROGRAMFILES, fileChooser.getSelectedFile(), pbar);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				} catch (URISyntaxException e1) {
					e1.printStackTrace(); 
					JOptionPane.showMessageDialog(null, "ERROR: "+e1.getMessage());
					//System.out.println(PROGRAMFILESFOLDERS);
				}
			}
		});
		
	}

	
	public static BufferedImage resizedImage(BufferedImage inputImage, int scaledWidth, int scaledHeight)
            throws IOException {
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
 
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
 
        // writes to output file
        return outputImage;
    }
}
