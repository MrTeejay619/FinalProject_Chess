package sample;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.omg.PortableInterceptor.INACTIVE;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

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
    private File register = new File("/home/aleem/Documents/2020/FinalProject_Chess", "register.xml");
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
                    getUserList();
                    break;
                case "Refresh":
                    getChallenges();
                    break;
                case "Exit":
                    exitUser();
                    break;
                case "Send Move":
                    addMove();
                    // serverExit();
                    break;
                case "Challenge User":
                    challengeUser();
                    break;
                case "Challenge":
                    challenge();
                    break;
                case "Get Response":
                    getResponse();
                    break;
                case "Get Move":
                    getMove();
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
        objectOut.flush();
        socket.shutdownOutput();

    }

    public void exitUser() throws IOException{
        String username = in.readLine();
        socket.shutdownInput();
        Server.activeUsers.remove(username);
    }

    /*
    public void serverExit() throws IOException {
        String temp = in.readLine();
        socket.shutdownInput();
        for (User temp2 : Server.clientList){
            if(temp2.getSocket().getInetAddress().toString().equals(temp)){
                Server.clientList.remove(temp2);
                break;
            }
        }
    }*/

    public void challengeUser() throws IOException {
        String opponent = in.readLine();
        String challenger = in.readLine();
        String[] list = {opponent, challenger};
        socket.shutdownInput();

        Server.challengeUsers.add(list);
    }

    public void challenge() throws IOException {
        String choice = in.readLine();
        String opponent = in.readLine();
        String challenger = in.readLine();
        String colour = in.readLine();

        System.out.println("Got the stuff");

        String[] list = {choice, opponent, challenger, colour};
        Server.checks.add(list);
        /*
        if (choice.equals("Yes")){
            for (User c : Server.clientList){
                if(c.getUsername().equals(opponent)){
                    Server.writeToOne(c.getSocket().getInetAddress(), c.getPort(), challenger, "Start Game");
                } else if (c.getUsername().equals(challenger)){
                    Server.writeToOne(c.getSocket().getInetAddress(), c.getPort(), opponent, "Start Game");
                }
            }

        } else if (choice.equals("No")){
            for (User c : Server.clientList){
                if(c.getUsername().equals(opponent)){
                    Server.writeToOne(c.getSocket().getInetAddress(), c.getPort(), challenger, "Reject Game");
                    break;
                }
            }
        }
        */

    }

    public void getChallenges() throws IOException{
        String username = in.readLine();
        Boolean gameRequest = false;
        String opponent = "No Opponent";
        for (String[] c : Server.challengeUsers){
            if(username.equals(c[0])){
                gameRequest = true;
                opponent = c[1];
                Server.challengeUsers.remove(c);
                break;
            }
        }
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeBoolean(gameRequest);
        out.writeUTF(opponent);
        out.flush();
        socket.shutdownOutput();
    }

    public void getResponse() throws IOException{
        String username = in.readLine();
        Boolean found = false;
        String[] temp = null;

        if(Server.checks.isEmpty()){
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("No Response");
            out.flush();
            socket.shutdownOutput();
        } else {
            for (String[] c : Server.checks) {
                if (c[1].equals(username)) {
                    if (c[0].equals("Yes")) {
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                        out.println("Yes");
                        out.println(c[3]);
                        out.flush();
                        socket.shutdownOutput();
                        found = true;
                        temp = c;
                    } else if (c[0].equals("No")) {
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                        out.println("No");
                        out.println(c[2]);
                        out.flush();
                        socket.shutdownOutput();
                        found = true;
                        temp = c;
                    }
                }
            }
            if (!found) {
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println("No Response");
                out.flush();
                socket.shutdownOutput();
            } else if(found){
                Server.checks.remove(temp);
            }
        }
    }

    public void addMove() throws IOException{
        String opponent = in.readLine();
        String move1 = in.readLine();
        String move2 = in.readLine();
        String move3 = in.readLine();
        String move4 = in.readLine();

        String temp[] = {opponent, move1, move2, move3, move4};
        Server.moveCheck.add(temp);
    }

    public void getMove() throws IOException{
        String username = in.readLine();
        Boolean found = false;
        String[] temp = null;

        if(Server.moveCheck.isEmpty()){
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("No Response");
            out.flush();
            socket.shutdownOutput();
        } else {
            for (String[] c : Server.moveCheck){
                if(c[0].equals(username)){
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.println("Found");
                    out.println(c[1]);
                    out.println(c[2]);
                    out.println(c[3]);
                    out.println(c[4]);
                    out.flush();
                    socket.shutdownOutput();
                    found = true;
                    temp = c;
                }
            }
            if(!found){
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println("No Response");
                out.flush();
                socket.shutdownOutput();
            } else if(found){
                Server.moveCheck.remove(temp);
            }
        }
    }
}