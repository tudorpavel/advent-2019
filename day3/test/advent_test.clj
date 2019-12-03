(ns advent-test
  (:require [advent :refer :all]
            [clojure.test :refer :all]))

(deftest next-point-test
  (is (= {:x 8, :y 0} (next-point {:x 0, :y 0} "R8")))
  (is (= {:x 8, :y 5} (next-point {:x 8, :y 0} "U5")))
  )

(deftest point->str-test
  (is (= "-3,123" (point->str {:x -3, :y 123})))
  (is (= "123,4" (point->str {:x 123, :y 4})))
  )

(deftest str->point-test
  (is (= {:x -3, :y 123} (str->point "-3,123")))
  (is (= {:x 123, :y 4} (str->point "123,4")))
  )

(deftest draw-segment-test
  (is (= {"0,0" {:wire2 3}, "1,0" {:wire2 4}, "2,0" {:wire1 1, :wire2 5}}
         (draw-segment {:x 0, :y 0} {:x 2, :y 0} :wire2
                       {"0,0" {:wire2 3}, "2,0" {:wire1 1}})))
  (is (= {"0,0" {:wire2 2}, "-1,0" {:wire2 3}, "-2,0" {:wire1 2, :wire2 4}}
         (draw-segment {:x 0, :y 0} {:x -2, :y 0} :wire2
                       {"0,0" {:wire2 2}, "-2,0" {:wire1 2}})))
  (is (= {"0,0" {:wire2 3}, "0,1" {:wire2 4}, "0,2" {:wire1 3, :wire2 5}}
         (draw-segment {:x 0, :y 0} {:x 0, :y 2} :wire2
                       {"0,0" {:wire2 3}, "0,2" {:wire1 3}})))
  (is (= {"0,0" {:wire2 2}, "0,-1" {:wire2 3}, "0,-2" {:wire1 4, :wire2 4}}
         (draw-segment {:x 0, :y 0} {:x 0, :y -2} :wire2
                       {"0,0" {:wire2 2}, "0,-2" {:wire1 4}})))
  (is (= {"0,1" {:wire1 1}, "0,2" {:wire1 2, :wire2 3}, "1,2" {:wire2 4}, "2,2" {:wire2 5}}
         (draw-segment {:x 0, :y 2} {:x 2, :y 2} :wire2
                       {"0,1" {:wire1 1}, "0,2" {:wire1 2, :wire2 3}})))
  )

(deftest trace-test
  (is (= {"1,0" {:wire1 3, :wire2 1}, "2,0" {:wire2 2}, "3,0" {:wire2 3}, "4,0" {:wire2 4},
          "4,1" {:wire2 5}, "4,2" {:wire2 6}, "4,3" {:wire2 7},
          "3,3" {:wire2 8}, "2,3" {:wire2 9},
          "2,2" {:wire2 10}, "2,1" {:wire2 11}, "2,-1" {:wire2 13}, "2,-2" {:wire2 14}}
         (trace ["R4" "U3" "L2" "D5"] {:x 0, :y 0} :wire2 {"1,0" {:wire1 3}})))
  )

(deftest manhattan-distance-from-origin-test
  (is (= 6 (manhattan-distance-from-origin {"0,4" {:wire2 4},
                                            "6,5" {:wire1 15, :wire2 15},
                                            "3,3" {:wire1 20, :wire2 20}})))
  )

(deftest solve1-test
  (is (= 6 (solve1 [["R8" "U5" "L5" "D3"], ["U7" "R6" "D4" "L4"]])))
  (is (= 159 (solve1 [["R75" "D30" "R83" "U83" "L12" "D49" "R71" "U7" "L72"],
                      ["U62" "R66" "U55" "R34" "D71" "R55" "D58" "R83"]])))
  (is (= 135 (solve1 [["R98" "U47" "R26" "D63" "R33" "U87" "L62" "D20" "R33" "U53" "R51"],
                      ["U98" "R91" "D20" "R16" "D67" "R40" "U7" "R15" "U6" "R7"]])))
  )

(deftest solve2-test
  (is (= 30 (solve2 [["R8" "U5" "L5" "D3"], ["U7" "R6" "D4" "L4"]])))
  (is (= 610 (solve2 [["R75" "D30" "R83" "U83" "L12" "D49" "R71" "U7" "L72"],
                      ["U62" "R66" "U55" "R34" "D71" "R55" "D58" "R83"]])))
  (is (= 410 (solve2 [["R98" "U47" "R26" "D63" "R33" "U87" "L62" "D20" "R33" "U53" "R51"],
                      ["U98" "R91" "D20" "R16" "D67" "R40" "U7" "R15" "U6" "R7"]])))
  )
