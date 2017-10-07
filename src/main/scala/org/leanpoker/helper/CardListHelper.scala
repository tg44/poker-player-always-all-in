package org.leanpoker.helper

import org.leanpoker.player.{Card}

object CardListHelper{

  def biggestRankInList(cards: Seq[Card]): String = {
    CardRank.all.find(c => cards.exists(c2 => c2.rank == c)).get
  }

  def isThereAPairInThisList(cards: Seq[Card]): Boolean = {
    cards.groupBy(_.rank).size != cards.size
  }

  def isThereSameColorInThisList(cards: Seq[Card]): Boolean = {
    cards.groupBy(_.suit).size != cards.size
  }

  def countHigh(cards: Seq[Card]): Int = {
    cards.count(c => CardRank.hights.contains(c.rank))
  }

  def countReallyHigh(cards: Seq[Card]): Int = {
    cards.count(c => CardRank.reallyHighs.contains(c.rank))
  }

  def countMid(cards: Seq[Card]): Int = {
    cards.count(c => CardRank.mid.contains(c.rank))
  }

  def hasRankInHand(rank: String, cards: Seq[Card]): Boolean = {
    cards.exists(_.rank == rank)
  }

  def hasRanksInHand(rankA: String, rankB: String, cards: Seq[Card]): Boolean = {
    hasRankInHand(rankA, cards) && hasRankInHand(rankB, cards)
  }

  def naiveAllIn(cards: Seq[Card]): Boolean = {
    isThereAPairInThisList(cards) && countHigh(cards) > 0 ||
      hasRankInHand(CardRank.A, cards) && hasRankInHand(CardRank.K, cards)
  }

  def utgAllIn(cards: Seq[Card]): Boolean = {
    (isThereAPairInThisList(cards) && countReallyHigh(cards) > 0) ||
      (hasRankInHand(CardRank.A, cards) && hasRankInHand(CardRank.K, cards)) ||
      (hasRankInHand(CardRank.A, cards) && hasRankInHand(CardRank.Q, cards)) ||
      (hasRankInHand(CardRank.A, cards) && hasRankInHand(CardRank.J, cards) && isThereSameColorInThisList(cards))
  }

  def earlyPositionAllIn(cards: Seq[Card]): Boolean = {
    utgAllIn(cards) ||
      (isThereAPairInThisList(cards) && hasRankInHand(CardRank.r10, cards)) ||
      (hasRankInHand(CardRank.A, cards) && hasRankInHand(CardRank.J, cards)) ||
      (hasRankInHand(CardRank.K, cards) && hasRankInHand(CardRank.Q, cards) && isThereSameColorInThisList(cards))
  }

  def earlyPositionHoldLicit(cards: Seq[Card]): Boolean = {
    hasRankInHand(CardRank.K, cards) && hasRankInHand(CardRank.Q, cards)
  }

  def middlePositionAllIn(cards: Seq[Card]): Boolean = {
    earlyPositionAllIn(cards) || earlyPositionHoldLicit(cards) ||
      (hasRankInHand(CardRank.K, cards) && hasRankInHand(CardRank.J, cards) && isThereSameColorInThisList(cards)) ||
      (hasRankInHand(CardRank.Q, cards) && hasRankInHand(CardRank.J, cards) && isThereSameColorInThisList(cards))
  }

  def middlePositionHoldLicit(cards: Seq[Card]): Boolean = {
    hasRanksInHand(CardRank.A, CardRank.r10, cards) ||
      (hasRankInHand(CardRank.K, cards) && hasRankInHand(CardRank.J, cards))||
      (hasRankInHand(CardRank.Q, cards) && hasRankInHand(CardRank.J, cards)) ||
      hasRankInHand(CardRank.r9, cards) && isThereAPairInThisList(cards)
      hasRankInHand(CardRank.r8, cards) && isThereAPairInThisList(cards)
  }

  def latePositionAllIn(cards: Seq[Card]): Boolean = {
    middlePositionAllIn(cards) || middlePositionHoldLicit(cards)
  }

  def latePositionHoldLicit(cards: Seq[Card]): Boolean = {
    hasRankInHand(CardRank.r7, cards) && isThereAPairInThisList(cards) ||
    hasRankInHand(CardRank.r6, cards) && isThereAPairInThisList(cards) ||
    hasRankInHand(CardRank.r5, cards) && isThereAPairInThisList(cards) ||
      hasRanksInHand(CardRank.K, CardRank.r10, cards) ||
      hasRanksInHand(CardRank.Q, CardRank.r10, cards) ||
      hasRanksInHand(CardRank.J, CardRank.r10, cards) ||
      (hasRanksInHand(CardRank.J, CardRank.r9, cards) && isThereSameColorInThisList(cards))
  }

  def midrangeHand(cards: Seq[Card]): Boolean = {
    isThereAPairInThisList(cards) && countMid(cards) > 0 ||
      hasRankInHand(CardRank.A, cards) && countHigh(cards) > 1 ||
      hasRankInHand(CardRank.K, cards) && hasRankInHand(CardRank.Q, cards)
  }

  def notSoBadHand(cards: Seq[Card]): Boolean = {
    midrangeHand(cards) ||
      hasRanksInHand(CardRank.A, CardRank.r9, cards) ||
      hasRanksInHand(CardRank.K, CardRank.J, cards) ||
      hasRanksInHand(CardRank.K, CardRank.r10, cards)||
      hasRanksInHand(CardRank.Q, CardRank.J, cards)||
      hasRanksInHand(CardRank.Q, CardRank.r10, cards)||
      hasRanksInHand(CardRank.J, CardRank.r10, cards)
  }
}

object CardSuit {
  val hearts = "hearts"
  val spades = "spades"
  val clubs = "clubs"
  val diamonds = "diamonds"
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

  val hights = Seq(A, K, Q, J, r10)

  val reallyHighs = Seq(A, K, Q, J)

  val mid = Seq(r9, r8, r7, r6, r5)

  val others = Seq(r4, r3, r2)

  val all = hights ++ mid ++ others
}