import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.TrustAnchor;
import java.util.Arrays;
import java.io.*;

public class Client {
    // initialize socket and input output streams
    private Socket socket = null;
    private OutputStream out = null;
    private InputStream in = null;
    byte[] buffer = new byte[1024];

    // constructor to put ip address and port
    public Client(String address, int port) {
        // establish a connection
        try {
            System.out.println("Start connect server");
            socket = new Socket(address, port);
            if (socket.isConnected()) {
                System.out.println("Connected server");
            }
            else
            {
                System.out.println("Client can't get Server");
            }

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
            //takes input from socket
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException u) {
            System.out.println(u);
        }


//        try {
//            byte[] arr = "calibrateGripper()".getBytes(StandardCharsets.UTF_8);
//
//            // sending data to server
//            out.write(arr);
//            out.flush();
//            String req = Arrays.toString(arr);
//            //printing request to console
//            System.out.println("Sent to server : " + req);
//            System.out.println("Sent to server : " + new String(arr,StandardCharsets.UTF_8));
//
//            // Receiving reply from server
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            byte buffer[] = new byte[1024];
//            baos.write(buffer, 0 , in.read(buffer));
//
//            byte result[] = baos.toByteArray();
//
//            String res = Arrays.toString(result);
//
//            // printing reply to console
//            System.out.println("Recieved from server : " + res);
//        } catch (IOException i) {
//            System.out.println(i);
//        } finally {
//            try {
//                in.close();
//                out.close();
//                socket.close();
//            }catch (IOException i)
//            {
//                System.out.println(i);
//            }
//
//        }
        // }

    }
    public String calibrateGripper()
    {

        System.out.println("Start calibrate");
        String cmd_args="calibrateGripper()"+"\n";
        return pub_cmd(cmd_args);
    }

    public String moveTo(double position,double speed,double acceleration,double torque,double tolerance,boolean waitFlag)
    {
        //tolerance表明目标位置和实际位置的最大误差，waitflag=True表明夹的过程是阻塞式
        System.out.printf("move to position %f%n", position);
        String cmd_args="moveTo"+String.format("(%f,%f,%f,%f,%f,%b)",position,speed,acceleration,torque,tolerance,waitFlag)+"\n";
        return pub_cmd(cmd_args);
    }

    public String getStatus()
    {
        System.out.println("get status:");
        String cmd_args="getStatus()"+"\n";
        return pub_cmd(cmd_args);
    }

    public String getCalibrated()
    {
        System.out.println("check calibrated");
        String cmd_args="getCalibrated()"+"\n";
        return pub_cmd(cmd_args);
    }

    public String shutdown()
    {
        System.out.println("will shutdown");
        String cmd_args="shutdown()"+"\n";
        return pub_cmd(cmd_args);
    }

    public String restart()
    {
        System.out.println("start restart");
        String cmd_args="restart()"+"\n";
        return pub_cmd(cmd_args);
    }

    public String pub_cmd(String cmd_args)
    {
        int length=buffer.length;
        java.util.Arrays.fill(buffer, (byte)0);//读取前，缓冲区置0
        byte[] payload = cmd_args.getBytes(StandardCharsets.UTF_8);
        try {
            out.write(payload);
            out.flush();
            length=in.read(buffer);//可以获取读取的长度
        }catch (IOException i)
        {
            System.out.println(i);
        }
        return new String(buffer,0,length, StandardCharsets.UTF_8);
    }



    public static void main(String[] args) {
        Client client=new Client("192.168.137.9", 9999);//需要在网页上设定
        //暂时没有考虑从流中读出的混合问题，要实地测试才知道，也可以并列避免。
        String status= client.moveTo(101.2345678910,100,100,1,10, false);
        for (int i = 0; i < 2000; i++) {
            status= client.getStatus();
            System.out.println(status);
        }
//        status= client.shutdown();
//        System.out.println(status);
    }
}