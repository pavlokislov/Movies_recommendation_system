###  Recommendation system<br />
The Recommendation system recommends movies based on similarity.<br />
The raters that have more similar taste, they have bigger weight for recommendation.<br />
To computes recommendation list can be used filters to restrict numbers of movies. <br />
Movies can be filtered by genres, year, directors, etc. Filters can be combined. <br />
Data stores more than 3000 movies and more than 10000 ratings made by 1048 raters. <br />

For example: let's find who has more similar taste.<br />
1. Let's represent each rater by vector of ratings.<br />
Sam   [0,5,2,7,0,8,1]<br />
Chris [6,7,5,0,0,0,9]<br />
Me    [2,6,0,4,5,4,6]<br />

2. To represent 1-10 scale, let's adjust ratings by subtraction 5.<br />
Sam   [ *,0,-3, 2,*, 3,-4]<br />
Chris [ 1,2, 0, *,*, *, 4]<br />
Me    [-3,1, *,-1,0,-1, 1]<br />
 
3. Let's calculate which a rater more close to me.<br />
Sam and me: 0*1 + 2*-1 + 3*-1 + -4*1 = -9<br />
Chris and me: 1*-3 + 2*1 + 4*1 = 3<br />
Chris has more similar taste, so his ratings has more weigh.<br />

**Algorithm:**<br />
The raters that have more similar taste, they have bigger weight for recommendation.<br />
Step 1. Center the ratings by subtracting the middle rating of five from each one.<br />
Step 2. Calculate dot product between meRater and other raters. Dot product is a measure of closeness between two raters.<br />
Step 3. Calculate similarity of meRater and others.<br />
Step 4. Calculate list based on average weighted rating with a rule <br />
that the movie has to be rated at least minimal number of raters and minimal number of raters from similarities list.<br />

Data of raters and movies are taken from https://github.com/sidooms/MovieTweetings. <br />
Also org.commons.apache.cvs used additional library for work with data.<br />
