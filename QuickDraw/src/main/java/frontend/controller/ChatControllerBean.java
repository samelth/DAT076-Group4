/*
 * Copyright (C) 2021 Samuel Local
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
package frontend.controller;

import frontend.session.PlayerSessionBean;
import frontend.view.BackingBeanCreateGame;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import model.chat.Chat;
import model.chat.Message;
import model.database.dao.PlayerDAO;
import model.database.entity.Player;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

/**
 *
 * @author Samuel Local
 */
@Named(value = "chatControllerBean")
@RequestScoped
public class ChatControllerBean {
  @Inject
  private BackingBeanCreateGame backingBeanCreateGame;
  @EJB
  private PlayerDAO playerDAO;
  @EJB
  private Chat chat;
  @Inject @Push
  private PushContext messageChannel;
  
  @Inject
  private PlayerSessionBean playerSessionBean;
  
  public void onPostNewMessage(){
    chat.add(new Message(playerSessionBean.getPlayer().getUsername(),backingBeanCreateGame.getNewMessage()));
    Collection<Player> recipients = playerDAO.findUsersInSameLobby(playerSessionBean.getPlayer().getLobby());
    messageChannel.send("newMsg",recipients);
  }
 
}
