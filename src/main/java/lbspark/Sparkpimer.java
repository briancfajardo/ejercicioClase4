package lbspark;

import static spark.Spark.get;
import static spark.Spark.staticFiles;

public class Sparkpimer {

    public static void main( String[] args )
    {
        // root is 'src/main/resources', so put files in 'src/main/resources/public'
        staticFiles.location("/public"); // Static files
        get("/hello", (req, resp) -> "Hello Wold!");
        get("/pi", (req, resp) -> Math.PI);
    }
}
