package lbspark;


import java.io.IOException;
import java.net.URISyntaxException;

import static lbspark.LBSpark.get;

public class MisServicios {

    public static void main(String[] args) throws IOException, URISyntaxException {
        get("/hola", (req) -> "El Query es: " + req);
        LBSpark.getInstance().runServer(args);
    }
}
