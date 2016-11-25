import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;

class ClientTest
{
	private static Socket socket;
	private static PrintWriter printWriter;
    private static BufferedReader br;
    
	public static void main (String [] args)
	{
		 try
		{
			int i = 0;
			while(i < 1)
			{

				socket = new Socket("localhost", 7777);
				printWriter = new PrintWriter(socket.getOutputStream(), true);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				printWriter.println("Hello I'm player"+(i+1));
                String role = br.readLine();
                System.out.println(role);
                String playNum = br.readLine();
                System.out.println(playNum);
				i++;

			}
		}
		catch(Exception e)
		{

		}
	}
}