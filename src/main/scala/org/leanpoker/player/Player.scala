package org.leanpoker.player

import com.google.gson.JsonElement
import spray.json.JsValue

object Player {

  val VERSION = "Default Scala folding player"

  def betRequest(request: JsonElement) = 100

  def showdown(game: JsonElement) {

  }
}


class Dto {

  val sample =
    """{"players":[
    {
      "name":"Player 1",
      "stack":1000,
      "status":"active",
      "bet":0,
      "hole_cards":[],
      "version":"Version name 1",
      "id":0
    },
    {
      "name":"Player 2",
      "stack":1000,
      "status":"active",
      "bet":0,
      "hole_cards":[],
      "version":"Version name 2",
      "id":1
    }
    ],
    "tournament_id":"550d1d68cd7bd10003000003",
    "game_id":"550da1cb2d909006e90004b1",
    "round":0,
    "bet_index":0,
    "small_blind":10,
    "orbits":0,
    "dealer":0,
    "community_cards":[],
    "current_buy_in":0,
    "pot":0
  }"""

}

case class Player(name: String, stack: Int, status: String, bet: Int, hole_cards: JsValue, version: String, id: Int)

case class GameState(
                      tournament_id: String,
                     game_id: String,
                      round: Int,
                      bet_index: Int,
                      small_blind: Int,
                      orbits: Int,
                      dealer: Int,
                      community_cards: JsValue,
                      current_buy_in: Int,
                      pot: Int
                    )