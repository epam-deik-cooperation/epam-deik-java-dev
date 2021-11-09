package com.epam.training.webshop;

import com.epam.training.webshop.config.WebShopConfiguration;
import com.epam.training.webshop.presentation.cli.CliInterpreter;
import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CliApplication {

    public static void main(String[] args) throws IOException {
        final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(WebShopConfiguration.class);
        //        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(System.out::println); //példa a Context-ben lévő Bean-ek neveinek kiíratására
        final CliInterpreter interpreter = applicationContext.getBean(CliInterpreter.class);
        interpreter.start();
    }
}
