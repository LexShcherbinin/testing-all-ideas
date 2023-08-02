package spring.firstmvc.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.web.filter.HiddenHttpMethodFilter;
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
    return new Class[] {SpringConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    System.out.println("/".repeat(100));
    return new String[] {"/"};
  }

  @Override
  public void onStartup(ServletContext aServletContext) throws ServletException {
    super.onStartup(aServletContext);
    registerHiddenFieldFilter(aServletContext);
  }

  private void registerHiddenFieldFilter(ServletContext aContext) {
    aContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null, true, "/*");
  }

}
