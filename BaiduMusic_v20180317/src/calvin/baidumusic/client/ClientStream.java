package calvin.baidumusic.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 客户端IO流
 * 用于与服务器进行数据的收发
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
		System.out.println("客户端收到数据：" + msg);
		return msg;
	}
	
	public int read(byte[] b) throws IOException {
		int len = dis.read(b);
		System.out.println("客户端读到字节数：" + len);
		return len;
	}
	
	public void send(String msg) throws IOException {
		dos.writeUTF(msg);
		dos.flush();
		System.out.println("客户端发送数据：" + msg);
	}
	
	public void write(byte[] b, int off, int len) throws IOException {
		dos.write(b, off, len);
		dos.flush();
		System.out.println("客户端写入字节数：" + len);
	}
}
