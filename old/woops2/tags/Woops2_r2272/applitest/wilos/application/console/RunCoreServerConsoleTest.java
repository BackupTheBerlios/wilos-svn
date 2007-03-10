package wilos.application.console;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RunCoreServerConsoleTest {
	public static void main(String[] args) {
		
		@SuppressWarnings("unused")
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		}
}
