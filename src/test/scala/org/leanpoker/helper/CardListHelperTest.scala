package org.leanpoker.helper

import org.leanpoker.player.{Card}
import org.scalatest.{FunSpec, MustMatchers}

class CardListHelperTest extends FunSpec with MustMatchers {

  val noPairs: Seq[Card] = Seq(Card(CardRank.K,CardSuit.clubs), Card(CardRank.A,CardSuit.clubs))
  val hasPairs: Seq[Card] = Seq(Card(CardRank.K,CardSuit.clubs), Card(CardRank.K,CardSuit.clubs))

  it("should be false") {
    CardListHelper.isThereAPairInThisList(noPairs) mustBe false
  }

  it("should be true") {
    CardListHelper.isThereAPairInThisList(hasPairs) mustBe true
  }
}