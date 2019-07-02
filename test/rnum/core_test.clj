(ns rnum.core-test
  (:require [rnum.core :as sut]
            [clojure.test :refer :all]
            ))

(deftest simple-tests
    (is (= (sut/to-numerals 1) "I"))
    (is (= (sut/to-numerals 5) "V"))
    (is (= (sut/to-numerals 88) "LXXXVIII"))
    (is (= (sut/to-numerals 1712) "MDCCXII"))
    (is (= (sut/to-numerals 2000) "MM"))
    (is (= (sut/to-numerals 2029) "MMXXIX"))
    )

(deftest boundery-tests
    (is (= (sut/to-numerals 0) ""))
    (is (= (sut/to-numerals -1) ""))
    )

(deftest internal-to-factors
  (is (= (sut/to-factors 12 [10 1]) [1 2]))
  (is (= (sut/to-factors 12 [8 4 1]) [1 1 0]))
  (is (= (sut/to-factors 100 [64 32 16 8 4 2 1]) [1 1 0 0 1 0 0 ]))
  (is (= (sut/to-factors 99 [50 40 10 9 5 4 1]) [1 1 0 1 0 0 0]))
  )

(deftest internal-expand-to-string
  (is (= (sut/expand-to-string [["x" 10]]) "xxxxxxxxxx"))
  (is (= (sut/expand-to-string [["x" 2] ["y" 2]]) "xxyy"))
  (is (= (sut/expand-to-string [["bob" 2] ["bad" 0] ["fred" 1] ["bad" 0]]) "bobbobfred"))
  )

; reverse checking

(defn reduce-factors
  [basis factors]
  (reduce + (map * basis factors)))

; generative tests

(deftest internal-to-factors-generative-explicit
  (are [d basis] (= (reduce-factors basis (sut/to-factors d basis)) d)
       12 [10 1]
       12 [8 4 1]
       99 [100 99 98 97 96 1]
       ))


