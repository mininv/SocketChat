package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Server {
    private static final Random rn = new Random();
    public int  rage=33;
    public String s,s1;
    public int begin = 1; //Строка с которой начинается считывание
    public int end = 32; //Строка на которой заканчивается считывание
    public int counter = 0;
    int port = 5000;

    public int  gId(){
        return rn.nextInt(rage);
    }

    public void readFileAndTheWriteInAnother() {

        List<String> file_strings = new ArrayList<>();

        try(
            BufferedReader reader = new BufferedReader(new FileReader("afaf.txt"))){

            ServerSocket serverSocet = new ServerSocket(port);
            System.out.println("Ждем подключения к серверу");
            Socket socket = serverSocet.accept();
            System.out.println("Подключение состоялось ");

            InputStream socketInStr = socket.getInputStream();
            OutputStream socketOutStr = socket.getOutputStream();

            DataInputStream in =new DataInputStream(socketInStr);
            DataOutputStream out = new DataOutputStream(socketOutStr);

            String anyPhrase;
            while((anyPhrase =reader.readLine()) != null) {
                counter++;
                if(counter>begin-1){file_strings.add(anyPhrase);}
                if(counter==end){break;}
            }
            do{
                s= file_strings.get(this.gId());
                System.out.println("Полученна фразу: ");
                s1 = in.readUTF();
                System.out.println(s1);
                System.out.println(s);
                out.writeUTF(s);
                out.flush();
            }while (!s1.equals("закончить"));

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.readFileAndTheWriteInAnother();
    }
}