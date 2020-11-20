# Conway's Game of Life

## To Do:
- [x] "New" button, starts new `Game` with an empty grid
- [ ] Consider adding living points to a `Set` - when displayed iterate over these
- [ ] Consider removing dead cells whenever the `cells` `HashMap` has more coordinates than the grid (currently 50 x 50) - hopefully, this will keep the program running indefinitely
- [x] Tutorial on using JavaFX + FXML + Using a proper Controller
- [x] Fix code based on tutorial
- [x] Place living cells onto grid - do research or figure it out via printing to console
- [x] Pan using arrow keys / drag canvas?
- [ ] Load pattern files - ask classmate for link if they have it
- [ ] Allow the canvas to grow?
- [ ] Zoom in and out?

## Journal

### Day 0
This is my own, personal attempt at Conway's Game of Life. I decided to attempt this problem following a unit in University where this concept was covered, but where I was not able to implement a final working (meaning, non-crashing) program.

The basic rules of this version are:
- If the cell is dead and it has 3 living neighbours, it will be alive in the next round
- If the cell is alive and it has 2 or 3 neighbours, it will continue to be alive in the next round
- All other cells will be dead in the next round 

A few learnings I learned in this unit:
1. Limit the O Factor by using `Sets` over `Lists` where possible
2. Do not use a 2D array to contain coordinate details - this causes unnecessary issues, considering the grid is infinite
3. 2D arrays must also use nested loops to iterate through all items - we must limit iteration where possible
4. JavaFX (current library being used in our course):
	1. Has a `Point2D` class to easily keep track of coordinates
	2. Using a `HashMap` with a `Point2D` as the key and cell information as the value works quickly
	3. Displaying `Nodes` on a pane is a memory hog and will cause the program to hang and crash eventually; however, it is easier to click on `Nodes`
	4. Drawing to a `Canvas` is less intensive - learn how to do this
	5. Consider only drawing living cells to the `Canvas` to assist with potential memory issues
	6. Living cells that are off screen can still be "drawn" to the `Canvas`, preventing the need to check for cells that are "out-of-bounds"
5. One nice idea (that was not mine) is that cells that have been alive for more than one round (i.e. they are "static") can be displayed via another colour.
6. Try using FXML and the SceneBuilder to create the GUI.

**Note:** I will not be referencing any previous code from this unit while working through the problem, but may reference other online help.

### Day 1
Goals for the day were to gain some insight into drawing onto a `Canvas` using JavaFX. I referenced [this video](https://www.youtube.com/watch?v=_NvD0WzKTC8) for assistance. One thing I was not expecting to use was the `Affline` class to transform the `GraphicsContext`. This was walked through in the tutorial, and I intended to not use it. However, I was not able to draw a grid of lines to the `Canvas` accurately without it. 

The `Affline` class makes it easier to draw to the canvas because we do not have to manually transform each coordinate to the exact pixels. Each rectangle drawn can be done so with dimesions of 1 x 1.

I ended the day with a very basic program that could only draw the grid lines on the canvas.

[Add a picture]

### Day 2
I began work on the actual `Game`. The basic algorithm is:
1. The `Game` has a `HashMap` holding coordinate points and `Cells` (it's called `cells`). At first, it will only contain the randomly generated cells.
2. For each entry in `cells`, add all of its neighbours that *do not already exist in the map* to `cells`.
3. For each entry in `cells`, check how many of it's neighbours either exist in the map or are alive.
4. Following the rules stated in Day 0, update the `nextState` of each `Cell` in the map.
5. Update all the cells so that the `lastState` is update to the current state, `isAlive()`, and the current state is updated to the `nextState`.
6. Clear the `GraphicsContext`, draw all of the cells in the map to the `Canvas`, and redraw all the grid lines.

This method ultimately worked; however, I ran into some issues when attempting to update cells while also iterating through the `cells` map. Referencing [this StackOverflow thread](https://stackoverflow.com/questions/26494197/java-util-concurrentmodificationexception-when-removing-elements-from-a-hashmap), I was able to implement an `Iterator` which I had never used before. This worked surprisingly well and ultimately fixed the issues I was having.

At the end of the day, the program could step one round at a time through the game as expected. Living cells that were off screen did not cause crashes or errors. These patterns could eventually move back into frame.

[Add a picture]

### Day 3
Added a new button that would animate the game for me. Toward the end of the day, I ran the program several times for 20 - 60 minutes.

1. Ran for about 20 minutes, there eventually was some lag. It appears that there may have been a few larger patterns happening off screen. The lag was minimal - I think it was just taking a long time to work through all the cells. Basically, in the current code, whenever a cell is alive, its neighbours need to be added to the data structure *even if they stay dead forever*. I might need to counteract this by removing dead cells whenever the data structure gets too large.

2. Ran for about 20 minutes - no lag. I am not certain if there was anything offscreen.

3. 60 minutes - this one definitely have a 5-point glider that flew off screen. This experienced no lag and continued to run fine.

It was at this point, when adding in new `EventHandlers` that did not have an associated button that I started to struggle accessing parts of the FXML code. I tried implementing a way to pan around using the arrow keys, but I could never access the scene. I will look at some tutorials on Day 4 to help with this.

### Day 4
I got started later in the day than anticipated, but was still able to make great improvement. After watching a few videos by thenewboston on Youtube ([1](https://www.youtube.com/watch?v=LMdjhuYSrqg) & [2](https://www.youtube.com/watch?v=RojHZhj3UQA)), I gathered a better understanding of how to use a Controller separate from the Main `Application` class and was able to quickly move forward. I also needed to refer to [this StackOverflow thread](https://stackoverflow.com/questions/36754063/java-fx-scene-builder-how-to-make-event-key-pressed) to further solidify everything for me.

Taking some code that wasn't working correctly the day prior, I was able to verify my key presses were working. Unfortunately, my  arrow keys weren't playing along (they were cycling through the buttons instead). I found [this StackOverflow thread](https://stackoverflow.com/questions/49288049/keyevents-not-firing-in-javafx) which taught me about refocusing panes. This eventually solved my problem.

Using the arrow keys, users can now pan around the canvas. Upon hitting an arrow key, the `cells` `HashMap` will be iterated through. A new point a set distance away from the current point (depending on the direction) will be created as a key a new `HashMap` with the cell set as its value. `cells` will be cleared and set to this new `HashMap`. I was surprised how well this worked. The animation did not need to be paused in order for the panning to work correctly.

I also spent some time by myself figuring out the formula for getting the cell coordinates for mouse clicks on the canvas. Once this was figured out, it was fairly easy implement a cell on or off toggle.

I also added a "Clear" funcion and button. Now, pressing the Clear button empties `cells`, displays a blank screen, and pauses the animation. This same function is used when generating random cells rather than creating a new game every time. 

### Day 5
