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
import model.database.entity.Word;
import model.database.entity.QWord;

/**
 *
 * @author Karl Svensson
 */
@Stateless
public class WordDAO extends AbstractDAO<Word> {
  @Getter @PersistenceContext(unitName = "Games")
  private EntityManager entityManager;
  
  public WordDAO(){
    super(Word.class);
  }
  
  public List<Word> findWordsByLevel(int lvl){
    final JPAQuery<?> query = new JPAQuery<Void>(entityManager);
    final QWord word = QWord.word1;
    
    return query
            .select(word)
            .from(word)
            .where(word.lvl.eq(lvl))
            .fetch();
  }
  
  @Override
  public Word find(Word dw) {
    return getEntityManager().find(dw.getClass(), dw.getWord());
  }
}
