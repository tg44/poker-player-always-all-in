package org.leanpoker.helper

import org.leanpoker.player.Card

object CardListHelper{
    def isThereAPairInThisList(cards: Seq[Card]): Boolean = {
      return CardListHelper.isThereASameColorPair(cards) && CardListHelper.isThereASameNumberPair(cards);
    }

    private def isThereASameColorPair(cards: Seq[Card]): Boolean = {
      true
    }

    private def isThereASameNumberPair(cards: Seq[Card]): Boolean = {
      true
    }
}
