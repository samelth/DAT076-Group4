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
package frontend.controller;

import frontend.session.PlayerSessionBean;
import frontend.view.BackingBeanDrawPage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import model.database.dao.DrawingDAO;
import model.database.dao.DrawingWordDAO;
import model.database.dao.GameSessionDAO;
import model.database.entity.Drawing;
import model.database.entity.DrawingWord;
import model.database.entity.GameSession;

/**
 *
 * @author Karl Svensson
 */
@Data
@Named("drawingController")
@ViewScoped
public class DrawingpageController implements Serializable {
  @Inject PlayerSessionBean playerSessionBean;
  
  @Inject BackingBeanDrawPage drawPageView;
  
  @EJB
  DrawingWordDAO drawingWordDAO;
  
  @EJB
  GameSessionDAO gameSessionDAO;
  
  @EJB
  DrawingDAO drawingDAO;
  
  private LinkedList<DrawingWord> words;
  private DrawingWord currentWord;
  private GameSession currentGame;
  private final int game_id = 1;
  private String url;
  private Drawing d;
  
  @PostConstruct
  public void init(){
    currentGame = gameSessionDAO.findGameSessionByGameId(game_id);
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
  
  public void addToDB(){
    char[] url = drawPageView.getImgURL().toCharArray();
    d = new Drawing();
    d.setPlayer(playerSessionBean.getPlayer());
    d.setUrl(url);
    drawingDAO.create(d);
  }
  
}
