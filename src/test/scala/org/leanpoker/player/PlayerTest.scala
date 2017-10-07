package org.leanpoker.player

import com.google.gson.JsonParser
import org.scalatest.{FunSpec, MustMatchers}
import spray.json._

class PlayerTest extends FunSpec with MustMatchers with JsonSupport {

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
    "pot":0,
    "in_action": 1,
    "minimum_raise": 240
  }"""

  it("test bet request") {
    val jsonElement = sample.parseJson
    Player.betRequest(jsonElement) must be (240)
  }


  it("can parse json") {

    val inJson = sample.parseJson
    inJson.convertTo[GameState]

  }


}