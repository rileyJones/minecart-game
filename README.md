# Minecart Game
## By Riley Jones

### Objective
The goal is to defeat as many enemies as possible by knocking them into the 
holes at the four corners of the map.

### Mechanics
* Every time you beat a "wave" of enemies, up to 4 blocks will fall on the
map
* Every 5 waves, a full heal power-up will drop at the player spawn
* Enemies path to the player using Dijkstra's algorithm

### Items
* Sword: Knocks enemies back, can be canceled early by using the sword again
* Shield: Grants invulnerability while the button is held, but slows the 
player down drastically
* Feather: Grants temporary invulnerability when used, and allows the
player to pass over the holes in the 4 corners
* Staff: Creates a block in front of the player that acts like a normal 
block, with the exception that it is removed when it comes into contact with
the sword. There can be 4 out at any given time
* Ray: Creates an instant warp between 1 tile away from the wall and the
player's location, enemies will path through the 'tunnel'
* Spices: Grants a temporary movement speed boost
* Gale: Hidden item, instantly defeats any enemy it touches

### Controls
* Enter: Open the inventory menu
* Arrow Keys: Move 
* Z: Use the B-Button Item
* X: Use the A-Button Item

### Debug
* Press the "`" or "~" key to show enemy pathing
* While on the inventory screen, hold both 'z' and 'x' and press 'ENTER" to
select the Gale item

### Low-Bar Checklist
* Collision Detection: Complete
* Path-Finding: Complete
* State-Based Behavior: Complete
* Maze Generation: Complete
* Item Hot-Swapping: Complete
* Portal Dynamics: Incomplete
