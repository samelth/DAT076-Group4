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
package model.entity;

import java.util.ArrayList;
import javax.ejb.EJB;
import org.junit.Assert;
import model.database.entity.Lobby;
import model.database.entity.Player;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author lewiv
 */



public class LobbyTest {
  
  @EJB
  private Lobby lob;
  
  @EJB
  private Player player;
  
  @Before
  public void init() {
     lob = new Lobby();
     player = new Player();
  }
  
  @Test 
  public void checkThatNextRoundMethodIncrementsRound(){
    int prevRound = lob.getRound();
    lob.nextRound(); 
    Assert.assertEquals(prevRound + 1, lob.getRound());
  }

  @Test 
  public void checkThatJoinPlayerAddsPlayerToLobby(){
    int lobSize = lob.getPlayers().size();
    lob.join(player);
    Assert.assertEquals(lobSize + 1,lob.getPlayers().size());
    Assert.assertTrue(lob.getPlayers().contains(player));
  }
  
  @Test 
  public void checkThatKickPlayerKicksPlayerFromLobby(){
    lob.join(player);
    lob.kick(player);
    Assert.assertFalse(lob.getPlayers().contains(player));
  }
}