package org.leanpoker.player

import com.google.gson.JsonElement
import spray.json.{DefaultJsonProtocol, JsValue, RootJsonFormat}

object Player extends JsonSupport {

  val VERSION = "Default Scala folding player"

  def betRequest(request: JsValue) = {
    val state= request.convertTo[GameState]
    println("REQUEST OBJECT >>>>>>>>>>>>" + state);
    val response = generateResponse(state)
    println("OUR RESPONSE >>>>>>>>>>>>" + response);
  }

  def generateResponse(state: GameState): Int = {
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
                      minimum_raise: Option[Int]
                    )

case class Card(rank: String, suit: String)

object CardSuit {
  val hearts = "hearts";
  val spades = "spades";
  val clubs = "clubs";
  val diamonds = "diamonds";
}

object CardRank {
  val r1 = "1"
  val r2 = "2"
  val r3 = "3"
  val r4 = "4"
  val r5 = "5"
  val r6 = "6"
  val r7 = "7"
  val r8 = "8"
  val r9 = "9"
  val r10 = "10"
  val J = "J"
  val Q = "Q"
  val K = "K"
  val A = "A"
}

trait JsonSupport extends DefaultJsonProtocol {

  implicit val cardJsonFormatter: RootJsonFormat[Card] = jsonFormat2(Card)
  implicit val playerJsonFormatter: RootJsonFormat[PlayerDto] = jsonFormat7(PlayerDto)
  implicit val gameStateJsonFormatter: RootJsonFormat[GameState] = jsonFormat13(GameState)
}