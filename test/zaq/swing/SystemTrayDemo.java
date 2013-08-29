package zaq.swing;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.PopupMenu;
import java.awt.MenuItem;
import java.awt.Image;
import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class SystemTrayDemo {
	SystemTray systemTray;
	TrayIcon trayIcon;
	Image trayImage;
	Image waitImage;
	String trayTip;
	PopupMenu trayPopupMenu;
	MenuItem trayMenuItem;
	ActionListener trayActionListener;
	ActionListener menuActionListener;
	MouseListener trayMouseListener;
	Thread displayTime;

	public SystemTrayDemo() {
		if (SystemTray.isSupported()) {
			trayMouseListener = new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					System.out.println("Tray Icon - Mouse clicked!");
				}

				public void mouseEntered(MouseEvent e) {
					System.out.println("Tray Icon - Mouse entered!");
				}

				public void mouseExited(MouseEvent e) {
					System.out.println("Tray Icon - Mouse exited!");
				}

				public void mousePressed(MouseEvent e) {
					System.out.println("Tray Icon - Mouse pressed!");
				}

				public void mouseReleased(MouseEvent e) {
					System.out.println("Tray Icon - Mouse released!");
				}
			};
			trayActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Tray Icon - Action event happened!");
					;
				}
			};
			menuActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			};
			trayPopupMenu = new PopupMenu();
			trayMenuItem = new MenuItem("exit");
			trayMenuItem.addActionListener(menuActionListener);
			trayPopupMenu.add(trayMenuItem);
			trayTip = "This is a SystemTray Demo";
			trayImage = Toolkit.getDefaultToolkit().getImage(SystemTrayDemo.class.getResource("/zaq/res/mesage.png"));
			waitImage =Toolkit.getDefaultToolkit().getImage(SystemTrayDemo.class.getResource("/zaq/res/Away.png"));
			trayIcon = new TrayIcon(trayImage, trayTip, trayPopupMenu);
			trayIcon.addActionListener(trayActionListener);
			trayIcon.addMouseListener(trayMouseListener);
			trayIcon.setImageAutoSize(true);
			systemTray = SystemTray.getSystemTray();
			try {
				systemTray.add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("SystemTray unsupported!");
		}
		displayTime = new Thread(new Runnable() {
			public void run() {
				Calendar now;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String time;
				while (true) {
					now = Calendar.getInstance();
					time = sdf.format(now.getTime());
					trayIcon.displayMessage("报时", time, TrayIcon.MessageType.INFO);
					trayIcon.setImage(trayImage);
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					now = Calendar.getInstance();
					time = sdf.format(now.getTime());
					trayIcon.displayMessage("报时", time, TrayIcon.MessageType.INFO);
					trayIcon.setImage(waitImage);
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		displayTime.start();
	}

	public static void main(String[] args) {
		new SystemTrayDemo();
	}
}