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

import frontend.request.DrawRequest;
import frontend.session.PlebSession;
import frontend.view.DrawView;
import frontend.view.ResultView;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import model.database.dao.PictureDAO;
import model.database.dao.WordDAO;
import model.database.dao.GameDAO;
import model.database.dao.LobbyDAO;
import model.database.dao.PlebDAO;
import model.database.entity.Picture;
import model.database.entity.Pleb;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

/**
 *
 * @author Karl Svensson
 */
@Data
@Named("drawController")
@ViewScoped
public class DrawController implements Serializable {
  @Inject @Push private PushContext guessChannel;
  @Inject @Push private PushContext resultChannel;
  @Inject       private PlebSession plebSession;
  @Inject       private DrawView drawView;
  @Inject       private DrawRequest drawRequest;
  @Inject       private ResultView resultView;
 
  @EJB private WordDAO wordDAO;
  @EJB private GameDAO gameDAO;
  @EJB private PictureDAO pictureDAO;
  @EJB private PlebDAO plebDAO;
  
  public void nextWord(){
    this.drawView.setWord(drawRequest.nextWord());
  }
  
  public void currentWord() {
    drawView.setWord(drawRequest.currentWord());
  }
  
  public void submitPicture(){
    char[] url;
    url = drawView.getImgURL().toCharArray();
    Picture pic = new Picture();
    pic.setPleb(plebSession.getPleb());
    pic.setUrl(url);
    pic.setGame(plebSession.getGame());
    pictureDAO.update(pic);
    Pleb guesser = plebSession.getGuesser();
    guessChannel.send("newPic", guesser);
    resultChannel.send("newPic", plebDAO.findPlebsInSameLobby(plebSession.getLobby()));
    
  }
}
