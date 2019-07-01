(ns rnum.core
  (:gen-class))

(def numerals [["M" 1000]
               ["CM" 900]
               ["D" 500]
               ["CD" 400]
               ["C" 100]
               ["XC" 90]
               ["L" 50]
               ["XL" 40]
               ["X" 10]
               ["IX" 9]
               ["V" 5]
               ["IV" 4]
               ["I" 1]])

(defn firsts [v] (map first v))

(defn seconds [v] (map second v))

(defn to-factors
  "factor a decimal into the sum of multiples of a set of values
  returnes a vector that is positionaly equivilent to the values in basis
  so that multiplying basis with output vector and summing the result will give d"
  {:test #(do
    (assert (= (to-factors 12 [10 1]) [1 2]))
    (assert (= (to-factors 12 [8 4 1]) [1 1 0])))}
  [d basis]
  (rest
    (firsts
      (reduce
        #(conj %1
              ((juxt quot mod)
               (last (last %1))
               %2))
         [[0 d]]
         basis))))

(defn expand-to-string
  "expand a vector of pairs : [str num] and expand it
  by concatinating all sets of str repeated num times"
  {:test #(do
    (assert (= (expand-to-string [["x" 2] ["y" 2]]) "xxyy"))
    (assert (= (expand-to-string [["bob" 2] ["x" 0] ["fred" 1] ["x" 0]]) "bobbobfred")))}
  [v]
  (clojure.string/join
    (map
      #(let [[s n] %1]
        (clojure.string/join (repeat n s)))
      v)))

(defn to-numerals [d]
  "from decimal to roman numerals"
  (if
    (< d 1)
      ""
      (expand-to-string
        (map
          vector
          (firsts numerals)
          (to-factors d (seconds numerals))))))

(defn -main
  [& args]
  (println (map (juxt identity to-numerals) (range 100)))
  )

