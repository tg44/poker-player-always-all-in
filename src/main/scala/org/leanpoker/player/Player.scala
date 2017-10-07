package org.leanpoker.player

import com.google.gson.JsonElement
import org.leanpoker.helper.CardListHelper
import spray.json.{DefaultJsonProtocol, JsValue, RootJsonFormat}

object Player extends JsonSupport {

  val VERSION = "Default Scala folding player"

  def betRequest(request: JsValue): Int = {
    val state= request.convertTo[GameState]
    println("REQUEST OBJECT >>>>>>>>>>>>" + request.prettyPrint)
    val response = generateResponse(state)
    println("OUR RESPONSE >>>>>>>>>>>>" + response)
    response
  }

  def generateResponse(state: GameState): Int = {
    val me = GameStateHelper.me(state)
    if(CardListHelper.naiveAllIn(me.hole_cards.get)) me.stack
    else if (CardListHelper.midrangeHand(me.hole_cards.get)) me.stack
    else 0
  }

  def showdown(game: JsValue) {
    val state= game.convertTo[GameState]
  }
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
                    )

case class Card(rank: String, suit: String)

object GameStateHelper {
  def me(gameState: GameState): PlayerDto = {
    gameState.players(gameState.in_action.get)
  }

}

trait JsonSupport extends DefaultJsonProtocol {

  implicit val cardJsonFormatter: RootJsonFormat[Card] = jsonFormat2(Card)
  implicit val playerJsonFormatter: RootJsonFormat[PlayerDto] = jsonFormat7(PlayerDto)
  implicit val gameStateJsonFormatter: RootJsonFormat[GameState] = jsonFormat13(GameState)
}