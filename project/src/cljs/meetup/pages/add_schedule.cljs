(ns meetup.pages.add-schedule
  (:require [meetup.config :as config]
            [meetup.components.fullcalendar :as fullcalendar]
            [meetup.components.gitter :as gitter]
            [meetup.pages.navbar :as navbar]
            [cljsjs.material-ui]
            [cljsjs.material-ui-svg-icons]
            [cljsjs.moment]
            [reagent.core :as reagent]
            [reagent.session :as session]
            [ajax.core :as ajax]
            [ajax.edn :as edn]
            [bidi.bidi :as bidi]))



(defn password-valid?
  "Valid if password is greater than 5 characters"
  [password]
  (> (count password) 5))

(defn password-color [password]
  (let [valid-color "green"
        invalid-color "red"]
    (if (password-valid? password)
      valid-color
      invalid-color)))

(def app-state (reagent/atom {:password nil}))

(defn password []
  [:input {:type "password"
           :on-change #(swap! app-state assoc :password (-> % .-target .-value))}])

(defn home []
  [:div {:style {:margin-top "30px"}}
   "Please enter a password greater than 5 characters. "
   [:span {:style {:padding "20px"
                   :background-color (password-color (@app-state :password))}}
    [password]
    ]])

(defn page []
  "Configured routes, missing an implementation, go here"
  [:span [:h1 "43304: My basdfad"]
   [home]])
