import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Clock {
	public static void main(String[] args) {

		JFrame f = new JFrame("GoonClock 9000");
		f.setSize(200, 0);

		f.addWindowListener(new ExitListener());
		
		f.setState ( f.ICONIFIED );
		f.setVisible(true);
		while (true) {

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String str = sdf.format(new Date());
			f.setTitle(str);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static class ExitListener extends WindowAdapter {
		public void windowClosing(WindowEvent event) {
			System.exit(0);
		}
	}

}
