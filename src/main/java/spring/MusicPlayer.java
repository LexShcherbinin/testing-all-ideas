package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MusicPlayer {
  private Music music;

  @Value("${musicPlayer.name}")
  private String name;

  @Value("${musicPlayer.volume}")
  private int volume;

  public String getName() {
    return name;
  }

  public MusicPlayer setName(String name) {
    this.name = name;
    return this;
  }

  public int getVolume() {
    return volume;
  }

  public MusicPlayer setVolume(int volume) {
    this.volume = volume;
    return this;
  }

  private Music music1;
  private Music music2;

  @Autowired
  public MusicPlayer(@Qualifier("rockMusic") Music music1, @Qualifier("classicalMusic") Music music2) {
    this.music1 = music1;
    this.music2 = music2;
  }

  public MusicPlayer() {

  }

  public void setMusic(Music music) {
    this.music = music;
  }

  public void playMusic() {
    System.out.println("Playing: " + music.getSong());
  }

}
