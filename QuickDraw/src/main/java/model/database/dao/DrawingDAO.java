/*
 * Copyright (C) 2021 Karl Svensson
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

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;
import model.database.entity.Drawing;
import model.database.entity.GameSession;
import model.database.entity.QDrawing;


/**
 *
 * @author Karl Svensson
 */
@Stateless
public class DrawingDAO extends AbstractDAO<Drawing> {
  @Getter @PersistenceContext(unitName = "Games")
  private EntityManager entityManager;
  
  public DrawingDAO() {
    super(Drawing.class);
  }
  
  public Drawing findDByRoundandGameSession(GameSession gs){
    JPAQuery<Drawing> query = new JPAQuery<>(entityManager);
    QDrawing drawing = QDrawing.drawing;
    return query
            .select(drawing)
            .from(drawing)
            .where(drawing.gameSession.game_id.eq(gs.getGame_id()).and(drawing.round.eq(gs.getRound())))
            .fetchFirst();
  
  }
  public Drawing find(Drawing d) {
    return getEntityManager().find(d.getClass(), d.getPlayer());
  }
  
}
