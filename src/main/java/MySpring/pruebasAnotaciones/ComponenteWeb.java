package MySpring.pruebasAnotaciones;
@Component
public class ComponenteWeb {
    @GetMapping("/hello")
    public String hello(){
        return "Hello world!";
    }
}
