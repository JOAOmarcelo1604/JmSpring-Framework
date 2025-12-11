package devjoaomarcelo.jmspring.web;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import devjoaomarcelo.jmspring.annotations.JmGetMethod;
import devjoaomarcelo.jmspring.annotations.JmPostMethod;
import devjoaomarcelo.jmspring.explorer.ClassExplorer;
import devjoaomarcelo.jmspring.util.JmLogger;

public class JmSpringWebApplication {

	public static void run(Class<?> sourceClass) {

		java.util.logging.Logger.getLogger("org.apache").setLevel(java.util.logging.Level.OFF);
		long ini, fim;
		JmLogger.showBanner();

		try {
			ini = System.currentTimeMillis();
			JmLogger.log("Embeded Web Container", "Starting JmSpringWebApplication");

			extractMetaData(sourceClass);

			Tomcat tomcat = new Tomcat();
			Connector connector = new Connector();
			connector.setPort(8080);
			JmLogger.log("Embeded Web Container", "Web Container started on port 8080");
			tomcat.setConnector(connector);

			Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
			Tomcat.addServlet(ctx, "JmDispatchServlet", new JmDispatchServlet());

			ctx.addServletMappingDecoded("/*", "JmDispatchServlet");
			tomcat.start();
			fim = System.currentTimeMillis();
			double tempoEmSegundos = (fim - ini) / 1000.0; // Note os parênteses e o .0

			JmLogger.log("Embeded Web Container",
					String.format("JmSpringWebApplication starten in %.3f seconds", tempoEmSegundos));
			tomcat.getServer().await();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private static void extractMetaData(Class<?> sourceClass) throws Exception {

		List<String> allClasses = ClassExplorer.retriaveAllClasses(sourceClass);

		for (String jmClass : allClasses) {
			// recupero as anotações da classe
			Annotation an[] = Class.forName(jmClass).getAnnotations();
			for (Annotation classAnnotation : an) {
				if (classAnnotation.annotationType().getName()
						.equals("devjoaomarcelo.jmspring.annotations.JmController")) {
					JmLogger.log("Metadata Explorer", "Found a Controller " + jmClass);
					extractMethod(jmClass);
				}
			}
		}
	}

	private static void extractMethod(String className) throws Exception {
		for (Method method : Class.forName(className).getDeclaredMethods()) {
			for (Annotation an : method.getAnnotations()) {
				if (an.annotationType().getName().equals("devjoaomarcelo.jmspring.annotations.JmGetMethod")) {
					String path = ((JmGetMethod) an).value();
					JmLogger.log("", "+ method: " + method.getName() + " - URL GET = " + path);
				} else if (an.annotationType().getName().equals("devjoaomarcelo.jmspring.annotations.JmPostMethod")) {
					String path = ((JmPostMethod) an).value();
					JmLogger.log("", "+ method: " + method.getName() + " - URL POST = " + path);
				}
			}

		}
	}
}
