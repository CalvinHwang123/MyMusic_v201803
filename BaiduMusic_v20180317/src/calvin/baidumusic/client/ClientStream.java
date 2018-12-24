package calvin.baidumusic.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * �ͻ���IO��
 * ������������������ݵ��շ�
 * @author Calvin
 *
 */
public class ClientStream {
	
	private DataInputStream dis;
	private DataOutputStream dos;
//	private Socket socket;
	
	public ClientStream(Socket socket) throws IOException {
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
		System.out.println("�ͻ����յ����ݣ�" + msg);
		return msg;
	}
	
	public int read(byte[] b) throws IOException {
		int len = dis.read(b);
		System.out.println("�ͻ��˶����ֽ�����" + len);
		return len;
	}
	
	public void send(String msg) throws IOException {
		dos.writeUTF(msg);
		dos.flush();
		System.out.println("�ͻ��˷������ݣ�" + msg);
	}
	
	public void write(byte[] b, int off, int len) throws IOException {
		dos.write(b, off, len);
		dos.flush();
		System.out.println("�ͻ���д���ֽ�����" + len);
	}
}
