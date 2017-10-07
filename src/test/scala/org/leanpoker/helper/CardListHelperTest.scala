package org.leanpoker.helper

import org.leanpoker.player.{Card}
import org.scalatest.{FunSpec, MustMatchers}

class CardListHelperTest extends FunSpec with MustMatchers {

  val noPairs: Seq[Card] = Seq(Card(CardRank.K,CardSuit.clubs), Card(CardRank.A,CardSuit.clubs))
  val hasPairs: Seq[Card] = Seq(Card(CardRank.K,CardSuit.clubs), Card(CardRank.K,CardSuit.clubs))

  val hasHighPairR10: Seq[Card] = Seq(Card(CardRank.r10,CardSuit.clubs), Card(CardRank.r10,CardSuit.clubs))
  val hasHighPairKing: Seq[Card] = Seq(Card(CardRank.K,CardSuit.clubs), Card(CardRank.K,CardSuit.clubs))
  val notHighPair: Seq[Card] = Seq(Card(CardRank.r9,CardSuit.clubs), Card(CardRank.r9,CardSuit.clubs))

  val hasAceAndKing: Seq[Card] = Seq(Card(CardRank.K,CardSuit.clubs), Card(CardRank.A,CardSuit.clubs))
  val hasAceAndNotKing: Seq[Card] = Seq(Card(CardRank.A,CardSuit.clubs), Card(CardRank.J,CardSuit.clubs)) // for naiveAllIn it hsould only be ace && king

  it("should be false") {
    CardListHelper.isThereAPairInThisList(noPairs) mustBe false
  }

  it("should be true") {
    CardListHelper.isThereAPairInThisList(hasPairs) mustBe true
  }

  describe("naive all in") {
    it("should be true if we have a high pair") {
      CardListHelper.naiveAllIn(hasHighPairR10) mustBe true
      CardListHelper.naiveAllIn(hasHighPairKing) mustBe true
    }

    it("should be true if we have ace and king") {
      CardListHelper.naiveAllIn(hasAceAndKing) mustBe true
    }

    it("should be false if we have no high pair") {
      CardListHelper.naiveAllIn(notHighPair) mustBe false
    }

    it("should be false if we have an ace and not king (high)") {
      CardListHelper.naiveAllIn(notHighPair) mustBe false
    }
  }
}