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
  (is (= {"1,0" 1, "2,0" 2}
         (draw-segment {:x 0, :y 0} {:x 2, :y 0} inc {"2,0" 1})))
  (is (= {"-1,0" 1, "-2,0" 3}
         (draw-segment {:x 0, :y 0} {:x -2, :y 0} inc {"-2,0" 2})))
  (is (= {"0,1" 1, "0,2" 2}
         (draw-segment {:x 0, :y 0} {:x 0, :y 2} inc {"0,2" 1})))
  (is (= {"0,-1" 1, "0,-2" 3}
         (draw-segment {:x 0, :y 0} {:x 0, :y -2} inc {"0,-2" 2})))
  (is (= {"0,1" 1, "0,2" 1, "1,2" 1, "2,2" 1}
         (draw-segment {:x 0, :y 2} {:x 2, :y 2} inc {"0,1" 1, "0,2" 1})))
  )

(deftest trace-test
  (is (= {"1,0" 2, "2,0" 2, "3,0" 1, "4,0" 1,
          "4,1" 1, "4,2" 1, "4,3" 1,
          "3,3" 1, "2,3" 1,
          "2,2" 1, "2,1" 1}
         (trace ["R4" "U3" "L2" "D3"] {:x 0, :y 0} inc {"1,0" 1})))
  )

(deftest manhattan-distance-from-origin-test
  (is (= 6 (manhattan-distance-from-origin {"0,4" 1, "6,5" 16, "0,7" 1, "0,2" 1,
                                            "4,5" 1, "4,7" 1, "1,7" 1, "8,0" 1,
                                            "2,3" 1, "6,3" 1, "1,0" 1, "8,5" 1,
                                            "0,1" 1, "3,2" 1, "3,5" 1, "8,4" 1,
                                            "8,2" 1, "2,0" 1, "6,6" 1, "4,3" 1,
                                            "7,5" 1, "8,3" 1, "3,7" 1, "4,0" 1,
                                            "5,0" 1, "8,1" 1, "6,4" 1, "5,3" 1,
                                            "2,7" 1, "0,5" 1, "0,3" 1, "3,4" 1,
                                            "7,0" 1, "0,6" 1, "5,7" 1, "3,3" 16,
                                            "3,0" 1, "5,5" 1, "6,0" 1, "6,7" 1})))
  )

(deftest solve1-test
  (is (= 6 (solve1 [["R8" "U5" "L5" "D3"], ["U7" "R6" "D4" "L4"]])))
  (is (= 159 (solve1 [["R75" "D30" "R83" "U83" "L12" "D49" "R71" "U7" "L72"],
                      ["U62" "R66" "U55" "R34" "D71" "R55" "D58" "R83"]])))
  (is (= 135 (solve1 [["R98" "U47" "R26" "D63" "R33" "U87" "L62" "D20" "R33" "U53" "R51"],
                      ["U98" "R91" "D20" "R16" "D67" "R40" "U7" "R15" "U6" "R7"]])))
  )
