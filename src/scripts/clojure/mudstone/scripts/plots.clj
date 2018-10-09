;; clj src/scripts/clojure/mudstone/scripts/plots.clj
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)
;;----------------------------------------------------------------
(ns mudstone.scripts.plots
  
  {:doc "data files for R plots of interpolators"
   :author "palisades dot lakes at gmail dot com"
   :version "2018-10-08"}
  
  (:require [clojure.java.io :as io]
            [clojure.string :as s])
  (:import 
    [java.io PrintWriter]
    [java.lang Math]
    [java.lang.reflect Method]
    [java.util Arrays]
    [mudstone.java.functions.scalar 
     Interval
     ConstantFunctional
     AffineFunctional
     QuadraticLagrange
     QuadraticMonomial
     QuadraticMonomialShifted
     QuadraticMonomialStandardized
     QuadraticNewton
     CubicHermite
     CubicLagrange
     CubicMonomial
     CubicNewton
     ScalarFunctional]
    [mudstone.java.test.scalar Common]))

;;----------------------------------------------------------------

(def interpolators
  [#_ConstantFunctional
   #_AffineFunctional
   QuadraticLagrange
   QuadraticMonomial
   QuadraticMonomialShifted
   QuadraticMonomialStandardized
   QuadraticNewton
   CubicHermite
   CubicLagrange
   CubicMonomial
   CubicNewton])

;;----------------------------------------------------------------

;(defn- ^String hex [^double x]
;  (.toUpperCase
;    (Long/toHexString
;      (Double/doubleToLongBits x))))

;;----------------------------------------------------------------

(defn- valid-knots? [^Class interpolator ^"[[D" knots]
  (let [^Method method (.getMethod interpolator "validKnots"
                         (into-array Class [(Class/forName "[[D")]))]
    #_(println (.toString method))
    (boolean
      (.invoke method nil (into-array Object [knots])))))

;;----------------------------------------------------------------

(defn- ^ScalarFunctional interpolate [^Class interpolator 
                                      ^ScalarFunctional f 
                                      ^"[[D" knots]
  
  "Return an instance of <code>interpolator</code>
   constructed by call the 'interpolate' class (static) method on
   the supplied test function and knots."
  
  (let [^Method method (.getMethod interpolator "interpolate"
                         (into-array Class [Object Object]))]
    (.invoke method nil (into-array Object [f knots]))))

;;----------------------------------------------------------------

(defn prefix [^ScalarFunctional testf
              ^"[[D" knots]
  (s/join "." [(.safeName testf) (Common/knotString knots)]))

;;----------------------------------------------------------------

(defn- write-micro-file [^ScalarFunctional testf
                         ^"[[D" knots 
                         ^ScalarFunctional interpolant]
  (when (Double/isFinite (.doubleArgmin testf Interval/ALL))
    (let [file (io/file 
                 "plots" "interpolate" 
                 (s/join "." [(prefix testf knots) 
                              (.getSimpleName (class interpolant))
                              "micro" "tsv"]))
          _(println (.getPath file))
          _(io/make-parents file)
          xmin (double (.doubleArgmin testf Interval/ALL))
          n (int 255)
          n2 (inc (* 2 n))
          x0 (double 
               (loop [i (int 0)
                      x (double xmin)]
                 (if (< i n) 
                   (recur (inc i) (Math/nextDown x))
                   x)))
          tname (.safeName testf)
          iname (.getSimpleName (class interpolant))]
      (with-open [^PrintWriter w (PrintWriter. (io/writer file))]
        (.println w (s/join "\t" ["function" "x" "y"]))
        (dotimes [i (alength ^"[D" (aget knots 0))]
          (let [x (double (aget knots 0 i))]
            (.println w 
              (s/join "\t"
                      ["valueKnot" x (.doubleValue testf x)]))))
        (dotimes [i (alength ^"[D" (aget knots 1))]
          (let [x (double (aget knots 1 i))]
            (.println w 
              (s/join "\t" ["slopeKnot" x (.slope testf x)]))))
        (loop [i (int 0)
               x (double x0)]
          (when (<= i n2)
            (.println w 
              (s/join "\t" [tname x (.doubleValue testf x)]))
            (recur (inc i) (Math/nextUp x))))
        (loop [i (int 0)
               x (double x0)]
          (when (<= i n2)
            (.println w 
              (s/join "\t" [iname x (.doubleValue interpolant x)]))
            (recur (inc i) (Math/nextUp x))))))))

;;----------------------------------------------------------------

(doseq [^ScalarFunctional testf (take 3 Common/testFns)
        ^"[[D" knots Common/allKnots
        ^Class interpolator (take 3 interpolators)]
  (when (valid-knots? interpolator knots)
    (let [interpolant (interpolate interpolator testf knots)]
      (write-micro-file testf knots interpolant))))

#_(let [^ints files (int-array 1)]
  (doseq [^ScalarFunctional testf (take 1 Common/testFns)
          ^"[[D" knots (take 1 Common/allKnots)]
    (println)
    (println (.toString testf))
    (println (.safeName testf))
    (println (Arrays/toString ^doubles (aget knots 0))
             (Arrays/toString ^doubles (aget knots 1)))
    (println (Common/knotString knots))
    (let [^ints counter (int-array 1)]
      (doseq [^Class interpolator (take 1 interpolators)]
        (when (valid-knots? interpolator knots)
          (let [interpolant (interpolate interpolator testf knots)]
            (when (= interpolator (class interpolant))
              (aset-int counter 0 (inc (aget counter 0)))
              (println (.getSimpleName interpolator))
              (println (.toString interpolant))))))
      (when (< 0 (aget counter 0))
        (println (s/join "." 
                         [(.safeName testf)  
                          (Common/knotString knots)
                          "micro"
                          "tsv"])) 
        (println "interpolants:" (aget counter 0))
        (aset-int files 0 (inc (aget files 0))))))
  (println "files:" (aget files 0)))
