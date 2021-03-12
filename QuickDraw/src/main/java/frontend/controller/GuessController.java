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
import frontend.request.GuessRequest;
import frontend.session.PlebSession;
import frontend.view.GuessView;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import model.database.dao.LobbyDAO;
import model.database.dao.PictureDAO;
import model.database.dao.PlebDAO;
import model.database.entity.Picture;
import model.database.entity.Pleb;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Karl Svensson
 */
@Data
@Named("guessController")
@ViewScoped
public class GuessController implements Serializable {
  @Inject @Push private PushContext scoreChannel;
  @Inject       private PlebSession plebSession;
  @Inject       private GuessView guessView;
  @Inject       private DrawRequest drawRequest;
  @Inject       private GuessRequest guessRequest;
  
  @EJB private PictureDAO pictureDAO;
  @EJB private PlebDAO plebDAO;
  @EJB private LobbyDAO lobbyDAO;
  
  private Queue<Picture> submissions;
  private boolean guessing;
  private int count;
  private int numberOfPlebs;
  private int guessCount;
  
  @PostConstruct
  public void init() {
    submissions = new LinkedList<>();
    count = 0;
    guessCount = 0;
    numberOfPlebs = plebDAO.findPlebsInSameLobby(plebSession.getLobby()).size();
  }
  
  public void newPicture() {
    final Picture pic = pictureDAO.findDByGame(plebSession.getLobby().getGame()).get(0);
    submissions.add(pic);
    pictureDAO.remove(pic);
    count ++;
    showPicture();
  }
  
  public void showPicture(){
    // If all players has submitted and guesser has seen all pictures, jump to result.
    if(submissions.isEmpty() && count+1 == numberOfPlebs){
      guessRequest.jumpToResult();
    }
    else {
      if(!guessing) {
        if(!submissions.isEmpty()) {
          guessing = true;
          guessView.setImgURL(String.valueOf(submissions.peek().getUrl()));
          FacesContext.getCurrentInstance().getPartialViewContext().setRenderAll(true);
          PrimeFaces.current().executeScript("startProgressBar(\"#p1\");");
          PrimeFaces.current().executeScript("playTime(\"#countdown\");");
        } else {
          // Set empty picture and wait for next player to submit
          guessView.setImgURL("");
          guessing = false;
        }
      }
    }
  }
  
  public void guess() throws Exception {
    if(submissions.isEmpty()) return;
    String guessed = guessView.getGuessed();
    String correctWord = drawRequest.currentWord().getWord();
    if(guessed != null && guessed.equalsIgnoreCase(correctWord)) {
      final Collection<Pleb> recipients = plebDAO.findPlebsInSameLobby(plebSession.getLobby());
      Pleb p = submissions.remove().getPleb();
      p.setScore(plebDAO.find(p).getScore() + numberOfPlebs - guessCount);
      plebDAO.update(p);
      p = plebDAO.find(plebSession.getPleb());
      p.setScore(plebDAO.find(p).getScore() + numberOfPlebs - guessCount);
      plebDAO.update(p);
      scoreChannel.send("newScore",recipients);
      guessCount++;
      guessRequest.jumpToResult();
    }
    else{
      submissions.remove();
      guessing = false;
      guessCount++;
      showPicture();
    }
  }
}
