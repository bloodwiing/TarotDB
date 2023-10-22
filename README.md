# TarotDB

A database of Tarot cards to explore Major & Minor arcana and even make 3-card readings.

This was a 3rd University assignment for the JAVA course, I do not plan any updates for this.

## Art

* [Rider Waite Smith](https://en.wikipedia.org/wiki/Rider%E2%80%93Waite_Tarot)
  * by Pamela Colman Smith (1909)
* [Golden Thread Tarot](https://labyrinthos.co/collections/tarot-decks-for-sale/products/golden-thread-tarot-deck-cards)
  * by Labyrinthos Academy aka Tina Gong (2016)
* [Seventh Sphere Rider Waite Smith Tarot](https://labyrinthos.co/collections/tarot-decks-for-sale/products/seventh-sphere-rider-waite-smith-tarot-deck)
  * by Labyrinthos Academy aka Tina Gong (2021)

## Disclaimer

This project is not associated with Labyrinthos Academy. Please do not use the decks provided in this project. This project is only used for educational purposes.

## Notes

There are a couple of issues with design, specifically:
* Creating and adding elements is slow. The filter should be re-written to contain a list of elements without the need to re-create them each filter use.
* There are a couple of places where I could've extracted shared behaviour into a parent class, like `createElement()`.
* Some functions are lengthy and hard to read, I could've split them.
