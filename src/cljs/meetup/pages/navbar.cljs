(ns meetup.pages.navbar
  (:require
   [cljsjs.react-bootstrap]
   [bidi.bidi :as bidi]
   [reagent.core :as reagent]))

;; TODO: Is there anything good library for reactbootstrap?
(def Navbar
  (reagent/adapt-react-class (aget js/ReactBootstrap "Navbar")))

(def Navbar-Header
  (reagent/adapt-react-class (aget js/ReactBootstrap.Navbar "Header")))

(def Navbar-Brand
  (reagent/adapt-react-class (aget js/ReactBootstrap.Navbar "Brand")))

(def Navbar-Toggle
  (reagent/adapt-react-class (aget js/ReactBootstrap.Navbar "Toggle")))

(def Navbar-Collapse
  (reagent/adapt-react-class (aget js/ReactBootstrap.Navbar "Collapse")))

(def Nav
  (reagent/adapt-react-class (aget js/ReactBootstrap "Nav")))

(def NavItem
  (reagent/adapt-react-class (aget js/ReactBootstrap "NavItem")))

(def base-url "/meetup.clj")

(defn navbar [app-routes page]
  [Navbar
   [Navbar-Header
    [Navbar-Brand
     [:a {:href (bidi/path-for app-routes :index)} [:i.fa.fa-home {:aria-hidden true}]]]
    [Navbar-Toggle]]

   [Navbar-Collapse
    [Nav {:pullRight true :activeKey page}
     [NavItem {:href "https://www.meetup.com/clojure-tokyo/"}
      [:img {:src (str base-url "/images/clojure.tokyo.jpeg")
             :style {:height "100%" :width "auto"}}]]
     [NavItem {:eventKey :index :href (bidi/path-for app-routes :index)}
      [:i.fa.fa-calendar {:aria-hidden true}] "CALENDAR"]
     [NavItem {:eventKey :list :href (bidi/path-for app-routes :list)}
      [:i.fa.fa-list {:aria-hidden true}] "LIST"]
     [NavItem {:eventKey :chat :href (bidi/path-for app-routes :chat)}
      [:i.fa.fa-comments {:aria-hidden true}] "CHAT"]]]
   ])
