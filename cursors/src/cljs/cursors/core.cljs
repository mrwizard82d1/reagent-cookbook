(ns cursors.core
    (:require [reagent.core :as reagent]))

;; Define the application state used to render the UI
(def app-state (reagent/atom {:foo {:bar "Hello, world!"
                                    :baz {:quux "Woot"}}}))

;; A component that looks inside the application state
(defn inside-app-state []
  [:div (str "Inside app-state: " @app-state)])

;; `foo-cursor` examines the `:foo` portion of `app-state`
;;
;; Remember to use a vector to access portions of `app-state`
(def foo-cursor (reagent/cursor app-state [:foo]))

(defn inside-foo-cursor []
  [:div (str "Inside foo-cursor: " @foo-cursor)])

;; `foobar-cursor` examines `[:foo :bar]`
(def foobar-cursor (reagent/cursor app-state [:foo :bar]))

(defn inside-foobar-cursor []
  [:div (str "Inside foobar-cursor: " @foobar-cursor)])

;; `foobaz-cursor` examines `[:foo :baz]`
(def foobaz-cursor (reagent/cursor app-state [:foo :baz]))

(defn inside-foobaz-cursor []
  [:div (str "Inside foobaz-cursor: " @foobaz-cursor)])

;; `foobazquux-cursor` examines `[:foo :baz :quux]`
(def foobazquux-cursor (reagent/cursor app-state [:foo :baz :quux]))

(defn inside-foobazquux-cursor []
  [:div (str "Inside foobazquux-cursor: " @foobazquux-cursor)])

(defn home []
  [:div
   [inside-app-state] 
   [inside-foo-cursor]
   [inside-foobar-cursor]
   [inside-foobaz-cursor]
   [inside-foobazquux-cursor]
   ])

(defn ^:export main []
  (reagent/render [home]
                  (.getElementById js/document "app")))

