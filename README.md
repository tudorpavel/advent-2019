# Advent of Code 2019

I will attempt to learn Clojure during this year's AoC.

## Setup

To run a particular day, `cd` into the folder and run:

```
clj -m advent < input.txt
```

To run tests using Spacemacs and CIDER, first open a CIDER session:

```
SPC m s i
```

And run all tests (you might need to run this command in both the src file and the test file to load them):

```
SPC m t a
```

Then you can run a single test by moving the cursor on a `deftest` and run it with:

```
SPC m t t
```
