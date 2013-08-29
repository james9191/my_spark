package zaq.swing;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import org.jdesktop.jdic.tray.SystemTray;
import org.jdesktop.jdic.tray.TrayIcon;

/**
 * swing程序最小化至系统托盘
 * 
 * @author seara
 */
public class TraySysteTray {
	public static void main(String args[]) {
		TrayIcon trayIcon = null;
		if (java.awt.SystemTray.isSupported()) // 判断系统是否支持系统托盘
		{
			SystemTray tray = SystemTray.getDefaultSystemTray(); // 创建系统托盘
			ImageIcon image = new ImageIcon(TraySysteTray.class.getResource("Away.png"));// 载入图片,这里要写你的图标路径哦
			ActionListener listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame frame = new JFrame();
					frame.setBounds(400, 400, 200, 200);
					JLabel label = new JLabel("welcome JDK1.6");
					frame.add(label);
					frame.setVisible(true);
				}
			};
			// 创建弹出菜单
			JPopupMenu popup = new JPopupMenu();
			JMenuItem defaultItem = new JMenuItem("主界面");// MenuItem("主界面");
			defaultItem.addActionListener(listener);
			JMenuItem exitItem = new JMenuItem("退出程序");
			exitItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (JOptionPane.showConfirmDialog(null, "确定退出系统") == 0) {
						System.exit(0);
					}
				}
			});
			popup.add(defaultItem);
			popup.add(exitItem);
			trayIcon = new TrayIcon(image, "ZAQZAq", popup);// 创建trayIcon
			trayIcon.addActionListener(listener);
			tray.addTrayIcon(trayIcon);
		}
	}
}