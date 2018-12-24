package calvin.baidumusic.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * ������IO��
 * ������ͻ��˽������ݵ��շ�
 * @author Calvin
 *
 */
public class ServerStream {
	
	private DataInputStream dis;
	private DataOutputStream dos;
//	private Socket socket;
	
	public ServerStream(Socket socket) throws IOException {
//		this.socket = socket;
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
	}
	
	public DataInputStream getDis() {
		return dis;
	}

	public void setDis(DataInputStream dis) {
		this.dis = dis;
	}

	public DataOutputStream getDos() {
		return dos;
	}

	public void setDos(DataOutputStream dos) {
		this.dos = dos;
	}

	public String receive() throws IOException {
		String msg = dis.readUTF();
		System.out.println("�������յ����ݣ�" + msg);
		return msg;
	}
	
	public int read(byte[] b) throws IOException {
		int len = dis.read(b);
		System.out.println("�����������ֽ�����" + len);
		return len;
	}
	
	public void send(String msg) throws IOException {
		dos.writeUTF(msg);
		dos.flush();
		System.out.println("�������������ݣ�" + msg);
	}
	
	public void write(byte[] b, int off, int len) throws IOException {
		dos.write(b, off, len);
		dos.flush();
		System.out.println("������д���ֽ�����" + len);
	}
}
