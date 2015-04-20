package srk.syracuse.gameofcards.Connections;


import android.os.AsyncTask;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnectionTask extends AsyncTask<Void, Void, Void> {
    public static Socket socket = null;
    String dstAddress;
    int dstPort = 8080;

    ClientConnectionTask(String addr) {
        dstAddress = addr;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        try {
            socket = new Socket(dstAddress, dstPort);
            if (socket.isConnected()) {
                ClientListenerThread clientListener = new ClientListenerThread(socket);
                clientListener.run();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void params) {

    }
}
