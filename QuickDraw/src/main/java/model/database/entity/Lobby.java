/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.database.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
  @OneToMany(mappedBy = "lobby") private List<Player> players = new ArrayList<>();
  @Id @GeneratedValue private int lid;
  private int round;
 
  public void nextRound() {
    this.round++;
  }
  
  public void join(Player p) {
    players.add(p);
  }
  
  public void kick(Player p) {
    players.remove(p);
  }
  
  /**
   * @return sorted list of top three players in the lobby. Where the first element corresponds to first place. 
   */
  public List<Player> topThree() {
    return players
            .stream()
            .sorted((p1,p2) -> p2.getScore() - p1.getScore())
            .limit(3)
            .collect(Collectors.toList());
  }
}
