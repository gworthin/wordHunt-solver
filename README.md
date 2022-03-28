# wordHunt-solver
Takes in a board from the GamePigeon WordHunt game and outputs all possible words

<p float="left">
  <img src="https://user-images.githubusercontent.com/102486685/160310824-3c3cac65-1d04-40e9-a0d9-3ab20447f192.jpeg" height="200">
  <img src="https://user-images.githubusercontent.com/102486685/160310827-a91eedd2-7a23-48cf-8a3e-8eed44919c92.jpg" height="200">
  <img src="https://user-images.githubusercontent.com/102486685/160310829-ef4203f6-a46d-4d33-be5e-ebc7eba4e463.jpeg" height="200">
</p>

WordHunt is a GamePigeon game played on a board of 16 letters. The player must create words by connecting neighboring letters to one another. For example, VOTE is a valid word as seen above. Words with more letters are worth more points, and the player with the most points at the end of 80 seconds wins. 

This program takes a string of 16 letters representing the board as input and prints a list of all words that can be played.

Boards are represented as strings going left-right and top-down. For example, the above board would be represented with the string *VCWKOLHWEVFDOTET*
