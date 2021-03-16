/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 * Entity class for a Lobby.
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
   * @see Lobby#addPleb
   * @param p the player to add to the lobby
   */
  public void removePleb(Pleb p) {
    plebs.remove(p);
    p.setLobby(null);
  }

  /**
   * Update a player in the lobby, remove the player then add
   * with updated information.
   * @param p player to update.
   */
  public void updatePleb(Pleb p) {
    final boolean playerExist = plebs.remove(p);
    if (playerExist) {
      plebs.add(p);
    }
  }

  /**
   * 
   * @return List of plebs in the lobby.
   */
  public List<Pleb> getPlebs(){
    return plebs;
  }

  /**
   * Get the host information.
   * @return Host of the lobby.
   */
  public Pleb getHost(){
    return host;
  }

}
