import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class TestPost {
	public static void main(String[] args) {
		
		try {
			GameData data = new GameData(12, 23, false);
			Socket socket = null;
			socket = new Socket("127.0.0.1", 55555);
			ObjectOutputStream oout = new ObjectOutputStream(socket.getOutputStream());
			oout.writeObject(data);
			oout.flush();
			ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
			data = (GameData)oin.readObject();
			System.out.println(data.isChecked());
			oout.close();
			oin.close();
			socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
