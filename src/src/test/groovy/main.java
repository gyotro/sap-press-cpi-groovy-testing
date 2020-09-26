import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class main {

    public static void main(String[] args) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("testGroovy.groovy");
        GroovyClassLoader gcl = new GroovyClassLoader();
        String sPath = "src/src/main/groovy/testGroovy.groovy";
        Class clazz = null;
        Object o = null;
        Class[] arg = new Class[2];
        arg[0] = String.class;
        arg[1] = String.class;
        Object i = null;

        {
            try {
                clazz = gcl.parseClass(is, "testGroovy.groovy");
                o = clazz.newInstance();


                //clazz.getDeclaredMethod( "print", String.class, String.class ).invoke( o, "test", "test2" ) ;
                i = clazz.getDeclaredMethod( "print", arg ).invoke( o, "test", "test2" ) ;
                System.out.println(i.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                Class scriptClass = new GroovyScriptEngine( "src/src/main/groovy/" ).loadScriptByName( "testScript.groovy" ) ;
                Object scriptInstance = null;
                scriptInstance = scriptClass.newInstance();
                scriptClass.getDeclaredMethod( "print", arg ).invoke( scriptInstance, "test", "test2" ) ;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //System.out.println(o.getClass());
    }


}
