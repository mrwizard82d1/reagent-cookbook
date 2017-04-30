(ns basic-component.core
    (:require [reagent.core :as reagent]))

(defn home []
  [:div
   [foo]
   ])

;; Form-3 component
(defn foo []
  (reagent/create-class {:reagent-render (fn [] [:div "Hello, World"])}))

(defn ^:export main []
  (reagent/render [home]
                  (.getElementById js/document "app")))

