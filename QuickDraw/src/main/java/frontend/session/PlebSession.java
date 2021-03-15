/*
 * Copyright (C) 2021 lewiv
 *
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
package frontend.session;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Data;
import model.database.entity.Game;
import model.database.entity.Lobby;
import model.database.entity.Pleb;
import model.database.entity.Word;

/**
 * Bean to handle a pleb session.
 * @author lewiv
 */

@Data
@Named("plebSession")
@SessionScoped
public class PlebSession implements Serializable {
  private Pleb pleb;
  private Queue<Pleb> guessers;

  public boolean renderNextRoundButton() {
    return isHost() && !guessers.isEmpty();
  }

  public boolean renderBackToLobbyButton() {
    return isHost() && guessers.isEmpty();
  }

  public boolean isHost(){
    // null check guarding from run time null point exception.
    // Reason why its done this way :
    // Avoid multiple ifs to check every getter that it returns null.
    Optional<Pleb> host = Optional.of(pleb)
            .map(Pleb::getLobby)
            .map(Lobby::getHost);

    if (host.isEmpty()) {
      return false;
    }
    final boolean isPlayerTheHost = host.get().equals(pleb);
    return isPlayerTheHost;
  }

  public void setUsername(String username) {
    pleb.setUsername(username);
  }

  public String getUsername() {
    return pleb.getUsername();
  }

  public List<Word> getWords(){
    return getGame().getWords();
  }

  public void setScore(int score) {
    pleb.setScore(score);
  }

  public int getScore() {
    return pleb.getScore();
  }

  public void setLobby(Lobby l) {
    pleb.setLobby(l);
  }

  public Lobby getLobby() {
    return pleb.getLobby();
  }

  public Pleb getHost() {
    if (pleb == null || pleb.getLobby() == null) {
      return null;
    }
    return getLobby().getHost();
  }

  public Pleb getGuesser() {
    if (pleb == null || pleb.getLobby() == null) {
      return null;
    }
    return getGame().getGuesser();
  }

  public int getUser_id(){
    return pleb.getUser_id();
  }

  public Game getGame() {
    return getLobby().getGame();
  }

  public boolean isGuesser() {
    return pleb.equals(getGuesser());
  }
}
