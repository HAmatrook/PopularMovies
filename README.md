# PopularMovies
Android App for movies information.
# Features
- Present the user with a grid arrangement of movie posters upon launch.
- Allow your user to change sort order via a setting:
  - The sort order can be by most popular or by highest-rated
- Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
  - original title
  - movie poster image thumbnail
  - a plot synopsis (called overview in the api)
  - user rating (called vote_average in the api)
  - release date
- Allow users to view and play trailers (either in the youtube app or a web browser).
- Allow users to read reviews of a selected movie.
- Also allow users to mark a movie as a favorite in the details view by tapping a button (star).
- Make use of Android Architecture Components (Room, LiveData, ViewModel and Lifecycle) to create a robust an efficient application.
- Create a database using Room to store the names and ids of the user's favorite movies (and optionally, the rest of the information needed to display their favorites collection while offline).
- Modify the existing sorting criteria for the main view to include an additional pivot to show their favorites collection.
