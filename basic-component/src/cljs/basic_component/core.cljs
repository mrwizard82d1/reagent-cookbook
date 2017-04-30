(ns basic-component.core
    (:require [reagent.core :as reagent]))

;; From https://github.com/reagent-project/reagent-cookbook/tree/master/basics/basic-component 

;; Form-3 component
;;
;; The map expected by `create-class` supports two equivalent keys: `reagent-render` and `render`
;;
;; I'm a bit confused: the source code indicates that using `render` is exactly equivalent to using `reagent-render`. 
;; (See https://github.com/reagent-project/reagent/blob/e53a5c2b1357c0560f0c4c15b28f00d09e27237b/src/reagent/core.cljs#L110.) 
;; However, the Day-8 wiki on creating Reagent components indicate that `render` will only accept a *single* argument.
;; (See https://github.com/Day8/re-frame/wiki/Creating-Reagent-Components.) I'm tempted to believe the comment in the
;; source code, but I just do not know. To be safe, I'll use `reagent-render`. Sigh...
(defn foo []
  (reagent/create-class {:reagent-render (fn [] [:div "Hello, World"])}))

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

