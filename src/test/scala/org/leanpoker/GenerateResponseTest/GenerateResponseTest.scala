package org.leanpoker.GenerateResponseTest

import org.leanpoker.helper.{CardRank, CardSuit}
import org.leanpoker.player.{Card, GameState, Player, PlayerDto}
import org.scalatest.{FunSpec, MustMatchers}

class GenerateResponseTest extends FunSpec with MustMatchers {

  val DoubleAceGameState = GameState(
    Seq(
      PlayerDto("team1", 1200, "active", 100, Option.empty, "0", 0),
      PlayerDto("team1", 1200, "active", 100, Option.apply(Seq(
        Card(CardRank.A, CardSuit.clubs),
        Card(CardRank.A, CardSuit.clubs)
      )), "0", 1)
    ),
    "tournament_id",
    "game_id",
    4,
    Option.empty,
    20,
    10,
    1,
    Seq(
      Card(CardRank.A, CardSuit.clubs),
      Card(CardRank.A, CardSuit.clubs)
    ),
    20,
    30,
    Option.apply(1),
    Option.empty
  )

  val BadCardsGameState = GameState(
    Seq(
      PlayerDto("team1", 1200, "active", 100, Option.empty, "0", 0),
      PlayerDto("team1", 1200, "active", 100, Option.apply(Seq(
        Card(CardRank.r1, CardSuit.clubs),
        Card(CardRank.A, CardSuit.clubs)
      )), "0", 1)
    ),
    "tournament_id",
    "game_id",
    4,
    Option.empty,
    20,
    10,
    1,
    Seq(
      Card(CardRank.A, CardSuit.clubs),
      Card(CardRank.A, CardSuit.clubs)
    ),
    20,
    30,
    Option.apply(1),
    Option.empty
  )

  it("should be return as response all our chips cause its double ace") {
    Player.generateResponse(DoubleAceGameState) mustBe 1200;
  }

  it("should be return 0 if dont have good cards") {
    Player.generateResponse(BadCardsGameState) mustBe 0;
  }

}
