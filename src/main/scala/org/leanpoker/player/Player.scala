package org.leanpoker.player

import com.google.gson.JsonElement
import org.leanpoker.helper.CardListHelper
import spray.json.{DefaultJsonProtocol, JsValue, RootJsonFormat}

object Player extends JsonSupport {

  val VERSION = "haha we always all in xD"

  def betRequest(request: JsValue): Int = {
    try {
      val state = request.convertTo[GameState]
      println("REQUEST OBJECT >>>>>>>>>>>>" + request.prettyPrint)
      val response = generateResponse(state)
      println("OUR RESPONSE >>>>>>>>>>>>" + response)
      response
    } catch {
      case e =>
        println("=======++ error ++======")
        e.printStackTrace()
        0
    }
  }

  def generateResponse(state: GameState): Int = {
    val me = state.me

    if(state.preflop) {
      preFlopLogic(state)
    } else {
      if(CardListHelper.isThereAPairInThisList(me.hole_cards.get ++ state.community_cards)) {
        if(me.hole_cards.get.exists(_.rank == CardListHelper.biggestRankInList(state.community_cards))) {
          me.stack
        } else {
          if(CardListHelper.notSoBadHand(me.hole_cards.get)){
            state.holdLicit
          } else 0
        }
      } else {
        0
      }
    }
  }

  def preFlopLogic(state: GameState): Int = {
    val me = state.me
    if (CardListHelper.naiveAllIn(me.hole_cards.get)) me.stack
    else if(state.playersInGame > 2) {
      if (CardListHelper.midrangeHand(me.hole_cards.get) && me.bet > 0) state.holdLicit
      else 0
    } else {
      if (CardListHelper.midrangeHand(me.hole_cards.get)) me.stack
      else if(CardListHelper.notSoBadHand(me.hole_cards.get)){
        state.holdLicit
      } else 0
    }
  }

//  def preFlopLogic2(state: GameState): Int = {
//    val me = state.me
//    val position = state.whereAmI(state)
//    position match {
//      case 3 => utgStrategy(state) //UTG
//      case 4 | 0 => //early
//      case 1 => //middle
//      case 2 => //late
//    }
//
//  }
//
//  def earlyPositionStrategy(state: GameState): Int = {
//    val me = state.me
//  }

  def utgStrategy(state: GameState): Int = {
    val me = state.me
    if (CardListHelper.naiveAllIn(me.hole_cards.get)) me.stack
    else if(state.playersInGame > 2) {
      if (CardListHelper.midrangeHand(me.hole_cards.get) && state.affordableLoss) state.holdLicit
      else 0
    } else {
      if(CardListHelper.notSoBadHand(me.hole_cards.get)){
        state.holdLicit
      } else 0
    }
  }



  def showdown(game: JsValue) {
    val state= game.convertTo[GameState]
  }
}

case class Strategy(state: GameState) {

}

case class PlayerDto(name: String,
                  stack: Int,
                  status: String,
                  bet: Int,
                  hole_cards: Option[Seq[Card]],
                  version: String,
                  id: Int)

case class GameState(
                      players: Seq[PlayerDto],
                      tournament_id: String,
                      game_id: String,
                      round: Int,
                      bet_index: Option[Int],
                      small_blind: Int,
                      orbits: Int,
                      dealer: Int,
                      community_cards: Seq[Card],
                      current_buy_in: Int,
                      pot: Int,
                      in_action: Option[Int],
                      minimum_raise: Option[Int]
                    ) {

  def me: PlayerDto = {
    players(in_action.get)
  }

  def holdLicit: Int = {
    current_buy_in - me.bet
  }

  def raise(raiseAboveMinimum: Int = 0): Int = {
    holdLicit + minimum_raise.get + raiseAboveMinimum
  }

  def whereAmI(gameState: GameState): Int = {
    val k = (gameState.in_action.get - gameState.dealer) % gameState.players.count(_.status != "out")
    if(k<0) k + gameState.players.size
    else k
  }

  def affordableLoss: Boolean = {
    holdLicit < (me.stack+ me.bet) * 0.1
  }

  def playersInGame: Int = {
    players.count(p => p.bet + p.stack > 0)
  }

  def preflop: Boolean = {
    community_cards.isEmpty
  }

}

case class Card(rank: String, suit: String)

object GameStateHelper {

}

trait JsonSupport extends DefaultJsonProtocol {

  implicit val cardJsonFormatter: RootJsonFormat[Card] = jsonFormat2(Card)
  implicit val playerJsonFormatter: RootJsonFormat[PlayerDto] = jsonFormat7(PlayerDto)
  implicit val gameStateJsonFormatter: RootJsonFormat[GameState] = jsonFormat13(GameState)
}