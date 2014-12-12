/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fourG.model;

import java.io.Serializable;

/**
 *
 * @author vogi23
 */
public enum ModelState implements Serializable{
    Home,                   // StartBildschirm
    SearchOnlineGames,       // Statusbildschirm mit möglichen Online Games, wo man teilnehmen kann. (Refresh Button)
    WaitingForRemoteJoin,   // Statusbildschimr mit aktuellen Anfragen von Online Gegnern
    PreparingEnemy,         // Waiting for Enemy to be ready (local or remote or loading saved game)
    Playing,                // Lokaler User oder Gegner ist am Zug: see model.getCurrentPlayer();
    GameOver                // Spiel beendet, Sieger steht fest und wird angezeigt
}
