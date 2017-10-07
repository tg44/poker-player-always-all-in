package org.leanpoker.helper

import org.leanpoker.player.{Card}

object CardListHelper{


  def isThereAPairInThisList(cards: Seq[Card]): Boolean = {
    cards.groupBy(_.rank).size != cards.size
  }

  def isThereHigh(cards: Seq[Card]): Boolean = {
    cards.exists(c => CardRank.hights.contains(c.rank))
  }

  def hasRankInHand(rank: String, cards: Seq[Card]): Boolean = {
    cards.exists(_.rank == rank)
  }

  def naiveAllIn(cards: Seq[Card]): Boolean = {
    isThereAPairInThisList(cards) && isThereHigh(cards) ||
      hasRankInHand(CardRank.A, cards) && hasRankInHand(CardRank.K, cards)
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
}