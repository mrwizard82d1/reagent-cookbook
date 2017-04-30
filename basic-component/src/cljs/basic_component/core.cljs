(ns basic-component.core
    (:require [reagent.core :as reagent]))

;; From https://github.com/reagent-project/reagent-cookbook/tree/master/basics/basic-component 

;; Form-3 component
;;
;; The map expected by `create-class` supports two equivalent keys: `reagent-render` and `render`
;;
(defn foo []
  (reagent/create-class {:render (fn [] [:div "Hello, World"])}))

;; Form-1 component
;;
;; The most common way to create a react component: a function returning hiccup
(defn bar []
  [:div "Hello, World"])

;; Form-2 component
;;
;; A final way to create a react component: a function returning a function of no arguments returning hiccup.
(defn baz []
  (fn []
    [:div "Hello, World"]))

(defn home []
  [:div
   [:h1 "Three equivalent React components"]
   [foo]
   [bar]
   [baz]])

(defn ^:export main []
  (reagent/render [home]
                  (.getElementById js/document "app")))

