/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.database.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Karl Svensson <Svensson.Karl@iCloud.com>
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Lobby implements Serializable {
  @OneToMany(mappedBy = "lobby", cascade = CascadeType.ALL) 
  private List<Player> players = new ArrayList<Player>();
  @OneToOne private GameSession gameSession;
  @Id @GeneratedValue private int lid;
  private Player host;
  @OneToOne private Chat chat;
  
  @Override
  public String toString() {
    return String.valueOf(this.lid);
  }
  
   /**
   * According to hibernates recommendations
   * a developer must make sure the bidirectional relationship is always in sync at all times. 
   * Hence the removePlayer() and addPlayer() is implemented in this way. 
   * @see <a href="https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html />
   * @param p the player to add to the lobby
   */
  public void addPlayer(Player p) {
    players.add(p);
    p.setLobby(this);
  }
  
  /**
   * @see addPlayer
   * @param p the player to add to the lobby
   */
  public void removePlayer(Player p) {
    players.remove(p);
    p.setLobby(null);
  }
  
  public void updatePlayer(Player p) {
    if(players.remove(p)) {
      players.add(p);
    }
  }
  
  public List<Player> getPlayers(){
    return players;
  }
  
  public Player getHost(){
    return host;
  }
  
}
