package spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import spring.music.ClassicalMusic;
import spring.music.RockMusic;

@Configuration
@ComponentScan("spring")
@PropertySource("classpath:musicPlayer.properties")
public class SpringConfig {

  @Bean
  public ClassicalMusic classicalMusicBean() {
    return new ClassicalMusic();
  }

  @Bean
  public RockMusic rockMusicBean() {
    return new RockMusic();
  }

  @Bean
  public MusicPlayer musicPlayer() {
    return new MusicPlayer(rockMusicBean(), classicalMusicBean());
  }

}
