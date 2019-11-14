# Music Curator

![alt test](screenshot.png)

#### Short Description:

The music recommender will be a program with a user GUI that takes in a user’s favorite musical artists/songs and provides the user with recommendations on other artists/albums/songs that they may like (including the ability to play a preview of the artist or song). It will also be able to accept user feedback on the suggestions and update recommendations based on the user’s feedback (ex: remove suggestions that the user does not like and other recommendations that are too similar to the disliked suggestion). 

#### Features:
* Search artist, artist attributes (lyrics, trivia, artist description)
* “Artists you may like” recommendation list
* “Albums you may like” recommendation list
* Song/artist audio preview/download (30 seconds)
* com.curator.models.User feedback for the recommendations (like/dislike)
* Updates recommendation list based on user feedback
* Removes suggestions they don’t like and any other artists who are too similar
If a user likes a suggestion, the recommendation list will update with more artists similar to the liked suggestion

### Running on Eclipse:
##### Cloning Repository
1. File -> Import... -> Git -> Projects from Git (with smart import) -> Clone URI
2. Under "Location" fields, set "URI": https://github.com/UPenn-CIT599/final-project-music-com.curator. Set "Host": `github.com`. Set "Repository Path": `/UPenn-CIT599/final-project-music-com.curator`
3. Under "Connection", set "Protocol": `https`. Under "Authentication", fill your github username and password. Click Next.
4. Select "master" branch. Next, select desired directory or use the default setting. 
5. Next, cloning begins and click "Finish" once completed.

##### Set Run Configuration
6. From menu bar, "Run" -> "Run Configurations".
7. On the left, select "Java Application", then click "New launch configuration" on top left corner (blank paper icon).
8. On the newly created configuration select "Main" tab, set "Name" to `Main`. Under "Project", click "Browse..." and select `final-project-music-com.curator`. On the "Main class" path, click "Search..." and select `Main - com.com.curator` under "Matching items". Press OK.
9. On the same run configuration select "Arguments" tab, set "**VM arguments**" as `--module-path full/path/to/lib --add-modules javafx.controls,javafx.base,javafx.graphics,javafx.fxml,javafx.media`

Example path: `/home/username/eclipse-workspace/final-project-music-com.curator/lib`; or on Windows: `C:/com.curator.models.User/eclipse-workspace/final-project-music-com.curator/lib`.
10. "Apply" and "Close".

##### Set Resource Folder
11. Select top directory of `final-project-music-com.curator` on Project Explorer, then on the menu bar click "Project -> "Properties".
12. Choose "Java Build Path". Select "Source" tab.
13. Select "Add Folder...", and select "res" folder under "src/main". Press OK then "Apply and Close".

Project is ready to run. Go to "Run" -> "Run".

#### Team 71
* Isaac Longkutoy ([islong@seas.upenn.edu](mailto:islong@seas.upenn.edu)) 
* Luke Glover ([lcglover@seas.upenn.edu](mailto:lcglover@seas.upenn.edu))
* Meng Wang ([mw3131@seas.upenn.edu](mailto:mw3131@seas.upenn.edu))

