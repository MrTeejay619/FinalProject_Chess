package sample;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnectHandler implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private String password;
    private String username;
    private String selection;
    //private File register = new File("C:\\Users\\Taabish\\Desktop\\GitHub\\CSCI2020_Taab\\FinalProjectServerTest","register.xml");
    private File register = new File("/home/taabish/Desktop/CSCI2020/FinalProjectServerTest", "register.xml");
    private ArrayList<User> users = new ArrayList<>();

    private boolean empty = register.length() == 0;

    public ClientConnectHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try{
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            selection = in.readLine();

            if (!empty) {
                userList();
            }

            if (!register.exists()) {
                register.createNewFile();
            }

            switch(selection){
                case "Login":
                    checkUser();
                    break;
                case "New User":
                    createUser();
                    break;
                case "Get User List":
                    //getUserList();
                    System.out.println("test kek");
                    newClient();
                    break;
                case "Exit":
                    exitUser();
                    break;
                case "ServerExit":
                    serverExit();
                    break;
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void checkUser() throws IOException{
        username = in.readLine();
        password = in.readLine();
        socket.shutdownInput();
        Boolean userExist = false;
        Boolean alreadyLoggedIn = false;

        for (User c: this.users){
            if (c.getPassword().equals(password) && c.getUsername().equals(username.toLowerCase())) {
                userExist = true;
                break;
            }
        }

        for (String check: Server.activeUsers){
            if(username.toLowerCase().equals(check)){
                alreadyLoggedIn = true;
                break;
            }
        }

        PrintWriter out = new PrintWriter(socket.getOutputStream());
        if(userExist && !alreadyLoggedIn){
            Server.activeUsers.add(username);
            User temp = new User(username, socket);
            Server.clientList.add(temp);
        }

        out.println(alreadyLoggedIn);
        out.println(userExist);
        out.flush();
        socket.shutdownOutput();
    }

    public void createUser() throws IOException{
        username = in.readLine();
        password = in.readLine();
        socket.shutdownInput();
        boolean alreadyExist = false;
        User newUser = new User(username, password);

        for (User check: this.users){
            if (newUser.getUsername().equals(check.getUsername().toLowerCase())){
                alreadyExist = true;
                break;
            }
        }

        if(!alreadyExist) {
            this.users.add(newUser);
        }

        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(alreadyExist);
        out.flush();
        socket.shutdownOutput();
        if(!alreadyExist) {
            XMLEncoder encoder = new XMLEncoder(new FileOutputStream(register));
            for (User in : this.users) {
                encoder.writeObject(in);
            }
            encoder.flush();
            encoder.close();
            System.out.println("User Successfully created");
        }
    }

    public void userList() throws IOException{
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(register)));
        try {
            while (true) {
                this.users.add((User)decoder.readObject());
            }
        } catch ( ArrayIndexOutOfBoundsException e ) {
        } finally {
            decoder.close();
        }
    }

    public void getUserList() throws IOException{
        socket.shutdownInput();
        ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
        objectOut.writeObject(Server.activeUsers);
        objectOut.close();
    }

    public void exitUser() throws IOException{
        String username = in.readLine();
        socket.shutdownInput();
        Server.activeUsers.remove(username);
        System.out.println("A User just Left");
        newClient();
    }

    public void serverExit() throws IOException {
        String temp = in.readLine();
        socket.shutdownInput();
        for (User temp2 : Server.clientList){
            if(temp2.getSocket().getInetAddress().toString().equals(temp)){
                Server.clientList.remove(temp2);
                break;
            }
        }
    }

    public void write(InetAddress address) throws IOException {
        System.out.println("test kek3");
        Socket socket2 = new Socket(address, 10500);
        //PrintWriter out = new PrintWriter(socket2.getOutputStream());
        //out.println("List");
        //out.flush();
        ObjectOutputStream objectOut = new ObjectOutputStream(socket2.getOutputStream());
        objectOut.writeUTF("List");
        objectOut.flush();
        objectOut.writeObject(Server.activeUsers);
        objectOut.flush();
        socket2.close();
    }

    public void newClient() throws IOException{
        String name = in.readLine();
        System.out.println(name);
        System.out.println("test kek1");
        for (User c : Server.clientList){
            System.out.println(c.getUsername());
            if(c.getUsername().equals(name)) {
                System.out.println("test kek2");
                write(c.getSocket().getInetAddress());
            }
        }
    }

}