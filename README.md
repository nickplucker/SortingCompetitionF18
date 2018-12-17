# SortingCompetitionMaterials2018

Materials for UMM CSci 3501 "Algorithms and computability" 2018 sorting competition.

# Table of contents
* [Goal of the competition](#goal)
* [The data](#data)
* [How is the data generated](#generating)
* [String Feature Background](#stringbackground)
* [How do you need to sort the data](#sortingRules)
* [Setup for sorting](#setup)
* [Submision deadlines](#deadlines)
* [Scoring](#scoring)
* [System specs](#specs)
* [Results of the first preliminary round](#round1)
* [Results of the final competition](#final)
* [Presentations](#presentation)


## Goal of the competition <a name="goal"></a>

The Sorting Competition is a multi-lab exercise on developing the fastest sorting algorithm for a given type of data. By "fast" we mean the actual running time and not the Big-Theta approximation. The solutions are developed in Java and will be ran on a single processor.

## The data  <a name="data"></a>

Data for this sorting competition consists of standard UTF-8 character strings of length 100 to 1000 characters passed to the sorting method as an array of strings.  The strings will be randomly selected from the file `corpus.txt` (available on this repo) which is comprised of the ASCII versions of the works of Arthur Conan Doyle and the complete works of Shakespeare.  In the first file source all newlines have been converted to spaces, and in the second, all newlines and tabs have been converted to spaces. The initial files were downloaded from https://sherlock-holm.es/stories/plain-text/cnus.txt and https://ocw.mit.edu/ans7870/6/6.006/s08/lecturenotes/files/t8.shakespeare.txt respectively.  


## How is the data generated <a name="generating"></a>

* A random number `l` between 100 and 1000 (uniform) is generated
* A random location `t` within the text file `corpus.txt` (positions 1 to `n-l+1`) is selected
* The substring covering `t` to `t+l-1` is extracted

The generated strings are written into a data file one per line. Sample data files are: [data1.txt](data1.txt) (1000 elements), [data2.txt](data2.txt) (1000 elements), and [data3.txt](data3.txt) (10000 elements). 

## String feature background (LRMUS and M-LRMUS) <a name="stringbackground"></a>

Every text string has at least one substring (possibly the entire string) that is unique.  A **left to right minimial unique substring** (LRMUS) is one that becomes repetitive when the last letter is removed.  For example, the sentence 

```
the alien was confused by the assignment
```
Has **many** unique substrings: `the alien`, `confused`, `assignment`, `l`, `li`, `ali`, etc.

It also has many LRMUS's.  Some include:

`the al`, `he al`, `e al`, ` al`, `l`, 

You can check by removing the last letters.  Consider the two substrings `the al` and `the as`.  Removing the last letter produces `the a` which is no longer unique. 

Unique substrings of length 1, such as  `l`, are also considered to be an LRMUS.  Note that the space character in the sample string is **not** an LRMUS because that character appears more than once.

An LRMUS is **maximal** (M-LRMUS) if it is the **longest LRMUS** in a string.  Every string will have at least one M-LRMUS, but it may have several.  In our sample string `the al` and `the as` are both M-LRMUSs.  If the string had been of the form

```
the alien was confused by the a
```

then only `the al` is an M-LRMUS.

## How do you need to sort the data <a name="sortingRules"></a>

The strings are compared as follows

1. The one with the shortest (M-LRMUS) comes first
2. In the event of a tie, the string with the left-most M-LRMUS comes first
3. In the event of a tie, the string whose M-LRMUS is alphabetically earliest comes first
4. In the event of a tie, the normal alphabetical ordering determines which comes first

For instance:

* `Zunzibar` comes before `rorocco` since the length of the M-LRMUS's in `Zunzibar` are 1, but in `rorocco` it is 3.
* `rorocco` comes before `abarbah`:  because although both have M-LRMUS of 3, The left-most LRMUS in `rorocco` is `ror` in position 0 and the left-most LRMUS in `abarbah` is `bar` which appears in position 1.
* `Zunzibar` comes after `Africa`:   The left-most M-LRMUS in the first string is `Z` in position 1 and it is `A`, also in position 1, in the second.  Since `A` comes before `Z`.
* `Aunzibar` comes after `Africa`:  Both have the same left-most M-RLMUS of `A` in position 1.  So we revert to normal alphabetical order. 

The file [Group0.java](src/Group0.java) provides a Comparator that implements this comparison and provides some tests. Please
consult it as needed. However, note that this is a very slow implementation, and you should think of a way to make it much faster. 

Once the data is sorted, it is written out to the output file, also one number per line, in the increasing order (according to the comparison given above). The files [out1.txt](out1.txt), [out2.txt](out2.txt), and [out3.txt](out3.txt) have the results of sorting for the three given data files. 

## Setup for sorting <a name="setup"></a>

The file [Group0.java](src/Group0.java) provides a template for the setup for your solution. Your class will be called `GroupN`, where `N` is the group number that is assigned to your group. The template class runs the sorting method once before the timing for the [JVM warmup](https://www.ibm.com/developerworks/library/j-jtp12214/index.html). It also pauses for 10ms before the actual test to let any leftover I/O or garbage collection to finish. Since the warmup and the actual sorting are done on the same array (for no reason other than simplicity), the array is cloned from the same input data. 

The data reading, the array cloning, the warmup sorting, and writing out the output are all outside of the timed portion of the method, and thus do not affect the total time. 

You may not use any global variables that depend on your data. You may, however, have global constants that are initialized to fixed values (no computation!) before the data is being read and stay the same throughout the run. These constants may be arrays of no more than 1000 `long` numberss or equivalent amount of memory. For instance, if you are storing an array of objects that contain two `long` fields, you can only have 500 of them. We consider one `long` to be the same as two `int` numbers, so you can store an array of 2000 `int` numbers.  
If in doubt about specific cases, please discuss with me. 

The method in the [Group0.java](src/Group0.java) files that you may modify is the `sort` method. It must take the array of strings. The return type of the method can be what it is now, which is the same as the parameter type `String []`, or it can be a different array type. If you are sorting in-place, i.e. the sorted result is in the same array, then you can just return a reference to that array, as my prototype method does, or make your sorting method `void`. If you are returning a different type of an array, the following rules have to be followed:
* Your `sort` method return type needs to be changed to whatever  array you are returning, and consequently the type of `sorted` array in `main` needs to be changed. 
* Your return type has to be an array (not an array list!) and it has to have the same number of elements as the original array. 
* You need to supply a method to write out your result into a file. The file has to be exactly the same as in the prototype implementation; they will be compared using `diff` system command. 

If you are not changing the return type, you don't need to modify anything other than `sort` method. 

Even though you are not modifying anything other than the `sort` method, you still need to submit your entire class: copy the template, rename the Java class to your group number, and change the`sort` method. You may use supplementary classes, just don't forget to submit them. Make sure to add your names in comments when you submit. 

Your program must print **the only value**, which is the **time** (as it currently does). Any other printed output disqualifies your submission. If you use test prints, make sure to turn them off for submission. 

**Important:** if the sorting times may be too small to distinguish groups based on just one run of the sorting, so I may loop over the sorting section multiple times. If this is the case, I will let you know no later than a day after the preliminary competition and will modify `Group0` file accordingly.  

## Submision deadlines <a name="deadlines"></a>

See Canvas for the deadlines of this competition (if you're not in the class I'll send you a copy via email).

*Thursday, Nov 1st* in the lab is the *final* competition. All source code is posted immediately after that. Those in class will have their names revealed, others may choose to remain anonymous (but the code will still be posted). 

## Scoring <a name="scoring"></a>

The programs are tested on a few (between 1 and 3) data sets. For each data set each group's program is run three times, the median value counts. The groups are ordered by their median score for each data file and assigned places, from 1 to N. 

The final score is given by the sum of places for all data sets. If the sum of places is equal for two groups, the sum of median times for all the runs resolves the tie. So if one group was first for one data set and third for the other one (2 sets total being run), it scored better than a group that was third for the first data set and second for the other. However, if one group was first for the first set and third for the other one, and the second group was second in both, the sum of times determines which one of them won since the sum of places is the same (1 + 3 = 2 + 2). 

If a program has a compilation or a runtime error, doesn't sort correctly, or prints anything other than the total time in milliseconds, it gets a penalty of 1000000ms for that run. 

## System specs <a name="specs"></a>

The language used is Java 8 (as installed in the CSci lab). It's ran on a single CPU core.  

I will post a script for running this program (with a correctness check and all), but for now a couple of things to know: run your program out of `/tmp` directory to avoid overhead of communications with the file server, and pin your program to a single core, i.e. run it like this:
``taskset -c 0 java GroupN``

## Results of the first preliminary round <a name="round1"></a>

This round will occur October 11th 2018

The results of the first preliminary round are posted in the folder [round1/bin](round1/bin). The folder has all the `.class` files. Groups that didn't have a submission (or their submission didn't compile or would go into an infinite loop) show up as errors. 

Each data file was ran three times for each group, and the median result was used for scoring. 

The data files were: [prelim1.txt](round1/bin/prelim1.txt) (700 numbers) and [prelim2.txt](round1/bin/prelim2.txt) (400 numbers). The correct output is in files [outRun1Group0.txt](round1/bin/outRun1Group0.txt) and [outRun2Group0.txt](round1/bin/outRun2Group0.txt).

The files [results1.txt](round1/bin/results1.txt) and [results2.txt](round1/bin/results2.txt) have the complete timing results for the two data sets. The file [scoreboard.txt](round1/bin/scoreboard.txt) has the places that each team got.  

The ruby script [run_all.rb](round1/bin/run_all.rb) was used to run the programs. If you want to reproduce the results or try them on a different set, so the following:
* Create a directory in `/tmp` directory on a lab machine. 
* Copy the entire `bin` folder from github into that directory. 
* Remove the output files, results1.txt, results2.txt, and the scoreboard.txt. 
* If you want to run the programs on different data sets, call your data files `prelim1.txt` and 'prelim2.txt` and copy them into the same folder - or copy then by different names, abnd then open the  script and change the files in the `inFileNames`. 
* Type `taskset -c 0 ruby run_all.rb` to run the script. 

## Results of the competition <a name="final"></a>

Congratulations to everybody who contributed!  We had some great competition.  Thanks to everybody who participated.  You can look at the scoreboard in round2/bin for more exact details on how the scoring went.  The only out-of-place example comes from an entry that was submitted as a JAR file and had to be run separately... it's therefore, not in the scoreboard file.  The results, in order of achievement:

* group 15 (not in the class): 
* group 8 (Nick Plucker, Ethan Uphoff)
* group 6 (Liam Koehler, Charlot Shaw)
* group 2 (Mathew Munns, Ahnaf Prio)
* group 10 (Nick Bushway, Leonid Scott)
* group 9 (John Hoff, Abenezer Monjor)
* group 13 (David Chong, Jubair Hassan)
* group 7 (Ethan Hamer, Avery Koranda)
* group 0 (reference)
* group 11 (Ariel Cordes, Sungjae Park)
* group 5 (Yutaro Miyata, Sam Score)
* Standalone (not in class):
* group 1 didn't sort correctly (Dexter An, John Schonenbaum)
* group 3 didn't sort correctly (Colin Rabe, Kai Zang)
* group 4 didn't sort correctly (Hunter Welch, Laly Xiong)
* group 14 didn't sort correctly (Spencer Hammerstein, Xuejiao Yang)
* group 12 didn't sort correctly (Vipul Sharma)


## Presentations <a name="presentation"></a>

The presentations date will November 29th . Each group needs to submit a
set of 4-5 pdf slides by 11:59pm the day before the presentation; you will have 3-4 minutes to present. Both group members must be a part of a presentation. The presentation must have:

* Your names (first names ok since this will be on github) and group number,
* Your results of the competition (overall place, times, correctness). If there were any correctness concerns, they need to be addressed.
* Big-picture description of your algorithm and data representation (what sorting have you used?
what were you storing? Precomputing?)
* The theoretical worst case efficiency and expected efficiency. For instance, insertion sort in theory has a quadratic efficiency, but if you are using it only on almost-sorted data, it's close to linear.
* The most intersting features of your algorithm.
* What worked, what didn't, what you would've done differently.

Be prepared to answer questions. 

## Final write-up <a name="writeup"></a>

*details will be posted here*
