package Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class Client {
    int servPort =  5000;
    String interAdress = "127.0.0.1";
    public void addChat() {
        try(FileWriter fw = new FileWriter("tst.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));) {
            InetAddress inetAdress = InetAddress.getByName(interAdress);
            System.out.println("Подключаемся к серверу: " + servPort);
            Socket socket = new Socket(inetAdress, servPort);

            InputStream socketInStr = socket.getInputStream();
            OutputStream socketOutStr = socket.getOutputStream();

            DataInputStream in = new DataInputStream(socketInStr);
            DataOutputStream out = new DataOutputStream(socketOutStr);


            String string = null;
            System.out.println("Введите фразу для перелачи серверу: ");

            do {
                string = reader.readLine();
                fw.write(string + "\r\n");
                out.writeUTF(string);
                if (string.equals("стоп")) {
                    do {
                        string = reader.readLine();
                        fw.write(string + "\r\n");
                    } while (!string.equals("продолжить"));
                }
                if (string.equals("закончить")) break;
                out.flush();//закрываем поток
                string = in.readUTF();
                fw.write(string + "\r\n");
                System.out.println("Сервер прислал в ответ: " + string);
                System.out.println("Введите следующую фразу для отправки на сервер: ");
            } while (!string.equals("закончить"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)throws IOException {
        Client client = new Client();
        client.addChat();

    }
}
