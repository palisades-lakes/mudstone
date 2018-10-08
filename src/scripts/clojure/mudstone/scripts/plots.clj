;; clj src/scripts/clojure/mudstone/scripts/plots.clj
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)
;;----------------------------------------------------------------
(ns mudstone.scripts.plots
  
  {:doc "data files for R plots of interpolants"
   :author "palisades dot lakes at gmail dot com"
   :version "2018-10-08"}
  
  (:require [clojure.java.io :as io])
  (:import [java.util Arrays]
           [mudstone.java.functions.scalar 
            ConstantFunctional
            AffineFunctional
            QuadraticLagrange
            QuadraticMonomial
            QuadraticMonomialShifted
            QuadraticMonomialStandardizrd
            QuadraticNewton
            CubicHermite
            CubicLagrange
            CubicMonomial
            CubicNewtonScalarFunctional]
           [mudstone.java.test.scalar Common]))

;;----------------------------------------------------------------


;;----------------------------------------------------------------

(def interpolant-classes
  [ConstantFunctional
   AffineFunctional
   QuadraticLagrange
   QuadraticMonomial
   QuadraticMonomialShifted
   QuadraticMonomialStandardizrd
   QuadraticNewton
   CubicHermite
   CubicLagrange
   CubicMonomial
   CubicNewton])

;;----------------------------------------------------------------

(defn supported-knots? [^Class interpolant-class 
                        ^"[[D" knots]
  
  ;;----------------------------------------------------------------
  
  (defn ^ScalarFunctional interpolate [^Class interpolant-class 
                                       ^ScalarFunctional f 
                                       ^"[[D" knots]
    
    "Return an instance of <code>interpolant-class</code>
   constructed by call the 'interpolate' class (static) method on
   the supplied test function and knots."
    
    (let [^Method method (.getMethod fclass "interpolate"
                           nil Object Object)]
      (.invoke method nil f knots)))
  
  ;;----------------------------------------------------------------
  
  (doseq [^doubles vk value-knots]
    (println (Arrays/toString vk)))
  
  (println AffineFunctional1d/INTERPOLATORS)
  (println AffineFunctional1d/interpolateXY)