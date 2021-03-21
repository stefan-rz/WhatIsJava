import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XlassLoader extends ClassLoader{
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class xlass = new XlassLoader().loadClass("Hello.xlass");
        xlass.getMethod("hello").invoke(xlass.newInstance());
    }

    @Override
    protected Class<?> findClass(String name) {
        File f = new File(name);
        FileInputStream in = null;
        try {
            /**
             * HOw to convert File to byte[], please go through tinyurl.com/3udch2mk
             */

            Path path = Paths.get(name);
            byte[] decodeArr = Files.readAllBytes(path);
            for (int i = 0; i < decodeArr.length; i++) {
                decodeArr[i] = (byte) (255 - decodeArr[i]);
            }
            //defineClass进行加载
            return defineClass("Hello", decodeArr, 0, decodeArr.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
