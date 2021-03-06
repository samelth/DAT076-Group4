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
import frontend.view.GuessView;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import model.database.dao.PictureDAO;
import model.database.entity.Picture;

/**
 *
 * @author Karl Svensson
 */
@Data
@Named("guessController")
@ViewScoped
public class GuessController implements Serializable {
  @Inject private PlebSession plebSession;
  @Inject private GuessView guessView;
  @Inject private DrawRequest drawRequest;
  
  @EJB private PictureDAO pictureDAO;
  
  public void nextPicture() {
    try {
      Picture pic = pictureDAO.findDByRoundandGameSession(plebSession.getLobby().getGame());
      String url = String.valueOf(pic.getUrl());
      guessView.setImgURL(url);
      pictureDAO.remove(pic);
    } catch(NullPointerException exception){
      guessView.setImgURL("");
    }
  }
  
  public void guess() {
    String guessed = guessView.getGuessed();
    String correctWord = drawRequest.nextWord().getWord();
    if(guessed.equalsIgnoreCase(correctWord)) {
      guessView.setResult("YOU GUESSED RIGHT!");
    }
    else{
      guessView.setGuessed("YOU GUESSED WRONG");
    }
  }
}
