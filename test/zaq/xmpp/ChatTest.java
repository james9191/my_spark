package zaq.xmpp;

import java.io.File;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.ChatState;
import org.jivesoftware.smackx.ChatStateListener;
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;

public class ChatTest {
	public static void main(String[] args) throws XMPPException {
		ConnectionConfiguration config = new ConnectionConfiguration("localhost");
		XMPPConnection connection = new XMPPConnection(config);
		connection.connect();
		connection.login("zhangyj", "zaqzaq");
		System.out.println(connection.getServiceName());
		System.out.println(connection.getUser());
		Chat chat = connection.getChatManager().createChat("admin@localhost/Smack", null);

		chat.addMessageListener(new ChatStateListener() {
			@Override
			public void processMessage(Chat chat, Message message) {
				System.out.println("processMessage");
			}

			@Override
			public void stateChanged(Chat chat, ChatState chatstate) {
				System.out.println("stateChanged" + chatstate.name());
			}
		});
		chat.sendMessage("sb");

		connection.addPacketListener(new PacketListener() {

			@Override
			public void processPacket(Packet packet) {

				if (packet instanceof Presence) {
					Presence p = (Presence) packet;
					System.out.println("note:" + p.getStatus() + "XML" + packet.toXML());
					return;
				}
				if (packet instanceof Message) {
					Message m = (Message) packet;
					System.out.println("来自：" + m.getFrom() + "类型：" + m.getType() + "内容：" + m.getBody());
					System.out.println("test:" +m.getFrom());
					return;
				}
				System.out.println(packet.toXML());
			}
		}, new PacketFilter() {

			@Override
			public boolean accept(Packet packet) {
				return true;
			}
		});

		while (true) {
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("''''''''''''''''''''''");

			FileTransferManager transfer = new FileTransferManager(connection);
			OutgoingFileTransfer out = transfer.createOutgoingFileTransfer("admin@localhost/Spark 2.6.3");

			try {
				File file = new File("C:/setup.log");
				out.sendFile(file, file.getName());
				System.out.println(out.getStatus());
				System.out.println(out.getProgress());
				System.out.println(out.isDone());
				while (!out.isDone()) {

					if (out.getStatus() == FileTransfer.Status.in_progress)

						System.out.println(out.getProgress());
					// 可以调用transfer.getProgress();获得传输的进度
				}
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}

	}
}
