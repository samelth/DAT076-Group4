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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author Karl Svensson <Svensson.Karl@iCloud.com>
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Lobby implements Serializable {
  @OneToMany(mappedBy = "lobby", cascade = CascadeType.ALL)
  private List<Pleb> plebs = new ArrayList<Pleb>();
  @OneToOne(cascade = CascadeType.REMOVE)
  private Game game;
  @Id @GeneratedValue @EqualsAndHashCode.Include private int lobby_id;
  private Pleb host;
  @OneToOne (cascade = CascadeType.REMOVE)
  private Chat chat;

  @Override
  public String toString() {
    return String.valueOf(this.lobby_id);
  }

   /**
   * According to hibernates recommendations
   * a developer must make sure the bidirectional relationship is always in sync at all times.
   * Hence the removePlayer() and addPlayer() is implemented in this way.
   * @see <a href="https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html />
   * @param p the player to add to the lobby
   */
  public void addPleb(Pleb p) {
    plebs.add(p);
    p.setLobby(this);
  }

  /**
   * @see addPlayer#addUser
   * @param p the player to add to the lobby
   */
  public void removePleb(Pleb p) {
    plebs.remove(p);
    p.setLobby(null);
  }

  public void updatePleb(Pleb p) {
    if (plebs.remove(p)) {
      plebs.add(p);
    }
  }

  public List<Pleb> getPlebs(){
    return plebs;
  }

  public Pleb getHost(){
    return host;
  }

}
