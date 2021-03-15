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
package model.database.dao;

import com.querydsl.jpa.impl.JPAQuery;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;
import model.database.entity.Chat;
import model.database.entity.Lobby;
import model.database.entity.QChat;

/**
 *
 * @author Samuel Local
 */
@Stateless
public class ChatDAO extends AbstractDAO<Chat> {
  @Getter @PersistenceContext(unitName = "Games")
  private EntityManager entityManager;

  public ChatDAO(){
    super(Chat.class);
  }

  @Override
  public Chat find(final Chat c) {
    return getEntityManager().find(c.getClass(), c.getChat_id());
  }

  public Chat findChatByLobby(final Lobby lob) {
    final JPAQuery<Chat> q = new JPAQuery<>(entityManager);
    QChat chat = QChat.chat;
    return q
            .select(chat)
            .from(chat)
            .where(chat.lobby.lobby_id.eq(lob.getLobby_id()))
            .fetchOne();
  }
}
