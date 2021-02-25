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
package frontend.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.Query;
import model.database.dao.DrawingWordDAO;
import model.database.dao.GameSessionDAO;
import model.database.entity.DrawingWord;
import model.database.entity.GameSession;
import org.omnifaces.cdi.Param;

/**
 *
 * @author lewiv, Karl Svensson
 */
@Named(value = "bbDrawPage")
@ViewScoped
public class BackingBeanDrawPage implements Serializable {
  
  @EJB
  DrawingWordDAO drawingWordDAO;
  
  @EJB
  GameSessionDAO gameSessionDAO;
  
  private LinkedList<DrawingWord> words;
  private DrawingWord currentWord;
  private GameSession currentGame;
  
  //TODO: Fetch the game_id from the current gameSession
  private final int game_id = 1;
  
  @PostConstruct
  public void init(){
    currentGame = gameSessionDAO.find(game_id);
    words = new LinkedList<>(drawingWordDAO.getWordsByLevel(currentGame.getLevel()));
    Collections.shuffle(words);    
  }
  
  public String getNextWord(){
    
    try{
    currentWord = words.remove();
    }catch(NullPointerException e){
      System.err.println("Error: No more words to draw");
    }
    
    return currentWord.getWord();
  }

}
