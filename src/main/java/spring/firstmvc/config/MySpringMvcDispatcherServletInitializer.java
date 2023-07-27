package spring.firstmvc.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    System.out.println("+".repeat(100));
    return null;
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    System.out.println("*".repeat(100));
    return new Class[]{SpringConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    System.out.println("/".repeat(100));
    return new String[]{"/"};
  }
}
