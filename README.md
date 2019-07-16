This is a test application intended to demonstrate examples of my code. Application use MVVM, LiveData. Application doesn't use Rx, Clean Architecture. It is possible to add the necessary technology if necessary.

## Application Requirements:
The application must generate prime numbers up to a given
limit. The interface should contain a field for entering the upper limit of calculations, the button
"start generation", list of results. At the end of the generation, the application should also display the elapsed time (the time not only of the calculations, but the total time, including drawing and other manipulations).

*Restrictions:*
* calculation can take a maximum of 5 minutes;
* The upper limit of the calculation is the upper limit of the Integer type number.

Examples of the algorithm for generating prime numbers:
* Input: 10; Output: [2,3,5,7]
* Input: 0; Output: []
* Input: 3; Output: [2]

**Also required: ​​**
* support work in all orientations of the screen;
* during generation display the indicator showing the current progress;
* to speed up calculations use multiple threads;
* provide caching of results and a button to clear this cache.

On a separate screen you need to display the history of calculations: time, limit limit, duration.
