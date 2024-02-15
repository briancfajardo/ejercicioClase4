package MySpring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class InvokeMain {
    public static void main(String[] args) {
        // to invoke other class java -cp .\target\classes\ MySpring.InvokeMain MySpring.MySpring

        try {
            Class<?> c = Class.forName(args[0]);
            Class[] argTypes = new Class[]{int.class, int.class};
            Method main = c.getDeclaredMethod("suma", argTypes);
            String[] mainArgs = Arrays.copyOfRange(args, 1, args.length);
            System.out.format("invoking %s.main()%n", c.getName());
            main.invoke(null,  parseInt(mainArgs[0]), parseInt(mainArgs[1]));
            // production code should handle these exceptions more gracefully
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException x) {
            x.printStackTrace();
        } catch (NoSuchMethodException x) {
            x.printStackTrace();
        }
    }
}
