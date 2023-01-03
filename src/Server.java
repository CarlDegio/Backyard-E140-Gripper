import java.net.*;
import java.util.Arrays;
import java.io.*;

public class Server
{
    //initialize socket and input stream
    private Socket   socket = null;
    private ServerSocket server = null;
    private InputStream in  = null;
    private OutputStream out = null;

    // constructor with port
    public Server(int port)
    {
        try
        {
            InetAddress addr = Inet4Address.getByAddress(new byte[]{127,0,0,1});
            server = new ServerSocket(port,10,addr);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            socket = server.accept();
            System.out.println("Client accepted");


            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            // Receiving data from client
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            baos.write(buffer, 0 , in.read(buffer));

            byte[] result = baos.toByteArray();

            String res = Arrays.toString(result);
            System.out.println("Recieved from client : "+res);

            //echoing back to client
            out.write(result);

            System.out.println("Closing connection");

        }
        catch(IOException i)
        {
            System.out.println(i);
        }
//        finally {
//            try {
//                assert socket != null;
//                socket.close();
//                out.close();
//                in.close();
//            }catch (IOException i)
//            {
//                System.out.println(i);
//            }
//
//        }
    }

    public static void main(String[] args)
    {
        new Server(10001);
    }
}