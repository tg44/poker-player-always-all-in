package org.leanpoker.player

import com.google.gson.JsonElement
import spray.json.{DefaultJsonProtocol, JsValue, RootJsonFormat}

object Player {

  val VERSION = "Default Scala folding player"

  def betRequest(request: JsonElement) = 100

  def showdown(game: JsonElement) {

  }
}


case class PlayerDto(name: String,
                  stack: Int,
                  status: String,
                  bet: Int,
                  hole_cards: Seq[Card],
                  version: String,
                  id: Int)

case class GameState(
                    players: Seq[PlayerDto],
                      tournament_id: String,
                     game_id: String,
                      round: Int,
                      bet_index: Int,
                      small_blind: Int,
                      orbits: Int,
                      dealer: Int,
                      community_cards: Seq[Card],
                      current_buy_in: Int,
                      pot: Int
                    )

case class Card(rank: String, suit: String)

trait JsonSupport extends DefaultJsonProtocol {

  implicit val cardJsonFormatter: RootJsonFormat[Card] = jsonFormat2(Card)
  implicit val playerJsonFormatter: RootJsonFormat[PlayerDto] = jsonFormat7(PlayerDto)
  implicit val gameStateJsonFormatter: RootJsonFormat[GameState] = jsonFormat11(GameState)
}