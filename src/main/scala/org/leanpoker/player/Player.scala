package org.leanpoker.player

import com.google.gson.JsonElement
import spray.json.{DefaultJsonProtocol, JsValue, RootJsonFormat}

object Player extends JsonSupport {

  val VERSION = "Default Scala folding player"

  def betRequest(request: JsValue) = {
    val state= request.convertTo[GameState]
    if(state.round == 0) 100
    else state.players(state.in_action.get).stack
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
                      minimum_raise: Int
                    )

case class Card(rank: String, suit: String)

trait JsonSupport extends DefaultJsonProtocol {

  implicit val cardJsonFormatter: RootJsonFormat[Card] = jsonFormat2(Card)
  implicit val playerJsonFormatter: RootJsonFormat[PlayerDto] = jsonFormat7(PlayerDto)
  implicit val gameStateJsonFormatter: RootJsonFormat[GameState] = jsonFormat13(GameState)
}