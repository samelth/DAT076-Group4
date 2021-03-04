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

import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;
import model.database.entity.DrawingWord;
import model.database.entity.QDrawingWord;

/**
 *
 * @author Karl Svensson
 */
@Stateless
public class DrawingWordDAO extends AbstractDAO<DrawingWord> {
  @Getter @PersistenceContext(unitName = "Games")
  private EntityManager entityManager;
  
  public DrawingWordDAO(){
    super(DrawingWord.class);
  }
  
  public List<DrawingWord> getWordsByLevel(int level){
    JPAQuery<?> query = new JPAQuery<Void>(entityManager);
    QDrawingWord drawingWord = QDrawingWord.drawingWord;
    
    return query
            .select(drawingWord)
            .from(drawingWord)
            .where(drawingWord.level.eq(level))
            .fetch();
  }
  
  public DrawingWord find(DrawingWord dw) {
    JPAQuery<DrawingWord> q = new JPAQuery<>(entityManager);
    QDrawingWord drawingWord = QDrawingWord.drawingWord;
    return q
            .select(drawingWord)
            .from(drawingWord)
            .where(drawingWord.word.eq(dw.getWord()))
            .fetchOne();
  }
}
