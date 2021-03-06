(ns lair.rect
  (:require [clj-tuple :as tuple]))

(def rect tuple/vector)

(defn intersects?
  ([[x1 y1 w1 h1] [x2 y2 w2 h2]]
   (intersects? x1 y1 (or w1 1) (or h1 1) x2 y2 (or w2 1) (or h2 1)))
  ([x1 y1 w1 h1 x2 y2 w2 h2]
   (or (and (<= x1 x2 (+ x1 w1))
            (<= y1 y2 (+ y1 h1)))
       (and (<= x1 (+ x2 w2) (+ x1 w1))
            (<= y1 (+ y2 h2) (+ y1 h1))))))

(defn contains-point?
  ([[x y w h] [x2 y2]]
   (contains-point? x y w h x2 y2))
  ([[x y w h] x2 y2]
   (contains-point? x y w h x2 y2))
  ([x y w h x2 y2]
   (intersects? x y w h x2 y2 1 1)))

(defn scale
  ([[x y w h] n]
   (scale x y w h n))
  ([x y w h n]
   (rect (int (* x n)) (int (* y n)) (int (* w n)) (int (* h n)))))

(defn points
  ([[x y w h]]
   (points x y w h))
  ([x y w h]
   (for [x (range x (+ x w))
         y (range y (+ y h))]
     (vector x y))))

(defn edges
  ([[x y w h]]
   (edges x y w h))
  ([x y w h]
   (concat
    (for [x (range x (+ x w))
          y [y (+ y h -1)]]
      (vector x y))

    (for [y (range y (+ y h))
          x [x (+ x h -1)]]
      (vector x y))
   )))
