import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Clock {

	public static void main(String[] args) throws URISyntaxException {

		//declared final so they can be accessed by anonymous inner classes
		final String defaultSDFString = "HH:mm:ss";
		 SimpleDateFormat sdf = new SimpleDateFormat(defaultSDFString);
		final URI uri = new URI(
				"http://docs.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html");

		JFrame f = new JFrame("Super Clock 9000");
		f.setSize(300, 150);

		f.addWindowListener(new ExitListener());
		Container content = f.getContentPane();
		content.setLayout(new FlowLayout());

		JLabel label = new JLabel(
				"Set time format, uses Java's SimpleDateFormat:");
		final JTextField textField = new JTextField(20);
		textField.setText(defaultSDFString);
		JButton helpButton = new JButton();
		helpButton.setText("Help with formatting");
		helpButton.setToolTipText("This takes you to " + uri.toString());
		helpButton.addActionListener(new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				try {
					openURI();
				} catch (URISyntaxException e1) {

					e1.printStackTrace();
				}
			}

		});

		JButton setNewFormatButton = new JButton((new AbstractAction(
				"Set new format") {
			public void actionPerformed(ActionEvent e) {
				String str = textField.getText();

				try {
					sdf.applyPattern(str);

				} catch (IllegalArgumentException exc) {
					JOptionPane
							.showMessageDialog(
									null,
									"The format entered is incorrect, please see help button",
									"Error with format",
									JOptionPane.ERROR_MESSAGE);
				} catch (NullPointerException rexc) {
					JOptionPane
							.showMessageDialog(
									null,
									"You somehow managed to make the text field null, good job.",
									"Error with format",
									JOptionPane.ERROR_MESSAGE);
				}

			}
		}));

		setNewFormatButton
				.setToolTipText("This will set the clock to the new specified format");

		content.add(label);
		content.add(textField);
		content.add(setNewFormatButton);
		content.add(helpButton);

		f.setVisible(true);

		// loop that runs while program is open and updates time
		while (true) {

			String str = sdf.format(new Date());
			f.setTitle(str);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error sleeping thread");
			}

		}
	}

	static class ExitListener extends WindowAdapter {
		public void windowClosing(WindowEvent event) {
			System.exit(0);
		}
	}

	// opens passed URI in browser
	static void openURI() throws URISyntaxException {

		final URI uri = new URI(
				"http://docs.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html");
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) { /* TODO: error handling */
				JOptionPane.showMessageDialog(null,
						"There was an error taking you to specified link, try going manually: "
								+ uri.toString());
			}
		}
	}
}
