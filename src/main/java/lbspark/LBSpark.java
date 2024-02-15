package lbspark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LBSpark {
    private static String serviceUri = "";
    private static Function service = null;
    private static Boolean running = false;

    private static LBSpark _instance = new LBSpark();

    private LBSpark() {
    }

    public static LBSpark getInstance() {
        return _instance;
    }

    public void runServer(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;

            boolean firstLine = true;
            String uriStr = "";

            while ((inputLine = in.readLine()) != null) {
                if (firstLine) {
                    uriStr = inputLine.split(" ")[1];
                    firstLine = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            URI requestUri = new URI(uriStr);

            try {
                if (requestUri.getPath().startsWith("/action")) {
                    outputLine = callService(requestUri);
                } else {
                    outputLine = httpResponse(requestUri);
                }
            } catch (Exception e) {
                e.printStackTrace();
                outputLine = httpError();
            }


            //if(uriStr.startsWith("/cliente")){
            //    outputLine = httpResponse();
            //}else{
            //    outputLine = httpError();
            //}
            out.println(outputLine);

            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    private String callService(URI requestUri) {
        String calledServiceUri = requestUri.getPath().substring(7);
        String output = "";

        if (serviceUri.equals(calledServiceUri)){
            output = service.handle(requestUri.getQuery());
        }
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type:text/html\r\n"
                + "\r\n"
                + output;
    }

    public static void get(String path, Function svc) throws IOException, URISyntaxException {
        String[] args = {};
        serviceUri = path;
        service = svc;

        //if (!running) LBSpark.getInstance().runServer(args);
    }

    private static String httpError() {
        String outputLine = "HTTP/1.1 400 Not Found\r\n"
                + "Content-Type:text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Error Not found</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <h1>Error</h1>\n"
                + "    </body>\n";
        return outputLine;

    }

    public static String httpResponse(URI uriResquest) throws IOException {

        String outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type:text/html\r\n"
                + "\r\n";

        Path file = Paths.get("target/classes/public" + uriResquest.getPath());

        Charset charset = Charset.forName("UTF-8");

        BufferedReader reader = Files.newBufferedReader(file, charset);

        String line = null;

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            outputLine += line;
        }


        return outputLine;

    }


}