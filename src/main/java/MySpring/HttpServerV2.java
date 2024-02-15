package MySpring;

import MySpring.pruebasAnotaciones.Component;
import MySpring.pruebasAnotaciones.GetMapping;
import MySpring.pruebasAnotaciones.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Class.forName;
public class HttpServerV2 {
    static Map<String, Method> componentes = new HashMap<>();

    public static void main(String[] args) throws ClassNotFoundException {
        //1. Cargar los componentes anotados con @component
        //Para el primer prototipo los leeré de la línea de comandos
        //Para la entrega final los deben leer del disco

        Class c = Class.forName(args[0]);

        if (c.isAnnotationPresent(Component.class)) {
            //2. Almacenar todos los métodos en una estructura llave valor
            //La llave será el path del web service y el valor son métodos
            //Todos lo métodos serán estáticos
            int passed = 0, failed = 0;
            for (Method m : Class.forName(args[0]).getMethods()) {
                if (m.isAnnotationPresent(GetMapping.class)) {
                    componentes.put(m.getDefaultValue().toString(), m);
                }
            }

        }
        // 3. Si llega una ruta que está enlazada a un componente
        // Ejecute el componente. No olvide los encabezados
        // Igualmente debe pasar parámetros. Implementelos.

        String pathDelGet = "/components/hello";

        Method m = componentes.get(pathDelGet.substring(10));

        if(m != null) {
            //Ejecute
        }

    }
}
