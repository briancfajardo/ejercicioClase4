package MySpring.pruebasAnotaciones;
@Component
public class ComponenteWeb {
    @GetMapping("/hello")
    public static String hello(String name){
        return "Hello world! " + name;
    }
}
