# androidapp-androidapp-team06

***************
FIRST ITERATION

User Stories successfully implemented included: 1, 2, 3, 8, 11. Most of the implementations where mainly UI set up for the different menus and did not go into the game mechanics of the game. 

User story 1 \
  All scenarios tested and completed.\
  We are successfully able to open the app (without it crashing!) and display our basic MainMenu.class.\
  From the MainMenu, user can open the SettingsMenu and the TeamSelectMenu\
  We plan to update its graphics and "make it prettier" in a later iteration.\
User story 2\
  All scenarios tested and completed.\
  We successfully built a skeleton menu for where the game will be "housed" in GameView.class\
  As of now, no matter what Level is selected in LevelSelect, it brings the user to the same skeleton menu which was purposful as we will not implement the actual game levels with differeing game mechanics until a later iteration. However, each level button on LevelSelect passes tests and runs successfully.
  Evenutally the game will be shown here.\
User story 3\
  All scenarios tested and completed. \
  We successfully implemented a SettingsMenu in which the default is Rams (go Rams) but the user can choose between Spiders, Hokies, or Rams.\
  When selected, these buttons set up a file with the corresponding team name and that team name is stored.\
 User story 8\
 All scenarios tested and completed.\
  We successfully implemented the PauseButton in the GameView menu. This launches the SettingsMenu. It does not, however, pause the actual game as we have not implemented the game mechanics. \
  We also implemented the Wave Info button to display a message.\
  We implemented the Shop menu to show upon pressing the Shop button.\
 User story 11\
   We successfully implemented the a Health TextView that we plan to be able to update when the actual game is running.\
   We did not fully impelment scenarios 2 or 3, as these would delve into the deeper game mechanics, however we did implement and test the point system for the health.
 
 Difficulties: \
  Activity switching was fairly difficult to implement. \
    We started by setting up all menus of the game and implemented the activity switching later. Because of this, some of the menu default UI we used from Android Studio needed to be edited before the Activity Switches could occur.\
  Espresso testing was at times difficult to begin to set up but once up and running was not difficult to implement.
  
 ******************
 
 
 ***************
SECOND ITERATION
User story 16 \
  Successfully implemented different enemy types through classes
  Successfully completed all scenarios and tested them, however we were not able to fully test damage by towers on enemies.
    We will do this once other game mechanics are in place.
User story 17 \
  Successfully implemented all scenarios and tested them.
User story 6 \
  Sucessfully implemented all scenarios and tested them.
User story 7 \
  Partially completed all scenarios. Methods to update/upgrade are in place, but not fully implmented within GameView.
User story 5 \
  Successfully implemented all scenarios and tested them.
User story 4 \
  Successfully implemented all scenarios and tested them.
\
Some user story scenarios were edited as they were repetitive.
\
\
DIFFICULTIES\
We couldn't figure out how to test gameView within expresso . We researched for severeal hours but could'nt access the game view in our test class because it is a custom view and does not have a xml. Instead we recorded out test to test the functionality in game view.\
Learning the onTouch method and Canvas class was very difficult and therefore difficult to implement. This was due to confusing documentation and conflicting resources. \
Implementing a timer for the game was confusing as there are several types of timers and they all are very specialized. This may become the one of the most complex aspects of the game.\
******************
