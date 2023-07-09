package spring.music;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ClassicalMusic implements Music {

//  private ClassicalMusic() {
//
//  }
//
//  public static ClassicalMusic getClassicalMusic() {
//    return new ClassicalMusic();
//  }

  @PostConstruct
  public void doMyInit() {
    System.out.println("Doing my initialization");
  }

  @PreDestroy
  public void doMyDestroy() {
    System.out.println("Doing my destruction");
  }

  @Override
  public String getSong() {
    return "Hungarian Rhapsody";
  }
}
