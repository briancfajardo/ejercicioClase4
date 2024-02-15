package MySpring;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import static java.lang.System.out;


public class MySpring {
    public static void main(String[] args) throws ClassNotFoundException {
        //Class c = String.class;
        Class c = Class.forName("MySpring.Alumno");
        Field[] campos = c.getFields();
        printMembers(campos, "Campos");

        Constructor[] constructores = c.getConstructors();

        printMembers(constructores, "Constructores");

        Method[] metodos = c.getMethods();

        printMembers(metodos, "Métodos");
    }
    private static void printMembers(Member[] mbrs, String s) {
        out.format("%s:%n", s);
        for (Member mbr : mbrs) {
            if (mbr instanceof Field)
                out.format(" %s%n", ((Field) mbr).toGenericString());
            else if (mbr instanceof Constructor)
                out.format(" %s%n", ((Constructor) mbr).toGenericString());
            else if (mbr instanceof Method)
                out.format(" %s%n", ((Method) mbr).toGenericString());
        }
        if (mbrs.length == 0)
            out.format(" -- No %s --%n", s);
        out.format("%n");

    }

    public static void suma(int a, int b){
        System.out.println("a + b = "+ (a + b));
    }
}
