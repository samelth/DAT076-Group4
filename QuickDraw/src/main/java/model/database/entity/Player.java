/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.database.entity;



import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 *
 * @author Karl Svensson
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Player implements Serializable {
  @JoinColumn(name = "lobby")
  @ManyToOne private Lobby lobby;
  @Id @GeneratedValue private int user_id;
  private String username;
  private int score;
  private boolean judge;
}