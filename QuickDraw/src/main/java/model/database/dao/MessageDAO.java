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
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;
import model.database.entity.Chat;
import model.database.entity.Message;
import model.database.entity.QMessage;

/**
 *
 * @author Samuel Local
 */
@Stateless
public class MessageDAO extends AbstractDAO<Message> {
  @Getter @PersistenceContext(unitName = "Games")
  private EntityManager entityManager;

  public MessageDAO() {
    super(Message.class);
  }

  @Override
  public Message find(final Message m) {
    return getEntityManager().find(m.getClass(), m.getMsg_id());
  }

  public List<Message> messages(final Chat chat){
    final JPAQuery<List<Message>> query = new JPAQuery<>(entityManager);
    final QMessage message = QMessage.message;
    return query
            .select(message)
            .from(message)
            .where(message.chat.chat_id.eq(chat.getChat_id()))
            .fetch();
}
}
